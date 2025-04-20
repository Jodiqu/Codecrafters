// src/model/Cliente.java
// Pega aquí el código correspondiente.
package model;

public class Cliente {
    private String nif;
    private String nombre;
    private String domicilio;
    private String email;

    public Cliente(String nif, String nombre, String domicilio, String email) {
        this.nif = nif;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.email = email;
    }

    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format("Cliente[nif=%s, nombre=%s, domicilio=%s, email=%s]", nif, nombre, domicilio, email);
    }
}