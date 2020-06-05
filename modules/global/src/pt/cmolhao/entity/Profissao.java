package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DesignSupport("{'imported':true}")
@NamePattern("%s|nome")
@Table(name = "profissao")
@Entity(name = "cmolhao_Profissao")
public class Profissao extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 2329000520071541145L;
    @Column(name = "nome")
    protected String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}