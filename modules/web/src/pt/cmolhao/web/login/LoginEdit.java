package pt.cmolhao.web.login;

import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Login;

@UiController("cmolhao_Login.edit")
@UiDescriptor("login-edit.xml")
@EditedEntityContainer("loginDc")
@LoadDataBeforeShow
public class LoginEdit extends StandardEditor<Login> {
}