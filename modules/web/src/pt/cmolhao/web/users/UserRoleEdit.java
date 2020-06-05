package pt.cmolhao.web.users;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@UiController("sec$UserRole.edit")
@UiDescriptor("user-role-edit.xml")
@EditedEntityContainer("userRoleDc")
@LoadDataBeforeShow
public class UserRoleEdit extends StandardEditor<UserRole> {
    @Inject
    private DataManager dataManager;
    @Inject
    private LookupField roleField;
    @Inject
    private TextField<User> label_user;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {


        getWindow().setCaption("Edit/Add Papel Utl. '"+label_user.getRawValue()+"' ");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Acesso minimo ao Sistema", "system-minimal");
        map.put("Acesso Minimo de Gestão de relatórios ao Sistema", "system-reports-minimal");
        map.put("Acesso Administrativo (Admin)", "system-full-access");
        roleField.setOptionsMap(map);


    }
}