package pt.cmolhao.web.tiporelacionamentoutentes;

import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoRelacionamentoUtentes;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@UiController("cmolhao_TipoRelacionamentoUtentes.edit")
@UiDescriptor("tipo-relacionamento-utentes-edit.xml")
@EditedEntityContainer("tipoRelacionamentoUtentesDc")
@LoadDataBeforeShow
public class TipoRelacionamentoUtentesEdit extends StandardEditor<TipoRelacionamentoUtentes> {
    @Inject
    protected LookupField<String> tipoRelacionamentoField;
    @Inject
    protected LookupField<String> tipoRelacionamentoInvField;
    @Inject
    protected TextField<UUID> idTipoRelacionamentoUtentes;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list_tipo_relacionamento = new ArrayList<>();
        list_tipo_relacionamento.add("Pai");
        list_tipo_relacionamento.add("Mãe");
        list_tipo_relacionamento.add("Filho(a)");
        list_tipo_relacionamento.add("Tio(a)");
        list_tipo_relacionamento.add("Sobrinho(a)");
        list_tipo_relacionamento.add("Avó");
        list_tipo_relacionamento.add("Avô");
        list_tipo_relacionamento.add("Neto(a)");
        list_tipo_relacionamento.add("Sogro(a)");
        list_tipo_relacionamento.add("Genro");
        list_tipo_relacionamento.add("Nora");
        list_tipo_relacionamento.add("Bisavó");
        list_tipo_relacionamento.add("Bisavô");
        list_tipo_relacionamento.add("Bisneto(a)");
        list_tipo_relacionamento.add("Cunhado(a)");
        list_tipo_relacionamento.add("Amigo(a)");
        list_tipo_relacionamento.add("Colega de trabalho e/ou Chefe");
        list_tipo_relacionamento.add("Namorado(a)");
        tipoRelacionamentoField.setOptionsList(list_tipo_relacionamento);
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Tipo de Relacionamento de Utentes - " + idTipoRelacionamentoUtentes.getValue());

        List<String> list_topologia_rel_inv = new ArrayList<>();
        if (tipoRelacionamentoField.getValue() != null)
        {
            tipoRelacionamentoInvField.setValue(null);
            if (Objects.equals(tipoRelacionamentoField.getValue(), "Mãe") || Objects.equals(tipoRelacionamentoField.getValue(), "Pai"))
            {
                list_topologia_rel_inv.add("Filho(a)");
            }
            if (tipoRelacionamentoField.getValue().equals("Filho(a)"))
            {
                list_topologia_rel_inv.add("Mãe");
                list_topologia_rel_inv.add("Pai");
            }
            if (tipoRelacionamentoField.getValue().equals("Tio(a)"))
            {
                list_topologia_rel_inv.add("Sobrinho(a)");
            }
            if (tipoRelacionamentoField.getValue().equals("Sobrinho(a)"))
            {
                list_topologia_rel_inv.add("Tio(a)");
            }
            if (tipoRelacionamentoField.getValue().equals("Avó") || tipoRelacionamentoField.getValue().equals("Avô"))
            {
                list_topologia_rel_inv.add("Neto(a)");
            }
            if (tipoRelacionamentoField.getValue().equals("Neto(a)"))
            {
                list_topologia_rel_inv.add("Avó");
                list_topologia_rel_inv.add("Avô");
            }
            if (tipoRelacionamentoField.getValue().equals("Sogro(a)"))
            {
                list_topologia_rel_inv.add("Genro");
                list_topologia_rel_inv.add("Nora");
            }
            if (tipoRelacionamentoField.getValue().equals("Cunhado(a)"))
            {
                list_topologia_rel_inv.add("Cunhado(a)");
            }
            if (tipoRelacionamentoField.getValue().equals("Genro") || tipoRelacionamentoField.getValue().equals("Nora"))
            {
                list_topologia_rel_inv.add("Sogro(a)");
            }
            if (tipoRelacionamentoField.getValue().equals("Bisavó") || tipoRelacionamentoField.getValue().equals("Bisavô"))
            {
                list_topologia_rel_inv.add("Bisneto(a)");
            }
            if (tipoRelacionamentoField.getValue().equals("Bisneto(a)"))
            {
                list_topologia_rel_inv.add("Bisavó");
                list_topologia_rel_inv.add("Bisavô");
            }
            if (tipoRelacionamentoField.getValue().equals("Amigo(a)"))
            {
                list_topologia_rel_inv.add("Amigo(a)");
            }
            if (tipoRelacionamentoField.getValue().equals("Colega de trabalho e/ou Chefe"))
            {
                list_topologia_rel_inv.add("Colega de trabalho e/ou Chefe");
            }
            if (tipoRelacionamentoField.getValue().equals("Namorado(a)"))
            {
                list_topologia_rel_inv.add("Namorado(a)");
            }
            tipoRelacionamentoInvField.setOptionsList(list_topologia_rel_inv);
        }

    }

    @Subscribe("tipoRelacionamentoField")
    protected void onTipoRelacionamentoFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        List<String> list_topologia_rel_inv = new ArrayList<>();
        tipoRelacionamentoInvField.setValue(null);
        list_topologia_rel_inv.clear();
        if (event.isUserOriginated())
        {
            if (event.getValue().equals("Mãe") || event.getValue().equals("Pai"))
            {
                list_topologia_rel_inv.add("Filho(a)");
            }
            if (event.getValue().equals("Filho(a)"))
            {
                list_topologia_rel_inv.add("Mãe");
                list_topologia_rel_inv.add("Pai");
            }
            if (event.getValue().equals("Tio(a)"))
            {
                list_topologia_rel_inv.add("Sobrinho(a)");
            }
            if (event.getValue().equals("Sobrinho(a)"))
            {
                list_topologia_rel_inv.add("Tio(a)");
            }
            if (event.getValue().equals("Avó") || tipoRelacionamentoField.getValue().equals("Avô"))
            {
                list_topologia_rel_inv.add("Neto(a)");
            }
            if (event.getValue().equals("Neto(a)"))
            {
                list_topologia_rel_inv.add("Avó");
                list_topologia_rel_inv.add("Avô");
            }
            if (event.getValue().equals("Sogro(a)"))
            {
                list_topologia_rel_inv.add("Genro");
                list_topologia_rel_inv.add("Nora");
            }
            if (event.getValue().equals("Cunhado(a)"))
            {
                list_topologia_rel_inv.add("Cunhado(a)");
            }
            if (event.getValue().equals("Genro") || tipoRelacionamentoField.getValue().equals("Nora"))
            {
                list_topologia_rel_inv.add("Sogro(a)");
            }
            if (event.getValue().equals("Bisavó") || tipoRelacionamentoField.getValue().equals("Bisavô"))
            {
                list_topologia_rel_inv.add("Bisneto(a)");
            }
            if (event.getValue().equals("Bisneto(a)"))
            {
                list_topologia_rel_inv.add("Bisavó");
                list_topologia_rel_inv.add("Bisavô");
            }
            if (event.getValue().equals("Amigo(a)"))
            {
                list_topologia_rel_inv.add("Amigo(a)");
            }
            if (event.getValue().equals("Colega de trabalho e/ou Chefe"))
            {
                list_topologia_rel_inv.add("Colega de trabalho e/ou Chefe");
            }
            if (event.getValue().equals("Namorado(a)"))
            {
                list_topologia_rel_inv.add("Namorado(a)");
            }
            tipoRelacionamentoInvField.setOptionsList(list_topologia_rel_inv);
        }
    }
}