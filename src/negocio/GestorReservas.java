package negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import persistencia.Repository;
import modelo.Reserva;
import persistencia.ReservaRepository;

public class GestorReservas {

    private Repository<Reserva> repository;
    private final int cupoMaximo;

    public GestorReservas(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
        this.repository = new ReservaRepository(cupoMaximo);
    }

    public boolean realizarReserva(Reserva reserva) {
        if (getCuposDisponibles() <= 0) {
            System.out.println("No hay cupos disponibles");
            return false;
        }
        repository.add(reserva);
        return true;
    }

    public List<Reserva> getReservas() {
        return repository.getAll();
    }

    public double calcularIngresos(Predicate<Reserva> filtro) {
        return repository.findBy(filtro).stream()
                .mapToDouble(Reserva::getPrecio)
                .sum();
    }

    public double calcularIngresosTotales() {
        return calcularIngresos(Reserva::isPagoConfirmado);
    }

    public int getCuposDisponibles() {
        return cupoMaximo - getReservas().size();
    }

}
