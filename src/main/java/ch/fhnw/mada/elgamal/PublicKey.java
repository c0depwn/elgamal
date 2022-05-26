package ch.fhnw.mada.elgamal;

import java.math.BigInteger;

public class PublicKey {
    public BigInteger n;
    public BigInteger g;
    public BigInteger gb;

    public PublicKey(BigInteger n, BigInteger g, BigInteger gb) {
        this.n = n;
        this.g = g;
        this.gb = gb;
    }
}
