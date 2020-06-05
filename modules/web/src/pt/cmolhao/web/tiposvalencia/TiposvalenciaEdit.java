package pt.cmolhao.web.tiposvalencia;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Tiposvalencia;

@UiController("cmolhao_Tiposvalencia.edit")
@UiDescriptor("tiposvalencia-edit.xml")
@EditedEntityContainer("tiposvalenciaDc")
@LoadDataBeforeShow
public class TiposvalenciaEdit extends StandardEditor<Tiposvalencia> {
}