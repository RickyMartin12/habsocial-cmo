package pt.cmolhao.web.assessoria_tecnica_ambiolhao;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.PareceresInstituicoes;

@UiController("cmolhao_PareceresInstituicoes.browseAssessoriaTecnicaAmboiOlhao")
@UiDescriptor("pareceres-instituicoes-browse.xml")
@LookupComponent("pareceresInstituicoesesTable")
@LoadDataBeforeShow
public class PareceresInstituicoesBrowse extends StandardLookup<PareceresInstituicoes> {
}