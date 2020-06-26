package pt.cmolhao.web.moradores;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.HabitacaoSocial;
import pt.cmolhao.entity.Moradores;
import pt.cmolhao.entity.Utente;

import javax.inject.Inject;
import java.sql.Date;
import java.util.*;

@UiController("cmolhao_Moradores.browse")
@UiDescriptor("moradores-browse.xml")
@LookupComponent("moradoresesTable")
@LoadDataBeforeShow
public class MoradoresBrowse extends StandardLookup<Moradores> {
    @Inject
    protected LookupField linhasMoradeores;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected LookupField<HabitacaoSocial> habitacaoSocialField;
    @Inject
    protected CollectionContainer<HabitacaoSocial> habitacaoSocialsDc;
    @Inject
    protected CollectionLoader<Moradores> moradoresesDl;
    @Inject
    protected LookupField<Utente> utenteField;
    @Inject
    protected DateField<Date> dataInicioField;
    @Inject
    protected DateField<Date> dataFimField;
    @Inject
    protected GroupTable<Moradores> moradoresesTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private DataManager dataManager;

    @Subscribe("linhasMoradeores")
    protected void onLinhasMoradeoresValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            moradoresesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            moradoresesDl.setMaxResults(0);
        }
        moradoresesDl.load();
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
        linhasMoradeores.setOptionsList(list);

        moradoresesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(moradoresesTable)
                        .newEntity()
                        .withInitializer(customer -> {

                            customer.setHabitacaoSocial(habitacaoSocialField.getValue());
                            customer.setUtente(utenteField.getValue());
                            if (dataInicioField.getValue() != null)
                            {
                                customer.setDataInicio(dataInicioField.getValue());
                            }
                            if (dataFimField.getValue() != null)
                            {
                                customer.setDateFim(dataFimField.getValue());
                            }
                        })
                        .withScreenClass(MoradoresEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Moradores");
        Map<String, HabitacaoSocial> map = new HashMap<>();
        Collection<HabitacaoSocial> customers = habitacaoSocialsDc.getItems();
        for (HabitacaoSocial item : customers) {
            if (item != null) {
                map.put("Habitação Social: ( " +  item.getId()+ " ) - " + item.getBloc().getDesignacao() , item);
            }
        }
        habitacaoSocialField.setOptionsMap(map);
    }

    public Component MoradoresHabitacaoSocial(Moradores entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getHabitacaoSocial() != null)
        {
            label.setValue("Habitação Social ( " +  entity.getHabitacaoSocial().getId() + " ) - " + entity.getHabitacaoSocial().getBloc().getDesignacao());
            return label;
        }
        return null;
    }

    @Subscribe("reset_moradores")
    protected void onReset_moradoresClick(Button.ClickEvent event) {
            habitacaoSocialField.setValue(null);
            utenteField.setValue(null);
            dataFimField.setValue(null);
            dataInicioField.setValue(null);
            linhasMoradeores.setValue(null);
            moradoresesDl.removeParameter("habitacaoSocial");
            moradoresesDl.removeParameter("utente");
            moradoresesDl.removeParameter("dataInicio");
            moradoresesDl.removeParameter("dateFim");
            moradoresesDl.setMaxResults(0);
            moradoresesDl.load();
    }

    @Subscribe("search_moradores")
    protected void onSearch_moradoresClick(Button.ClickEvent event) {
        if (habitacaoSocialField.getValue() != null)
        {
            moradoresesDl.setParameter("habitacaoSocial",  habitacaoSocialField.getValue().getId());
        } else {
            moradoresesDl.removeParameter("habitacaoSocial");
        }

        if (utenteField.getValue() != null)
        {
            moradoresesDl.setParameter("utente",  utenteField.getValue().getId());
        } else {
            moradoresesDl.removeParameter("utente");
        }

        if (dataInicioField.getValue() != null)
        {
            moradoresesDl.setParameter("dataInicio", dataInicioField.getValue());
        }
        else
        {
            moradoresesDl.removeParameter("dataInicio");
        }

        if (dataFimField.getValue() != null)
        {
            moradoresesDl.setParameter("dateFim", dataFimField.getValue());
        }
        else
        {
            moradoresesDl.removeParameter("dateFim");
        }

        moradoresesDl.load();


    }

    @Subscribe("dataInicioField")
    protected void onDataInicioFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated())
        {
            if (event.getValue() != null)
            {
                dataFimField.setRangeStart(event.getValue());
            }
        }
    }

    @Subscribe("dataFimField")
    protected void onDataFimFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                dataInicioField.setRangeEnd(event.getValue());
            }
        }
    }
}