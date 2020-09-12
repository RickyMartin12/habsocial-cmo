package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Set;

@DesignSupport("{'dbView':true,'imported':true}")
@NamePattern("%s (%s)|nome,email")
@Table(name = "tecnico_nucleo_executivo")
@Entity(name = "cmolhao_TecnicoNucleoExecutivo")
public class TecnicoNucleoExecutivo extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 2310744011533526529L;
    @Column(name = "email")
    protected String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instituicao")
    protected InstituicoesNucleoExecutivo idInstituicao;
    @Column(name = "nome")
    protected String nome;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idTecnico")
    protected Set<AtendimentoNucleoExecutivo> atendimentoNucleoExecutivos;

    public Set<AtendimentoNucleoExecutivo> getAtendimentoNucleoExecutivos() {
        return atendimentoNucleoExecutivos;
    }

    public void setAtendimentoNucleoExecutivos(Set<AtendimentoNucleoExecutivo> atendimentoNucleoExecutivos) {
        this.atendimentoNucleoExecutivos = atendimentoNucleoExecutivos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public InstituicoesNucleoExecutivo getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(InstituicoesNucleoExecutivo idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}