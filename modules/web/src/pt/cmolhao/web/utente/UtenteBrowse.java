package pt.cmolhao.web.utente;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.*;
import pt.cmolhao.web.tecnico.TecnicoEdit;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UiController("cmolhao_Utente.browse")
@UiDescriptor("utente-browse.xml")
@LookupComponent("utentesTable")
@LoadDataBeforeShow
public class UtenteBrowse extends StandardLookup<Utente> {
    @Inject
    protected Label<String> text_deficiente;
    @Inject
    protected Label<String> text_dependente;
    @Inject
    protected LookupField linhasUtente;
    @Inject
    protected CollectionLoader<Utente> utentesDl;
    @Inject
    protected LookupField<GrauEscolaridade> grauEscolaridadeField;
    @Inject
    protected LookupField<HabilitacoesLiterarias> habilitacoesField;
    @Inject
    protected LookupField<TipologiaFamiliar> idTipologiaFamiliarField;
    @Inject
    protected LookupField<TipoCivil> idTipoCivilField;
    @Inject
    protected TextField<String> anoNascField;
    @Inject
    protected CheckBox deficienteField;
    @Inject
    protected CheckBox dependenteField;
    @Inject
    protected LookupField niss_utn_id;
    @Inject
    protected TextField<String> nomeField;
    @Inject
    protected LookupField num_cont_utente_id;
    @Inject
    protected LookupField email_uten_id;
    @Inject
    protected GroupTable<Utente> utentesTable;
    @Inject
    protected LookupField<Profissao> profissaoField;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private DataManager dataManager;
    @Inject
    private Notifications notifications;

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        // Email do Utente
        List<String> options = new ArrayList<>();
        String queryString = "select o.email as email from cmolhao_Utente o where o.email is not null group by o.email";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("email");
        List<KeyValueEntity>  resultList = dataManager.loadValues(valueLoadContextontext);
        for(KeyValueEntity entry : resultList){
            options.add(entry.getValue("email"));
        }
        email_uten_id.setOptionsList(options);

        // Numero de Segurança Social do Utente

