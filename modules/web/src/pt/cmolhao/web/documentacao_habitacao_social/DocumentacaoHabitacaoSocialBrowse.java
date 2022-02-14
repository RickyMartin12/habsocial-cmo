package pt.cmolhao.web.documentacao_habitacao_social;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoHabitacaoSocial;

@UiController("cmolhao_DocumentacaoHabitacaoSocial.browse")
@UiDescriptor("documentacao-habitacao-social-browse.xml")
@LookupComponent("documentacaoHabitacaoSocialsTable")
@LoadDataBeforeShow
public class DocumentacaoHabitacaoSocialBrowse extends StandardLookup<DocumentacaoHabitacaoSocial> {
}