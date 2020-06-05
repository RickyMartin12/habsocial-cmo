package pt.cmolhao.web.ajudastecnicas;

import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.AjudasTecnicas;
import pt.cmolhao.entity.FotosValencia;
import pt.cmolhao.entity.Valencias;
import pt.cmolhao.web.fotosvalencia.FotosValenciaEdit;

import javax.inject.Inject;
import java.sql.Date;
import java.util.*;

@UiController("cmolhao_AjudasTecnicas.browse")
@UiDescriptor("ajudas-tecnicas-browse.xml")
@LookupComponent("ajudasTecnicasesTable")
@LoadDataBeforeShow
public class AjudasTecnicasBrowse extends StandardLookup<AjudasTecnicas> {
    @Inject
    private GroupTable<AjudasTecnicas> ajudasTecnicasesTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private CollectionLoader<AjudasTecnicas> ajudasTecnicasesDl;
    @Inject
    private LookupField<Valencias> idvalenciaField;
    @Inject
    private DateField<Date> datadisponivelField;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    private LookupField linhasAjudasTecnicas;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;

    public Component generateValenciasDescricao(AjudasTecnicas entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getIdValencia() != null)
        {
            label.setValue(entity.getIdValencia().getDescricaotecnica());
            return label;
        }
        return null;
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            if (item != null) {
                map.put(item.getDescricaotecnica() + " " , item);
            }
        }
        idvalenciaField.setOptionsMap(map);
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
        linhasAjudasTecnicas.setOptionsList(list);

        ajudasTecnicasesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(ajudasTecnicasesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdValencia(idvalenciaField.getValue());
                            customer.setDatadisponivel(datadisponivelField.getValue());
                        })
                        .withScreenClass(AjudasTecnicasEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("search_ajudas_tecnicas")
    public void onSearch_ajudas_tecnicasClick(Button.ClickEvent event) {
        // ID de valencia
        if (idvalenciaField.getValue() != null) {


            ajudasTecnicasesDl.setParameter("idValencia",  idvalenciaField.getValue().getId());
        } else {
            ajudasTecnicasesDl.removeParameter("idValencia");
        }

        // Data Disponivel

        if (datadisponivelField.getValue() != null)
        {
            ajudasTecnicasesDl.setParameter("datadisponivel",  datadisponivelField.getValue());
        } else {
            ajudasTecnicasesDl.removeParameter("datadisponivel");
        }

        ajudasTecnicasesDl.load();
    }

    @Subscribe("reset_search_ajudas_tecnicas")
    public void onReset_search_ajudas_tecnicasClick(Button.ClickEvent event) {
        idvalenciaField.setValue(null);
        datadisponivelField.setValue(null);
        linhasAjudasTecnicas.setValue(null);
        ajudasTecnicasesDl.removeParameter("datadisponivel");
        ajudasTecnicasesDl.removeParameter("idValencia");
        ajudasTecnicasesDl.setMaxResults(0);
        ajudasTecnicasesDl.load();
    }

    @Subscribe("linhasAjudasTecnicas")
    public void onLinhasAjudasTecnicasValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            ajudasTecnicasesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            ajudasTecnicasesDl.setMaxResults(0);
        }
        ajudasTecnicasesDl.load();
    }


}