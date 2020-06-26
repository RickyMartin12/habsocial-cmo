package pt.cmolhao.web.equipamento;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Equipamento;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_Equipamento.edit")
@UiDescriptor("equipamento-edit.xml")
@EditedEntityContainer("equipamentoDc")
@LoadDataBeforeShow
public class EquipamentoEdit extends StandardEditor<Equipamento> {

    @Inject
    protected TextField<UUID> idEquipamentoField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Equipamento - " + idEquipamentoField.getValue());
    }


}