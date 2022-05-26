package ch.fhnw.mada.elgamal;

public class KeyPair {
    public PublicKey publicKey;
    public PrivateKey privateKey;

    public KeyPair(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
}
