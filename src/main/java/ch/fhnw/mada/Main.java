package ch.fhnw.mada;

import ch.fhnw.mada.cli.Decrypt;
import ch.fhnw.mada.cli.Encrypt;
import ch.fhnw.mada.cli.EntryPoint;
import ch.fhnw.mada.cli.GenerateKeyPair;

public class Main {
    public static void main(String[] args) {
        EntryPoint entryPoint = new EntryPoint(System.out);
        entryPoint.register(new GenerateKeyPair(), new Encrypt(), new Decrypt());
        entryPoint.run(args.length > 0 ? args[0] : "", args);
    }
}
