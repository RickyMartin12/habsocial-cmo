package pt.cmolhao.web.claso;

import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.MembrosRedesTrabalhoClaso;
import pt.cmolhao.entity.RedeTrabalho;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UiController("cmolhao_MembrosRedesTrabalhoClaso.edit")
@UiDescriptor("membros-redes-trabalho-claso-edit.xml")
@EditedEntityContainer("membrosRedesTrabalhoClasoDc")
@LoadDataBeforeShow
public class MembrosRedesTrabalhoClasoEdit extends StandardEditor<MembrosRedesTrabalhoClaso> {
    @Inject
    protected TextField<UUID> idMembrosTrabalho;
    @Inject
    protected CollectionContainer<RedeTrabalho> redeTrabalhoDc;
    @Inject
    protected LookupPickerField<RedeTrabalho> idRedeTrabalhoField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Adicionar/Editar Membros da Instituição do Claso - " + idMembrosTrabalho.getValue());

        idRedeTrabalhoField.setEditable(false);
        Map<String, RedeTrabalho> map = new HashMap<>();
        Collection<RedeTrabalho> customers = redeTrabalhoDc.getItems();
        for (RedeTrabalho item : customers) {
            if (item.getNome().equals("Claso"))
            {
                map.put(item.getNome(), item);
                idRedeTrabalhoField.setValue(item);
            }
        }

        idRedeTrabalhoField.setOptionsMap(map);



    }


}