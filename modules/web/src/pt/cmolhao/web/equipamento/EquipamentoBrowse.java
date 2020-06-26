package pt.cmolhao.web.equipamento;

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
import pt.cmolhao.entity.Equipamento;
import pt.cmolhao.web.apoios.ApoiosEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Equipamento.browse")
@UiDescriptor("equipamento-browse.xml")
@LookupComponent("equipamentoesTable")
@LoadDataBeforeShow
public class EquipamentoBrowse extends StandardLookup<Equipamento> {
    @Inject
    protected LookupField desc_equipamento_id;
    @Inject
    protected LookupField linhasEquipamento;
    @Inject
    protected CollectionLoader<Equipamento> equipamentoesDl;
    @Inject
    protected Table<Equipamento> equipamentoesTable;
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
        linhasEquipamento.setOptionsList(list);

        equipamentoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(equipamentoesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (desc_equipamento_id.getValue() != null)
                            {
                                customer.setDescricao(desc_equipamento_id.getValue().toString());
                            }
                        })
                        .withScreenClass(EquipamentoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Equipamento");
        // Arrend - Renda
        List<String> options = new ArrayList<>();
        String queryString = "select o.descricao as descricao from cmolhao_Equipamento o where o.descricao is not null group by o.descricao";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("descricao");
        List<KeyValueEntity> resultList = dataManager.loadValues(valueLoadContextontext);
        for (KeyValueEntity entry : resultList) {
            options.add(entry.getValue("descricao"));
        }
        desc_equipamento_id.setOptionsList(options);
    }

    @Subscribe("search_equipamento")
    protected void onSearch_equipamentoClick(Button.ClickEvent event) {
        // Descrição do Equipamento
        if (desc_equipamento_id.getValue() != null) {
            equipamentoesDl.setParameter("descricao",  desc_equipamento_id.getValue().toString());
        } else {
            equipamentoesDl.removeParameter("descricao");
        }

        equipamentoesDl.load();
    }

    @Subscribe("reset_equipamento")
    protected void onReset_equipamentoClick(Button.ClickEvent event) {
        desc_equipamento_id.setValue(null);
        linhasEquipamento.setValue(null);
        equipamentoesDl.removeParameter("descricao");
        equipamentoesDl.setMaxResults(0);
        equipamentoesDl.load();
    }

    @Subscribe("linhasEquipamento")
    protected void onLinhasEquipamentoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            equipamentoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            equipamentoesDl.setMaxResults(0);
        }
        equipamentoesDl.load();
    }
}