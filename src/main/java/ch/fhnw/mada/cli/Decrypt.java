package ch.fhnw.mada.cli;

import ch.fhnw.mada.cli.command.Command;
import ch.fhnw.mada.cli.command.Option;
import ch.fhnw.mada.elgamal.KeyGenerator;
import ch.fhnw.mada.elgamal.PrivateKey;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

public class Decrypt extends Command {
    @Option(displayName = "PRIVATE_KEY", flagName = "p")
    private String privateKeyPath;
    @Option(displayName = "ENCRYPTED_FILE", flagName = "i")
    private String encryptedFilePath;
    @Option(displayName = "DECRYPTED_FILE", flagName = "o")
    private String outputFilePath;

    @Override
    public void run(PrintStream output) {
        var keyPath = Path.of(this.privateKeyPath);
        var inputFilePath = Path.of(this.encryptedFilePath);
        var outputFilePath = Path.of(this.outputFilePath);

        output.println("💭... reading private key " + keyPath.toAbsolutePath());

        var privateKey = this.readPrivateKey(keyPath);

        output.println("🔑... decrypting ");

        var decrypt = new ch.fhnw.mada.elgamal.Decrypt(
            inputFilePath,
            outputFilePath,
            privateKey
        );
        decrypt.decrypt();

        output.println("🔓... done decrypted file located at " + outputFilePath.normalize().toAbsolutePath());
    }

    private PrivateKey readPrivateKey(Path source) {
        try (
            var reader = Files.newBufferedReader(source)
        ) {
            var b = new BigInteger(reader.readLine(), 10);
            return new PrivateKey(
                KeyGenerator.getDefaultN(),
                KeyGenerator.getDefaultG(),
                b
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void configure() {
        super.setIcon("🔓");
        super.setName("decrypt");
        super.setDescription("decrypt a file");
    }
}
