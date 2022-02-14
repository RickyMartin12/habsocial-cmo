package pt.cmolhao.web.plataforma_supraconcelhia;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoPlataformaSupraconcelhia;

@UiController("cmolhao_DocumentacaoPlataformaSupraconcelhia.browse")
@UiDescriptor("documentacao-plataforma-supraconcelhia-browse.xml")
@LookupComponent("documentacaoPlataformaSupraconcelhiasTable")
@LoadDataBeforeShow
public class DocumentacaoPlataformaSupraconcelhiaBrowse extends StandardLookup<DocumentacaoPlataformaSupraconcelhia> {
}