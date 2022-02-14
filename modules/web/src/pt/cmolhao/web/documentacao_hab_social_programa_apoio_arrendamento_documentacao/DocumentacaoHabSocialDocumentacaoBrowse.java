package pt.cmolhao.web.documentacao_hab_social_programa_apoio_arrendamento_documentacao;

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
import pt.cmolhao.web.documentacao_hab_social_programa_apoio_arrendamento_3_fase.DocumentacaoHabSocialProgramaApoioArrendamento3FaseEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@UiController("cmolhao_DocumentacaoHabSocialDocumentacao.browse")
@UiDescriptor("documentacao-hab-social-documentacao-browse.xml")
@LookupComponent("documentacaoHabSocialDocumentacaosTable")
@LoadDataBeforeShow
public class DocumentacaoHabSocialDocumentacaoBrowse extends StandardLookup<DocumentacaoHabSocialDocumentacao> {
    @Inject
    protected CollectionLoader<DocumentacaoHabSocialDocumentacao> documentacaoHabSocialDocumentacaosDl;
    @Inject
    protected CollectionContainer<RedeTrabalho> redeTrabalhoDc;
    @Inject
    protected CollectionContainer<SubRedeTrabalho> subRedeTrabalhoDc;
    @Inject
    protected CollectionContainer<HabitacaoSocial> habitacaoSocialsDc;
    @Inject
    protected LookupPickerField<HabitacaoSocial> habitacaoSocialField;
    @Inject
    protected LookupPickerField<RedeTrabalho> idRedeTrabalhoField;
    @Inject
    protected LookupPickerField<SubRedeTrabalho> idSubRedeTrabalhoField;
    @Inject
    protected TextField<String> numeroDocumentacaoField;
    @Inject
    protected LookupField linhasDocumentacao;
    @Inject
    protected GroupTable<DocumentacaoHabSocialDocumentacao> documentacaoHabSocialDocumentacaosTable;
    @Named("documentacaoHabSocialDocumentacaosTable.remove")
    protected RemoveAction<DocumentacaoHabSocialDocumentacao> documentacaoHabSocialDocumentacaosTableRemove;

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
        getWindow().setCaption("Listar Documentos da Habitação Social no Programação de Apoio de Arrendamento na Documentação");

        idRedeTrabalhoField.setEditable(false);

        Map<String, RedeTrabalho> map = new HashMap<>();
        Collection<RedeTrabalho> customers = redeTrabalhoDc.getItems();
        for (RedeTrabalho item : customers) {
            if (item.getNome().equals("Programa de Apoio ao Arrendamento"))
            {
                idRedeTrabalhoField.setValue(item);
                map.put(item.getNome(), item);
            }
        }

        idRedeTrabalhoField.setOptionsMap(map);

        if(idRedeTrabalhoField.getValue() != null)
        {
            Map<String, SubRedeTrabalho> map2 = new HashMap<>();
            Collection<SubRedeTrabalho> customers2 = subRedeTrabalhoDc.getItems();
            for (SubRedeTrabalho item2 : customers2) {
                if (item2.getIdRedeTrabalho().getId().equals(idRedeTrabalhoField.getValue().getId()))
                {
                    if (item2.getNome().equals("Documentação"))
                    {
                        idSubRedeTrabalhoField.setValue(item2);
                        map2.put(item2.getNome(), item2);
                    }

                }
            }
            idSubRedeTrabalhoField.setEditable(false);
            idSubRedeTrabalhoField.setOptionsMap(map2);
        }
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

        documentacaoHabSocialDocumentacaosTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(documentacaoHabSocialDocumentacaosTable)
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
                        .withScreenClass(DocumentacaoHabSocialDocumentacaoEdit.class)    // specific editor screen
                        .build()
                        .show()
        );



        documentacaoHabSocialDocumentacaosTable.addGeneratedColumn("file", entity -> {
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
        numeroDocumentacaoField.setValue(null);
        documentacaoHabSocialDocumentacaosDl.removeParameter("idHabitacaoSocial");
        documentacaoHabSocialDocumentacaosDl.removeParameter("numDocumento");
        documentacaoHabSocialDocumentacaosDl.load();
    }

    @Subscribe("search_documentacao")
    protected void onSearch_documentacaoClick(Button.ClickEvent event) {
        if (habitacaoSocialField.getValue() != null) {
            documentacaoHabSocialDocumentacaosDl.setParameter("idHabitacaoSocial",  habitacaoSocialField.getValue().getId());
        } else {
            documentacaoHabSocialDocumentacaosDl.removeParameter("idHabitacaoSocial");
        }


        if (idRedeTrabalhoField.getValue() != null) {
            documentacaoHabSocialDocumentacaosDl.setParameter("idRedeTrabalho",  idRedeTrabalhoField.getValue().getId());
        } else {
            documentacaoHabSocialDocumentacaosDl.removeParameter("idRedeTrabalho");
        }

        if (idSubRedeTrabalhoField.getValue() != null) {
            documentacaoHabSocialDocumentacaosDl.setParameter("idSubRedeTrabalho",  idSubRedeTrabalhoField.getValue().getId());
        } else {
            documentacaoHabSocialDocumentacaosDl.removeParameter("idSubRedeTrabalho");
        }


        if (numeroDocumentacaoField.getValue() != null) {
            if (isNumeric(numeroDocumentacaoField.getValue()))
            {
                documentacaoHabSocialDocumentacaosDl.setParameter("numDocumento", "(?i)%" + numeroDocumentacaoField.getValue() + "%" );
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
            documentacaoHabSocialDocumentacaosDl.removeParameter("numDocumento");
        }


        documentacaoHabSocialDocumentacaosDl.load();
    }

    @Subscribe("linhasDocumentacao")
    protected void onLinhasDocumentacaoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            documentacaoHabSocialDocumentacaosDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            documentacaoHabSocialDocumentacaosDl.setMaxResults(0);
        }
        documentacaoHabSocialDocumentacaosDl.load();
    }

    @Subscribe("documentacaoHabSocialDocumentacaosTable.remove")
    protected void onDocumentacaoHabSocialDocumentacaosTableRemove(Action.ActionPerformedEvent event) {
        documentacaoHabSocialDocumentacaosTableRemove.setConfirmation(false);
        if (documentacaoHabSocialDocumentacaosTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de documentos de habitação social no Programa de Apoio ao Arrendamento na Documentação")
                    .withMessage("Deve seleccionar pelo um dos documentos de habitação social no Programa de Apoio ao Arrendamento na Documentação")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            DocumentacaoHabSocialDocumentacao user = documentacaoHabSocialDocumentacaosTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do documentação de habitação social no Programa de Apoio ao Arrendamento na Documentação número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do documentação de habitação social no Programa de Apoio ao Arrendamento na Documentação número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        documentacaoHabSocialDocumentacaosTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}