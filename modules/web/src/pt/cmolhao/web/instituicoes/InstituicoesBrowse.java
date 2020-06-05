package pt.cmolhao.web.instituicoes;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.web.fotosvalencia.FotosValenciaEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Instituicoes.browse")
@UiDescriptor("instituicoes-browse.xml")
@LookupComponent("instituicoesesTable")
@LoadDataBeforeShow
public class InstituicoesBrowse extends StandardLookup<Instituicoes> {

    @Inject
    private LookupField<Instituicoes> desricaoField;
    @Inject
    private CollectionLoader<Instituicoes> instituicoesesDl;

    @Inject
    private Notifications notifications;
    @Inject
    private LookupField linhasInstituicoes;
    @Inject
    private LookupField caeField;
    @Inject
    private DataManager dataManager;
    @Inject
    private LookupField naturezajuridcaField;
    @Inject
    private Label<String> text_clasolhao;
    @Inject
    private CheckBox clasolhaoField;
    @Inject
    private Label<String> text_cpcj;
    @Inject
    private CheckBox cpcjField;
    @Inject
    private CheckBox rsiField;
    @Inject
    private Label<String> text_rsi;
    @Inject
    private Label<String> text_plataformatematica;
    @Inject
    private CheckBox plataformatematicaField;
    @Inject
    private Label<String> text_projectoscomunitarios;
    @Inject
    private CheckBox projectoscomunitariosField;
    @Inject
    private Label<String> text_outraredelocal;
    @Inject
    private CheckBox outraredelocalField;
    @Inject
    private Table<Instituicoes> instituicoesesTable;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    protected void onInit(InitEvent event) {
        text_clasolhao.setValue("Olhão Classificado: ");
        text_cpcj.setValue("Centro de Protecção de Crianças e Jovens: ");
        text_rsi.setValue("Rendimento Social de Inserção: ");
        text_plataformatematica.setValue("Plataforma de Informática: ");
        text_projectoscomunitarios.setValue("Projectos Comunitários: ");
        text_outraredelocal.setValue("Outra Rede Local: ");
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
        linhasInstituicoes.setOptionsList(list);

        instituicoesesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(instituicoesesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setDescricao(desricaoField.getValue().getDescricao());
                            customer.setCae(caeField.getValue().toString());
                            customer.setNaturezajuridica(naturezajuridcaField.getValue().toString());
                            customer.setClasolhao(clasolhaoField.getValue());
                            customer.setCpcj(cpcjField.getValue());
                            customer.setRsi(rsiField.getValue());
                            customer.setPlataformatematica(plataformatematicaField.getValue());
                            customer.setProjectoscomunitarios(projectoscomunitariosField.getValue());
                            customer.setOutraredelocal(outraredelocalField.getValue());
                        })
                        .withScreenClass(InstituicoesEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {




        List<String> options = new ArrayList<>();
        String queryString = "select o.cae as cae from cmolhao_Instituicoes o where o.cae is not null group by o.cae";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("cae");
        List<KeyValueEntity> resultList = dataManager.loadValues(valueLoadContextontext);
        for (KeyValueEntity entry : resultList) {
            options.add(entry.getValue("cae"));
        }
        caeField.setOptionsList(options);

        List<String> options_natrureza_juridica = new ArrayList<>();
        String queryString_natrureza_juridica  = "select o.naturezajuridica as naturezajuridica from cmolhao_Instituicoes o where o.naturezajuridica is not null group by o.naturezajuridica";
        ValueLoadContext valueLoadContextontextNaturezaJuridica = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString_natrureza_juridica));
        valueLoadContextontextNaturezaJuridica.addProperty("naturezajuridica");
        List<KeyValueEntity> resultListNaturezaJuridica = dataManager.loadValues(valueLoadContextontextNaturezaJuridica);
        for (KeyValueEntity entry : resultListNaturezaJuridica) {
            options_natrureza_juridica.add(entry.getValue("naturezajuridica"));
        }
        naturezajuridcaField.setOptionsList(options_natrureza_juridica);


    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe("reset_search_instituicoes")
    public void onReset_search_instituicoesClick(Button.ClickEvent event) {
        desricaoField.setValue(null);
        caeField.setValue(null);
        naturezajuridcaField.setValue(null);
        linhasInstituicoes.setValue(null);
        clasolhaoField.setValue(false);
        cpcjField.setValue(false);
        rsiField.setValue(false);
        plataformatematicaField.setValue(false);
        projectoscomunitariosField.setValue(false);
        outraredelocalField.setValue(false);
        instituicoesesDl.removeParameter("descricao");
        instituicoesesDl.removeParameter("cae");
        instituicoesesDl.removeParameter("naturezajuridica");
        instituicoesesDl.removeParameter("clasolhao");
        instituicoesesDl.removeParameter("cpcj");
        instituicoesesDl.removeParameter("rsi");
        instituicoesesDl.removeParameter("plataformatematica");
        instituicoesesDl.removeParameter("projectoscomunitarios");
        instituicoesesDl.removeParameter("outraredelocal");
        instituicoesesDl.setMaxResults(0);
        instituicoesesDl.load();


    }

    @Subscribe("search_instituicoes")
    public void onSearch_instituicoesClick(Button.ClickEvent event) {
        // Descrição da Instituição
        if (desricaoField.getValue() != null) {
            instituicoesesDl.setParameter("descricao",  desricaoField.getValue().getDescricao());
        } else {
            instituicoesesDl.removeParameter("descricao");
        }

        // Classificação Portuguesa de Actividades Económicas - cae
        if (caeField.getValue() != null) {
            if (isNumeric(caeField.getValue().toString()))
            {
                instituicoesesDl.setParameter("cae", caeField.getValue().toString() );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do numero de cae</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }

        } else {
            instituicoesesDl.removeParameter("cae");
        }

        // Natureza Juridica
        if (naturezajuridcaField.getValue() != null) {
            instituicoesesDl.setParameter("naturezajuridica", naturezajuridcaField.getValue().toString() );
        }
        else
        {
            instituicoesesDl.removeParameter("naturezajuridica");
        }

        if (clasolhaoField.getValue())
        {
            instituicoesesDl.setParameter("clasolhao",  true);
        } else {
            instituicoesesDl.removeParameter("clasolhao");
        }

        if (cpcjField.getValue())
        {
            instituicoesesDl.setParameter("cpcj",  true);
        } else {
            instituicoesesDl.removeParameter("cpcj");
        }

        if (rsiField.getValue())
        {
            instituicoesesDl.setParameter("rsi",  true);
        } else {
            instituicoesesDl.removeParameter("rsi");
        }


        if (plataformatematicaField.getValue())
        {
            instituicoesesDl.setParameter("plataformatematica",  true);
        } else {
            instituicoesesDl.removeParameter("plataformatematica");
        }


        if (projectoscomunitariosField.getValue())
        {
            instituicoesesDl.setParameter("projectoscomunitarios",  true);
        } else {
            instituicoesesDl.removeParameter("projectoscomunitarios");
        }

        if (outraredelocalField.getValue())
        {
            instituicoesesDl.setParameter("outraredelocal",  true);
        } else {
            instituicoesesDl.removeParameter("outraredelocal");
        }


        instituicoesesDl.load();
    }

    @Subscribe("linhasInstituicoes")
    public void onLinhasInstituicoesValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            instituicoesesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            instituicoesesDl.setMaxResults(0);
        }
        instituicoesesDl.load();

    }
}