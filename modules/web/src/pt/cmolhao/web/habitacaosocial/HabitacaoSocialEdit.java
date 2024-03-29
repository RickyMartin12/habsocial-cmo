package pt.cmolhao.web.habitacaosocial;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.BlocosHabitacaoSocial;
import pt.cmolhao.entity.HabitacaoSocial;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import pt.cmolhao.entity.Moradores;
import pt.cmolhao.entity.Utente;

@UiController("cmolhao_HabitacaoSocial.edit")
@UiDescriptor("habitacao-social-edit.xml")
@EditedEntityContainer("habitacaoSocialDc")
@LoadDataBeforeShow
public class HabitacaoSocialEdit extends StandardEditor<HabitacaoSocial> {
    @Inject
    protected InstanceContainer<HabitacaoSocial> habitacaoSocialDc;
    @Inject
    protected TextField<Integer> valorPatrimonioField;
    @Inject
    protected TextArea<String> obsgeraisField;
    @Inject
    protected TextField<Integer> arrendField;
    @Inject
    protected TextField<String> tipoArrendamentoField;
    @Inject
    protected TextField<UUID> idHabSocialField;
    @Inject
    protected Table<Moradores> moradoresesTable;
    @Named("moradoresesTable.remove")
    protected RemoveAction<Moradores> moradoresesTableRemove;

    @Inject
    private Metadata metadata;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    private Notifications notifications;

    /*@Inject
    private MapViewer map;*/
    @Inject
    private TextField<String> coordField;

    @Inject
    private Dialogs dialogs;
    @Inject
    private LookupPickerField<BlocosHabitacaoSocial> blocField;
    @Inject
    private CollectionContainer<BlocosHabitacaoSocial> customersDc;
    private static final String HTML_MAPS = "<h3><img src='https://cdn.onlinewebfonts.com/svg/img_467222.png' width='20' height='20'>&nbsp; Localizacao de mapas da morada na habitação social</h3>\n";
    @Inject
    private CollectionContainer<Utente> UtentesDC;
    @Inject
    private TextArea<String> ruaField;
    @Inject
    private TextField<String> blField;
    @Inject
    private TextField<String> localidadeField;
    @Inject
    private TextField<String> freguesiaField;
    @Inject
    private TextArea<String> sitoLugarField;
    @Inject
    private TextField<String> codPostalField;


    // -- Buscar a Morada
    public void locationData(String morada) {
        Map<String, String> hash_map = new HashMap<>();
        //String text = "";
        String string_addr = "";
        try {
            String locationAddres = morada.replaceAll(" ", "%20");
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+locationAddres+"&location_type=ROOFTOP&result_type=street_address&key=AIzaSyAQHab9s1jUhlfo2GFHmme8bXXugkKMvrA");
            try(InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {
                JsonObject obj = rdr.readObject();
                JsonArray results = obj.getJsonArray("results");
                JsonObject geoMetryObject, locations, long_name;
                JsonArray addressComponentsArray;
                if (results.size() > 0)
                {
                    for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                        geoMetryObject=result.getJsonObject("geometry");
                        locations=geoMetryObject.getJsonObject("location");
                        coordField.setValue(locations.get("lat").toString()+";"+locations.get("lng").toString());
                        //addMarker(Double.parseDouble(locations.get("lat").toString()), Double.parseDouble(locations.get("lng").toString()));
                        addressComponentsArray=result.getJsonArray("address_components");
                        string_addr = result.getString("formatted_address");
                        sitoLugarField.setValue(string_addr);
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
                    coordField.setValue(null);
                    sitoLugarField.setValue(null);
                }

            }
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }

        if (!hash_map.isEmpty() )
        {
            ruaField.setValue(hash_map.get("route"));
            freguesiaField.setValue(hash_map.get("locality"));
            localidadeField.setValue(hash_map.get("locality"));
            codPostalField.setValue(hash_map.get("postal_code"));

            if(freguesiaField.getValue().equals("Quelfes") || freguesiaField.getValue().equals("Pechão") ||
                    freguesiaField.getValue().equals("Olhão") || freguesiaField.getValue().equals("Fuseta") ||
                    freguesiaField.getValue().equals("Moncarapacho"))
            {
                tipoArrendamentoField.setValue("Reside no Concelho");
            }
            else
            {
                tipoArrendamentoField.setValue("Não reside no Concelho");
            }

        }
        else
        {
            ruaField.setValue(null);
            freguesiaField.setValue(null);
            localidadeField.setValue(null);
            codPostalField.setValue(null);
        }

        //if ()




    }

    public static boolean isValidPostalCode(String str) {
        return str != null && str.matches("\\d{4}(-\\d{3})?");
    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Adicionar/Editar Habitação Social - " + idHabSocialField.getValue());

        if (blocField.getValue() != null)
        {
            locationData(blocField.getValue().getDesignacao());
            /*String coord = coordField.getRawValue();
            if (coord != "")
            {
                String[] arrOfStr = coord.split(";");
                String longitudade = arrOfStr[0];
                String latitude = arrOfStr[1];
                //addMarker(Double.parseDouble(longitudade), Double.parseDouble(latitude));
                coordField.setValue(Double.parseDouble(longitudade)+";"+Double.parseDouble(latitude));
            }*/
        }

        codPostalField.addValidator(value -> {
            if (value != null)
            {
                    if (!isValidPostalCode(value))
                    {
                        throw new ValidationException("O Código postal de portugal não é válido");
                    }
            }

        });
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }


    @Subscribe("blocField")
    protected void onBlocFieldValueChange(HasValue.ValueChangeEvent<BlocosHabitacaoSocial> event) {
        if (event.isUserOriginated())
        {
            if (event.getValue() != null)
            {
                locationData(event.getValue().getDesignacao());
            }

        }
    }

    @Subscribe("moradoresesTable.remove")
    protected void onMoradoresesTableRemove(Action.ActionPerformedEvent event) {
        moradoresesTableRemove.setConfirmation(false);
        if (moradoresesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção dos moradores")
                    .withMessage("Deve seleccionar pelo um dos moradores")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Moradores user = moradoresesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do morador número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do morador número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        moradoresesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


    // Adicionar uma dada localizacao
    /*private void addMarker(double latitude, double longitude) {
        map.clearMarkers();
        Marker marker = map.createMarker("Ponto da Coordenada: ", map.createGeoPoint(latitude, longitude), true);
        marker.setClickable(true);
        map.addMarker(marker);
        coordField.setValue(latitude+";"+longitude);
    }*/
















}