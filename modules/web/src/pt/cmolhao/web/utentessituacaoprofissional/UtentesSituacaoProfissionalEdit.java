package pt.cmolhao.web.utentessituacaoprofissional;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.UtentesSituacaoProfissional;

@UiController("cmolhao_UtentesSituacaoProfissional.edit")
@UiDescriptor("utentes-situacao-profissional-edit.xml")
@EditedEntityContainer("utentesSituacaoProfissionalDc")
@LoadDataBeforeShow
public class UtentesSituacaoProfissionalEdit extends StandardEditor<UtentesSituacaoProfissional> {
}