package pt.cmolhao.web.grauescolaridade;

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
import pt.cmolhao.entity.GrauEscolaridade;
import pt.cmolhao.web.tipoajuda.TipoAjudaEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_GrauEscolaridade.browse")
@UiDescriptor("grau-escolaridade-browse.xml")
@LookupComponent("grauEscolaridadesTable")
@LoadDataBeforeShow
public class GrauEscolaridadeBrowse extends StandardLookup<GrauEscolaridade> {
    @Inject
    protected Table<GrauEscolaridade> grauEscolaridadesTable;
    @Inject
    protected LookupField linhasGrauEscolraidade;
    @Inject
    protected LookupField desc_grau_escolaridade_id;
    @Inject
    protected CollectionLoader<GrauEscolaridade> grauEscolaridadesDl;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        // Arrend - Renda
        List<String> options = new ArrayList<>();
        String queryString = "select o.descricao as descricao from cmolhao_GrauEscolaridade o where o.descricao is not null group by o.descricao";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("descricao");
        List<KeyValueEntity> resultList = dataManager.loadValues(valueLoadContextontext);
        for (KeyValueEntity entry : resultList) {
            options.add(entry.getValue("descricao"));
        }
        desc_grau_escolaridade_id.setOptionsList(options);
    }

    @Subscribe("reset_grau_escolaridade")
    protected void onReset_grau_escolaridadeClick(Button.ClickEvent event) {
        desc_grau_escolaridade_id.setValue(null);
        linhasGrauEscolraidade.setValue(null);
        grauEscolaridadesDl.removeParameter("descricao");
        grauEscolaridadesDl.setMaxResults(0);
        grauEscolaridadesDl.load();
    }

    @Subscribe("search_grau_escolaridade")
    protected void onSearch_grau_escolaridadeClick(Button.ClickEvent event) {
        // Descrição de Tipo Ajuda

        if (desc_grau_escolaridade_id.getValue() != null) {
            grauEscolaridadesDl.setParameter("descricao",  desc_grau_escolaridade_id.getValue().toString());
        } else {
            grauEscolaridadesDl.removeParameter("descricao");
        }

        grauEscolaridadesDl.load();
    }

    @Subscribe("linhasGrauEscolraidade")
    protected void onLinhasGrauEscolraidadeValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            grauEscolaridadesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            grauEscolaridadesDl.setMaxResults(0);
        }
        grauEscolaridadesDl.load();
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
        linhasGrauEscolraidade.setOptionsList(list);

        grauEscolaridadesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(grauEscolaridadesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (desc_grau_escolaridade_id.getValue() != null)
                            {
                                customer.setDescricao(desc_grau_escolaridade_id.getValue().toString());
                            }
                        })
                        .withScreenClass(GrauEscolaridadeEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }
}