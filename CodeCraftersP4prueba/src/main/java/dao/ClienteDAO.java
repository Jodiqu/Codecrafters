// src/dao/ClienteDAO.java
// Pega aquí el código correspondiente.
package dao;

import model.Cliente;
import java.util.List;

public interface ClienteDAO {
    void addCliente(Cliente cliente) throws DAOException;
    Cliente getClienteByEmail(String email) throws DAOException;
    Cliente getClienteByNif(String nif) throws DAOException;
    List<Cliente> getAllClientes() throws DAOException;
    void deleteCliente(String nif) throws DAOException;  // nueva operación
}