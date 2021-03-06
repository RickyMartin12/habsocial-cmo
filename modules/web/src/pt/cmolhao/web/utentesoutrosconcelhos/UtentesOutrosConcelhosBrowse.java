package pt.cmolhao.web.utentesoutrosconcelhos;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.FotosValencia;
import pt.cmolhao.entity.Tiposvalencia;
import pt.cmolhao.entity.UtentesOutrosConcelhos;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.web.habitacaosocial.HabitacaoSocialEdit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@UiController("cmolhao_UtentesOutrosConcelhos.browse")
@UiDescriptor("utentes-outros-concelhos-browse.xml")
@LookupComponent("utentesOutrosConcelhosesTable")
@LoadDataBeforeShow
public class UtentesOutrosConcelhosBrowse extends StandardLookup<UtentesOutrosConcelhos> {

    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected CollectionLoader<UtentesOutrosConcelhos>  utentesOutrosConcelhosesDl;
    @Inject
    protected LookupPickerField<Valencias> idValenciaField;
    @Inject
    protected LookupField linhasUtentesOutrosConcelhos;
    @Inject
    protected GroupTable<UtentesOutrosConcelhos> utentesOutrosConcelhosesTable;
    @Inject
    protected CollectionContainer<Valencias> valenciasDc;
    @Inject
    protected TextField<String> utentes_conc_freguesia_id;
    @Inject
    protected TextField<String> utentes_conc_concelho_id;
    @Named("utentesOutrosConcelhosesTable.remove")
    protected RemoveAction<UtentesOutrosConcelhos> utentesOutrosConcelhosesTableRemove;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Dialogs dialogs;

    public Component generateValenciasDescricao(UtentesOutrosConcelhos entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdValencia() != null)
        {
            label.setValue(entity.getIdValencia().getDescricaotecnica());
            return label;
        }
        return null;
    }

    @Subscribe("search_utentes_outros_concelhos")
    protected void onSearch_utentes_outros_concelhosClick(Button.ClickEvent event) {
        if (idValenciaField.getValue() != null)
        {
            utentesOutrosConcelhosesDl.setParameter("idValencia",  idValenciaField.getValue().getId());
        } else {
            utentesOutrosConcelhosesDl.removeParameter("idValencia");
        }

        if (utentes_conc_freguesia_id.getValue() != null)
        {
            utentesOutrosConcelhosesDl.setParameter("freguesia",  "(?i)" + utentes_conc_freguesia_id.getValue() + "%");
        } else {
            utentesOutrosConcelhosesDl.removeParameter("freguesia");
        }

        if (utentes_conc_concelho_id.getValue() != null)
        {
            utentesOutrosConcelhosesDl.setParameter("concelho",  "(?i)" + utentes_conc_concelho_id.getValue() + "%");
        } else {
            utentesOutrosConcelhosesDl.removeParameter("concelho");
        }
        utentesOutrosConcelhosesDl.load();
    }

    @Subscribe("reset_utentes_outros_concelhos")
    protected void onReset_utentes_outros_concelhosClick(Button.ClickEvent event) {
        idValenciaField.setValue(null);
        utentes_conc_freguesia_id.setValue(null);
        utentes_conc_concelho_id.setValue(null);
        utentesOutrosConcelhosesDl.removeParameter("idValencia");
        utentesOutrosConcelhosesDl.removeParameter("freguesia");
        utentesOutrosConcelhosesDl.removeParameter("concelho");
        utentesOutrosConcelhosesDl.load();
    }

    @Subscribe("linhasUtentesOutrosConcelhos")
    protected void onLinhasUtentesOutrosConcelhosValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            utentesOutrosConcelhosesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            utentesOutrosConcelhosesDl.setMaxResults(0);
        }


        utentesOutrosConcelhosesDl.load();
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
        linhasUtentesOutrosConcelhos.setOptionsList(list);

        utentesOutrosConcelhosesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(utentesOutrosConcelhosesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdValencia(idValenciaField.getValue());

                            if (idValenciaField.getValue() != null)
                            {
                                Map<String, String> hash_map = new HashMap<>();
                                //String text = "";
                                String string_addr = "";
                                try {
                                    String locationAddres = idValenciaField.getValue().getMorada().replaceAll(" ", "%20");
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
                                    customer.setFreguesia(hash_map.get("locality"));
                                    customer.setConcelho(hash_map.get("locality"));

                                }
                                else
                                {
                                    customer.setFreguesia(null);
                                    customer.setFreguesia(null);
                                }
                            }
                            if(utentes_conc_freguesia_id.getValue() != null)
                            {
                                customer.setFreguesia(utentes_conc_freguesia_id.getValue());
                            }
                            if(utentes_conc_concelho_id.getValue() != null)
                            {
                                customer.setConcelho(utentes_conc_concelho_id.getValue());
                            }
                        })
                        .withScreenClass(UtentesOutrosConcelhosEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Utentes de Outros Concelhos");
        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item :
                customers) {
            map.put(item.getDescricaotecnica() + " " , item);
        }
        idValenciaField.setOptionsMap(map);

    }

    @Subscribe("utentesOutrosConcelhosesTable.remove")
    protected void onUtentesOutrosConcelhosesTableRemove(Action.ActionPerformedEvent event) {
        utentesOutrosConcelhosesTableRemove.setConfirmation(false);
        if (utentesOutrosConcelhosesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selec????o dos utentes e outros concelhos")
                    .withMessage("Deve seleccionar pelo um dos utentes e/ou outros concelhos")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            UtentesOutrosConcelhos user = utentesOutrosConcelhosesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do utente e/ou outro concelho n??mero '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do utente e/ou outro concelho n??mero '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        utentesOutrosConcelhosesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

}