package pt.cmolhao.web.moradores;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.HabitacaoSocial;
import pt.cmolhao.entity.Moradores;
import pt.cmolhao.entity.Utente;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@UiController("cmolhao_Moradores.edit")
@UiDescriptor("moradores-edit.xml")
@EditedEntityContainer("moradoresDc")
@LoadDataBeforeShow
public class MoradoresEdit extends StandardEditor<Moradores> {

    @Inject
    protected DateField<Date> dataInicioField;
    @Inject
    protected DateField<Date> dataFimField;
    @Inject
    protected LookupPickerField<HabitacaoSocial> habitacaoSocialField;
    @Inject
    protected LookupPickerField<Utente> utenteField;
    @Inject
    protected CollectionContainer<HabitacaoSocial> habitacaoSocialsDc;
    @Inject
    protected TextField<UUID> idMoradoresField;
    @Inject
    protected TextArea<String> moradaSocialField;
    @Inject
    private Notifications notifications;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Adicionar/Editar Morador - " + idMoradoresField.getValue());

        Date data_inicio = dataInicioField.getValue();
        Date date_fim = dataFimField.getValue();

        if (data_inicio != null && date_fim != null)
        {
            dataFimField.setRangeStart(data_inicio);
            dataInicioField.setRangeEnd(date_fim);
        }

        Map<String, HabitacaoSocial> map = new HashMap<>();
        Collection<HabitacaoSocial> customers = habitacaoSocialsDc.getItems();
        for (HabitacaoSocial item : customers) {
            if (item != null) {
                map.put("Habitação Social: ( " +  item.getId()+ " ) - " + item.getBloc().getDesignacao() , item);
            }
        }
        habitacaoSocialField.setOptionsMap(map);


    }

    @Subscribe("dataInicioField")
    protected void onDataInicioFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated())
        {
            if (event.getValue() != null)
            {
                dataFimField.setRangeStart(event.getValue());
            }
        }
    }

    @Subscribe("dataFimField")
    protected void onDataFimFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                dataInicioField.setRangeEnd(event.getValue());
            }
        }
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
                if (results.size() > 0)
                {
                    for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                        geoMetryObject=result.getJsonObject("geometry");
                        locations=geoMetryObject.getJsonObject("location");
                        //addMarker(Double.parseDouble(locations.get("lat").toString()), Double.parseDouble(locations.get("lng").toString()));
                        addressComponentsArray=result.getJsonArray("address_components");
                        string_addr = result.getString("formatted_address");
                        moradaSocialField.setValue(string_addr);
                    }
                }

            }
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }

        //if ()




    }

    @Subscribe("habitacaoSocialField")
    protected void onHabitacaoSocialFieldValueChange(HasValue.ValueChangeEvent<HabitacaoSocial> event) {
        if (event.isUserOriginated())
        {
            if (event.getValue() != null)
            {
                locationData(event.getValue().getBloc().getDesignacao());
            }
        }
    }

}