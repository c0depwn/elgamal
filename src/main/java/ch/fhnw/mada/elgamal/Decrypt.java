package ch.fhnw.mada.elgamal;

import ch.fhnw.mada.util.UnsafeWrite;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Decrypt {
    private final Path encryptedSource;
    private final Path output;
    private final PrivateKey key;

    public Decrypt(Path encryptedSource, Path output, PrivateKey key) {
        this.encryptedSource = encryptedSource;
        this.output = output;
        this.key = key;
    }

    public void decrypt() {
        // (...);(...);(...) -> [(...), (...), (...)]
        Function<String, List<String>> parseLine = (String line) -> List.of(line.split(";"));

        // [(y1,y2), (y1,y2), ...] -> [EncryptedChar, EncryptedChar, ...]
        Function<String, EncryptedChar> parseLineParts = (String part) -> {
            var temp = part.replaceAll("[()]", "").split(",");
            return new EncryptedChar(new BigInteger(temp[0], 10), new BigInteger(temp[1], 10));
        };

        // EncryptedChar -> (y1 ^ b)^-1 • y2 -> decrypted char
        BiFunction<PrivateKey, EncryptedChar, String> decrypt = (key, enc) -> {
            var decrypted = enc.y1.modPow(key.b, key.n)
                .modInverse(key.n)
                .multiply(enc.y2)
                .remainder(key.n);
            return String.valueOf((char) decrypted.intValue());
        };

        try (
            var reader = Files.newBufferedReader(this.encryptedSource);
            var writer = Files.newBufferedWriter(this.output);
        ) {
            // decrypt each character on the fly using streaming API :)
            reader.lines()
                .map(parseLine)
                .flatMap(Collection::stream)
                .map(parseLineParts)
                .map(encryptedChar -> decrypt.apply(this.key, encryptedChar))
                .forEach(s -> UnsafeWrite.write(writer, s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
