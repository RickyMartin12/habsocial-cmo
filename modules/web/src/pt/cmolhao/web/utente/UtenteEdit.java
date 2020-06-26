package pt.cmolhao.web.utente;

import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.ValidationException;
import com.haulmont.cuba.gui.screen.*;
import pt.cmolhao.entity.Utente;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@UiController("cmolhao_Utente.edit")
@UiDescriptor("utente-edit.xml")
@EditedEntityContainer("utenteDc")
@LoadDataBeforeShow
public class UtenteEdit extends StandardEditor<Utente> {

    @Inject
    protected TextField<String> certUniaoEuropeiaField;
    @Inject
    protected TextField<String> numContribuinteField;
    @Inject
    protected TextField<String> numIdCivilField;
    @Inject
    protected TextField<String> telefoneField;
    @Inject
    protected TextField<String> telem_velField;
    @Inject
    protected LookupField<String> estadoCivilField;
    @Inject
    protected LookupField<String> paisOrigemField;
    @Inject
    protected TextField<UUID> idUtenteField;

    @Inject
    private Notifications notifications;

    @Inject
    private Dialogs dialogs;

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    public static boolean isIdentity(String str) {
        return str != null && str.matches("\\d{8} \\d \\w{3}");
    }


    @Subscribe
    protected void onInit(InitEvent event) {

        List<String> list_estado_civil = new ArrayList<>();
        list_estado_civil.add("Solteiro(a)");
        list_estado_civil.add("Casado(a)");
        list_estado_civil.add("Divorciado(a)");
        list_estado_civil.add("Viúvo(a)");
        list_estado_civil.add("Separado(a)");
        estadoCivilField.setOptionsList(list_estado_civil);

        certUniaoEuropeiaField.addValidator(
                value -> {
                    if (value != null)
                    {
                        if (!isIdentity(value))
                        {
                            throw new ValidationException("A Certidão da União Europeia não é valida");
                        }
                    }
        });

        numContribuinteField.addValidator(value -> {
            if (value != null)
            {
                if (!isNumeric(value))
                {
                    throw new ValidationException("O Número Contribuinte tem que ser um numero.");
                }
                else
                {
                    int n_rep = 0;
                    String s = value;
                    int[] intArray = new int[s.length()];

                    for (int i = 0; i < s.length(); i++) {
                        intArray[i] = Character.digit(s.charAt(i), 10);
                    }

                    for (int i = 0; i < intArray.length-1; i++)
                    {
                        for (int j = i+1; j < intArray.length; j++)
                        {
                            if ((intArray[i] == intArray[j]) && (i != j))
                            {
                                n_rep++;
                            }
                        }
                    }
                    if (n_rep > 0)
                    {
                        throw new ValidationException("O Número Contribuinte tem que possuir números diferentes");
                    }
                }
            }
        });

        numIdCivilField.addValidator(value -> {
            if (value != null)
            {
                if (!isNumeric(value))
                {
                    throw new ValidationException("O Número ID Civil tem que ser um numero.");
                }
                else
                {
                    int n_rep_a = 0;
                    String s1 = value;
                    int[] intArray1 = new int[s1.length()];

                    for (int i = 0; i < s1.length(); i++) {
                        intArray1[i] = Character.digit(s1.charAt(i), 10);
                    }

                    for (int i = 0; i < intArray1.length-1; i++)
                    {
                        for (int j = i+1; j < intArray1.length; j++)
                        {
                            if ((intArray1[i] == intArray1[j]) && (i != j))
                            {
                                n_rep_a++;
                            }
                        }
                    }
                    if (n_rep_a > 0)
                    {
                        throw new ValidationException("O Número do ID Civil tem que possuir números diferentes");
                    }
                }
            }
        });


        telefoneField.addValidator(value -> {
            if (value != null) {
                if (!isNumeric(value)) {
                    throw new ValidationException("O Telefone do Utente tem que ser um numero.");
                }
            }
        });

        telem_velField.addValidator(value -> {
            if (value != null) {
                if (!isNumeric(value)) {
                    throw new ValidationException("O Telemóvel do Utente tem que ser um numero.");
                }
            }
        });



    }

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Adicionar/Editar Utente - " + idUtenteField.getValue());
        List<String> list_paises = new ArrayList<>();
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale obj = new Locale("", countryCode);
            list_paises.add(obj.getDisplayCountry());
        }
        paisOrigemField.setOptionsList(list_paises);

    }


}