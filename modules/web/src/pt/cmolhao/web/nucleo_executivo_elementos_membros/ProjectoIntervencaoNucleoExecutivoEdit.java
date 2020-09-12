package pt.cmolhao.web.nucleo_executivo_elementos_membros;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.InstituicoesNucleoExecutivo;
import pt.cmolhao.entity.ProjectoIntervencaoNucleoExecutivo;
import pt.cmolhao.entity.ProjectosAprovacaoNucleoExecutivo;
import pt.cmolhao.entity.ProjectosEmAprovacao;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_ProjectoIntervencaoNucleoExecutivo.edit")
@UiDescriptor("projecto-intervencao-nucleo-executivo-edit.xml")
@EditedEntityContainer("projectoIntervencaoNucleoExecutivoDc")
@LoadDataBeforeShow
public class ProjectoIntervencaoNucleoExecutivoEdit extends StandardEditor<ProjectoIntervencaoNucleoExecutivo> {

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
    protected UiComponents uiComponents;
    @Inject
    protected Table<ProjectosAprovacaoNucleoExecutivo> projectosEmAprovacaosTable;
    @Named("projectosEmAprovacaosTable.remove")
    protected RemoveAction<ProjectosAprovacaoNucleoExecutivo> projectosEmAprovacaosTableRemove;
    @Inject
    protected LookupPickerField<InstituicoesNucleoExecutivo> idinstituicaoField;
    @Inject
    protected CollectionContainer<InstituicoesNucleoExecutivo> InstituicaoNucleoExecutivoDc;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Projecto de Intervenção do núcleo executivo- " + idProjectosIntervencaoField.getValue());


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



    public Component projectosEmIntervencaoInstituição(ProjectosAprovacaoNucleoExecutivo entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdprojectosintervencao() != null)
        {
            label.setValue("Projecto: " + entity.getIdprojectosintervencao().getId() + " - " + entity.getIdprojectosintervencao().getIdinstituicao().getDescricao());
            return label;
        }
        return null;
    }

    @Subscribe("projectosEmAprovacaosTable.remove")
    protected void onProjectosEmAprovacaosTableRemove(Action.ActionPerformedEvent event) {
        projectosEmAprovacaosTableRemove.setConfirmation(false);
        if (projectosEmAprovacaosTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de projectos em aprovação do núcleo executivo")
                    .withMessage("Deve seleccionar pelo um dos projectos em aprovação do núcleo executivo")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            ProjectosAprovacaoNucleoExecutivo user = projectosEmAprovacaosTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do projecto em aprovação do núcleo executivo número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do projecto em aprovação do núcleo executivo número '"+user.getId()+"'?")
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