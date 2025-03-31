package modelos;

public class Producto {
    String nombre;
    int stock;

    public Producto(String nombre, int stock) {
        this.nombre = nombre;
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }
}