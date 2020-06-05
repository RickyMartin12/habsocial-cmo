package pt.cmolhao.web.tiporelacionamentoutentes;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoRelacionamentoUtentes;

@UiController("cmolhao_TipoRelacionamentoUtentes.edit")
@UiDescriptor("tipo-relacionamento-utentes-edit.xml")
@EditedEntityContainer("tipoRelacionamentoUtentesDc")
@LoadDataBeforeShow
public class TipoRelacionamentoUtentesEdit extends StandardEditor<TipoRelacionamentoUtentes> {
}