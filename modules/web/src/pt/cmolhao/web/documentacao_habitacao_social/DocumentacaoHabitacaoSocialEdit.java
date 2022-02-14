package pt.cmolhao.web.documentacao_habitacao_social;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoHabitacaoSocial;

@UiController("cmolhao_DocumentacaoHabitacaoSocial.edit")
@UiDescriptor("documentacao-habitacao-social-edit.xml")
@EditedEntityContainer("documentacaoHabitacaoSocialDc")
@LoadDataBeforeShow
public class DocumentacaoHabitacaoSocialEdit extends StandardEditor<DocumentacaoHabitacaoSocial> {
}