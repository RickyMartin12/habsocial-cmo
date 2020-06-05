package pt.cmolhao.web.tecnico;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.Tecnico;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Tecnico.browse")
@UiDescriptor("tecnico-browse.xml")
@LookupComponent("tecnicoesTable")
@LoadDataBeforeShow
public class TecnicoBrowse extends StandardLookup<Tecnico> {
    @Inject
    protected CollectionLoader<Tecnico> tecnicoesDl;
    @Inject
    protected GroupTable<Tecnico> tecnicoesTable;
    @Inject
    protected LookupField<Instituicoes> idInstituicaoField;
    @Inject
    protected LookupField email_tec_id;
    @Inject
    protected TextField<String> nomeField;
    @Inject
    protected LookupField linhasTecnico;
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
        linhasTecnico.setOptionsList(list);

        tecnicoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tecnicoesTable)
                        .newEntity()
                        .withInitializer(customer -> {

                            customer.setIdInstituicao(idInstituicaoField.getValue());
                            if (email_tec_id.getValue() != null)
                            {
                                customer.setEmail(email_tec_id.getValue().toString());
                            }
                            if(nomeField.getValue() != null)
                            {
                                customer.setNome(nomeField.getValue());
                            }

                        })
                        .withScreenClass(TecnicoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        // Arrend - Renda
        List<String> options = new ArrayList<>();
        String queryString = "select o.email as email from cmolhao_Tecnico o where o.email is not null group by o.email";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("email");
        List<KeyValueEntity> resultList = dataManager.loadValues(valueLoadContextontext);
        for (KeyValueEntity entry : resultList) {
            options.add(entry.getValue("email"));
        }
        email_tec_id.setOptionsList(options);
    }

    @Subscribe("reset_tecnico")
    protected void onReset_tecnicoClick(Button.ClickEvent event) {
        linhasTecnico.setValue(null);
        nomeField.setValue(null);
        email_tec_id.setValue(null);
        idInstituicaoField.setValue(null);
        tecnicoesDl.setMaxResults(0);
        tecnicoesDl.removeParameter("nome");
        tecnicoesDl.removeParameter("email");
        tecnicoesDl.removeParameter("idInstituicao");
        tecnicoesDl.load();
    }

    @Subscribe("search_tecnico")
    protected void onSearch_tecnicoClick(Button.ClickEvent event) {


        if (idInstituicaoField.getValue() != null) {
            tecnicoesDl.setParameter("idInstituicao", idInstituicaoField.getValue().getId());
        } else {
            tecnicoesDl.removeParameter("idInstituicao");
        }

        if (email_tec_id.getValue() != null) {
            tecnicoesDl.setParameter("email", email_tec_id.getValue().toString());
        } else {
            tecnicoesDl.removeParameter("email");
        }

        if (nomeField.getValue() != null) {
            tecnicoesDl.setParameter("nome", "(?i)%" + nomeField.getValue() + "%");
        } else {
            tecnicoesDl.removeParameter("nome");
        }

        tecnicoesDl.load();
    }

    @Subscribe("linhasTecnico")
    protected void onLinhasTecnicoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tecnicoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tecnicoesDl.setMaxResults(0);
        }
        tecnicoesDl.load();
    }
}