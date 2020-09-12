package pt.cmolhao.web.tipossituacoesutentes;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.RendimentosUtente;
import pt.cmolhao.entity.SituacaoUtente;
import pt.cmolhao.entity.TiposSituacoesUtentes;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_TiposSituacoesUtentes.edit")
@UiDescriptor("tipos-situacoes-utentes-edit.xml")
@EditedEntityContainer("tiposSituacoesUtentesDc")
@LoadDataBeforeShow
public class TiposSituacoesUtentesEdit extends StandardEditor<TiposSituacoesUtentes> {

    @Inject
    protected TextField<UUID> idTipoSituacaoUtenteField;
    @Inject
    protected Table<SituacaoUtente> situacaoUtentesTable;
    @Named("situacaoUtentesTable.remove")
    protected RemoveAction<SituacaoUtente> situacaoUtentesTableRemove;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Situação do Utente - " + idTipoSituacaoUtenteField.getValue());
    }

    @Subscribe("situacaoUtentesTable.remove")
    protected void onSituacaoUtentesTableRemove(Action.ActionPerformedEvent event) {
        situacaoUtentesTableRemove.setConfirmation(false);
        if (situacaoUtentesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de situações dos utentes")
                    .withMessage("Deve seleccionar pelo um das situações dos utentes")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            SituacaoUtente user = situacaoUtentesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da situação do utente número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da situação do utente número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        situacaoUtentesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}