package TestGestionPedidos.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Servicios.GestionPedidos;
import modelos.Pedido;

import java.util.ArrayList;
import java.util.List;

public class Test_20_Gestion_Pedidos{
    private GestionPedidos gestionPedidos;

    @BeforeEach
    public void setUp() {
        gestionPedidos = new GestionPedidos();
    }

    @Test
    public void testCrearPedido() {
        Pedido pedido = gestionPedidos.crearPedido("Laptop", 2);
        Assertions.assertNotNull(pedido);
        Assertions.assertEquals(1, pedido.getId());
        Assertions.assertEquals("Laptop", pedido.getProducto());
        Assertions.assertEquals(2, pedido.getCantidad());
        Assertions.assertEquals("Pendiente", pedido.getEstado());
    }

    @Test
    public void testActualizarEstadoPedido() {
        Pedido pedido = gestionPedidos.crearPedido("Laptop", 2);
        gestionPedidos.actualizarEstado(pedido.getId(), "Pagado");
        Assertions.assertEquals("Pagado", gestionPedidos.consultarPedido(pedido.getId()).getEstado());
    }

    @Test
    public void testEliminarPedido() {
        Pedido pedido = gestionPedidos.crearPedido("Laptop", 2);
        boolean eliminado = gestionPedidos.eliminarPedido(pedido.getId());
        Assertions.assertTrue(eliminado);
        Assertions.assertNull(gestionPedidos.consultarPedido(pedido.getId()));
    }

    @Test
    public void testConsultarPedidoExistente() {
        Pedido pedido = gestionPedidos.crearPedido("Tablet", 1);
        Pedido resultado = gestionPedidos.consultarPedido(pedido.getId());
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(pedido.getId(), resultado.getId());
    }

    @Test
    public void testConsultarPedidoInexistente() {
        Assertions.assertNull(gestionPedidos.consultarPedido(99));
    }

    @Test
    public void testListarPedidos() {
        gestionPedidos.crearPedido("Laptop", 2);
        gestionPedidos.crearPedido("Tablet", 1);
        List<Pedido> pedidos = gestionPedidos.listarPedidos();
        Assertions.assertEquals(2, pedidos.size());
    }

    @Test
    public void testCancelarPedidoPendiente() {
        Pedido pedido = gestionPedidos.crearPedido("Mouse", 3);
        boolean cancelado = gestionPedidos.cancelarPedido(pedido.getId());
        Assertions.assertTrue(cancelado);
        Assertions.assertNull(gestionPedidos.consultarPedido(pedido.getId()));
    }

    @Test
    public void testCancelarPedidoNoPendiente() {
        Pedido pedido = gestionPedidos.crearPedido("Teclado", 1);
        gestionPedidos.actualizarEstado(pedido.getId(), "Pagado");
        boolean cancelado = gestionPedidos.cancelarPedido(pedido.getId());
        Assertions.assertFalse(cancelado);
    }

    @Test
    public void testEliminarPedidoInexistente() {
        Assertions.assertFalse(gestionPedidos.eliminarPedido(99));
    }

    @Test
    public void testActualizarEstadoPedidoInexistente() {
        gestionPedidos.actualizarEstado(99, "Enviado");
        Assertions.assertNull(gestionPedidos.consultarPedido(99));
    }

    @Test
    public void testCrearVariosPedidos() {
        for (int i = 0; i < 10; i++) {
            gestionPedidos.crearPedido("Producto" + i, i + 1);
        }
        Assertions.assertEquals(10, gestionPedidos.listarPedidos().size());
    }

    @Test
    public void testEliminarTodosLosPedidos() {
        for (int i = 0; i < 5; i++) {
            gestionPedidos.crearPedido("Producto" + i, i + 1);
        }

        List<Pedido> pedidosAEliminar = new ArrayList<>(gestionPedidos.listarPedidos());
        for (Pedido p : pedidosAEliminar) {
            gestionPedidos.eliminarPedido(p.getId());
        }

        Assertions.assertEquals(0, gestionPedidos.listarPedidos().size());
    }



    @Test
    public void testEstadosDiferentesPedidos() {
        Pedido p1 = gestionPedidos.crearPedido("Impresora", 1);
        Pedido p2 = gestionPedidos.crearPedido("Monitor", 2);
        gestionPedidos.actualizarEstado(p1.getId(), "Enviado");
        gestionPedidos.actualizarEstado(p2.getId(), "Pagado");
        Assertions.assertEquals("Enviado", gestionPedidos.consultarPedido(p1.getId()).getEstado());
        Assertions.assertEquals("Pagado", gestionPedidos.consultarPedido(p2.getId()).getEstado());
    }

    @Test
    public void testReutilizarIdPedidos() {
        Pedido p1 = gestionPedidos.crearPedido("Producto1", 1);
        gestionPedidos.eliminarPedido(p1.getId());
        Pedido p2 = gestionPedidos.crearPedido("Producto2", 2);
        Assertions.assertNotEquals(p1.getId(), p2.getId());
    }

    @Test
    public void testCantidadPedidoNegativa() {
        GestionPedidos gestionPedidos = new GestionPedidos();

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> gestionPedidos.crearPedido("Laptop", -3),
                "Se esperaba una excepcion al ingresar una cantidad negativa"
        );

        Assertions.assertEquals("La cantidad debe ser mayor a 0", exception.getMessage());
    }

    @Test
    public void testActualizarEstadoVariosPedidos() {
        Pedido p1 = gestionPedidos.crearPedido("ProductoA", 1);
        Pedido p2 = gestionPedidos.crearPedido("ProductoB", 2);
        gestionPedidos.actualizarEstado(p1.getId(), "Pagado");
        gestionPedidos.actualizarEstado(p2.getId(), "Cancelado");
        Assertions.assertEquals("Pagado", gestionPedidos.consultarPedido(p1.getId()).getEstado());
        Assertions.assertEquals("Cancelado", gestionPedidos.consultarPedido(p2.getId()).getEstado());
    }
    @Test
    public void testCantidadPedidoCero() {
        GestionPedidos gestionPedidos = new GestionPedidos();

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> gestionPedidos.crearPedido("Tablet", 0),
                "Se esperaba una excepción al ingresar cantidad 0"
        );

        Assertions.assertEquals("La cantidad debe ser mayor a 0", exception.getMessage());
    }
    @Test
    public void testCantidadPedidoValida() {
        GestionPedidos gestionPedidos = new GestionPedidos();

        Pedido pedido = gestionPedidos.crearPedido("Mouse", 5);

        Assertions.assertNotNull(pedido, "El pedido no debe ser nulo");
        Assertions.assertEquals(5, pedido.getCantidad(), "La cantidad del pedido debe coincidir");
    }
    @Test
    public void testNombreProductoVacio() {
        GestionPedidos gestionPedidos = new GestionPedidos();

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> gestionPedidos.crearPedido("", 2),
                "Se esperaba una excepción al ingresar un nombre vacío"
        );

        Assertions.assertEquals("El nombre del producto no puede estar vacío", exception.getMessage());
    }

    @Test
    public void testNombreProductoNulo() {
        GestionPedidos gestionPedidos = new GestionPedidos();

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> gestionPedidos.crearPedido(null, 3),
                "Se esperaba una excepcion al ingresar un nombre nulo"
        );

        Assertions.assertEquals("El nombre del producto no puede ser nulo", exception.getMessage());
    }
}
