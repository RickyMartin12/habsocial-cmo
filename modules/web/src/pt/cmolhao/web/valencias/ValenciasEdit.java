package pt.cmolhao.web.valencias;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.components.data.value.ContainerValueSource;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
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
import java.util.*;
import java.util.stream.Collectors;

@UiController("cmolhao_Valencias.edit")
@UiDescriptor("valencias-edit.xml")
@EditedEntityContainer("valenciasDc")
@LoadDataBeforeShow
public class ValenciasEdit extends StandardEditor<Valencias> {

    @Inject
    protected DateField<Date> certdatainicioField;
    @Inject
    protected TextField<UUID> idValenciasField;
    @Inject
    protected LookupPickerField<RespostaSocial> idResSocialField;
    @Inject
    protected CollectionContainer<RespostaSocial> respostaSocialsDc;
    @Inject
    protected Table<FotosValencia> fotosValenciasTable;
    @Inject
    protected LookupPickerField<Tiposvalencia> idtipovalenciaField;
    @Inject
    protected TextField<Integer> acordocapacidadeField;
    @Inject
    protected CollectionContainer<Tiposvalencia> TipoValenciaDc;
    @Inject
    protected Table<AjudasTecnicas> ajudasTecnicasesTable;
    @Named("ajudasTecnicasesTable.remove")
    protected RemoveAction<AjudasTecnicas> ajudasTecnicasesTableRemove;
    @Named("fotosValenciasTable.remove")
    protected RemoveAction<FotosValencia> fotosValenciasTableRemove;
    @Inject
    protected Table<Localizacoes> localizacoesesTable;
    @Named("localizacoesesTable.remove")
    protected RemoveAction<Localizacoes> localizacoesesTableRemove;
    @Inject
    protected Table<UtentesOutrosConcelhos> utentesOutrosConcelhosesTable;
    @Named("utentesOutrosConcelhosesTable.remove")
    protected RemoveAction<UtentesOutrosConcelhos> utentesOutrosConcelhosesTableRemove;
    @Inject
    protected CollectionContainer<Valencias> valencias_dc;
    @Inject
    protected InstanceContainer<Valencias> valenciasDc;
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

    @Inject
    protected UiComponents uiComponents;

    @Inject
    private Dialogs dialogs;

    @Inject
    private DataManager dataManager;

    @Inject
    private ExportDisplay exportDisplay;



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

