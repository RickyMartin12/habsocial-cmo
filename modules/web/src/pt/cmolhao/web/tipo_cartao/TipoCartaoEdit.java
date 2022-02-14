package pt.cmolhao.web.tipo_cartao;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoCartao;

@UiController("cmolhao_TipoCartao.edit")
@UiDescriptor("tipo-cartao-edit.xml")
@EditedEntityContainer("tipoCartaoDc")
@LoadDataBeforeShow
public class TipoCartaoEdit extends StandardEditor<TipoCartao> {
}