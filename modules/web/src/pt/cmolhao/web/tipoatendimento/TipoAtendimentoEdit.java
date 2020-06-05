package pt.cmolhao.web.tipoatendimento;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoAtendimento;

@UiController("cmolhao_TipoAtendimento.edit")
@UiDescriptor("tipo-atendimento-edit.xml")
@EditedEntityContainer("tipoAtendimentoDc")
@LoadDataBeforeShow
public class TipoAtendimentoEdit extends StandardEditor<TipoAtendimento> {
}