    public Component generateValenciasDescricaoUtentesOutrosConcelhos(UtentesOutrosConcelhos entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdValencia() != null)
        {
            label.setValue(entity.getIdValencia().getDescricaotecnica());
            return label;
        }
        return null;
    }

    public Component generateValenciasDescricaoAjudasTecnicas(AjudasTecnicas entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdValencia() != null)
        {
            label.setValue(entity.getIdValencia().getDescricaotecnica());
            return label;
        }
        return null;
    }

    public Component generateValenciasDescricaoFotosValencia(FotosValencia entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdvalencia() != null)
        {
            label.setValue(entity.getIdvalencia().getDescricaotecnica());
            return label;
        }
        return null;
    }

    public Component generateValenciasDescricaoLocalizacoes(Localizacoes entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdvalencia() != null)
        {
            label.setValue(entity.getIdvalencia().getDescricaotecnica());
            return label;
        }
        return null;


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
                    Double latitude = Double.valueOf(locations.get("lat").toString());
                    Double longitude = Double.valueOf(locations.get("lng").toString());
                    String rs = convert(latitude, longitude);
                    coordenadagpsField.setValue(rs);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Valência - " + idValenciasField.getValue());
        if(idtipovalenciaField.getValue() != null)
        {
            Map<String, RespostaSocial> map = new HashMap<>();
            Collection<RespostaSocial> customers = respostaSocialsDc.getItems();
            for (RespostaSocial item : customers) {
                if (item.getIdTipoValencia().getId().equals(idtipovalenciaField.getValue().getId()))
                {
                    map.put(item.getNome(), item);

                }
            }
            idResSocialField.setEditable(true);
            idResSocialField.setOptionsMap(map);
        }

        /*if (idResSocialField.getValue() != null)
        {
            Map<String, Tiposvalencia> map_res = new HashMap<>();
            Collection<Tiposvalencia> customers = TipoValenciaDc.getItems();
            for (Tiposvalencia item : customers) {
                if (item.getId().equals(idResSocialField.getValue().getIdTipoValencia().getId()))
                {
                    map_res.put(item.getNome(), item);
                }
            }
            idtipovalenciaField.setEditable(true);
            idtipovalenciaField.setOptionsMap(map_res);
        }*/



    }


    @Subscribe("idtipovalenciaField")
    protected void onIdtipovalenciaFieldValueChange(HasValue.ValueChangeEvent<Tiposvalencia> event) {

        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                Map<String, RespostaSocial> map = new HashMap<>();
                Collection<RespostaSocial> customers = respostaSocialsDc.getItems();
                for (RespostaSocial item : customers) {
                    if (item.getIdTipoValencia().getId().equals(event.getValue().getId()))
                    {
                        map.put(item.getNome(), item);
                    }
                }
                idResSocialField.setEditable(true);
                idResSocialField.setValue(null);
                idResSocialField.setOptionsMap(map);

            }
        }

    }

    @Subscribe
    protected void onInit(InitEvent event) {



        fotosValenciasTable.addGeneratedColumn(
                "image",
                this::renderAvatarImageComponent
        );

        fotosValenciasTable.addGeneratedColumn("download", entity -> {
            Button btn = uiComponents.create(Button.class);
            btn.setCaption("Descarregar");
            btn.addStyleName("download_fotos_valencia");
            btn.setIcon("font-icon:PHOTO");
            btn.setAction(new BaseAction("download") {
                @Override
                public void actionPerform(Component component) {
                    FotosValencia user = fotosValenciasTable.getSingleSelected();
                    if (user != null)
                    {
                        FileDescriptor imageFile = user.getImage();
                        exportDisplay.show(imageFile, ExportFormat.OCTET_STREAM);
                    }

                }
            });
            return btn;
        });

    }




    private Component renderAvatarImageComponent(FotosValencia vet) {
        FileDescriptor imageFile = vet.getImage();
        
        Image image = null;
        if (imageFile == null) {
            image = nofotoImage();
            image.setSource(ThemeResource.class)
                    .setPath("images/no_image.jpg");
        }
        else
        {
            image = smallAvatarImage();
            image.setSource(FileDescriptorResource.class)
                    .setFileDescriptor(imageFile);
        }
        return image;
    }

    private Image smallAvatarImage() {

        Image image = uiComponents.create(Image.class);
        image.setScaleMode(Image.ScaleMode.CONTAIN);
        image.setHeight("400");
        image.setWidth("400");
        image.setStyleName("avatar-icon-small");
        return image;
    }

    private Image nofotoImage() {
        Image image = uiComponents.create(Image.class);
        image.setScaleMode(Image.ScaleMode.CONTAIN);
        image.setHeight("100");
        image.setWidth("100");
        image.setStyleName("avatar-icon-small");
        return image;
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    private void onPreCommit(DataContext.PreCommitEvent event) {

        int ti = 0;
        List<Integer> list = new ArrayList<>();
        List<Integer> list_temp = new ArrayList<>();

        Valencias customer = metadata.create(Valencias.class);


        if (idResSocialField.getValue() != null) {
            if (acordocapacidadeField.getValue() != null) {
                int capacidade = idResSocialField.getValue().getCapacidade();

                list.add(acordocapacidadeField.getValue());
                list_temp.add(acordocapacidadeField.getValue());
                Collection<Valencias> customers_valencias = valencias_dc.getItems();

                for (Valencias item : customers_valencias) {

                    if (item.getIdResSocial() != null)
                    {
                        if (item.getIdResSocial().getId().equals(idResSocialField.getValue().getId()))
                        {
                            if (item.getAcordocapacidade() != null)
                            {
                                String v = "" + idValenciasField.getValue();

                                if (item.getId().toString().equals(v))
                                        {
                                            list_temp.remove(item.getAcordocapacidade());
                                        }


                                list.add(item.getAcordocapacidade());
                                list_temp.add(item.getAcordocapacidade());
                            }

                        }

                    }



                }

                /*notifications.create()
                        .withCaption("Position: " + list_temp.toString())
                        .show();*/



                for (int i : list_temp)
                {
                    ti = ti + i;
                }


                if (ti > capacidade)
                {
                    dialogs.createOptionDialog()
                            .withCaption("Excesso da insercao do numero maximo de pessoas na resposta social")
                            .withMessage("O Numero de pessoas não pode ultrapassar o numero de máximo de pessoas da resposta social.")
                            .withActions(
                                    new DialogAction(DialogAction.Type.CLOSE)
                            )
                            .show();
                    event.preventCommit();
                }



            }
        }





    }



    @Subscribe("moradaField")
    protected void onMoradaFieldTextChange1(TextInputField.TextChangeEvent event) {
        locationData(event.getText());
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

    @Subscribe("fotosValenciasTable.remove")
    protected void onFotosValenciasTableRemove(Action.ActionPerformedEvent event) {
        fotosValenciasTableRemove.setConfirmation(false);
        if (fotosValenciasTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de fotos de valênçia")
                    .withMessage("Deve seleccionar pelo um das fotos de valênçia")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            FotosValencia user = fotosValenciasTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da foto de valênçia número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da foto de valênçia número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        fotosValenciasTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

    @Subscribe("localizacoesesTable.remove")
    protected void onLocalizacoesesTableRemove(Action.ActionPerformedEvent event) {
        localizacoesesTableRemove.setConfirmation(false);
        if (localizacoesesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de localizações")
                    .withMessage("Deve seleccionar pelo um das localizações")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Localizacoes user = localizacoesesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela da localização número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela da localização número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        localizacoesesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

    @Subscribe("utentesOutrosConcelhosesTable.remove")
    protected void onUtentesOutrosConcelhosesTableRemove(Action.ActionPerformedEvent event) {
        utentesOutrosConcelhosesTableRemove.setConfirmation(false);
        if (utentesOutrosConcelhosesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção dos utentes e outros concelhos")
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
                    .withCaption("Remover a linha da tabela do utente e/ou outro concelho número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do utente e/ou outro concelho número '"+user.getId()+"'?")
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