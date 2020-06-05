package pt.cmolhao.web.tipoajuda;

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
import pt.cmolhao.entity.TipoAjuda;
import pt.cmolhao.web.equipamento.EquipamentoEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoAjuda.browse")
@UiDescriptor("tipo-ajuda-browse.xml")
@LookupComponent("tipoAjudasTable")
@LoadDataBeforeShow
public class TipoAjudaBrowse extends StandardLookup<TipoAjuda> {
    @Inject
    protected LookupField desc_equipamento_id;
    @Inject
    protected LookupField linhasTipoAjuda;
    @Inject
    protected Table<TipoAjuda> tipoAjudasTable;
    @Inject
    protected CollectionLoader<TipoAjuda> tipoAjudasDl;
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
        linhasTipoAjuda.setOptionsList(list);

        tipoAjudasTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipoAjudasTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (desc_equipamento_id.getValue() != null)
                            {
                                customer.setDescricaoTipoAjuda(desc_equipamento_id.getValue().toString());
                            }
                        })
                        .withScreenClass(TipoAjudaEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }



    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        // Arrend - Renda
        List<String> options = new ArrayList<>();
        String queryString = "select o.descricaoTipoAjuda as descricaoTipoAjuda from cmolhao_TipoAjuda o where o.descricaoTipoAjuda is not null group by o.descricaoTipoAjuda";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("descricaoTipoAjuda");
        List<KeyValueEntity> resultList = dataManager.loadValues(valueLoadContextontext);
        for (KeyValueEntity entry : resultList) {
            options.add(entry.getValue("descricaoTipoAjuda"));
        }
        desc_equipamento_id.setOptionsList(options);
    }

    @Subscribe("linhasTipoAjuda")
    protected void onLinhasTipoAjudaValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipoAjudasDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipoAjudasDl.setMaxResults(0);
        }
        tipoAjudasDl.load();
    }

    @Subscribe("reset_tipo_ajuda")
    protected void onReset_tipo_ajudaClick(Button.ClickEvent event) {
        desc_equipamento_id.setValue(null);
        linhasTipoAjuda.setValue(null);
        tipoAjudasDl.removeParameter("descricaoTipoAjuda");
        tipoAjudasDl.setMaxResults(0);
        tipoAjudasDl.load();
    }

    @Subscribe("search_tipo_ajuda")
    protected void onSearch_tipo_ajudaClick(Button.ClickEvent event) {
        // Descrição de Tipo Ajuda

        if (desc_equipamento_id.getValue() != null) {
            tipoAjudasDl.setParameter("descricaoTipoAjuda",  desc_equipamento_id.getValue().toString());
        } else {
            tipoAjudasDl.removeParameter("descricaoTipoAjuda");
        }

        tipoAjudasDl.load();
    }








}