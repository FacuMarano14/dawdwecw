package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reserva implements Serializable {

    private int idExcursion;
    private String cliente;
    private String fechaReserva;
    private double precio;
    private boolean pagoConfirmado;

    public Reserva(int idExcursion, String cliente, double precio) {

        if (cliente == null || cliente.isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacio");
        }

        this.idExcursion = idExcursion;
        this.cliente = cliente;
        this.fechaReserva = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.precio = precio;
        this.pagoConfirmado = false;
    }

    public boolean confirmarPago() {
        this.pagoConfirmado = true;
        return true;
    }

    public String detalleReserva() {
        return String.format("Reserva para excursion ID: %d\nCliente: %d\nFecha: %s\nPrecio: %f\nPago Confirmado: %s",
                idExcursion, cliente, fechaReserva, precio, pagoConfirmado ? "Si" : "No");
    }

    public int getIdExcursion() {
        return idExcursion;
    }

    public String getCliente() {
        return cliente;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isPagoConfirmado() {
        return pagoConfirmado;
    }

}
