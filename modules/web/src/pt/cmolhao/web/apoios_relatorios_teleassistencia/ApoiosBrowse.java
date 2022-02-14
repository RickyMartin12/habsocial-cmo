package pt.cmolhao.web.apoios_relatorios_teleassistencia;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Apoios;

@UiController("cmolhao_Apoios.browseRelatoriosTeleassistencia")
@UiDescriptor("apoios-browse.xml")
@LookupComponent("apoiosesTable")
@LoadDataBeforeShow
public class ApoiosBrowse extends StandardLookup<Apoios> {
}