package pt.cmolhao.web.utentesrelacionados;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.RemoveAction;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.TipoRelacionamentoUtentes;
import pt.cmolhao.entity.Utente;
import pt.cmolhao.entity.UtentesRelacionados;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.List;

@UiController("cmolhao_UtentesRelacionados.browse")
@UiDescriptor("utentes-relacionados-browse.xml")
@LookupComponent("utentesRelacionadosesTable")
@LoadDataBeforeShow
public class UtentesRelacionadosBrowse extends StandardLookup<UtentesRelacionados> {
    @Inject
    protected CollectionLoader<UtentesRelacionados> utentesRelacionadosesDl;
    @Inject
    protected LookupField linhasUtentesRelacionados;
    @Inject
    protected GroupTable<UtentesRelacionados> utentesRelacionadosesTable;
    @Inject
    protected LookupField<Utente> idUtenteRel1Field;
    @Inject
    protected LookupField<Utente> idUtenteRel2Field;
    @Inject
    protected LookupField<TipoRelacionamentoUtentes> idTipoRelUtentesField;
    @Inject
    protected CollectionLoader<TipoRelacionamentoUtentes> tipoRelacionamentoUtentesesDl;
    @Inject
    protected CollectionContainer<Utente> utentesDc1;
    @Inject
    protected CollectionContainer<Utente> utentesDc2;
    @Named("utentesRelacionadosesTable.remove")
    protected RemoveAction<UtentesRelacionados> utentesRelacionadosesTableRemove;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Dialogs dialogs;


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Utentes Relacionados");
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
        linhasUtentesRelacionados.setOptionsList(list);

        utentesRelacionadosesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(utentesRelacionadosesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdTipoRelUtentes(idTipoRelUtentesField.getValue());
                            customer.setIdUtenteRel1(idUtenteRel1Field.getValue());
                            customer.setIdUtenteRel2(idUtenteRel2Field.getValue());
                        })
                        .withScreenClass(UtentesRelacionadosEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("linhasUtentesRelacionados")
    protected void onLinhasUtentesRelacionadosValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            utentesRelacionadosesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            utentesRelacionadosesDl.setMaxResults(0);
        }
        utentesRelacionadosesDl.load();
    }

    @Subscribe("reset_utentes_relacionados")
    protected void onReset_utentes_relacionadosClick(Button.ClickEvent event) {
        linhasUtentesRelacionados.setValue(null);
        idUtenteRel1Field.setValue(null);
        idUtenteRel2Field.setValue(null);
        idTipoRelUtentesField.setValue(null);
        utentesRelacionadosesDl.removeParameter("idUtenteRel1");
        utentesRelacionadosesDl.removeParameter("idUtenteRel2");
        utentesRelacionadosesDl.removeParameter("idTipoRelUtentes");
        utentesRelacionadosesDl.setMaxResults(0);
        utentesRelacionadosesDl.load();
    }

    @Subscribe("search_utentes_relacionados")
    protected void onSearch_utentes_relacionadosClick(Button.ClickEvent event) {
        if(idUtenteRel1Field.getValue() != null)
        {
            utentesRelacionadosesDl.setParameter("idUtenteRel1",  idUtenteRel1Field.getValue().getId());
        }
        else
        {
            utentesRelacionadosesDl.removeParameter("idUtenteRel1");
        }

        if(idUtenteRel2Field.getValue() != null)
        {
            utentesRelacionadosesDl.setParameter("idUtenteRel2",  idUtenteRel2Field.getValue().getId());
        }
        else
        {
            utentesRelacionadosesDl.removeParameter("idUtenteRel2");
        }

        if(idTipoRelUtentesField.getValue() != null)
        {
            utentesRelacionadosesDl.setParameter("idTipoRelUtentes",  idTipoRelUtentesField.getValue().getId());
        }
        else
        {
            utentesRelacionadosesDl.removeParameter("idTipoRelUtentes");
        }


        utentesRelacionadosesDl.load();

    }

    @Subscribe("idUtenteRel1Field")
    protected void onIdUtenteRel1FieldValueChange(HasValue.ValueChangeEvent<Utente> event) {
        Map<String, Utente> map = new HashMap<>();
        Collection<Utente> customers = utentesDc2.getItems();
        for (Utente item : customers) {
            if (item != null) {
                if (event.getValue() != null)
                {
                    if (!event.getValue().getId().equals(item.getId()))
                    {
                        if (item.getNumContribuinte() != null && item.getNome() != null)
                        {
                            map.put(" ( " + item.getNumContribuinte() + "  ) " +  item.getNome(), item);
                        }
                        else if (item.getNumContribuinte() != null && item.getNome() == null)
                        {
                            map.put(" ( " + item.getNumContribuinte() + "  ) ", item);
                        }
                        else if (item.getNumContribuinte() == null && item.getNome() != null)
                        {
                            map.put(item.getNome(), item);
                        }


                    }
                }
            }
        }
        idUtenteRel2Field.setOptionsMap(map);
    }

    @Subscribe("idUtenteRel2Field")
    protected void onIdUtenteRel2FieldValueChange(HasValue.ValueChangeEvent<Utente> event) {
        Map<String, Utente> map = new HashMap<>();
        Collection<Utente> customers = utentesDc1.getItems();
        for (Utente item : customers) {
            if (item != null) {
                if (event.getValue() != null)
                {
                    if (!event.getValue().getId().equals(item.getId()))
                    {
                        if (item.getNumContribuinte() != null && item.getNome() != null)
                        {
                            map.put(" ( " + item.getNumContribuinte() + "  ) " +  item.getNome(), item);
                        }
                        else if (item.getNumContribuinte() != null && item.getNome() == null)
                        {
                            map.put(" ( " + item.getNumContribuinte() + "  ) ", item);
                        }
                        else if (item.getNumContribuinte() == null && item.getNome() != null)
                        {
                            map.put(item.getNome(), item);
                        }

                    }
                }
            }
        }
        idUtenteRel1Field.setOptionsMap(map);
    }

    @Subscribe("utentesRelacionadosesTable.remove")
    protected void onUtentesRelacionadosesTableRemove(Action.ActionPerformedEvent event) {
        utentesRelacionadosesTableRemove.setConfirmation(false);
        if (utentesRelacionadosesTable.getSelected().isEmpty())
        {
            dialogs.createOptionDialog()
                    .withCaption("Selecção dos utentes relacionados")
                    .withMessage("Deve seleccionar pelo um dos utentes relacionados")
                    .withActions(
                            new DialogAction(DialogAction.Type.CLOSE)
                    )
                    .show();
        }
        else
        {
            UtentesRelacionados user = utentesRelacionadosesTable.getSingleSelected();
            dialogs.createOptionDialog()
                    .withCaption("Remover a linha da tabela dos utentes relacionados número '"+user.getId()+"' ")
                    .withMessage("Tens a certeza que quer remover esta linha da tabela dos utentes relacionados número '"+user.getId()+"'?")
                    .withActions(
                            new DialogAction(DialogAction.Type.YES)
                                    .withHandler(e ->
                                    {
                                        utentesRelacionadosesTableRemove.execute();
                                    }),
                            new DialogAction(DialogAction.Type.NO)
                    )
                    .show();
        }
    }
}