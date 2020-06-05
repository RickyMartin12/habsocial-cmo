package pt.cmolhao.web.login;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Login;

@UiController("cmolhao_Login.browse")
@UiDescriptor("login-browse.xml")
@LookupComponent("loginsTable")
@LoadDataBeforeShow
public class LoginBrowse extends StandardLookup<Login> {
}