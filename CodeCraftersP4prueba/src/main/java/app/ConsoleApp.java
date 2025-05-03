package app;

import dao.ArticuloDAO;
import dao.ClienteDAO;
import dao.DAOException;
import dao.DAOFactory;
import dao.PedidoDAO;
import dao.PremiumDAO;
import model.Articulo;
import model.Cliente;
import model.Pedido;
import model.Premium;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ConsoleApp {
    private static final ClienteDAO clienteDAO   = DAOFactory.getClienteDAO();
    private static final PremiumDAO premiumDAO   = DAOFactory.getPremiumDAO();
    private static final ArticuloDAO articuloDAO = DAOFactory.getArticuloDAO();
    private static final PedidoDAO pedidoDAO     = DAOFactory.getPedidoDAO();
    private static final Scanner scanner         = new Scanner(System.in);

    // Expresiones regulares para validar NIF y email
    private static final Pattern NIF_REGEX   = Pattern.compile("^\\d{8}[A-Za-z]$");
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$");

    private static boolean isValidNif(String nif) {
        return NIF_REGEX.matcher(nif).matches();
    }

    private static boolean isValidEmail(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- MENÚ TIENDA ---");
            System.out.println("1. Añadir cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Eliminar cliente");
            System.out.println("4. Añadir artículo");
            System.out.println("5. Listar artículos");
            System.out.println("6. Eliminar artículo");
            System.out.println("7. Crear pedido");
            System.out.println("8. Listar pedidos pendientes");
            System.out.println("9. Marcar pedido como enviado");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida.");
                continue;
            }
            try {
                switch (opcion) {
                    case 1: altaCliente(); break;
                    case 2: listarClientes(); break;
                    case 3: eliminarCliente(); break;
                    case 4: altaArticulo(); break;
                    case 5: listarArticulos(); break;
                    case 6: eliminarArticulo(); break;
                    case 7: crearPedido(); break;
                    case 8: listarPedidosPendientes(); break;
                    case 9: marcarEnviado(); break;
                    case 0:
                        System.out.println("¡Hasta luego!");
                        System.exit(0);
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (DAOException e) {
                System.err.println("Error en operación DAO: " + e.getMessage());
            }
        }
    }

    private static void altaCliente() throws DAOException {
        System.out.print("¿Es cliente Premium? (s/n): ");
        String resp = scanner.nextLine();

        // Validar NIF
        String nif;
        do {
            System.out.print("NIF (8 dígitos + letra): ");
            nif = scanner.nextLine();
            if (!isValidNif(nif)) {
                System.out.println("Formato de NIF inválido. Inténtalo de nuevo.");
            }
        } while (!isValidNif(nif));

        // Nombre y domicilio
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Domicilio: ");
        String domicilio = scanner.nextLine();

        // Validar email
        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine();
            if (!isValidEmail(email)) {
                System.out.println("Formato de email inválido. Inténtalo de nuevo.");
            }
        } while (!isValidEmail(email));

        try {
            if (resp.equalsIgnoreCase("s")) {
                System.out.print("Cuota anual: ");
                double cuota = Double.parseDouble(scanner.nextLine());
                System.out.print("Descuento envío (%): ");
                double desc = Double.parseDouble(scanner.nextLine());
                Premium p = new Premium(nif, nombre, domicilio, email, cuota, desc);
                premiumDAO.addPremium(p);
                System.out.println("Cliente Premium añadido.");
            } else {
                Cliente c = new Cliente(nif, nombre, domicilio, email);
                clienteDAO.addCliente(c);
                System.out.println("Cliente añadido.");
            }
        } catch (DAOException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                System.err.println("Error: Ya existe un cliente con ese NIF o email.");
            } else {
                throw e;
            }
        }
    }

    private static void listarClientes() throws DAOException {
        List<Cliente> clientes = clienteDAO.getAllClientes();
        List<Premium> premiumList = premiumDAO.getAllPremium();
        Set<String> premiumNifs = premiumList.stream()
            .map(Premium::getNif)
            .collect(Collectors.toSet());
        for (Cliente c : clientes) {
            if (premiumNifs.contains(c.getNif())) {
                Premium p = premiumList.stream()
                    .filter(pr -> pr.getNif().equals(c.getNif()))
                    .findFirst()
                    .orElse(null);
                System.out.println(p);
            } else {
                System.out.println(c);
            }
        }
    }

    private static void eliminarCliente() throws DAOException {
        System.out.print("NIF del cliente a eliminar: ");
        String nif = scanner.nextLine();
        clienteDAO.deleteCliente(nif);
        System.out.println("Cliente eliminado.");
    }

    private static void altaArticulo() throws DAOException {
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Descripción: ");
        String desc = scanner.nextLine();
        System.out.print("Precio venta: ");
        double p = Double.parseDouble(scanner.nextLine());
        System.out.print("Gastos envío: ");
        double g = Double.parseDouble(scanner.nextLine());
        System.out.print("Tiempo preparación (min): ");
        int t = Integer.parseInt(scanner.nextLine());
        Articulo a = new Articulo(codigo, desc, p, g, t);
        articuloDAO.addArticulo(a);
        System.out.println("Artículo añadido.");
    }

    private static void listarArticulos() throws DAOException {
        List<Articulo> art = articuloDAO.getAllArticulos();
        art.forEach(System.out::println);
    }

    private static void eliminarArticulo() throws DAOException {
        System.out.print("Código del artículo a eliminar: ");
        String codigo = scanner.nextLine();
        articuloDAO.deleteArticulo(codigo);
        System.out.println("Artículo eliminado.");
    }

    private static void crearPedido() throws DAOException {
        System.out.print("Email cliente: ");
        String email = scanner.nextLine();
        Cliente c = clienteDAO.getClienteByEmail(email);
        if (c == null) {
            System.out.println("Cliente con email '" + email + "' no encontrado.");
            return;
        }
        System.out.print("Código artículo: ");
        String cod = scanner.nextLine();
        Articulo a = articuloDAO.getArticuloByCodigo(cod);
        if (a == null) {
            System.out.println("Artículo con código '" + cod + "' no encontrado.");
            return;
        }
        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(scanner.nextLine());
        Pedido p = new Pedido(c, a, cantidad, new Date());
        pedidoDAO.addPedido(p);
        System.out.println("Pedido creado.");
    }

    private static void listarPedidosPendientes() throws DAOException {
        List<Pedido> ps = pedidoDAO.getPedidosPendientes();
        ps.forEach(System.out::println);
    }

    private static void marcarEnviado() throws DAOException {
        System.out.print("Número de pedido a marcar: ");
        int num = Integer.parseInt(scanner.nextLine());
        pedidoDAO.markPedidoEnviado(num);
        System.out.println("Pedido marcado como enviado.");
    }
}
