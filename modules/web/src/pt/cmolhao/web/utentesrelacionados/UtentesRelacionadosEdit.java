package pt.cmolhao.web.utentesrelacionados;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.UtentesRelacionados;

@UiController("cmolhao_UtentesRelacionados.edit")
@UiDescriptor("utentes-relacionados-edit.xml")
@EditedEntityContainer("utentesRelacionadosDc")
@LoadDataBeforeShow
public class UtentesRelacionadosEdit extends StandardEditor<UtentesRelacionados> {
}