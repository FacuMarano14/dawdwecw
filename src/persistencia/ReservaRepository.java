package persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import modelo.Reserva;

public class ReservaRepository implements Repository<Reserva> {

    private List<Reserva> reservas;
    private GestorPersistencia<Reserva> persistencia;
    private String filePath = "reservas.dat";

    public ReservaRepository(int cupoMaximo) {
        this.persistencia = new GestorPersistencia<>();
        this.reservas = persistencia.cargarDatos(filePath).orElse(new ArrayList<>());
    }

    @Override
    public void add(Reserva entity) {
        reservas.add(entity);
        persistencia.guardarDatos(filePath, reservas);
    }

    @Override
    public void remove(int id) {
        reservas.removeIf(reserva -> reserva.getIdExcursion() == id);
        persistencia.guardarDatos(filePath, reservas);
    }

    @Override
    public Optional<Reserva> findById(int id) {
        return reservas.stream().filter(reserva -> reserva.getIdExcursion() == id).findFirst();
    }

    @Override
    public List<Reserva> findby(Predicate<Reserva> criterio) {
        return reservas.stream().filter(criterio).toList();
    }

    @Override
    public List<Reserva> getAll() {
        return new ArrayList<>(reservas);
    }

}
