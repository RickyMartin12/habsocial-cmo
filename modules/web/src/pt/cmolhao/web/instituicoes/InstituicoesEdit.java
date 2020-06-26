package pt.cmolhao.web.instituicoes;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.TextInputField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Instituicoes;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@UiController("cmolhao_Instituicoes.edit")
@UiDescriptor("instituicoes-edit.xml")
@EditedEntityContainer("instituicoesDc")
@LoadDataBeforeShow
public class InstituicoesEdit extends StandardEditor<Instituicoes> {
    @Inject
    protected TextField<UUID> idInstituicaoField;
    @Inject
    private TextField<String> coordenadasgpsField;

    public static String convert(Double latitude, Double longitude){
        return convertLatitud(latitude) + ' ' + convertLongitude(longitude);
    }

    public static String convertLatitud(Double latitude){
        String result = "";
        if(latitude != null){
            String direction = "N";
            if(latitude < 0){
                direction = "S";
            }
            result = convert(latitude) + direction;
        }
        return result;
    }

    public static String convertLongitude(Double longitude){
        String result = "";
        if(longitude != null){
            String direction = "E";
            if(longitude < 0){
                direction = "O";
            }
            result = convert(longitude) + direction;
        }
        return result;
    }

    private static String convert(Double d){
        d = Math.abs(d);
        //degrees
        Integer i = d.intValue();
        String s = String.valueOf(i) + '°';
        //minutes
        d = d - i;
        d = d * 60;
        i = d.intValue();
        s = s + String.valueOf(i) + '\'';
        //seconds
        d = d - i;
        d = d * 60;
        i = (int)Math.round(d);
        s = s + String.valueOf(i) + '"';
        return s;
    }

    public void locationData(String morada) {
        try {
            String locationAddres = morada.replaceAll(" ", "%20");
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?sensor=false&address="+locationAddres+"&language=en&key=AIzaSyAQHab9s1jUhlfo2GFHmme8bXXugkKMvrA");
            try(InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {
                JsonObject obj = rdr.readObject();
                JsonArray results = obj.getJsonArray("results");
                JsonObject geoMetryObject, locations;
                for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                    geoMetryObject=result.getJsonObject("geometry");
                    locations=geoMetryObject.getJsonObject("location");
                    Double latitude = Double. valueOf(locations.get("lat").toString());
                    Double longitude = Double. valueOf(locations.get("lng").toString());
                    String rs = convert(latitude, longitude);
                    coordenadasgpsField.setValue(rs);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Subscribe("moradacompletaField")
    public void onMoradacompletaFieldTextChange(TextInputField.TextChangeEvent event) {
        locationData(event.getText());
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Instituição - " + idInstituicaoField.getValue());
    }


}