package pt.cmolhao.web.grupos_trabalho;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoGruposTrabalho;

@UiController("cmolhao_DocumentacaoGruposTrabalho.edit")
@UiDescriptor("documentacao-grupos-trabalho-edit.xml")
@EditedEntityContainer("documentacaoGruposTrabalhoDc")
@LoadDataBeforeShow
public class DocumentacaoGruposTrabalhoEdit extends StandardEditor<DocumentacaoGruposTrabalho> {
}