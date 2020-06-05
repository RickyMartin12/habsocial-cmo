package pt.cmolhao.web.projectosintervencao;

import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.ProjectosIntervencao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_ProjectosIntervencao.browse")
@UiDescriptor("projectos-intervencao-browse.xml")
@LookupComponent("projectosIntervencaosTable")
@LoadDataBeforeShow
public class ProjectosIntervencaoBrowse extends StandardLookup<ProjectosIntervencao> {
    @Inject
    private Label<String> text_pretende_algarga_servicos;
    @Inject
    private LookupField linhasProjectosIntervencao;
    @Inject
    private Label<String> text_criancas;
    @Inject
    private Label<String> text_jovens;
    @Inject
    private Label<String> text_adultos;
    @Inject
    private Label<String> text_idosos;
    @Inject
    private Label<String> text_comunidade;
    @Inject
    private Label<String> text_deficientes;
    @Inject
    private Label<String> text_idososlar;
    @Inject
    private Label<String> text_idosospequenolar;
    @Inject
    private Label<String> text_idososcentrodia;
    @Inject
    private Label<String> text_idososcat;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    private Label<String> text_idososapoiodomiciliario;
    @Inject
    private Label<String> text_jovensjardiminfancia;
    @Inject
    private Label<String> text_jovensatl;
    @Inject
    private Label<String> text_jovenscat;
    @Inject
    private Label<String> text_deficientescao;
    @Inject
    private Label<String> text_deficienteslarresidencial;
    @Inject
    private Label<String> texdeficientesapoiodomiciliario;
    @Inject
    private CollectionLoader<ProjectosIntervencao> projectosIntervencaosDl;
    @Inject
    private LookupField<Instituicoes> idinstituicaoField;
    @Inject
    private Label<String> text_projectosemaprovacao;
    @Inject
    private CheckBox pretendealargarservicosField;
    @Inject
    private CheckBox criancasField;
    @Inject
    private CheckBox jovensField;
    @Inject
    private CheckBox adultosField;
    @Inject
    private CheckBox idososField;
    @Inject
    private CheckBox comunidadeField;
    @Inject
    private CheckBox deficientesField;
    @Inject
    private CheckBox idososlarField;
    @Inject
    private CheckBox idosospequenolarField;
    @Inject
    private CheckBox idososcentrodiaField;
    @Inject
    private CheckBox idososcatField;
    @Inject
    private CheckBox idososapoiodomiciliarioField;
    @Inject
    private CheckBox jovensjardiminfanciaField;
    @Inject
    private CheckBox jovensatlField;
    @Inject
    private CheckBox jovenscatField;
    @Inject
    private CheckBox deficientescaoField;
    @Inject
    private CheckBox deficienteslarresidencialField;
    @Inject
    private CheckBox deficientesapoiodomiciliarioField;
    @Inject
    private CheckBox projectosemaprovacaoField;
    @Inject
    private Label<String> text_jovenscreche;
    @Inject
    private CheckBox jovenscrecheField;
    @Inject
    private GroupTable<ProjectosIntervencao> projectosIntervencaosTable;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    public void onInit(InitEvent event) {
        text_pretende_algarga_servicos.setValue("Pretende Serviços Algarvios: ");
        text_criancas.setValue("Crianças: ");
        text_jovens.setValue("Jovens: ");
        text_adultos.setValue("Adultos: ");
        text_idosos.setValue("Idosos: ");
        text_comunidade.setValue("Comunidade: ");
        text_deficientes.setValue("Deficientes: ");
        text_idososlar.setValue("Idosos Lar: ");
        text_idosospequenolar.setValue("Idosos de Pequenos Lares: ");
        text_idososcentrodia.setValue("Idosos Centros do Dia: ");
        text_idososcat.setValue("Categoria de Idosos: ");
        text_idososapoiodomiciliario.setValue("Idosos Apoio Domiciliario: ");
        text_jovensjardiminfancia.setValue("Jovens de Jardim de Infância: ");
        text_jovensatl.setValue("Jovens ATL: ");
        text_jovenscat.setValue("Categoria de Jovens: ");
        text_deficientescao.setValue("Categoria de Deficientes: ");
        text_deficienteslarresidencial.setValue("Deficientes de lares residencial: ");
        texdeficientesapoiodomiciliario.setValue("Deficientes de apoio domiciliario: ");
        text_projectosemaprovacao.setValue("Projectos em aprovação: ");
        text_jovenscreche.setValue("Jovens de Creche: ");

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
        linhasProjectosIntervencao.setOptionsList(list);

        projectosIntervencaosTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(projectosIntervencaosTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdinstituicao(idinstituicaoField.getValue());
                            customer.setPretendealargarservicos(pretendealargarservicosField.getValue());
                            customer.setCriancas(criancasField.getValue());
                            customer.setIdosos(idososField.getValue());
                            customer.setJovens(jovensField.getValue());
                            customer.setAdultos(adultosField.getValue());
                            customer.setComunidade(comunidadeField.getValue());
                            customer.setDeficientes(deficientesField.getValue());
                            customer.setIdososlar(idososlarField.getValue());
                            customer.setIdosospequenolar(idosospequenolarField.getValue());
                            customer.setIdososcentrodia(idososcentrodiaField.getValue());
                            customer.setIdososcat(idososcatField.getValue());
                            customer.setIdososapoiodomiciliario(idososapoiodomiciliarioField.getValue());
                            customer.setJovensjardiminfancia(jovensjardiminfanciaField.getValue());
                            customer.setJovensatl(jovensatlField.getValue());
                            customer.setJovenscat(jovenscatField.getValue());
                            customer.setDeficientescao(deficientescaoField.getValue());
                            customer.setDeficienteslarresidencial(deficienteslarresidencialField.getValue());
                            customer.setDeficientesapoiodomiciliario(deficientesapoiodomiciliarioField.getValue());
                            customer.setJovenscreche(jovenscrecheField.getValue());
                            customer.setProjectosemaprovacao(projectosemaprovacaoField.getValue());
                        })
                        .withScreenClass(ProjectosIntervencaoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("search_projectos_intervencao")
    public void onSearch_projectos_intervencaoClick(Button.ClickEvent event) {

        // ID de Instituição

        if (idinstituicaoField.getValue() != null)
        {
            projectosIntervencaosDl.setParameter("idinstituicao",  idinstituicaoField.getValue().getId());
        } else {
            projectosIntervencaosDl.removeParameter("idinstituicao");
        }

        //  pretendealargarservicos

        if (pretendealargarservicosField.getValue())
        {
            projectosIntervencaosDl.setParameter("pretendealargarservicos",  true);
        } else {
            projectosIntervencaosDl.removeParameter("pretendealargarservicos");
        }

        // crianças

        if (criancasField.getValue())
        {
            projectosIntervencaosDl.setParameter("criancas",  true);
        } else {
            projectosIntervencaosDl.removeParameter("criancas");
        }

        // Jovens

        if (jovensField.getValue())
        {
            projectosIntervencaosDl.setParameter("jovens",  true);
        } else {
            projectosIntervencaosDl.removeParameter("jovens");
        }

        // Adultos

        if (adultosField.getValue())
        {
            projectosIntervencaosDl.setParameter("adultos",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("adultos");
        }

        // Idosos

        if (idososField.getValue())
        {
            projectosIntervencaosDl.setParameter("idosos",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("idosos");
        }

        // Comunidade

        if (comunidadeField.getValue())
        {
            projectosIntervencaosDl.setParameter("comunidade",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("comunidade");
        }

        // deficientes

        if (deficientesField.getValue())
        {
            projectosIntervencaosDl.setParameter("deficientes",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("deficientes");
        }

        // idososlar

        if (idososlarField.getValue())
        {
            projectosIntervencaosDl.setParameter("idososlar",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("idososlar");
        }

        // idosospequenolar

        if (idosospequenolarField.getValue())
        {
            projectosIntervencaosDl.setParameter("idosospequenolar",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("idosospequenolar");
        }

        // idososcentrodia

        if (idososcentrodiaField.getValue())
        {
            projectosIntervencaosDl.setParameter("idososcentrodia",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("idososcentrodia");
        }

        // idososcat

        if (idososcatField.getValue())
        {
            projectosIntervencaosDl.setParameter("idososcat",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("idososcat");
        }

        // idososapoiodomiciliario

        if (idososapoiodomiciliarioField.getValue())
        {
            projectosIntervencaosDl.setParameter("idososapoiodomiciliario",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("idososapoiodomiciliario");
        }

        // jovensjardiminfancia

        if (jovensjardiminfanciaField.getValue())
        {
            projectosIntervencaosDl.setParameter("jovensjardiminfancia",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("jovensjardiminfancia");
        }

        // jovensatl

        if (jovensatlField.getValue())
        {
            projectosIntervencaosDl.setParameter("jovensatl",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("jovensatl");
        }

        // jovenscat

        if (jovenscatField.getValue())
        {
            projectosIntervencaosDl.setParameter("jovenscat",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("jovenscat");
        }

        // deficientescao

        if (deficientescaoField.getValue())
        {
            projectosIntervencaosDl.setParameter("deficientescao",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("deficientescao");
        }

        // deficienteslarresidencial

        if (deficienteslarresidencialField.getValue())
        {
            projectosIntervencaosDl.setParameter("deficienteslarresidencial",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("deficienteslarresidencial");
        }

        // deficientesapoiodomiciliario

        if (deficientesapoiodomiciliarioField.getValue())
        {
            projectosIntervencaosDl.setParameter("deficientesapoiodomiciliario",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("deficientesapoiodomiciliario");
        }

        // projectosemaprovacao

        if (projectosemaprovacaoField.getValue())
        {
            projectosIntervencaosDl.setParameter("projectosemaprovacao",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("projectosemaprovacao");
        }

        // jovenscreche

        if (jovenscrecheField.getValue())
        {
            projectosIntervencaosDl.setParameter("jovenscreche",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("jovenscreche");
        }
        projectosIntervencaosDl.load();
        
    }

    @Subscribe("reset_search_projectos_intervencao")
    public void onReset_search_projectos_intervencaoClick(Button.ClickEvent event) {
        idinstituicaoField.setValue(null);
        linhasProjectosIntervencao.setValue(null);
        pretendealargarservicosField.setValue(false);
        criancasField.setValue(false);
        jovensField.setValue(false);
        adultosField.setValue(false);
        idososField.setValue(false);
        comunidadeField.setValue(false);
        deficientesField.setValue(false);
        idososlarField.setValue(false);
        idosospequenolarField.setValue(false);
        idososcentrodiaField.setValue(false);
        idososcatField.setValue(false);
        idososapoiodomiciliarioField.setValue(false);
        jovensjardiminfanciaField.setValue(false);
        jovensatlField.setValue(false);
        jovenscatField.setValue(false);
        deficientescaoField.setValue(false);
        deficienteslarresidencialField.setValue(false);
        deficientesapoiodomiciliarioField.setValue(false);
        jovenscrecheField.setValue(false);
        projectosemaprovacaoField.setValue(false);
        projectosIntervencaosDl.removeParameter("idinstituicao");
        projectosIntervencaosDl.removeParameter("pretendealargarservicos");
        projectosIntervencaosDl.removeParameter("criancas");
        projectosIntervencaosDl.removeParameter("jovens");
        projectosIntervencaosDl.removeParameter("adultos");
        projectosIntervencaosDl.removeParameter("idosos");
        projectosIntervencaosDl.removeParameter("comunidade");
        projectosIntervencaosDl.removeParameter("deficientes");
        projectosIntervencaosDl.removeParameter("idososlar");
        projectosIntervencaosDl.removeParameter("idosospequenolar");
        projectosIntervencaosDl.removeParameter("idososcentrodia");
        projectosIntervencaosDl.removeParameter("idososcat");
        projectosIntervencaosDl.removeParameter("idososapoiodomiciliario");
        projectosIntervencaosDl.removeParameter("jovensjardiminfancia");
        projectosIntervencaosDl.removeParameter("jovensatl");
        projectosIntervencaosDl.removeParameter("jovenscat");
        projectosIntervencaosDl.removeParameter("deficientescao");
        projectosIntervencaosDl.removeParameter("deficienteslarresidencial");
        projectosIntervencaosDl.removeParameter("deficientesapoiodomiciliario");
        projectosIntervencaosDl.removeParameter("projectosemaprovacao");
        projectosIntervencaosDl.removeParameter("jovenscreche");
        projectosIntervencaosDl.setMaxResults(0);
        projectosIntervencaosDl.load();
    }

    @Subscribe("linhasProjectosIntervencao")
    public void onLinhasProjectosIntervencaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            projectosIntervencaosDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            projectosIntervencaosDl.setMaxResults(0);
        }
        projectosIntervencaosDl.load();
    }
}