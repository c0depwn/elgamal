package ch.fhnw.mada.cli;

import ch.fhnw.mada.cli.command.Command;
import ch.fhnw.mada.cli.command.Option;
import ch.fhnw.mada.elgamal.KeyGenerator;
import ch.fhnw.mada.elgamal.PublicKey;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

public class Encrypt extends Command {
    @Option(displayName = "PUBLIC_KEY", flagName = "p")
    private String publicKeyPath;
    @Option(displayName = "TEXT_FILE", flagName = "i")
    private String inputFilePath;
    @Option(displayName = "ENCRYPTED_FILE", flagName = "o")
    private String outputFilePath;

    @Override
    public void run(PrintStream output) {
        var keyPath = Path.of(this.publicKeyPath);
        var inputFilePath = Path.of(this.inputFilePath);
        var outputFilePath = Path.of(this.outputFilePath);

        output.println("💭... reading public key " + keyPath.toAbsolutePath());

        var publicKey = this.readPublicKey(keyPath);

        output.println("🔑... encrypting ");

        var encrypt = new ch.fhnw.mada.elgamal.Encrypt(
            publicKey,
            inputFilePath,
            outputFilePath
        );
        encrypt.encrypt();

        output.println("🔒... done encrypted file located at " + outputFilePath.normalize().toAbsolutePath());
    }

    private PublicKey readPublicKey(Path source) {
        try (
            var reader = Files.newBufferedReader(source)
        ) {
            var gb = new BigInteger(reader.readLine(), 10);
            return new PublicKey(
                KeyGenerator.getDefaultN(),
                KeyGenerator.getDefaultG(),
                gb
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void configure() {
        super.setIcon("🔐");
        super.setName("encrypt");
        super.setDescription("encrypt a ASCII text file");
    }
}
