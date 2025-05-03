package model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Table(name = "Cliente")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente {

    @Id
    @Column(name = "nif", length = 9)
    private String nif;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "domicilio", length = 200)
    private String domicilio;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    /** Constructor sin argumentos requerido por Hibernate */
    public Cliente() {
    }

    /** Constructor de conveniencia */
    public Cliente(String nif, String nombre, String domicilio, String email) {
        this.nif = nif;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.email = email;
    }

    // Getters y setters
    public String getNif() {
        return nif;
    }
    public void setNif(String nif) {
        this.nif = nif;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDomicilio() {
        return domicilio;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format(
            "Cliente[nif=%s, nombre=%s, domicilio=%s, email=%s]",
            nif, nombre, domicilio, email
        );
    }
}
