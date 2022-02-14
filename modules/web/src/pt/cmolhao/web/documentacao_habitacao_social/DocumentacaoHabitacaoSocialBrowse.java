package pt.cmolhao.web.documentacao_habitacao_social;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@UiController("cmolhao_DocumentacaoHabitacaoSocial.browse")
@UiDescriptor("documentacao-habitacao-social-browse.xml")
@LookupComponent("documentacaoHabitacaoSocialsTable")
@LoadDataBeforeShow
public class DocumentacaoHabitacaoSocialBrowse extends StandardLookup<DocumentacaoHabitacaoSocial> {
    @Inject
    protected CollectionContainer<DocumentacaoHabitacaoSocial> documentacaoHabitacaoSocialsDc;
    @Inject
    protected CollectionLoader<DocumentacaoHabitacaoSocial> documentacaoHabitacaoSocialsDl;
    @Inject
    protected CollectionContainer<HabitacaoSocial> habitacaoSocialsDc;
    @Inject
    protected LookupPickerField<HabitacaoSocial> habitacaoSocialField;
    @Inject
    protected CollectionContainer<RedeTrabalho> redeTrabalhoDc;
    @Inject
    protected CollectionContainer<SubRedeTrabalho> subRedeTrabalhoDc;
    @Inject
    protected LookupPickerField<RedeTrabalho> idRedeTrabalhoField;
    @Inject
    protected LookupPickerField<SubRedeTrabalho> idSubRedeTrabalhoField;
    @Inject
    protected TextField<String> numeroDocumentacaoField;
    @Inject
    protected LookupField linhasDocumentacao;
    @Inject
    protected GroupTable<DocumentacaoHabitacaoSocial> documentacaoHabitacaoSocialsTable;
    @Named("documentacaoHabitacaoSocialsTable.remove")
    protected RemoveAction<DocumentacaoHabitacaoSocial> documentacaoHabitacaoSocialsTableRemove;

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

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Documentos da habitação Social");

        idSubRedeTrabalhoField.setEditable(false);

        Map<String, RedeTrabalho> map = new HashMap<>();
        Collection<RedeTrabalho> customers = redeTrabalhoDc.getItems();
        for (RedeTrabalho item : customers) {
            if (item.getNome().equals("Programa de Apoio ao Arrendamento"))
            {
                map.put(item.getNome(), item);
            }
        }

