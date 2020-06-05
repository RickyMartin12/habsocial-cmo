package pt.cmolhao.web.tipossituacoesutentes;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TiposSituacoesUtentes;

@UiController("cmolhao_TiposSituacoesUtentes.browse")
@UiDescriptor("tipos-situacoes-utentes-browse.xml")
@LookupComponent("tiposSituacoesUtentesesTable")
@LoadDataBeforeShow
public class TiposSituacoesUtentesBrowse extends StandardLookup<TiposSituacoesUtentes> {
}