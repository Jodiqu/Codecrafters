// src/dao/ArticuloDAO.java
// Pega aquí el código correspondiente.
package dao;

import model.Articulo;
import java.util.List;

public interface ArticuloDAO {
    void addArticulo(Articulo articulo) throws DAOException;
    Articulo getArticuloByCodigo(String codigo) throws DAOException;
    List<Articulo> getAllArticulos() throws DAOException;
    void deleteArticulo(String codigo) throws DAOException;  // nueva operación
}