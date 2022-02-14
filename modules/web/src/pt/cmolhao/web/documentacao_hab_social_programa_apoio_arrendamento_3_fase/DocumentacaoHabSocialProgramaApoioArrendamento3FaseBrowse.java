package pt.cmolhao.web.documentacao_hab_social_programa_apoio_arrendamento_3_fase;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoHabSocialProgramaApoioArrendamento3Fase;

@UiController("cmolhao_DocumentacaoHabSocialProgramaApoioArrendamento3Fase.browse")
@UiDescriptor("documentacao-hab-social-programa-apoio-arrendamento3-fase-browse.xml")
@LookupComponent("documentacaoHabSocialProgramaApoioArrendamento3FasesTable")
@LoadDataBeforeShow
public class DocumentacaoHabSocialProgramaApoioArrendamento3FaseBrowse extends StandardLookup<DocumentacaoHabSocialProgramaApoioArrendamento3Fase> {
}