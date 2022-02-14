package pt.cmolhao.web.tipos_pareceres;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipoPareceres;

@UiController("cmolhao_TipoPareceres.browse")
@UiDescriptor("tipo-pareceres-browse.xml")
@LookupComponent("tipoPareceresesTable")
@LoadDataBeforeShow
public class TipoPareceresBrowse extends StandardLookup<TipoPareceres> {
}