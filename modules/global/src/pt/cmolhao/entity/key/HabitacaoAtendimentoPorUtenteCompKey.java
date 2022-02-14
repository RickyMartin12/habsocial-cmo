package pt.cmolhao.entity.key;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.cuba.core.entity.EmbeddableEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@DesignSupport("{'imported':true}")
@MetaClass(name = "cmolhao_HabitacaoAtendimentoPorUtenteCompKey")
@Embeddable
public class HabitacaoAtendimentoPorUtenteCompKey extends EmbeddableEntity {
    private static final long serialVersionUID = 7811407883823696148L;
    @Column(name = "atendimento_id")
    protected Integer atendimento;
    @Column(name = "bloco_hab_social")
    protected Integer blocoHabSocial;
    @Column(name = "utente_id")
    protected Integer utente;

    @Override
    public int hashCode() {
        return Objects.hash(utente, atendimento, blocoHabSocial);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HabitacaoAtendimentoPorUtenteCompKey entity = (HabitacaoAtendimentoPorUtenteCompKey) o;
        return Objects.equals(this.utente, entity.utente) &&
                Objects.equals(this.atendimento, entity.atendimento) &&
                Objects.equals(this.blocoHabSocial, entity.blocoHabSocial);
    }

    public Integer getUtente() {
        return utente;
    }

    public void setUtente(Integer utente) {
        this.utente = utente;
    }

    public Integer getBlocoHabSocial() {
        return blocoHabSocial;
    }

    public void setBlocoHabSocial(Integer blocoHabSocial) {
        this.blocoHabSocial = blocoHabSocial;
    }

    public Integer getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Integer atendimento) {
        this.atendimento = atendimento;
    }
}