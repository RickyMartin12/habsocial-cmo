package pt.cmolhao.web.tipologiafamiliar;

import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipologiaFamiliar;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipologiaFamiliar.edit")
@UiDescriptor("tipologia-familiar-edit.xml")
@EditedEntityContainer("tipologiaFamiliarDc")
@LoadDataBeforeShow
public class TipologiaFamiliarEdit extends StandardEditor<TipologiaFamiliar> {
    @Inject
    protected LookupField<String> tipologiaFamiliarField;
    @Inject
    protected LookupField<String> tipologiaFamiliarEspecificaField;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list_topologia_familiar = new ArrayList<>();
        list_topologia_familiar.add("Estrutura e dinâmica global");
        list_topologia_familiar.add("Relação Conjugal");
        list_topologia_familiar.add("Relação Parental");
        tipologiaFamiliarField.setOptionsList(list_topologia_familiar);
    }

    @Subscribe("tipologiaFamiliarField")
    protected void onTipologiaFamiliarFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        List<String> list_topologia_familiar_especifica = new ArrayList<>();
        tipologiaFamiliarEspecificaField.setValue(null);
        list_topologia_familiar_especifica.clear();
        tipologiaFamiliarEspecificaField.setInputPrompt("(Seleccione a Tipologia Familiar Especifica)");
        if (event.isUserOriginated())
        {
            if (event.getValue().equals("Estrutura e dinâmica global"))
            {
                list_topologia_familiar_especifica.add("Família Díade Nuclear");
                list_topologia_familiar_especifica.add("Família Grávida");
                list_topologia_familiar_especifica.add("Família Nuclear ou Simples");
                list_topologia_familiar_especifica.add("Família Alargada ou Extensa");
                list_topologia_familiar_especifica.add("Família com prole extensa ou numerosa");
                list_topologia_familiar_especifica.add("Família Reconstruída, Combinada ou Recombinada");
                list_topologia_familiar_especifica.add("Família Homossexual");
                list_topologia_familiar_especifica.add("Família Dança a Dois");
                list_topologia_familiar_especifica.add("Família Unitária");
                list_topologia_familiar_especifica.add("Família de Co-habitação");
                list_topologia_familiar_especifica.add("Família Comunitária");
                list_topologia_familiar_especifica.add("Família Hospedeira");
                list_topologia_familiar_especifica.add("Família Adoptiva");
                list_topologia_familiar_especifica.add("Família Consanguínea");
                list_topologia_familiar_especifica.add("Família com Dependente");
                list_topologia_familiar_especifica.add("Família com Fantasma");
                list_topologia_familiar_especifica.add("Família Acordeão");
                list_topologia_familiar_especifica.add("Família Flutuante");
                list_topologia_familiar_especifica.add("Família Descontrolada");
                list_topologia_familiar_especifica.add("Família Múltipla");

            }
            if (event.getValue().equals("Relação Conjugal"))
            {
                list_topologia_familiar_especifica.add("Família Tradicional");
                list_topologia_familiar_especifica.add("Família Moderna");
                list_topologia_familiar_especifica.add("Família Fortaleza");
                list_topologia_familiar_especifica.add("Família Companheirismo");
                list_topologia_familiar_especifica.add("Família Paralela");
                list_topologia_familiar_especifica.add("Família Associação");
            }
            if (event.getValue().equals("Relação Parental"))
            {
                list_topologia_familiar_especifica.add("Família Equilibrada");
                list_topologia_familiar_especifica.add("Família Rígida");
                list_topologia_familiar_especifica.add("Família Super-protectora");
                list_topologia_familiar_especifica.add("Família Permissiva");
                list_topologia_familiar_especifica.add("Família Centrada nos filhos");
                list_topologia_familiar_especifica.add("Família Centrada nos pais");
                list_topologia_familiar_especifica.add("Família Sem objectivos");
            }
            tipologiaFamiliarEspecificaField.setOptionsList(list_topologia_familiar_especifica);
        }
    }
}