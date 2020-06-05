package pt.cmolhao.web.localizacoes;

import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.FotosValencia;
import pt.cmolhao.entity.Localizacoes;
import pt.cmolhao.entity.ProjectosIntervencao;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.web.fotosvalencia.FotosValenciaEdit;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.List;

@UiController("cmolhao_Localizacoes.browse")
@UiDescriptor("localizacoes-browse.xml")
@LookupComponent("localizacoesesTable")
@LoadDataBeforeShow
public class LocalizacoesBrowse extends StandardLookup<Localizacoes> {
    @Inject
    private GroupTable<Localizacoes> localizacoesesTable;
    @Inject
    private LookupField<Valencias> idvalenciaField;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;
    @Inject
    private CollectionLoader<Localizacoes> localizacoesesDl;

    @Inject
    protected UiComponents uiComponents;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private LookupField linhasLocalizacoes;




    public com.haulmont.cuba.gui.components.Component generateValenciasDescricao(Localizacoes entity) {
        com.haulmont.cuba.gui.components.Label label = (com.haulmont.cuba.gui.components.Label) uiComponents.create(com.haulmont.cuba.gui.components.Label.NAME);
        if (entity.getIdvalencia() != null)
        {
            label.setValue(entity.getIdvalencia().getDescricaotecnica());
            return label;
        }
        return null;


    }


    @Subscribe
    protected void onInit(InitEvent event) {
        localizacoesesTable.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent -> {
                    Localizacoes customer = localizacoesesTable.getSingleSelected();
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


                    }
                }));


        localizacoesesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(localizacoesesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdvalencia(idvalenciaField.getValue());
                            if (idvalenciaField.getValue() != null)
                            {
                                try {
                                    String locationAddres = idvalenciaField.getValue().getMorada().replaceAll(" ", "%20");
                                    URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?sensor=false&address="+locationAddres+"&language=en&key=AIzaSyAQHab9s1jUhlfo2GFHmme8bXXugkKMvrA");
                                    try(InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {
                                        JsonObject obj = rdr.readObject();
                                        JsonArray results = obj.getJsonArray("results");
                                        JsonObject geoMetryObject, locations;
                                        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                                            geoMetryObject=result.getJsonObject("geometry");
                                            locations=geoMetryObject.getJsonObject("location");
                                            customer.setCoord(locations.get("lat").toString()+";"+locations.get("lng").toString());
                                        }
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        })
                        .withScreenClass(LocalizacoesEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

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
        linhasLocalizacoes.setOptionsList(list);



    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            map.put(item.getDescricaotecnica() + " " , item);
        }
        idvalenciaField.setOptionsMap(map);
    }

    @Subscribe("search_localizacoes")
    public void onSearch_localizacoesClick(com.haulmont.cuba.gui.components.Button.ClickEvent event) {
        // ID de valencia
        if (idvalenciaField.getValue() != null) {
            localizacoesesDl.setParameter("idvalencia",  idvalenciaField.getValue().getId());
        } else {
            localizacoesesDl.removeParameter("idvalencia");
        }

        localizacoesesDl.load();
    }

    @Subscribe("reset_search_localizacoes")
    public void onReset_search_localizacoesClick(com.haulmont.cuba.gui.components.Button.ClickEvent event) {
        idvalenciaField.setValue(null);
        linhasLocalizacoes.setValue(null);
        localizacoesesDl.removeParameter("idvalencia");
        localizacoesesDl.setMaxResults(0);
        localizacoesesDl.load();
    }

    @Subscribe("linhasLocalizacoes")
    public void onLinhasLocalizacoesValueChange(HasValue.ValueChangeEvent event) {

        if (event.getValue() != null)
        {
            localizacoesesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            localizacoesesDl.setMaxResults(0);
        }
        localizacoesesDl.load();

    }



}