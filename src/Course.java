import java.io.*;
import java.util.ArrayList;

public class Course {
    private static final String FILE_PATH = "course_data.txt";

    // Add a new course
    public void addCourse(String courseCode, String name, String creditHours) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean courseExists = false;

            // Check if course code already exists in the file
            while ((line = reader.readLine()) != null) {
                if (line.contains(courseCode)) {
                    courseExists = true;
                    break;
                }
            }

            if (courseExists) {
                new ReusableClass().printMessage("The course code already exists!");
                return;
            }

            // If course doesn't exist, add it to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                String courseData = courseCode + "," + name + "," + creditHours;
                writer.write(courseData);
                writer.newLine();
                new ReusableClass().printMessage("Course registered successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            new ReusableClass().printMessage("App error: Unable to add course! " + e.getMessage());
        }
    }

    // Modify an existing course
    public void modifyCourse(String courseCode, String creditHours) {
        try {
            ArrayList<String> fileContent = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            boolean courseFound = false;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(courseCode)) {
                    line = courseCode + "," + data[1] + "," + creditHours;
                    courseFound = true;
                }
                fileContent.add(line);
            }
            reader.close();

            if (!courseFound) {
                new ReusableClass().printMessage("The course code is not registered!");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String content : fileContent) {
                    writer.write(content);
                    writer.newLine();
                }
                new ReusableClass().printMessage("Course modified successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            new ReusableClass().printMessage("App error: Unable to modify course! " + e.getMessage());
        }
    }

    // Delete an existing course
    public void deleteCourse(String courseCode) {
        try {
            ArrayList<String> fileContent = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            boolean courseFound = false;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(courseCode)) {
                    courseFound = true;
                    continue;
                }
                fileContent.add(line);
            }
            reader.close();

            if (!courseFound) {
                new ReusableClass().printMessage("Course does not exist!");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String content : fileContent) {
                    writer.write(content);
                    writer.newLine();
                }
                new ReusableClass().printMessage("Course deleted successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            new ReusableClass().printMessage("App error: Unable to delete course! " + e.getMessage());
        }
    }

    // Show all courses
    public ArrayList<String> showCourses() {
        ArrayList<String> contentList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                contentList.add(data[0] + " - " + data[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            new ReusableClass().printMessage("App Error: Unable to load courses!");
        }
        return contentList;
    }

    // Search for students registered in a specific course
    public ArrayList<String> searchRegisteredStudents(String courseId) {
        ArrayList<String> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("course_data.txt"))) {
            String line;
            boolean studentsFound = false;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(courseId)) {
                    studentsFound = true;
                    students.add("Student ID: " + data[0] + " - Course Code: " + data[1]);
                }
            }

            if (!studentsFound) {
                students.add("No students found for this course.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            students.add("Error retrieving registered students.");
        }
        return students;
    }

    // Get lecturers assigned to a specific course
    public ArrayList<String[]> getLecturersByCourse(String courseId) {
        ArrayList<String[]> lecturers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("lecturer_data.txt"))) {
            String line;
            boolean lecturersFound = false;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(courseId)) {
                    lecturersFound = true;
                    lecturers.add(new String[]{data[0], data[2], data[3]});
                }
            }

            if (!lecturersFound) {
                lecturers.add(new String[]{"No lecturers found for this course."});
            }
        } catch (IOException e) {
            e.printStackTrace();
            lecturers.add(new String[]{"Error retrieving lecturers."});
        }
        return lecturers;
    }
}

