package pt.cmolhao.web.hab_social_plataforma_eaa;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import pt.cmolhao.entity.*;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@UiController("cmolhao_HabitacaoSocial.browsePlataformaEaa")
@UiDescriptor("habitacao-social-browse.xml")
@LookupComponent("habitacaoSocialsTable")
@LoadDataBeforeShow
public class HabitacaoSocialBrowse extends StandardLookup<HabitacaoSocial> {

    @Inject
    protected UiComponents uiComponents;
    @Inject
    protected CollectionLoader<HabitacaoSocial> habitacaoSocialsDl;
    @Inject
    protected TextField<String> localidadeField;
    @Inject
    protected LookupField tipoArrendamentoField;
    @Inject
    protected LookupPickerField<Utente> utenteField;
    @Inject
    protected LookupPickerField<HabilitacoesLiterarias> habilitacoesField;
    @Inject
    protected LookupPickerField<TipoCartao> idTipoCartaoField;
    @Inject
    protected LookupPickerField<Tecnico> idTecnicoField;
    @Inject
    protected LookupPickerField<Instituicoes> idInstituicaoField;
    @Inject
    protected LookupPickerField<AtendimentoObjetivos> atendimentoObjectivosField;
    @Inject
    protected LookupPickerField<AtendimentoEncaminhamento> idAtendimentoEncaminhamentoField;
    @Inject
    protected DateField<Date> dataInicioField;
    @Inject
    protected DateField<Date> dataFimField;
    @Inject
    protected LookupField linhasHabSocial;
    @Inject
    protected LookupPickerField<BlocosHabitacaoSocial> blocField;

    @Subscribe
    public void onInit(InitEvent event) {
        List<String> list = new ArrayList<>();
        list.add("Reside no Concelho");
        list.add("Não reside no Concelho");
        tipoArrendamentoField.setOptionsList(list);

        List<Integer> list_hab_social = new ArrayList<>();
        list_hab_social.add(10);
        list_hab_social.add(20);
        list_hab_social.add(50);
        list_hab_social.add(100);
        list_hab_social.add(200);
        list_hab_social.add(500);
        list_hab_social.add(1000);
        list_hab_social.add(2000);
        list_hab_social.add(5000);
        list_hab_social.add(10000);
        linhasHabSocial.setOptionsList(list_hab_social);


    }


