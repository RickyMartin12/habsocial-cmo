package pt.cmolhao.web.situacaoutente;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.SituacaoUtente;

@UiController("cmolhao_SituacaoUtente.browse")
@UiDescriptor("situacao-utente-browse.xml")
@LookupComponent("situacaoUtentesTable")
@LoadDataBeforeShow
public class SituacaoUtenteBrowse extends StandardLookup<SituacaoUtente> {
}