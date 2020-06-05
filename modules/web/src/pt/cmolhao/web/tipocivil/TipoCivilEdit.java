package pt.cmolhao.web.tipocivil;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoCivil;

@UiController("cmolhao_TipoCivil.edit")
@UiDescriptor("tipo-civil-edit.xml")
@EditedEntityContainer("tipoCivilDc")
@LoadDataBeforeShow
public class TipoCivilEdit extends StandardEditor<TipoCivil> {
}