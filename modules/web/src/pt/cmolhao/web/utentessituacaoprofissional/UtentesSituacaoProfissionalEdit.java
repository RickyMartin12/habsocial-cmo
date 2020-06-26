package pt.cmolhao.web.utentessituacaoprofissional;

import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.UtentesSituacaoProfissional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UiController("cmolhao_UtentesSituacaoProfissional.edit")
@UiDescriptor("utentes-situacao-profissional-edit.xml")
@EditedEntityContainer("utentesSituacaoProfissionalDc")
@LoadDataBeforeShow
public class UtentesSituacaoProfissionalEdit extends StandardEditor<UtentesSituacaoProfissional> {
    @Inject
    protected LookupField<String> situacaoProfissionalField;
    @Inject
    protected TextField<UUID> idUtentesSituacaoProfissionalField;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list_situacao_profissional = new ArrayList<>();
        list_situacao_profissional.add("Trabalhador por conta de outrém");
        list_situacao_profissional.add("Bolseiro");
        list_situacao_profissional.add("Desempregado");
        list_situacao_profissional.add("Estagiário");
        list_situacao_profissional.add("Trabalhador por conta própria (Com trabalhadores a cargo)");
        list_situacao_profissional.add("Trabalhador por conta própria (Sem trabalhadores a cargo)");
        situacaoProfissionalField.setOptionsList(list_situacao_profissional);
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Utente da Situação Profissional - " + idUtentesSituacaoProfissionalField.getValue());
    }
}