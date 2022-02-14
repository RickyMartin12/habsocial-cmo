package pt.cmolhao.web.valencia_intervencao_relatorios;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Valencias;

@UiController("cmolhao_Valencias.browseRelatoriosIntervencao")
@UiDescriptor("valencias-browse.xml")
@LookupComponent("valenciasesTable")
@LoadDataBeforeShow
public class ValenciasBrowse extends StandardLookup<Valencias> {
}