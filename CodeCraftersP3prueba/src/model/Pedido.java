// src/model/Pedido.java
// Pega aquí el código correspondiente.
package model;

import java.util.Date;

public class Pedido {
    private int numero;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private Date fechaHora;
    private boolean enviado;

    public Pedido(Cliente cliente, Articulo articulo, int cantidad, Date fechaHora) {
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
        this.enviado = false;
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public Date getFechaHora() { return fechaHora; }
    public void setFechaHora(Date fechaHora) { this.fechaHora = fechaHora; }
    public boolean isEnviado() { return enviado; }
    public void setEnviado(boolean enviado) { this.enviado = enviado; }

    @Override
    public String toString() {
        return String.format("Pedido[numero=%d, cliente=%s, articulo=%s, cantidad=%d, fechaHora=%s, enviado=%b]",
            numero, cliente.getNif(), articulo.getCodigo(), cantidad, fechaHora, enviado);
    }
}