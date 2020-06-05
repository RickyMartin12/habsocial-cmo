package pt.cmolhao.web.moradores;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Moradores;

@UiController("cmolhao_Moradores.browse")
@UiDescriptor("moradores-browse.xml")
@LookupComponent("moradoresesTable")
@LoadDataBeforeShow
public class MoradoresBrowse extends StandardLookup<Moradores> {
}