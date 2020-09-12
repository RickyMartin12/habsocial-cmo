package pt.cmolhao.web.nucleo_executivo_elementos_membros;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.ProjectosAprovacaoNucleoExecutivo;

@UiController("cmolhao_ProjectosAprovacaoNucleoExecutivo.edit")
@UiDescriptor("projectos-aprovacao-nucleo-executivo-edit.xml")
@EditedEntityContainer("projectosAprovacaoNucleoExecutivoDc")
@LoadDataBeforeShow
public class ProjectosAprovacaoNucleoExecutivoEdit extends StandardEditor<ProjectosAprovacaoNucleoExecutivo> {
}