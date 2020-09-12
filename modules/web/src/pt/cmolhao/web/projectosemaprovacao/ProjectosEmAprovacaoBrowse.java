package pt.cmolhao.web.projectosemaprovacao;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.ProjectosEmAprovacao;
import pt.cmolhao.entity.ProjectosIntervencao;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@UiController("cmolhao_ProjectosEmAprovacao.browse")
@UiDescriptor("projectos-em-aprovacao-browse.xml")
@LookupComponent("projectosEmAprovacaosTable")
@LoadDataBeforeShow


public class ProjectosEmAprovacaoBrowse extends StandardLookup<ProjectosEmAprovacao> {

    @Inject
    protected UiComponents uiComponents;
    @Named("projectosEmAprovacaosTable.remove")
    protected RemoveAction<ProjectosEmAprovacao> projectosEmAprovacaosTableRemove;
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

    @Inject
    private Dialogs dialogs;

    public Component projectosEmIntervencaoInstituição(ProjectosEmAprovacao entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdprojectosintervencao() != null)
        {
            label.setValue("Projecto: " + entity.getIdprojectosintervencao().getId() + " - " + entity.getIdprojectosintervencao().getIdinstituicao().getDescricao());
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
                            map.put("Projecto: " + item.getId() + " - " + item.getIdinstituicao().getDescricao(), item);
                        } else {
                            map.put("Projecto: " + item.getId().toString(), item);
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
        projectosEmAprovacaosDl.removeParameter("etapaprocesso");
        projectosEmAprovacaosDl.removeParameter("idprojectosintervencao");
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

    @Subscribe("projectosEmAprovacaosTable.remove")
    protected void onProjectosEmAprovacaosTableRemove(Action.ActionPerformedEvent event) {
        projectosEmAprovacaosTableRemove.setConfirmation(false);
        if (projectosEmAprovacaosTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de projectos em aprovação")
                    .withMessage("Deve seleccionar pelo um dos projectos em aprovação")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            ProjectosEmAprovacao user = projectosEmAprovacaosTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do projecto em aprovação número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do projecto em aprovação número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        projectosEmAprovacaosTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }

    }
}