package pt.cmolhao.web.atendimento;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Atendimento;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_Atendimento.edit")
@UiDescriptor("atendimento-edit.xml")
@EditedEntityContainer("atendimentoDc")
@LoadDataBeforeShow
public class AtendimentoEdit extends StandardEditor<Atendimento> {

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