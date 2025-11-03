package org.example.dao;

import org.example.model.Vehiculo;
import java.util.List;

public interface VehiculoDAO {
    void crear(Vehiculo vehiculo);
    Vehiculo leer(int id);
    void actualizar(Vehiculo vehiculo);
    void eliminar(int id);
    List<Vehiculo> listar();
}
