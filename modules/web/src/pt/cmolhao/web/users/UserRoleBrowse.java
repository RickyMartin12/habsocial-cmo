package pt.cmolhao.web.users;

import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.UserRole;

@UiController("sec$UserRole.browse")
@UiDescriptor("user-role-browse.xml")
@LookupComponent("userRolesTable")
@LoadDataBeforeShow
public class UserRoleBrowse extends StandardLookup<UserRole> {
    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar os Papéis dos Utilizadores");
    }

}