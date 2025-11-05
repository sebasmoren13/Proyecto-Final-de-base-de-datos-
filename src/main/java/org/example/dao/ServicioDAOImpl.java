package org.example.dao;

import org.example.model.Servicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAOImpl implements ServicioDAO {
    private final Connection conn;

    public ServicioDAOImpl(Connection conn) { this.conn = conn; }

    @Override
    public void crear(Servicio s) {
        String sqlWithId = "INSERT INTO servicios (servicioID, nombre, precio) VALUES (?, ?, ?)";
        String sqlNoId = "INSERT INTO servicios (nombre, precio) VALUES (?, ?)";
        try {
            if (s.getServicioID() > 0) {
                try (PreparedStatement ps = conn.prepareStatement(sqlWithId)) {
                    ps.setInt(1, s.getServicioID());
                    ps.setString(2, s.getNombre());
                    ps.setDouble(3, s.getPrecio());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(sqlNoId, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, s.getNombre());
                    ps.setDouble(2, s.getPrecio());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) s.setServicioID(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public Servicio leer(int id) {
        String sql = "SELECT * FROM servicios WHERE servicioID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return new Servicio(
                        rs.getInt("servicioID"),
                        rs.getString("nombre"),
                        rs.getDouble("precio")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public void actualizar(Servicio s) {
        String sql = "UPDATE servicios SET nombre=?, precio=? WHERE servicioID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getNombre());
            ps.setDouble(2, s.getPrecio());
            ps.setInt(3, s.getServicioID());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM servicios WHERE servicioID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Servicio> listar() {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicios";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Servicio(
                        rs.getInt("servicioID"),
                        rs.getString("nombre"),
                        rs.getDouble("precio")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}
