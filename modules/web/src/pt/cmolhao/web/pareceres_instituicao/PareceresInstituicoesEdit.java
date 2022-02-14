package pt.cmolhao.web.pareceres_instituicao;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.PareceresInstituicoes;

@UiController("cmolhao_PareceresInstituicoes.edit")
@UiDescriptor("pareceres-instituicoes-edit.xml")
@EditedEntityContainer("pareceresInstituicoesDc")
@LoadDataBeforeShow
public class PareceresInstituicoesEdit extends StandardEditor<PareceresInstituicoes> {
}