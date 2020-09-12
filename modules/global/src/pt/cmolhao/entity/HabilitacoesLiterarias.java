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
@NamePattern("%s|descricao")
@Table(name = "habilitacoes_literarias")
@Entity(name = "cmolhao_HabilitacoesLiterarias")
public class HabilitacoesLiterarias extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -3672572851172091670L;
    @Column(name = "descricao")
    protected String descricao;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "habilitacoes")
    protected Set<Utente> utentes;

    public Set<Utente> getUtentes() {
        return utentes;
    }

    public void setUtentes(Set<Utente> utentes) {
        this.utentes = utentes;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}