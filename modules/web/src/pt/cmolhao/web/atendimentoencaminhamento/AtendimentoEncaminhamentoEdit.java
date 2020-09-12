package pt.cmolhao.web.atendimentoencaminhamento;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Atendimento;
import pt.cmolhao.entity.AtendimentoEncaminhamento;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_AtendimentoEncaminhamento.edit")
@UiDescriptor("atendimento-encaminhamento-edit.xml")
@EditedEntityContainer("atendimentoEncaminhamentoDc")
@LoadDataBeforeShow
public class AtendimentoEncaminhamentoEdit extends StandardEditor<AtendimentoEncaminhamento> {

    @Inject
    protected TextField<UUID> idAtendimentoEncaminhamentoField;
    @Named("atendimentoesTable.remove")
    protected RemoveAction<Atendimento> atendimentoesTableRemove;
    @Inject
    protected Table<Atendimento> atendimentoesTable;
    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Atendimento Encaminhamento - " + idAtendimentoEncaminhamentoField.getValue());
    }

    @Subscribe("atendimentoesTable.remove")
    protected void onAtendimentoesTableRemove(Action.ActionPerformedEvent event) {
        atendimentoesTableRemove.setConfirmation(false);
        if (atendimentoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de atendimentos")
                    .withMessage("Deve seleccionar pelo um dos atendimentos")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Atendimento user = atendimentoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do atendimento número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do atendimento número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        atendimentoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

}