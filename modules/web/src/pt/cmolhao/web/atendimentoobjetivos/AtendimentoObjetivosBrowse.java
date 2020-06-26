package pt.cmolhao.web.atendimentoobjetivos;

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
import pt.cmolhao.entity.AtendimentoObjetivos;
import pt.cmolhao.web.atendimentoencaminhamento.AtendimentoEncaminhamentoEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_AtendimentoObjetivos.browse")
@UiDescriptor("atendimento-objetivos-browse.xml")
@LookupComponent("atendimentoObjetivosesTable")
@LoadDataBeforeShow
public class AtendimentoObjetivosBrowse extends StandardLookup<AtendimentoObjetivos> {
    @Inject
    protected Table<AtendimentoObjetivos> atendimentoObjetivosesTable;
    @Inject
    protected LookupField linhasAtendimentoObjectivos;
    @Inject
    protected LookupField aten_enca_id;
    @Inject
    protected CollectionLoader<AtendimentoObjetivos> atendimentoObjetivosesDl;
    @Inject
    private DataManager dataManager;
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
        linhasAtendimentoObjectivos.setOptionsList(list);

        atendimentoObjetivosesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                        screenBuilders.editor(atendimentoObjetivosesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (aten_enca_id.getValue() != null)
                            {
                                customer.setAtendimentoObjetivoGeral(aten_enca_id.getValue().toString());
                            }
                        })
                        .withScreenClass(AtendimentoObjetivosEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Atendimento Objetivos");
        // Arrend - Renda
        List<String> options = new ArrayList<>();
        String queryString = "select o.atendimentoObjetivoGeral as atendimentoObjetivoGeral from cmolhao_AtendimentoObjetivos o where o.atendimentoObjetivoGeral is not null group by o.atendimentoObjetivoGeral";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("atendimentoObjetivoGeral");
        List<KeyValueEntity> resultList = dataManager.loadValues(valueLoadContextontext);
        for (KeyValueEntity entry : resultList) {
            options.add(entry.getValue("atendimentoObjetivoGeral"));
        }
        aten_enca_id.setOptionsList(options);
    }

    @Subscribe("search_atendimento_objectivos")
    protected void onSearch_atendimento_objectivosClick(Button.ClickEvent event) {
        if (aten_enca_id.getValue() != null)
        {
            atendimentoObjetivosesDl.setParameter("atendimentoObjetivoGeral", aten_enca_id.getValue().toString());
        }
        else
        {
            atendimentoObjetivosesDl.removeParameter("atendimentoObjetivoGeral");
        }

        atendimentoObjetivosesDl.load();
    }

    @Subscribe("reset_atendimento_objectivos")
    protected void onReset_atendimento_objectivosClick(Button.ClickEvent event) {
        aten_enca_id.setValue(null);
        linhasAtendimentoObjectivos.setValue(null);
        atendimentoObjetivosesDl.removeParameter("atendimentoObjetivoGeral");
        atendimentoObjetivosesDl.setMaxResults(0);
        atendimentoObjetivosesDl.load();
    }

    @Subscribe("linhasAtendimentoObjectivos")
    protected void onLinhasAtendimentoObjectivosValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            atendimentoObjetivosesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            atendimentoObjetivosesDl.setMaxResults(0);
        }
        atendimentoObjetivosesDl.load();
    }


}