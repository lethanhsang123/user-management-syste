package com.test.infrastructure.util;

import com.test.domain.ActionType;
import com.test.domain.UserActivity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class ApplicationUtil {

    public static final BigDecimal ONE_VALUE = new BigDecimal(1);

    public static void getApplicationMenu() {
        System.out.println();
        System.out.println("*************************************");
        System.out.println("        USER MANAGEMENT SYSTEM       ");
        System.out.println("*************************************");
        System.out.println("1. Import user history with file.");
        System.out.println("2. Search user history.");
        System.out.println("3. Search user total points.");
        System.out.println("4. Show leader board with users.");
        System.out.println("5. Export leader board users.");
        System.out.println("6. Add user into system.");
        System.out.println("0. Exit");
        System.out.println("*************************************");
    }

    public static String getInputUserDataMessage() {
        return "Input User: ";
    }
    public static String getInputUserIdMessage() {
        return "Input User ID: ";
    }

    public static String getInputFromDateMessage() {
        return "Input FromDate (timestamp): ";
    }

    public static String getInputToDateMessage() {
        return "Input ToDate (timestamp): ";
    }

    public static void getApplicationFinish() {
        System.out.println("Exiting the com.test.application. Goodbye!");
    }
    public static void getApplicationDoesNotSupport() {
        System.out.println("Application does not support this function !!!");
    }


    public static BigInteger promptForBigInteger(Scanner scanner, String message) {
        System.out.println(message);
        while (!scanner.hasNextBigInteger()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Clear invalid input
        }
        return scanner.nextBigInteger();
    }

    public static Long promptForLong(Scanner scanner, String message) {
        System.out.println(message);
        while (!scanner.hasNextLong()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Clear invalid input
        }
        return scanner.nextLong();
    }

    public static String promptForString(Scanner scanner, String message) {
        System.out.println(message);
        while (!scanner.hasNextLine()) {
            System.out.println("Invalid input. Please enter a valid message.");
            scanner.next(); // Clear invalid input
        }
        return scanner.nextLine();
    }

    public static UserActivity rawDataToUserActivity(String[] values) {
        int userId = Integer.parseInt(values[0]);
        ActionType actionType = ActionType.values()[Integer.parseInt(values[1]) - 1];
        long timestamp = Long.parseLong(values[2]);
        return new UserActivity(userId, actionType, timestamp);
    }

}
