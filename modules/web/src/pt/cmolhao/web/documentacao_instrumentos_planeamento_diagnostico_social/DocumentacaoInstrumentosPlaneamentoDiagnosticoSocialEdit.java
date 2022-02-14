package pt.cmolhao.web.documentacao_instrumentos_planeamento_diagnostico_social;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoInstrumentosPlaneamentoDiagnosticoSocial;

@UiController("cmolhao_DocumentacaoInstrumentosPlaneamentoDiagnosticoSocial.edit")
@UiDescriptor("documentacao-instrumentos-planeamento-diagnostico-social-edit.xml")
@EditedEntityContainer("documentacaoInstrumentosPlaneamentoDiagnosticoSocialDc")
@LoadDataBeforeShow
public class DocumentacaoInstrumentosPlaneamentoDiagnosticoSocialEdit extends StandardEditor<DocumentacaoInstrumentosPlaneamentoDiagnosticoSocial> {
}