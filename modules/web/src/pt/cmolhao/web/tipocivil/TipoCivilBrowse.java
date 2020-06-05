package pt.cmolhao.web.tipocivil;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoCivil;

@UiController("cmolhao_TipoCivil.browse")
@UiDescriptor("tipo-civil-browse.xml")
@LookupComponent("tipoCivilsTable")
@LoadDataBeforeShow
public class TipoCivilBrowse extends StandardLookup<TipoCivil> {
}