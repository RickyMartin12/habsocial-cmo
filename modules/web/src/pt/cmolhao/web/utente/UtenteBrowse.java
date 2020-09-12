package pt.cmolhao.web.utente;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.*;
import pt.cmolhao.web.tecnico.TecnicoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@UiController("cmolhao_Utente.browse")
@UiDescriptor("utente-browse.xml")
@LookupComponent("utentesTable")
@LoadDataBeforeShow
public class UtenteBrowse extends StandardLookup<Utente> {
    @Inject
    protected LookupField linhasUtente;
    @Inject
    protected CollectionLoader<Utente> utentesDl;
    @Inject
    protected TextField<String> anoNascField;
    @Inject
    protected TextField<String> nomeField;
    @Inject
    protected GroupTable<Utente> utentesTable;
    @Inject
    protected LookupField estadoCivilField;
    @Inject
    protected LookupField paisOrigemField;
    @Inject
    protected TextField<String> num_cont_utente_id;
    @Named("utentesTable.remove")
    protected RemoveAction<Utente> utentesTableRemove;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private DataManager dataManager;
    @Inject
    private Notifications notifications;


    @Inject
    private Dialogs dialogs;

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Utentes");

        List<String> list_estado_civil = new ArrayList<>();
        list_estado_civil.add("Solteiro(a)");
        list_estado_civil.add("Casado(a)");
        list_estado_civil.add("Divorciado(a)");
        list_estado_civil.add("Viúvo(a)");
        list_estado_civil.add("Separado(a)");
        estadoCivilField.setOptionsList(list_estado_civil);

        List<String> list_paises = new ArrayList<>();
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale obj = new Locale("", countryCode);
            list_paises.add(obj.getDisplayCountry());
        }
        paisOrigemField.setOptionsList(list_paises);

    }

    @Subscribe
    protected void onInit(InitEvent event) {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(50);
        list.add(100);
        list.add(200);
        list.add(500);
        list.add(1000);
        list.add(2000);
        list.add(5000);
        list.add(10000);
        linhasUtente.setOptionsList(list);

        utentesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(utentesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (anoNascField.getValue() != null)
                            {
                                String dia = "01";
                                String mes = "01";
                                String ano = anoNascField.getValue();
                                String data = dia +"/"+mes+"/"+ano;
                                try
                                {
                                    Date date_ano = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                                    customer.setDataNasc(date_ano);
                                }
                                catch (ParseException e)
                                {
                                    notifications.create()
                                            .withCaption("Mensagem de erro: " + e.toString())
                                            .withType(Notifications.NotificationType.TRAY)
                                            .show();
                                }
                            }
                            if (num_cont_utente_id.getValue() != null)
                            {
                                customer.setNumContribuinte(num_cont_utente_id.getValue().toString());
                            }
                            if(nomeField.getValue() != null)
                            {
                                customer.setNome(nomeField.getValue());
                            }
                            if(paisOrigemField.getValue() != null)
                            {
                                customer.setPaisOrigem(paisOrigemField.getValue().toString());
                            }
                        })
                        .withScreenClass(UtenteEdit.class)    // specific editor screen
                        .build()
                        .show()
        );



    }

    @Subscribe("search_utente")
    protected void onSearch_utenteClick(Button.ClickEvent event) {
        if (anoNascField.getValue() != null) {
            if (isNumeric(anoNascField.getValue()))
            {
                utentesDl.setParameter("anoNasc", Integer.valueOf(anoNascField.getValue()) );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do ano de nascimento</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }

        } else {
            utentesDl.removeParameter("anoNasc");
        }

        if (num_cont_utente_id.getValue() != null) {
            if (isNumeric(num_cont_utente_id.getValue()))
            {
                utentesDl.setParameter("numContribuinte", "(?i)%" + Integer.valueOf(num_cont_utente_id.getValue()) + "%" );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do número contribuinte</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }

        } else {
            utentesDl.removeParameter("numContribuinte");
        }

        if (nomeField.getValue() != null) {
            utentesDl.setParameter("nome", "(?i)%" + nomeField.getValue() + "%");
        } else {
            utentesDl.removeParameter("nome");
        }

        if (estadoCivilField.getValue() != null)
        {
            utentesDl.setParameter("estadoCivil",  estadoCivilField.getValue().toString());
        }
        else
        {
            utentesDl.removeParameter("estadoCivil");
        }

        if (paisOrigemField.getValue() != null)
        {
            utentesDl.setParameter("paisOrigem",  paisOrigemField.getValue().toString());
        }
        else
        {
            utentesDl.removeParameter("paisOrigem");
        }

        utentesDl.load();
    }

    @Subscribe("reset_utente")
    protected void onReset_utenteClick(Button.ClickEvent event) {
        anoNascField.setValue(null);
        num_cont_utente_id.setValue(null);
        nomeField.setValue(null);
        estadoCivilField.setValue(null);
        paisOrigemField.setValue(null);
        utentesDl.removeParameter("nome");
        utentesDl.removeParameter("numContribuinte");
        utentesDl.removeParameter("anoNasc");
        utentesDl.removeParameter("estadoCivil");
        utentesDl.removeParameter("paisOrigem");
        utentesDl.load();
    }

    @Subscribe("linhasUtente")
    protected void onLinhasUtenteValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            utentesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            utentesDl.setMaxResults(0);
        }
        utentesDl.load();
    }

    @Subscribe("utentesTable.remove")
    protected void onUtentesTableRemove(Action.ActionPerformedEvent event) {
        utentesTableRemove.setConfirmation(false);
        if (utentesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do utente")
                    .withMessage("Deve seleccionar pelo um dos utentes")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Utente user = utentesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do utente número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do utente número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        utentesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }




}