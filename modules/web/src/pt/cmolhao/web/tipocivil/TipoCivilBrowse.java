package pt.cmolhao.web.tipocivil;

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
import pt.cmolhao.entity.TipoCivil;
import pt.cmolhao.web.profissao.ProfissaoEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoCivil.browse")
@UiDescriptor("tipo-civil-browse.xml")
@LookupComponent("tipoCivilsTable")
@LoadDataBeforeShow
public class TipoCivilBrowse extends StandardLookup<TipoCivil> {
    @Inject
    protected CollectionLoader<TipoCivil> tipoCivilsDl;
    @Inject
    protected Table<TipoCivil> tipoCivilsTable;
    @Inject
    protected LookupField linhasTipoCivil;
    @Inject
    protected LookupField idTipoCivilField;
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
        linhasTipoCivil.setOptionsList(list);

        tipoCivilsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipoCivilsTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (idTipoCivilField.getValue() != null)
                            {
                                customer.setTipo(idTipoCivilField.getValue().toString());
                            }
                        })
                        .withScreenClass(TipoCivilEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Tipos de Civil");
        // Arrend - Renda
        List<String> options = new ArrayList<>();
        String queryString = "select o.tipo as tipo from cmolhao_TipoCivil o where o.tipo is not null group by o.tipo";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("tipo");
        List<KeyValueEntity> resultList = dataManager.loadValues(valueLoadContextontext);
        for (KeyValueEntity entry : resultList) {
            options.add(entry.getValue("tipo"));
        }
        idTipoCivilField.setOptionsList(options);
    }

    @Subscribe("reset_tipo_civil")
    protected void onReset_tipo_civilClick(Button.ClickEvent event) {
        idTipoCivilField.setValue(null);
        linhasTipoCivil.setValue(null);
        tipoCivilsDl.removeParameter("tipo");
        tipoCivilsDl.setMaxResults(0);
        tipoCivilsDl.load();
    }

    @Subscribe("search_tipo_civil")
    protected void onSearch_tipo_civilClick(Button.ClickEvent event) {
        if (idTipoCivilField.getValue() != null) {
            tipoCivilsDl.setParameter("tipo",  idTipoCivilField.getValue().toString());
        } else {
            tipoCivilsDl.removeParameter("tipo");
        }

        tipoCivilsDl.load();
    }

    @Subscribe("linhasTipoCivil")
    protected void onLinhasTipoCivilValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipoCivilsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipoCivilsDl.setMaxResults(0);
        }
        tipoCivilsDl.load();
    }
}