package pt.cmolhao.web.apoios;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Apoios;

@UiController("cmolhao_Apoios.edit")
@UiDescriptor("apoios-edit.xml")
@EditedEntityContainer("apoiosDc")
@LoadDataBeforeShow
public class ApoiosEdit extends StandardEditor<Apoios> {
}