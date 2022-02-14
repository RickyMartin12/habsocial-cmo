package pt.cmolhao.web.tipos_pareceres;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoPareceres;

@UiController("cmolhao_TipoPareceres.edit")
@UiDescriptor("tipo-pareceres-edit.xml")
@EditedEntityContainer("tipoPareceresDc")
@LoadDataBeforeShow
public class TipoPareceresEdit extends StandardEditor<TipoPareceres> {
}