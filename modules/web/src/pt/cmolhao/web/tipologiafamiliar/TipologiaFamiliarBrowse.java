package pt.cmolhao.web.tipologiafamiliar;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipologiaFamiliar;

@UiController("cmolhao_TipologiaFamiliar.browse")
@UiDescriptor("tipologia-familiar-browse.xml")
@LookupComponent("tipologiaFamiliarsTable")
@LoadDataBeforeShow
public class TipologiaFamiliarBrowse extends StandardLookup<TipologiaFamiliar> {
}