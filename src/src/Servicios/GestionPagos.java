package Servicios;

import modelos.Pedido;

public class GestionPagos {
    private GestionInventario gestionInventario;
    private GestionPedidos gestionPedidos;

    public GestionPagos(GestionInventario gestionInventario, GestionPedidos gestionPedidos) {
        this.gestionInventario = gestionInventario;
        this.gestionPedidos = gestionPedidos;
    }

    public boolean procesarPago(Pedido pedido, double monto) {

        if (!gestionInventario.verificarStock(pedido.getProducto(), pedido.getCantidad())) {
            System.out.println("Pago rechazado: No hay suficiente stock para " + pedido.getProducto());
            gestionPedidos.actualizarEstado(pedido.getId(), "Cancelado");
            return false;
        }

        boolean exito = Math.random() > 0.2;

        if (exito) {
            System.out.println("Pago aprobado: Se ha cobrado " + monto + " por " + pedido.getProducto());
            gestionInventario.reducirStock(pedido.getProducto(), pedido.getCantidad());
            gestionPedidos.actualizarEstado(pedido.getId(), "Pagado");
            System.out.println("Stock actualizado y pedido marcado como pagado.");
        } else {
            System.out.println("Pago fallido: No se pudo procesar el pago de " + monto);
            gestionPedidos.actualizarEstado(pedido.getId(), "Cancelado");
        }

        return exito;
    }
}