    public Component generateAtendimentosTecnicoNome(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        String tipos_atendimentos = "";
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                tipos_atendimentos += "\n";

                for (Atendimento a : set) {

                    if (a.getIdTecnico() != null)
                    {
                        tipos_atendimentos += a.getIdTecnico().getNome() + "\n\n";
                    }

                }

                label.setValue(tipos_atendimentos);
                return label;


            }

        }
        return null;
    }

    public Component generateAtendimentoTecnicos(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        String tipos_atendimentos = "";
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                tipos_atendimentos += "\n";

                for (Atendimento a : set) {

                    if (a.getIdTecnico() != null)
                    {
                        if (a.getIdTecnico().getIdInstituicao() != null)
                        {
                            tipos_atendimentos += a.getIdTecnico().getIdInstituicao().getDescricao() + "\n\n";
                        }

                    }

                }

                label.setValue(tipos_atendimentos);
                return label;


            }

        }
        return null;
    }

    public Component generateDataAtendimento(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        StringBuilder data_atendimento = new StringBuilder();
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                data_atendimento.append("\n");

                for (Atendimento a : set) {

                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                    String data_atend = formatter.format(a.getDataAtendimento());

                    data_atendimento.append(data_atend).append("\n\n");


                }

                label.setValue(data_atendimento.toString());
                return label;


            }

        }
        return null;
    }


    public Component generateTiposAtendimentos(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        String tipos_atendimentos = "";
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                tipos_atendimentos += "\n";

                for (Atendimento a : set) {

                    if (a.getIdTipoAtendimento() != null)
                    {
                        tipos_atendimentos += a.getIdTipoAtendimento().getTipoAtendimento() + "\n\n";
                    }

                }

                label.setValue(tipos_atendimentos);
                return label;


            }

        }
        return null;
    }

    public Component generateAtendimentosEncaminhamentos(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        String atendimentos_encaminhamentos = "";
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                atendimentos_encaminhamentos += "\n";

                for (Atendimento a : set) {

                    if (a.getIdAtendimentoEncaminhamento() != null)
                    {
                        atendimentos_encaminhamentos += a.getIdAtendimentoEncaminhamento().getAtendimentoEncaminhamento() + "\n\n";
                    }

                }

                label.setValue(atendimentos_encaminhamentos);
                return label;


            }

        }
        return null;
    }

    public Component generateTiposAtendimentosObjectivos(HabitacaoSocial entity) {
        Label label = (Label) uiComponents.create(Label.NAME);
        String atendimentos_objectivos = "";
        if (entity.getIdUtente() != null)
        {
            if (entity.getIdUtente().getAtendimentos() != null)
            {
                Set<Atendimento> set = entity.getIdUtente().getAtendimentos();

                atendimentos_objectivos += "\n";

                for (Atendimento a : set) {

                    List<AtendimentoObjetivos> ao = a.getAtendimentoObjetivos();

                    for (int i = 0; i < ao.size(); i++) {
                        atendimentos_objectivos += ao.get(i).getAtendimentoObjetivoGeral() + "\n\n";
                    }



                }

                label.setValue(atendimentos_objectivos);
                return label;


            }

        }
        return null;
    }

    @Subscribe("linhasHabSocial")
    protected void onLinhasHabSocialValueChange(HasValue.ValueChangeEvent event) {
        if (event.getValue() != null)
        {
            habitacaoSocialsDl.setMaxResults(Integer.parseInt(event.getValue().toString()));
        }
        else
        {
            habitacaoSocialsDl.setMaxResults(0);
        }
        habitacaoSocialsDl.load();
    }

    @Subscribe("reset_habitacao_social")
    protected void onReset_habitacao_socialClick(Button.ClickEvent event) {
        if (dataInicioField.getValue() != null)
        {
            dataInicioField.setValue(null);
            habitacaoSocialsDl.removeParameter("dataInicio");
            habitacaoSocialsDl.setQuery("select e from cmolhao_HabitacaoSocial e join e.idUtente.atendimentos a join a.idTipoAtendimento b where b.id = 4");
        }
        if (dataFimField.getValue() != null)
        {
            dataFimField.setValue(null);
            habitacaoSocialsDl.removeParameter("dateFim");
            habitacaoSocialsDl.setQuery("select e from cmolhao_HabitacaoSocial e join e.idUtente.atendimentos a join a.idTipoAtendimento b where b.id = 4");
            dataFimField.setEnabled(false);
        }

        idAtendimentoEncaminhamentoField.setValue(null);
        habitacaoSocialsDl.removeParameter("idAtendimentoEncaminhamento");

        blocField.setValue(null);
        habitacaoSocialsDl.removeParameter("bloc");

        localidadeField.setValue(null);
        habitacaoSocialsDl.removeParameter("localidade");

        tipoArrendamentoField.setValue(null);
        habitacaoSocialsDl.removeParameter("tipoArrendamento");

        utenteField.setValue(null);
        habitacaoSocialsDl.removeParameter("idUtente");

        habilitacoesField.setValue(null);
        habitacaoSocialsDl.removeParameter("habilitacoes");


        idTecnicoField.setValue(null);
        habitacaoSocialsDl.removeParameter("idTecnico");

        idInstituicaoField.setValue(null);
        habitacaoSocialsDl.removeParameter("idInstituicao");

        idTipoCartaoField.setValue(null);
        habitacaoSocialsDl.removeParameter("idTipoCartao");

        atendimentoObjectivosField.setValue(null);
        habitacaoSocialsDl.removeParameter("atendimentoObjetivos");





        habitacaoSocialsDl.load();
    }

    @Subscribe("search_habitacao_social")
    protected void onSearch_habitacao_socialClick(Button.ClickEvent event) {
        if (dataInicioField.getValue() != null && dataFimField.getValue() == null)
        {
            habitacaoSocialsDl.setQuery("select e from cmolhao_HabitacaoSocial e join e.idUtente.atendimentos l join l.idTipoAtendimento b where l.dataAtendimento = :dataInicio and b.id = 4");
            habitacaoSocialsDl.setParameter("dataInicio",  dataInicioField.getValue());
        }
        if (dataInicioField.getValue() != null && dataFimField.getValue() != null)
        {
            habitacaoSocialsDl.setQuery("select e from cmolhao_HabitacaoSocial e join e.idUtente.atendimentos l join l.idTipoAtendimento b where l.dataAtendimento >= :dataInicio and l.dataAtendimento <= :dateFim and b.id = 4");
            habitacaoSocialsDl.setParameter("dataInicio",  dataInicioField.getValue());
            habitacaoSocialsDl.setParameter("dateFim",  dataFimField.getValue());
        }



        if (idAtendimentoEncaminhamentoField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("idAtendimentoEncaminhamento", idAtendimentoEncaminhamentoField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("idAtendimentoEncaminhamento");
        }


        if (blocField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("bloc", blocField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("bloc");
        }

        if (localidadeField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("localidade",  "(?i)%" + localidadeField.getValue() + "%");
        }
        else
        {
            habitacaoSocialsDl.removeParameter("localidade");
        }

        if (tipoArrendamentoField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("tipoArrendamento", tipoArrendamentoField.getValue());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("tipoArrendamento");
        }

        if(utenteField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("idUtente", utenteField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("idUtente");
        }

        if (habilitacoesField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("habilitacoes", habilitacoesField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("habilitacoes");
        }


        if(idTecnicoField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("idTecnico", idTecnicoField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("idTecnico");
        }

        if(idInstituicaoField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("idInstituicao", idInstituicaoField.getValue().getId());
        }
        else
        {
            habitacaoSocialsDl.removeParameter("idInstituicao");
        }

        if (idTipoCartaoField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("idTipoCartao", idTipoCartaoField.getValue().getId());
        }
        else {
            habitacaoSocialsDl.removeParameter("idTipoCartao");
        }

        if (atendimentoObjectivosField.getValue() != null)
        {
            habitacaoSocialsDl.setParameter("atendimentoObjetivos",  atendimentoObjectivosField.getValue().getId());
        } else {
            habitacaoSocialsDl.removeParameter("atendimentoObjetivos");
        }




        habitacaoSocialsDl.load();
    }


    @Subscribe("dataInicioField")
    protected void onDataInicioFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        dataFimField.setEnabled(true);
        if (event.isUserOriginated())
        {
            if (event.getValue() != null)
            {
                dataFimField.setRangeStart(event.getValue());
            }
        }
    }

    @Subscribe("dataFimField")
    protected void onDataFimFieldValueChange(HasValue.ValueChangeEvent<Date> event) {
        if (event.isUserOriginated()) {
            if (event.getValue() != null) {
                dataInicioField.setRangeEnd(event.getValue());
            }
        }
    }



}