package pt.cmolhao.web.localizacoes;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Localizacoes;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@UiController("cmolhao_Localizacoes.edit")
@UiDescriptor("localizacoes-edit.xml")
@EditedEntityContainer("localizacoesDc")
@LoadDataBeforeShow


public class LocalizacoesEdit extends StandardEditor<Localizacoes> {
    @Inject
    private TextField<String> coordField;

    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Button click_map;

    @Inject
    private Screens screens;
    @Inject
    private Notifications notifications;
    @Inject
    private LookupField<Valencias> idvalenciaField;
    @Inject
    private MetadataTools metadataTools;

    // -- Buscar a Morada
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
                    //log.info("Using Gerocode call - lat : lng value for "+ anyLocationSpec +" is - "+locations.get("lat")+" : "+locations.get("lng"));
                    //latLng = locations.get("lat").toString() + "," +locations.get("lng").toString();
                    /*notifications.create()
                            .withCaption(locations.get("lat").toString() + "," +locations.get("lng").toString())
                            .withType(Notifications.NotificationType.TRAY)
                            .show();*/
                    coordField.setValue(locations.get("lat").toString()+";"+locations.get("lng").toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

    }

    @Subscribe("click_map")
    public void onClick_mapClick(Button.ClickEvent event) {

        MapaLocalizoes screen = screenBuilders.screen(this)
                .withScreenClass(MapaLocalizoes.class)
                .withAfterCloseListener(afterCloseEvent -> {
                    MapaLocalizoes otherScreen = afterCloseEvent.getScreen();
                    otherScreen.setFancyMessage(coordField.getValue());
                    if (afterCloseEvent.closedWith(StandardOutcome.COMMIT)) {
                        String result = otherScreen.getResult();
                        coordField.setValue(result);
                    }
                })
                .build();
        screen.setFancyMessage(coordField.getValue());

        String coord = coordField.getValue();

        if (coord != null)
        {
            boolean p=coord.contains(";");
            if(p)
            {
                String[] arrOfStr = coord.split(";");
                String longitudade = arrOfStr[0];
                String latitude = arrOfStr[1];
                screen.setLongitude(longitudade);
                screen.setLatitude(latitude);
            }
            else
            {
                screen.setLongitude(coord);
                screen.setLatitude("0");
            }
        }

        screen.show();

    }



    @Subscribe(id = "localizacoesDc", target = Target.DATA_CONTAINER)
    public void onLocalizacoesDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<Localizacoes> event) {
        if (event.getValue() != null)
        {
            Object str = event.getValue() instanceof Entity
                    ? metadataTools.getInstanceName((Entity) event.getValue())
                    : event.getValue();

            String idval = str.toString();
            String[] arrOfStr = idval.split("-", 2);
            locationData(arrOfStr[1]);
        }
    }
}