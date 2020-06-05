package pt.cmolhao.web.valencias;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Instituicoes;
import pt.cmolhao.entity.Tiposvalencia;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_Valencias.browse")
@UiDescriptor("valencias-browse.xml")
@LookupComponent("valenciasesTable")
@LoadDataBeforeShow
public class ValenciasBrowse extends StandardLookup<Valencias> {
    @Inject
    private LookupField<Instituicoes> idinstituicaoField;

    @Inject
    private Notifications notifications;
    @Inject
    private CollectionLoader<Valencias> valenciasesDl;
    @Inject
    private LookupField<Tiposvalencia> idtipovalenciaField;
    @Inject
    private GroupTable<Valencias> valenciasesTable;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private LookupField linhasValencias;

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
        linhasValencias.setOptionsList(list);


        valenciasesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(valenciasesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdinstituicao(idinstituicaoField.getValue());
                            customer.setIdtipovalencia(idtipovalenciaField.getValue());
                        })
                        .withScreenClass(ValenciasEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }



    @Subscribe("search_valencia")
    public void onSearch_valenciaClick(Button.ClickEvent event) {


        // Id de instituição
        if (idinstituicaoField.getValue() != null) {
            valenciasesDl.setParameter("idinstituicao",  idinstituicaoField.getValue().getId());
        } else {
            valenciasesDl.removeParameter("idinstituicao");
        }

        // Tipo de valencia
        if (idtipovalenciaField.getValue() != null) {
            valenciasesDl.setParameter("idtipovalencia",  idtipovalenciaField.getValue().getId());
        } else {
            valenciasesDl.removeParameter("idtipovalencia");
        }


        valenciasesDl.load();
    }

    @Subscribe("reset_valencia")
    public void onReset_valenciaClick(Button.ClickEvent event) {
        idinstituicaoField.setValue(null);
        idtipovalenciaField.setValue(null);
        linhasValencias.setValue(null);
        valenciasesDl.removeParameter("idinstituicao");
        valenciasesDl.removeParameter("idtipovalencia");
        valenciasesDl.setMaxResults(0);
        valenciasesDl.load();

    }

    @Subscribe("linhasValencias")
    public void onLinhasValenciasValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            valenciasesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            valenciasesDl.setMaxResults(0);
        }
        valenciasesDl.load();
    }


}