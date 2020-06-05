package pt.cmolhao.web.valencias;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.TextInputField;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@UiController("cmolhao_Valencias.edit")
@UiDescriptor("valencias-edit.xml")
@EditedEntityContainer("valenciasDc")
@LoadDataBeforeShow
public class ValenciasEdit extends StandardEditor<Valencias> {

    @Inject
    protected DateField<Date> certdatainicioField;
    @Inject
    private Metadata metadata;
    @Inject
    private InstanceContainer<Valencias> valencias_Dc;
    @Inject
    private TextField<Integer> versionField;
    @Inject
    private Notifications notifications;
    @Inject
    private TextField<String> moradaField;
    @Inject
    private TextField<String> coordenadagpsField;

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
                    coordenadagpsField.setValue(rs);




                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Carregar os dados da designacao dos blocos de habitação social
    public void loadValencias()
    {
        Valencias order = metadata.create(Valencias.class);
        valencias_Dc.setItem(order);
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        versionField.setValue(1);
    }


    @Subscribe("moradaField")
    public void onMoradaFieldTextChange(TextInputField.TextChangeEvent event) {
        locationData(event.getText());
    }


}