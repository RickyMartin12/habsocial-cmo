package pt.cmolhao.web.nucleo_executivo_elementos_membros;

import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.MembrosRedesTrabalhoNuceloExecutivo;
import pt.cmolhao.entity.RedeTrabalho;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UiController("cmolhao_MembrosRedesTrabalhoNuceloExecutivo.edit")
@UiDescriptor("membros-redes-trabalho-nucelo-executivo-edit.xml")
@EditedEntityContainer("membrosRedesTrabalhoNuceloExecutivoDc")
@LoadDataBeforeShow
public class MembrosRedesTrabalhoNuceloExecutivoEdit extends StandardEditor<MembrosRedesTrabalhoNuceloExecutivo> {

    @Inject
    protected TextField<UUID> idMembrosTrabalho;
    @Inject
    protected CollectionContainer<RedeTrabalho> redeTrabalhoDc;
    @Inject
    protected LookupPickerField<RedeTrabalho> idRedeTrabalhoField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {

        getWindow().setCaption("Adicionar/Editar Membros da Instituição do Núcleo Executivo - " + idMembrosTrabalho.getValue());

        idRedeTrabalhoField.setEditable(false);
        Map<String, RedeTrabalho> map = new HashMap<>();
        Collection<RedeTrabalho> customers = redeTrabalhoDc.getItems();
        for (RedeTrabalho item : customers) {
            if (item.getNome().equals("Núcleo Executivo"))
            {
                map.put(item.getNome(), item);
                idRedeTrabalhoField.setValue(item);
            }
        }

        idRedeTrabalhoField.setOptionsMap(map);



    }
}