package pt.cmolhao.web.claso;

import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.AjudasTecnicas;
import pt.cmolhao.entity.AjudasTecnicasClaso;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.entity.ValenciasClaso;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@UiController("cmolhao_AjudasTecnicasClaso.edit")
@UiDescriptor("ajudas-tecnicas-claso-edit.xml")
@EditedEntityContainer("ajudasTecnicasClasoDc")
@LoadDataBeforeShow
public class AjudasTecnicasClasoEdit extends StandardEditor<AjudasTecnicasClaso> {
    @Inject
    protected InstanceContainer<AjudasTecnicasClaso> ajudasTecnicasClasoDc;

    @Inject
    protected TextField<UUID> idAjudasTecnicasField;
    @Inject
    protected TextField<String> localizacaoField;
    @Inject
    protected LookupPickerField<ValenciasClaso> idValenciaField;
    @Inject
    protected CollectionContainer<ValenciasClaso> valenciasClasoDc;
    @Inject
    private DateField<Date> datadisponivelField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Ajudas Técnicas - " + idAjudasTecnicasField.getValue());
        Map<String, ValenciasClaso> map = new HashMap<>();
        Collection<ValenciasClaso> customers = valenciasClasoDc.getItems();
        for (ValenciasClaso item : customers) {
            if (item != null) {
                map.put(item.getDescricaotecnica() + " " , item);
            }
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
            localizacaoField.setValue(hash_map.get("locality"));

        }
        else
        {
            localizacaoField.setValue(null);
        }



    }


    @Subscribe("idValenciaField")
    protected void onIdValenciaFieldValueChange1(HasValue.ValueChangeEvent<ValenciasClaso> event) {
            if (event.getValue() != null) {
                locationData(event.getValue().getMorada());
            }
    }
}