package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.List;

@DesignSupport("{'imported':true}")
@NamePattern("%s|atendimentoObjetivoGeral")
@Table(name = "atendimento_objetivos")
@Entity(name = "cmolhao_AtendimentoObjetivos")
public class AtendimentoObjetivos extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -6325050959253350383L;
    @Column(name = "atendimemto_objetivo_especifico")
    protected String atendimemtoObjetivoEspecifico;

    @Column(name = "atendimento_objetivo_geral")
    protected String atendimentoObjetivoGeral;

    @JoinTable(name = "objetivos_atendimento",
            joinColumns = @JoinColumn(name = "id_atendimento_objectivos"),
            inverseJoinColumns = @JoinColumn(name = "id_atendimento"))
    @ManyToMany
    protected List<Atendimento> atendimentos;

    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public void setAtendimentos(List<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public String getAtendimentoObjetivoGeral() {
        return atendimentoObjetivoGeral;
    }

    public void setAtendimentoObjetivoGeral(String atendimentoObjetivoGeral) {
        this.atendimentoObjetivoGeral = atendimentoObjetivoGeral;
    }

    public String getAtendimemtoObjetivoEspecifico() {
        return atendimemtoObjetivoEspecifico;
    }

    public void setAtendimemtoObjetivoEspecifico(String atendimemtoObjetivoEspecifico) {
        this.atendimemtoObjetivoEspecifico = atendimemtoObjetivoEspecifico;
    }



}