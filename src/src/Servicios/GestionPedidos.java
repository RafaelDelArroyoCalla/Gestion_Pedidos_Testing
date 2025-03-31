package Servicios;

import modelos.Pedido;

import java.util.ArrayList;
import java.util.List;

public class GestionPedidos {
    private List<Pedido> pedidos = new ArrayList<>();
    private int contadorId = 1;

    public Pedido crearPedido(String producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo");
        }
        if (producto.isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        Pedido pedido = new Pedido(contadorId++, producto, cantidad);
        pedidos.add(pedido);
        return pedido;
    }


    public void actualizarEstado(int id, String estado) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                p.setEstado(estado);
                break;
            }
        }
    }

    public boolean eliminarPedido(int id) {
        return pedidos.removeIf(p -> p.getId() == id);
    }

    public Pedido consultarPedido(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public List<Pedido> listarPedidos() {
        return pedidos;
    }


    public boolean cancelarPedido(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id && p.getEstado().equals("Pendiente")) {
                pedidos.remove(p);
                return true; // Pedido cancelado
            }
        }
        return false;
    }
}
