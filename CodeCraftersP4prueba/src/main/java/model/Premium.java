package model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Column;

@Entity
@Table(name = "Premium")
@PrimaryKeyJoinColumn(name = "nif")
public class Premium extends Cliente {

    @Column(name = "cuota_anual", nullable = false)
    private double cuotaAnual;

    @Column(name = "descuento_envio", nullable = false)
    private double descuentoEnvio;

    /** Constructor sin argumentos requerido por Hibernate */
    public Premium() {
    }

    /** Constructor de conveniencia */
    public Premium(
        String nif,
        String nombre,
        String domicilio,
        String email,
        double cuotaAnual,
        double descuentoEnvio
    ) {
        super(nif, nombre, domicilio, email);
        this.cuotaAnual = cuotaAnual;
        this.descuentoEnvio = descuentoEnvio;
    }

    // Getters y setters
    public double getCuotaAnual() {
        return cuotaAnual;
    }
    public void setCuotaAnual(double cuotaAnual) {
        this.cuotaAnual = cuotaAnual;
    }
    public double getDescuentoEnvio() {
        return descuentoEnvio;
    }
    public void setDescuentoEnvio(double descuentoEnvio) {
        this.descuentoEnvio = descuentoEnvio;
    }

    @Override
    public String toString() {
        return String.format(
            "Premium[nif=%s, nombre=%s, domicilio=%s, email=%s, cuotaAnual=%.2f, descuentoEnvio=%.2f]",
            getNif(), getNombre(), getDomicilio(), getEmail(),
            cuotaAnual, descuentoEnvio
        );
    }
}
