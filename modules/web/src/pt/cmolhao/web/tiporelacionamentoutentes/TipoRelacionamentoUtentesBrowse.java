package pt.cmolhao.web.tiporelacionamentoutentes;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoRelacionamentoUtentes;

@UiController("cmolhao_TipoRelacionamentoUtentes.browse")
@UiDescriptor("tipo-relacionamento-utentes-browse.xml")
@LookupComponent("tipoRelacionamentoUtentesesTable")
@LoadDataBeforeShow
public class TipoRelacionamentoUtentesBrowse extends StandardLookup<TipoRelacionamentoUtentes> {
}