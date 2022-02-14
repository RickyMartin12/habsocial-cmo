package pt.cmolhao.web.blocoshabitacaosocial;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.BlocosHabitacaoSocial;
import pt.cmolhao.entity.HabitacaoSocial;
import pt.cmolhao.web.habitacaosocial.HabitacaoSocialEdit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UiController("cmolhao_BlocosHabitacaoSocial.edit")
@UiDescriptor("blocos-habitacao-social-edit.xml")
@EditedEntityContainer("blocosHabitacaoSocialDc")
@LoadDataBeforeShow
public class BlocosHabitacaoSocialEdit extends StandardEditor<BlocosHabitacaoSocial> {

    @Inject
    protected TextField<UUID> idBlocoshabitacaoSocialField;
    @Inject
    protected Table<HabitacaoSocial> habitacaoSocialsTable;
    @Inject
    protected TextField<String> designacaoField;
    @Named("habitacaoSocialsTable.create")
    protected CreateAction<HabitacaoSocial> habitacaoSocialsTableCreate;
    @Named("habitacaoSocialsTable.edit")
    protected EditAction<HabitacaoSocial> habitacaoSocialsTableEdit;
    @Named("habitacaoSocialsTable.remove")
    protected RemoveAction<HabitacaoSocial> habitacaoSocialsTableRemove;

    @Inject
    private Dialogs dialogs;

    @Inject
    private ScreenBuilders screenBuilders;



    @Inject
    private Notifications notifications;

    @Inject
    private DataManager dataManager;



    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Blocos de Habitação Social - " + idBlocoshabitacaoSocialField.getValue());

        habitacaoSocialsTable.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent -> {
                    HabitacaoSocial customer = habitacaoSocialsTable.getSingleSelected();
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
                        else
                        {
                            notifications.create()
                                    .withCaption("<i>Deve introduzir as coordenadas da morada no respectivo dado na habitação social</i>")
                                    .withType(Notifications.NotificationType.WARNING)
                                    .withContentMode(ContentMode.HTML)
                                    .show();
                        }

                    }
                }));
    }

    @Subscribe("habitacaoSocialsTable.create")
    protected void onHabitacaoSocialsTableCreate(Action.ActionPerformedEvent event) {
        screenBuilders.editor(habitacaoSocialsTable)
                .newEntity()
                .withScreenClass(HabitacaoSocialEdit.class)
                .withInitializer(afterScreenCloseEvent -> {
                    if (designacaoField.getValue() != null)
                    {
                        Map<String, String> hash_map = new HashMap<>();
                        //String text = "";
                        String string_addr = "";
                        try {
                            String locationAddres = designacaoField.getValue() .replaceAll(" ", "%20");
                            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?sensor=false&address="+locationAddres+"&language=en&key=AIzaSyAQHab9s1jUhlfo2GFHmme8bXXugkKMvrA");

                            try(InputStream is = url.openStream(); JsonReader rdr = Json.createReader(is)) {
                                JsonObject obj = rdr.readObject();
                                JsonArray results = obj.getJsonArray("results");
                                JsonObject geoMetryObject, locations;
                                JsonArray addressComponentsArray;
                                if (results.size() > 0)
                                {
                                    for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                                        geoMetryObject=result.getJsonObject("geometry");
                                        locations=geoMetryObject.getJsonObject("location");
                                        afterScreenCloseEvent.setCoord(locations.get("lat").toString()+";"+locations.get("lng").toString());
                                        addressComponentsArray=result.getJsonArray("address_components");
                                        string_addr = result.getString("formatted_address");
                                        afterScreenCloseEvent.setSitoLugar(string_addr);
                                        for (JsonObject addressComponentsArray_result : addressComponentsArray.getValuesAs(JsonObject.class)) {
                                            JsonArray types_array = addressComponentsArray_result.getJsonArray("types");
                                            String vale = addressComponentsArray_result.get("long_name").toString().replace("\"", "");
                                            String types_a = types_array.getString(0);
                                            hash_map.put(types_a, vale);
                                        }
                                    }
                                }
                                else
                                {
                                    afterScreenCloseEvent.setCoord(null);
                                    afterScreenCloseEvent.setSitoLugar(null);
                                }


                            }
                        } catch (IOException e) {
                            //throw new RuntimeException(e);
                        }

                        if (!hash_map.isEmpty())
                        {
                            afterScreenCloseEvent.setRua(hash_map.get("route"));
                            afterScreenCloseEvent.setFreguesia(hash_map.get("route"));
                            afterScreenCloseEvent.setLocalidade(hash_map.get("locality"));
                            afterScreenCloseEvent.setCodPostal(hash_map.get("postal_code"));


                        }
                        else
                        {
                            afterScreenCloseEvent.setRua(null);
                            afterScreenCloseEvent.setFreguesia(null);
                            afterScreenCloseEvent.setLocalidade(null);
                            afterScreenCloseEvent.setCodPostal(null);
                        }
                    }
                })
                .build()
                .show();


    }

    @Subscribe("habitacaoSocialsTable.remove")
    protected void onHabitacaoSocialsTableRemove(Action.ActionPerformedEvent event) {
        habitacaoSocialsTableRemove.setConfirmation(false);
        if (habitacaoSocialsTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção da habitação social")
                    .withMessage("Deve seleccionar pelo uma das habitações sociais")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            HabitacaoSocial user = habitacaoSocialsTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da habitação social número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da habitação social número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        habitacaoSocialsTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

}