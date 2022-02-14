package pt.cmolhao.web.documentacao_instrumentos_planeamento_diagnostico_social;

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
import pt.cmolhao.entity.DocumentacaoInstrumentosPlaneamentoDiagnosticoSocial;
import pt.cmolhao.entity.DocumentacaoLegislacao;
import pt.cmolhao.web.documentos_legislacao.DocumentacaoLegislacaoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_DocumentacaoInstrumentosPlaneamentoDiagnosticoSocial.browse")
@UiDescriptor("documentacao-instrumentos-planeamento-diagnostico-social-browse.xml")
@LookupComponent("documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTable")
@LoadDataBeforeShow
public class DocumentacaoInstrumentosPlaneamentoDiagnosticoSocialBrowse extends StandardLookup<DocumentacaoInstrumentosPlaneamentoDiagnosticoSocial> {
    @Inject
    protected CollectionContainer<DocumentacaoInstrumentosPlaneamentoDiagnosticoSocial> documentacaoInstrumentosPlaneamentoDiagnosticoSocialsDc;
    @Inject
    protected CollectionLoader<DocumentacaoInstrumentosPlaneamentoDiagnosticoSocial> documentacaoInstrumentosPlaneamentoDiagnosticoSocialsDl;

    @Inject
    protected TextField<String> numeroDocumentacaoField;
    @Inject
    protected LookupField linhasDocumentacao;
    @Inject
    protected GroupTable<DocumentacaoInstrumentosPlaneamentoDiagnosticoSocial> documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTable;
    @Named("documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTable.remove")
    protected RemoveAction<DocumentacaoInstrumentosPlaneamentoDiagnosticoSocial> documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTableRemove;


    @Inject
    protected UiComponents uiComponents;

    @Inject
    private ExportDisplay exportDisplay;

    @Inject
    protected ScreenBuilders screenBuilders;

    @Inject
    private Notifications notifications;

    @Inject
    private Dialogs dialogs;

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Instrumentos de Planeamento no Diagnóstico Social");
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

        documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (numeroDocumentacaoField.getValue() != null) {
                                if (isNumeric(numeroDocumentacaoField.getValue())) {
                                    customer.setNumeroDocumentacao(numeroDocumentacaoField.getValue());
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
                        .withScreenClass(DocumentacaoInstrumentosPlaneamentoDiagnosticoSocialEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

        documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTable.addGeneratedColumn("file", entity -> {
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





    @Subscribe("reset_documentacao_instrumentos_planeamento_diagnostico_social")
    protected void onReset_documentacao_instrumentos_planeamento_diagnostico_socialClick(Button.ClickEvent event) {
        numeroDocumentacaoField.setValue(null);
        documentacaoInstrumentosPlaneamentoDiagnosticoSocialsDl.removeParameter("numeroDocumentacao");
        documentacaoInstrumentosPlaneamentoDiagnosticoSocialsDl.load();
    }

    @Subscribe("search_documentacao_instrumentos_planeamento_diagnostico_social")
    protected void onSearch_documentacao_instrumentos_planeamento_diagnostico_socialClick(Button.ClickEvent event) {
        if (numeroDocumentacaoField.getValue() != null) {
            if (isNumeric(numeroDocumentacaoField.getValue()))
            {
                documentacaoInstrumentosPlaneamentoDiagnosticoSocialsDl.setParameter("numeroDocumentacao", "(?i)%" + numeroDocumentacaoField.getValue() + "%" );
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
            documentacaoInstrumentosPlaneamentoDiagnosticoSocialsDl.removeParameter("numeroDocumentacao");
        }
        documentacaoInstrumentosPlaneamentoDiagnosticoSocialsDl.load();
    }

    @Subscribe("linhasDocumentacao")
    protected void onLinhasDocumentacaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            documentacaoInstrumentosPlaneamentoDiagnosticoSocialsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            documentacaoInstrumentosPlaneamentoDiagnosticoSocialsDl.setMaxResults(0);
        }
        documentacaoInstrumentosPlaneamentoDiagnosticoSocialsDl.load();
    }

    @Subscribe("documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTable.remove")
    protected void onDocumentacaoInstrumentosPlaneamentoDiagnosticoSocialsTableRemove(Action.ActionPerformedEvent event) {
        documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTableRemove.setConfirmation(false);
        if (documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de documentação de instrumentos de planeamento do diagnostico social")
                    .withMessage("Deve seleccionar pelo um dos documentação de instrumentos de planeamento do diagnostico social")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            DocumentacaoInstrumentosPlaneamentoDiagnosticoSocial user = documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela documentação de instrumentos de planeamento do diagnostico social número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela documentação de instrumentos de planeamento do diagnostico social número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        documentacaoInstrumentosPlaneamentoDiagnosticoSocialsTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}