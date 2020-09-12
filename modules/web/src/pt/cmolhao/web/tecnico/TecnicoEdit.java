package pt.cmolhao.web.tecnico;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Atendimento;
import pt.cmolhao.entity.Tecnico;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_Tecnico.edit")
@UiDescriptor("tecnico-edit.xml")
@EditedEntityContainer("tecnicoDc")
@LoadDataBeforeShow
public class TecnicoEdit extends StandardEditor<Tecnico> {

    @Inject
    protected TextField<UUID> idTecnicoField;
    @Inject
    protected Table<Atendimento> atendimentoesTable;
    @Named("atendimentoesTable.remove")
    protected RemoveAction<Atendimento> atendimentoesTableRemove;
    @Inject
    private Dialogs dialogs;


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Técnico - " + idTecnicoField.getValue());
    }

    @Subscribe("atendimentoesTable.remove")
    protected void onAtendimentoesTableRemove(Action.ActionPerformedEvent event) {
        atendimentoesTableRemove.setConfirmation(false);
        if (atendimentoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de ajudas técnicas")
                    .withMessage("Deve seleccionar pelo um das ajudas técnicas")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Atendimento user = atendimentoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da ajuda técnica número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da ajuda técnica número '"+user.getId()+"'?")
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