// src/dao/MySQLArticuloDAO.java
// Pega aquí el código correspondiente.
package dao;

import model.Articulo;
import util.DBConnectionUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLArticuloDAO implements ArticuloDAO {
    private static final String CALL_DELETE = "{CALL sp_delete_articulo(?)}";
    private static final String CALL_ADD = "{CALL sp_add_articulo(?,?,?,?,?)}";
    private static final String SELECT_BY_CODIGO =
        "SELECT codigo, descripcion, precio_venta, gastos_envio, tiempo_preparacion FROM Articulo WHERE codigo = ?";
    private static final String SELECT_ALL =
        "SELECT codigo, descripcion, precio_venta, gastos_envio, tiempo_preparacion FROM Articulo";

    @Override
    public void addArticulo(Articulo articulo) throws DAOException {
        try (Connection conn = DBConnectionUtil.getConnection();
             CallableStatement cs = conn.prepareCall(CALL_ADD)) {
            cs.setString(1, articulo.getCodigo());
            cs.setString(2, articulo.getDescripcion());
            cs.setDouble(3, articulo.getPrecioVenta());
            cs.setDouble(4, articulo.getGastosEnvio());
            cs.setInt(5, articulo.getTiempoPreparacion());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error adding Articulo", e);
        }
    }

    @Override
    public Articulo getArticuloByCodigo(String codigo) throws DAOException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_CODIGO)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                    ? new Articulo(rs.getString("codigo"), rs.getString("descripcion"), rs.getDouble("precio_venta"), rs.getDouble("gastos_envio"), rs.getInt("tiempo_preparacion"))
                    : null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error fetching Articulo by codigo", e);
        }
    }

    @Override
    public List<Articulo> getAllArticulos() throws DAOException {
        List<Articulo> list = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Articulo(rs.getString("codigo"), rs.getString("descripcion"), rs.getDouble("precio_venta"), rs.getDouble("gastos_envio"), rs.getInt("tiempo_preparacion")));
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException("Error fetching all Articulos", e);
        }
    }

    @Override
    public void deleteArticulo(String codigo) throws DAOException {
        try (Connection conn = DBConnectionUtil.getConnection();
             CallableStatement cs = conn.prepareCall(CALL_DELETE)) {
            cs.setString(1, codigo);
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Articulo", e);
        }
    }
}
