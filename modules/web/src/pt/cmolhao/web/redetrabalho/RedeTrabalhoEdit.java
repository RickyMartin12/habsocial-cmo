package pt.cmolhao.web.redetrabalho;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Documentacao;
import pt.cmolhao.entity.RedeTrabalho;
import pt.cmolhao.entity.SubRedeTrabalho;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@UiController("cmolhao_RedeTrabalho.edit")
@UiDescriptor("rede-trabalho-edit.xml")
@EditedEntityContainer("redeTrabalhoDc")
@LoadDataBeforeShow
public class RedeTrabalhoEdit extends StandardEditor<RedeTrabalho> {
    @Inject
    protected TextField<UUID> idRedeTrabalhoField;
    @Inject
    protected Table<Documentacao> documentacaosTable;

    @Inject
    protected UiComponents uiComponents;
    @Named("documentacaosTable.remove")
    protected RemoveAction<Documentacao> documentacaosTableRemove;
    @Named("subRedeTrabalhosTable.remove")
    protected RemoveAction<SubRedeTrabalho> subRedeTrabalhosTableRemove;
    @Inject
    protected Table<SubRedeTrabalho> subRedeTrabalhosTable;

    @Inject
    private ExportDisplay exportDisplay;

    @Inject
    private Dialogs dialogs;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Rede de Trabalho - " + idRedeTrabalhoField.getValue());
    }

    @Subscribe
    protected void onInit(InitEvent event) {
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

    @Subscribe("subRedeTrabalhosTable.remove")
    protected void onSubRedeTrabalhosTableRemove(Action.ActionPerformedEvent event) {
        subRedeTrabalhosTableRemove.setConfirmation(false);
        if (subRedeTrabalhosTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção de sub de rede de trabalho")
                    .withMessage("Deve seleccionar pelo uma das sub redes de trabalho")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            SubRedeTrabalho user = subRedeTrabalhosTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela sub rede de trabalho número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela sub rede de trabalho número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        subRedeTrabalhosTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}