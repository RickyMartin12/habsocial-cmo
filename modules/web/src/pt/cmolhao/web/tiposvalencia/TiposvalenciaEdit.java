package pt.cmolhao.web.tiposvalencia;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.RespostaSocial;
import pt.cmolhao.entity.Tiposvalencia;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_Tiposvalencia.edit")
@UiDescriptor("tiposvalencia-edit.xml")
@EditedEntityContainer("tiposvalenciaDc")
@LoadDataBeforeShow
public class TiposvalenciaEdit extends StandardEditor<Tiposvalencia> {

    @Inject
    protected TextField<UUID> idTipoValenciaField;
    @Inject
    protected Table<Valencias> valenciasesTable;
    @Named("valenciasesTable.remove")
    protected RemoveAction<Valencias> valenciasesTableRemove;
    @Inject
    protected Table<RespostaSocial> respostaSocialsTable;
    @Named("respostaSocialsTable.remove")
    protected RemoveAction<RespostaSocial> respostaSocialsTableRemove;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Valência - " + idTipoValenciaField.getValue());
    }


    @Subscribe("valenciasesTable.remove")
    protected void onValenciasesTableRemove(Action.ActionPerformedEvent event) {
        valenciasesTableRemove.setConfirmation(false);
        if (valenciasesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de valênçias")
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
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da valênçias número '"+user.getId()+"'?")
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

    @Subscribe("respostaSocialsTable.remove")
    protected void onRespostaSocialsTableRemove(Action.ActionPerformedEvent event) {
        respostaSocialsTableRemove.setConfirmation(false);
        if (respostaSocialsTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção da resposta social")
                    .withMessage("Deve seleccionar pelo uma das respostas sociais")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            RespostaSocial user = respostaSocialsTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da resposta social número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da resposta social número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        respostaSocialsTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

}