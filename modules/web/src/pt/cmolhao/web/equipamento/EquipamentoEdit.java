package pt.cmolhao.web.equipamento;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Equipamento;

@UiController("cmolhao_Equipamento.edit")
@UiDescriptor("equipamento-edit.xml")
@EditedEntityContainer("equipamentoDc")
@LoadDataBeforeShow
public class EquipamentoEdit extends StandardEditor<Equipamento> {
}