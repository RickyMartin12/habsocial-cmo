package pt.cmolhao.web.passwords;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;
import pt.cmolhao.web.users.UserEdit;

import javax.inject.Inject;

@UiController("UserPassword.browse")
@UiDescriptor("user-password-browse.xml")
@LookupComponent("usersTable")
@LoadDataBeforeShow
public class UserPasswordBrowse extends StandardLookup<User> {

    @Inject
    protected Button editBtn;
    @Inject
    private GroupTable<User> usersTable;

    @Inject
    private Notifications notifications;

    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private DataManager dataManager;


    @Subscribe
    public void onInit(InitEvent event) {

        getWindow().setCaption("Passwords dos Utilizadores");



        usersTable.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent -> {
                    User editedEntity = usersTable.getSingleSelected();
                    if (editedEntity != null)
                    {
                        screenBuilders.editor(usersTable)
                                .withScreenClass(UserPasswordEdit.class)
                                .withTransformation(colour -> dataManager.reload(colour, View.BASE))
                                .editEntity(editedEntity)
                                .show();
                    }

                }));

    }

    @Subscribe("editBtn")
    protected void onEditBtnClick(Button.ClickEvent event) {
                    User editedEntity = usersTable.getSingleSelected();
                    if (editedEntity != null)
                    {

                        screenBuilders.editor(usersTable)
                                .withScreenClass(UserPasswordEdit.class)
                                .withTransformation(colour -> dataManager.reload(colour, View.BASE))
                                .editEntity(editedEntity)
                                .show();
                    }


    }

    @Subscribe("usersTable")
    protected void onUsersTableSelection(Table.SelectionEvent<User> event) {
        User customerLine =  event.getSource().getSingleSelected();
        if (customerLine != null)
        {
            editBtn.setEnabled(true);
        }
    }


}