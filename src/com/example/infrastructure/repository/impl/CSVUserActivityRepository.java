package com.example.infrastructure.repository.impl;

import com.example.domain.UserActivity;
import com.example.infrastructure.repository.UserActivityRepository;
import com.example.infrastructure.util.ApplicationUtil;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSVUserActivityRepository implements UserActivityRepository {
    private String filePath;
    private List<UserActivity> activities;


    public CSVUserActivityRepository() {
        activities = new ArrayList<>();
    }

    private List<UserActivity> loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                activities.add(ApplicationUtil.rawDataToUserActivity(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activities;
    }

    @Override
    public void mappingUserDataSource() {
        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();

        // Set the title for the file explorer
        fileChooser.setDialogTitle("Select User file (CSV)");

        // Show the open dialog and capture the user's selection
        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();

            // Get the path of the selected file
            String filePath = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + filePath);
            this.filePath = filePath;
            this.activities = loadFromFile();
            // Print the file path
        } else {
            System.out.println("No file selected.");
//            activities.clear();
        }
        System.out.println("Import user successfully !!!");
    }

    @Override
    public List<UserActivity> findAll() {
        return activities;
    }

    @Override
    public List<UserActivity> findByUserId(int userId) {
        List<UserActivity> result = new ArrayList<>();
        for (UserActivity activity : activities) {
            if (activity.getUserId() == userId) {
                result.add(activity);
            }
        }
        return result;
    }

    @Override
    public void save(UserActivity activity) {
        activities.add(activity);
        if (Objects.nonNull(filePath) && !filePath.isBlank()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(activity.getUserId() + "," + (activity.getActionType().ordinal() + 1) + "," + activity.getTimestamp() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
