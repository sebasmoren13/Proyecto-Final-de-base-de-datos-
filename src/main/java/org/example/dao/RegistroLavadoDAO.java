package org.example.dao;

import org.example.model.RegistroLavado;
import java.util.List;

public interface RegistroLavadoDAO {
    void crear(RegistroLavado registro);
    RegistroLavado leer(int id);
    void actualizar(RegistroLavado registro);
    void eliminar(int id);
    List<RegistroLavado> listar();
}
