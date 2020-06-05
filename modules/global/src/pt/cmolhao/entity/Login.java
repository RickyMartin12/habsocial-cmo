package pt.cmolhao.entity;

import com.haulmont.cuba.core.entity.BaseIntegerIdEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.*;

@DesignSupport("{'imported':true}")
@Table(name = "login")
@Entity(name = "cmolhao_Login")
public class Login extends BaseIntegerIdEntity {
    private static final long serialVersionUID = -6103584937804272053L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instituicao_id")
    protected pt.cmolhao.entity.Instituicoes instituicao;
    @Column(name = "is_enabled", nullable = false)
    protected Integer isEnabled;
    @Column(name = "password", length = 20)
    protected String password;
    @Column(name = "username", nullable = false, length = 20)
    protected String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public pt.cmolhao.entity.Instituicoes getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(pt.cmolhao.entity.Instituicoes instituicao) {
        this.instituicao = instituicao;
    }
}