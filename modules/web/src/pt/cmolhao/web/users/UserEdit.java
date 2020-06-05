package pt.cmolhao.web.users;

import com.haulmont.cuba.core.global.PasswordEncryption;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;
import pt.cmolhao.entity.FotosValencia;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@UiController("User.Edit")
@UiDescriptor("user-edit-user.xml")
@EditedEntityContainer("userDc")
@LoadDataBeforeShow
public class UserEdit extends StandardEditor<User> {
    @Inject
    protected UiComponents uiComponents;
    @Inject
    private CollectionContainer<Group> GroupUsersDc;
    @Inject
    private LookupField<Group> groupField;
    @Inject
    private TextField<String> loginField;
    @Inject
    private TextField<String> emailField;
    @Inject
    private TextField<String> nameField;
    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Editar Utl. '"+loginField.getValue()+"' ");


        Map<String, Group> map = new HashMap<>();
        Collection<Group> customers = GroupUsersDc.getItems();
        for (Group item : customers) {

            if (item.getName().equals("Company"))
            {
                map.put("Companhia Empresarial" + " " , item);
            }
            else
            {
                map.put(item.getName() + " " , item);
            }

        }
        groupField.setOptionsMap(map);
    }

    public Component generateRoleName(UserRole entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getRoleName() != null)
        {
            if (entity.getRoleName().equals("system-minimal"))
            {
                label.setValue("Acesso minimo ao Sistema");
            }
            else if (entity.getRoleName().equals("system-reports-minimal"))
            {
                label.setValue("Acesso Minimo de Gestão de relatórios ao Sistema");
            }
            else if (entity.getRoleName().equals("system-full-access"))
            {
                label.setValue("Acesso Administrativo (Admin)");
            }

            return label;
        }
        return null;
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
                    .withMessage("A Mudança do Username doi efectuado com sucesso. Verifique no seu email")
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
        String username = loginField.getValue();

        String nome_utilizador = "";

        if (nameField.getValue() != null)
        {
            nome_utilizador = nameField.getValue();
        }
        else
        {
            nome_utilizador = "";
        }

        String mensagem = "<h2>Mudanca de nome do utilizador - '"+username+"'</h2>" +
                "<br>" +
                "<p><b>Username:</b> "+username+"</p>" +
                "<p><b>Nome Completo:</b> " +nome_utilizador+"</p>" +
                "<br>" +
                "Camara Municipal de Olhao - Habitacao Social";
        String subject = "Mudanca de nome do utilizador - '"+username+"'";

        if (emailField.getValue() != null)
        {
            String email = emailField.getValue();

            send("ricardopeleira16@gmail.com","peleira2018",email,subject,mensagem);
        }
        else
        {
            send("ricardopeleira16@gmail.com","peleira2018","r.peleira@hotmail.com",subject,mensagem);
        }

    }

    @Subscribe(target = Target.DATA_CONTEXT)
    private void onPreCommit(DataContext.PreCommitEvent event) {
        sendMail();
    }






}