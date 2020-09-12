package pt.cmolhao.web.atendimentoencaminhamento;

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
import pt.cmolhao.entity.AtendimentoEncaminhamento;
import pt.cmolhao.web.utentessituacaoprofissional.UtentesSituacaoProfissionalEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_AtendimentoEncaminhamento.browse")
@UiDescriptor("atendimento-encaminhamento-browse.xml")
@LookupComponent("atendimentoEncaminhamentoesTable")
@LoadDataBeforeShow
public class AtendimentoEncaminhamentoBrowse extends StandardLookup<AtendimentoEncaminhamento> {
    @Inject
    protected Table<AtendimentoEncaminhamento> atendimentoEncaminhamentoesTable;
    @Inject
    protected LookupField linhasAtendimentoEncaminhamento;

    @Inject
    protected CollectionLoader<AtendimentoEncaminhamento> atendimentoEncaminhamentoesDl;
    @Inject
    protected TextField<String> aten_enca_id;
    @Named("atendimentoEncaminhamentoesTable.remove")
    protected RemoveAction<AtendimentoEncaminhamento> atendimentoEncaminhamentoesTableRemove;
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
        linhasAtendimentoEncaminhamento.setOptionsList(list);

        atendimentoEncaminhamentoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(atendimentoEncaminhamentoesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (aten_enca_id.getValue() != null)
                            {
                                customer.setAtendimentoEncaminhamento(aten_enca_id.getValue());
                            }
                        })
                        .withScreenClass(AtendimentoEncaminhamentoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe("linhasAtendimentoEncaminhamento")
    protected void onLinhasAtendimentoEncaminhamentoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            atendimentoEncaminhamentoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            atendimentoEncaminhamentoesDl.setMaxResults(0);
        }
        atendimentoEncaminhamentoesDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Atendimento Encaminhamento");
    }

    @Subscribe("reset_atendimento_encaminhamento")
    protected void onReset_atendimento_encaminhamentoClick(Button.ClickEvent event) {
        aten_enca_id.setValue(null);
        atendimentoEncaminhamentoesDl.removeParameter("atendimentoEncaminhamento");
        atendimentoEncaminhamentoesDl.load();
    }

    @Subscribe("search_atendimento_encaminhamento")
    protected void onSearch_atendimento_encaminhamentoClick(Button.ClickEvent event) {
        if (aten_enca_id.getValue() != null)
        {
            atendimentoEncaminhamentoesDl.setParameter("atendimentoEncaminhamento", "(?i)" + aten_enca_id.getValue() + "%");
        }
        else
        {
            atendimentoEncaminhamentoesDl.removeParameter("atendimentoEncaminhamento");
        }

        atendimentoEncaminhamentoesDl.load();
    }

    @Subscribe("atendimentoEncaminhamentoesTable.remove")
    protected void onAtendimentoEncaminhamentoesTableRemove(Action.ActionPerformedEvent event) {
        atendimentoEncaminhamentoesTableRemove.setConfirmation(false);
        if (atendimentoEncaminhamentoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de atendimento por encaminhamento")
                    .withMessage("Deve seleccionar pelo um das atendimentos por encaminhamentos")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            AtendimentoEncaminhamento user = atendimentoEncaminhamentoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do atendimento por encaminhamento número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do atendimento por encaminhamento número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        atendimentoEncaminhamentoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}