package pt.cmolhao.web.apoios;

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
import pt.cmolhao.entity.*;
import pt.cmolhao.web.ajudastecnicas.AjudasTecnicasEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UiController("cmolhao_Apoios.browse")
@UiDescriptor("apoios-browse.xml")
@LookupComponent("apoiosesTable")
@LoadDataBeforeShow
public class ApoiosBrowse extends StandardLookup<Apoios> {
    @Inject
    protected CollectionLoader<Apoios> apoiosesDl;
    @Inject
    protected LookupField<Instituicoes> idInstituicaoField;
    @Inject
    protected LookupField<Utente> utenteField;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected LookupField linhasApoio;
    @Inject
    protected GroupTable<Apoios> apoiosesTable;
    @Inject
    protected TextField<String> numProcessoField;
    @Named("apoiosesTable.remove")
    protected RemoveAction<Apoios> apoiosesTableRemove;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Notifications notifications;

    @Inject
    private Dialogs dialogs;

    @Inject
    private ExportDisplay exportDisplay;



    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Apoios");
    }


    public Component generateValorApoio(Apoios entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getValorApoio() != null)
        {
            label.setValue(entity.getValorApoio() + " €");
            return label;
        }
        return null;
    }

    @Subscribe
    public void onInit(InitEvent event) {

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
        linhasApoio.setOptionsList(list);

        apoiosesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(apoiosesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdInstituicao(idInstituicaoField.getValue());
                            customer.setIdUtente(utenteField.getValue());
                            if(numProcessoField.getValue() != null)
                            {
                                customer.setNumProcesso(Integer.valueOf(numProcessoField.getValue()));
                            }
                        })
                        .withScreenClass(ApoiosEdit.class)    // specific editor screen
                        .build()
                        .show()
        );

        apoiosesTable.addGeneratedColumn("file", entity -> {
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

    @Subscribe("reset_apoios")
    protected void onReset_apoiosClick(Button.ClickEvent event) {
        idInstituicaoField.setValue(null);
        utenteField.setValue(null);
        numProcessoField.setValue(null);
        apoiosesDl.removeParameter("idInstituicao");
        apoiosesDl.removeParameter("idUtente");
        apoiosesDl.removeParameter("numProcesso");

        apoiosesDl.load();
    }


    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe("search_apoios")
    protected void onSearch_apoiosClick(Button.ClickEvent event) {


        // ID do Instituição
        if (idInstituicaoField.getValue() != null) {
            apoiosesDl.setParameter("idInstituicao",  idInstituicaoField.getValue().getId());
        } else {
            apoiosesDl.removeParameter("idInstituicao");
        }


        // ID do Utente
        if (utenteField.getValue() != null) {
            apoiosesDl.setParameter("idUtente",  utenteField.getValue().getId());
        } else {
            apoiosesDl.removeParameter("idUtente");
        }

        // Arrend
        if (numProcessoField.getValue() != null) {
            if (isNumeric(numProcessoField.getValue()))
            {
                apoiosesDl.setParameter("numProcesso", "(?i)%" + Integer.valueOf(numProcessoField.getValue()) + "%");
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do numero de processo</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }

        } else {
            apoiosesDl.removeParameter("numProcesso");
        }


        apoiosesDl.load();
    }

    @Subscribe("linhasApoio")
    protected void onLinhasApoioValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            apoiosesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            apoiosesDl.setMaxResults(0);
        }
        apoiosesDl.load();
    }

    @Subscribe("apoiosesTable.remove")
    protected void onApoiosesTableRemove(Action.ActionPerformedEvent event) {
        apoiosesTableRemove.setConfirmation(false);
        if (apoiosesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de apoios")
                    .withMessage("Deve seleccionar pelo um dos apoios")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            Apoios user = apoiosesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do apoio número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do apoio número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        apoiosesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}