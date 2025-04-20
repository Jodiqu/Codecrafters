// src/model/Premium.java
// Pega aquí el código correspondiente.
package model;

public class Premium extends Cliente {
    private double cuotaAnual;
    private double descuentoEnvio;

    public Premium(String nif, String nombre, String domicilio, String email, double cuotaAnual, double descuentoEnvio) {
        super(nif, nombre, domicilio, email);
        this.cuotaAnual = cuotaAnual;
        this.descuentoEnvio = descuentoEnvio;
    }

    public double getCuotaAnual() { return cuotaAnual; }
    public void setCuotaAnual(double cuotaAnual) { this.cuotaAnual = cuotaAnual; }
    public double getDescuentoEnvio() { return descuentoEnvio; }
    public void setDescuentoEnvio(double descuentoEnvio) { this.descuentoEnvio = descuentoEnvio; }

    public double calcularDescuentoEnvio(double gastos) {
        return gastos * (descuentoEnvio / 100);
    }

    @Override
    public String toString() {
        return String.format("Premium[%s, cuotaAnual=%.2f, descuentoEnvio=%.2f%%]", super.toString(), cuotaAnual, descuentoEnvio);
    }
}