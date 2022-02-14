package pt.cmolhao.web.documentacao_hab_social_programa_apoio_arrendamento_documentacao;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoHabSocialDocumentacao;

@UiController("cmolhao_DocumentacaoHabSocialDocumentacao.edit")
@UiDescriptor("documentacao-hab-social-documentacao-edit.xml")
@EditedEntityContainer("documentacaoHabSocialDocumentacaoDc")
@LoadDataBeforeShow
public class DocumentacaoHabSocialDocumentacaoEdit extends StandardEditor<DocumentacaoHabSocialDocumentacao> {
}