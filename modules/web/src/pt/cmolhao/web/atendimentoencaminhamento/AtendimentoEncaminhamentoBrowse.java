package pt.cmolhao.web.atendimentoencaminhamento;

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
import pt.cmolhao.entity.AtendimentoEncaminhamento;
import pt.cmolhao.web.utentessituacaoprofissional.UtentesSituacaoProfissionalEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_AtendimentoEncaminhamento.browse")
@UiDescriptor("atendimento-encaminhamento-browse.xml")
@LookupComponent("atendimentoEncaminhamentoesTable")
@LoadDataBeforeShow
public class AtendimentoEncaminhamentoBrowse extends StandardLookup<AtendimentoEncaminhamento> {
    @Inject
    protected Table<AtendimentoEncaminhamento> atendimentoEncaminhamentoesTable;
    @Inject
    protected LookupField linhasAtendimentoEncaminhamento;
    @Inject
    protected LookupField aten_enca_id;
    @Inject
    protected CollectionLoader<AtendimentoEncaminhamento> atendimentoEncaminhamentoesDl;
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
        linhasAtendimentoEncaminhamento.setOptionsList(list);

        atendimentoEncaminhamentoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(atendimentoEncaminhamentoesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (aten_enca_id.getValue() != null)
                            {
                                customer.setAtendimentoEncaminhamento(aten_enca_id.getValue().toString());
                            }
                        })
                        .withScreenClass(AtendimentoEncaminhamentoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe("linhasAtendimentoEncaminhamento")
    protected void onLinhasAtendimentoEncaminhamentoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            atendimentoEncaminhamentoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            atendimentoEncaminhamentoesDl.setMaxResults(0);
        }
        atendimentoEncaminhamentoesDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Atendimento Encaminhamento");
        // Arrend - Renda
        List<String> options = new ArrayList<>();
        String queryString = "select o.atendimentoEncaminhamento as atendimentoEncaminhamento from cmolhao_AtendimentoEncaminhamento o where o.atendimentoEncaminhamento is not null group by o.atendimentoEncaminhamento";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("atendimentoEncaminhamento");
        List<KeyValueEntity> resultList = dataManager.loadValues(valueLoadContextontext);
        for (KeyValueEntity entry : resultList) {
            options.add(entry.getValue("atendimentoEncaminhamento"));
        }
        aten_enca_id.setOptionsList(options);
    }

    @Subscribe("reset_atendimento_encaminhamento")
    protected void onReset_atendimento_encaminhamentoClick(Button.ClickEvent event) {
        aten_enca_id.setValue(null);
        linhasAtendimentoEncaminhamento.setValue(null);
        atendimentoEncaminhamentoesDl.setMaxResults(0);
        atendimentoEncaminhamentoesDl.removeParameter("atendimentoEncaminhamento");
        atendimentoEncaminhamentoesDl.load();
    }

    @Subscribe("search_atendimento_encaminhamento")
    protected void onSearch_atendimento_encaminhamentoClick(Button.ClickEvent event) {
        if (aten_enca_id.getValue() != null)
        {
            atendimentoEncaminhamentoesDl.setParameter("atendimentoEncaminhamento", aten_enca_id.getValue().toString());
        }
        else
        {
            atendimentoEncaminhamentoesDl.removeParameter("atendimentoEncaminhamento");
        }

        atendimentoEncaminhamentoesDl.load();
    }
}