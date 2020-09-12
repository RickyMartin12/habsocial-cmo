package pt.cmolhao.web.documentacao;

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
import pt.cmolhao.entity.Apoios;
import pt.cmolhao.entity.Documentacao;
import pt.cmolhao.entity.RedeTrabalho;
import pt.cmolhao.entity.SubRedeTrabalho;
import pt.cmolhao.web.atendimento.AtendimentoEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@UiController("cmolhao_Documentacao.browse")
@UiDescriptor("documentacao-browse.xml")
@LookupComponent("documentacaosTable")
@LoadDataBeforeShow
public class DocumentacaoBrowse extends StandardLookup<Documentacao> {
    @Inject
    protected GroupTable<Documentacao> documentacaosTable;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected CollectionLoader<Documentacao> documentacaosDl;
    @Inject
    protected LookupField linhasDocumentacao;
    @Inject
    protected TextField<String> numeroDocumentacaoField;
    @Inject
    protected LookupPickerField<SubRedeTrabalho> idSubRedeTrabalhoField;
    @Inject
    protected LookupPickerField<RedeTrabalho> idRedeTrabalhoField;
    @Inject
    protected CollectionContainer<RedeTrabalho> redeTrabalhoDc;
    @Inject
    protected CollectionContainer<SubRedeTrabalho> subRedeTrabalhoDc;
    @Named("documentacaosTable.remove")
    protected RemoveAction<Documentacao> documentacaosTableRemove;

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
        getWindow().setCaption("Listar Documentos");
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

        documentacaosTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(documentacaosTable)
                        .newEntity()
                        .withInitializer(customer -> {

                            customer.setIdRedeTrabalho(idRedeTrabalhoField.getValue());
                            customer.setIdSubRedeTrabalho(idSubRedeTrabalhoField.getValue());
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
                        .withScreenClass(DocumentacaoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );



        documentacaosTable.addGeneratedColumn("file", entity -> {
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

    @Subscribe("linhasDocumentacao")
    protected void onLinhasDocumentacaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            documentacaosDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            documentacaosDl.setMaxResults(0);
        }
        documentacaosDl.load();
    }

    @Subscribe("search_documentacao")
    protected void onSearch_documentacaoClick(Button.ClickEvent event) {

        if (idRedeTrabalhoField.getValue() != null) {
            documentacaosDl.setParameter("idRedeTrabalho",  idRedeTrabalhoField.getValue().getId());
        } else {
            documentacaosDl.removeParameter("idRedeTrabalho");
        }

        if (idSubRedeTrabalhoField.getValue() != null) {
            documentacaosDl.setParameter("idSubRedeTrabalho",  idSubRedeTrabalhoField.getValue().getId());
        } else {
            documentacaosDl.removeParameter("idSubRedeTrabalho");
        }


        if (numeroDocumentacaoField.getValue() != null) {
            if (isNumeric(numeroDocumentacaoField.getValue()))
            {
                documentacaosDl.setParameter("numeroDocumentacao", "(?i)%" + numeroDocumentacaoField.getValue() + "%" );
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
            documentacaosDl.removeParameter("numeroDocumentacao");
        }


        documentacaosDl.load();

        
    }

    @Subscribe("reset_documentacao")
    protected void onReset_documentacaoClick(Button.ClickEvent event) {
        idRedeTrabalhoField.setValue(null);
        idSubRedeTrabalhoField.setValue(null);
        numeroDocumentacaoField.setValue(null);
        documentacaosDl.removeParameter("idRedeTrabalho");
        documentacaosDl.removeParameter("idSubRedeTrabalho");
        documentacaosDl.removeParameter("numeroDocumentacao");
        documentacaosDl.load();
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
                        map.put(item.getNome(), item);
                    }
                }
                idSubRedeTrabalhoField.setEditable(true);
                idSubRedeTrabalhoField.setOptionsMap(map);
            }
        }

    }

    @Subscribe("documentacaosTable.remove")
    protected void onDocumentacaosTableRemove(Action.ActionPerformedEvent event) {
        documentacaosTableRemove.setConfirmation(false);
        if (documentacaosTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de documentos")
                    .withMessage("Deve seleccionar pelo um dos documentos")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Documentacao user = documentacaosTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do documentação número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do documentação número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        documentacaosTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }

}