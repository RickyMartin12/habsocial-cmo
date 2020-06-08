package pt.cmolhao.web.utente;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Utente;

@UiController("cmolhao_Utente.edit")
@UiDescriptor("utente-edit.xml")
@EditedEntityContainer("utenteDc")
@LoadDataBeforeShow
public class UtenteEdit extends StandardEditor<Utente> {

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }


    @Subscribe
    protected void onInit(InitEvent event) {


    }


}