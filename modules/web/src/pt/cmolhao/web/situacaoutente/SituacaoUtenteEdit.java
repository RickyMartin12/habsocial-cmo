package pt.cmolhao.web.situacaoutente;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.SituacaoUtente;

@UiController("cmolhao_SituacaoUtente.edit")
@UiDescriptor("situacao-utente-edit.xml")
@EditedEntityContainer("situacaoUtenteDc")
@LoadDataBeforeShow
public class SituacaoUtenteEdit extends StandardEditor<SituacaoUtente> {
}