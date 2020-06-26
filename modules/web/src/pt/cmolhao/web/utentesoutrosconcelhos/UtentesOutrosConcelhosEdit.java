package pt.cmolhao.web.utentesoutrosconcelhos;

import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.UtentesOutrosConcelhos;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.haulmont.cuba.core.entity.Entity;

@UiController("cmolhao_UtentesOutrosConcelhos.edit")
@UiDescriptor("utentes-outros-concelhos-edit.xml")
@EditedEntityContainer("utentesOutrosConcelhosDc")
@LoadDataBeforeShow
public class UtentesOutrosConcelhosEdit extends StandardEditor<UtentesOutrosConcelhos> {
    @Inject
    protected LookupField<Valencias> idValenciaField;
    @Inject
    protected CollectionContainer<Valencias> valenciasDc;
    @Inject
    protected TextField<String> concelhoField;
    @Inject
    protected TextField<String> freguesiaField;
    @Inject
    protected InstanceContainer<UtentesOutrosConcelhos> utentesOutrosConcelhosDc;
    @Inject
    protected TextField<UUID> idUtenteOutrosConcelhosField;
    @Inject
    private MetadataTools metadataTools;
    @Inject
    private Notifications notifications;

    @Inject
    private DataContext dataContext;

    @Inject
    private Dialogs dialogs;



    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar utente de outro concelho - " + idUtenteOutrosConcelhosField.getValue());
        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            map.put(item.getDescricaotecnica() + " " , item);
        }
        idValenciaField.setOptionsMap(map);
    }


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
                for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                    geoMetryObject=result.getJsonObject("geometry");
                    locations=geoMetryObject.getJsonObject("location");

                    addressComponentsArray=result.getJsonArray("address_components");
                    string_addr = result.getString("formatted_address");
                    for (JsonObject addressComponentsArray_result : addressComponentsArray.getValuesAs(JsonObject.class)) {
                        JsonArray types_array = addressComponentsArray_result.getJsonArray("types");
                        String vale = addressComponentsArray_result.get("long_name").toString().replace("\"", "");
                        String types_a = types_array.getString(0);
                        hash_map.put(types_a, vale);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!hash_map.isEmpty() )
        {
            freguesiaField.setValue(hash_map.get("locality"));
            concelhoField.setValue(hash_map.get("locality"));

        }
        else
        {
            freguesiaField.setValue(null);
            concelhoField.setValue(null);
        }



    }




    @Subscribe("idValenciaField")
    protected void onIdValenciaFieldValueChange(HasValue.ValueChangeEvent<Valencias> event) {
        if (event.isUserOriginated())
        {
            if (event.getValue() != null) {
                locationData(event.getValue().getMorada());
            }
        }

    }





}