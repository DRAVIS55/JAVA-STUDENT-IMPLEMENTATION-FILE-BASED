import java.io.*;
import java.util.ArrayList;

public class Student extends Person {

    // Instance of ReusableClass for printing messages
    

    // Constructor
    public Student() {
        
    }

    // Register a student via file operations
    public void registerStudent(String firstName, String lastName, String email, String phone, String gender, String programmeID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("programme_data.txt"))) {
            String line;
            boolean programmeExists = false;

            // Check if the programme ID exists
            while ((line = reader.readLine()) != null) {
                if (line.contains(programmeID)) {
                    programmeExists = true;
                    break;
                }
            }

            if (!programmeExists) {
                new ReusableClass().printMessage("❌ Error: programme_id '" + programmeID + "' does not exist!");
                return;
            }

            // Save the student information
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("student_data.txt", true))) {
                String studentData = firstName + "," + lastName + "," + email + "," + phone + "," + gender + "," + programmeID;
                writer.write(studentData);
                writer.newLine();
                new ReusableClass().printMessage("✅ Student registered successfully!");
            }
        } catch (IOException e) {
            new ReusableClass().printMessage("❌ Error registering student!");
        }
    }

    // Enroll a student in a course via file operations
    public void enrollStudent(String studentID, String courseCode, String semester, String score) {
       
        try {
            // Check if the student exists
            BufferedReader studentReader = new BufferedReader(new FileReader("student_data.txt"));
            String line;
            boolean studentExists = false;
            while ((line = studentReader.readLine()) != null) {
                if (line.split(",")[0].equals(studentID)) {
                    studentExists = true;
                    break;
                }
            }

            if (!studentExists) {
                new ReusableClass().printMessage("❌ Student not found!");
                return;
            }

            // Check if the course exists
            BufferedReader courseReader = new BufferedReader(new FileReader("course_data.txt"));
            boolean courseExists = false;
            while ((line = courseReader.readLine()) != null) {
                if (line.contains(courseCode)) {
                    courseExists = true;
                    break;
                }
            }

            if (!courseExists) {
                new ReusableClass().printMessage("❌ Course not found!");
                return;
            }

            // Save enrollment data
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("student_courses.txt", true))) {
                String enrollmentData = studentID + "," + courseCode + "," + semester + "," + score;
                writer.write(enrollmentData);
                writer.newLine();
                new ReusableClass().printMessage("✅ Student enrolled successfully in the course!");
            }
        } catch (IOException e) {
            new ReusableClass().printMessage("❌ Error enrolling student to course!");
        }
    }

    // Method to assign scores to a student for a specific course
    public void assignScores(String studentID, String courseCode, String score) {
        try {
            ArrayList<String> fileContent = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader("student_courses.txt"));
            String line;
            boolean updated = false;

            // Check and update the score for the student-course pair
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(studentID) && data[1].equals(courseCode)) {
                    line = studentID + "," + courseCode + "," + data[2] + "," + score;  // Update score
                    updated = true;
                }
                fileContent.add(line);
            }

            if (updated) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("student_courses.txt"))) {
                    for (String content : fileContent) {
                        writer.write(content);
                        writer.newLine();
                    }
                    new ReusableClass().printMessage("✅ Score successfully assigned!");
                }
            } else {
                new ReusableClass().printMessage("❌ Student-course pair not found!");
            }
        } catch (IOException e) {
            new ReusableClass().printMessage("❌ Error assigning score!");
        }
    }

    // Method to search a student by ID and return details
    public ArrayList<String> searchStudent(String studentID) {
        ArrayList<String> details = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("student_data.txt"))) {
            String line;
            boolean studentFound = false;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(studentID)) {
                    studentFound = true;
                    details.add("Student ID: " + data[0]);
                    details.add("First Name: " + data[1]);
                    details.add("Last Name: " + data[2]);
                    details.add("Email: " + data[3]);
                    details.add("Phone: " + data[4]);
                    details.add("Gender: " + data[5]);
                    details.add("Programme ID: " + data[6]);
                    break;
                }
            }

            if (!studentFound) {
                details.add("❌ Student not found!");
            }
        } catch (IOException e) {
            details.add("❌ Error searching student!");
        }

        return details;
    }

    // Search student results by ID
    public ArrayList<String> searchStudentResults(String studentId) throws IOException {
        ArrayList<String> results = new ArrayList<>();
        BufferedReader scReader = new BufferedReader(new FileReader("student_courses.txt"));
        String line;
        boolean found = false;

        while ((line = scReader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(studentId)) {
                String courseCode = parts[1];
                String score = parts[2];

                String courseName = getCourseName(courseCode);
                results.add(courseName + " - Score: " + score);
                found = true;
            }
        }
        scReader.close();

        if (!found) {
            results.add("No courses found for this student.");
        }

        return results;
    }

    // Helper method to get course name by course code
    private String getCourseName(String courseCode) throws IOException {
        BufferedReader courseReader = new BufferedReader(new FileReader("course_data.txt"));
        String line;

        while ((line = courseReader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(courseCode)) {
                courseReader.close();
                return parts[1];
            }
        }
        courseReader.close();

        return "Course not found"; 
    }
}
