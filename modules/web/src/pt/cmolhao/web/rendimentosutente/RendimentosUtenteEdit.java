package pt.cmolhao.web.rendimentosutente;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.RendimentosUtente;

@UiController("cmolhao_RendimentosUtente.edit")
@UiDescriptor("rendimentos-utente-edit.xml")
@EditedEntityContainer("rendimentosUtenteDc")
@LoadDataBeforeShow
public class RendimentosUtenteEdit extends StandardEditor<RendimentosUtente> {
}