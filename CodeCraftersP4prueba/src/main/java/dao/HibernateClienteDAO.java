package dao;

import model.Cliente;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class HibernateClienteDAO implements ClienteDAO {
    @Override
    public void addCliente(Cliente cliente) throws DAOException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DAOException("Error adding Cliente", e);
        }
    }

    @Override
    public Cliente getClienteByEmail(String email) throws DAOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Cliente> query = session.createQuery("from Cliente where email = :email", Cliente.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new DAOException("Error fetching Cliente by email", e);
        }
    }

    @Override
    public Cliente getClienteByNif(String nif) throws DAOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Cliente.class, nif);
        } catch (Exception e) {
            throw new DAOException("Error fetching Cliente by NIF", e);
        }
    }

    @Override
    public List<Cliente> getAllClientes() throws DAOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Cliente> query = session.createQuery("from Cliente", Cliente.class);
            return query.list();
        } catch (Exception e) {
            throw new DAOException("Error fetching all Clientes", e);
        }
    }

    @Override
    public void deleteCliente(String nif) throws DAOException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Cliente cliente = session.get(Cliente.class, nif);
            if (cliente != null) session.delete(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DAOException("Error deleting Cliente", e);
        }
    }
}