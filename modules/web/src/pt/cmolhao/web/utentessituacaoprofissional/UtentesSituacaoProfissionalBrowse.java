package pt.cmolhao.web.utentessituacaoprofissional;

import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Utente;
import pt.cmolhao.entity.UtentesSituacaoProfissional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_UtentesSituacaoProfissional.browse")
@UiDescriptor("utentes-situacao-profissional-browse.xml")
@LookupComponent("utentesSituacaoProfissionalsTable")
@LoadDataBeforeShow
public class UtentesSituacaoProfissionalBrowse extends StandardLookup<UtentesSituacaoProfissional> {
    @Inject
    protected CollectionLoader<UtentesSituacaoProfissional> utentesSituacaoProfissionalsDl;
    @Inject
    protected LookupField<Utente> utenteField;
    @Inject
    protected LookupField linhasUtentesSituacaoProfissao;
    @Inject
    protected LookupField situacaoProfissionalField;
    @Inject
    protected GroupTable<UtentesSituacaoProfissional> utentesSituacaoProfissionalsTable;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    protected void onInit(InitEvent event) {

        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(50);
        list.add(100);
        list.add(200);
        list.add(500);
        list.add(1000);
        list.add(2000);
        list.add(5000);
        list.add(10000);
        linhasUtentesSituacaoProfissao.setOptionsList(list);

        List<String> list_situacao_profissional = new ArrayList<>();
        list_situacao_profissional.add("Trabalhador por conta de outrém");
        list_situacao_profissional.add("Bolseiro");
        list_situacao_profissional.add("Desempregado");
        list_situacao_profissional.add("Estagiário");
        list_situacao_profissional.add("Trabalhador por conta própria (Com trabalhadores a cargo)");
        list_situacao_profissional.add("Trabalhador por conta própria (Sem trabalhadores a cargo)");
        situacaoProfissionalField.setOptionsList(list_situacao_profissional);


        utentesSituacaoProfissionalsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(utentesSituacaoProfissionalsTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setUtente(utenteField.getValue());
                            if (situacaoProfissionalField.getValue() != null)
                            {
                                customer.setSituacaoProfissional(situacaoProfissionalField.getValue().toString());
                            }
                        })
                        .withScreenClass(UtentesSituacaoProfissionalEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("reset_utentes_situacao_profissao")
    protected void onReset_utentes_situacao_profissaoClick(Button.ClickEvent event) {
        utenteField.setValue(null);
        situacaoProfissionalField.setValue(null);
        linhasUtentesSituacaoProfissao.setValue(null);
        utentesSituacaoProfissionalsDl.setMaxResults(0);
        utentesSituacaoProfissionalsDl.removeParameter("situacaoProfissional");
        utentesSituacaoProfissionalsDl.removeParameter("utente");
        utentesSituacaoProfissionalsDl.load();
    }

    @Subscribe("search_utentes_situacao_profissao")
    protected void onSearch_utentes_situacao_profissaoClick(Button.ClickEvent event) {
        if (utenteField.getValue() != null)
        {
            utentesSituacaoProfissionalsDl.setParameter("utente", utenteField.getValue().getId());
        }
        else
        {
            utentesSituacaoProfissionalsDl.removeParameter("utente");
        }

        if (situacaoProfissionalField.getValue() != null)
        {
            utentesSituacaoProfissionalsDl.setParameter("situacaoProfissional", situacaoProfissionalField.getValue().toString());
        }
        else
        {
            utentesSituacaoProfissionalsDl.removeParameter("situacaoProfissional");
        }
        utentesSituacaoProfissionalsDl.load();
    }

    @Subscribe("linhasUtentesSituacaoProfissao")
    protected void onLinhasUtentesSituacaoProfissaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            utentesSituacaoProfissionalsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            utentesSituacaoProfissionalsDl.setMaxResults(0);
        }
        utentesSituacaoProfissionalsDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Utentes de Situações Profissionais");
    }
}