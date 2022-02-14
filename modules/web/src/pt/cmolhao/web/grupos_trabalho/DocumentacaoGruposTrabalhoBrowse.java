package pt.cmolhao.web.grupos_trabalho;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoGruposTrabalho;

@UiController("cmolhao_DocumentacaoGruposTrabalho.browse")
@UiDescriptor("documentacao-grupos-trabalho-browse.xml")
@LookupComponent("documentacaoGruposTrabalhoesTable")
@LoadDataBeforeShow
public class DocumentacaoGruposTrabalhoBrowse extends StandardLookup<DocumentacaoGruposTrabalho> {
}