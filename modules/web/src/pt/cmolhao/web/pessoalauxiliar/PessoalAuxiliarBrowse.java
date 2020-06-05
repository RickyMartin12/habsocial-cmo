package pt.cmolhao.web.pessoalauxiliar;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.PessoalAuxiliar;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.web.habitacaosocial.HabitacaoSocialEdit;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@UiController("cmolhao_PessoalAuxiliar.browse")
@UiDescriptor("pessoal-auxiliar-browse.xml")
@LookupComponent("pessoalAuxiliarsTable")
@LoadDataBeforeShow
public class PessoalAuxiliarBrowse extends StandardLookup<PessoalAuxiliar> {
    @Inject
    private Label<String> text_sexo_masculino;
    @Inject
    private Label<String> text_formacao_profissional;
    @Inject
    private CollectionLoader<PessoalAuxiliar> pessoalAuxiliarsDl;
    @Inject
    private LookupField linhasPessoalAuxiliar;
    @Inject
    private LookupField<Valencias> idvalenciaField;
    @Inject
    private CheckBox sexomasculinoField;
    @Inject
    private CheckBox formacaoprofissionalField;
    @Inject
    private DataManager dataManager;
    @Inject
    private LookupField categoria_profissional_id;
    @Inject
    private LookupField habilitacoes_literarias_id;
    @Inject
    private GroupTable<PessoalAuxiliar> pessoalAuxiliarsTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;

    @Subscribe
    protected void onInit(InitEvent event) {




        text_sexo_masculino.setValue("Sexo Masculino: ");
        text_formacao_profissional.setValue("Formação Profissional: ");

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
        linhasPessoalAuxiliar.setOptionsList(list);

        pessoalAuxiliarsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(pessoalAuxiliarsTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (categoria_profissional_id.getValue() != null)
                            {
                                customer.setCategoriaprofissional(categoria_profissional_id.getValue().toString());
                            }
                            if (habilitacoes_literarias_id.getValue() != null)
                            {
                                customer.setHabilitacoesliterarias(habilitacoes_literarias_id.getValue().toString());
                            }
                            customer.setIdValencia(idvalenciaField.getValue());
                            customer.setSexomasculino(sexomasculinoField.getValue());
                            customer.setFormacaoprofissional(formacaoprofissionalField.getValue());
                        })
                        .withScreenClass(PessoalAuxiliarEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("search_pssoal_auxiliar")
    public void onSearch_pssoal_auxiliarClick(Button.ClickEvent event) {

        // ID de valencia
        if (idvalenciaField.getValue() != null) {
            pessoalAuxiliarsDl.setParameter("idValencia",  idvalenciaField.getValue().getId());
        } else {
            pessoalAuxiliarsDl.removeParameter("idValencia");
        }

        // Sexo Masculino

        if (sexomasculinoField.getValue())
        {
            pessoalAuxiliarsDl.setParameter("sexomasculino",  true);
        } else {
            pessoalAuxiliarsDl.removeParameter("sexomasculino");
        }

        // Formação Profissional

        if (formacaoprofissionalField.getValue())
        {
            pessoalAuxiliarsDl.setParameter("formacaoprofissional",  true);
        }
        else
        {
            pessoalAuxiliarsDl.removeParameter("formacaoprofissional");
        }

        // Categoria Profissional

        if (categoria_profissional_id.getValue() != null) {
            pessoalAuxiliarsDl.setParameter("categoriaprofissional",  categoria_profissional_id.getValue().toString());
        } else {
            pessoalAuxiliarsDl.removeParameter("categoriaprofissional");
        }

        // Habilitações Litetarias

        if (habilitacoes_literarias_id.getValue() != null) {
            pessoalAuxiliarsDl.setParameter("habilitacoesliterarias",  habilitacoes_literarias_id.getValue().toString());
        } else {
            pessoalAuxiliarsDl.removeParameter("habilitacoesliterarias");
        }


        pessoalAuxiliarsDl.load();


    }

    @Subscribe("linhasPessoalAuxiliar")
    public void onLinhasPessoalAuxiliarValueChange(HasValue.ValueChangeEvent event) {

        if (event.getValue() != null)
        {
            pessoalAuxiliarsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            pessoalAuxiliarsDl.setMaxResults(0);
        }
        pessoalAuxiliarsDl.load();

    }

    @Subscribe("reset_search_pssoal_auxiliar")
    public void onReset_search_pssoal_auxiliarClick(Button.ClickEvent event) {
        idvalenciaField.setValue(null);
        sexomasculinoField.setValue(false);
        formacaoprofissionalField.setValue(false);
        linhasPessoalAuxiliar.setValue(null);
        categoria_profissional_id.setValue(null);
        habilitacoes_literarias_id.setValue(null);
        pessoalAuxiliarsDl.removeParameter("sexomasculino");
        pessoalAuxiliarsDl.removeParameter("formacaoprofissional");
        pessoalAuxiliarsDl.removeParameter("idValencia");
        pessoalAuxiliarsDl.removeParameter("categoriaprofissional");
        pessoalAuxiliarsDl.removeParameter("habilitacoesliterarias");
        pessoalAuxiliarsDl.setMaxResults(0);
        pessoalAuxiliarsDl.load();
    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        // Categoria Profissional
        List<String> options = new ArrayList<>();
        String queryString = "select o.categoriaprofissional as categoriaprofissional from cmolhao_PessoalAuxiliar o where o.categoriaprofissional is not null group by o.categoriaprofissional";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("categoriaprofissional");
        List<KeyValueEntity> resultList = dataManager.loadValues(valueLoadContextontext);
        for (KeyValueEntity entry : resultList) {
            options.add(entry.getValue("categoriaprofissional"));
        }
        categoria_profissional_id.setOptionsList(options);

        // Habilitações Literarias

        List<String> list = new ArrayList<>();
        list.add("Sem Habilidade Literária");
        list.add("1.º Ciclo Ens. Primário do 1.ª ano ao 4.ª Ano");
        list.add("1.º Ciclo Ens. Básico do 5.ª ano ao 9.ª Ano");
        list.add("Ensino Secundário");
        list.add("Barcerlato");
        list.add("Ensino Universitário");
        list.add("Mestrado");
        habilitacoes_literarias_id.setOptionsList(list);

        /*List<String> options_hab_literarias = new ArrayList<>();
        String queryString_hab_literarias = "select o.habilitacoesliterarias as habilitacoesliterarias from cmolhao_PessoalAuxiliar o where o.habilitacoesliterarias is not null group by o.habilitacoesliterarias";
        ValueLoadContext valueLoadContextontext__hab_literarias = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString_hab_literarias));
        valueLoadContextontext__hab_literarias.addProperty("habilitacoesliterarias");
        List<KeyValueEntity> resultList__hab_literaria = dataManager.loadValues(valueLoadContextontext__hab_literarias);
        for (KeyValueEntity entry : resultList__hab_literaria) {
            options_hab_literarias.add(entry.getValue("habilitacoesliterarias"));
        }
        habilitacoes_literarias_id.setOptionsList(options_hab_literarias);*/


        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            map.put(item.getDescricaotecnica() + " " , item);
        }
        idvalenciaField.setOptionsMap(map);


    }
}