package pt.cmolhao.web.casos_sociais;

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
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.DocumentacaoCasosSociais;
import pt.cmolhao.entity.DocumentacaoGruposTrabalho;
import pt.cmolhao.web.grupos_trabalho.DocumentacaoGruposTrabalhoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_DocumentacaoCasosSociais.browse")
@UiDescriptor("documentacao-casos-sociais-browse.xml")
@LookupComponent("documentacaoCasosSociaisesTable")
@LoadDataBeforeShow
public class DocumentacaoCasosSociaisBrowse extends StandardLookup<DocumentacaoCasosSociais> {
    @Inject
    protected CollectionLoader<DocumentacaoCasosSociais> documentacaoCasosSociaisesDl;
    @Inject
    protected LookupField linhasDocumentacao;
    @Inject
    protected TextField<String> numeroDocumentacaoField;
    @Inject
    protected GroupTable<DocumentacaoCasosSociais> documentacaoCasosSociaisesTable;
    @Named("documentacaoCasosSociaisesTable.remove")
    protected RemoveAction<DocumentacaoCasosSociais> documentacaoCasosSociaisesTableRemove;

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

        documentacaoCasosSociaisesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(documentacaoCasosSociaisesTable)
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
                        .withScreenClass(DocumentacaoCasosSociaisEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


        documentacaoCasosSociaisesTable.addGeneratedColumn("file", entity -> {
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



    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Documentação dos Casos Sociais");
    }

    @Subscribe("reset_documentacao_casos_sociais")
    protected void onReset_documentacao_casos_sociaisClick(Button.ClickEvent event) {
        numeroDocumentacaoField.setValue(null);
        documentacaoCasosSociaisesDl.removeParameter("numeroDocumentacao");
        documentacaoCasosSociaisesDl.load();
    }

    @Subscribe("search_documentacao_casos_sociais")
    protected void onSearch_documentacao_casos_sociaisClick(Button.ClickEvent event) {
        if (numeroDocumentacaoField.getValue() != null) {
            if (isNumeric(numeroDocumentacaoField.getValue()))
            {
                documentacaoCasosSociaisesDl.setParameter("numeroDocumentacao", "(?i)%" + numeroDocumentacaoField.getValue() + "%" );
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
            documentacaoCasosSociaisesDl.removeParameter("numeroDocumentacao");
        }


        documentacaoCasosSociaisesDl.load();
    }

    @Subscribe("documentacaoCasosSociaisesTable.remove")
    protected void onDocumentacaoCasosSociaisesTableRemove(Action.ActionPerformedEvent event) {
        documentacaoCasosSociaisesTableRemove.setConfirmation(false);
        if (documentacaoCasosSociaisesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de documentos dos casos sociais")
                    .withMessage("Deve seleccionar pelo um dos documentos dos casos sociais")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            DocumentacaoCasosSociais user = documentacaoCasosSociaisesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela documento dos casos sociais número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela documento dos casos sociais número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        documentacaoCasosSociaisesTableRemove.execute();
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
            documentacaoCasosSociaisesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            documentacaoCasosSociaisesDl.setMaxResults(0);
        }
        documentacaoCasosSociaisesDl.load();
    }
}