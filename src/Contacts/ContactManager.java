package Contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactManager {
    private static List<Contacts> contacts = new ArrayList<>();
    private static final String FILE_PATH = "./src/contacts.txt";

    public static void main(String[] args) {
        loadContacts();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            choice = displayMainMenu(scanner);

            switch (choice) {
                case 1:
                    viewContacts();
                    break;
                case 2:
                    addContact(scanner);
                    break;
                case 3:
                    searchContact(scanner);
                    break;
                case 4:
                    deleteContact(scanner);
                    break;
                case 5:
                    saveContacts();
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static int displayMainMenu(Scanner scanner) {
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.print("Enter an option (1, 2, 3, 4, or 5): ");
        return scanner.nextInt();
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
        System.out.println("===========================================================");
        System.out.println("|NAME                |PHONE NUMBER   |EMAIL               |");
        System.out.println("===========================================================");
        for (Contacts contact : contacts) {
            String formattedPhoneNumber = formatPhoneNumber(contact.getPhoneNumber()); // Format phone number
//            System.out.println(contact.getName() + " | " + formattedPhoneNumber + " | " + contact.getEmail());
            System.out.printf("|%-20s|%-15s|%-20s|\n",contact.getName(), formattedPhoneNumber, contact.getEmail());
            System.out.println("+--------------------+---------------+--------------------+");
        }
    }

    private static void addContact(Scanner scanner) {
        System.out.print("Enter the name: ");
        String name = scanner.next();

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
            String overrideChoice = scanner.next();
            if (overrideChoice.equalsIgnoreCase("yes")) {
                // Remove the existing contact
                contacts.remove(existingContact);
                System.out.println("Existing contact overridden.");
            } else {
                System.out.println("Contact not overridden.");
                return; // Exit the method without adding the contact
            }
        }

        System.out.print("Enter the phone number: ");
        String phoneNumber = scanner.next();
        System.out.print("Enter the email: ");
        String email = scanner.next();

        contacts.add(new Contacts(name, phoneNumber, email));
        System.out.println("Contact added successfully!");
    }


    private static void searchContact(Scanner scanner) {
        System.out.print("Enter the name to search: ");
        String searchName = scanner.next().toLowerCase();
        boolean found = false;
        System.out.println("Name | Phone number");
        System.out.println("--------------------------------------");
        for (Contacts contact : contacts) {
            if (contact.getName().toLowerCase().contains(searchName)) {
                System.out.println(contact);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Contact not found.");
        }
    }

    private static void deleteContact(Scanner scanner) {
        System.out.print("Enter the name to delete: ");
        String deleteName = scanner.next().toLowerCase();
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
    }
}
