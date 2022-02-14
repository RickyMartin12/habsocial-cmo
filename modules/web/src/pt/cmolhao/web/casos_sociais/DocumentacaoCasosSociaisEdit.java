package pt.cmolhao.web.casos_sociais;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.DocumentacaoCasosSociais;

@UiController("cmolhao_DocumentacaoCasosSociais.edit")
@UiDescriptor("documentacao-casos-sociais-edit.xml")
@EditedEntityContainer("documentacaoCasosSociaisDc")
@LoadDataBeforeShow
public class DocumentacaoCasosSociaisEdit extends StandardEditor<DocumentacaoCasosSociais> {
}