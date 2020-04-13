package chaves.camila.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Orden.
 */
@Entity
@Table(name = "orden")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "total")
    private Long total;

    @OneToMany(mappedBy = "orden")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LineaOrden> lineaOrdens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public Orden numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getCliente() {
        return cliente;
    }

    public Orden cliente(String cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Orden fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Long getTotal() {
        return total;
    }

    public Orden total(Long total) {
        this.total = total;
        return this;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Set<LineaOrden> getLineaOrdens() {
        return lineaOrdens;
    }

    public Orden lineaOrdens(Set<LineaOrden> lineaOrdens) {
        this.lineaOrdens = lineaOrdens;
        return this;
    }

    public Orden addLineaOrden(LineaOrden lineaOrden) {
        this.lineaOrdens.add(lineaOrden);
        lineaOrden.setOrden(this);
        return this;
    }

    public Orden removeLineaOrden(LineaOrden lineaOrden) {
        this.lineaOrdens.remove(lineaOrden);
        lineaOrden.setOrden(null);
        return this;
    }

    public void setLineaOrdens(Set<LineaOrden> lineaOrdens) {
        this.lineaOrdens = lineaOrdens;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Orden)) {
            return false;
        }
        return id != null && id.equals(((Orden) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Orden{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", cliente='" + getCliente() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", total=" + getTotal() +
            "}";
    }
}
