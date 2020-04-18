package chaves.camila.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "cantidad")
    private Long cantidad;

    @Column(name = "precio")
    private Long precio;

    @ManyToOne
    @JsonIgnoreProperties("items")
    private Orden orden;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Item codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDetalle() {
        return detalle;
    }

    public Item detalle(String detalle) {
        this.detalle = detalle;
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public Item cantidad(Long cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPrecio() {
        return precio;
    }

    public Item precio(Long precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public Orden getOrden() {
        return orden;
    }

    public Item orden(Orden orden) {
        this.orden = orden;
        return this;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", detalle='" + getDetalle() + "'" +
            ", cantidad=" + getCantidad() +
            ", precio=" + getPrecio() +
            "}";
    }
}
