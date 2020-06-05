package pt.cmolhao.web.atendimentoencaminhamento;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.AtendimentoEncaminhamento;

@UiController("cmolhao_AtendimentoEncaminhamento.browse")
@UiDescriptor("atendimento-encaminhamento-browse.xml")
@LookupComponent("atendimentoEncaminhamentoesTable")
@LoadDataBeforeShow
public class AtendimentoEncaminhamentoBrowse extends StandardLookup<AtendimentoEncaminhamento> {
}