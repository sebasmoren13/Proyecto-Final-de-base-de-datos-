package org.example.model;

public class Cliente {
    private int clienteID;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String direccion;

    public Cliente() {}

    public Cliente(int clienteID, String nombre, String apellido, String telefono, String email, String direccion) {
        this.clienteID = clienteID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    public int getClienteID() { return clienteID; }
    public void setClienteID(int clienteID) { this.clienteID = clienteID; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return "Cliente{" +
                "clienteID=" + clienteID +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
