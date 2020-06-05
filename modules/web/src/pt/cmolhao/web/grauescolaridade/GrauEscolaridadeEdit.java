package pt.cmolhao.web.grauescolaridade;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.GrauEscolaridade;

@UiController("cmolhao_GrauEscolaridade.edit")
@UiDescriptor("grau-escolaridade-edit.xml")
@EditedEntityContainer("grauEscolaridadeDc")
@LoadDataBeforeShow
public class GrauEscolaridadeEdit extends StandardEditor<GrauEscolaridade> {
}