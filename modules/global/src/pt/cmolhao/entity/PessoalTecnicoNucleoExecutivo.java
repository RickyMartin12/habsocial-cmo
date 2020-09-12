package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'dbView':true,'imported':true}")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "idpessoaltecnico"))
})
@Table(name = "pessoal_tecnico_nucleo_executivo")
@Entity(name = "cmolhao_PessoalTecnicoNucleoExecutivo")
public class PessoalTecnicoNucleoExecutivo extends BaseIntegerIdEntity {
    private static final long serialVersionUID = 1600021077474589012L;
    @Column(name = "anosservicoinstituicao")
    protected Integer anosservicoinstituicao;
    @Column(name = "categoriaprofissional", length = 250)
    protected String categoriaprofissional;
    @Column(name = "formacaoprofissional")
    protected Boolean formacaoprofissional;
    @Column(name = "habilitacoesliterarias", length = 250)
    protected String habilitacoesliterarias;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_valencia")
    protected ValenciaNucleoExecutivo idValencia;
    @Column(name = "idade")
    protected Integer idade;
    @Column(name = "sexomasculino")
    protected Boolean sexomasculino;
    @Column(name = "tipovinculo", length = 250)
    protected String tipovinculo;

    public String getTipovinculo() {
        return tipovinculo;
    }

    public void setTipovinculo(String tipovinculo) {
        this.tipovinculo = tipovinculo;
    }

    public Boolean getSexomasculino() {
        return sexomasculino;
    }

    public void setSexomasculino(Boolean sexomasculino) {
        this.sexomasculino = sexomasculino;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public ValenciaNucleoExecutivo getIdValencia() {
        return idValencia;
    }

    public void setIdValencia(ValenciaNucleoExecutivo idValencia) {
        this.idValencia = idValencia;
    }

    public String getHabilitacoesliterarias() {
        return habilitacoesliterarias;
    }

    public void setHabilitacoesliterarias(String habilitacoesliterarias) {
        this.habilitacoesliterarias = habilitacoesliterarias;
    }

    public Boolean getFormacaoprofissional() {
        return formacaoprofissional;
    }

    public void setFormacaoprofissional(Boolean formacaoprofissional) {
        this.formacaoprofissional = formacaoprofissional;
    }

    public String getCategoriaprofissional() {
        return categoriaprofissional;
    }

    public void setCategoriaprofissional(String categoriaprofissional) {
        this.categoriaprofissional = categoriaprofissional;
    }

    public Integer getAnosservicoinstituicao() {
        return anosservicoinstituicao;
    }

    public void setAnosservicoinstituicao(Integer anosservicoinstituicao) {
        this.anosservicoinstituicao = anosservicoinstituicao;
    }
}