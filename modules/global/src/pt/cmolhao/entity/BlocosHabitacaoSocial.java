package pt.cmolhao.entity;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@DesignSupport("{'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "codigo"))
})
@NamePattern("%s|designacao")
@Table(name = "blocos_habitacao_social")
@Entity(name = "cmolhao_BlocosHabitacaoSocial")

public class BlocosHabitacaoSocial extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 5627109600170464818L;
    @Temporal(TemporalType.DATE)
    @Column(name = "ano_de_construcao")
    protected Date anoDeConstrucao;
    @Lob
    @Column(name = "designacao")
    protected String designacao;
    @Lob
    @Column(name = "morada")
    protected String morada;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "bloc")
    protected Set<HabitacaoSocial> habitacaoSocials;

    public Set<HabitacaoSocial> getHabitacaoSocials() {
        return habitacaoSocials;
    }

    public void setHabitacaoSocials(Set<HabitacaoSocial> habitacaoSocials) {
        this.habitacaoSocials = habitacaoSocials;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public Date getAnoDeConstrucao() {
        return anoDeConstrucao;
    }

    public void setAnoDeConstrucao(Date anoDeConstrucao) {
        this.anoDeConstrucao = anoDeConstrucao;
    }
}