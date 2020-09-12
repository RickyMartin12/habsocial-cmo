package pt.cmolhao.web.localizacoes;

import com.haulmont.charts.gui.components.map.MapViewer;
import com.haulmont.charts.gui.map.model.InfoWindow;
import com.haulmont.charts.gui.map.model.Marker;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.awt.*;

@UiController("cmolhao_MapaLocalizoes")
@UiDescriptor("mapa-localizoes.xml")
@DialogMode(forceDialog = true, width = "2000px", height = "1200px")
public class MapaLocalizoes extends Screen {


    @Inject
    private Button closeBtn;
    @Inject
    private TextField<String> messageLabel;

    @Inject
    private Notifications notifications;

    @Inject
    private Screens screens;
    @Inject
    private TextField<String> latitudeField;
    @Inject
    private TextField<String> longuitudeField;
    @Inject
    private MapViewer map;


    public void setFancyMessage(String message) {
        messageLabel.setValue(message);
    }

    public void setLatitude (String latitude)
    {
        latitudeField.setValue(latitude);
    }

    public void setLongitude(String longuitude)
    {
        longuitudeField.setValue(longuitude);
    }


    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    @Subscribe
    protected void onInit(InitEvent event) {


        // Carregar o mapa
        map.setCenter(map.createGeoPoint(37.03518, -7.83019));
        map.setZoom(12);
        map.setScrollWheelEnabled(false);
        // Adiocionar marker ao mapa
        map.addMapClickListener(mapClickEvent ->
                addMarker(mapClickEvent.getPosition().getLatitude(), mapClickEvent.getPosition().getLongitude()));
        map.addMarkerClickListener(markerClickEvent -> {
            Marker marker = markerClickEvent.getMarker();
            String caption = String.format("Ponto da Coordenada: %.2f, %.2f",
                    marker.getPosition().getLatitude(),
                    marker.getPosition().getLongitude());
            InfoWindow infoWindow = map.createInfoWindow(caption, marker);
            map.openInfoWindow(infoWindow);
            //coordField.setValue(marker.getPosition().getLatitude().toString());
        });
        map.addMarkerDragListener(markerDragEvent -> {
            Marker marker = markerDragEvent.getMarker();
            String caption = String.format("Ponto da Coordenada: %.2f, %.2f",
                    marker.getPosition().getLatitude(),
                    marker.getPosition().getLongitude());
            InfoWindow infoWindow = map.createInfoWindow(caption, marker);
            double latitude = marker.getPosition().getLatitude();
            double longitude = marker.getPosition().getLongitude();
            messageLabel.setValue(latitude+";"+longitude);
            longuitudeField.setValue(Double.toString(longitude));
            latitudeField.setValue(Double.toString(latitude));
        });


    }

    @Subscribe("closeBtn")
    public void onCloseBtnClick(Button.ClickEvent event) {
        result = messageLabel.getValue();
        close(StandardOutcome.COMMIT);
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
            String coord = messageLabel.getRawValue();
            getWindow().setIconFromSet(CubaIcon.MAP);
            getWindow().setCaption("Mapa - Coordenada: " + coord);

            if (coord != "")
                {
                    String[] arrOfStr = coord.split(";");
                    String longitudade = arrOfStr[0];
                    String latitude = arrOfStr[1];
                    addMarker(Double.parseDouble(longitudade), Double.parseDouble(latitude));
                }


    }

    // Adicionar uma dada localizacao
    private void addMarker(double latitude, double longitude) {
        map.clearMarkers();
        Marker marker = map.createMarker("Ponto da Coordenada: " + latitude + ";" + longitude, map.createGeoPoint(latitude, longitude), true);
        marker.setClickable(true);
        map.addMarker(marker);
        messageLabel.setValue(latitude+";"+longitude);
        longuitudeField.setValue(Double.toString(longitude));
        latitudeField.setValue(Double.toString(latitude));
    }



    

}