package pt.cmolhao.web.valecias_relatorios_pessoas_idosas;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Valencias;

@UiController("cmolhao_Valencias.browseRelatoriosPessoasIdosas")
@UiDescriptor("valencias-browse.xml")
@LookupComponent("valenciasesTable")
@LoadDataBeforeShow
public class ValenciasBrowse extends StandardLookup<Valencias> {
}