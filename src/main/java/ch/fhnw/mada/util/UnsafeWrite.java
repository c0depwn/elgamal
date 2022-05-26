package ch.fhnw.mada.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public class UnsafeWrite {
    public static void write(Writer writer, String data) {
        try {
            writer.write(data);
        } catch (IOException e) {
            // because java exception handling sucks writer.write cannot be used inside the stream
            // so we make this helper method throw an unchecked exception to avoid
            // handling the exception inside the stream
            throw new UncheckedIOException(e);
        }
    }
}
