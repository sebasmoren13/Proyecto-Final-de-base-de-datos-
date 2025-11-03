package org.example.dao;

import org.example.model.Cliente;
import java.util.List;

public interface ClienteDAO {
    void crear(Cliente cliente);
    Cliente leer(int id);
    void actualizar(Cliente cliente);
    void eliminar(int id);
    List<Cliente> listar();
}
