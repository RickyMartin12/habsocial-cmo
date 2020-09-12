package pt.cmolhao.web.claso_atas;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import pt.cmolhao.entity.DocumentacaoClasoAtas;
import pt.cmolhao.entity.RedeTrabalho;
import pt.cmolhao.entity.SubRedeTrabalho;

import javax.inject.Inject;
import java.util.*;

@UiController("cmolhao_DocumentacaoClasoAtas.edit")
@UiDescriptor("documentacao-claso-atas-edit.xml")
@EditedEntityContainer("documentacaoClasoAtasDc")
@LoadDataBeforeShow
public class DocumentacaoClasoAtasEdit extends StandardEditor<DocumentacaoClasoAtas> {


    @Inject
    protected LookupPickerField<RedeTrabalho> idRedeTrabalhoField;
    @Inject
    protected LookupPickerField<SubRedeTrabalho> idSubRedeTrabalhoField;
    @Inject
    protected CollectionContainer<DocumentacaoClasoAtas> documentacaoClasoAtasesDc;
    @Inject
    protected CollectionContainer<RedeTrabalho> redeTrabalhoDc;
    @Inject
    protected CollectionContainer<SubRedeTrabalho> subRedeTrabalhoDc;
    @Inject
    protected InstanceContainer<DocumentacaoClasoAtas> documentacaoClasoAtasDc;

    @Inject
    protected FileUploadField fileField;

    @Inject
    protected Button clearImageBtn;
    @Inject
    protected LinkButton file_name_text;
    @Inject
    protected Image file_documento;

    @Inject
    protected VBoxLayout dropZone;
    @Inject
    protected TextField<String> numeroDocumentacaoField;
    @Inject
    protected DateField<Date> dataDocumentoField;
    @Inject
    protected TextField<String> nomeFicheiroField;
    @Inject
    protected TextField<UUID> idDocumentacao;

    @Inject
    private FileUploadingAPI fileUploadingAPI;

    @Inject
    private DataManager dataManager;

    @Inject
    private Notifications notifications;

    @Inject
    private ExportDisplay exportDisplay;

    @Inject
    private Screens screens;

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }




    @Subscribe
    protected void onInit(InitEvent event) {

        numeroDocumentacaoField.addValidator(value -> {
            if (value != null && !isNumeric(value))
                throw new ValidationException("O Numero de documento do ficheiro possui numeros inteiros");
        });


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

        documentacaoClasoAtasDc.addItemPropertyChangeListener(employeeItemPropertyChangeEvent -> {
            if ("file".equals(employeeItemPropertyChangeEvent.getProperty()))
            {
                updateImageButtons(employeeItemPropertyChangeEvent.getValue() != null);
            }
        });
    }




    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Adicionar/Editar Documentacao Atas do Claso - " + idDocumentacao.getValue());

        idRedeTrabalhoField.setEditable(false);
        Map<String, RedeTrabalho> map = new HashMap<>();
        Collection<RedeTrabalho> customers = redeTrabalhoDc.getItems();
        for (RedeTrabalho item : customers) {
            if (item.getNome().equals("Claso"))
            {
                map.put(item.getNome(), item);
                idRedeTrabalhoField.setValue(item);
            }
        }

        idRedeTrabalhoField.setOptionsMap(map);

        displayFicheiro();
        updateImageButtons(getEditedEntity().getFile() != null);

        if(idRedeTrabalhoField.getValue() != null)
        {
            Map<String, SubRedeTrabalho> map2 = new HashMap<>();
            Collection<SubRedeTrabalho> customers2 = subRedeTrabalhoDc.getItems();
            for (SubRedeTrabalho item2 : customers2) {
                if (item2.getIdRedeTrabalho().getId().equals(idRedeTrabalhoField.getValue().getId()))
                {
                    if (item2.getNome().equals("Atas"))
                    {
                        map2.put(item2.getNome(), item2);
                        idSubRedeTrabalhoField.setValue(item2);
                    }

                }
            }
            idSubRedeTrabalhoField.setEditable(false);
            idSubRedeTrabalhoField.setOptionsMap(map2);
        }

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

            dataDocumentoField.setValue(getEditedEntity().getFile().getCreateDate());
            nomeFicheiroField.setValue(getEditedEntity().getFile().getName());

        } else {
            file_name_text.setVisible(false);
            file_documento.setVisible(false);

            dataDocumentoField.setValue(null);
            nomeFicheiroField.setValue("");
        }
    }

    private void updateImageButtons(boolean enable) {
        clearImageBtn.setEnabled(enable);
    }

    public void onClearImageBtnClick() {
        getEditedEntity().setFile(null);
        displayFicheiro();
    }

    @Subscribe("file_name_text")
    protected void onFile_name_textClick(Button.ClickEvent event) {
        if (getEditedEntity().getFile() != null)
            exportDisplay.show(getEditedEntity().getFile(), ExportFormat.OCTET_STREAM);
    }

    @Subscribe("idRedeTrabalhoField")
    protected void onIdRedeTrabalhoFieldValueChange(HasValue.ValueChangeEvent<RedeTrabalho> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                Map<String, SubRedeTrabalho> map = new HashMap<>();
                Collection<SubRedeTrabalho> customers = subRedeTrabalhoDc.getItems();
                for (SubRedeTrabalho item : customers) {
                    if (item.getIdRedeTrabalho().getId().equals(event.getValue().getId()))
                    {
                        if (item.getNome().equals("Atas"))
                        {
                            map.put(item.getNome(), item);
                        }

                    }
                }
                idSubRedeTrabalhoField.setEditable(true);
                idSubRedeTrabalhoField.setOptionsMap(map);
            }
        }
    }



}