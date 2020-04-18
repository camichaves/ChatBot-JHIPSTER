package chaves.camila.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Chat.
 */
@Entity
@Table(name = "chat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_wa")
    private String authorWA;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "archivo")
    private String archivo;

    @ManyToOne
    @JsonIgnoreProperties("chats")
    private Conversacion conversacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorWA() {
        return authorWA;
    }

    public Chat authorWA(String authorWA) {
        this.authorWA = authorWA;
        return this;
    }

    public void setAuthorWA(String authorWA) {
        this.authorWA = authorWA;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Chat mensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Chat fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getArchivo() {
        return archivo;
    }

    public Chat archivo(String archivo) {
        this.archivo = archivo;
        return this;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Conversacion getConversacion() {
        return conversacion;
    }

    public Chat conversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
        return this;
    }

    public void setConversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chat)) {
            return false;
        }
        return id != null && id.equals(((Chat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chat{" +
            "id=" + getId() +
            ", authorWA='" + getAuthorWA() + "'" +
            ", mensaje='" + getMensaje() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", archivo='" + getArchivo() + "'" +
            "}";
    }
}
