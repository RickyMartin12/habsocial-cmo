package pt.cmolhao.web.atendimentoencaminhamento;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.AtendimentoEncaminhamento;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_AtendimentoEncaminhamento.edit")
@UiDescriptor("atendimento-encaminhamento-edit.xml")
@EditedEntityContainer("atendimentoEncaminhamentoDc")
@LoadDataBeforeShow
public class AtendimentoEncaminhamentoEdit extends StandardEditor<AtendimentoEncaminhamento> {

    @Inject
    protected TextField<UUID> idAtendimentoEncaminhamentoField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Atendimento Encaminhamento - " + idAtendimentoEncaminhamentoField.getValue());
    }

}