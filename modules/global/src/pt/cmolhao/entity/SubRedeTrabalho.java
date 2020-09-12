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
@Table(name = "sub_rede_trabalho")
@Entity(name = "cmolhao_SubRedeTrabalho")
public class SubRedeTrabalho extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -3155182695658532199L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rede_trabalho")
    protected pt.cmolhao.entity.RedeTrabalho idRedeTrabalho;
    @Lob
    @Column(name = "nome")
    protected String nome;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "idSubRedeTrabalho")
    protected Set<Documentacao> documentacaos;

    public Set<Documentacao> getDocumentacaos() {
        return documentacaos;
    }

    public void setDocumentacaos(Set<Documentacao> documentacaos) {
        this.documentacaos = documentacaos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public pt.cmolhao.entity.RedeTrabalho getIdRedeTrabalho() {
        return idRedeTrabalho;
    }

    public void setIdRedeTrabalho(pt.cmolhao.entity.RedeTrabalho idRedeTrabalho) {
        this.idRedeTrabalho = idRedeTrabalho;
    }
}