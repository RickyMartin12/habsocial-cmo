package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@DesignSupport("{'imported':true}")
@NamePattern("%s - %s|atendimentoObjetivoGeral,atendimemtoObjetivoEspecifico")
@Table(name = "atendimento_objetivos")
@Entity(name = "cmolhao_AtendimentoObjetivos")
public class AtendimentoObjetivos extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -6325050959253350383L;
    @Column(name = "atendimemto_objetivo_especifico")
    protected String atendimemtoObjetivoEspecifico;
    @Column(name = "atendimento_objetivo_geral")
    protected String atendimentoObjetivoGeral;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idAtendimentoObjetivo")
    protected Set<Atendimento> atendimentos;
    public Set<Atendimento> getAtendimentos() {
        return atendimentos;
    }
    public void setAtendimentos(Set<Atendimento> atendimentos) {
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