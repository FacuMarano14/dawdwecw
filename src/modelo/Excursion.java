package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import negocio.GestorReservas;

public abstract class Excursion implements Serializable {

    private static int nextId = 1;
    private final int id;
    private String titulo;
    private double precioBase;
    private int cupoMaximo;
    private transient GestorReservas gestorReservas;
    private List<String> actividades;

    public Excursion(String titulo, double precioBase, int cupoMaximo) {
        if (titulo == null || titulo.length() < 3) {
            throw new IllegalArgumentException("El titulo debe tener 3 caracteres minimo");
        }
        if (cupoMaximo <= 0) {
            throw new IllegalArgumentException("El cupo maximo debe ser mayor que 0");
        }

        this.id = nextId++;
        this.titulo = titulo;
        this.precioBase = precioBase;
        this.cupoMaximo = cupoMaximo;
        this.gestorReservas = new GestorReservas(cupoMaximo);
        this.actividades = new ArrayList<>();

    }

    public boolean realizarReserva(String cliente) {
        Reserva reserva = new Reserva(this.id, cliente, calcularPrecioFinal());
        return gestorReservas.realizarReserva(reserva);
    }

    public void agregarActividad(String actividad) {
        if (actividad == null || actividad.isEmpty()) {
            throw new IllegalArgumentException("La actividad no puede estar vacia");
        }
        actividades.add(actividad);
    }
    
    public String mostrarResumen() {
        return String.format(
            "Excursión: %s (ID: %d)\nPrecio Base: %.2f\nCupos Disponibles: %d\nActividades: %s",
            titulo, id, precioBase, gestorReservas.getCuposDisponibles(), actividades
        );
    }

    public Optional<List<Reserva>> traerReservas() {
        return Optional.of(gestorReservas.getReservas());
    }

    protected abstract double calcularPrecioFinal();

    protected String generarFilePath() {
        return "reservas excursion_" + id + ".dat";
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

}
