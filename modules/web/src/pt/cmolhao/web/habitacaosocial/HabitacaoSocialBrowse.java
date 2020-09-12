package pt.cmolhao.web.habitacaosocial;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
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
import javax.inject.Named;
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

    @Named("habitacaoSocialsTable.remove")
    protected RemoveAction<HabitacaoSocial> habitacaoSocialsTableRemove;
    @Inject
    protected TextField<String> hab_loc_id;
    @Inject
    private CollectionContainer<HabitacaoSocial> habitacaoSocialsDc;
    @Inject
    private CollectionLoader<HabitacaoSocial> habitacaoSocialsDl;
    @Inject
    private LookupPickerField<BlocosHabitacaoSocial> blocField;

    @Inject
    private Notifications notifications;

    //@Inject
    //private TextField<String> arrend_fiel;

    @Inject
    private DataManager dataManager;
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
    private LookupPickerField<Utente> utenteField;

    @Inject
    private Dialogs dialogs;

    private static final String FICHA_COMPLETA = "Ficha Completa: <br> ";
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


        habitacaoSocialsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(habitacaoSocialsTable)
                        .newEntity()
                        .withInitializer(customer -> {


                            if(hab_loc_id.getValue() != null)
                            {
                                customer.setLocalidade(hab_loc_id.getValue());
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
                                customer.setBloc(blocField.getValue());
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

                                    if(hab_loc_id.getValue() != null)
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

                            customer.setIdUtente(utenteField.getValue());

                        })
                        .withScreenClass(HabitacaoSocialEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

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


            // ID Utente
            if (utenteField.getValue() != null)
            {
                habitacaoSocialsDl.setParameter("idUtente",  utenteField.getValue().getId());
            }
            else
            {
                habitacaoSocialsDl.removeParameter("idUtente");
            }


            //Localidade
            if (hab_loc_id.getValue() != null )
            {
                habitacaoSocialsDl.setParameter("localidade",  "(?i)%" + hab_loc_id.getValue() + "%");
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

            habitacaoSocialsDl.load();

    }

    @Subscribe("reset_search_hab_social")
    public void onReset_search_hab_socialClick(Button.ClickEvent event) {
        blocField.setValue(null);
        utenteField.setValue(null);
        hab_loc_id.setValue(null);
        codPostalField.setValue(null);
        habitacaoSocialsDl.removeParameter("bloc");
        habitacaoSocialsDl.removeParameter("idUtente");
        habitacaoSocialsDl.removeParameter("codPostal");
        habitacaoSocialsDl.removeParameter("localidade");
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

    @Subscribe("habitacaoSocialsTable.remove")
    protected void onHabitacaoSocialsTableRemove(Action.ActionPerformedEvent event) {
        habitacaoSocialsTableRemove.setConfirmation(false);
        if (habitacaoSocialsTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção da habitação social")
                    .withMessage("Deve seleccionar pelo uma das habitações sociais")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            HabitacaoSocial user = habitacaoSocialsTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da habitação social número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da habitação social número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        habitacaoSocialsTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }



    }

}