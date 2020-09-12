package pt.cmolhao.web.atendimentoobjetivos;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.AtendimentoObjetivos;
import pt.cmolhao.web.atendimentoencaminhamento.AtendimentoEncaminhamentoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_AtendimentoObjetivos.browse")
@UiDescriptor("atendimento-objetivos-browse.xml")
@LookupComponent("atendimentoObjetivosesTable")
@LoadDataBeforeShow
public class AtendimentoObjetivosBrowse extends StandardLookup<AtendimentoObjetivos> {
    @Inject
    protected Table<AtendimentoObjetivos> atendimentoObjetivosesTable;
    @Inject
    protected LookupField linhasAtendimentoObjectivos;

    @Inject
    protected CollectionLoader<AtendimentoObjetivos> atendimentoObjetivosesDl;
    @Inject
    protected TextField<String> aten_enca_id;
    @Named("atendimentoObjetivosesTable.remove")
    protected RemoveAction<AtendimentoObjetivos> atendimentoObjetivosesTableRemove;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private Dialogs dialogs;

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
        linhasAtendimentoObjectivos.setOptionsList(list);

        atendimentoObjetivosesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                        screenBuilders.editor(atendimentoObjetivosesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (aten_enca_id.getValue() != null)
                            {
                                customer.setAtendimentoObjetivoGeral(aten_enca_id.getValue());
                            }
                        })
                        .withScreenClass(AtendimentoObjetivosEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Atendimento Objetivos");

    }

    @Subscribe("search_atendimento_objectivos")
    protected void onSearch_atendimento_objectivosClick(Button.ClickEvent event) {
        if (aten_enca_id.getValue() != null)
        {
            atendimentoObjetivosesDl.setParameter("atendimentoObjetivoGeral", "(?i)" + aten_enca_id.getValue() + "%");
        }
        else
        {
            atendimentoObjetivosesDl.removeParameter("atendimentoObjetivoGeral");
        }

        atendimentoObjetivosesDl.load();
    }

    @Subscribe("reset_atendimento_objectivos")
    protected void onReset_atendimento_objectivosClick(Button.ClickEvent event) {
        aten_enca_id.setValue(null);
        atendimentoObjetivosesDl.removeParameter("atendimentoObjetivoGeral");
        atendimentoObjetivosesDl.load();
    }

    @Subscribe("linhasAtendimentoObjectivos")
    protected void onLinhasAtendimentoObjectivosValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            atendimentoObjetivosesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            atendimentoObjetivosesDl.setMaxResults(0);
        }
        atendimentoObjetivosesDl.load();
    }

    @Subscribe("atendimentoObjetivosesTable.remove")
    protected void onAtendimentoObjetivosesTableRemove(Action.ActionPerformedEvent event) {
        atendimentoObjetivosesTableRemove.setConfirmation(false);
        if (atendimentoObjetivosesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de atendimento por objectivos")
                    .withMessage("Deve seleccionar pelo um dos atendimentos por objectivos")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            AtendimentoObjetivos user = atendimentoObjetivosesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela de atendimento por objectivos '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do atendimento por objectivos número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        atendimentoObjetivosesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}