package chaves.camila.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "det_adicional")
    private String detAdicional;

    @Column(name = "precio")
    private Long precio;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "stock")
    private Long stock;

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

    public Producto codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDetalle() {
        return detalle;
    }

    public Producto detalle(String detalle) {
        this.detalle = detalle;
        return this;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDetAdicional() {
        return detAdicional;
    }

    public Producto detAdicional(String detAdicional) {
        this.detAdicional = detAdicional;
        return this;
    }

    public void setDetAdicional(String detAdicional) {
        this.detAdicional = detAdicional;
    }

    public Long getPrecio() {
        return precio;
    }

    public Producto precio(Long precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public Producto imagen(String imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Long getStock() {
        return stock;
    }

    public Producto stock(Long stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", detalle='" + getDetalle() + "'" +
            ", detAdicional='" + getDetAdicional() + "'" +
            ", precio=" + getPrecio() +
            ", imagen='" + getImagen() + "'" +
            ", stock=" + getStock() +
            "}";
    }
}
