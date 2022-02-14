package pt.cmolhao.web.apoios_financeiro_candidaturas;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Apoios;

@UiController("cmolhao_Apoios.browseFinanceirosCandidaturas")
@UiDescriptor("apoios-browse.xml")
@LookupComponent("apoiosesTable")
@LoadDataBeforeShow
public class ApoiosBrowse extends StandardLookup<Apoios> {
}