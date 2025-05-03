package dao;

import model.Pedido;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class HibernatePedidoDAO implements PedidoDAO {
    @Override
    public void addPedido(Pedido pedido) throws DAOException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(pedido);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DAOException("Error adding Pedido", e);
        }
    }

    @Override
    public Pedido getPedidoByNumero(int numero) throws DAOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Pedido.class, numero);
        } catch (Exception e) {
            throw new DAOException("Error fetching Pedido by numero", e);
        }
    }

    @Override
    public List<Pedido> getPedidosPendientes() throws DAOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Pedido> query = session.createQuery("from Pedido where enviado = false", Pedido.class);
            return query.list();
        } catch (Exception e) {
            throw new DAOException("Error fetching pending Pedidos", e);
        }
    }

    @Override
    public List<Pedido> getPedidosEnviados() throws DAOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Pedido> query = session.createQuery("from Pedido where enviado = true", Pedido.class);
            return query.list();
        } catch (Exception e) {
            throw new DAOException("Error fetching sent Pedidos", e);
        }
    }

    @Override
    public void deletePedido(int numero) throws DAOException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Pedido pedido = session.get(Pedido.class, numero);
            if (pedido != null) session.delete(pedido);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DAOException("Error deleting Pedido", e);
        }
    }

    @Override
    public void markPedidoEnviado(int numero) throws DAOException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Pedido pedido = session.get(Pedido.class, numero);
            if (pedido != null) {
                pedido.setEnviado(true);
                session.update(pedido);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DAOException("Error marking Pedido as sent", e);
        }
    }
}