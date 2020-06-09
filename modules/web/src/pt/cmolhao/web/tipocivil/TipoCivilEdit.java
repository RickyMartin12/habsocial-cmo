package pt.cmolhao.web.tipocivil;

import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoCivil;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoCivil.edit")
@UiDescriptor("tipo-civil-edit.xml")
@EditedEntityContainer("tipoCivilDc")
@LoadDataBeforeShow
public class TipoCivilEdit extends StandardEditor<TipoCivil> {
}