package org.example.dao;

import org.example.model.RegistroLavado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroLavadoDAOImpl implements RegistroLavadoDAO {
    private final Connection connection;

    public RegistroLavadoDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void crear(RegistroLavado registro) {
        String sql = "INSERT INTO registrosLavado (vehiculoID, servicioID, fechaLavado, horaInicio, horaFin, precioTotal) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, registro.getVehiculoID());
            statement.setInt(2, registro.getServicioID());
            statement.setDate(3, java.sql.Date.valueOf(registro.getFechaLavado()));
            statement.setTime(4, java.sql.Time.valueOf(registro.getHoraInicio()));
            statement.setTime(5, java.sql.Time.valueOf(registro.getHoraFin()));
            statement.setDouble(6, registro.getPrecioTotal());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RegistroLavado leer(int id) {
        String sql = "SELECT * FROM registrosLavado WHERE registroID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new RegistroLavado(
                        resultSet.getInt("registroID"),
                        resultSet.getInt("vehiculoID"),
                        resultSet.getInt("servicioID"),
                        resultSet.getDate("fechaLavado").toLocalDate(),
                        resultSet.getTime("horaInicio").toLocalTime(),
                        resultSet.getTime("horaFin").toLocalTime(),
                        resultSet.getDouble("precioTotal")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actualizar(RegistroLavado registro) {
        String sql = "UPDATE registrosLavado SET vehiculoID=?, servicioID=?, fechaLavado=?, horaInicio=?, horaFin=?, precioTotal=? WHERE registroID=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, registro.getVehiculoID());
            statement.setInt(2, registro.getServicioID());
            statement.setDate(3, java.sql.Date.valueOf(registro.getFechaLavado()));
            statement.setTime(4, java.sql.Time.valueOf(registro.getHoraInicio()));
            statement.setTime(5, java.sql.Time.valueOf(registro.getHoraFin()));
            statement.setDouble(6, registro.getPrecioTotal());
            statement.setInt(7, registro.getRegistroID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM registrosLavado WHERE registroID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<RegistroLavado> listar() {
        List<RegistroLavado> lista = new ArrayList<>();
        String sql = "SELECT * FROM registrosLavado";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                lista.add(new RegistroLavado(
                        resultSet.getInt("registroID"),
                        resultSet.getInt("vehiculoID"),
                        resultSet.getInt("servicioID"),
                        resultSet.getDate("fechaLavado").toLocalDate(),
                        resultSet.getTime("horaInicio").toLocalTime(),
                        resultSet.getTime("horaFin").toLocalTime(),
                        resultSet.getDouble("precioTotal")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
