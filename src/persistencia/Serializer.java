package persistencia;

import java.util.List;
import java.util.Optional;

public interface Serializer<T> {

    void guardarDatos(String filePath, List<T> data);

    Optional<List<T>> cargarDatos(String filePath);

}
