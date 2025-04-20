// src/model/Articulo.java
// Pega aquí el código correspondiente.
package model;

public class Articulo {
    private String codigo;
    private String descripcion;
    private double precioVenta;
    private double gastosEnvio;
    private int tiempoPreparacion;

    public Articulo(String codigo, String descripcion, double precioVenta, double gastosEnvio, int tiempoPreparacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }
    public double getGastosEnvio() { return gastosEnvio; }
    public void setGastosEnvio(double gastosEnvio) { this.gastosEnvio = gastosEnvio; }
    public int getTiempoPreparacion() { return tiempoPreparacion; }
    public void setTiempoPreparacion(int tiempoPreparacion) { this.tiempoPreparacion = tiempoPreparacion; }

    @Override
    public String toString() {
        return String.format("Articulo[codigo=%s, descripcion=%s, precioVenta=%.2f, gastosEnvio=%.2f, tiempoPreparacion=%d]",
            codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
    }
}