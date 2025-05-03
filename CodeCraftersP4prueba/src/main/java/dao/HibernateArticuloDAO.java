package dao;

import model.Articulo;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class HibernateArticuloDAO implements ArticuloDAO {
    @Override
    public void addArticulo(Articulo articulo) throws DAOException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(articulo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DAOException("Error adding Articulo", e);
        }
    }

    @Override
    public Articulo getArticuloByCodigo(String codigo) throws DAOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Articulo.class, codigo);
        } catch (Exception e) {
            throw new DAOException("Error fetching Articulo by codigo", e);
        }
    }

    @Override
    public List<Articulo> getAllArticulos() throws DAOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Articulo> query = session.createQuery("from Articulo", Articulo.class);
            return query.list();
        } catch (Exception e) {
            throw new DAOException("Error fetching all Articulos", e);
        }
    }

    @Override
    public void deleteArticulo(String codigo) throws DAOException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Articulo articulo = session.get(Articulo.class, codigo);
            if (articulo != null) session.delete(articulo);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DAOException("Error deleting Articulo", e);
        }
    }
}