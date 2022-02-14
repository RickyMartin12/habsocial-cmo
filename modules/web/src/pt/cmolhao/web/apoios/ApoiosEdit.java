package pt.cmolhao.web.apoios;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.*;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import pt.cmolhao.entity.Apoios;
import pt.cmolhao.entity.RespostaSocial;
import pt.cmolhao.entity.TipoAjuda;
import pt.cmolhao.entity.TipoDocApoio;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UiController("cmolhao_Apoios.edit")
@UiDescriptor("apoios-edit.xml")
@EditedEntityContainer("apoiosDc")
@LoadDataBeforeShow
public class ApoiosEdit extends StandardEditor<Apoios> {

    @Inject
    protected TextField<UUID> idApoiosField;
    @Inject
    protected InstanceContainer<Apoios> apoiosDc;
    @Inject
    protected LookupPickerField<TipoAjuda> idTipoapoioField;
    @Inject
    protected LookupPickerField<TipoDocApoio> idTipoDocApoioField;
    @Inject
    protected CollectionContainer<TipoDocApoio> tipoDocApoioDc;
    @Inject
    protected CollectionContainer<TipoAjuda> tipoAjudasDc;
    @Inject
    private ExportDisplay exportDisplay;

    @Inject
    protected ScreenBuilders screenBuilders;

    @Inject
    private Notifications notifications;

    @Inject
    private Dialogs dialogs;

    @Inject
    protected UiComponents uiComponents;

    @Inject
    protected VBoxLayout dropZone;

    @Inject
    private FileUploadingAPI fileUploadingAPI;

    @Inject
    private DataManager dataManager;

    @Inject
    private Screens screens;

    @Inject
    protected Button clearImageBtn;
    @Inject
    protected LinkButton file_name_text;
    @Inject
    protected Image file_documento;

    @Inject
    protected FileUploadField fileField;



    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Apoios - " + idApoiosField.getValue());

        if(idTipoapoioField.getValue() != null)
        {
            Map<String, TipoDocApoio> map = new HashMap<>();
            Collection<TipoDocApoio> customers = tipoDocApoioDc.getItems();
            for (TipoDocApoio item : customers) {
                if (item.getIdTipoApoio().getId().equals(idTipoapoioField.getValue().getId()))
                {
                    map.put(item.getDescricao(), item);
                }
            }
            idTipoDocApoioField.setEditable(true);
            idTipoDocApoioField.setOptionsMap(map);
        }



        displayFicheiro();
        updateImageButtons(getEditedEntity().getFile() != null);
    }

    @Subscribe
    protected void onInit(InitEvent event) {

        fileField.addFileUploadSucceedListener(uploadSucceedEvent -> {
            FileDescriptor fd = fileField.getFileDescriptor();
            try {
                fileUploadingAPI.putFileIntoStorage(fileField.getFileId(), fd);
            } catch (FileStorageException e) {
                throw new RuntimeException("Error saving file to FileStorage", e);
            }
            getEditedEntity().setFile(dataManager.commit(fd));
            displayFicheiro();
        });

        fileField.addFileUploadErrorListener(uploadErrorEvent ->
                notifications.create()
                        .withCaption("File upload error")
                        .show());

        apoiosDc.addItemPropertyChangeListener(employeeItemPropertyChangeEvent -> {
            if ("file".equals(employeeItemPropertyChangeEvent.getProperty()))
            {
                updateImageButtons(employeeItemPropertyChangeEvent.getValue() != null);
            }
        });
    }


    private void displayFicheiro() {
        if (getEditedEntity().getFile() != null) {
            file_name_text.setCaption(getEditedEntity().getFile().getName());
            file_name_text.setVisible(true);

            String ext = getEditedEntity().getFile().getExtension();

            file_documento.setSource(ThemeResource.class)
                    .setPath("images/formats_file/file.png");

            if (ext.equals("pdf")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/pdf.png");
            }
            if (ext.equals("docx")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/docx.png");
            }
            if (ext.equals("doc")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/doc.png");
            }
            if (ext.equals("webm")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/webm.png");
            }
            if (ext.equals("mp4")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/mp4.png");
            }
            if (ext.equals("mpg")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/mpg.png");
            }
            if (ext.equals("ogg")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/ogg.png");
            }
            if (ext.equals("avi")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/avi.png");
            }
            if (ext.equals("mov")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/mov.png");
            }
            if (ext.equals("wav")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/wav.png");
            }
            if (ext.equals("mp3")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/mp3.png");
            }
            if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || ext.equals("gif")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/photo.png");
            }
            if (ext.equals("rar") || ext.equals("zip")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/compress.png");
            }
            if (ext.equals("xlsx")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/xlsx.png");
            }
            if (ext.equals("xls")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/xls.png");
            }
            if (ext.equals("csv")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/csv.png");
            }
            if (ext.equals("pptx")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/pptx.png");
            }
            if (ext.equals("ppt")) {
                file_documento.setSource(ThemeResource.class)
                        .setPath("images/formats_file/ppt.png");
            }

            file_documento.setVisible(true);

        } else {
            file_name_text.setVisible(false);
            file_documento.setVisible(false);
        }
    }

    private void updateImageButtons(boolean enable) {
        clearImageBtn.setEnabled(enable);
    }

    public void onClearImageBtnClick() {
        getEditedEntity().setFile(null);
        displayFicheiro();
    }

    @Subscribe("idTipoapoioField")
    protected void onIdTipoapoioFieldValueChange(HasValue.ValueChangeEvent<TipoAjuda> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                Map<String, TipoDocApoio> map = new HashMap<>();
                Collection<TipoDocApoio> customers = tipoDocApoioDc.getItems();
                for (TipoDocApoio item : customers) {
                    if (item.getIdTipoApoio().getId().equals(event.getValue().getId()))
                    {
                        map.put(item.getDescricao(), item);
                    }
                }

                idTipoDocApoioField.setEditable(true);
                idTipoDocApoioField.setValue(null);
                idTipoDocApoioField.setOptionsMap(map);


            }
        }
    }

    @Subscribe("idTipoapoioField")
    protected void onIdTipoapoioFieldValueChange1(HasValue.ValueChangeEvent<TipoAjuda> event) {

    }


}