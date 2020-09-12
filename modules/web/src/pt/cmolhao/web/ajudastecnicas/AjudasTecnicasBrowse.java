package pt.cmolhao.web.ajudastecnicas;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.AjudasTecnicas;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.util.*;

@UiController("cmolhao_AjudasTecnicas.browse")
@UiDescriptor("ajudas-tecnicas-browse.xml")
@LookupComponent("ajudasTecnicasesTable")
@LoadDataBeforeShow
public class AjudasTecnicasBrowse extends StandardLookup<AjudasTecnicas> {
    @Named("ajudasTecnicasesTable.remove")
    protected RemoveAction<AjudasTecnicas> ajudasTecnicasesTableRemove;
    @Inject
    private GroupTable<AjudasTecnicas> ajudasTecnicasesTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionLoader<AjudasTecnicas> ajudasTecnicasesDl;
    @Inject
    private LookupField<Valencias> idvalenciaField;
    @Inject
    private DateField<Date> datadisponivelField;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    private LookupField linhasAjudasTecnicas;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;

    @Inject
    private Dialogs dialogs;

    public Component generateValenciasDescricao(AjudasTecnicas entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdValencia() != null)
        {
            label.setValue(entity.getIdValencia().getDescricaotecnica());
            return label;
        }
        return null;
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Ajudas Técnicas");
        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            if (item != null) {
                map.put(item.getDescricaotecnica() + " " , item);
            }
        }
        idvalenciaField.setOptionsMap(map);
    }



    @Subscribe
    public void onInit(InitEvent event) {

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
        linhasAjudasTecnicas.setOptionsList(list);

        ajudasTecnicasesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(ajudasTecnicasesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdValencia(idvalenciaField.getValue());
                            if (idvalenciaField.getValue() != null)
                            {
                                Map<String, String> hash_map = new HashMap<>();
                                //String text = "";
                                String string_addr = "";
                                try {
                                    String locationAddres = idvalenciaField.getValue().getMorada().replaceAll(" ", "%20");
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
                                    customer.setLocalizacao(hash_map.get("locality"));
                                }
                                else
                                {
                                    customer.setLocalizacao(null);
                                }
                            }
                            customer.setDatadisponivel(datadisponivelField.getValue());
                        })
                        .withScreenClass(AjudasTecnicasEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("search_ajudas_tecnicas")
    public void onSearch_ajudas_tecnicasClick(Button.ClickEvent event) {
        // ID de valencia
        if (idvalenciaField.getValue() != null) {


            ajudasTecnicasesDl.setParameter("idValencia",  idvalenciaField.getValue().getId());
        } else {
            ajudasTecnicasesDl.removeParameter("idValencia");
        }

        // Data Disponivel

        if (datadisponivelField.getValue() != null)
        {
            ajudasTecnicasesDl.setParameter("datadisponivel",  datadisponivelField.getValue());
        } else {
            ajudasTecnicasesDl.removeParameter("datadisponivel");
        }

        ajudasTecnicasesDl.load();
    }

    @Subscribe("reset_search_ajudas_tecnicas")
    public void onReset_search_ajudas_tecnicasClick(Button.ClickEvent event) {
        idvalenciaField.setValue(null);
        datadisponivelField.setValue(null);
        ajudasTecnicasesDl.removeParameter("datadisponivel");
        ajudasTecnicasesDl.removeParameter("idValencia");
        ajudasTecnicasesDl.load();
    }

    @Subscribe("linhasAjudasTecnicas")
    public void onLinhasAjudasTecnicasValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            ajudasTecnicasesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            ajudasTecnicasesDl.setMaxResults(0);
        }
        ajudasTecnicasesDl.load();
    }

    @Subscribe("ajudasTecnicasesTable.remove")
    protected void onAjudasTecnicasesTableRemove(Action.ActionPerformedEvent event) {
        ajudasTecnicasesTableRemove.setConfirmation(false);
        if (ajudasTecnicasesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de ajudas técnicas")
                    .withMessage("Deve seleccionar pelo um das ajudas técnicas")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            AjudasTecnicas user = ajudasTecnicasesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da ajuda técnica número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da ajuda técnica número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        ajudasTecnicasesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}