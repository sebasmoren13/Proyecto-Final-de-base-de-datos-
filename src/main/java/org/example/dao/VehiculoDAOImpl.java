package org.example.dao;

import org.example.model.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAOImpl implements VehiculoDAO {
    private final Connection connection;

    public VehiculoDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void crear(Vehiculo v) {
        String sql = "INSERT INTO vehiculos (id, clienteID, marca, modelo, placa, color, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, v.getId());
            ps.setInt(2, v.getClienteID());
            ps.setString(3, v.getMarca());
            ps.setString(4, v.getModelo());
            ps.setString(5, v.getPlaca());
            ps.setString(6, v.getColor());
            ps.setString(7, v.getTipo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vehiculo leer(int id) {
        String sql = "SELECT * FROM vehiculos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Vehiculo(
                            rs.getInt("id"),
                            rs.getInt("clienteID"),
                            rs.getString("marca"),
                            rs.getString("modelo"),
                            rs.getString("placa"),
                            rs.getString("color"),
                            rs.getString("tipo")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actualizar(Vehiculo v) {
        String sql = "UPDATE vehiculos SET clienteID = ?, marca = ?, modelo = ?, placa = ?, color = ?, tipo = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, v.getClienteID());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setString(4, v.getPlaca());
            ps.setString(5, v.getColor());
            ps.setString(6, v.getTipo());
            ps.setInt(7, v.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM vehiculos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vehiculo> listar() {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Vehiculo(
                        rs.getInt("id"),
                        rs.getInt("clienteID"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getString("color"),
                        rs.getString("tipo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
