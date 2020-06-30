package pt.cmolhao.web.habitacaosocial;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.BlocosHabitacaoSocial;
import com.haulmont.cuba.gui.Notifications.NotificationType;
import pt.cmolhao.entity.HabitacaoSocial;
import pt.cmolhao.entity.Utente;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.*;
import java.util.List;

@UiController("cmolhao_HabitacaoSocial.browse")
@UiDescriptor("habitacao-social-browse.xml")
@LookupComponent("habitacaoSocialsTable")
@LoadDataBeforeShow
public class HabitacaoSocialBrowse extends StandardLookup<HabitacaoSocial> {

    @Inject
    protected LookupField tipoArrendamentoField;
    @Inject
    private CollectionContainer<HabitacaoSocial> habitacaoSocialsDc;
    @Inject
    private CollectionLoader<HabitacaoSocial> habitacaoSocialsDl;
    @Inject
    private LookupField<BlocosHabitacaoSocial> blocField;

    @Inject
    private Notifications notifications;

    //@Inject
    //private TextField<String> arrend_fiel;
    @Inject
    private LookupField luke_hab;

    @Inject
    private DataManager dataManager;
    @Inject
    private LookupField hab_bloco;
    @Inject
    private LookupField linhasHabSocial;
    @Inject
    private GroupTable<HabitacaoSocial> habitacaoSocialsTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    private LookupField<Utente> utenteField;
    @Inject
    private Label<String> text_fichaCompleta;

    private static final String FICHA_COMPLETA = "Ficha Completa: <br> ";
    @Inject
    private CheckBox fichaCompletaField;
    @Inject
    private LookupField hab_freg_id;
    @Inject
    private LookupField hab_loc_id;
    @Inject
    private TextField<String> codPostalField;


