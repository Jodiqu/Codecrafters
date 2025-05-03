// src/main/java/model/Articulo.java
package model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

/**
 * Entidad que representa un artículo de la tienda.
 */
@Entity
@Table(name = "Articulo")
public class Articulo {

    @Id
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_venta")
    private double precioVenta;

    @Column(name = "gastos_envio")
    private double gastosEnvio;

    @Column(name = "tiempo_preparacion")
    private int tiempoPreparacion;

    /**
     * Constructor sin argumentos requerido por Hibernate.
     */
    public Articulo() {
    }

    /**
     * Constructor de conveniencia.
     */
    public Articulo(String codigo, String descripcion,
                    double precioVenta, double gastosEnvio,
                    int tiempoPreparacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    // Getters y setters

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    @Override
    public String toString() {
        return String.format(
            "Articulo[codigo=%s, descripcion=%s, precioVenta=%.2f, gastosEnvio=%.2f, tiempoPreparacion=%d]",
            codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion
        );
    }
}
