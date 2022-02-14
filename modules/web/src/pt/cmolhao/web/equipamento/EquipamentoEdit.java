package pt.cmolhao.web.equipamento;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Apoios;
import pt.cmolhao.entity.Equipamento;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_Equipamento.edit")
@UiDescriptor("equipamento-edit.xml")
@EditedEntityContainer("equipamentoDc")
@LoadDataBeforeShow
public class EquipamentoEdit extends StandardEditor<Equipamento> {

    @Inject
    protected TextField<UUID> idEquipamentoField;
    @Inject
    protected Table<Apoios> apoiosesTable;
    @Named("apoiosesTable.remove")
    protected RemoveAction<Apoios> apoiosesTableRemove;
    @Inject
    protected UiComponents uiComponents;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Equipamento - " + idEquipamentoField.getValue());
    }

    @Subscribe("apoiosesTable.remove")
    protected void onApoiosesTableRemove(Action.ActionPerformedEvent event) {
        apoiosesTableRemove.setConfirmation(false);
        if (apoiosesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção dos apoios")
                    .withMessage("Deve seleccionar pelo um dos apoios")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Apoios user = apoiosesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela de apoio número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela de apoio número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        apoiosesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


    public Component generateValorApoio(Apoios entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getValorApoio() != null)
        {
            label.setValue(entity.getValorApoio() + " €");
            return label;
        }
        return null;
    }
}