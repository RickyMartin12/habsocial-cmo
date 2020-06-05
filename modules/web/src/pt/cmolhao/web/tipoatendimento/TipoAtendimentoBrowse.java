package pt.cmolhao.web.tipoatendimento;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoAtendimento;

@UiController("cmolhao_TipoAtendimento.browse")
@UiDescriptor("tipo-atendimento-browse.xml")
@LookupComponent("tipoAtendimentoesTable")
@LoadDataBeforeShow
public class TipoAtendimentoBrowse extends StandardLookup<TipoAtendimento> {
}