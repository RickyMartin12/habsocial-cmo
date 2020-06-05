package pt.cmolhao.web.fotosvalencia;

import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.FotosValencia;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@UiController("cmolhao_FotosValencia.edit")
@UiDescriptor("fotos-valencia-edit.xml")
@EditedEntityContainer("fotosValenciaDc")
@LoadDataBeforeShow
public class FotosValenciaEdit extends StandardEditor<FotosValencia> {

    @Inject
    private LookupField<Valencias> idvalenciaField;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            map.put(item.getDescricaotecnica() + " " , item);
        }
        idvalenciaField.setOptionsMap(map);
    }
   
    

}