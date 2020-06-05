package pt.cmolhao.web.tecnico;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Tecnico;

@UiController("cmolhao_Tecnico.edit")
@UiDescriptor("tecnico-edit.xml")
@EditedEntityContainer("tecnicoDc")
@LoadDataBeforeShow
public class TecnicoEdit extends StandardEditor<Tecnico> {
}