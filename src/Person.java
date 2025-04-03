public class Person {
    // Fields (attributes)
    private String firstName;
    private String lastName;
    private String id; // This could be a student ID, employee ID, etc.

    // Default constructor
    public Person() {
        // Initialize with default values
        this.firstName = "";
        this.lastName = "";
        this.id = "";
    }

    // Constructor with parameters
    public Person(String firstName, String lastName, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    // Getter and Setter methods for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter and Setter methods for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter and Setter methods for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // toString method to provide a string representation of the Person object
    @Override
    public String toString() {
        return "Person [ID=" + id + ", Name=" + firstName + " " + lastName + "]";
    }
}
