package pt.cmolhao.web.utentesrelacionados;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.UtentesRelacionados;

@UiController("cmolhao_UtentesRelacionados.browse")
@UiDescriptor("utentes-relacionados-browse.xml")
@LookupComponent("utentesRelacionadosesTable")
@LoadDataBeforeShow
public class UtentesRelacionadosBrowse extends StandardLookup<UtentesRelacionados> {
}