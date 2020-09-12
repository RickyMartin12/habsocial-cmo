package pt.cmolhao.web.valencias;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Valencias.browse")
@UiDescriptor("valencias-browse.xml")
@LookupComponent("valenciasesTable")
@LoadDataBeforeShow
public class ValenciasBrowse extends StandardLookup<Valencias> {
    @Inject
    protected TextField<String> descricaotecnicaField;
    @Named("valenciasesTable.remove")
    protected RemoveAction<Valencias> valenciasesTableRemove;
    @Inject
    private LookupField<Instituicoes> idinstituicaoField;

    @Inject
    private Notifications notifications;
    @Inject
    private CollectionLoader<Valencias> valenciasesDl;
    @Inject
    private LookupPickerField<Tiposvalencia> idtipovalenciaField;
    @Inject
    private GroupTable<Valencias> valenciasesTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private LookupField linhasValencias;

    @Inject
    private Dialogs dialogs;



    @Subscribe
    public void onInit(InitEvent event) {

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
        linhasValencias.setOptionsList(list);


        valenciasesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(valenciasesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdinstituicao(idinstituicaoField.getValue());
                            customer.setIdtipovalencia(idtipovalenciaField.getValue());
                            if (descricaotecnicaField.getValue() != null)
                            {
                                customer.setDescricaotecnica(descricaotecnicaField.getValue());
                            }

                        })
                        .withScreenClass(ValenciasEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }




    @Subscribe("search_valencia")
    public void onSearch_valenciaClick(Button.ClickEvent event) {


        // Id de instituição
        if (idinstituicaoField.getValue() != null) {
            valenciasesDl.setParameter("idinstituicao",  idinstituicaoField.getValue().getId());
        } else {
            valenciasesDl.removeParameter("idinstituicao");
        }

        // Tipo de valencia
        if (idtipovalenciaField.getValue() != null) {
            valenciasesDl.setParameter("idtipovalencia",  idtipovalenciaField.getValue().getId());
        } else {
            valenciasesDl.removeParameter("idtipovalencia");
        }

        // Descricao
        if (descricaotecnicaField.getValue() != null) {
            valenciasesDl.setParameter("descricaotecnica", "(?i)%" + descricaotecnicaField.getValue() + "%");
        } else {
            valenciasesDl.removeParameter("descricaotecnica");
        }


        valenciasesDl.load();
    }

    @Subscribe("reset_valencia")
    public void onReset_valenciaClick(Button.ClickEvent event) {
        idinstituicaoField.setValue(null);
        idtipovalenciaField.setValue(null);
        descricaotecnicaField.setValue(null);
        valenciasesDl.removeParameter("idinstituicao");
        valenciasesDl.removeParameter("idtipovalencia");
        valenciasesDl.removeParameter("descricaotecnica");
        valenciasesDl.load();

    }

    @Subscribe("linhasValencias")
    public void onLinhasValenciasValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            valenciasesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            valenciasesDl.setMaxResults(0);
        }
        valenciasesDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Valências");
    }

    @Subscribe("valenciasesTable.remove")
    protected void onValenciasesTableRemove(Action.ActionPerformedEvent event) {
        valenciasesTableRemove.setConfirmation(false);
        if (valenciasesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de valênçias")
                    .withMessage("Deve seleccionar pelo um das valênçias")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Valencias user = valenciasesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da valênçia número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da valênçias número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        valenciasesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}