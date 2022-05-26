package ch.fhnw.mada.elgamal;

import ch.fhnw.mada.math.Helpers;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Encrypt {
    private PublicKey publicKey;
    private Path input;
    private Path output;

    public Encrypt(PublicKey publicKey, Path input, Path output) {
        this.publicKey = publicKey;
        this.input = input;
        this.output = output;
    }

    public void encrypt() {
        // choose random a of {0, 1, 2, ... ord(G) - 1}
        var a = Helpers.findRandom(this.publicKey.n, this.publicKey.g);

        BiFunction<Integer, BigInteger, EncryptedChar> encrypt = (c, randomA) -> {
            var x = BigInteger.valueOf(c);
            // y1 = g^a mod n
            var y1 = this.publicKey.g.modPow(randomA, this.publicKey.n);
            // y2 = (g^b)^a • x
            var y2 = this.publicKey.gb.modPow(randomA, this.publicKey.n).multiply(x).remainder(this.publicKey.n);
            return new EncryptedChar(y1, y2);
        };

        Function<EncryptedChar, String> format = enc -> "(" + enc.y1.toString(10) + "," + enc.y2.toString(10) + ")";

        try (
            var reader = Files.newBufferedReader(this.input);
            var writer = Files.newBufferedWriter(this.output);
        ) {
            // encrypt each character on the fly using streaming API :)
            var encrypted = reader.lines()
                .flatMapToInt(String::chars)
                .mapToObj(c -> encrypt.apply(c, a))
                .map(format)
                .collect(Collectors.joining(";"));
            writer.write(encrypted);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
