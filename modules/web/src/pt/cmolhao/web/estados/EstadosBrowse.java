package pt.cmolhao.web.estados;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Estados;
import pt.cmolhao.web.tipoajuda.TipoAjudaEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Estados.browse")
@UiDescriptor("estados-browse.xml")
@LookupComponent("estadosesTable")
@LoadDataBeforeShow
public class EstadosBrowse extends StandardLookup<Estados> {
    @Inject
    protected LookupField linhasEstados;

    @Inject
    protected CollectionLoader<Estados> estadosesDl;
    @Inject
    protected Table<Estados> estadosesTable;
    @Inject
    protected TextField<String> est_processos_apoio_id;
    @Named("estadosesTable.remove")
    protected RemoveAction<Estados> estadosesTableRemove;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

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
        linhasEstados.setOptionsList(list);

        estadosesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(estadosesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (est_processos_apoio_id.getValue() != null)
                            {
                                customer.setEstadosProcessos(est_processos_apoio_id.getValue().toString());
                            }
                        })
                        .withScreenClass(EstadosEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("linhasEstados")
    protected void onLinhasEstadosValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            estadosesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            estadosesDl.setMaxResults(0);
        }
        estadosesDl.load();
    }

    @Subscribe("reset_tipo_ajuda")
    protected void onReset_tipo_ajudaClick(Button.ClickEvent event) {
        est_processos_apoio_id.setValue(null);
        estadosesDl.removeParameter("estadosProcessos");
        estadosesDl.load();
    }

    @Subscribe("search_tipo_ajuda")
    protected void onSearch_tipo_ajudaClick(Button.ClickEvent event) {
        if (est_processos_apoio_id.getValue() != null) {
            estadosesDl.setParameter("estadosProcessos",  "(?i)%" + est_processos_apoio_id.getValue() + "%");
        } else {
            estadosesDl.removeParameter("estadosProcessos");
        }
        estadosesDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Estados");
    }

    @Subscribe("estadosesTable.remove")
    protected void onEstadosesTableRemove(Action.ActionPerformedEvent event) {
        estadosesTableRemove.setConfirmation(false);
        if (estadosesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de estados")
                    .withMessage("Deve seleccionar pelo um dos estados")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Estados user = estadosesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do estado número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do estado número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        estadosesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }

    }


}