        idRedeTrabalhoField.setOptionsMap(map);
    }

    @Subscribe
    protected void onInit(InitEvent event) {

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
        linhasDocumentacao.setOptionsList(list);

        documentacaoHabitacaoSocialsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(documentacaoHabitacaoSocialsTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdHabitacaoSocial(habitacaoSocialField.getValue());
                            customer.setIdRedeTrabalho(idRedeTrabalhoField.getValue());
                            customer.setIdSubRedeTrabalho(idSubRedeTrabalhoField.getValue());
                            if (numeroDocumentacaoField.getValue() != null) {
                                if (isNumeric(numeroDocumentacaoField.getValue())) {
                                    customer.setNumDocumento(numeroDocumentacaoField.getValue());
                                } else {
                                    notifications.create()
                                            .withCaption("<code>Erro ao atribuir string do número de documentação </code>")
                                            .withDescription("<u>Devera introduzir um numero inteiro</u>")
                                            .withType(Notifications.NotificationType.ERROR)
                                            .withContentMode(ContentMode.HTML)
                                            .show();
                                }
                            }

                        })
                        .withScreenClass(DocumentacaoHabitacaoSocialEdit.class)    // specific editor screen
                        .build()
                        .show()
        );



        documentacaoHabitacaoSocialsTable.addGeneratedColumn("file", entity -> {
            if(entity.getFile() != null) {
                Button btn = uiComponents.create(Button.class);
                btn.setCaption(entity.getFile().getName());
                btn.addStyleName("download_documentos");
                String ext = entity.getFile().getExtension();
                btn.setIcon("font-icon:FILE_O");

                if (ext.equals("pdf")) {
                    btn.setIcon("font-icon:FILE_PDF_O");
                }
                if (ext.equals("docx") || ext.equals("doc")) {
                    btn.setIcon("font-icon:FILE_WORD_O");
                }
                if (ext.equals("webm") || ext.equals("mp4") || ext.equals("mpg") || ext.equals("ogg") || ext.equals("avi") || ext.equals("mov")) {
                    btn.setIcon("font-icon:FILE_VIDEO_O");
                }
                if (ext.equals("wav") || ext.equals("mp3")) {
                    btn.setIcon("font-icon:FILE_SOUND_O");
                }
                if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png") || ext.equals("gif")) {
                    btn.setIcon("font-icon:FILE_PICTURE_O");
                }
                if (ext.equals("rar") || ext.equals("zip")) {
                    btn.setIcon("font-icon:FILE_ZIP_O");
                }
                if (ext.equals("xlsx") || ext.equals("xls") || ext.equals("csv")) {
                    btn.setIcon("font-icon:FILE_EXCEL_O");
                }
                if (ext.equals("pptx") || ext.equals("ppt")) {
                    btn.setIcon("font-icon:FILE_POWERPOINT_O");
                }
                btn.setAction(new BaseAction("download") {
                    @Override
                    public void actionPerform(Component component) {
                        FileDescriptor imageFile = entity.getFile();
                        exportDisplay.show(imageFile, ExportFormat.OCTET_STREAM);

                    }
                });
                return btn;
            }
            else
            {
                return null;
            }
        });
    }

    @Subscribe("reset_documentacao")
    protected void onReset_documentacaoClick(Button.ClickEvent event) {
        habitacaoSocialField.setValue(null);
        idRedeTrabalhoField.setValue(null);
        idSubRedeTrabalhoField.setValue(null);
        numeroDocumentacaoField.setValue(null);
        documentacaoHabitacaoSocialsDl.removeParameter("idHabitacaoSocial");
        documentacaoHabitacaoSocialsDl.removeParameter("idRedeTrabalho");
        documentacaoHabitacaoSocialsDl.removeParameter("idSubRedeTrabalho");
        documentacaoHabitacaoSocialsDl.removeParameter("numDocumento");
        documentacaoHabitacaoSocialsDl.load();
    }

    @Subscribe("search_documentacao")
    protected void onSearch_documentacaoClick(Button.ClickEvent event) {
        if (habitacaoSocialField.getValue() != null) {
            documentacaoHabitacaoSocialsDl.setParameter("idHabitacaoSocial",  habitacaoSocialField.getValue().getId());
        } else {
            documentacaoHabitacaoSocialsDl.removeParameter("idHabitacaoSocial");
        }


        if (idRedeTrabalhoField.getValue() != null) {
            documentacaoHabitacaoSocialsDl.setParameter("idRedeTrabalho",  idRedeTrabalhoField.getValue().getId());
        } else {
            documentacaoHabitacaoSocialsDl.removeParameter("idRedeTrabalho");
        }

        if (idSubRedeTrabalhoField.getValue() != null) {
            documentacaoHabitacaoSocialsDl.setParameter("idSubRedeTrabalho",  idSubRedeTrabalhoField.getValue().getId());
        } else {
            documentacaoHabitacaoSocialsDl.removeParameter("idSubRedeTrabalho");
        }


        if (numeroDocumentacaoField.getValue() != null) {
            if (isNumeric(numeroDocumentacaoField.getValue()))
            {
                documentacaoHabitacaoSocialsDl.setParameter("numDocumento", "(?i)%" + numeroDocumentacaoField.getValue() + "%" );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do número de documentação</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }

        } else {
            documentacaoHabitacaoSocialsDl.removeParameter("numDocumento");
        }


        documentacaoHabitacaoSocialsDl.load();
    }

    @Subscribe("linhasDocumentacao")
    protected void onLinhasDocumentacaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            documentacaoHabitacaoSocialsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            documentacaoHabitacaoSocialsDl.setMaxResults(0);
        }
        documentacaoHabitacaoSocialsDl.load();
    }

    @Subscribe("documentacaoHabitacaoSocialsTable.remove")
    protected void onDocumentacaoHabitacaoSocialsTableRemove(Action.ActionPerformedEvent event) {
        documentacaoHabitacaoSocialsTableRemove.setConfirmation(false);
        if (documentacaoHabitacaoSocialsTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de documentos de habitação social")
                    .withMessage("Deve seleccionar pelo um dos documentos de habitação social")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            DocumentacaoHabitacaoSocial user = documentacaoHabitacaoSocialsTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do documentação de habitação social número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do documentação de habitação social número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        documentacaoHabitacaoSocialsTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

    @Subscribe("idRedeTrabalhoField")
    protected void onIdRedeTrabalhoFieldValueChange(HasValue.ValueChangeEvent<RedeTrabalho> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {

                if (event.getValue().getNome().equals("Programa de Apoio ao Arrendamento"))
                {
                    Map<String, SubRedeTrabalho> map = new HashMap<>();
                    Collection<SubRedeTrabalho> customers = subRedeTrabalhoDc.getItems();
                    for (SubRedeTrabalho item : customers) {
                        if (item.getIdRedeTrabalho().getId().equals(event.getValue().getId()))
                        {
                            map.put(item.getNome(), item);
                        }
                    }
                    idSubRedeTrabalhoField.setEditable(true);
                    idSubRedeTrabalhoField.setOptionsMap(map);
                }
                else
                {
                    idSubRedeTrabalhoField.setValue(null);
                    idSubRedeTrabalhoField.setEditable(false);
                }


            }
        }
    }


    

}