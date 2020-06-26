package pt.cmolhao.web.tipoatendimento;

import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoAtendimento;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UiController("cmolhao_TipoAtendimento.edit")
@UiDescriptor("tipo-atendimento-edit.xml")
@EditedEntityContainer("tipoAtendimentoDc")
@LoadDataBeforeShow
public class TipoAtendimentoEdit extends StandardEditor<TipoAtendimento> {
    @Inject
    protected LookupField<String> tipoAtendimentoField;
    @Inject
    protected TextField<UUID> idTipoAtendimentoField;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list_tipo_atendimento = new ArrayList<>();
        list_tipo_atendimento.add("Pedidos de Habitação por email");
        list_tipo_atendimento.add("Pedidos de Habitação por sinalização por outra instituição");
        list_tipo_atendimento.add("Plataforma eAA");
        list_tipo_atendimento.add("Atendimento técnico presencial");
        tipoAtendimentoField.setOptionsList(list_tipo_atendimento);
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Atendimento - " + idTipoAtendimentoField.getValue());
    }


}