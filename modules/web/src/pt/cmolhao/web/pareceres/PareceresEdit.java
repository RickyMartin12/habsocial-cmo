package pt.cmolhao.web.pareceres;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Pareceres;

@UiController("cmolhao_Pareceres.edit")
@UiDescriptor("pareceres-edit.xml")
@EditedEntityContainer("pareceresDc")
@LoadDataBeforeShow
public class PareceresEdit extends StandardEditor<Pareceres> {
}