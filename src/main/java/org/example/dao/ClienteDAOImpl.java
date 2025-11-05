package org.example.dao;

import org.example.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {
    private final Connection conn;

    public ClienteDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void crear(Cliente cliente) {
        // Si clienteID > 0 intentamos insertar con id explícito, si es 0 dejamos que la BD lo genere
        String sqlWithId = "INSERT INTO clientes (clienteID, nombre, apellido, telefono, email, direccion) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlNoId = "INSERT INTO clientes (nombre, apellido, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";

        try {
            if (cliente.getClienteID() > 0) {
                try (PreparedStatement ps = conn.prepareStatement(sqlWithId)) {
                    ps.setInt(1, cliente.getClienteID());
                    ps.setString(2, cliente.getNombre());
                    ps.setString(3, cliente.getApellido());
                    ps.setString(4, cliente.getTelefono());
                    ps.setString(5, cliente.getEmail());
                    ps.setString(6, cliente.getDireccion());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(sqlNoId, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, cliente.getNombre());
                    ps.setString(2, cliente.getApellido());
                    ps.setString(3, cliente.getTelefono());
                    ps.setString(4, cliente.getEmail());
                    ps.setString(5, cliente.getDireccion());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) cliente.setClienteID(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente leer(int id) {
        String sql = "SELECT * FROM clientes WHERE clienteID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, apellido=?, telefono=?, email=?, direccion=? WHERE clienteID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getDireccion());
            ps.setInt(6, cliente.getClienteID());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE clienteID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
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
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}
