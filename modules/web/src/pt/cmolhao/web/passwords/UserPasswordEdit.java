package pt.cmolhao.web.passwords;

import com.haulmont.cuba.core.app.EmailService;
import com.haulmont.cuba.core.global.EmailInfo;
import com.haulmont.cuba.core.global.EmailInfoBuilder;
import com.haulmont.cuba.core.global.PasswordEncryption;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

@UiController("UserPassword.edit")
@UiDescriptor("user-password-edit.xml")
@EditedEntityContainer("userDc")
@LoadDataBeforeShow
@DialogMode(forceDialog = true, width = "600px", height = "250px")
public class UserPasswordEdit extends StandardEditor<User> {

    @Inject
    private Label<UUID> idUser;


    @Inject
    private PasswordField passwordField;
    @Inject
    private PasswordField ConfpasswordField;
    @Inject
    private Label<String> encrp_passw;

    @Inject
    protected PasswordEncryption passwordEncryption;

    @Inject
    private Notifications notifications;
    @Inject
    private TextField<String> loginField;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Editar Password Utilizador '"+loginField.getValue()+"'");

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
                new javax.mail.Authenticator() {
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
                    .withCaption("Sucesso ao actualizar a password")
                    .withMessage("A password do utilizador "+loginField.getValue()+ " foi actualizado com sucesso")
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
        User user = getEditedEntity();
        String username = loginField.getValue();
        String password = passwordField.getValue();
        String mensagem = "<h2>Alteracao da Palavra Passe do Utilizador '"+username+"'</h2>" +
                "<br>" +
                "<p><b>Username:</b> "+username+"</p>" +
                "<p><b>Password:</b> "+password+"</p>" +
                "<br>" +
                "Camara Municipal de Olhao - Habitacao Social";
        String subject = "Alteração da Palavra Passe do Utilizador '"+username+"'";
        if (user.getEmail() != null)
        {
            String email = user.getEmail();
            send("ricardopeleira16@gmail.com","peleira2018",email,subject,mensagem);
        }
        else
        {
            send("ricardopeleira16@gmail.com","peleira2018","r.peleira@hotmail.com",subject,mensagem);
        }
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    private void onPreCommit(DataContext.PreCommitEvent event) {
        String password = passwordField.getValue();
        String passwordConfirmation = ConfpasswordField.getValue();
        // Verificação das passwords
            if (password != null && passwordConfirmation != null)
            {
                if (!password.equals(passwordConfirmation)) {
                    dialogs.createOptionDialog()
                            .withCaption("Erro das Passwords")
                            .withMessage("As Passwords do Utlilizador "+loginField.getValue()+" são Diferentes. Coloque as passwords iguais.")
                            .withActions(
                                    new DialogAction(DialogAction.Type.CLOSE)
                            )
                            .show();

                    event.preventCommit();

                }
                else
                {
                    // Gerar a Hash da password com o id do utilizador
                    if (idUser.getValue() != null && passwordField.getValue() != null)
                    {
                        //UUID uid = UUID.fromString(idUser.getValue());
                        UUID uid = UUID.fromString(idUser.getValue().toString());
                        String passwordHash = passwordEncryption.getPasswordHash(uid, passwordField.getValue());
                        encrp_passw.setValue(passwordHash);
                        sendMail();
                    }
                }
        }

    }




}