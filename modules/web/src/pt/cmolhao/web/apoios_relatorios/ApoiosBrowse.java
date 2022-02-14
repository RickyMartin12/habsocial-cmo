package pt.cmolhao.web.apoios_relatorios;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Apoios;

@UiController("cmolhao_Apoios.browseRelatorios")
@UiDescriptor("apoios-browse.xml")
@LookupComponent("apoiosesTable")
@LoadDataBeforeShow
public class ApoiosBrowse extends StandardLookup<Apoios> {
}