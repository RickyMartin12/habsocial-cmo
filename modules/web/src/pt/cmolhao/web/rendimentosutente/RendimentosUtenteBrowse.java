package pt.cmolhao.web.rendimentosutente;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.RendimentosUtente;

@UiController("cmolhao_RendimentosUtente.browse")
@UiDescriptor("rendimentos-utente-browse.xml")
@LookupComponent("rendimentosUtentesTable")
@LoadDataBeforeShow
public class RendimentosUtenteBrowse extends StandardLookup<RendimentosUtente> {
}