package pt.cmolhao.web.projectosintervencao;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.ProjectosIntervencao;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_ProjectosIntervencao.browse")
@UiDescriptor("projectos-intervencao-browse.xml")
@LookupComponent("projectosIntervencaosTable")
@LoadDataBeforeShow
public class ProjectosIntervencaoBrowse extends StandardLookup<ProjectosIntervencao> {
    @Named("projectosIntervencaosTable.remove")
    protected RemoveAction<ProjectosIntervencao> projectosIntervencaosTableRemove;
    @Inject
    protected TextField<String> nomeProjectoField;
    @Inject
    private LookupField linhasProjectosIntervencao;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    private CollectionLoader<ProjectosIntervencao> projectosIntervencaosDl;
    @Inject
    private LookupPickerField<Instituicoes> idinstituicaoField;
    @Inject
    private Label<String> text_projectosemaprovacao;
    @Inject
    private CheckBox projectosemaprovacaoField;
    @Inject
    private GroupTable<ProjectosIntervencao> projectosIntervencaosTable;
    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    public void onInit(InitEvent event) {
        text_projectosemaprovacao.setValue("Projectos em aprovação: ");

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
                            customer.setProjectosemaprovacao(projectosemaprovacaoField.getValue());
                            if (nomeProjectoField.getValue() != null)
                            {
                                customer.setNomeProjecto(nomeProjectoField.getValue());
                            }

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

        // projectosemaprovacao

        if (projectosemaprovacaoField.getValue())
        {
            projectosIntervencaosDl.setParameter("projectosemaprovacao",  true);
        }
        else
        {
            projectosIntervencaosDl.removeParameter("projectosemaprovacao");
        }

        if (nomeProjectoField.getValue() != null)
        {
            projectosIntervencaosDl.setParameter("nomeProjecto",  "(?i)%" + nomeProjectoField.getValue() + "%");
        } else {
            projectosIntervencaosDl.removeParameter("nomeProjecto");
        }

        projectosIntervencaosDl.load();
        
    }

    @Subscribe("reset_search_projectos_intervencao")
    public void onReset_search_projectos_intervencaoClick(Button.ClickEvent event) {
        idinstituicaoField.setValue(null);
        nomeProjectoField.setValue(null);
        projectosemaprovacaoField.setValue(false);
        projectosIntervencaosDl.removeParameter("idinstituicao");
        projectosIntervencaosDl.removeParameter("projectosemaprovacao");
        projectosIntervencaosDl.removeParameter("nomeProjecto");
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

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Projectos de Intervenção");
    }

    @Subscribe("projectosIntervencaosTable.remove")
    protected void onProjectosIntervencaosTableRemove(Action.ActionPerformedEvent event) {
        projectosIntervencaosTableRemove.setConfirmation(false);
        if (projectosIntervencaosTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção dos projectos de intervenção")
                    .withMessage("Deve seleccionar pelo uma dos projectos")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            ProjectosIntervencao user = projectosIntervencaosTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do projecto número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do projecto número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        projectosIntervencaosTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

}