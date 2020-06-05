package pt.cmolhao.web.profissao;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Profissao;

@UiController("cmolhao_Profissao.edit")
@UiDescriptor("profissao-edit.xml")
@EditedEntityContainer("profissaoDc")
@LoadDataBeforeShow
public class ProfissaoEdit extends StandardEditor<Profissao> {
}