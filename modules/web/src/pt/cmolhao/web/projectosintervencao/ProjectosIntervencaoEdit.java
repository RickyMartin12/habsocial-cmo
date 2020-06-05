package pt.cmolhao.web.projectosintervencao;

import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.ProjectosIntervencao;

import javax.inject.Inject;

@UiController("cmolhao_ProjectosIntervencao.edit")
@UiDescriptor("projectos-intervencao-edit.xml")
@EditedEntityContainer("projectosIntervencaoDc")
@LoadDataBeforeShow
public class ProjectosIntervencaoEdit extends StandardEditor<ProjectosIntervencao> {
    @Inject
    private LookupField<Instituicoes> idinstituicaoField;
    @Inject
    private CollectionContainer<Instituicoes> InstituicaoDc;


}