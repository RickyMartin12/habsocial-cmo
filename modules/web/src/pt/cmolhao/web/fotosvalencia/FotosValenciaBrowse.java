package pt.cmolhao.web.fotosvalencia;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
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
import java.util.*;

@UiController("cmolhao_FotosValencia.browse")
@UiDescriptor("fotos-valencia-browse.xml")
@LookupComponent("fotosValenciasTable")
@LoadDataBeforeShow
public class FotosValenciaBrowse extends StandardLookup<FotosValencia> {

    @Inject
    protected UiComponents uiComponents;
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
    private Label<String> text_eq_colaboradores;
    @Inject
    private CheckBox daequipacolaboradoresField;
    @Inject
    private CheckBox doequipamentoField;
    @Inject
    private Label<String> text_do_equipamento;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private LookupField linhasFotValencias;


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
        text_eq_colaboradores.setValue("Equipa de Colaboradores: ");
        text_do_equipamento.setValue("Equipamentos: ");

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
                            customer.setDaequipacolaboradores(daequipacolaboradoresField.getValue());
                            customer.setDoequipamento(doequipamentoField.getValue());
                        })
                        .withScreenClass(FotosValenciaEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    private Component renderAvatarImageComponent(FotosValencia vet) {
        FileDescriptor imageFile = vet.getImage();
        if (imageFile == null) {
            return null;
        }
        Image image = smallAvatarImage();
        image.setSource(FileDescriptorResource.class)
                .setFileDescriptor(imageFile);

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

    @Subscribe("search_foto_valencia")
    public void onSearch_foto_valenciaClick(Button.ClickEvent event) {
        // ID de valencia
        if (idvalenciaField.getValue() != null) {
            fotosValenciasDl.setParameter("idvalencia",  idvalenciaField.getValue().getId());
        } else {
            fotosValenciasDl.removeParameter("idvalencia");
        }

        // Equipa de Colaboradores

        if (daequipacolaboradoresField.getValue())
        {
            fotosValenciasDl.setParameter("daequipacolaboradores",  true);
        } else {
            fotosValenciasDl.removeParameter("daequipacolaboradores");
        }

        // Equipamentos

       if (doequipamentoField.getValue() )
       {
           fotosValenciasDl.setParameter("doequipamento",  true);
       }
       else
       {
           fotosValenciasDl.removeParameter("doequipamento");
       }

        fotosValenciasDl.load();
    }

    @Subscribe("reset_search_foto_valencia")
    public void onReset_search_foto_valenciaClick(Button.ClickEvent event) {
        idvalenciaField.setValue(null);
        daequipacolaboradoresField.setValue(false);
        doequipamentoField.setValue(false);
        linhasFotValencias.setValue(null);
        fotosValenciasDl.removeParameter("daequipacolaboradores");
        fotosValenciasDl.removeParameter("doequipamento");
        fotosValenciasDl.removeParameter("idvalencia");
        fotosValenciasDl.setMaxResults(0);
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


}