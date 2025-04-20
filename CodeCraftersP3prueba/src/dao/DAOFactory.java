// src/dao/DAOFactory.java
// Pega aquí el código correspondiente.
package dao;

public class DAOFactory {
    public static ClienteDAO getClienteDAO() { return new MySQLClienteDAO(); }
    public static ArticuloDAO getArticuloDAO() { return new MySQLArticuloDAO(); }
    public static PedidoDAO getPedidoDAO() { return new MySQLPedidoDAO(); }
    public static PremiumDAO getPremiumDAO() { return new MySQLPremiumDAO(); }
}