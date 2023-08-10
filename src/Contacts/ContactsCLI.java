package Contacts;

import util.Input;

public class ContactsCLI {
    public static void main(String[] args) {
        Input input = new Input();

        System.out.println("Welcome to the Contacts Manager CLI!");
        System.out.print("Enter a contact's name: ");
        String name = input.getString();

        System.out.print("Enter " + name + "'s phone number: ");
        String phoneNumber = input.getString();

        System.out.print("Enter " + name + "'s email: ");
        String email = input.getString();

        Contacts newContact = new Contacts(name, phoneNumber, email);

        System.out.println("\nContact created:\n" + newContact);
    }
}
