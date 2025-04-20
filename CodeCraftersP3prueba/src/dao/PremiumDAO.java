package dao;

import model.Premium;
import java.util.List;

public interface PremiumDAO {
    void addPremium(Premium premium) throws DAOException;
    List<Premium> getAllPremium() throws DAOException;
}
