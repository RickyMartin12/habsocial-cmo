package pt.cmolhao.web.claso_fichas_adesao;

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
import pt.cmolhao.entity.DocumentacaoClasoAtas;
import pt.cmolhao.entity.DocumentacaoClasoFichasAdesao;
import pt.cmolhao.web.claso_atas.DocumentacaoClasoAtasEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_DocumentacaoClasoFichasAdesao.browse")
@UiDescriptor("documentacao-claso-fichas-adesao-browse.xml")
@LookupComponent("documentacaoClasoFichasAdesaosTable")
@LoadDataBeforeShow
public class DocumentacaoClasoFichasAdesaoBrowse extends StandardLookup<DocumentacaoClasoFichasAdesao> {
    @Inject
    protected TextField<String> numeroDocumentacaoField;
    @Inject
    protected CollectionLoader<DocumentacaoClasoFichasAdesao> documentacaoClasoFichasAdesaosDl;
    @Inject
    protected LookupField linhasDocumentacao;
    @Inject
    protected GroupTable<DocumentacaoClasoFichasAdesao> documentacaoClasoFichasAdesaosTable;
    @Named("documentacaoClasoFichasAdesaosTable.remove")
    protected RemoveAction<DocumentacaoClasoFichasAdesao> documentacaoClasoFichasAdesaosTableRemove;
    @Inject
    protected CollectionContainer<DocumentacaoClasoFichasAdesao> documentacaoClasoFichasAdesaosDc;

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
        getWindow().setCaption("Listar Claso de Fichas de Adesão de Documentos");
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

        documentacaoClasoFichasAdesaosTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(documentacaoClasoFichasAdesaosTable)
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
                        .withScreenClass(DocumentacaoClasoFichasAdesaoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

        documentacaoClasoFichasAdesaosTable.addGeneratedColumn("file", entity -> {
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

    @Subscribe("search_documentacao_claso")
    protected void onSearch_documentacao_clasoClick(Button.ClickEvent event) {
        if (numeroDocumentacaoField.getValue() != null) {
            if (isNumeric(numeroDocumentacaoField.getValue()))
            {
                documentacaoClasoFichasAdesaosDl.setParameter("numeroDocumentacao", "(?i)%" + numeroDocumentacaoField.getValue() + "%" );
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
            documentacaoClasoFichasAdesaosDl.removeParameter("numeroDocumentacao");
        }


        documentacaoClasoFichasAdesaosDl.load();
    }

    @Subscribe("reset_documentacao_claso")
    protected void onReset_documentacao_clasoClick(Button.ClickEvent event) {
        numeroDocumentacaoField.setValue(null);
        documentacaoClasoFichasAdesaosDl.removeParameter("numeroDocumentacao");
        documentacaoClasoFichasAdesaosDl.load();
    }

    @Subscribe("linhasDocumentacao")
    protected void onLinhasDocumentacaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            documentacaoClasoFichasAdesaosDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            documentacaoClasoFichasAdesaosDl.setMaxResults(0);
        }
        documentacaoClasoFichasAdesaosDl.load();
    }

    @Subscribe("documentacaoClasoFichasAdesaosTable.remove")
    protected void onDocumentacaoClasoFichasAdesaosTableRemove(Action.ActionPerformedEvent event) {
        documentacaoClasoFichasAdesaosTableRemove.setConfirmation(false);
        if (documentacaoClasoFichasAdesaosTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de documentos do claso das fichas de adesão")
                    .withMessage("Deve seleccionar pelo um dos documentos do claso das fichas de adesão")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            DocumentacaoClasoFichasAdesao user = documentacaoClasoFichasAdesaosTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela documento do claso da ficha de adesão número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela documento do claso da ficha de adesão número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        documentacaoClasoFichasAdesaosTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}