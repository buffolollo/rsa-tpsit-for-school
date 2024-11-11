package it.buffolollo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigInteger;
import java.security.SecureRandom;

//public key = (n, e) for encryption
//private key = (n, d) for decryption

public class RSA {
    private static final Logger logger = LogManager.getLogger(RSA.class);

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phiN;
    private BigInteger e;
    private BigInteger d;

    public RSA() {
        p = generatePrimeNumber(64);
        logger.info("Generated prime number p: " + p);

        q = generatePrimeNumber(64);
        logger.info("Generated prime number q: " + q);

        n = p.multiply(q);
        logger.info("Generated n: " + n);

        phiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        logger.info("Generated phi(n): " + phiN);

        // esponente che deve essere coprime, (1 < e < phi(n))
        e = generateCoprimePublicKey();
        logger.info("Generated public comprime key e: " + e);

        d = generatePrivateKey();
        logger.info("Generated private key d: " + d);

        logger.info("Public key: (" + n + ", " + e + ")");
        logger.info("Private key: (" + n + ", " + d + ")");

    }

    private BigInteger generatePrimeNumber(int bitLength) {
        SecureRandom random = new SecureRandom();
        return BigInteger.probablePrime(bitLength, random);
    }

    private BigInteger generateCoprimePublicKey() {
        SecureRandom random = new SecureRandom();
        BigInteger e;
        do {
            e = new BigInteger(phiN.bitLength(), random);
        } while ((e.compareTo(BigInteger.ONE) <= 0) || (e.compareTo(phiN) >= 0) || !e.gcd(phiN).equals(BigInteger.ONE));
        return e;
    }

    private BigInteger generatePrivateKey() {
        BigInteger d = e.modInverse(phiN);
        return d;
    }

    public String encrypt(String message) {
        StringBuilder ciphertextBuilder = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            BigInteger m = BigInteger.valueOf((int) c);
            BigInteger encrypted = m.modPow(e, n);
            ciphertextBuilder.append(encrypted).append(" ");
        }

        return ciphertextBuilder.toString();
    }

    public String decrypt(String ciphertext) {
        StringBuilder plaintextBuilder = new StringBuilder();
        String[] encryptedChars = ciphertext.split(" ");

        for (String encryptedChar : encryptedChars) {
            BigInteger encrypted = new BigInteger(encryptedChar);
            BigInteger decrypted = encrypted.modPow(d, n);
            char plaintextChar = (char) decrypted.intValue();
            plaintextBuilder.append(plaintextChar);
        }

        return plaintextBuilder.toString();
    }
}
