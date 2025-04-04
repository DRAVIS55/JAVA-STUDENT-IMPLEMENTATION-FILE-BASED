import java.io.*;
import java.util.ArrayList;

public class Lecturer extends Person {
    private File file;

    // Constructor: Initialize file object
    public Lecturer(String fileName) {
        this.file = new File(fileName);
    }

    // Register a new Lecturer
    public boolean registerLecturer(String lecturerID, String f_name, String s_name, String phone, String department, String email) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(lecturerID + "," + f_name + "," + s_name + "," + phone + "," + department + "," + email);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update Lecturer Details
    public boolean updateLecturer(String lecturerID, String newName, String newEmail) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(lecturerID)) {
                    details[1] = newName;
                    details[5] = newEmail;
                    line = String.join(",", details);
                    updated = true;
                }
                content.append(line).append("\n");
            }

            if (updated) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(content.toString());
                }
            }
            return updated;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Assign courses to a lecturer
    public boolean assignCourses(String lecturerIdNo, ArrayList<String> courseCodes) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean lecturerFound = false;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(lecturerIdNo)) {
                    lecturerFound = true;
                    break;
                }
            }

            if (lecturerFound) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                    for (String courseCode : courseCodes) {
                        writer.write(lecturerIdNo + " is assigned to course " + courseCode);
                        writer.newLine();
                    }
                }
                return true;
            } else {
                new ReusableClass().printMessage("Lecturer with ID " + lecturerIdNo + " does not exist.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search for a Lecturer by ID
    public ArrayList<String> searchLecturer(String lecturerID) {
        ArrayList<String> lecturerDetails = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details[0].equals(lecturerID)) {
                    lecturerDetails.add("Lecturer ID: " + details[0]);
                    lecturerDetails.add("First Name: " + details[1]);
                    lecturerDetails.add("Last Name: " + details[2]);
                    lecturerDetails.add("Phone: " + details[3]);
                    lecturerDetails.add("Department: " + details[4]);
                    lecturerDetails.add("Email: " + details[5]);
                    return lecturerDetails;
                }
            }
            lecturerDetails.add("Lecturer not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lecturerDetails;
    }
}

