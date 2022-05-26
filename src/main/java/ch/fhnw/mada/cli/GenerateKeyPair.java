package ch.fhnw.mada.cli;

import ch.fhnw.mada.cli.command.Command;
import ch.fhnw.mada.cli.command.Option;
import ch.fhnw.mada.elgamal.KeyGenerator;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class GenerateKeyPair extends Command {
    @Option(displayName = "OUTPUT_DIR", flagName = "o")
    private String outputDir;

    @Option(displayName = "PREFIX", flagName = "p", required = false)
    private String prefix;

    @Override
    public void run(PrintStream output) {
        Path targetFolder = FileSystems.getDefault()
            .getPath(this.outputDir)
            .normalize()
            .toAbsolutePath();

        Path privateKey = Path.of(targetFolder.toString(), prefix + "sk.txt");
        Path publicKey = Path.of(targetFolder.toString(), prefix + "pk.txt");

        output.println("✨\t...generating key pair");

        var keyPair = new KeyGenerator().generateKeyPair();

        output.println("📝\t...writing keys to " + targetFolder);

        try (
            var pWriter = Files.newBufferedWriter(publicKey);
            var sWriter = Files.newBufferedWriter(privateKey);
        ) {
            pWriter.write(keyPair.publicKey.gb.toString(10));
            sWriter.write(keyPair.privateKey.b.toString(10));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        output.println("✔︎\t...done");
    }

    @Override
    public void configure() {
        super.setIcon("🔨");
        super.setName("generate");
        super.setDescription("generate a new key pair and store it at the specified location");
    }
}
