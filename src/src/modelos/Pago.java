package modelos;

public class Pago {
    int idPedido;
    double monto;
    boolean exitoso;

    public Pago(int idPedido, double monto, boolean exitoso) {
        this.idPedido = idPedido;
        this.monto = monto;
        this.exitoso = exitoso;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public double getMonto() {
        return monto;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

}
