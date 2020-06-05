package pt.cmolhao.web.tipologiafamiliar;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipologiaFamiliar;

@UiController("cmolhao_TipologiaFamiliar.edit")
@UiDescriptor("tipologia-familiar-edit.xml")
@EditedEntityContainer("tipologiaFamiliarDc")
@LoadDataBeforeShow
public class TipologiaFamiliarEdit extends StandardEditor<TipologiaFamiliar> {
}