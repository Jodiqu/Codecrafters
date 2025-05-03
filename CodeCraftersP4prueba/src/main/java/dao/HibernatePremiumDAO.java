package dao;

import model.Premium;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class HibernatePremiumDAO implements PremiumDAO {
    @Override
    public void addPremium(Premium premium) throws DAOException {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(premium);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new DAOException("Error adding Premium", e);
        }
    }

    @Override
    public List<Premium> getAllPremium() throws DAOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Premium> query = session.createQuery("from Premium", Premium.class);
            return query.list();
        } catch (Exception e) {
            throw new DAOException("Error fetching all Premium", e);
        }
    }
}