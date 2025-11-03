package org.example.model;

public class Servicio {
    private int servicioID;
    private String nombre;
    private double precio;

    public Servicio() {}

    public Servicio(int servicioID, String nombre, double precio) {
        this.servicioID = servicioID;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getServicioID() { return servicioID; }
    public void setServicioID(int servicioID) { this.servicioID = servicioID; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return "Servicio{" +
                "servicioID=" + servicioID +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}
