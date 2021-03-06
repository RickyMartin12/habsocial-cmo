package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Set;

@DesignSupport("{'imported':true}")
@NamePattern("%s (%s)|nome,email")
@Table(name = "tecnico")
@Entity(name = "cmolhao_Tecnico")
public class Tecnico extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -43271551868624640L;
    @Column(name = "email")
    protected String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instituicao")
    protected Instituicoes idInstituicao;
    @Column(name = "nome")
    protected String nome;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idTecnico")
    protected Set<Atendimento> atendimentos;

    public Set<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public void setAtendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Instituicoes getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Instituicoes idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}