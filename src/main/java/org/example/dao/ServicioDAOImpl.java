package org.example.dao;

import org.example.model.Servicio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAOImpl implements ServicioDAO {
    private final Connection connection;

    public ServicioDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void crear(Servicio servicio) {
        String sql = "INSERT INTO servicios (nombre, precio) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, servicio.getNombre());
            ps.setDouble(2, servicio.getPrecio());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Servicio leer(int id) {
        String sql = "SELECT * FROM servicios WHERE servicioID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Servicio(
                        rs.getInt("servicioID"),
                        rs.getString("nombre"),
                        rs.getDouble("precio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actualizar(Servicio servicio) {
        String sql = "UPDATE servicios SET nombre=?, precio=? WHERE servicioID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, servicio.getNombre());
            ps.setDouble(2, servicio.getPrecio());
            ps.setInt(3, servicio.getServicioID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM servicios WHERE servicioID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Servicio> listar() {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicios";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Servicio(
                        rs.getInt("servicioID"),
                        rs.getString("nombre"),
                        rs.getDouble("precio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
