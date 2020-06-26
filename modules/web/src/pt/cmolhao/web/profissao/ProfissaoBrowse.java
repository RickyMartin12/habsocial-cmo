package pt.cmolhao.web.profissao;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Profissao;
import pt.cmolhao.web.habilitacoesliterarias.HabilitacoesLiterariasEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Profissao.browse")
@UiDescriptor("profissao-browse.xml")
@LookupComponent("profissaosTable")
@LoadDataBeforeShow
public class ProfissaoBrowse extends StandardLookup<Profissao> {
    @Inject
    protected LookupField nome_prof_id;
    @Inject
    protected LookupField linhasProfissao;
    @Inject
    protected Table<Profissao> profissaosTable;
    @Inject
    protected CollectionLoader<Profissao> profissaosDl;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    public void onInit(InitEvent event) {
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
        linhasProfissao.setOptionsList(list);

        profissaosTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(profissaosTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (nome_prof_id.getValue() != null)
                            {
                                customer.setNome(nome_prof_id.getValue().toString());
                            }
                        })
                        .withScreenClass(ProfissaoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Profissões");
        // Arrend - Renda
        List<String> options = new ArrayList<>();
        String queryString = "select o.nome as nome from cmolhao_Profissao o where o.nome is not null group by o.nome";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("nome");
        List<KeyValueEntity> resultList = dataManager.loadValues(valueLoadContextontext);
        for (KeyValueEntity entry : resultList) {
            options.add(entry.getValue("nome"));
        }
        nome_prof_id.setOptionsList(options);
    }

    @Subscribe("linhasProfissao")
    protected void onLinhasProfissaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            profissaosDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            profissaosDl.setMaxResults(0);
        }
        profissaosDl.load();
    }

    @Subscribe("reset_profissao")
    protected void onReset_profissaoClick(Button.ClickEvent event) {
        nome_prof_id.setValue(null);
        linhasProfissao.setValue(null);
        profissaosDl.removeParameter("nome");
        profissaosDl.setMaxResults(0);
        profissaosDl.load();
    }

    @Subscribe("search_profissao")
    protected void onSearch_profissaoClick(Button.ClickEvent event) {
        if (nome_prof_id.getValue() != null) {
            profissaosDl.setParameter("nome",  nome_prof_id.getValue().toString());
        } else {
            profissaosDl.removeParameter("nome");
        }

        profissaosDl.load();
    }
}