    public Component generateValorPatrimonio(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getValorPatrimonio() != null)
        {
            label.setValue(entity.getValorPatrimonio() + " €");
            return label;
        }
        return null;
    }

    public Component generateRendimentoAnual(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getRendimentoAnual() != null)
        {
            label.setValue(entity.getRendimentoAnual() + " €");
            return label;
        }
        return null;
    }


    @Subscribe
    public void onInit(InitEvent event) {
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

        List<String> list_tipo_arrendamento = new ArrayList<>();
        list_tipo_arrendamento.add("Reside no Concelho");
        list_tipo_arrendamento.add("Não reside no Concelho");
        tipoArrendamentoField.setOptionsList(list_tipo_arrendamento);

        habitacaoSocialsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(habitacaoSocialsTable)
                        .newEntity()
                        .withInitializer(customer -> {

                            if (luke_hab.getValue() != null)
                            {
                                customer.setArrend(Integer.valueOf(luke_hab.getValue().toString()));
                            }
                            if (hab_bloco.getValue() != null)
                            {
                                customer.setBl(hab_bloco.getValue().toString());
                            }
                            customer.setBloc(blocField.getValue());
                            customer.setIdUtente(utenteField.getValue());
                            customer.setFichaCompleta(fichaCompletaField.getValue());
                            if(hab_freg_id.getValue() != null)
                            {
                                customer.setFreguesia(hab_freg_id.getValue().toString());
                            }
                            if(hab_loc_id.getValue() != null)
                            {
                                customer.setLocalidade(hab_loc_id.getValue().toString());
                                if(hab_loc_id.getValue().equals("Quelfes") || hab_loc_id.getValue().equals("Pechão") ||
                                        hab_loc_id.getValue().equals("Olhão") || hab_loc_id.getValue().equals("Fuseta") ||
                                        hab_loc_id.getValue().equals("Moncarapacho"))
                                {
                                    customer.setTipoArrendamento("Reside no Concelho");
                                }
                                else
                                {
                                    customer.setTipoArrendamento("Não reside no Concelho");
                                }
                            }
                            if(codPostalField.getValue() != null)
                            {
                                customer.setCodPostal(codPostalField.getValue());
                            }
                            if (blocField.getValue() != null)
                            {
                                Map<String, String> hash_map = new HashMap<>();
                                //String text = "";
                                String string_addr = "";
                                try {
                                    String locationAddres = blocField.getValue().getDesignacao().replaceAll(" ", "%20");
                                    URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?sensor=false&address="+locationAddres+"&language=en&key=AIzaSyAQHab9s1jUhlfo2GFHmme8bXXugkKMvrA");

                                    try(InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {
                                        JsonObject obj = rdr.readObject();
                                        JsonArray results = obj.getJsonArray("results");
                                        JsonObject geoMetryObject, locations;
                                        JsonArray addressComponentsArray;
                                        if (results.size() > 0)
                                        {
                                            for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                                                geoMetryObject=result.getJsonObject("geometry");
                                                locations=geoMetryObject.getJsonObject("location");
                                                customer.setCoord(locations.get("lat").toString()+";"+locations.get("lng").toString());
                                                addressComponentsArray=result.getJsonArray("address_components");
                                                string_addr = result.getString("formatted_address");
                                                customer.setSitoLugar(string_addr);
                                                for (JsonObject addressComponentsArray_result : addressComponentsArray.getValuesAs(JsonObject.class)) {
                                                    JsonArray types_array = addressComponentsArray_result.getJsonArray("types");
                                                    String vale = addressComponentsArray_result.get("long_name").toString().replace("\"", "");
                                                    String types_a = types_array.getString(0);
                                                    hash_map.put(types_a, vale);
                                                }
                                            }
                                        }
                                        else
                                        {
                                            customer.setCoord(null);
                                            customer.setSitoLugar(null);
                                        }


                                    }
                                } catch (IOException e) {
                                    //throw new RuntimeException(e);
                                }

                                if (!hash_map.isEmpty())
                                {
                                    customer.setRua(hash_map.get("route"));
                                    customer.setFreguesia(hash_map.get("route"));
                                    customer.setLocalidade(hash_map.get("locality"));
                                    customer.setCodPostal(hash_map.get("postal_code"));

                                    if (hab_loc_id.getValue() != null)
                                    {
                                        if(hab_loc_id.getValue().equals("Quelfes") || hab_loc_id.getValue().equals("Pechão") ||
                                                hab_loc_id.getValue().equals("Olhão") || hab_loc_id.getValue().equals("Fuseta") ||
                                                hab_loc_id.getValue().equals("Moncarapacho"))
                                        {
                                            customer.setTipoArrendamento("Reside no Concelho");
                                        }
                                        else
                                        {
                                            customer.setTipoArrendamento("Não reside no Concelho");
                                        }
                                    }
                                }
                                else
                                {
                                    customer.setRua(null);
                                    customer.setFreguesia(null);
                                    customer.setLocalidade(null);
                                    customer.setCodPostal(null);
                                }

                            }

                        })
                        .withScreenClass(HabitacaoSocialEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

        habitacaoSocialsTable.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent -> {
                    HabitacaoSocial customer = habitacaoSocialsTable.getSingleSelected();
                    if (customer != null) {
                        String coord = customer.getCoord();
                        if (coord != null)
                        {
                            String[] arrOfStr = coord.split(";");
                            String longitudade = arrOfStr[0];
                            String latitude = arrOfStr[1];
                            try {
                                Desktop.getDesktop().browse(new URL("https://www.google.com/maps/search/?api=1&query="+longitudade+","+latitude+"").toURI());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            notifications.create()
                                    .withCaption("<i>Deve introduzir as coordenadas da morada no respectivo dado na habitação social</i>")
                                    .withType(NotificationType.WARNING)
                                    .withContentMode(ContentMode.HTML)
                                    .show();
                        }

                    }
                }));

        text_fichaCompleta.setHtmlEnabled(true);
        text_fichaCompleta.setHtmlSanitizerEnabled(true);
        text_fichaCompleta.setValue(FICHA_COMPLETA);

        
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Listar Habitação Social");
        // Arrend - Renda
        List<Integer> options = new ArrayList<>();
        String queryString = "select o.arrend as arrend from cmolhao_HabitacaoSocial o where o.arrend is not null group by o.arrend";
        ValueLoadContext valueLoadContextontext = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString));
        valueLoadContextontext.addProperty("arrend");
        List<KeyValueEntity>  resultList = dataManager.loadValues(valueLoadContextontext);
        for(KeyValueEntity entry : resultList){
            options.add((Integer)entry.getValue("arrend"));
        }
        luke_hab.setOptionsList(options);

        // Bl - Bloco
        List<String> optionsBl = new ArrayList<>();
        String queryString_Bl = "select o.bl as bl from cmolhao_HabitacaoSocial o where o.bl is not null group by o.bl";
        ValueLoadContext valueLoadContextontextBl = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString_Bl));
        valueLoadContextontextBl.addProperty("bl");
        List<KeyValueEntity> resultListBl = dataManager.loadValues(valueLoadContextontextBl);

        for(KeyValueEntity entry : resultListBl){
            optionsBl.add(entry.getValue("bl"));
        }

        hab_bloco.setOptionsList(optionsBl);


        // Freguesia

        List<String> optionsFreguesia = new ArrayList<>();
        String queryString_Freguesia = "select o.freguesia as freguesia from cmolhao_HabitacaoSocial o where o.freguesia is not null group by o.freguesia";
        ValueLoadContext valueLoadContextontextFreguesia = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString_Freguesia));
        valueLoadContextontextFreguesia.addProperty("freguesia");
        List<KeyValueEntity> resultListFreguesia = dataManager.loadValues(valueLoadContextontextFreguesia);

        for(KeyValueEntity entry : resultListFreguesia){
            optionsFreguesia.add(entry.getValue("freguesia"));
        }

        hab_freg_id.setOptionsList(optionsFreguesia);


        // Localidade

        List<String> optionsLocalidade = new ArrayList<>();
        String queryString_Localidade = "select o.localidade as localidade from cmolhao_HabitacaoSocial o where o.localidade is not null group by o.localidade";
        ValueLoadContext valueLoadContextontextLocalidade = ValueLoadContext.create()
                .setQuery(ValueLoadContext.createQuery(queryString_Localidade));
        valueLoadContextontextLocalidade.addProperty("localidade");
        List<KeyValueEntity> resultListLocalidade = dataManager.loadValues(valueLoadContextontextLocalidade);

        for(KeyValueEntity entry : resultListLocalidade){
            optionsLocalidade.add(entry.getValue("localidade"));
        }

        hab_loc_id.setOptionsList(optionsLocalidade);

    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    public static boolean isValidPostalCode(String str) {
        return str != null && str.matches("\\d{4}(-\\d{3})?");
    }

    @Subscribe("click")
    public void onClickClick(Button.ClickEvent event) {

            // Bloc de Designacao Id
            if (blocField.getValue() != null) {
                habitacaoSocialsDl.setParameter("bloc",  blocField.getValue().getId());
            } else {
                habitacaoSocialsDl.removeParameter("bloc");
            }

            // Arrend
            if (luke_hab.getValue() != null) {
                if (isNumeric(luke_hab.getValue().toString()))
                {
                    habitacaoSocialsDl.setParameter("arrend", Integer.valueOf(luke_hab.getValue().toString()) );
                }
                else
                {
                    notifications.create()
                            .withCaption("<code>Erro ao atribuir string do numero de renda</code>")
                            .withDescription("<u>Devera introduzir um numero inteiro</u>")
                            .withType(NotificationType.ERROR)
                            .withContentMode(ContentMode.HTML)
                            .show();
                }

            } else {
                habitacaoSocialsDl.removeParameter("arrend");
            }

            // Bloco
            if (hab_bloco.getValue() != null) {
                habitacaoSocialsDl.setParameter("bl",  hab_bloco.getValue().toString());
            } else {
                habitacaoSocialsDl.removeParameter("bl");
            }

            // ID Utente
            if (utenteField.getValue() != null)
            {
                habitacaoSocialsDl.setParameter("idUtente",  utenteField.getValue().getId());
            }
            else
            {
                habitacaoSocialsDl.removeParameter("idUtente");
            }

            // Ficha Completa
            if (fichaCompletaField.getValue() )
            {
                habitacaoSocialsDl.setParameter("fichaCompleta",  true);
            }
            else
            {
                habitacaoSocialsDl.removeParameter("fichaCompleta");
            }

            // Freguesia
            if (hab_freg_id.getValue() != null )
            {
                habitacaoSocialsDl.setParameter("freguesia",  hab_freg_id.getValue().toString());
            }
            else
            {
                habitacaoSocialsDl.removeParameter("freguesia");
            }

            //Localidade
            if (hab_loc_id.getValue() != null )
            {
                habitacaoSocialsDl.setParameter("localidade",  hab_loc_id.getValue().toString());
            }
            else
            {
                habitacaoSocialsDl.removeParameter("localidade");
            }

            // Codigo Postal
            if (codPostalField.getValue() != null)
            {
                if (isValidPostalCode(codPostalField.getValue()))
                {
                    habitacaoSocialsDl.setParameter("codPostal", "(?i)%" + codPostalField.getValue() + "%");
                }
                else
                {
                    notifications.create()
                            .withCaption("<code>Erro ao atribuir o código postal</code>")
                            .withDescription("<u>Devera introduzir o código postal válido</u>")
                            .withType(NotificationType.ERROR)
                            .withContentMode(ContentMode.HTML)
                            .show();
                }
            }
            else
            {
                habitacaoSocialsDl.removeParameter("codPostal");
            }

        //Localidade
        if (tipoArrendamentoField.getValue() != null )
        {
            habitacaoSocialsDl.setParameter("tipoArrendamento",  tipoArrendamentoField.getValue().toString());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("tipoArrendamento");
        }

            habitacaoSocialsDl.load();

    }

    @Subscribe("reset_search_hab_social")
    public void onReset_search_hab_socialClick(Button.ClickEvent event) {
        blocField.setValue(null);
        luke_hab.setValue(null);
        hab_bloco.setValue(null);
        linhasHabSocial.setValue(null);
        utenteField.setValue(null);
        fichaCompletaField.setValue(false);
        hab_freg_id.setValue(null);
        hab_loc_id.setValue(null);
        codPostalField.setValue(null);
        tipoArrendamentoField.setValue(null);
        habitacaoSocialsDl.removeParameter("bloc");
        habitacaoSocialsDl.removeParameter("arrend");
        habitacaoSocialsDl.removeParameter("bl");
        habitacaoSocialsDl.removeParameter("idUtente");
        habitacaoSocialsDl.removeParameter("fichaCompleta");
        habitacaoSocialsDl.removeParameter("codPostal");
        habitacaoSocialsDl.removeParameter("localidade");
        habitacaoSocialsDl.removeParameter("freguesia");
        habitacaoSocialsDl.removeParameter("tipoArrendamento");
        habitacaoSocialsDl.setMaxResults(0);
        habitacaoSocialsDl.load();



    }

    @Subscribe("linhasHabSocial")
    public void onLinhasHabSocialValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            habitacaoSocialsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            habitacaoSocialsDl.setMaxResults(0);
        }
        habitacaoSocialsDl.load();
    }

}