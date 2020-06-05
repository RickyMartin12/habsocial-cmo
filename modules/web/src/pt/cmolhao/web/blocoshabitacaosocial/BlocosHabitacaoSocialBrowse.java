package pt.cmolhao.web.blocoshabitacaosocial;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import org.springframework.format.annotation.DateTimeFormat;
import pt.cmolhao.entity.BlocosHabitacaoSocial;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UiController("cmolhao_BlocosHabitacaoSocial.browse")
@UiDescriptor("blocos-habitacao-social-browse.xml")
@LookupComponent("blocosHabitacaoSocialsTable")
@LoadDataBeforeShow

public class BlocosHabitacaoSocialBrowse extends StandardLookup<BlocosHabitacaoSocial> {
    @Inject
    private CollectionLoader<BlocosHabitacaoSocial> blocosHabitacaoSocialsDl;
    //@Inject
    //private DateField<Date> anoDeConstrucaoField;
    @Inject
    private Notifications notifications;
    @Inject
    private LookupField bloco_hab_designacao_text;

    @Inject
    private DataManager dataManager;
    @Inject
    private TextField<String> anoCons;
    @Inject
    private LookupField linhasHabSocial;
    @Inject
    private Table<BlocosHabitacaoSocial> blocosHabitacaoSocialsTable;
    @Inject
    private ScreenBuilders screenBuilders;

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe
    public void onInit(InitEvent event)  {

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
        linhasHabSocial.setOptionsList(list);

        // Inserir novos dados caso que a tabela estaja vazia


        blocosHabitacaoSocialsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(blocosHabitacaoSocialsTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (anoCons.getValue() != null)
                            {
                                String dia = "01";
                                String mes = "01";
                                String ano = anoCons.getValue();
                                String data = dia +"/"+mes+"/"+ano;
                                try
                                {
                                    Date date_ano = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                                    customer.setAnoDeConstrucao(date_ano);
                                }
                                catch (ParseException e)
                                {
                                    notifications.create()
                                            .withCaption("Mensagem de erro: " + e.toString())
                                            .withType(Notifications.NotificationType.TRAY)
                                            .show();
                                }
                            }


                            if (bloco_hab_designacao_text.getValue() != null)
                            {
                                customer.setDesignacao(bloco_hab_designacao_text.getValue().toString());
                            }


                        })
                        .withScreenClass(BlocosHabitacaoSocialEdit.class)    // specific editor screen
                        .build()
                        .show()
        );





    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        // Bl - Bloco
        List<String> options_designacao_bloco_hab_social = new ArrayList<>();
        String queryString_hab_social_designacao = "select o.designacao as designacao from cmolhao_BlocosHabitacaoSocial o where o.designacao is not null group by o.designacao";
        ValueLoadContext valueLoadContextontext_hab_social_designacao = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString_hab_social_designacao));
        valueLoadContextontext_hab_social_designacao.addProperty("designacao");
        List<KeyValueEntity> resultList_hab_social_designacao = dataManager.loadValues(valueLoadContextontext_hab_social_designacao);

        for(KeyValueEntity entry : resultList_hab_social_designacao){
            options_designacao_bloco_hab_social.add(entry.getValue("designacao"));
        }

        bloco_hab_designacao_text.setOptionsList(options_designacao_bloco_hab_social);


    }




    @Subscribe("search_bloco_hab_social")
    public void onSearch_bloco_hab_socialClick(Button.ClickEvent event) throws Exception {
        // Ano de Construção
        /*if (anoDeConstrucaoField.getValue() != null) {
            Date date_ano = new SimpleDateFormat("yyyy-MM-dd").parse(anoDeConstrucaoField.getValue().toString());
            blocosHabitacaoSocialsDl.setParameter("anoDeConstrucao",  date_ano);
        } else {
            blocosHabitacaoSocialsDl.removeParameter("anoDeConstrucao");
        }*/

        // Designação da Morada do Bloco de Habitação Social
        if (bloco_hab_designacao_text.getValue() != null) {
            blocosHabitacaoSocialsDl.setParameter("designacao",  bloco_hab_designacao_text.getValue().toString());
        } else {
            blocosHabitacaoSocialsDl.removeParameter("designacao");
        }

        if (anoCons.getValue() != null) {
            if (isNumeric(anoCons.getValue()))
            {
                blocosHabitacaoSocialsDl.setParameter("anoCons", Integer.valueOf(anoCons.getValue()) );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do ano de construção</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }

        } else {
            blocosHabitacaoSocialsDl.removeParameter("anoCons");
        }

        blocosHabitacaoSocialsDl.load();
    }

    @Subscribe("reset_search_blocos_hab_social")
    public void onReset_search_blocos_hab_socialClick(Button.ClickEvent event) {
        anoCons.setValue(null);
        bloco_hab_designacao_text.setValue(null);
        linhasHabSocial.setValue(null);
        blocosHabitacaoSocialsDl.removeParameter("designacao");
        blocosHabitacaoSocialsDl.removeParameter("anoCons");
        blocosHabitacaoSocialsDl.setMaxResults(0);
        blocosHabitacaoSocialsDl.load();
    }

    @Subscribe("linhasHabSocial")
    public void onLinhasHabSocialValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            blocosHabitacaoSocialsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            blocosHabitacaoSocialsDl.setMaxResults(0);
        }
        blocosHabitacaoSocialsDl.load();
    }



    
}