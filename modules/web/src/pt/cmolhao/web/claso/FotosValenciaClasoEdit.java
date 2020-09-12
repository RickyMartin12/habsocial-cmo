package pt.cmolhao.web.claso;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.FotosValenciaClaso;

@UiController("cmolhao_FotosValenciaClaso.edit")
@UiDescriptor("fotos-valencia-claso-edit.xml")
@EditedEntityContainer("fotosValenciaClasoDc")
@LoadDataBeforeShow
public class FotosValenciaClasoEdit extends StandardEditor<FotosValenciaClaso> {
}