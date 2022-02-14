package pt.cmolhao.web.documentacao_hab_social_programa_apoio_arrendamento_documentacao;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoHabSocialDocumentacao;

@UiController("cmolhao_DocumentacaoHabSocialDocumentacao.browse")
@UiDescriptor("documentacao-hab-social-documentacao-browse.xml")
@LookupComponent("documentacaoHabSocialDocumentacaosTable")
@LoadDataBeforeShow
public class DocumentacaoHabSocialDocumentacaoBrowse extends StandardLookup<DocumentacaoHabSocialDocumentacao> {
}