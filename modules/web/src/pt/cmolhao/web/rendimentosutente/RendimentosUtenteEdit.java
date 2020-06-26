package pt.cmolhao.web.rendimentosutente;

import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.RendimentosUtente;

import javax.inject.Inject;
import java.util.UUID;

@UiController("cmolhao_RendimentosUtente.edit")
@UiDescriptor("rendimentos-utente-edit.xml")
@EditedEntityContainer("rendimentosUtenteDc")
@LoadDataBeforeShow
public class RendimentosUtenteEdit extends StandardEditor<RendimentosUtente> {
    @Inject
    protected TextField<String> anoField;
    @Inject
    protected TextField<UUID> idRendimentoUtenteField;

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe
    protected void onInit(InitEvent event) {
        anoField.addValidator(
                value -> {
                    if (value != null)
                    {
                        if (!isNumeric(value))
                        {
                            throw new ValidationException("O ano do rendimento do utente deve possuir numeros inteiros");
                        }
                    }
                });
    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Rendimento do Utente - " + idRendimentoUtenteField.getValue());
    }


}