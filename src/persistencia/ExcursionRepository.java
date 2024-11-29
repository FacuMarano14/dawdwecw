package persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import modelo.Excursion;

public class ExcursionRepository implements Repository<Excursion> {

    private List<Excursion> excursiones;
    private GestorPersistencia<Excursion> persistencia;
    private String filePath = "excursiones.dat";
    private int ultimoId = 0;

    public ExcursionRepository() {
        this.persistencia = new GestorPersistencia<>();
        this.excursiones = persistencia.cargarDatos(filePath).orElse(new ArrayList<>());
        this.ultimoId = excursiones.size();
    }

    @Override
    public void add(Excursion entity) {
        excursiones.add(entity);
        persistencia.guardarDatos(filePath, excursiones);
    }

    @Override
    public void remove(int id) {
        excursiones.removeIf(excursion -> excursion.getId() == id);
        persistencia.guardarDatos(filePath, excursiones);
    }

    @Override
    public Optional<Excursion> findById(int id) {
        return excursiones.stream().filter(excursion -> excursion.getId() == id).findFirst();
    }

    @Override
    public List<Excursion> findby(Predicate<Excursion> criterio) {
        return excursiones.stream().filter(criterio).toList();
    }

    @Override
    public List<Excursion> getAll() {
        return new ArrayList<>(excursiones);
    }

}
