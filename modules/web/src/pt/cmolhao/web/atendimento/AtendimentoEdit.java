package pt.cmolhao.web.atendimento;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Atendimento;

@UiController("cmolhao_Atendimento.edit")
@UiDescriptor("atendimento-edit.xml")
@EditedEntityContainer("atendimentoDc")
@LoadDataBeforeShow
public class AtendimentoEdit extends StandardEditor<Atendimento> {
}