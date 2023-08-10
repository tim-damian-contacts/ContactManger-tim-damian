package util;

import java.util.Scanner;

public class Input {

    // this is our personal Scanner we are creating for ourselves.
    private final Scanner scanner;

    // Constructor
    public Input() {
        this.scanner = new Scanner(System.in);
    }

    // String method for the class Input
    public String getString() {
        // prompt would go here
        return this.scanner.nextLine();
    }
    //bonus
    public String getString(String prompt) {
        System.out.println(prompt);
        return this.getString();
    }

    // Boolean method for our class Input
    public boolean getBoolean() {
        // return true if user enters, "yes, "y", "Y", "yasss"
        // my code
//         String userInput = scanner.next().toLowerCase();
//        return userInput.equals("y") || userInput.equals("yes");
        // walkthrough
        boolean isYes = this.getString().trim().toLowerCase().startsWith("y");
        return isYes;
    }

    public boolean yesNo() {
        return this.getString().trim().toLowerCase().startsWith("y");
    }
    public boolean yesNo(String prompt) {
        System.out.println(prompt);
        return this.yesNo();
    }
    public int getInt() { // this will make sure we get an integer
        try {
            return Integer.parseInt(this.getString());
        } catch (NumberFormatException error) {
            System.out.println("You must enter a whole number?");
            // we must now capture the input again
            return this.getInt();
        }
    }

    public int getInt(String prompt) {
        System.out.println(prompt);
        return this.getInt();
    }
    public int getInt(int min, int max) {
        // we're weaving in the input from getInt(); above to hold the Int and then run our conditionals below.
        int userInput = getInt();
        if(userInput >= min && userInput <= max) {
            return userInput;
        } else {
            System.out.printf("The number must be between %d & %d. Please try again.%n", min, max);
            return getInt(min, max);
        }
    }
    public int getInt(String prompt, int min, int max) {
        System.out.println(prompt);
        return getInt(min, max);
    }

    public double getDouble() {
        try {
            return Double.parseDouble(this.getString());
        } catch (NumberFormatException error) {
            System.out.println("You must enter a whole number?");
            // we must now capture the input again
            return this.getDouble();
        }
    }
    public double getDouble(String prompt) {
        System.out.println(prompt);
        return this.getDouble();
    }

    public double getDouble(double min, double max) {
        double userDouble = getDouble();
        if(userDouble >= min && userDouble <= max) {
            return userDouble;
        } else {
            System.out.printf("The number must be between %f & %f. Please try again.%n", min, max);
            return getDouble(min, max);
        }
    }



}
