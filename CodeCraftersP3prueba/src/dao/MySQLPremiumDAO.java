package dao;

import model.Premium;
import util.DBConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLPremiumDAO implements PremiumDAO {
    private static final String CALL_ADD = "{CALL sp_add_premium(?,?,?)}";
    private static final String SELECT_ALL =
        "SELECT c.nif, c.nombre, c.domicilio, c.email, p.cuota_anual, p.descuento_envio " +
        "FROM Cliente c JOIN Premium p ON c.nif = p.nif";

    @Override
    public void addPremium(Premium premium) throws DAOException {
        // Inserta primero en Cliente (tablas herencia)
        ClienteDAO clienteDAO = DAOFactory.getClienteDAO();
        clienteDAO.addCliente(premium);
        // Luego en Premium
        try (Connection conn = DBConnectionUtil.getConnection();
             CallableStatement cs = conn.prepareCall(CALL_ADD)) {
            cs.setString(1, premium.getNif());
            cs.setDouble(2, premium.getCuotaAnual());
            cs.setDouble(3, premium.getDescuentoEnvio());
            cs.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error adding Premium client", e);
        }
    }

    @Override
    public List<Premium> getAllPremium() throws DAOException {
        List<Premium> list = new ArrayList<>();
        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Premium p = new Premium(
                    rs.getString("nif"),
                    rs.getString("nombre"),
                    rs.getString("domicilio"),
                    rs.getString("email"),
                    rs.getDouble("cuota_anual"),
                    rs.getDouble("descuento_envio")
                );
                list.add(p);
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException("Error fetching Premium clients", e);
        }
    }
}
