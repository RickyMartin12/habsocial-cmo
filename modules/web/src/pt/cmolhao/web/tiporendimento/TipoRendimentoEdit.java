package pt.cmolhao.web.tiporendimento;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoRendimento;

@UiController("cmolhao_TipoRendimento.edit")
@UiDescriptor("tipo-rendimento-edit.xml")
@EditedEntityContainer("tipoRendimentoDc")
@LoadDataBeforeShow
public class TipoRendimentoEdit extends StandardEditor<TipoRendimento> {
}