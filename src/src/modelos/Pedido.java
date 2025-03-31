package modelos;

public class Pedido {
    int id;
    String producto;
    int cantidad;
    String estado; // "Pendiente", "Pagado", "Cancelado"

    public Pedido(int id, String producto, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.estado = "Pendiente";
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public String getProducto() {
        return producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
