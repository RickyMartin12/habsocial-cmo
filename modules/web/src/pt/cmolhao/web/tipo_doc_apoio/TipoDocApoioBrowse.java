package pt.cmolhao.web.tipo_doc_apoio;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoDocApoio;

@UiController("cmolhao_TipoDocApoio.browse")
@UiDescriptor("tipo-doc-apoio-browse.xml")
@LookupComponent("tipoDocApoiosTable")
@LoadDataBeforeShow
public class TipoDocApoioBrowse extends StandardLookup<TipoDocApoio> {
}