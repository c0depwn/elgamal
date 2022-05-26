package ch.fhnw.mada.math;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Helpers {
    /**
     * Calculate the magnitude of a group in the form of Z* of n.
     * This will only work if n is prime due to efficiency.
     * @param n
     * @return
     */
    public static BigInteger phiOfN(BigInteger n) {
        var isPrime = n.isProbablePrime(10);
        if (!isPrime) {
            throw new RuntimeException("n is not prime");
        }
        return n.subtract(BigInteger.ONE);
    }

    /**
     * Find a random value within {0, 1, ... phiOfN(n) - 1}
     * @param n
     * @param g
     * @return
     */
    public static BigInteger findRandom(BigInteger n, BigInteger g) {
        var max = Helpers.phiOfN(n).subtract(BigInteger.ONE);
        var candidate = BigInteger.ZERO;

        do {
            candidate = new BigInteger(max.bitLength(), new SecureRandom());
        } while (candidate.compareTo(max) >= 0);

        return candidate;
    }
}
