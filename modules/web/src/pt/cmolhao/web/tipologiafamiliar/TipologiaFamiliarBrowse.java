package pt.cmolhao.web.tipologiafamiliar;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.TipologiaFamiliar;
import pt.cmolhao.web.grauescolaridade.GrauEscolaridadeEdit;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@UiController("cmolhao_TipologiaFamiliar.browse")
@UiDescriptor("tipologia-familiar-browse.xml")
@LookupComponent("tipologiaFamiliarsTable")
@LoadDataBeforeShow
public class TipologiaFamiliarBrowse extends StandardLookup<TipologiaFamiliar> {
    @Inject
    protected CollectionLoader<TipologiaFamiliar> tipologiaFamiliarsDl;
    @Inject
    protected Table<TipologiaFamiliar> tipologiaFamiliarsTable;
    @Inject
    protected LookupField linhasTipologiaFamiliar;
    @Inject
    protected LookupField tipologiaFamiliarField;
    @Inject
    protected LookupField tipologiaFamiliarEspecificaField;
    @Inject
    private DataManager dataManager;
    @Inject
    private ScreenBuilders screenBuilders;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<String> list_topologia_familiar = new ArrayList<>();
        list_topologia_familiar.add("Estrutura e dinâmica global");
        list_topologia_familiar.add("Relação Conjugal");
        list_topologia_familiar.add("Relação Parental");
        tipologiaFamiliarField.setOptionsList(list_topologia_familiar);

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
        linhasTipologiaFamiliar.setOptionsList(list);

        tipologiaFamiliarsTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(tipologiaFamiliarsTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            if (tipologiaFamiliarField.getValue() != null)
                            {
                                customer.setTipologiaFamiliar(tipologiaFamiliarField.getValue().toString());
                            }
                            if (tipologiaFamiliarEspecificaField.getValue() != null)
                            {
                                customer.setTipologiaFamiliarEspecifica(tipologiaFamiliarEspecificaField.getValue().toString());
                            }
                        })
                        .withScreenClass(TipologiaFamiliarEdit.class)    // specific editor screen
                        .build()
                        .show()
        );


    }

    @Subscribe("reset_tipologia_familiar")
    protected void onReset_tipologia_familiarClick(Button.ClickEvent event) {
        linhasTipologiaFamiliar.setValue(null);
        tipologiaFamiliarEspecificaField.setValue(null);
        tipologiaFamiliarField.setValue(null);
        tipologiaFamiliarsDl.setMaxResults(0);
        tipologiaFamiliarsDl.removeParameter("tipologiaFamiliar");
        tipologiaFamiliarsDl.removeParameter("tipologiaFamiliarEspecifica");
        tipologiaFamiliarsDl.load();
    }

    @Subscribe("search_tipologia_familiar")
    protected void onSearch_tipologia_familiarClick(Button.ClickEvent event) {
        if(tipologiaFamiliarField.getValue() != null)
        {
            tipologiaFamiliarsDl.setParameter("tipologiaFamiliar",  tipologiaFamiliarField.getValue().toString());
        }
        else
        {
            tipologiaFamiliarsDl.removeParameter("tipologiaFamiliar");
        }

        if(tipologiaFamiliarEspecificaField.getValue() != null)
        {
            tipologiaFamiliarsDl.setParameter("tipologiaFamiliarEspecifica",  tipologiaFamiliarEspecificaField.getValue().toString());
        }
        else
        {
            tipologiaFamiliarsDl.removeParameter("tipologiaFamiliarEspecifica");
        }


        tipologiaFamiliarsDl.load();
    }

    @Subscribe("linhasTipologiaFamiliar")
    protected void onLinhasTipologiaFamiliarValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            tipologiaFamiliarsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            tipologiaFamiliarsDl.setMaxResults(0);
        }
        tipologiaFamiliarsDl.load();
    }

    @Subscribe("tipologiaFamiliarField")
    protected void onTipologiaFamiliarFieldValueChange(HasValue.ValueChangeEvent event) {
        List<String> list_topologia_familiar_especifica = new ArrayList<>();
        tipologiaFamiliarEspecificaField.setEditable(false);
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
            tipologiaFamiliarEspecificaField.setEditable(true);
            tipologiaFamiliarEspecificaField.setOptionsList(list_topologia_familiar_especifica);
        }
    }
}