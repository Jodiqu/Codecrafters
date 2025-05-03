package model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "Pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero")
    private int numero;

    @ManyToOne
    @JoinColumn(name = "cliente_nif", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "articulo_codigo", nullable = false)
    private Articulo articulo;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_hora", nullable = false)
    private Date fechaHora;

    @Column(name = "enviado", nullable = false)
    private boolean enviado;

    /** Constructor sin argumentos requerido por Hibernate */
    public Pedido() {
    }

        /**
     * Constructor de conveniencia sin especificar estado enviado
     * (por defecto enviado = false).
     */
    public Pedido(Cliente cliente, Articulo articulo, int cantidad, Date fechaHora) {
        this(cliente, articulo, cantidad, fechaHora, false);
    }

    /** Constructor de conveniencia */
    public Pedido(
        Cliente cliente,
        Articulo articulo,
        int cantidad,
        Date fechaHora,
        boolean enviado
    ) {
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
        this.enviado = enviado;
    }

    // Getters y setters
    public int getNumero() {
        return numero;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Articulo getArticulo() {
        return articulo;
    }
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public Date getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
    public boolean isEnviado() {
        return enviado;
    }
    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    @Override
    public String toString() {
        return String.format(
            "Pedido[numero=%d, cliente=%s, articulo=%s, cantidad=%d, fechaHora=%s, enviado=%b]",
            numero,
            cliente != null ? cliente.getNif() : "null",
            articulo != null ? articulo.getCodigo() : "null",
            cantidad,
            fechaHora,
            enviado
        );
    }
}
