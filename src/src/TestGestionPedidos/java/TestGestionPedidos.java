package TestGestionPedidos.java;

import Servicios.GestionInventario;
import Servicios.GestionPedidos;
import modelos.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class TestGestionPedidos {
    @Test
    public void testValidacionStockAntesDelPago() {
        GestionInventario inventario = new GestionInventario();
        inventario.agregarProducto("Laptop", 5);
        Assertions.assertTrue(inventario.verificarStock("Laptop", 3));
    }

    @Test
    public void testActualizacionInventarioDespuesDePago() {
        GestionInventario inventario = new GestionInventario();
        inventario.agregarProducto("Laptop", 5);
        inventario.reducirStock("Laptop", 3);
        Assertions.assertFalse(inventario.verificarStock("Laptop", 3));
    }

    @Test
    public void testCancelacionPedidoPorFalloPago() {
        GestionPedidos gestionPedidos = new GestionPedidos();
        Pedido pedido = gestionPedidos.crearPedido("Laptop", 2);
        gestionPedidos.actualizarEstado(pedido.getId(), "Cancelado");
        Assertions.assertEquals("Cancelado", pedido.getEstado());
    }
    @Test
    public void testNotificacionConfirmacionCliente() {
        GestionPedidos gestionPedidos = new GestionPedidos();
        Pedido pedido = gestionPedidos.crearPedido("Laptop", 1);
        gestionPedidos.actualizarEstado(pedido.getId(), "Pagado");
        String mensaje = "Pedido #" + pedido.getId() + " confirmado. Gracias por su compra.";
        System.out.println(mensaje);
        Assertions.assertEquals("Pagado", pedido.getEstado());
    }



    @Test
    public void testManejoConcurrenciaInventario() {
        GestionInventario inventario = new GestionInventario();
        inventario.agregarProducto("Laptop", 1);

        boolean cliente1Compra = inventario.verificarStock("Laptop", 1);
        if (cliente1Compra) {
            inventario.reducirStock("Laptop", 1);
            System.out.println("Cliente 1 compró con exito.");
        }
        boolean cliente2Compra = inventario.verificarStock("Laptop", 1);
        if (!cliente2Compra) {
            System.out.println("Cliente 2 no pudo comprar Stock agotado.");
        }

        Assertions.assertTrue(cliente1Compra);
        Assertions.assertFalse(cliente2Compra);
    }
}