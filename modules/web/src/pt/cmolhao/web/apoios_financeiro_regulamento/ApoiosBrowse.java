package pt.cmolhao.web.apoios_financeiro_regulamento;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Apoios;

@UiController("cmolhao_Apoios.browseApoiosFinanceirosRegulamentos")
@UiDescriptor("apoios-browse.xml")
@LookupComponent("apoiosesTable")
@LoadDataBeforeShow
public class ApoiosBrowse extends StandardLookup<Apoios> {
}