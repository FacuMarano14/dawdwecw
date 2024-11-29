package persistencia;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class GestorPersistencia<T> implements Serializer<T> {

    @Override
    public void guardarDatos(String filePath, List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<List<T>> cargarDatos(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return Optional.of((List<T>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

}
