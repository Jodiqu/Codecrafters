package dao;

public class DAOFactory {
    public static ClienteDAO getClienteDAO() { return new HibernateClienteDAO(); }
    public static ArticuloDAO getArticuloDAO() { return new HibernateArticuloDAO(); }
    public static PedidoDAO getPedidoDAO() { return new HibernatePedidoDAO(); }
    public static PremiumDAO getPremiumDAO() { return new HibernatePremiumDAO(); }
}
