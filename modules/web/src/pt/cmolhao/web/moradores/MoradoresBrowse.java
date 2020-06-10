package pt.cmolhao.web.moradores;

import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.HabitacaoSocial;
import pt.cmolhao.entity.Moradores;
import pt.cmolhao.entity.ProjectosEmAprovacao;
import pt.cmolhao.entity.ProjectosIntervencao;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    @Subscribe("linhasMoradeores")
    protected void onLinhasMoradeoresValueChange(HasValue.ValueChangeEvent event) {
        
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
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
}