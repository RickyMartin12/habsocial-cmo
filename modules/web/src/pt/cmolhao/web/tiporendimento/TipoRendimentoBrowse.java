package pt.cmolhao.web.tiporendimento;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoRendimento;

@UiController("cmolhao_TipoRendimento.browse")
@UiDescriptor("tipo-rendimento-browse.xml")
@LookupComponent("tipoRendimentoesTable")
@LoadDataBeforeShow
public class TipoRendimentoBrowse extends StandardLookup<TipoRendimento> {
}