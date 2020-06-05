package pt.cmolhao.web.estados;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Estados;

@UiController("cmolhao_Estados.edit")
@UiDescriptor("estados-edit.xml")
@EditedEntityContainer("estadosDc")
@LoadDataBeforeShow
public class EstadosEdit extends StandardEditor<Estados> {
}