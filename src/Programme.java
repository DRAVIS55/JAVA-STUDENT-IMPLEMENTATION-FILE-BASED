import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Programme  {

    // Path for the storage files (for simplicity, storing as plain text files)
    private static final String PROGRAMME_FILE = "programme_data.txt";
    private static final String PROGRAMME_COURSES_FILE = "programme_data.txt";

    // Explicitly call the superclass constructor (no parameters in this case)
    public Programme()  { // Call the App constructor
        
    }

    // Add a programme to the file
    public void addProgram(App app, String programmeName, String programmeCode, BigDecimal programmeCost) throws IOException {
        File programmeFile = new File(PROGRAMME_FILE);
    
        // Check if the file is writable
        if (!hasWritePermission(programmeFile)) {
            new ReusableClass().printMessage("The file is not writable. Please check the file permissions.");
            return;
        }
        File myobj=new File("programme_data.txt");
    Scanner reader=new Scanner(myobj);
       // BufferedReader reader = new BufferedReader(new FileReader(programmeFile));
        String line;
    
        // Check if the programme already exists
        while ( reader.hasNextLine())  {
            line=reader.nextLine();
            String[] parts = line.split(",");
            if (parts[1].equals(programmeCode)) {
                new ReusableClass().printMessage("The Programme code or programme already exists!");
                reader.close();
                return;
            }
        }
        reader.close();
    
        // Add new programme if writable
        BufferedWriter writer = new BufferedWriter(new FileWriter(programmeFile, true));
        writer.write(programmeName + "," + programmeCode + "," + programmeCost + "\n");
        writer.close();
    
        new ReusableClass().printMessage("The programme is updated successfully\nProgramme code: " + programmeCode + "\nProgramme Name: " + programmeName);
    }
    // Check if the file has write permission
public boolean hasWritePermission(File file) {
    if (file.exists()) {
        return file.canWrite();  // Check if the file has write permissions
    }
    return false;
}


    // Modify programme details in the file
    public void modifyProgram(App app, BigDecimal cost, String code) throws IOException {
        File tempFile = new File("temp_programmes.txt");
        BufferedReader reader = new BufferedReader(new FileReader(PROGRAMME_FILE));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean found = false;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[1].equals(code)) {
                line = parts[0] + "," + parts[1] + "," + cost;  // Modify cost
                found = true;
            }
            writer.write(line + "\n");
        }

        reader.close();
        writer.close();

        if (found) {
            tempFile.renameTo(new File(PROGRAMME_FILE));  // Replace original file with modified file
            new ReusableClass().printMessage("Updated successfully: Programme code: " + code + "\nNew cost: " + cost);
        } else {
            tempFile.delete();
            new ReusableClass().printMessage("The programme does not exist! Consider creating one.");
        }
    }

    // Remove programme from the file
    public void removeProgram(App app, String programme_code) throws IOException {
        File tempFile = new File("temp_programmes.txt");
        BufferedReader reader = new BufferedReader(new FileReader(PROGRAMME_FILE));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        boolean found = false;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[1].equals(programme_code)) {
                found = true;  // Skip this programme
                continue;
            }
            writer.write(line + "\n");
        }

        reader.close();
        writer.close();

        if (found) {
            tempFile.renameTo(new File(PROGRAMME_FILE));  // Replace original file with modified file
            new ReusableClass().printMessage("Programme removed successfully!");
        } else {
            tempFile.delete();
            new ReusableClass().printMessage("The programme does not exist!");
        }
    }

    // Attach courses to a programme in the file
    public void attachProgramCourses(App app, String program, String course) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(PROGRAMME_COURSES_FILE));
        String line;
        
        // Check if the combination already exists
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(program) && parts[1].equals(course)) {
                new ReusableClass().printMessage("The program and course already exist in the table!");
                reader.close();
                return;
            }
        }
        reader.close();

        // Add new programme-course pair
        BufferedWriter writer = new BufferedWriter(new FileWriter(PROGRAMME_COURSES_FILE, true));
        writer.write(program + "," + course + "\n");
        writer.close();

        new ReusableClass().printMessage("Course assigned successfully!");
    }

    // Search for students by programme in the file
    public ArrayList<String> searchStudentsByProgramme(App app, String programmeId) throws IOException {
        ArrayList<String> students = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
        String line;

        boolean found = false;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[3].equals(programmeId)) {
                students.add(parts[0] + " - " + parts[1] + " " + parts[2]);
                found = true;
            }
        }
        reader.close();

        if (!found) {
            students.add("No students found for this programme.");
        }

        return students;
    }
}