        List<String> optionsNISS = new ArrayList<>();
        String queryString_NISS = "select o.niss as niss from cmolhao_Utente o where o.niss is not null group by o.niss";
        ValueLoadContext valueLoadContextontextNISS = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString_NISS));
        valueLoadContextontextNISS.addProperty("niss");
        List<KeyValueEntity> resultListNISS = dataManager.loadValues(valueLoadContextontextNISS);

        for(KeyValueEntity entry : resultListNISS){
            optionsNISS.add(entry.getValue("niss"));
        }

        niss_utn_id.setOptionsList(optionsNISS);

        //Numero de Contribuinte do Utente

        List<String> optionsNumContribuinte = new ArrayList<>();
        String queryString_NumContribuinte = "select o.numContribuinte as numContribuinte from cmolhao_Utente o where o.numContribuinte is not null group by o.numContribuinte";
        ValueLoadContext valueLoadContextontextNumContribuinte = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString_NumContribuinte));
        valueLoadContextontextNumContribuinte.addProperty("numContribuinte");
        List<KeyValueEntity> resultListNumContribuinte = dataManager.loadValues(valueLoadContextontextNumContribuinte);

        for(KeyValueEntity entry : resultListNumContribuinte){
            optionsNumContribuinte.add(entry.getValue("numContribuinte"));
        }

        num_cont_utente_id.setOptionsList(optionsNumContribuinte);

    }

    @Subscribe
    protected void onInit(InitEvent event) {
        text_deficiente.setValue("Deficiente: ");
        text_dependente.setValue("Dependente: ");

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

                            customer.setGrauEscolaridade(grauEscolaridadeField.getValue());
                            customer.setHabilitacoes(habilitacoesField.getValue());
                            customer.setIdTipologiaFamiliar(idTipologiaFamiliarField.getValue());
                            customer.setIdTipoCivil(idTipoCivilField.getValue());
                            customer.setProfissao(profissaoField.getValue());

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
                            customer.setDeficiente(deficienteField.getValue());
                            customer.setDependente(dependenteField.getValue());
                            if (niss_utn_id.getValue() != null)
                            {
                                customer.setNiss(niss_utn_id.getValue().toString());
                            }
                            if (num_cont_utente_id.getValue() != null)
                            {
                                customer.setNumContribuinte(num_cont_utente_id.getValue().toString());
                            }
                            if(nomeField.getValue() != null)
                            {
                                customer.setNome(nomeField.getValue());
                            }
                            if(email_uten_id.getValue() != null)
                            {
                                customer.setEmail(email_uten_id.getValue().toString());
                            }
                        })
                        .withScreenClass(UtenteEdit.class)    // specific editor screen
                        .build()
                        .show()
        );



    }

    @Subscribe("search_utente")
    protected void onSearch_utenteClick(Button.ClickEvent event) {
        if (grauEscolaridadeField.getValue() != null)
        {
            utentesDl.setParameter("grauEscolaridade",  grauEscolaridadeField.getValue().getId());
        } else {
            utentesDl.removeParameter("grauEscolaridade");
        }

        if (habilitacoesField.getValue() != null)
        {
            utentesDl.setParameter("habilitacoes",  habilitacoesField.getValue().getId());
        } else {
            utentesDl.removeParameter("habilitacoes");
        }

        if (idTipologiaFamiliarField.getValue() != null)
        {
            utentesDl.setParameter("idTipologiaFamiliar",  idTipologiaFamiliarField.getValue().getId());
        } else {
            utentesDl.removeParameter("idTipologiaFamiliar");
        }

        if (idTipoCivilField.getValue() != null)
        {
            utentesDl.setParameter("idTipoCivil",  idTipoCivilField.getValue().getId());
        } else {
            utentesDl.removeParameter("idTipoCivil");
        }

        if (profissaoField.getValue() != null)
        {
            utentesDl.setParameter("profissao",  profissaoField.getValue().getId());
        } else {
            utentesDl.removeParameter("profissao");
        }

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

        if (deficienteField.getValue() )
        {
            utentesDl.setParameter("deficiente",  true);
        }
        else
        {
            utentesDl.removeParameter("deficiente");
        }


        if (dependenteField.getValue() )
        {
            utentesDl.setParameter("dependente",  true);
        }
        else
        {
            utentesDl.removeParameter("dependente");
        }

        if (niss_utn_id.getValue() != null)
        {
            utentesDl.setParameter("niss",  niss_utn_id.getValue().toString());
        }
        else
        {
            utentesDl.removeParameter("niss");
        }

        if (num_cont_utente_id.getValue() != null)
        {
            utentesDl.setParameter("numContribuinte",  num_cont_utente_id.getValue().toString());
        }
        else
        {
            utentesDl.removeParameter("numContribuinte");
        }

        if (nomeField.getValue() != null) {
            utentesDl.setParameter("nome", "(?i)%" + nomeField.getValue() + "%");
        } else {
            utentesDl.removeParameter("nome");
        }

        if (email_uten_id.getValue() != null)
        {
            utentesDl.setParameter("email",  email_uten_id.getValue().toString());
        }
        else
        {
            utentesDl.removeParameter("email");
        }
        if(profissaoField.getValue() != null)
        {
            utentesDl.setParameter("profissao",  profissaoField.getValue().getId());
        }
        else
        {
            utentesDl.removeParameter("profissao");
        }

        utentesDl.load();
    }

    @Subscribe("reset_utente")
    protected void onReset_utenteClick(Button.ClickEvent event) {
        grauEscolaridadeField.setValue(null);
        habilitacoesField.setValue(null);
        idTipologiaFamiliarField.setValue(null);
        idTipoCivilField.setValue(null);
        profissaoField.setValue(null);
        anoNascField.setValue(null);
        dependenteField.setValue(false);
        dependenteField.setValue(false);
        niss_utn_id.setValue(null);
        num_cont_utente_id.setValue(null);
        nomeField.setValue(null);
        email_uten_id.setValue(null);
        linhasUtente.setValue(null);
        profissaoField.setValue(null);
        utentesDl.setMaxResults(0);
        utentesDl.removeParameter("email");
        utentesDl.removeParameter("nome");
        utentesDl.removeParameter("numContribuinte");
        utentesDl.removeParameter("niss");
        utentesDl.removeParameter("dependente");
        utentesDl.removeParameter("deficiente");
        utentesDl.removeParameter("anoNasc");
        utentesDl.removeParameter("idTipoCivil");
        utentesDl.removeParameter("idTipologiaFamiliar");
        utentesDl.removeParameter("habilitacoes");
        utentesDl.removeParameter("grauEscolaridade");
        utentesDl.removeParameter("profissao");
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




}