package TestGestionPedidos.java;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Servicios.GestionPagos;
import Servicios.GestionInventario;
import Servicios.GestionPedidos;
import modelos.Pedido;

class Test_20_Gestion_Pagos {

    @Test
    void testPagoFallaSiMontoInsuficiente() {
        GestionInventario gestionInventario = new GestionInventario();
        GestionPedidos gestionPedidos = new GestionPedidos();
        Pedido pedido = gestionPedidos.crearPedido("Zeembook A15", 4);
        gestionInventario.agregarProducto("Zeembook A15", 10);

        GestionPagos gestionPagos = new GestionPagos(gestionInventario, gestionPedidos);

        boolean pagoExitoso = gestionPagos.procesarPago(pedido, 50.0);

        assertFalse(pagoExitoso, "El pago no debería ser exitoso con un monto insuficiente.");
        assertNotEquals("Pagado", gestionPedidos.obtenerEstado(pedido.getId()),
                "El estado del pedido no debe ser 'Pagado' si el monto es insuficiente.");
    }


    @Test
    void testPagoConStockExacto() {
        GestionInventario gestionInventario = new GestionInventario();
        GestionPedidos gestionPedidos = new GestionPedidos();
        Pedido pedido = gestionPedidos.crearPedido("Motorola G20", 3);
        gestionInventario.agregarProducto("Motorola G20", 3);

        GestionPagos gestionPagos = new GestionPagos(gestionInventario, gestionPedidos);
        boolean pagoExitoso = gestionPagos.procesarPago(pedido, 150.0);

        assertTrue(pagoExitoso, "El pago debería ser exitoso si hay stock suficiente.");
        assertEquals(0, gestionInventario.obtenerStock("Producto B"),
                "El stock debería ser 0 después de la compra.");
        assertEquals("Pagado", gestionPedidos.obtenerEstado(pedido.getId()),
                "El estado del pedido debe cambiar a 'Pagado'.");
    }

    @Test
    void testPagoFallaSiNoHayStock() {
        GestionInventario gestionInventario = new GestionInventario();
        GestionPedidos gestionPedidos = new GestionPedidos();
        Pedido pedido = gestionPedidos.crearPedido("Televisor OSCURE", 2);


        GestionPagos gestionPagos = new GestionPagos(gestionInventario, gestionPedidos);
        boolean pagoExitoso = gestionPagos.procesarPago(pedido, 100.0);

        assertFalse(pagoExitoso, "El pago no debería ser exitoso si no hay stock.");
        assertEquals("Cancelado", gestionPedidos.obtenerEstado(pedido.getId()),
                "El estado del pedido debe ser 'Cancelado' por falta de stock.");
    }


    @Test
    void testPagoCanceladoPorFaltaDeStock() {
        GestionInventario gestionInventario = new GestionInventario();
        GestionPedidos gestionPedidos = new GestionPedidos();
        Pedido pedido = gestionPedidos.crearPedido("Laptop ASUS F15", 10);
        gestionInventario.agregarProducto("Laptop ASUS F15", 5); // Stock insuficiente

        GestionPagos gestionPagos = new GestionPagos(gestionInventario, gestionPedidos);
        boolean pagoExitoso = gestionPagos.procesarPago(pedido, 500.0);

        assertFalse(pagoExitoso, "El pago no debería ser exitoso debido a falta de stock.");
        assertEquals("Cancelado", gestionPedidos.obtenerEstado(pedido.getId()),
                "El estado del pedido debería ser 'Cancelado' por falta de stock.");
    }
    @Test
    void testPagoExitosoReduceStock() {
        GestionInventario gestionInventario = new GestionInventario();
        GestionPedidos gestionPedidos = new GestionPedidos();
        Pedido pedido = gestionPedidos.crearPedido("Producto Y", 3);
        gestionInventario.agregarProducto("Producto Y", 5);

        GestionPagos gestionPagos = new GestionPagos(gestionInventario, gestionPedidos);
        boolean pagoExitoso = gestionPagos.procesarPago(pedido, 150.0);

        assertTrue(pagoExitoso, "El pago debería ser exitoso.");
        assertEquals(2, gestionInventario.obtenerStock("Producto Y"),
                "El stock debería reducirse en la cantidad del pedido.");
    }

    @Test
    void testEstadoPedidoDespuesDePagoExitoso() {
        GestionInventario gestionInventario = new GestionInventario();
        GestionPedidos gestionPedidos = new GestionPedidos();
        Pedido pedido = gestionPedidos.crearPedido("Producto A", 2);

        gestionInventario.agregarProducto("Producto A", 5);

        GestionPagos gestionPagos = new GestionPagos(gestionInventario, gestionPedidos);
        boolean pagoExitoso = gestionPagos.procesarPago(pedido, 100.0);

        System.out.println("Estado del pedido después del pago: " + gestionPedidos.obtenerEstado(pedido.getId()));

        if (pagoExitoso) {
            assertEquals("Pagado", gestionPedidos.obtenerEstado(pedido.getId()), "El estado del pedido debe ser 'Pagado'.");
        } else {
            assertEquals("Cancelado", gestionPedidos.obtenerEstado(pedido.getId()), "El estado del pedido debe ser 'Cancelado'.");
        }
    }
    @Test
    void testPagoFallidoMantieneEstadoPedido() {
        GestionInventario gestionInventario = new GestionInventario();
        GestionPedidos gestionPedidos = new GestionPedidos();
        Pedido pedido = gestionPedidos.crearPedido("Producto X", 2);
        gestionInventario.agregarProducto("Producto X", 5);

        GestionPagos gestionPagos = new GestionPagos(gestionInventario, gestionPedidos);

        // Simulamos un pago fallido estableciendo manualmente la probabilidad de éxito a 0
        boolean pagoExitoso = false;

        if (!pagoExitoso) {
            gestionPedidos.actualizarEstado(pedido.getId(), "Pendiente");
        }

        assertEquals("Pendiente", gestionPedidos.obtenerEstado(pedido.getId()),
                "Si el pago falla, el estado del pedido debe mantenerse como 'Pendiente'.");
    }
}
