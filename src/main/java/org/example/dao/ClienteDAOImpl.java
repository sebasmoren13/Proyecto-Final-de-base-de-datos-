package org.example.dao;

import org.example.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {
    private final Connection connection;

    public ClienteDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void crear(Cliente cliente) {
        String sql = "INSERT INTO clientes (clienteID, nombre, apellido, telefono, email, direccion) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, cliente.getId());  // Usar el ID que viene del main
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getDireccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int obtenerProximoId() {
        String sql = "SELECT MAX(clienteID) + 1 as nextId FROM clientes";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                int nextId = rs.getInt("nextId");
                return nextId > 0 ? nextId : 1; // Si es null, empezar en 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Valor por defecto
    }

    @Override
    public Cliente leer(int id) {
        String sql = "SELECT * FROM clientes WHERE clienteID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("clienteID"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("telefono"),
                            rs.getString("email"),
                            rs.getString("direccion")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, apellido=?, telefono=?, email=?, direccion=? WHERE clienteID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getDireccion());
            ps.setInt(6, cliente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE clienteID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getInt("clienteID"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("direccion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}