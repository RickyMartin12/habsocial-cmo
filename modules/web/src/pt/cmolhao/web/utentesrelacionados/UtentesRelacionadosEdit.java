package pt.cmolhao.web.utentesrelacionados;

import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Utente;
import pt.cmolhao.entity.UtentesRelacionados;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UiController("cmolhao_UtentesRelacionados.edit")
@UiDescriptor("utentes-relacionados-edit.xml")
@EditedEntityContainer("utentesRelacionadosDc")
@LoadDataBeforeShow
public class UtentesRelacionadosEdit extends StandardEditor<UtentesRelacionados> {
    @Inject
    protected LookupPickerField<Utente> idUtenteRel1Field;
    @Inject
    protected LookupPickerField<Utente> idUtenteRel2Field;
    @Inject
    protected CollectionContainer<Utente> utentesDc1;
    @Inject
    protected CollectionContainer<Utente> utentesDc2;
    @Inject
    protected TextField<UUID> idUtentesRelacionadosField;

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


    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Utentes Relacionados - " + idUtentesRelacionadosField.getValue());

        // Load Utentes 1

        Map<String, Utente> map = new HashMap<>();
        Collection<Utente> customers = utentesDc2.getItems();
        for (Utente item : customers) {
            if (item != null) {
                if (idUtenteRel1Field.getValue() != null)
                {
                    if (!idUtenteRel1Field.getValue().getId().equals(item.getId()))
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

        // Load Utentes 2

        Map<String, Utente> map2 = new HashMap<>();
        Collection<Utente> customers2 = utentesDc1.getItems();

        for (Utente item : customers2) {
            if (item != null) {
                if (idUtenteRel2Field.getValue() != null)
                {
                    if (!idUtenteRel2Field.getValue().getId().equals(item.getId()))
                    {
                        if (item.getNumContribuinte() != null && item.getNome() != null)
                        {
                            map2.put(" ( " + item.getNumContribuinte() + "  ) " +  item.getNome(), item);
                        }
                        else if (item.getNumContribuinte() != null && item.getNome() == null)
                        {
                            map2.put(" ( " + item.getNumContribuinte() + "  ) ", item);
                        }
                        else if (item.getNumContribuinte() == null && item.getNome() != null)
                        {
                            map2.put(item.getNome(), item);
                        }

                    }
                }
            }
        }
        idUtenteRel1Field.setOptionsMap(map2);


    }
}