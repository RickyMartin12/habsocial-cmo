package pt.cmolhao.web.pessoaltecnico;

import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.PessoalTecnico;
import pt.cmolhao.entity.Valencias;

import javax.inject.Inject;
import java.util.*;

@UiController("cmolhao_PessoalTecnico.edit")
@UiDescriptor("pessoal-tecnico-edit.xml")
@EditedEntityContainer("pessoalTecnicoDc")
@LoadDataBeforeShow
public class PessoalTecnicoEdit extends StandardEditor<PessoalTecnico> {

    @Inject
    protected TextField<UUID> idPessoalTecnicoField;
    @Inject
    private CollectionContainer<Valencias> valenciasDc;
    @Inject
    private LookupField<Valencias> idValenciaField;
    @Inject
    private LookupField<String> habilitacoesliterariasField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Atendimento Pessoa Técnica - " + idPessoalTecnicoField.getValue());
        Map<String, Valencias> map = new HashMap<>();
        Collection<Valencias> customers = valenciasDc.getItems();
        for (Valencias item : customers) {
            map.put(item.getDescricaotecnica() + " " , item);
        }
        idValenciaField.setOptionsMap(map);
    }


    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Sem Habilidade Literária");
        list.add("1.º Ciclo Ens. Primário do 1.ª ano ao 4.ª Ano");
        list.add("1.º Ciclo Ens. Básico do 5.ª ano ao 9.ª Ano");
        list.add("Ensino Secundário");
        list.add("Barcerlato");
        list.add("Ensino Universitário");
        list.add("Mestrado");
        habilitacoesliterariasField.setOptionsList(list);
    }
}