package pt.cmolhao.web.habilitacoesliterarias;

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
import pt.cmolhao.entity.HabilitacoesLiterarias;
import pt.cmolhao.web.grauescolaridade.GrauEscolaridadeEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_HabilitacoesLiterarias.browse")
@UiDescriptor("habilitacoes-literarias-browse.xml")
@LookupComponent("habilitacoesLiterariasesTable")
@LoadDataBeforeShow
public class HabilitacoesLiterariasBrowse extends StandardLookup<HabilitacoesLiterarias> {

    @Inject
    protected LookupField desc_habilitacoes_literarias_id;
    @Inject
    protected LookupField linhasHabilitacoesLiterarias;
    @Inject
    protected CollectionLoader<HabilitacoesLiterarias> habilitacoesLiterariasesDl;
    @Inject
    protected Table<HabilitacoesLiterarias> habilitacoesLiterariasesTable;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        // Arrend - Renda
        List<String> options = new ArrayList<>();
        String queryString = "select o.descricao as descricao from cmolhao_HabilitacoesLiterarias o where o.descricao is not null group by o.descricao";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("descricao");
        List<KeyValueEntity> resultList = dataManager.loadValues(valueLoadContextontext);
        for (KeyValueEntity entry : resultList) {
            options.add(entry.getValue("descricao"));
        }
        desc_habilitacoes_literarias_id.setOptionsList(options);
    }

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
        linhasHabilitacoesLiterarias.setOptionsList(list);

        habilitacoesLiterariasesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(habilitacoesLiterariasesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (desc_habilitacoes_literarias_id.getValue() != null)
                            {
                                customer.setDescricao(desc_habilitacoes_literarias_id.getValue().toString());
                            }
                        })
                        .withScreenClass(HabilitacoesLiterariasEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe("reset_grau_escolaridade")
    protected void onReset_grau_escolaridadeClick(Button.ClickEvent event) {
        desc_habilitacoes_literarias_id.setValue(null);
        linhasHabilitacoesLiterarias.setValue(null);
        habilitacoesLiterariasesDl.removeParameter("descricao");
        habilitacoesLiterariasesDl.setMaxResults(0);
        habilitacoesLiterariasesDl.load();
    }

    @Subscribe("search_grau_escolaridade")
    protected void onSearch_grau_escolaridadeClick(Button.ClickEvent event) {
        if (desc_habilitacoes_literarias_id.getValue() != null) {
            habilitacoesLiterariasesDl.setParameter("descricao",  desc_habilitacoes_literarias_id.getValue().toString());
        } else {
            habilitacoesLiterariasesDl.removeParameter("descricao");
        }

        habilitacoesLiterariasesDl.load();
    }

    @Subscribe("linhasHabilitacoesLiterarias")
    protected void onLinhasHabilitacoesLiterariasValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            habilitacoesLiterariasesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            habilitacoesLiterariasesDl.setMaxResults(0);
        }
        habilitacoesLiterariasesDl.load();
    }


}