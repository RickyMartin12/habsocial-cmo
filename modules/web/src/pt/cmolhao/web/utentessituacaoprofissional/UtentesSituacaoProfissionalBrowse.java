package pt.cmolhao.web.utentessituacaoprofissional;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.UtentesSituacaoProfissional;

@UiController("cmolhao_UtentesSituacaoProfissional.browse")
@UiDescriptor("utentes-situacao-profissional-browse.xml")
@LookupComponent("utentesSituacaoProfissionalsTable")
@LoadDataBeforeShow
public class UtentesSituacaoProfissionalBrowse extends StandardLookup<UtentesSituacaoProfissional> {
}