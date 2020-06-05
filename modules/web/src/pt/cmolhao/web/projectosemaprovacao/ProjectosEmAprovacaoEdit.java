package pt.cmolhao.web.projectosemaprovacao;

import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.ProjectosEmAprovacao;
import pt.cmolhao.entity.ProjectosIntervencao;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import java.util.*;

@UiController("cmolhao_ProjectosEmAprovacao.edit")
@UiDescriptor("projectos-em-aprovacao-edit.xml")
@EditedEntityContainer("projectosEmAprovacaoDc")
@LoadDataBeforeShow
public class ProjectosEmAprovacaoEdit extends StandardEditor<ProjectosEmAprovacao> {
    @Inject
    private CollectionContainer<ProjectosIntervencao> ProjectosIntervencaoDc;
    @Inject
    private LookupField<ProjectosIntervencao> idprojectosintervencaoField;
    @Inject
    private LookupField<String> etapaprocessoField;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Em curso");
        list.add("Valência aprovada e em implementação");
        list.add("Valência reprovada");
        etapaprocessoField.setOptionsList(list);
    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        Map<String, ProjectosIntervencao> map = new HashMap<>();
        Collection<ProjectosIntervencao> customers = ProjectosIntervencaoDc.getItems();
        for (ProjectosIntervencao item : customers) {

            if (item.getProjectosemaprovacao() != null) {
                if (item.getProjectosemaprovacao().equals(true)) {
                    if (item.getIdinstituicao() != null) {
                        map.put("Projecto de Intervenção: " + item.getId() + " - " + item.getIdinstituicao().getDescricao(), item);
                    } else {
                        map.put("Projecto de Intervenção: " + item.getId().toString(), item);
                    }
                }
            }
        }
        idprojectosintervencaoField.setOptionsMap(map);


    }

}