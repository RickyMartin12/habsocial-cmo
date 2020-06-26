package pt.cmolhao.web.projectosintervencao;

import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.ProjectosIntervencao;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_ProjectosIntervencao.edit")
@UiDescriptor("projectos-intervencao-edit.xml")
@EditedEntityContainer("projectosIntervencaoDc")
@LoadDataBeforeShow
public class ProjectosIntervencaoEdit extends StandardEditor<ProjectosIntervencao> {
    @Inject
    protected TextField<UUID> idProjectosIntervencaoField;
    @Inject
    private LookupField<Instituicoes> idinstituicaoField;
    @Inject
    private CollectionContainer<Instituicoes> InstituicaoDc;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Projecto de Intervenção - " + idProjectosIntervencaoField.getValue());
    }


}