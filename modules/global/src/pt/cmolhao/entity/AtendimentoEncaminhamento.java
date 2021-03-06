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
@NamePattern("%s|atendimentoEncaminhamento")
@Table(name = "atendimento_encaminhamento")
@Entity(name = "cmolhao_AtendimentoEncaminhamento")
public class AtendimentoEncaminhamento extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 3664887026268993428L;
    @Column(name = "atendimento_encaminhamento")
    protected String atendimentoEncaminhamento;
    @Column(name = "outros_atendimentos_encaminhamentos")
    protected String outrosAtendimentosEncaminhamentos;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idAtendimentoEncaminhamento")
    protected Set<Atendimento> atendimentos;
    public Set<Atendimento> getAtendimentos() {
        return atendimentos;
    }
    public void setAtendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public String getOutrosAtendimentosEncaminhamentos() {
        return outrosAtendimentosEncaminhamentos;
    }

    public void setOutrosAtendimentosEncaminhamentos(String outrosAtendimentosEncaminhamentos) {
        this.outrosAtendimentosEncaminhamentos = outrosAtendimentosEncaminhamentos;
    }

    public String getAtendimentoEncaminhamento() {
        return atendimentoEncaminhamento;
    }

    public void setAtendimentoEncaminhamento(String atendimentoEncaminhamento) {
        this.atendimentoEncaminhamento = atendimentoEncaminhamento;
    }
}