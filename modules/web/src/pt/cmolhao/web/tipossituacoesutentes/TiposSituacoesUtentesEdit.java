package pt.cmolhao.web.tipossituacoesutentes;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TiposSituacoesUtentes;

@UiController("cmolhao_TiposSituacoesUtentes.edit")
@UiDescriptor("tipos-situacoes-utentes-edit.xml")
@EditedEntityContainer("tiposSituacoesUtentesDc")
@LoadDataBeforeShow
public class TiposSituacoesUtentesEdit extends StandardEditor<TiposSituacoesUtentes> {
}