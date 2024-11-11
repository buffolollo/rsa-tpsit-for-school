package it.buffolollo;

import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

public class Main {
    public static void main(String[] args) {
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.INFO);

        RSA rsa = new RSA();

        String message = getInput();

        String encryptedMessage = rsa.encrypt(message);

        System.out.println("Encrypted message: " + encryptedMessage);

        String decryptedMessage = rsa.decrypt(encryptedMessage);

        System.out.println("Decrypted message: " + decryptedMessage);
    }

    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a message to encrypt: ");
        String message = scanner.nextLine();
        scanner.close();
        return message;
    }
}