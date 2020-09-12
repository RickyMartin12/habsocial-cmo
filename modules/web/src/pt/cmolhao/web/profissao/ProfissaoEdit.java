package pt.cmolhao.web.profissao;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Profissao;
import pt.cmolhao.entity.Utente;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_Profissao.edit")
@UiDescriptor("profissao-edit.xml")
@EditedEntityContainer("profissaoDc")
@LoadDataBeforeShow
public class ProfissaoEdit extends StandardEditor<Profissao> {

    @Inject
    protected TextField<UUID> idProfissaoField;
    @Inject
    protected Table<Utente> utentesTable;
    @Named("utentesTable.remove")
    protected RemoveAction<Utente> utentesTableRemove;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Profissões - " + idProfissaoField.getValue());
    }

    @Subscribe("utentesTable.remove")
    protected void onUtentesTableRemove(Action.ActionPerformedEvent event) {
        utentesTableRemove.setConfirmation(false);
        if (utentesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de utentes")
                    .withMessage("Deve seleccionar pelo um dos utentes")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Utente user = utentesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do utente número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do utente número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        utentesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }

    }
}