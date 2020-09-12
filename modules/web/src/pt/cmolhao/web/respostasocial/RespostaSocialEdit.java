package pt.cmolhao.web.respostasocial;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.RespostaSocial;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_RespostaSocial.edit")
@UiDescriptor("resposta-social-edit.xml")
@EditedEntityContainer("respostaSocialDc")
@LoadDataBeforeShow
public class RespostaSocialEdit extends StandardEditor<RespostaSocial> {
    @Inject
    protected TextField<UUID> idRespostaSocialField;
    @Inject
    protected Table<Valencias> valenciasesTable;
    @Named("valenciasesTable.remove")
    protected RemoveAction<Valencias> valenciasesTableRemove;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Resposta Social - " + idRespostaSocialField.getValue());


    }

    @Subscribe("valenciasesTable.remove")
    protected void onValenciasesTableRemove(Action.ActionPerformedEvent event) {
        valenciasesTableRemove.setConfirmation(false);
        if (valenciasesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção das valênçias")
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
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da valênçia número '"+user.getId()+"'?")
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