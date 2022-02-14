package pt.cmolhao.web.tipocivil;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoCivil;
import pt.cmolhao.entity.Utente;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_TipoCivil.edit")
@UiDescriptor("tipo-civil-edit.xml")
@EditedEntityContainer("tipoCivilDc")
@LoadDataBeforeShow
public class TipoCivilEdit extends StandardEditor<TipoCivil> {

    @Inject
    protected TextField<UUID> idTipoCivilField;
    @Inject
    protected Table<Utente> utentesTable;
    @Named("utentesTable.remove")
    protected RemoveAction<Utente> utentesTableRemove;
    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Civil - " + idTipoCivilField.getValue());
    }

    @Subscribe("utentesTable.remove")
    protected void onUtentesTableRemove(Action.ActionPerformedEvent event) {
        utentesTableRemove.setConfirmation(false);
        if (utentesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do utente")
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