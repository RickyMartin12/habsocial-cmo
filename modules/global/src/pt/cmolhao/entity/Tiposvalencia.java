package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Set;

@DesignSupport("{'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idtiposvalencia"))
})
@NamePattern("%s|nome")
@Table(name = "tiposvalencia")
@Entity(name = "cmolhao_Tiposvalencia")
public class Tiposvalencia extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 9015538369120039895L;
    @Column(name = "nome", length = 250)
    protected String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idTipoValencia")
    protected Set<RespostaSocial> resp_social;

    public Set<RespostaSocial> getResp_social() {
        return resp_social;
    }

    public void setResp_social(Set<RespostaSocial> resp_social) {
        this.resp_social = resp_social;
    }

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idtipovalencia")
    protected Set<Valencias> valencias;

    public Set<Valencias> getValencias() {
        return valencias;
    }

    public void setValencias(Set<Valencias> valencias) {
        this.valencias = valencias;
    }


}