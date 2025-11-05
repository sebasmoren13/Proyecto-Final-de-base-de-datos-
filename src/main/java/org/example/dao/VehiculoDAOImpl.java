package org.example.dao;

import org.example.model.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAOImpl implements VehiculoDAO {
    private final Connection conn;

    public VehiculoDAOImpl(Connection conn) { this.conn = conn; }

    @Override
    public void crear(Vehiculo v) {
        String sqlWithId = "INSERT INTO vehiculos (vehiculoID, clienteID, marca, modelo, placa, color, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlNoId = "INSERT INTO vehiculos (clienteID, marca, modelo, placa, color, tipo) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            if (v.getVehiculoID() > 0) {
                try (PreparedStatement ps = conn.prepareStatement(sqlWithId)) {
                    ps.setInt(1, v.getVehiculoID());
                    ps.setInt(2, v.getClienteID());
                    ps.setString(3, v.getMarca());
                    ps.setString(4, v.getModelo());
                    ps.setString(5, v.getPlaca());
                    ps.setString(6, v.getColor());
                    ps.setString(7, v.getTipo());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(sqlNoId, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, v.getClienteID());
                    ps.setString(2, v.getMarca());
                    ps.setString(3, v.getModelo());
                    ps.setString(4, v.getPlaca());
                    ps.setString(5, v.getColor());
                    ps.setString(6, v.getTipo());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) v.setVehiculoID(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public Vehiculo leer(int id) {
        String sql = "SELECT * FROM vehiculos WHERE vehiculoID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Vehiculo(
                        rs.getInt("vehiculoID"),
                        rs.getInt("clienteID"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getString("color"),
                        rs.getString("tipo")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public void actualizar(Vehiculo v) {
        String sql = "UPDATE vehiculos SET clienteID=?, marca=?, modelo=?, placa=?, color=?, tipo=? WHERE vehiculoID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, v.getClienteID());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setString(4, v.getPlaca());
            ps.setString(5, v.getColor());
            ps.setString(6, v.getTipo());
            ps.setInt(7, v.getVehiculoID());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM vehiculos WHERE vehiculoID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Vehiculo> listar() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Vehiculo(
                        rs.getInt("vehiculoID"),
                        rs.getInt("clienteID"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getString("color"),
                        rs.getString("tipo")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}
