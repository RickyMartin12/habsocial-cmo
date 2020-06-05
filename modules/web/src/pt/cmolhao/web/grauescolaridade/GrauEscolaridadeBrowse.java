package pt.cmolhao.web.grauescolaridade;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.GrauEscolaridade;

@UiController("cmolhao_GrauEscolaridade.browse")
@UiDescriptor("grau-escolaridade-browse.xml")
@LookupComponent("grauEscolaridadesTable")
@LoadDataBeforeShow
public class GrauEscolaridadeBrowse extends StandardLookup<GrauEscolaridade> {
}