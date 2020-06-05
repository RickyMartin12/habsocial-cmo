package pt.cmolhao.web.users;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Set;

@UiController("User.browser")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
@LoadDataBeforeShow
public class UserBrowser extends StandardLookup<User> {
    @Inject
    private GroupTable<User> usersTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    protected DataManager dataManager;
    @Inject
    private Dialogs dialogs;
    @Named("usersTable.remove")
    private RemoveAction<User> usersTableRemove;


    @Subscribe
    public void onInit(InitEvent event) {

        getWindow().setCaption("Ver Utilizadores");
        usersTable.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent -> {
                    User editedEntity = usersTable.getSingleSelected();
                    if (editedEntity != null)
                    {
                        screenBuilders.editor(usersTable)
                                .withScreenClass(UserEdit.class)
                                .withTransformation(colour -> dataManager.reload(colour, View.BASE))
                                .editEntity(editedEntity)
                                .show();
                    }
                }));


    }

    @Subscribe("createBtn")
    public void onCreateBtnClick(Button.ClickEvent event) {
        screenBuilders.editor(usersTable)
                .newEntity()
                .withScreenClass(UserEditor.class)    // specific editor screen
                .build()
                .show();
    }

    @Subscribe("editBtn")
    public void onEditBtnClick(Button.ClickEvent event) {
        User editedEntity = usersTable.getSingleSelected();
        if (editedEntity != null)
        {
            screenBuilders.editor(usersTable)
                    .withScreenClass(UserEdit.class)
                    .withTransformation(colour -> dataManager.reload(colour, View.BASE))
                    .editEntity(editedEntity)
                    .show();
        }
    }

    public void send(String from,String password,String to,String sub,String msg){
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);
            message.setContent(msg, "text/html");
            //send message
            Transport.send(message);
            dialogs.createOptionDialog()
                    .withCaption("Sucesso")
                    .withMessage("A Remoção do utilizador foi feita com sucesso. Verifique o seu email.")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        } catch (MessagingException e) {dialogs.createOptionDialog()
                .withCaption("Erro")
                .withMessage(e.getMessage())
                .withActions(
                        new DialogAction(DialogAction.Type.CLOSE)
                )
                .show();}

    }

    private void sendMail ()
    {
        User user = usersTable.getSingleSelected();



    }


    @Subscribe("usersTable.remove")
    public void onUsersTableRemove(Action.ActionPerformedEvent event) {
        usersTableRemove.setConfirmation(false);
        //sendMail();
        if (usersTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do Utilizador")
                    .withMessage("Deve seleccionar pelo menos um dos utilizadores já criados para remover")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            User user = usersTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do utilizador - '"+user.getLogin()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do utilizador - '"+user.getLogin()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        usersTableRemove.execute();
                                        String username = "";
                                            if (user.getLogin() != null)
                                            {
                                                username = user.getLogin();
                                            }
                                            else
                                            {
                                                username = "";
                                            }

                                            String mensagem = "<h2>Remocao do Utilizador "+username+"</h2>" +
                                                    "<p>Foi removido o utilizador '"+username+"' na base de dados na Camara Municipal de Olhao - Habitacao Social" +
                                                    "<br>" +
                                                    "<p><b>Username:</b> "+username+"</p>" +
                                                    "<br>" +
                                                    "Camara Municipal de Olhao - Habitacao Social";
                                            String subject = "Remoção do Utilizador "+username;

                                            if (user.getEmail() != null)
                                            {
                                                String email = user.getEmail();

                                                send("ricardopeleira16@gmail.com","peleira2018",email,subject,mensagem);
                                            }
                                            else
                                            {
                                                send("ricardopeleira16@gmail.com","peleira2018","r.peleira@hotmail.com",subject,mensagem);
                                            }

                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

   


}

