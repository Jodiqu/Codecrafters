// src/dao/MySQLPedidoDAO.java
// Pega aquí el código correspondiente.
package dao;

import model.Pedido;
import model.Cliente;
import model.Articulo;
import util.DBConnectionUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MySQLPedidoDAO implements PedidoDAO {
    private static final String CALL_MARK_ENVIADO = "{CALL sp_mark_pedido_enviado(?)}";
    private static final String CALL_ADD       = "{CALL sp_add_pedido(?,?,?,?,?)}";
    private static final String CALL_DELETE    = "{CALL sp_delete_pedido(?)}";
    private static final String SELECT_BY_NUM  =
        "SELECT numero, cliente_nif, articulo_codigo, cantidad, fecha_hora, enviado FROM Pedido WHERE numero = ?";
    private static final String SELECT_PEND    =
        "SELECT numero, cliente_nif, articulo_codigo, cantidad, fecha_hora, enviado FROM Pedido WHERE enviado = false";
    private static final String SELECT_SENT    =
        "SELECT numero, cliente_nif, articulo_codigo, cantidad, fecha_hora, enviado FROM Pedido WHERE enviado = true";

    private final ClienteDAO clienteDAO = DAOFactory.getClienteDAO();
    private final ArticuloDAO articuloDAO = DAOFactory.getArticuloDAO();

    @Override
    public void addPedido(Pedido pedido) throws DAOException {
        Connection conn = null;
        try {
            conn = DBConnectionUtil.getConnection();
            conn.setAutoCommit(false);
            try (CallableStatement cs = conn.prepareCall(CALL_ADD)) {
                cs.setString(1, pedido.getCliente().getNif());
                cs.setString(2, pedido.getArticulo().getCodigo());
                cs.setInt(3, pedido.getCantidad());
                cs.setTimestamp(4, new Timestamp(pedido.getFechaHora().getTime()));
                cs.setBoolean(5, pedido.isEnviado());
                cs.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            throw new DAOException("Error adding Pedido", e);
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch(SQLException ex) { /* ignore */ }
            }
        }
    }

    @Override
    public Pedido getPedidoByNumero(int numero) throws DAOException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_NUM)) {
            ps.setInt(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                String nif = rs.getString("cliente_nif");
                String cod = rs.getString("articulo_codigo");
                Cliente c = clienteDAO.getClienteByNif(nif);
                Articulo a = articuloDAO.getArticuloByCodigo(cod);
                Pedido p = new Pedido(c, a, rs.getInt("cantidad"), rs.getTimestamp("fecha_hora"));
                p.setEnviado(rs.getBoolean("enviado"));
                p.setNumero(rs.getInt("numero"));
                return p;
            }
        } catch (SQLException e) {
            throw new DAOException("Error fetching Pedido by numero", e);
        }
    }

    @Override
    public List<Pedido> getPedidosPendientes() throws DAOException {
        List<Pedido> list = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_PEND);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nif = rs.getString("cliente_nif");
                String cod = rs.getString("articulo_codigo");
                Cliente c = clienteDAO.getClienteByNif(nif);
                Articulo a = articuloDAO.getArticuloByCodigo(cod);
                Pedido p = new Pedido(c, a, rs.getInt("cantidad"), rs.getTimestamp("fecha_hora"));
                p.setEnviado(false);
                p.setNumero(rs.getInt("numero"));
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException("Error fetching pending Pedidos", e);
        }
    }

    @Override
    public List<Pedido> getPedidosEnviados() throws DAOException {
        List<Pedido> list = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_SENT);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nif = rs.getString("cliente_nif");
                String cod = rs.getString("articulo_codigo");
                Cliente c = clienteDAO.getClienteByNif(nif);
                Articulo a = articuloDAO.getArticuloByCodigo(cod);
                Pedido p = new Pedido(c, a, rs.getInt("cantidad"), rs.getTimestamp("fecha_hora"));
                p.setEnviado(true);
                p.setNumero(rs.getInt("numero"));
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException("Error fetching sent Pedidos", e);
        }
    }

    @Override
    public void deletePedido(int numero) throws DAOException {
        try (Connection conn = DBConnectionUtil.getConnection();
             CallableStatement cs = conn.prepareCall(CALL_DELETE)) {
            cs.setInt(1, numero);
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Pedido", e);
        }
    }
   
    @Override
    public void markPedidoEnviado(int numero) throws DAOException {
        try (Connection conn = DBConnectionUtil.getConnection();
             CallableStatement cs = conn.prepareCall(CALL_MARK_ENVIADO)) {
            cs.setInt(1, numero);
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error marking Pedido as enviado", e);
        }
    }
}
