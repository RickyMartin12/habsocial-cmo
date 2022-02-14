package pt.cmolhao.web.tipo_doc_apoio;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.RespostaSocial;
import pt.cmolhao.entity.TipoAjuda;
import pt.cmolhao.entity.TipoDocApoio;
import pt.cmolhao.web.respostasocial.RespostaSocialEdit;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipoDocApoio.browse")
@UiDescriptor("tipo-doc-apoio-browse.xml")
@LookupComponent("tipoDocApoiosTable")
@LoadDataBeforeShow
public class TipoDocApoioBrowse extends StandardLookup<TipoDocApoio> {
    @Inject
    protected CollectionLoader<TipoDocApoio> tipoDocApoiosDl;
    @Inject
    protected CollectionContainer<TipoAjuda> tipoAjudasDc;
    @Inject
    protected LookupPickerField<TipoAjuda> idTipoapoioField;
    @Inject
    protected TextField<String> descricaoField;
    @Inject
    protected LookupField linhasSubRedeTrabalho;
    @Inject
    protected GroupTable<TipoDocApoio> tipoDocApoiosTable;
    @Named("tipoDocApoiosTable.remove")
    protected RemoveAction<TipoDocApoio> tipoDocApoiosTableRemove;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Dialogs dialogs;


    @Subscribe
    public void onInit(InitEvent event) {

        getWindow().setCaption("Listar Respostas Sociais");

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
        linhasSubRedeTrabalho.setOptionsList(list);

        tipoDocApoiosTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipoDocApoiosTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdTipoApoio(idTipoapoioField.getValue());
                            if (descricaoField.getValue() != null)
                            {
                                customer.setDescricao(descricaoField.getValue());
                            }

                        })
                        .withScreenClass(TipoDocApoioEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Tipos de Documentacao de Apoios");
    }


    @Subscribe("linhasSubRedeTrabalho")
    protected void onLinhasSubRedeTrabalhoValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipoDocApoiosDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipoDocApoiosDl.setMaxResults(0);
        }
        tipoDocApoiosDl.load();
    }



    @Subscribe("reset_tipo_doc_apoio")
    protected void onReset_tipo_doc_apoioClick(Button.ClickEvent event) {
        idTipoapoioField.setValue(null);
        descricaoField.setValue(null);
        tipoDocApoiosDl.removeParameter("idTipoApoio");
        tipoDocApoiosDl.removeParameter("descricao");
        tipoDocApoiosDl.load();
    }

    @Subscribe("search_tipo_doc_apoio")
    protected void onSearch_tipo_doc_apoioClick(Button.ClickEvent event) {
        // ID de Instituição

        if (idTipoapoioField.getValue() != null)
        {
            tipoDocApoiosDl.setParameter("idTipoApoio",  idTipoapoioField.getValue().getId());
        } else {
            tipoDocApoiosDl.removeParameter("idTipoApoio");
        }

        if (descricaoField.getValue() != null)
        {
            tipoDocApoiosDl.setParameter("descricao",  "(?i)%" + descricaoField.getValue() + "%");
        } else {
            tipoDocApoiosDl.removeParameter("descricao");
        }
        tipoDocApoiosDl.load();
    }

    @Subscribe("tipoDocApoiosTable.remove")
    protected void onTipoDocApoiosTableRemove(Action.ActionPerformedEvent event) {
        tipoDocApoiosTableRemove.setConfirmation(false);
        if (tipoDocApoiosTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção do tipo do documento de apoio")
                    .withMessage("Deve seleccionar pelo uma dos tipos de documentos de apoio")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            TipoDocApoio user = tipoDocApoiosTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela do tipo do documento de apoio número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela do tipo do documento de apoio número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        tipoDocApoiosTableRemove.execute();
                                    }), // execute action
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }


}