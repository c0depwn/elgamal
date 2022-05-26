package ch.fhnw.mada.elgamal;

import java.math.BigInteger;

public class EncryptedChar {
    public BigInteger y1;
    public BigInteger y2;

    public EncryptedChar(BigInteger y1, BigInteger y2) {
        this.y1 = y1;
        this.y2 = y2;
    }
}