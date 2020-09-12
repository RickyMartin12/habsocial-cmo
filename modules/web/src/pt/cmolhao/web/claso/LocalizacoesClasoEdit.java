package pt.cmolhao.web.claso;

import com.haulmont.cuba.core.global.MetadataTools;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.LocalizacoesClaso;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.entity.ValenciasClaso;
import pt.cmolhao.web.localizacoes.MapaLocalizoes;

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

@UiController("cmolhao_LocalizacoesClaso.edit")
@UiDescriptor("localizacoes-claso-edit.xml")
@EditedEntityContainer("localizacoesClasoDc")
@LoadDataBeforeShow
public class LocalizacoesClasoEdit extends StandardEditor<LocalizacoesClaso> {


    @Inject
    protected CollectionContainer<ValenciasClaso> valenciasDc;
    @Inject
    protected TextField<UUID> idLocalizacaoField;
    @Inject
    protected LookupPickerField<ValenciasClaso> idvalenciaField;
    @Inject
    protected TextField<String> coordField;

    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Button click_map;

    @Inject
    private Screens screens;
    @Inject
    private Notifications notifications;

    @Inject
    private MetadataTools metadataTools;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Localização - " + idLocalizacaoField.getValue());
        Map<String, ValenciasClaso> map = new HashMap<>();
        Collection<ValenciasClaso> customers = valenciasDc.getItems();
        for (ValenciasClaso item : customers) {
            map.put(item.getDescricaotecnica() + " " , item);
        }
        idvalenciaField.setOptionsMap(map);
    }

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

    @Subscribe("click_map")
    protected void onClick_mapClick(Button.ClickEvent event) {
        MapaLocalizacoesClaso screen = screenBuilders.screen(this)
                .withScreenClass(MapaLocalizacoesClaso.class)
                .withAfterCloseListener(afterCloseEvent -> {
                    MapaLocalizacoesClaso otherScreen = afterCloseEvent.getScreen();
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

    @Subscribe("idvalenciaField")
    protected void onIdvalenciaFieldValueChange(HasValue.ValueChangeEvent<ValenciasClaso> event) {
        if (event.getValue() != null)
        {
            if (event.getValue().getMorada() != null)
            {
                String idval = event.getValue().getMorada();
                String[] arrOfStr = idval.split("-", 2);
                locationData(arrOfStr[1]);
            }

        }
    }





}