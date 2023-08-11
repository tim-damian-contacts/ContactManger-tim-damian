package Contacts;

import util.Input;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ContactManager {
    private static List<Contacts> contacts = new ArrayList<>();
    private static final String FILE_PATH = "./src/contacts.txt";

    public static void main(String[] args) {
        loadContacts();

//        Scanner scanner = new Scanner(System.in);
        Input scanner = new Input();
        int choice;

        do {
            choice = displayMainMenu(scanner);

            switch (choice) {
                case 1:
                    viewContacts();
                    break;
                case 2:
                    addContact(scanner);
                    saveContacts();
                    break;
                case 3:
                    searchContact(scanner);
                    break;
                case 4:
                    deleteContact(scanner);
                    saveContacts();
                    break;
                case 5:
                    saveContacts();
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        } while (choice != 5);

//        scanner.close();
    }

    private static int displayMainMenu(Input scanner) {
        System.out.println("+===================+\n|     MAIN MENU     |\n+===================+");
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.print("Enter an option (1, 2, 3, 4, or 5): ");

        int choice = scanner.getInt();

        if (choice == 1) {
            simulateTyping("Loading... View contacts...\n", 100, 150); // Simulate typing for "View contacts" option
        }

        return choice;
    }

    public static void simulateTyping(String text, int minDelay, int maxDelay) {
        Random random = new Random();
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            int delay = random.nextInt(maxDelay - minDelay + 1) + minDelay; // Generate random delay
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String name = parts[0].trim();
                String phoneNumber = parts[1].trim();
                String email = parts[2].trim();
                contacts.add(new Contacts(name, phoneNumber, email));
            }
        } catch (IOException e) {
            System.out.println("Error reading contacts file: " + e.getMessage());
        }
    }

    private static void saveContacts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Contacts contact : contacts) {
                String formattedPhoneNumber = formatPhoneNumber(contact.getPhoneNumber()); // Format phone number
                writer.write(contact.getName() + " | " + formattedPhoneNumber + " | " + contact.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing contacts file: " + e.getMessage());
        }
    }

    private static String formatPhoneNumber(String phoneNumber) {
        // Assuming phoneNumber is a string of digits without dashes
        phoneNumber = phoneNumber.replace("-", "");
        return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
    }

    private static void viewContacts() {
        System.out.println("=================================================");
        System.out.println("|NAME           |PHONE NUMBER   |EMAIL          |");
        System.out.println("=================================================");
        for (Contacts contact : contacts) {
            String formattedPhoneNumber = formatPhoneNumber(contact.getPhoneNumber()); // Format phone number
            System.out.printf("|%-15s|%-15s|%-15s|\n", contact.getName(), formattedPhoneNumber, contact.getEmail());
            System.out.println("+---------------+---------------+---------------+");
        }
        System.out.println();
    }

    private static void addContact(Input scanner) {
        System.out.print("Enter the name: ");
        String name = scanner.getString();

        // Check if the name already exists
        Contacts existingContact = null;
        for (Contacts contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                existingContact = contact;
                break;
            }
        }

        if (existingContact != null) {
            System.out.println("A contact with the same name already exists.");
            System.out.print("Do you want to override the existing contact? (yes/no): ");
            boolean overrideChoice = scanner.yesNo();
            if (overrideChoice) {
                // Remove the existing contact
                contacts.remove(existingContact);
                System.out.println("Existing contact overridden.");
            } else {
                System.out.println("Contact not overridden.");
                return; // Exit the method without adding the contact
            }
        }

        System.out.print("Enter the phone number: ");
        String phoneNumber = scanner.getString();
        System.out.print("Enter the email: ");
        String email = scanner.getString();

        contacts.add(new Contacts(name, phoneNumber, email));
        System.out.println("Contact added successfully!");
        saveContacts();
    }

    private static void searchContact(Input scanner) {
        System.out.print("Enter the name to search: ");
        String searchName = scanner.getString().toLowerCase();
        boolean found = false;
        System.out.println("=================================================");
        System.out.println("|NAME           |PHONE NUMBER   |EMAIL          |");
        System.out.println("=================================================");
        for (Contacts contact : contacts) {
            if (contact.getName().toLowerCase().contains(searchName)) {
                String formattedPhoneNumber = formatPhoneNumber(contact.getPhoneNumber());
                System.out.printf("|%-15s|%-15s|%-15s|\n", contact.getName(), formattedPhoneNumber, contact.getEmail());
                System.out.println("+---------------+---------------+---------------+");
                found = true;
            }
            System.out.println();
        }
        if (!found) {
            System.out.println("Contact not found.");
        }
    }

    private static void deleteContact(Input scanner) {
        System.out.print("Enter the name to delete: ");
        String deleteName = scanner.getString().toLowerCase();
        Contacts contactToRemove = null;
        for (Contacts contact : contacts) {
            if (contact.getName().toLowerCase().contains(deleteName)) {
                contactToRemove = contact;
                break;
            }
        }
        if (contactToRemove != null) {
            contacts.remove(contactToRemove);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Contact not found.");
        }
        saveContacts();
    }
}
