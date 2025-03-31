package Servicios;

import modelos.Producto;

import java.util.ArrayList;
import java.util.List;

public class GestionInventario {
    private List<Producto> productos = new ArrayList<>();

    public void agregarProducto(String nombre, int stock) {
        productos.add(new Producto(nombre, stock));
    }

    public boolean verificarStock(String nombre, int cantidad) {
        for (Producto p : productos) {
            if (p.getNombre().equals(nombre) && p.getStock() >= cantidad) {
                return true;
            }
        }
        return false;
    }

    public boolean reducirStock(String nombre, int cantidad) {
        for (Producto p : productos) {
            if (p.getNombre().equals(nombre) && p.getStock() >= cantidad) {
                p.setStock(p.getStock() - cantidad);
                return true; // Stock reducido con éxito
            }
        }
        return false;
    }

    public boolean actualizarStock(String nombre, int nuevoStock) {
        for (Producto p : productos) {
            if (p.getNombre().equals(nombre)) {
                p.setStock(nuevoStock);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarProducto(String nombre) {
        return productos.removeIf(p -> p.getNombre().equals(nombre));
    }


    public List<Producto> obtenerInventario() {
        return productos;
    }

    public int obtenerStock(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equals(nombre)) {
                return p.getStock();
            }
        }
        return 0;
    }
}
