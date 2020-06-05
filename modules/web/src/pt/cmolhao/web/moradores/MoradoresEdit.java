package pt.cmolhao.web.moradores;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Moradores;

@UiController("cmolhao_Moradores.edit")
@UiDescriptor("moradores-edit.xml")
@EditedEntityContainer("moradoresDc")
@LoadDataBeforeShow
public class MoradoresEdit extends StandardEditor<Moradores> {
}