package pt.cmolhao.web.moradores;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.HabitacaoSocial;
import pt.cmolhao.entity.Moradores;
import pt.cmolhao.entity.Utente;

import javax.inject.Inject;
import java.util.*;

@UiController("cmolhao_Moradores.edit")
@UiDescriptor("moradores-edit.xml")
@EditedEntityContainer("moradoresDc")
@LoadDataBeforeShow
public class MoradoresEdit extends StandardEditor<Moradores> {

    @Inject
    protected DateField<Date> dataInicioField;
    @Inject
    protected DateField<Date> dataFimField;
    @Inject
    protected LookupField<HabitacaoSocial> habitacaoSocialField;
    @Inject
    protected LookupField<Utente> utenteField;
    @Inject
    protected CollectionContainer<HabitacaoSocial> habitacaoSocialsDc;
    @Inject
    protected TextField<UUID> idMoradoresField;
    @Inject
    private Notifications notifications;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Adicionar/Editar Morador - " + idMoradoresField.getValue());

        Date data_inicio = dataInicioField.getValue();
        Date date_fim = dataFimField.getValue();

        if (data_inicio != null && date_fim != null)
        {
            dataFimField.setRangeStart(data_inicio);
            dataInicioField.setRangeEnd(date_fim);
        }

        Map<String, HabitacaoSocial> map = new HashMap<>();
        Collection<HabitacaoSocial> customers = habitacaoSocialsDc.getItems();
        for (HabitacaoSocial item : customers) {
            if (item != null) {
                map.put("Habitação Social: ( " +  item.getId()+ " ) - " + item.getBloc().getDesignacao() , item);
            }
        }
        habitacaoSocialField.setOptionsMap(map);


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