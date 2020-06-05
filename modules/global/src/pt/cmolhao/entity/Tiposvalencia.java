package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

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
}