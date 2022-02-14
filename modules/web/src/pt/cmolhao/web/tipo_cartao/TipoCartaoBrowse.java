package pt.cmolhao.web.tipo_cartao;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoCartao;

@UiController("cmolhao_TipoCartao.browse")
@UiDescriptor("tipo-cartao-browse.xml")
@LookupComponent("tipoCartaosTable")
@LoadDataBeforeShow
public class TipoCartaoBrowse extends StandardLookup<TipoCartao> {
}