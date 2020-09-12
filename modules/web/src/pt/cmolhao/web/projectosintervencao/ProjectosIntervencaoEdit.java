package pt.cmolhao.web.projectosintervencao;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.ProjectosEmAprovacao;
import pt.cmolhao.entity.ProjectosIntervencao;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_ProjectosIntervencao.edit")
@UiDescriptor("projectos-intervencao-edit.xml")
@EditedEntityContainer("projectosIntervencaoDc")
@LoadDataBeforeShow
public class ProjectosIntervencaoEdit extends StandardEditor<ProjectosIntervencao> {
    @Inject
    protected TextField<UUID> idProjectosIntervencaoField;
    @Inject
    protected CheckBox deficientesField;
    @Inject
    protected CheckBox deficientescaoField;
    @Inject
    protected CheckBox adultosField;
    @Inject
    protected CheckBox deficientesapoiodomiciliarioField;
    @Inject
    protected CheckBox deficienteslarresidencialField;
    @Inject
    protected CheckBox criancasField;
    @Inject
    protected CheckBox jovensatlField;
    @Inject
    protected CheckBox jovenscrecheField;
    @Inject
    protected CheckBox jovensField;
    @Inject
    protected CheckBox jovenscatField;
    @Inject
    protected CheckBox jovensjardiminfanciaField;
    @Inject
    protected CheckBox idososField;
    @Inject
    protected CheckBox idososapoiodomiciliarioField;
    @Inject
    protected CheckBox idososcatField;
    @Inject
    protected CheckBox idososcentrodiaField;
    @Inject
    protected CheckBox idososlarField;
    @Inject
    protected CheckBox idosospequenolarField;
    @Inject
    protected Table<ProjectosEmAprovacao> projectosEmAprovacaosTable;
    @Named("projectosEmAprovacaosTable.remove")
    protected RemoveAction<ProjectosEmAprovacao> projectosEmAprovacaosTableRemove;
    @Inject
    private LookupPickerField<Instituicoes> idinstituicaoField;
    @Inject
    private CollectionContainer<Instituicoes> InstituicaoDc;
    @Inject
    protected UiComponents uiComponents;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Projecto de Intervenção - " + idProjectosIntervencaoField.getValue());


    }


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
        jovensField.addValueChangeListener(valueChangeEvent -> {
            if (Boolean.TRUE.equals(valueChangeEvent.getValue())) {
                jovensatlField.setValue(true);
                jovenscatField.setValue(true);
                jovenscrecheField.setValue(true);
                jovensjardiminfanciaField.setValue(true);
            } else {
                jovensatlField.setValue(false);
                jovenscatField.setValue(false);
                jovenscrecheField.setValue(false);
                jovensjardiminfanciaField.setValue(false);
            }
        });

        deficientescaoField.addValueChangeListener(booleanValueChangeEvent ->  {
            if (Boolean.TRUE.equals(booleanValueChangeEvent.getValue())) {
                deficientesapoiodomiciliarioField.setValue(true);
                deficientescaoField.setValue(true);
                deficienteslarresidencialField.setValue(true);
            }
            else
            {
                deficientesapoiodomiciliarioField.setValue(false);
                deficientescaoField.setValue(false);
                deficienteslarresidencialField.setValue(false);
            }
        });

        idososField.addValueChangeListener(booleanValueChangeEvent ->  {
            if (Boolean.TRUE.equals(booleanValueChangeEvent.getValue())) {
                idososapoiodomiciliarioField.setValue(true);
                idososcatField.setValue(true);
                idososcentrodiaField.setValue(true);
                idososlarField.setValue(true);
                idosospequenolarField.setValue(true);
            }
            else
            {
                idososapoiodomiciliarioField.setValue(false);
                idososcatField.setValue(false);
                idososcentrodiaField.setValue(false);
                idososlarField.setValue(false);
                idosospequenolarField.setValue(false);
            }
        });

        deficientesField.addValueChangeListener(booleanValueChangeEvent -> {
            if (Boolean.TRUE.equals(booleanValueChangeEvent.getValue())) {
                deficienteslarresidencialField.setValue(true);
                deficientescaoField.setValue(true);
                deficientesapoiodomiciliarioField.setValue(true);
            }
            else
            {
                deficienteslarresidencialField.setValue(false);
                deficientescaoField.setValue(false);
                deficientesapoiodomiciliarioField.setValue(false);
            }
        });




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