package pt.cmolhao.web.documentacao_hab_social_programa_apoio_arrendamento_2_fase;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoHabSocialProgramaApoioArrendamento2Fase;

@UiController("cmolhao_DocumentacaoHabSocialProgramaApoioArrendamento2Fase.browse")
@UiDescriptor("documentacao-hab-social-programa-apoio-arrendamento2-fase-browse.xml")
@LookupComponent("documentacaoHabSocialProgramaApoioArrendamento2FasesTable")
@LoadDataBeforeShow
public class DocumentacaoHabSocialProgramaApoioArrendamento2FaseBrowse extends StandardLookup<DocumentacaoHabSocialProgramaApoioArrendamento2Fase> {
}