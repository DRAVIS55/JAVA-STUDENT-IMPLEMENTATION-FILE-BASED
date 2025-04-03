import java.io.File;
import java.io.IOException;

public class App {
    

    // Constructor: Accepts a file name and ensures it exists
    public App(String fileName) throws IOException {
        checkAndCreateFile(fileName);
        
    }

    // Method to check if a file exists, is readable, and writable
    private void checkAndCreateFile(String fileName) throws IOException {
        File file = new File(fileName);

        // Check if the file exists
        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("Failed to create file: " + file.getName());
            }
        } else {
            System.out.println("File already exists: " + file.getName());

            // Check if the file is readable and writable
            if (!file.canRead() || !file.canWrite()) {
                System.out.println("File is not readable or writable. Attempting to adjust permissions...");

                // Attempt to set the file as readable and writable
                try {
                    if (file.setReadable(true) && file.setWritable(true)) {
                        System.out.println("Permissions set: File is now readable and writable.");
                    } else {
                        System.out.println("Failed to set file permissions.");
                    }
                } catch (SecurityException e) {
                    System.out.println("SecurityException: Could not adjust file permissions.");
                    e.printStackTrace();
                }

                // If still not readable or writable, delete and recreate the file
                if (!file.canRead() || !file.canWrite()) {
                    System.out.println("File permissions cannot be fixed. Deleting and recreating file...");
                    if (file.delete() && file.createNewFile()) {
                        System.out.println("File deleted and recreated: " + file.getName());
                    } else {
                        System.out.println("Failed to delete and recreate the file: " + file.getName());
                    }
                }
            }
        }
    }
}
