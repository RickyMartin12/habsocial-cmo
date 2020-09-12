package pt.cmolhao.web.fotosvalencia;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import jdk.internal.jline.internal.Nullable;
import pt.cmolhao.entity.FotosValencia;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.web.habitacaosocial.HabitacaoSocialEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@UiController("cmolhao_FotosValencia.browse")
@UiDescriptor("fotos-valencia-browse.xml")
@LookupComponent("fotosValenciasTable")
@LoadDataBeforeShow
public class FotosValenciaBrowse extends StandardLookup<FotosValencia> {

    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected TextField<String> descricaoField;
    @Named("fotosValenciasTable.remove")
    protected RemoveAction<FotosValencia> fotosValenciasTableRemove;
    @Inject
    private GroupTable<FotosValencia> fotosValenciasTable;
    @Inject
    private Notifications notifications;
    @Inject
    private LookupField<Valencias> idvalenciaField;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;
    @Inject
    private CollectionLoader<FotosValencia> fotosValenciasDl;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private LookupField linhasFotValencias;

    @Inject
    private Dialogs dialogs;


    public Component generateValenciasDescricao(FotosValencia entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdvalencia() != null)
        {
            label.setValue(entity.getIdvalencia().getDescricaotecnica());
            return label;
        }
        return null;
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Fotos de Valências");

        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            if (item != null) {
                map.put(item.getDescricaotecnica() + " " , item);
            }


        }
        idvalenciaField.setOptionsMap(map);
    }


    @Subscribe
    protected void onInit(InitEvent event) {

        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(50);
        list.add(100);
        list.add(200);
        list.add(500);
        list.add(1000);
        list.add(2000);
        list.add(5000);
        list.add(10000);
        linhasFotValencias.setOptionsList(list);

        fotosValenciasTable.addGeneratedColumn(
                "image",
                this::renderAvatarImageComponent
        );




        fotosValenciasTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(fotosValenciasTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdvalencia(idvalenciaField.getValue());
                        })
                        .withScreenClass(FotosValenciaEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    private Component renderAvatarImageComponent(FotosValencia vet) {
        FileDescriptor imageFile = vet.getImage();
        Image image = null;
        if (imageFile == null) {
            image = nofotoImage();
            image.setSource(ThemeResource.class)
                    .setPath("images/no_image.jpg");
        }
        else
        {
            image = smallAvatarImage();
            image.setSource(FileDescriptorResource.class)
                    .setFileDescriptor(imageFile);
        }
        return image;

    }

    private Image smallAvatarImage() {
        Image image = uiComponents.create(Image.class);
        image.setScaleMode(Image.ScaleMode.CONTAIN);
        image.setHeight("400");
        image.setWidth("400");
        image.setStyleName("avatar-icon-small");
        return image;
    }

    private Image nofotoImage() {
        Image image = uiComponents.create(Image.class);
        image.setScaleMode(Image.ScaleMode.CONTAIN);
        image.setHeight("100");
        image.setWidth("100");
        image.setStyleName("avatar-icon-small");
        return image;
    }

    @Subscribe("search_foto_valencia")
    public void onSearch_foto_valenciaClick(Button.ClickEvent event) {
        // ID de valencia
        if (idvalenciaField.getValue() != null) {
            fotosValenciasDl.setParameter("idvalencia",  idvalenciaField.getValue().getId());
        } else {
            fotosValenciasDl.removeParameter("idvalencia");
        }

        if (descricaoField.getValue() != null) {
            fotosValenciasDl.setParameter("descricao",  "(?i)%" + descricaoField.getValue() + "%");
        } else {
            fotosValenciasDl.removeParameter("descricao");
        }


        fotosValenciasDl.load();
    }

    @Subscribe("reset_search_foto_valencia")
    public void onReset_search_foto_valenciaClick(Button.ClickEvent event) {
        idvalenciaField.setValue(null);
        descricaoField.setValue(null);
        fotosValenciasDl.removeParameter("daequipacolaboradores");
        fotosValenciasDl.removeParameter("descricao");
        fotosValenciasDl.load();
    }

    @Subscribe("linhasFotValencias")
    public void onLinhasFotValenciasValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            fotosValenciasDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            fotosValenciasDl.setMaxResults(0);
        }
        fotosValenciasDl.load();
    }

    @Subscribe("fotosValenciasTable.remove")
    protected void onFotosValenciasTableRemove(Action.ActionPerformedEvent event) {
        fotosValenciasTableRemove.setConfirmation(false);
        if (fotosValenciasTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de fotos de valênçia")
                    .withMessage("Deve seleccionar pelo um das fotos de valênçia")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            FotosValencia user = fotosValenciasTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da foto de valênçia número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da foto de valênçia número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        fotosValenciasTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}