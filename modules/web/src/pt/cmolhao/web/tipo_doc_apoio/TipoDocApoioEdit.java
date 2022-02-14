package pt.cmolhao.web.tipo_doc_apoio;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoDocApoio;

@UiController("cmolhao_TipoDocApoio.edit")
@UiDescriptor("tipo-doc-apoio-edit.xml")
@EditedEntityContainer("tipoDocApoioDc")
@LoadDataBeforeShow
public class TipoDocApoioEdit extends StandardEditor<TipoDocApoio> {
}