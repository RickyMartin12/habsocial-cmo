package pt.cmolhao.web.instrumentos_planeamento;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.ExcelAction;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.DocumentacaoInstrumentosPlaneamento;
import pt.cmolhao.entity.DocumentacaoNucleoExecutivoAtas;
import pt.cmolhao.web.nucleo_executivo_atas.DocumentacaoNucleoExecutivoAtasEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_DocumentacaoInstrumentosPlaneamento.browse")
@UiDescriptor("documentacao-instrumentos-planeamento-browse.xml")
@LookupComponent("documentacaoInstrumentosPlaneamentoesTable")
@LoadDataBeforeShow
public class DocumentacaoInstrumentosPlaneamentoBrowse extends StandardLookup<DocumentacaoInstrumentosPlaneamento> {
    @Inject
    protected CollectionLoader<DocumentacaoInstrumentosPlaneamento> documentacaoInstrumentosPlaneamentoesDl;
    @Inject
    protected CollectionContainer<DocumentacaoInstrumentosPlaneamento> documentacaoInstrumentosPlaneamentoesDc;
    @Inject
    protected GroupTable<DocumentacaoInstrumentosPlaneamento> documentacaoInstrumentosPlaneamentoesTable;

    @Inject
    protected TextField<String> numeroDocumentacaoField;

    @Inject
    protected LookupField linhasDocumentacao;

    @Inject
    protected UiComponents uiComponents;
    @Named("documentacaoInstrumentosPlaneamentoesTable.remove")
    protected RemoveAction<DocumentacaoInstrumentosPlaneamento> documentacaoInstrumentosPlaneamentoesTableRemove;


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
        getWindow().setCaption("Listar Instrumentos de Planeamento de Documentos");
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

        documentacaoInstrumentosPlaneamentoesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(documentacaoInstrumentosPlaneamentoesTable)
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
                        .withScreenClass(DocumentacaoInstrumentosPlaneamentoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


        documentacaoInstrumentosPlaneamentoesTable.addGeneratedColumn("file", entity -> {
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



    @Subscribe("reset_documentacao_instrumentos_planeamento")
    protected void onReset_documentacao_instrumentos_planeamentoClick(Button.ClickEvent event) {
        numeroDocumentacaoField.setValue(null);
        documentacaoInstrumentosPlaneamentoesDl.removeParameter("numeroDocumentacao");
        documentacaoInstrumentosPlaneamentoesDl.load();
    }

    @Subscribe("search_documentacao_instrumentos_planeamento")
    protected void onSearch_documentacao_instrumentos_planeamentoClick(Button.ClickEvent event) {
        if (numeroDocumentacaoField.getValue() != null) {
            if (isNumeric(numeroDocumentacaoField.getValue()))
            {
                documentacaoInstrumentosPlaneamentoesDl.setParameter("numeroDocumentacao", "(?i)%" + numeroDocumentacaoField.getValue() + "%" );
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
            documentacaoInstrumentosPlaneamentoesDl.removeParameter("numeroDocumentacao");
        }


        documentacaoInstrumentosPlaneamentoesDl.load();
    }

    @Subscribe("documentacaoInstrumentosPlaneamentoesTable.remove")
    protected void onDocumentacaoInstrumentosPlaneamentoesTableRemove(Action.ActionPerformedEvent event) {
        documentacaoInstrumentosPlaneamentoesTableRemove.setConfirmation(false);
        if (documentacaoInstrumentosPlaneamentoesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de instrumentos de planeamento")
                    .withMessage("Deve seleccionar pelo um dos instrumentos de planeamento")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            DocumentacaoInstrumentosPlaneamento user = documentacaoInstrumentosPlaneamentoesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela documento do instrumento de planeamento número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela documento do instrumento de planeamento número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        documentacaoInstrumentosPlaneamentoesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

    @Subscribe("linhasDocumentacao")
    protected void onLinhasDocumentacaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            documentacaoInstrumentosPlaneamentoesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            documentacaoInstrumentosPlaneamentoesDl.setMaxResults(0);
        }
        documentacaoInstrumentosPlaneamentoesDl.load();
    }

   
}