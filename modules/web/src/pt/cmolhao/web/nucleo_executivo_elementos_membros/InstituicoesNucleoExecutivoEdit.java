package pt.cmolhao.web.nucleo_executivo_elementos_membros;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@UiController("cmolhao_InstituicoesNucleoExecutivo.edit")
@UiDescriptor("instituicoes-nucleo-executivo-edit.xml")
@EditedEntityContainer("instituicoesNucleoExecutivoDc")
@LoadDataBeforeShow
public class InstituicoesNucleoExecutivoEdit extends StandardEditor<InstituicoesNucleoExecutivo> {

    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected Table<ApoiosNucleoExecutivo> apoiosesTable;
    @Named("apoiosesTable.remove")
    protected RemoveAction<ApoiosNucleoExecutivo> apoiosesTableRemove;
    @Inject
    protected Table<ProjectoIntervencaoNucleoExecutivo> projectosIntervencaosTable;
    @Named("projectosIntervencaosTable.remove")
    protected RemoveAction<ProjectoIntervencaoNucleoExecutivo> projectosIntervencaosTableRemove;
    @Inject
    protected Table<TecnicoNucleoExecutivo> tecnicoesTable;
    @Named("tecnicoesTable.remove")
    protected RemoveAction<TecnicoNucleoExecutivo> tecnicoesTableRemove;
    @Inject
    private TextField<String> coordenadasgpsField;
    @Inject
    private Dialogs dialogs;

    @Inject
    protected TextField<UUID> idInstituicaoField;
    @Inject
    protected Table<ValenciaNucleoExecutivo> valenciasesTable;
    @Named("valenciasesTable.remove")
    protected RemoveAction<ValenciaNucleoExecutivo> valenciasesTableRemove;

    @Inject
    private Notifications notifications;

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
                    notifications.create()
                            .withCaption(rs)
                            .show();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Instituição do Núcleo Executivo - " + idInstituicaoField.getValue());
    }


    public Component generateValorApoio(ApoiosNucleoExecutivo entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getValorApoio() != null)
        {
            label.setValue(entity.getValorApoio() + " €");
            return label;
        }
        return null;
    }


    @Subscribe("moradacompletaField")
    protected void onMoradacompletaFieldTextChange(TextInputField.TextChangeEvent event) {

        locationData(event.getText());
    }

    @Subscribe("valenciasesTable.remove")
    protected void onValenciasesTableRemove(Action.ActionPerformedEvent event) {
        valenciasesTableRemove.setConfirmation(false);
        if (valenciasesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de valênçias do núcleo executivo")
                    .withMessage("Deve seleccionar pelo uma das valênçias do núcleo executivo")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            ValenciaNucleoExecutivo user = valenciasesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da valênçia do núcleo executivo número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da valênçia do núcleo executivo número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        valenciasesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }



    @Subscribe("apoiosesTable.remove")
    protected void onApoiosesTableRemove(Action.ActionPerformedEvent event) {
        apoiosesTableRemove.setConfirmation(false);
        if (apoiosesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de apoios do núcleo executivo")
                    .withMessage("Deve seleccionar pelo um das apoios do núcleo executivo")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            ApoiosNucleoExecutivo user = apoiosesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela de apoio do núcleo executivo número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela de apoio do núcleo executivo número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        apoiosesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

    @Subscribe("projectosIntervencaosTable.remove")
    protected void onProjectosIntervencaosTableRemove(Action.ActionPerformedEvent event) {
        projectosIntervencaosTableRemove.setConfirmation(false);
        if (projectosIntervencaosTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de projectos do núcleo executivo")
                    .withMessage("Deve seleccionar pelo um dos projectos do núcleo executivo")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            ProjectoIntervencaoNucleoExecutivo user = projectosIntervencaosTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do projecto do núcleo executivo número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do projecto do núcleo executivo número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        projectosIntervencaosTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }

    }

    @Subscribe("tecnicoesTable.remove")
    protected void onTecnicoesTableRemove(Action.ActionPerformedEvent event) {
        tecnicoesTableRemove.setConfirmation(false);
        if (tecnicoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção dos técnicos do núcleo executivo")
                    .withMessage("Deve seleccionar pelo um dos técnicos do núcleo executivo")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            TecnicoNucleoExecutivo user = tecnicoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do técnico do núcleo executivo número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do técnico do núcleo executivo número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tecnicoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }



}