package chaves.camila.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Contacto.
 */
@Entity
@Table(name = "contacto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contacto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_persona")
    private String nombrePersona;

    @Column(name = "author_wa")
    private String authorWA;

    @ManyToOne
    @JsonIgnoreProperties("contactos")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public Contacto nombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
        return this;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getAuthorWA() {
        return authorWA;
    }

    public Contacto authorWA(String authorWA) {
        this.authorWA = authorWA;
        return this;
    }

    public void setAuthorWA(String authorWA) {
        this.authorWA = authorWA;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Contacto cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contacto)) {
            return false;
        }
        return id != null && id.equals(((Contacto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contacto{" +
            "id=" + getId() +
            ", nombrePersona='" + getNombrePersona() + "'" +
            ", authorWA='" + getAuthorWA() + "'" +
            "}";
    }
}
