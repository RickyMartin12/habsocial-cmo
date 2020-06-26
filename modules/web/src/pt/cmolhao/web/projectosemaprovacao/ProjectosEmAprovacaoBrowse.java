package pt.cmolhao.web.projectosemaprovacao;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.ProjectosEmAprovacao;
import pt.cmolhao.entity.ProjectosIntervencao;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.web.pessoalauxiliar.PessoalAuxiliarEdit;

import javax.inject.Inject;
import java.util.*;

@UiController("cmolhao_ProjectosEmAprovacao.browse")
@UiDescriptor("projectos-em-aprovacao-browse.xml")
@LookupComponent("projectosEmAprovacaosTable")
@LoadDataBeforeShow


public class ProjectosEmAprovacaoBrowse extends StandardLookup<ProjectosEmAprovacao> {

    @Inject
    protected UiComponents uiComponents;
    @Inject
    private CollectionContainer<ProjectosIntervencao> projectosIntervencaosDc;
    @Inject
    private LookupField<ProjectosIntervencao> idprojectosintervencaoField;
    @Inject
    private LookupField etapaprocessoField;
    @Inject
    private CollectionLoader<ProjectosEmAprovacao> projectosEmAprovacaosDl;
    @Inject
    private LookupField linhasProjectosEmAprovacao;
    @Inject
    private GroupTable<ProjectosEmAprovacao> projectosEmAprovacaosTable;
    @Inject
    private ScreenBuilders screenBuilders;

    public Component projectosEmIntervencaoInstituição(ProjectosEmAprovacao entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdprojectosintervencao() != null)
        {
            label.setValue("Projecto de Intervenção: " + entity.getIdprojectosintervencao().getId() + " - " + entity.getIdprojectosintervencao().getIdinstituicao().getDescricao());
            return label;
        }
        return null;
    }

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Em curso");
        list.add("Valência aprovada e em implementação");
        list.add("Valência reprovada");
        etapaprocessoField.setOptionsList(list);

        List<Integer> lister = new ArrayList<>();
        lister.add(10);
        lister.add(20);
        lister.add(50);
        lister.add(100);
        lister.add(200);
        lister.add(500);
        lister.add(1000);
        lister.add(2000);
        lister.add(5000);
        lister.add(10000);
        linhasProjectosEmAprovacao.setOptionsList(lister);

        projectosEmAprovacaosTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(projectosEmAprovacaosTable)
                        .newEntity()
                        .withInitializer(customer -> {

                            customer.setIdprojectosintervencao(idprojectosintervencaoField.getValue());
                            if (etapaprocessoField.getValue() != null)
                            {
                                customer.setEtapaprocesso(etapaprocessoField.getValue().toString());
                            }
                        })
                        .withScreenClass(ProjectosEmAprovacaoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Projectos em Aprovação");
        Map<String, ProjectosIntervencao> map = new HashMap<>();
        Collection<ProjectosIntervencao> customers = projectosIntervencaosDc.getItems();
        for (ProjectosIntervencao item : customers) {
            if (item != null) {
                if (item.getProjectosemaprovacao() != null)
                {
                    if (item.getProjectosemaprovacao().equals(true))
                    {
                        if (item.getIdinstituicao() != null) {
                            map.put("Projecto de Intervenção: " + item.getId() + " - " + item.getIdinstituicao().getDescricao(), item);
                        } else {
                            map.put("Projecto de Intervenção: " + item.getId().toString(), item);
                        }
                    }

                }

            }
        }
        idprojectosintervencaoField.setOptionsMap(map);
    }

    @Subscribe("search_projectos_em_aprovacao")
    public void onSearch_projectos_em_aprovacaoClick(Button.ClickEvent event) {
        // ID de Projectos de Intervencao
        if (idprojectosintervencaoField.getValue() != null) {
            projectosEmAprovacaosDl.setParameter("idprojectosintervencao",  idprojectosintervencaoField.getValue().getId());
        } else {
            projectosEmAprovacaosDl.removeParameter("idprojectosintervencao");
        }

        // etapaprocessoField

        if (etapaprocessoField.getValue() != null)
        {
            projectosEmAprovacaosDl.setParameter("etapaprocesso", etapaprocessoField.getValue().toString());
        }
        else
        {
            projectosEmAprovacaosDl.removeParameter("etapaprocesso");
        }
        projectosEmAprovacaosDl.load();
    }

    @Subscribe("reset_search_projectos_em_aprovacao")
    public void onReset_search_projectos_em_aprovacaoClick(Button.ClickEvent event) {
        etapaprocessoField.setValue(null);
        idprojectosintervencaoField.setValue(null);
        linhasProjectosEmAprovacao.setValue(null);
        projectosEmAprovacaosDl.removeParameter("etapaprocesso");
        projectosEmAprovacaosDl.removeParameter("idprojectosintervencao");
        projectosEmAprovacaosDl.setMaxResults(0);
        projectosEmAprovacaosDl.load();
    }

    @Subscribe("linhasProjectosEmAprovacao")
    public void onLinhasProjectosEmAprovacaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            projectosEmAprovacaosDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            projectosEmAprovacaosDl.setMaxResults(0);
        }
        projectosEmAprovacaosDl.load();
    }
}