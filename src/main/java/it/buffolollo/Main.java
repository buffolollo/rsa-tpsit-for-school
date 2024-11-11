package it.buffolollo;

import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.INFO);

        int bitLength = getNumberInput();

        RSA rsa = new RSA(bitLength);

        String message = getInput();

        String encryptedMessage = rsa.encrypt(message);

        System.out.println("Encrypted message: " + encryptedMessage);

        String decryptedMessage = rsa.decrypt(encryptedMessage);

        System.out.println("Decrypted message: " + decryptedMessage);
    }

    public static String getInput() {
        System.out.print("Enter a message to encrypt: ");
        String message = scanner.nextLine();
        scanner.close();
        return message;
    }

    public static int getNumberInput() {
        int n;

        try {
            System.out.print("Enter a number: ");
            String text = scanner.nextLine();
            n = Integer.parseInt(text);
            return n;
        } catch (Exception e) {
            logger.error("Invalid input, please enter a number");
            return getNumberInput();
        }
    }
}