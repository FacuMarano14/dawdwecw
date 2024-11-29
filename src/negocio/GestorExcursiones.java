package negocio;

import java.util.List;
import java.util.function.Predicate;
import java.util.Optional;
import persistencia.Repository;
import modelo.Excursion;
import persistencia.ExcursionRepository;

public class GestorExcursiones {

    private Repository<Excursion> repository;

    public GestorExcursiones() {
        this.repository = new ExcursionRepository();
    }

    public boolean agregarExcursion(Excursion excursion) {
        repository.add(excursion);
        return true;
    }

    public Optional<Excursion> buscarPorId(int id) {
        return repository.findById(id);
    }

    public List<Excursion> filtrarExcursiones(Predicate<Excursion> criterio) {
        return repository.findBy(criterio);
    }

    public List<Excursion> consultarExcursionesPorPrecio(double precioMin) {
        return filtrarExcursiones(excursion -> excursion.calcularPrecioFinal() >= precioMin);
    }

}
