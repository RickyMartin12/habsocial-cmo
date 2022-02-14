package pt.cmolhao.web.casos_sociais;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoCasosSociais;

@UiController("cmolhao_DocumentacaoCasosSociais.browse")
@UiDescriptor("documentacao-casos-sociais-browse.xml")
@LookupComponent("documentacaoCasosSociaisesTable")
@LoadDataBeforeShow
public class DocumentacaoCasosSociaisBrowse extends StandardLookup<DocumentacaoCasosSociais> {
}