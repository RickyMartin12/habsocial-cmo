package pt.cmolhao.web.fotosvalencia;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import pt.cmolhao.entity.FotosValencia;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import java.io.File;
import java.util.*;
import com.haulmont.cuba.gui.export.ExportDisplay;
@UiController("cmolhao_FotosValencia.edit")
@UiDescriptor("fotos-valencia-edit.xml")
@EditedEntityContainer("fotosValenciaDc")
@LoadDataBeforeShow
public class FotosValenciaEdit extends StandardEditor<FotosValencia> {

    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected TextField<UUID> idFotosValenciaField;
    @Inject
    protected Image imagem_foto_valencia;
    @Inject
    protected InstanceContainer<FotosValencia> fotosValenciaDc;
    @Inject
    protected FileUploadField uploadField;
    @Inject
    protected Button clearImageBtn;
    @Inject
    protected Button downloadImageBtn;
    @Inject
    private LookupPickerField<Valencias> idvalenciaField;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private DataManager dataManager;
    @Inject
    private Notifications notifications;
    @Inject
    private ExportDisplay exportDisplay;

    /*@Subscribe("imageField")
    protected void onImageFieldFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        File file = fileUploadingAPI.getFile(imageField.getFileId());
        if (file != null) {
            notifications.create()
                    .withCaption("File is uploaded to temporary storage at " + file.getAbsolutePath())
                    .show();
        }

        FileDescriptor fd = imageField.getFileDescriptor();
        try {
            fileUploadingAPI.putFileIntoStorage(imageField.getFileId(), fd);
        } catch (FileStorageException e) {
            throw new RuntimeException("Error saving file to FileStorage", e);
        }
        getEditedEntity().setImage(dataManager.commit(fd));
        displayImage();
        imagem_foto_valencia.setSource(FileDescriptorResource.class).setFileDescriptor(getEditedEntity().getImage());
        notifications.create()
                .withCaption("Uploaded file: " + imageField.getFileName())
                .show();


    }*/

    @Subscribe
    protected void onInit(InitEvent event) {
        uploadField.setAccept(".jpg,.jpeg,.png,.gif");
        uploadField.addFileUploadSucceedListener(uploadSucceedEvent -> {
            FileDescriptor fd = uploadField.getFileDescriptor();
            try {
                fileUploadingAPI.putFileIntoStorage(uploadField.getFileId(), fd);
            } catch (FileStorageException e) {
                throw new RuntimeException("Error saving file to FileStorage", e);
            }
            getEditedEntity().setImage(dataManager.commit(fd));
            displayImage();
        });

        uploadField.addFileUploadErrorListener(uploadErrorEvent ->
                notifications.create()
                        .withCaption("File upload error")
                        .show());

        fotosValenciaDc.addItemPropertyChangeListener(employeeItemPropertyChangeEvent -> {
            if ("image".equals(employeeItemPropertyChangeEvent.getProperty()))
                updateImageButtons(employeeItemPropertyChangeEvent.getValue() != null);
        });
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        displayImage();
        updateImageButtons(getEditedEntity().getImage() != null);

        getWindow().setCaption("Adicionar/Editar Fotos Valência - " + idFotosValenciaField.getValue());
        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            map.put(item.getDescricaotecnica() + " " , item);
        }
        idvalenciaField.setOptionsMap(map);
    }



    public void onClearImageBtnClick() {
        getEditedEntity().setImage(null);
        displayImage();
    }


    public void onDownloadImageBtnClick() {
        if (getEditedEntity().getImage() != null)
            exportDisplay.show(getEditedEntity().getImage(), ExportFormat.OCTET_STREAM);
    }

    private void updateImageButtons(boolean enable) {
        clearImageBtn.setEnabled(enable);
        downloadImageBtn.setEnabled(enable);
    }


    private void displayImage() {
        if (getEditedEntity().getImage() != null) {
            imagem_foto_valencia.setSource(FileDescriptorResource.class).setFileDescriptor(getEditedEntity().getImage());
            imagem_foto_valencia.setVisible(true);
        } else {
            //imagem_foto_valencia.setVisible(false);
            imagem_foto_valencia.setSource(ThemeResource.class)
                    .setPath("images/no_image.jpg");
        }
    }



   
    

}