package chaves.camila.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Conversacion.
 */
@Entity
@Table(name = "conversacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conversacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "session_df")
    private String sessionDF;

    @Column(name = "author_wa")
    private String authorWA;

    @Column(name = "inicio")
    private Instant inicio;

    @Column(name = "ult_act_cli")
    private Instant ultActCli;

    @Column(name = "fin")
    private Instant fin;

    @OneToMany(mappedBy = "conversacion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chat> chats = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("conversacions")
    private Agente agente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public Conversacion cliente(String cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getSessionDF() {
        return sessionDF;
    }

    public Conversacion sessionDF(String sessionDF) {
        this.sessionDF = sessionDF;
        return this;
    }

    public void setSessionDF(String sessionDF) {
        this.sessionDF = sessionDF;
    }

    public String getAuthorWA() {
        return authorWA;
    }

    public Conversacion authorWA(String authorWA) {
        this.authorWA = authorWA;
        return this;
    }

    public void setAuthorWA(String authorWA) {
        this.authorWA = authorWA;
    }

    public Instant getInicio() {
        return inicio;
    }

    public Conversacion inicio(Instant inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(Instant inicio) {
        this.inicio = inicio;
    }

    public Instant getUltActCli() {
        return ultActCli;
    }

    public Conversacion ultActCli(Instant ultActCli) {
        this.ultActCli = ultActCli;
        return this;
    }

    public void setUltActCli(Instant ultActCli) {
        this.ultActCli = ultActCli;
    }

    public Instant getFin() {
        return fin;
    }

    public Conversacion fin(Instant fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(Instant fin) {
        this.fin = fin;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public Conversacion chats(Set<Chat> chats) {
        this.chats = chats;
        return this;
    }

    public Conversacion addChat(Chat chat) {
        this.chats.add(chat);
        chat.setConversacion(this);
        return this;
    }

    public Conversacion removeChat(Chat chat) {
        this.chats.remove(chat);
        chat.setConversacion(null);
        return this;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    public Agente getAgente() {
        return agente;
    }

    public Conversacion agente(Agente agente) {
        this.agente = agente;
        return this;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conversacion)) {
            return false;
        }
        return id != null && id.equals(((Conversacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Conversacion{" +
            "id=" + getId() +
            ", cliente='" + getCliente() + "'" +
            ", sessionDF='" + getSessionDF() + "'" +
            ", authorWA='" + getAuthorWA() + "'" +
            ", inicio='" + getInicio() + "'" +
            ", ultActCli='" + getUltActCli() + "'" +
            ", fin='" + getFin() + "'" +
            "}";
    }
}
