package pt.cmolhao.web.pareceres;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Pareceres;

@UiController("cmolhao_Pareceres.browse")
@UiDescriptor("pareceres-browse.xml")
@LookupComponent("pareceresesTable")
@LoadDataBeforeShow
public class PareceresBrowse extends StandardLookup<Pareceres> {
}