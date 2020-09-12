package pt.cmolhao.web.tiporendimento;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.RendimentosUtente;
import pt.cmolhao.entity.TipoRendimento;
import pt.cmolhao.entity.UtentesRelacionados;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_TipoRendimento.edit")
@UiDescriptor("tipo-rendimento-edit.xml")
@EditedEntityContainer("tipoRendimentoDc")
@LoadDataBeforeShow
public class TipoRendimentoEdit extends StandardEditor<TipoRendimento> {


    @Inject
    protected TextField<UUID> idTipoRendimentoField;

    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected Table<RendimentosUtente> rendimentosUtentesTable;
    @Named("rendimentosUtentesTable.remove")
    protected RemoveAction<RendimentosUtente> rendimentosUtentesTableRemove;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Rendimento - " + idTipoRendimentoField.getValue());
    }

    public Component generateValorRendimento(RendimentosUtente entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getValorRendimento() != null)
        {
            label.setValue(entity.getValorRendimento() + " €");
            return label;
        }
        return null;
    }

    @Subscribe("rendimentosUtentesTable.remove")
    protected void onRendimentosUtentesTableRemove(Action.ActionPerformedEvent event) {
        rendimentosUtentesTableRemove.setConfirmation(false);
        if (rendimentosUtentesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de utentes relacionados")
                    .withMessage("Deve seleccionar pelo um dos utentes relacionados")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            RendimentosUtente user = rendimentosUtentesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do utente relacionado número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do utente relacionado número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        rendimentosUtentesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}