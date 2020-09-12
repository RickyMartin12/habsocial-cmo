package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Set;

@NamePattern("%s|nome")
@DesignSupport("{'imported':true}")
@Table(name = "rede_trabalho")
@Entity(name = "cmolhao_RedeTrabalho")
public class RedeTrabalho extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 3279045523215689278L;
    @Lob
    @Column(name = "nome")
    protected String nome;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idRedeTrabalho")
    protected Set<SubRedeTrabalho> subRedeTrabalhos;

    public Set<SubRedeTrabalho> getSubRedeTrabalhos() {
        return subRedeTrabalhos;
    }

    public void setSubRedeTrabalhos(Set<SubRedeTrabalho> subRedeTrabalhos) {
        this.subRedeTrabalhos = subRedeTrabalhos;
    }


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idRedeTrabalho")
    protected Set<Documentacao> documentacaos;

    public Set<Documentacao> getDocumentacaos() {
        return documentacaos;
    }

    public void setDocumentacaos(Set<Documentacao> documentacaos) {
        this.documentacaos = documentacaos;
    }


    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idRedeTrabalho")
    protected Set<MembrosRedeTrabalho> membrosRedeTrabalhos;

    public Set<MembrosRedeTrabalho> getMembrosRedeTrabalhos() {
        return membrosRedeTrabalhos;
    }

    public void setMembrosRedeTrabalhos(Set<MembrosRedeTrabalho> membrosRedeTrabalhos) {
        this.membrosRedeTrabalhos = membrosRedeTrabalhos;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}