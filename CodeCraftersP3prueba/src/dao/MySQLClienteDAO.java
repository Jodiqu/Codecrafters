// src/dao/MySQLClienteDAO.java
// Pega aquí el código correspondiente.
package dao;

import model.Cliente;
import util.DBConnectionUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;


public class MySQLClienteDAO implements ClienteDAO {
    private static final String CALL_DELETE = "{CALL sp_delete_cliente(?)}";
    private static final String CALL_ADD = "{CALL sp_add_cliente(?,?,?,?)}";
    private static final String SELECT_BY_EMAIL =
        "SELECT nif,nombre,domicilio,email FROM Cliente WHERE email = ?";
    private static final String SELECT_BY_NIF =
        "SELECT nif,nombre,domicilio,email FROM Cliente WHERE nif = ?";
    private static final String SELECT_ALL =
        "SELECT nif,nombre,domicilio,email FROM Cliente";

    @Override
    public void deleteCliente(String nif) throws DAOException {
        try (Connection conn = DBConnectionUtil.getConnection();
             CallableStatement cs = conn.prepareCall(CALL_DELETE)) {
            cs.setString(1, nif);
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting Cliente", e);
        }
    }

    @Override
    public void addCliente(Cliente cliente) throws DAOException {
        try (Connection conn = DBConnectionUtil.getConnection();
             CallableStatement cs = conn.prepareCall(CALL_ADD)) {
            cs.setString(1, cliente.getNif());
            cs.setString(2, cliente.getNombre());
            cs.setString(3, cliente.getDomicilio());
            cs.setString(4, cliente.getEmail());
            cs.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DAOException("Duplicado de clave (NIF/email)", e);
        } catch (SQLException e) {
            throw new DAOException("Error adding Cliente", e);
        }
    }
    @Override
    public Cliente getClienteByEmail(String email) throws DAOException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                    ? new Cliente(rs.getString("nif"), rs.getString("nombre"), rs.getString("domicilio"), rs.getString("email"))
                    : null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error fetching Cliente by email", e);
        }
    }

    @Override
    public Cliente getClienteByNif(String nif) throws DAOException {
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_NIF)) {
            ps.setString(1, nif);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                    ? new Cliente(rs.getString("nif"), rs.getString("nombre"), rs.getString("domicilio"), rs.getString("email"))
                    : null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error fetching Cliente by NIF", e);
        }
    }

    @Override
    public List<Cliente> getAllClientes() throws DAOException {
        List<Cliente> list = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Cliente(rs.getString("nif"), rs.getString("nombre"), rs.getString("domicilio"), rs.getString("email")));
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException("Error fetching all Clientes", e);
        }
    }
}