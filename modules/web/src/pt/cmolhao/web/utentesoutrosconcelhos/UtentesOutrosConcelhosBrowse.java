package pt.cmolhao.web.utentesoutrosconcelhos;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.FotosValencia;
import pt.cmolhao.entity.UtentesOutrosConcelhos;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.web.habitacaosocial.HabitacaoSocialEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_UtentesOutrosConcelhos.browse")
@UiDescriptor("utentes-outros-concelhos-browse.xml")
@LookupComponent("utentesOutrosConcelhosesTable")
@LoadDataBeforeShow
public class UtentesOutrosConcelhosBrowse extends StandardLookup<UtentesOutrosConcelhos> {

    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected CollectionLoader<UtentesOutrosConcelhos>  utentesOutrosConcelhosesDl;
    @Inject
    protected LookupField<Valencias> idValenciaField;
    @Inject
    protected LookupField linhasUtentesOutrosConcelhos;
    @Inject
    protected LookupField utentes_conc_freguesia_id;
    @Inject
    protected LookupField utentes_conc_concelho_id;
    @Inject
    protected GroupTable<UtentesOutrosConcelhos> utentesOutrosConcelhosesTable;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    public Component generateValenciasDescricao(UtentesOutrosConcelhos entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdValencia() != null)
        {
            label.setValue(entity.getIdValencia().getDescricaotecnica());
            return label;
        }
        return null;
    }

    @Subscribe("search_utentes_outros_concelhos")
    protected void onSearch_utentes_outros_concelhosClick(Button.ClickEvent event) {
        if (idValenciaField.getValue() != null)
        {
            utentesOutrosConcelhosesDl.setParameter("idValencia",  idValenciaField.getValue().getId());
        } else {
            utentesOutrosConcelhosesDl.removeParameter("idValencia");
        }

        if (utentes_conc_freguesia_id.getValue() != null)
        {
            utentesOutrosConcelhosesDl.setParameter("freguesia",  utentes_conc_freguesia_id.getValue().toString());
        } else {
            utentesOutrosConcelhosesDl.removeParameter("freguesia");
        }

        if (utentes_conc_concelho_id.getValue() != null)
        {
            utentesOutrosConcelhosesDl.setParameter("concelho",  utentes_conc_concelho_id.getValue().toString());
        } else {
            utentesOutrosConcelhosesDl.removeParameter("concelho");
        }
        utentesOutrosConcelhosesDl.load();
    }

    @Subscribe("reset_utentes_outros_concelhos")
    protected void onReset_utentes_outros_concelhosClick(Button.ClickEvent event) {
        idValenciaField.setValue(null);
        utentes_conc_freguesia_id.setValue(null);
        utentes_conc_concelho_id.setValue(null);
        linhasUtentesOutrosConcelhos.setValue(null);
        utentesOutrosConcelhosesDl.removeParameter("idValencia");
        utentesOutrosConcelhosesDl.removeParameter("freguesia");
        utentesOutrosConcelhosesDl.removeParameter("concelho");
        utentesOutrosConcelhosesDl.setMaxResults(0);
        utentesOutrosConcelhosesDl.load();
    }

    @Subscribe("linhasUtentesOutrosConcelhos")
    protected void onLinhasUtentesOutrosConcelhosValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            utentesOutrosConcelhosesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            utentesOutrosConcelhosesDl.setMaxResults(0);
        }


        utentesOutrosConcelhosesDl.load();
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
        linhasUtentesOutrosConcelhos.setOptionsList(list);

        utentesOutrosConcelhosesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(utentesOutrosConcelhosesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdValencia(idValenciaField.getValue());
                            if(utentes_conc_freguesia_id.getValue() != null)
                            {
                                customer.setFreguesia(utentes_conc_freguesia_id.getValue().toString());
                            }
                            if(utentes_conc_concelho_id.getValue() != null)
                            {
                                customer.setConcelho(utentes_conc_concelho_id.getValue().toString());
                            }
                        })
                        .withScreenClass(UtentesOutrosConcelhosEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        // Freguesia

        List<String> optionsFreguesia = new ArrayList<>();
        String queryString_Freguesia = "select o.freguesia as freguesia from cmolhao_UtentesOutrosConcelhos o where o.freguesia is not null group by o.freguesia";
        ValueLoadContext valueLoadContextontextFreguesia = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString_Freguesia));
        valueLoadContextontextFreguesia.addProperty("freguesia");
        List<KeyValueEntity> resultListFreguesia = dataManager.loadValues(valueLoadContextontextFreguesia);

        for(KeyValueEntity entry : resultListFreguesia){
            optionsFreguesia.add(entry.getValue("freguesia"));
        }

        utentes_conc_freguesia_id.setOptionsList(optionsFreguesia);

        // Concelho

        List<String> optionsLocalidade = new ArrayList<>();
        String queryString_Localidade = "select o.concelho as concelho from cmolhao_UtentesOutrosConcelhos o where o.concelho is not null group by o.concelho";
        ValueLoadContext valueLoadContextontextLocalidade = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString_Localidade));
        valueLoadContextontextLocalidade.addProperty("concelho");
        List<KeyValueEntity> resultListLocalidade = dataManager.loadValues(valueLoadContextontextLocalidade);

        for(KeyValueEntity entry : resultListLocalidade){
            optionsLocalidade.add(entry.getValue("concelho"));
        }

        utentes_conc_concelho_id.setOptionsList(optionsLocalidade);



    }

}