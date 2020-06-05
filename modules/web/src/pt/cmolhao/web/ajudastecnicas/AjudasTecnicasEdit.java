package pt.cmolhao.web.ajudastecnicas;

import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.AjudasTecnicas;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@UiController("cmolhao_AjudasTecnicas.edit")
@UiDescriptor("ajudas-tecnicas-edit.xml")
@EditedEntityContainer("ajudasTecnicasDc")
@LoadDataBeforeShow
public class AjudasTecnicasEdit extends StandardEditor<AjudasTecnicas> {
    @Inject
    private InstanceContainer<AjudasTecnicas> ajudas_Tecnicas_Dc;
    @Inject
    private DateField<Date> datadisponivelField;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;
    @Inject
    private LookupField<Valencias> idValenciaField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            if (item != null) {
                map.put(item.getDescricaotecnica() + " " , item);
            }
        }
        idValenciaField.setOptionsMap(map);
    }
}