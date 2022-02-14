package pt.cmolhao.web.documentacao_hab_social_programa_apoio_arrendamento_1_fase;

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
import pt.cmolhao.web.documentacao_habitacao_social.DocumentacaoHabitacaoSocialEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@UiController("cmolhao_DocumentacaoHabSocialProgramaApoioArrendamento1Fase.browse")
@UiDescriptor("documentacao-hab-social-programa-apoio-arrendamento1-fase-browse.xml")
@LookupComponent("documentacaoHabSocialProgramaApoioArrendamento1FasesTable")
@LoadDataBeforeShow
public class DocumentacaoHabSocialProgramaApoioArrendamento1FaseBrowse extends StandardLookup<DocumentacaoHabSocialProgramaApoioArrendamento1Fase> {

    @Inject
    protected LookupPickerField<HabitacaoSocial> habitacaoSocialField;
    @Inject
    protected TextField<String> numeroDocumentacaoField;
    @Inject
    protected CollectionLoader<DocumentacaoHabSocialProgramaApoioArrendamento1Fase> documentacaoHabSocialProgramaApoioArrendamento1FasesDl;
    @Inject
    protected GroupTable<DocumentacaoHabSocialProgramaApoioArrendamento1Fase> documentacaoHabSocialProgramaApoioArrendamento1FasesTable;
    @Named("documentacaoHabSocialProgramaApoioArrendamento1FasesTable.remove")
    protected RemoveAction<DocumentacaoHabSocialProgramaApoioArrendamento1Fase> documentacaoHabSocialProgramaApoioArrendamento1FasesTableRemove;
    @Inject
    protected LookupField linhasDocumentacao;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected CollectionContainer<RedeTrabalho> redeTrabalhoDc;
    @Inject
    protected CollectionContainer<SubRedeTrabalho> subRedeTrabalhoDc;
    @Inject
    protected CollectionContainer<HabitacaoSocial> habitacaoSocialsDc;
    @Inject
    protected LookupPickerField<RedeTrabalho> idRedeTrabalhoField;
    @Inject
    protected LookupPickerField<SubRedeTrabalho> idSubRedeTrabalhoField;

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
        getWindow().setCaption("Listar Documentos da Habitação Social no Programação de Apoio de Arrendamento na 1ª Fase");

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
                    if (item2.getNome().equals("1ª fase"))
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

        documentacaoHabSocialProgramaApoioArrendamento1FasesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(documentacaoHabSocialProgramaApoioArrendamento1FasesTable)
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
                        .withScreenClass(DocumentacaoHabSocialProgramaApoioArrendamento1FaseEdit.class)    // specific editor screen
                        .build()
                        .show()
        );



        documentacaoHabSocialProgramaApoioArrendamento1FasesTable.addGeneratedColumn("file", entity -> {
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
        documentacaoHabSocialProgramaApoioArrendamento1FasesDl.removeParameter("idHabitacaoSocial");
        documentacaoHabSocialProgramaApoioArrendamento1FasesDl.removeParameter("numDocumento");
        documentacaoHabSocialProgramaApoioArrendamento1FasesDl.load();
    }

    @Subscribe("search_documentacao")
    protected void onSearch_documentacaoClick(Button.ClickEvent event) {
        if (habitacaoSocialField.getValue() != null) {
            documentacaoHabSocialProgramaApoioArrendamento1FasesDl.setParameter("idHabitacaoSocial",  habitacaoSocialField.getValue().getId());
        } else {
            documentacaoHabSocialProgramaApoioArrendamento1FasesDl.removeParameter("idHabitacaoSocial");
        }


        if (idRedeTrabalhoField.getValue() != null) {
            documentacaoHabSocialProgramaApoioArrendamento1FasesDl.setParameter("idRedeTrabalho",  idRedeTrabalhoField.getValue().getId());
        } else {
            documentacaoHabSocialProgramaApoioArrendamento1FasesDl.removeParameter("idRedeTrabalho");
        }

        if (idSubRedeTrabalhoField.getValue() != null) {
            documentacaoHabSocialProgramaApoioArrendamento1FasesDl.setParameter("idSubRedeTrabalho",  idSubRedeTrabalhoField.getValue().getId());
        } else {
            documentacaoHabSocialProgramaApoioArrendamento1FasesDl.removeParameter("idSubRedeTrabalho");
        }


        if (numeroDocumentacaoField.getValue() != null) {
            if (isNumeric(numeroDocumentacaoField.getValue()))
            {
                documentacaoHabSocialProgramaApoioArrendamento1FasesDl.setParameter("numDocumento", "(?i)%" + numeroDocumentacaoField.getValue() + "%" );
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
            documentacaoHabSocialProgramaApoioArrendamento1FasesDl.removeParameter("numDocumento");
        }


        documentacaoHabSocialProgramaApoioArrendamento1FasesDl.load();
    }

    @Subscribe("documentacaoHabSocialProgramaApoioArrendamento1FasesTable.remove")
    protected void onDocumentacaoHabSocialProgramaApoioArrendamento1FasesTableRemove(Action.ActionPerformedEvent event) {
        documentacaoHabSocialProgramaApoioArrendamento1FasesTableRemove.setConfirmation(false);
        if (documentacaoHabSocialProgramaApoioArrendamento1FasesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de documentos de habitação social no Programa de Apoio ao Arrendamento na 1ª fase")
                    .withMessage("Deve seleccionar pelo um dos documentos de habitação social no Programa de Apoio ao Arrendamento na 1ª fase")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            DocumentacaoHabSocialProgramaApoioArrendamento1Fase user = documentacaoHabSocialProgramaApoioArrendamento1FasesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do documentação de habitação social no Programa de Apoio ao Arrendamento na 1ª fase número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do documentação de habitação social no Programa de Apoio ao Arrendamento na 1ª fase número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        documentacaoHabSocialProgramaApoioArrendamento1FasesTableRemove.execute();
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
            documentacaoHabSocialProgramaApoioArrendamento1FasesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            documentacaoHabSocialProgramaApoioArrendamento1FasesDl.setMaxResults(0);
        }
        documentacaoHabSocialProgramaApoioArrendamento1FasesDl.load();
    }
}