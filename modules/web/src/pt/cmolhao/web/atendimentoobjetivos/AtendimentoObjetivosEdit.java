package pt.cmolhao.web.atendimentoobjetivos;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Atendimento;
import pt.cmolhao.entity.AtendimentoObjetivos;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_AtendimentoObjetivos.edit")
@UiDescriptor("atendimento-objetivos-edit.xml")
@EditedEntityContainer("atendimentoObjetivosDc")
@LoadDataBeforeShow
public class AtendimentoObjetivosEdit extends StandardEditor<AtendimentoObjetivos> {

    @Inject
    protected TextField<UUID> idAtendimentoObjetivosField;
    @Inject
    protected Table<Atendimento> atendimentoesTable;
    @Named("atendimentoesTable.remove")
    protected RemoveAction<Atendimento> atendimentoesTableRemove;
    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Atendimento Objetivos - " + idAtendimentoObjetivosField.getValue());
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