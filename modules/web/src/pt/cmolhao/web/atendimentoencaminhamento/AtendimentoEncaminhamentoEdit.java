package pt.cmolhao.web.atendimentoencaminhamento;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.AtendimentoEncaminhamento;

@UiController("cmolhao_AtendimentoEncaminhamento.edit")
@UiDescriptor("atendimento-encaminhamento-edit.xml")
@EditedEntityContainer("atendimentoEncaminhamentoDc")
@LoadDataBeforeShow
public class AtendimentoEncaminhamentoEdit extends StandardEditor<AtendimentoEncaminhamento> {
}