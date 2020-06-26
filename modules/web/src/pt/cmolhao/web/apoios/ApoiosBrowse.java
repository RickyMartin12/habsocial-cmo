package pt.cmolhao.web.apoios;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.*;
import pt.cmolhao.web.ajudastecnicas.AjudasTecnicasEdit;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UiController("cmolhao_Apoios.browse")
@UiDescriptor("apoios-browse.xml")
@LookupComponent("apoiosesTable")
@LoadDataBeforeShow
public class ApoiosBrowse extends StandardLookup<Apoios> {
    @Inject
    protected CollectionLoader<Apoios> apoiosesDl;
    @Inject
    protected LookupField<Equipamento> idEquipamentoField;
    @Inject
    protected LookupField<Instituicoes> idInstituicaoField;
    @Inject
    protected LookupField<TipoAjuda> idTipoapoioField;
    @Inject
    protected LookupField<Utente> utenteField;
    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected LookupField linhasApoio;
    @Inject
    protected GroupTable<Apoios> apoiosesTable;
    @Inject
    protected TextField<String> numProcessoField;
    @Inject
    protected TextField<String> dataAtribuicaoField;
    @Inject
    protected TextField<String> dataFimField;
    @Inject
    protected TextField<String> dataPedidoField;
    @Inject
    protected TextField<String> dataRenovacaoField;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Notifications notifications;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getWindow().setCaption("Listar Apoios");
    }


    public Component generateValorApoio(Apoios entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        if (entity.getValorApoio() != null)
        {
            label.setValue(entity.getValorApoio() + " €");
            return label;
        }
        return null;
    }

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
        linhasApoio.setOptionsList(list);

        apoiosesTable.setEmptyStateLinkClickHandler(emptyStateClickEvent ->
                screenBuilders.editor(apoiosesTable)
                        .newEntity()
                        .withInitializer(customer -> {
                            customer.setIdEquipamento(idEquipamentoField.getValue());
                            customer.setIdInstituicao(idInstituicaoField.getValue());
                            customer.setIdTipoapoio(idTipoapoioField.getValue());
                            customer.setIdUtente(utenteField.getValue());
                            if(numProcessoField.getValue() != null)
                            {
                                customer.setNumProcesso(Integer.valueOf(numProcessoField.getValue()));
                            }

                            if (dataAtribuicaoField.getValue() != null)
                            {
                                String dia = "01";
                                String mes = "01";
                                String ano = dataAtribuicaoField.getValue();
                                String data = dia +"/"+mes+"/"+ano;
                                try
                                {
                                    Date date_ano = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                                    customer.setDataAtribuicao(date_ano);
                                }
                                catch (ParseException e)
                                {
                                    notifications.create()
                                            .withCaption("Mensagem de erro: " + e.toString())
                                            .withType(Notifications.NotificationType.TRAY)
                                            .show();
                                }
                            }


                            if (dataFimField.getValue() != null)
                            {
                                String dia = "01";
                                String mes = "01";
                                String ano = dataFimField.getValue();
                                String data = dia +"/"+mes+"/"+ano;
                                try
                                {
                                    Date date_ano = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                                    customer.setDataFim(date_ano);
                                }
                                catch (ParseException e)
                                {
                                    notifications.create()
                                            .withCaption("Mensagem de erro: " + e.toString())
                                            .withType(Notifications.NotificationType.TRAY)
                                            .show();
                                }
                            }

                            if (dataPedidoField.getValue() != null)
                            {
                                String dia = "01";
                                String mes = "01";
                                String ano = dataPedidoField.getValue();
                                String data = dia +"/"+mes+"/"+ano;
                                try
                                {
                                    Date date_ano = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                                    customer.setDataPedido(date_ano);
                                }
                                catch (ParseException e)
                                {
                                    notifications.create()
                                            .withCaption("Mensagem de erro: " + e.toString())
                                            .withType(Notifications.NotificationType.TRAY)
                                            .show();
                                }
                            }


                            if (dataRenovacaoField.getValue() != null)
                            {
                                String dia = "01";
                                String mes = "01";
                                String ano = dataRenovacaoField.getValue();
                                String data = dia +"/"+mes+"/"+ano;
                                try
                                {
                                    Date date_ano = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                                    customer.setDataRenovacao(date_ano);
                                }
                                catch (ParseException e)
                                {
                                    notifications.create()
                                            .withCaption("Mensagem de erro: " + e.toString())
                                            .withType(Notifications.NotificationType.TRAY)
                                            .show();
                                }
                            }

                        })
                        .withScreenClass(ApoiosEdit.class)    // specific editor screen
                        .build()
                        .show()
        );
    }

    @Subscribe("reset_apoios")
    protected void onReset_apoiosClick(Button.ClickEvent event) {
        idEquipamentoField.setValue(null);
        idInstituicaoField.setValue(null);
        idTipoapoioField.setValue(null);
        utenteField.setValue(null);
        numProcessoField.setValue(null);
        linhasApoio.setValue(null);
        dataAtribuicaoField.setValue(null);
        dataFimField.setValue(null);
        dataPedidoField.setValue(null);
        dataRenovacaoField.setValue(null);
        apoiosesDl.setMaxResults(0);
        apoiosesDl.removeParameter("idEquipamento");
        apoiosesDl.removeParameter("idInstituicao");
        apoiosesDl.removeParameter("idTipoapoio");
        apoiosesDl.removeParameter("idUtente");
        apoiosesDl.removeParameter("numProcesso");

        apoiosesDl.removeParameter("dataAtribuicao");
        apoiosesDl.removeParameter("dataFim");
        apoiosesDl.removeParameter("dataPedido");
        apoiosesDl.removeParameter("dataRenovacao");

        apoiosesDl.load();
    }


    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }

    @Subscribe("search_apoios")
    protected void onSearch_apoiosClick(Button.ClickEvent event) {
        // ID do equipamento
        if (idEquipamentoField.getValue() != null) {
            apoiosesDl.setParameter("idEquipamento",  idEquipamentoField.getValue().getId());
        } else {
            apoiosesDl.removeParameter("idEquipamento");
        }

        // ID do Instituição
        if (idInstituicaoField.getValue() != null) {
            apoiosesDl.setParameter("idInstituicao",  idInstituicaoField.getValue().getId());
        } else {
            apoiosesDl.removeParameter("idInstituicao");
        }

        // ID Tipo de Ajuda
        if (idTipoapoioField.getValue() != null) {
            apoiosesDl.setParameter("idTipoapoio",  idTipoapoioField.getValue().getId());
        } else {
            apoiosesDl.removeParameter("idTipoapoio");
        }

        // ID do Utente
        if (utenteField.getValue() != null) {
            apoiosesDl.setParameter("idUtente",  utenteField.getValue().getId());
        } else {
            apoiosesDl.removeParameter("idUtente");
        }

        // Arrend
        if (numProcessoField.getValue() != null) {
            if (isNumeric(numProcessoField.getValue()))
            {
                apoiosesDl.setParameter("numProcesso", Integer.valueOf(numProcessoField.getValue()) );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do numero de processo</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }

        } else {
            apoiosesDl.removeParameter("numProcesso");
        }

        // Data de Atribuicao

        if (dataAtribuicaoField.getValue() != null)
        {
            if (isNumeric(dataAtribuicaoField.getValue()))
            {
                apoiosesDl.setParameter("dataAtribuicao", Integer.valueOf(dataAtribuicaoField.getValue()) );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do ano de atribuição</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }
        }
        else
        {
            apoiosesDl.removeParameter("dataAtribuicao");
        }

        // Data Fim

        if (dataFimField.getValue() != null)
        {
            if (isNumeric(dataFimField.getValue()))
            {
                apoiosesDl.setParameter("dataFim", Integer.valueOf(dataFimField.getValue()) );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do ano do fim</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }
        }
        else
        {
            apoiosesDl.removeParameter("dataFim");
        }

        // Data de Pedido

        if (dataPedidoField.getValue() != null)
        {
            if (isNumeric(dataPedidoField.getValue()))
            {
                apoiosesDl.setParameter("dataPedido", Integer.valueOf(dataPedidoField.getValue()) );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do ano do pedido</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }
        }
        else
        {
            apoiosesDl.removeParameter("dataPedido");
        }

        // Data Renovação

        if (dataRenovacaoField.getValue() != null)
        {
            if (isNumeric(dataRenovacaoField.getValue()))
            {
                apoiosesDl.setParameter("dataRenovacao", Integer.valueOf(dataRenovacaoField.getValue()) );
            }
            else
            {
                notifications.create()
                        .withCaption("<code>Erro ao atribuir string do ano de renovação</code>")
                        .withDescription("<u>Devera introduzir um numero inteiro</u>")
                        .withType(Notifications.NotificationType.ERROR)
                        .withContentMode(ContentMode.HTML)
                        .show();
            }
        }
        else
        {
            apoiosesDl.removeParameter("dataRenovacao");
        }

        apoiosesDl.load();
    }

    @Subscribe("linhasApoio")
    protected void onLinhasApoioValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            apoiosesDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            apoiosesDl.setMaxResults(0);
        }
        apoiosesDl.load();
    }
}