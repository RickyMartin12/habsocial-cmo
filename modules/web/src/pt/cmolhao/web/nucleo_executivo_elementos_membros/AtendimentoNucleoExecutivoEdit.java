package pt.cmolhao.web.nucleo_executivo_elementos_membros;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.AtendimentoNucleoExecutivo;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_AtendimentoNucleoExecutivo.edit")
@UiDescriptor("atendimento-nucleo-executivo-edit.xml")
@EditedEntityContainer("atendimentoNucleoExecutivoDc")
@LoadDataBeforeShow
public class AtendimentoNucleoExecutivoEdit extends StandardEditor<AtendimentoNucleoExecutivo> {

    @Inject
    protected TextField<UUID> idAtendimentoField;
    @Inject
    protected TextField<String> contactosEfetuadosField;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Atendimento - " + idAtendimentoField.getValue());
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }


    @Subscribe
    protected void onInit(InitEvent event) {

        contactosEfetuadosField.addValidator(value -> {
            if (value != null)
            {
                if (!isNumeric(value))
                {
                    throw new ValidationException("O contacto tem que ser um número inteiro");
                }
            }

        });
    }

}