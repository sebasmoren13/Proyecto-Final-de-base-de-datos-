package org.example.dao;

import org.example.model.Servicio;
import java.util.List;

public interface ServicioDAO {
    void crear(Servicio servicio);
    Servicio leer(int id);
    void actualizar(Servicio servicio);
    void eliminar(int id);
    List<Servicio> listar();
}
