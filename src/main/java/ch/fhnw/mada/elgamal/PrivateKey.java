package ch.fhnw.mada.elgamal;

import java.math.BigInteger;

public class PrivateKey {
    public BigInteger n;
    public BigInteger g;
    public BigInteger b;

    public PrivateKey(BigInteger n, BigInteger g, BigInteger b) {
        this.n = n;
        this.g = g;
        this.b = b;
    }
}
