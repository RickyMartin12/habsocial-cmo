package pt.cmolhao.web.pessoaltecnico;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.PessoalTecnico;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.web.pessoalauxiliar.PessoalAuxiliarEdit;

import javax.inject.Inject;
import java.util.*;

@UiController("cmolhao_PessoalTecnico.browse")
@UiDescriptor("pessoal-tecnico-browse.xml")
@LookupComponent("pessoalTecnicoesTable")
@LoadDataBeforeShow
public class PessoalTecnicoBrowse extends StandardLookup<PessoalTecnico> {

    @Inject
    private Label<String> text_sexo_masculino;
    @Inject
    private Label<String> text_formacao_profissional;
    @Inject
    private LookupField linhasPessoalTecnica;
    @Inject
    private LookupField<Valencias> idvalenciaField;
    @Inject
    private LookupField categoria_profissional_id;
    @Inject
    private LookupField habilitacoes_literarias_id;
    @Inject
    private CheckBox sexomasculinoField;
    @Inject
    private CheckBox formacaoprofissionalField;
    @Inject
    private CollectionLoader<PessoalTecnico> pessoalTecnicoesDl;
    @Inject
    private GroupTable<PessoalTecnico> pessoalTecnicoesTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;
    @Inject
    private DataManager dataManager;

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
        linhasPessoalTecnica.setOptionsList(list);

        pessoalTecnicoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(pessoalTecnicoesTable)
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
                        .withScreenClass(PessoalTecnicoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe("linhasPessoalTecnica")
    public void onLinhasPessoalTecnicaValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            pessoalTecnicoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            pessoalTecnicoesDl.setMaxResults(0);
        }
        pessoalTecnicoesDl.load();
    }

    @Subscribe("search_pessoal_tecnico")
    public void onSearch_pessoal_tecnicoClick(Button.ClickEvent event) {
        // ID de valencia
        if (idvalenciaField.getValue() != null) {
            pessoalTecnicoesDl.setParameter("idValencia",  idvalenciaField.getValue().getId());
        } else {
            pessoalTecnicoesDl.removeParameter("idValencia");
        }

        // Sexo Masculino

        if (sexomasculinoField.getValue())
        {
            pessoalTecnicoesDl.setParameter("sexomasculino",  true);
        } else {
            pessoalTecnicoesDl.removeParameter("sexomasculino");
        }

        // Formação Profissional

        if (formacaoprofissionalField.getValue())
        {
            pessoalTecnicoesDl.setParameter("formacaoprofissional",  true);
        }
        else
        {
            pessoalTecnicoesDl.removeParameter("formacaoprofissional");
        }

        // Categoria Profissional

        if (categoria_profissional_id.getValue() != null) {
            pessoalTecnicoesDl.setParameter("categoriaprofissional",  categoria_profissional_id.getValue().toString());
        } else {
            pessoalTecnicoesDl.removeParameter("categoriaprofissional");
        }

        // Habilitações Litetarias

        if (habilitacoes_literarias_id.getValue() != null) {
            pessoalTecnicoesDl.setParameter("habilitacoesliterarias",  habilitacoes_literarias_id.getValue().toString());
        } else {
            pessoalTecnicoesDl.removeParameter("habilitacoesliterarias");
        }


        pessoalTecnicoesDl.load();
    }

    @Subscribe("reset_search_pessoal_tecnico")
    public void onReset_search_pessoal_tecnicoClick(Button.ClickEvent event) {
        idvalenciaField.setValue(null);
        sexomasculinoField.setValue(false);
        formacaoprofissionalField.setValue(false);
        linhasPessoalTecnica.setValue(null);
        categoria_profissional_id.setValue(null);
        habilitacoes_literarias_id.setValue(null);
        pessoalTecnicoesDl.removeParameter("sexomasculino");
        pessoalTecnicoesDl.removeParameter("formacaoprofissional");
        pessoalTecnicoesDl.removeParameter("idValencia");
        pessoalTecnicoesDl.removeParameter("categoriaprofissional");
        pessoalTecnicoesDl.removeParameter("habilitacoesliterarias");
        pessoalTecnicoesDl.setMaxResults(0);
        pessoalTecnicoesDl.load();
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Pessoal Técnico");
        // Categoria Profissional
        List<String> options = new ArrayList<>();
        String queryString = "select o.categoriaprofissional as categoriaprofissional from cmolhao_PessoalTecnico o where o.categoriaprofissional is not null group by o.categoriaprofissional";
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