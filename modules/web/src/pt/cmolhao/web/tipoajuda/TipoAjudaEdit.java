package pt.cmolhao.web.tipoajuda;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoAjuda;

@UiController("cmolhao_TipoAjuda.edit")
@UiDescriptor("tipo-ajuda-edit.xml")
@EditedEntityContainer("tipoAjudaDc")
@LoadDataBeforeShow
public class TipoAjudaEdit extends StandardEditor<TipoAjuda> {
}