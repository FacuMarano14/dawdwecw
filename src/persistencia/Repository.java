package persistencia;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Repository<T> {

    void add(T entity);

    void remove(int id);

    Optional<T> findById(int id);

    List<T> findBy(Predicate<T> criterio);

    List<T> getAll();

}
