package org.example.model;


public class Cliente {
    private int id;  // Este 'id' representa el 'clienteID' de la BD
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String direccion;

    // Constructor con ID
    public Cliente(int id, String nombre, String apellido, String telefono, String email, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    // Constructor sin ID para nuevos clientes
    public Cliente(String nombre, String apellido, String telefono, String email, String direccion) {
        this.id = 0; // Se asignará automáticamente
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // toString
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    // También puedes agregar un toString más legible para mostrar en interfaces
    public String toStringLegible() {
        return "ID: " + id +
                " | Nombre: " + nombre + " " + apellido +
                " | Tel: " + telefono +
                " | Email: " + email +
                " | Dirección: " + direccion;
    }
}