package com.test.application.console;

import com.test.application.CalculateUserPoints;
import com.test.application.CheckUserHistory;
import com.test.application.GenerateLeaderboard;
import com.test.domain.UserActivity;
import com.test.infrastructure.repository.UserActivityRepository;
import com.test.infrastructure.repository.impl.CSVUserActivityRepository;
import com.test.infrastructure.util.ApplicationUtil;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApplication {

    private static final UserActivityRepository repository = new CSVUserActivityRepository();
    private static final CheckUserHistory checkUserHistory = new CheckUserHistory(repository);
    private static final CalculateUserPoints calculateUserPoints = new CalculateUserPoints(repository);
    private static final GenerateLeaderboard generateLeaderboard = new GenerateLeaderboard(repository);

    /**
     * Hàm thực hiện quản lý các chức năng trong hệ thống
     */
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        BigInteger userInput;
        do {
            ApplicationUtil.getApplicationMenu();
            userInput = scanner.nextBigInteger();
            scanner.nextLine();
            switch (userInput.intValue()) {
                case 1: {   // Chức năng import user với file
                    this.importUser();
                    break;
                }
                case 2: {   // Chức năng tìm kiếm lịch sử user
                    this.checkUserHistory(scanner);
                    break;
                }
                case 3: {
                    this.calculateTotalPoints(scanner);
                    break;
                }
                case 4: {
                    this.generateLeaderBoard(scanner);
                    break;
                }
                case 5: {
                    this.exportLeaderBoard(scanner);
                    break;
                }
                case 6: {
                    this.addUserData(scanner);
                    break;
                }
                default: {
                    ApplicationUtil.getApplicationDoesNotSupport();
                    break;
                }
            }
        } while (!userInput.equals(BigInteger.ZERO));
        ApplicationUtil.getApplicationFinish();
        scanner.close();
    }

    public void importUser() {
        repository.mappingUserDataSource();
    }

    public void checkUserHistory(Scanner scanner) {

        BigInteger userId = ApplicationUtil.promptForBigInteger(scanner, ApplicationUtil.getInputUserIdMessage());

        Long fromDate = ApplicationUtil.promptForLong(scanner, ApplicationUtil.getInputFromDateMessage());

        Long toDate = ApplicationUtil.promptForLong(scanner, ApplicationUtil.getInputToDateMessage());

        List<UserActivity> history = checkUserHistory.execute(userId.intValue(), fromDate, toDate);
        history.forEach(System.out::println);
    }

    private void calculateTotalPoints(Scanner scanner) {

        BigInteger userId = ApplicationUtil.promptForBigInteger(scanner, ApplicationUtil.getInputUserIdMessage());

        Long fromDate = ApplicationUtil.promptForLong(scanner, ApplicationUtil.getInputFromDateMessage());

        Long toDate = ApplicationUtil.promptForLong(scanner, ApplicationUtil.getInputToDateMessage());

        System.out.println("Total points: " + calculateUserPoints.execute(userId.intValue(), fromDate, toDate));
    }

    private void generateLeaderBoard(Scanner scanner) {
        Long fromDate = ApplicationUtil.promptForLong(scanner, ApplicationUtil.getInputFromDateMessage());

        Long toDate = ApplicationUtil.promptForLong(scanner, ApplicationUtil.getInputToDateMessage());

        List<Map.Entry<Integer, Integer>> leaderboard = generateLeaderboard.execute(fromDate, toDate);
        leaderboard.forEach(entry -> System.out.println("User: " + entry.getKey() + ", Points: " + entry.getValue()));
    }

    private void exportLeaderBoard(Scanner scanner) {
        Long fromDate = ApplicationUtil.promptForLong(scanner, ApplicationUtil.getInputFromDateMessage());

        Long toDate = ApplicationUtil.promptForLong(scanner, ApplicationUtil.getInputToDateMessage());

        List<Map.Entry<Integer, Integer>> leaderboard = generateLeaderboard.execute(fromDate, toDate);
        // Create a JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save"); // Set the dialog title

        // Show save dialog and check if the user selects a file
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String csvFilePath = fileToSave.getAbsolutePath();

            // Check if the file has a .csv extension, if not, append it
            if (!csvFilePath.endsWith(".csv")) {
                csvFilePath += ".csv";
            }

            // Write the data to the selected CSV file
            try {
                writeMapToCSV(leaderboard, csvFilePath);
                System.out.println("CSV file created successfully: " + csvFilePath);
            } catch (IOException e) {
                System.err.println("Error writing to CSV file: " + e.getMessage());
            }
        } else {
            System.out.println("Save command cancelled by user.");
        }
    }

    private static void writeMapToCSV(List<Map.Entry<Integer, Integer>> dataMap, String csvFilePath) throws IOException {
        // Use BufferedWriter to write to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {

            dataMap.forEach(entry -> {
                String line = "User: " + entry.getKey() + ", Points: " + entry.getValue();
                try {
                    writer.write(line);
                    writer.newLine(); // Move to the next line
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void addUserData(Scanner scanner) {
        String user = ApplicationUtil.promptForString(scanner, ApplicationUtil.getInputUserDataMessage(), ApplicationUtil.USER_INPUT_REGEX);
        String[] values = user.split(",");
        UserActivity userActivity = ApplicationUtil.rawDataToUserActivity(values);
        repository.save(userActivity);
        List<Map.Entry<Integer, Integer>> leaderboard = generateLeaderboard.execute(null, null);
        leaderboard.forEach(entry -> System.out.println((entry.getKey().equals(userActivity.getUserId()) ? "(*)" : "")
                + "User: " + entry.getKey() + ", Points: " + entry.getValue()));
    }


}
