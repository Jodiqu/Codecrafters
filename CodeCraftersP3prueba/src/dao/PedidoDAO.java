// src/dao/PedidoDAO.java
// Pega aquí el código correspondiente.
package dao;

import model.Pedido;
import java.util.List;

public interface PedidoDAO {
    void addPedido(Pedido pedido) throws DAOException;
    Pedido getPedidoByNumero(int numero) throws DAOException;
    List<Pedido> getPedidosPendientes() throws DAOException;
    List<Pedido> getPedidosEnviados() throws DAOException;
    void deletePedido(int numero) throws DAOException;
    // nuevo método:
    void markPedidoEnviado(int numero) throws DAOException;
}