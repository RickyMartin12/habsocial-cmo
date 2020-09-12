package pt.cmolhao.web.claso;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.InstituicoesClaso;

@UiController("cmolhao_InstituicoesClaso.edit")
@UiDescriptor("instituicoes-claso-edit.xml")
@EditedEntityContainer("instituicoesClasoDc")
@LoadDataBeforeShow
public class InstituicoesClasoEdit extends StandardEditor<InstituicoesClaso> {
}