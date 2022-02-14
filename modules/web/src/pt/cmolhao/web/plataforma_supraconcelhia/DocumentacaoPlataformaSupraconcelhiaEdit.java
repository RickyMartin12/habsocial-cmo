package pt.cmolhao.web.plataforma_supraconcelhia;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoPlataformaSupraconcelhia;

@UiController("cmolhao_DocumentacaoPlataformaSupraconcelhia.edit")
@UiDescriptor("documentacao-plataforma-supraconcelhia-edit.xml")
@EditedEntityContainer("documentacaoPlataformaSupraconcelhiaDc")
@LoadDataBeforeShow
public class DocumentacaoPlataformaSupraconcelhiaEdit extends StandardEditor<DocumentacaoPlataformaSupraconcelhia> {
}