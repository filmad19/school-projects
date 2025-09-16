package at.kaindorf.io;

import jakarta.xml.bind.JAXB;

import java.nio.file.Path;

public class IO_Access<T> {

    public T readData(Path path, Class type) {
        return (T) JAXB.unmarshal(path.toFile(), type);
    }
}
