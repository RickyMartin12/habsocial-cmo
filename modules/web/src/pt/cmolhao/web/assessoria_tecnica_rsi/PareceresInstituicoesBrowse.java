package pt.cmolhao.web.assessoria_tecnica_rsi;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.PareceresInstituicoes;

@UiController("cmolhao_PareceresInstituicoes.browseAssessoriaTecnicaRSI")
@UiDescriptor("pareceres-instituicoes-browse.xml")
@LookupComponent("pareceresInstituicoesesTable")
@LoadDataBeforeShow
public class PareceresInstituicoesBrowse extends StandardLookup<PareceresInstituicoes> {
}