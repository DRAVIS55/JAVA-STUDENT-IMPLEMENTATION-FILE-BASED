
import java.math.BigDecimal;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;

public class SystemGui extends JFrame {
private JButton queryBt, exitBt, student, lecturer, course, programme;
    private JPanel panel1;

    public SystemGui() {
        super("🎓 Student Portal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 730);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Gradient Background Panel
        panel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 102, 204), getWidth(), getHeight(), new Color(34, 45, 50));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel1.setLayout(new GridBagLayout());
        panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panel1, BorderLayout.CENTER);

        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(10, 10, 10, 10);
        gdc.fill = GridBagConstraints.BOTH;
        gdc.weightx = 1.0;
        gdc.weighty = 1.0;

        // Title Label
        JLabel titleLabel = new JLabel("Student Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gdc.gridx = 0;
        gdc.gridy = 0;
        gdc.gridwidth = 3;
        panel1.add(titleLabel, gdc);

        // Query Terminal Button
        queryBt = createStyledButton("🖥 Query Terminal");
        queryBt.addActionListener(e -> new QueryTerminal().setVisible(true));
        gdc.gridy = 1;
        panel1.add(queryBt, gdc);

        // Student Panel Button
        student = createStyledButton("👨‍🎓 Student Panel");
        student.addActionListener(e -> new StudentPanel().setVisible(true));
        gdc.gridwidth = 1;
        gdc.gridy = 2;
        gdc.gridx = 0;
        panel1.add(student, gdc);

        // Lecturer Panel Button
        lecturer = createStyledButton("👨‍🏫 Lecturer Panel");
        lecturer.addActionListener(e -> new LecturerPanel().setVisible(true));
        gdc.gridx = 1;
        panel1.add(lecturer, gdc);

        // Course Panel Button
        course = createStyledButton("📚 Course Panel");
        course.addActionListener(e -> new CoursePanel().setVisible(true));
        gdc.gridx = 2;
        panel1.add(course, gdc);

        // Programme Panel Button
        programme = createStyledButton("🏫 Programme Panel");
        programme.addActionListener(e -> new ProgrammePanel().setVisible(true));
        gdc.gridx = 0;
        gdc.gridy = 3;
        panel1.add(programme, gdc);

        // Exit Button
        exitBt = createExitButton();
        gdc.gridx = 2;
        gdc.gridy = 4;
        gdc.anchor = GridBagConstraints.SOUTHEAST;
        panel1.add(exitBt, gdc);
    }

    // Method to create a modern, rounded button
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 153, 204));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 165, 0)); // Orange on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 153, 204)); // Original blue
            }
        });

        return button;
    }

    // Exit button with hover effect
    private JButton createExitButton() {
        JButton exitButton = new JButton("❌ Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(Color.RED);
        exitButton.setFocusPainted(false);
        exitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        exitButton.setOpaque(true);

        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(Color.DARK_GRAY);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(Color.RED);
            }
        });

        exitButton.addActionListener(e -> System.exit(0));
        return exitButton;
    }
    public static void main(String[] args) {
        // Define the directory path where files will be stored
        String directoryPath = "data";  // Directory for storing the files

        try {
            // Create an instance of the App class which handles directory and file creation
            App app = new App(directoryPath);  // This will handle directory creation and file checks

            // After creating files and ensuring permissions, we can initialize the GUI
            SwingUtilities.invokeLater(() -> new SystemGui().setVisible(true));

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating files: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    //query terminal implementation
   
    class QueryTerminal extends JFrame {
        private JTextField queryTextField, messJTextField;
        private JButton submit;
    
        public QueryTerminal() {
            super("SQL Query Terminal");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(1000, 700);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
    
            // Message Field
            messJTextField = new JTextField("Enter the SQL code to execute");
            messJTextField.setBackground(Color.BLACK);
            messJTextField.setForeground(Color.RED);
            messJTextField.setEditable(false);
            add(messJTextField, BorderLayout.NORTH);
    
            // SQL Input Field
            queryTextField = new JTextField();
            queryTextField.setBackground(Color.BLACK);
            queryTextField.setForeground(Color.WHITE);
            queryTextField.setCaretColor(Color.WHITE);
            queryTextField.requestFocusInWindow();
            queryTextField.setCaretPosition(0);
            add(queryTextField, BorderLayout.CENTER);
    
            // Submit Button
            submit = new JButton("SUBMIT");
            submit.setBackground(new Color(0, 102, 204));
            submit.setForeground(Color.WHITE);
            submit.setFocusPainted(false);
            submit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            add(submit, BorderLayout.SOUTH);
    
            /*submit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        String sqlQuery = queryTextField.getText().trim();
                        if (sqlQuery.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "The SQL code is empty", "Error Notification", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
    
                        // Execute Query
                        ExecuteCode executor = new ExecuteCode();
                        executor.executeTerminalCode(sqlQuery);
    
                        // Display Results
                        JFrame resultFrame = new JFrame("Query Results");
                        resultFrame.setSize(1200, 900);
                        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        resultFrame.setLocationRelativeTo(null);
    
                        JPanel panel = new JPanel();
                        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
                        for (String result : ExecuteCode.results) {
                            JLabel label = new JLabel(result);
                            label.setForeground(Color.WHITE);
                            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                            panel.setBackground(Color.BLACK);
                            panel.add(label);
                        }
    
                        JScrollPane scrollPane = new JScrollPane(panel);
                        resultFrame.add(scrollPane, BorderLayout.CENTER);
                        resultFrame.setVisible(true);
    
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error running query", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });*/
    
            setVisible(true);
        }
    }
    
 
//student panel


class StudentPanel extends JFrame {
    public StudentPanel() {
        super("Student Panel ---");
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Styling
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        add(panel, BorderLayout.CENTER);

        // GridBagConstraints for layout
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(10, 10, 10, 10);
        gdc.fill = GridBagConstraints.HORIZONTAL;
        gdc.gridx = 0;

        // Buttons
        JButton button1 = createStyledButton("Admit/Assign Programme");
        button1.addActionListener(e -> admitStudent());
        gdc.gridy = 1;
        panel.add(button1, gdc);

        JButton button2 = createStyledButton("Choose Courses/Enroll");
        button2.addActionListener(e -> {
            try{
StudentEnrollmentGUI se=new StudentEnrollmentGUI(new App("student_data.txt"));
            }
            catch(Exception ie)
            {
                ie.printStackTrace();
                new ReusableClass().printMessage("app error: instalizing the enroll panel");
            }
        });
        gdc.gridy = 2;
        panel.add(button2, gdc);

        JButton button3 = createStyledButton("Assign Scores to Course");
        button3.addActionListener(e -> assignScores());
        gdc.gridy = 3;
        panel.add(button3, gdc);

        JButton button4 = createStyledButton("Search student");
        button4.addActionListener(e -> new StudentSearchGUI());
        gdc.gridy = 4;
        panel.add(button4, gdc);

        JButton button5 = createStyledButton("SHOW results");
        button5.addActionListener(e -> new StudentResultGUI());
        gdc.gridy = 5;
        panel.add(button5, gdc);
        // Exit Button (Aligned Bottom Right)
        JButton exitButton = createExitButton();
        add(exitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        return button;
    }

    private JButton createExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setFocusPainted(false);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> dispose());
        return exitButton;
    }

    public void admitStudent() {
        JFrame frame = new JFrame("Student Admission Form");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Panel for form elements
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        frame.add(panel, BorderLayout.CENTER);

        // GridBagConstraints for layout
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(5, 5, 5, 5);
        gdc.fill = GridBagConstraints.HORIZONTAL;
        gdc.anchor = GridBagConstraints.WEST;
        
        String[] labels = {"First Name:", "Last Name:", "Email:", "Phone:", "Gender:", "Programme ID:"};
        JTextField[] textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gdc.gridx = 0;
            gdc.gridy = i;
            panel.add(new JLabel(labels[i]), gdc);
            
            gdc.gridx = 1;
            if (i == 4) {  // Gender field should be a JComboBox
                String[] genderOptions = {"Male", "Female", "Other"};
                JComboBox<String> genderComboBox = new JComboBox<>(genderOptions);
                panel.add(genderComboBox, gdc);
                textFields[i] = new JTextField(); // Placeholder to store value later
                textFields[i].setVisible(false); // Hide the unused text field
                genderComboBox.addActionListener(e -> textFields[4].setText((String) genderComboBox.getSelectedItem()));
            } else {
                textFields[i] = new JTextField(15);
                panel.add(textFields[i], gdc);
            }
        }

        // Submit Button
        JButton submitButton = new JButton("Submit");
        gdc.gridx = 1;
        gdc.gridy = labels.length;
        gdc.fill = GridBagConstraints.NONE;
        panel.add(submitButton, gdc);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String firstName = textFields[0].getText().trim();
                    String lastName = textFields[1].getText().trim();
                    String email = textFields[2].getText().trim();
                    String phone = textFields[3].getText().trim();
                    String gender = textFields[4].getText().trim();
                    String programmeID = textFields[5].getText().trim();

                    // Basic Validations
                    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || programmeID.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                        JOptionPane.showMessageDialog(frame, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!phone.matches("\\d{10}")) {
                        JOptionPane.showMessageDialog(frame, "Phone number must be 10 digits!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    App app = new App("student_data.txt");
                    Student student=new Student();
                    student.registerStudent( firstName, lastName, email, phone, gender, programmeID);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error executing registration!", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        
        frame.setVisible(true);
    }
   
    
    public void assignScores(){
        JFrame frame=new JFrame();
            frame.setSize(500,400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel panel = new JPanel(new GridBagLayout());
            frame.add(panel, BorderLayout.CENTER);
            
            GridBagConstraints gdc = new GridBagConstraints();
            gdc.insets = new Insets(8, 8, 8, 8);
            gdc.fill = GridBagConstraints.HORIZONTAL;
    
            // Row 1: Programme Code Label
            gdc.gridx = 0;
            gdc.gridy = 0;
            panel.add(new JLabel("Regisrtation number:"), gdc);
    
            // Row 1: Programme Code TextField
            gdc.gridx = 1;
            JTextField text1 = new JTextField(15);
            gdc.weightx = 1.0; // Allow expansion
            panel.add(text1, gdc);
    
            // Row 2: Programme Name Label
            gdc.gridx = 0;
            gdc.gridy = 1;
            panel.add(new JLabel("Course code:"), gdc);
    
            // Row 2: Programme Name TextField
            gdc.gridx = 1;
            JTextField text2 = new JTextField(15);
            panel.add(text2, gdc);
    
            // Row 3: Programme Cost Label
            gdc.gridx = 0;
            gdc.gridy = 2;
            panel.add(new JLabel("Course Score:"), gdc);
    
            // Row 3: Programme Cost TextField
            gdc.gridx = 1;
            JTextField text3 = new JTextField(15);
            panel.add(text3, gdc);
    
            // Row 4: Submit Button
            gdc.gridx = 0;
            gdc.gridy = 3;
            gdc.gridwidth = 2; // Make button span both columns
            gdc.anchor = GridBagConstraints.CENTER;
            JButton submitButton = new JButton("Submit");
            panel.add(submitButton, gdc);
    
            frame.setVisible(true);
          
            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    try{
                    String code=text2.getText();
                    String name=text1.getText();
                    String score=text3.getText();
                    if(score.isEmpty()){
                        new ReusableClass().printMessage("enter all values: no course score !");
                        return;
                    }
                    if(name.isEmpty()|code.isEmpty()){
                        new ReusableClass().printMessage("cannot accept null input of registration number or course code !");
                        return;
                    }
                    
                    else{
                    App app=new App("student_data.txt");
                    new Student().assignScores(name, code, score);
                    }
                    }
                    catch(Exception ie){
                        new ReusableClass().printMessage("error: gui app error: error running!");
                        ie.printStackTrace();
                    }
                }
            });
    }
}




//course panel
class CoursePanel extends JFrame {
    public CoursePanel() {
        super("Course Panel ---");
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        add(panel, BorderLayout.CENTER);

        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(10, 10, 10, 10);
        gdc.fill = GridBagConstraints.HORIZONTAL;
        gdc.gridx = 0;

        JButton button1 = createStyledButton("Add Course");
        button1.addActionListener(e -> addCourseGUI());
        gdc.gridy = 1;
        panel.add(button1, gdc);

        JButton button2 = createStyledButton("Delete Course");
        button2.addActionListener(e -> deleteCourseGUI());
        gdc.gridy = 2;
        panel.add(button2, gdc);

        JButton button3 = createStyledButton("Modify Course");
        button3.addActionListener(e -> modifyCourseGUI());
        gdc.gridy = 3;
        panel.add(button3, gdc);

        JButton button4 = createStyledButton("Extract student enrolled");
        button4.addActionListener(e -> new CourseSearchGUI());
        gdc.gridy = 4;
        panel.add(button4, gdc);

        JButton button5 = createStyledButton("Extract Lecturer assignet to a course");
        button5.addActionListener(e -> new LecturerCourseGUI());
        gdc.gridy = 5;
        panel.add(button5, gdc);

        // Exit Button
        JButton exitButton = createExitButton();
        add(exitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        return button;
    }

    private JButton createExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setFocusPainted(false);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> dispose());
        return exitButton;
    }

    public void addCourseGUI() {

       JFrame frame=new JFrame();
            frame.setSize(500,400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel panel = new JPanel(new GridBagLayout());
            frame.add(panel, BorderLayout.CENTER);
            
            GridBagConstraints gdc = new GridBagConstraints();
            gdc.insets = new Insets(8, 8, 8, 8);
            gdc.fill = GridBagConstraints.HORIZONTAL;
    
            // Row 1: Programme Code Label
            gdc.gridx = 0;
            gdc.gridy = 0;
            panel.add(new JLabel("course Code"), gdc);
    
            // Row 1: Programme Code TextField
            gdc.gridx = 1;
            JTextField text1 = new JTextField(15);
            gdc.weightx = 1.0; // Allow expansion
            panel.add(text1, gdc);
    
            // Row 2: Programme Name Label
            gdc.gridx = 0;
            gdc.gridy = 1;
            panel.add(new JLabel("Course Name"), gdc);
    
            // Row 2: Programme Name TextField
            gdc.gridx = 1;
            JTextField text2 = new JTextField(15);
            panel.add(text2, gdc);
    
            // Row 3: Programme Cost Label
            gdc.gridx = 0;
            gdc.gridy = 2;
            panel.add(new JLabel("credit hours"), gdc);
    
            // Row 3: Programme Cost TextField
            gdc.gridx = 1;
            JTextField text3 = new JTextField(15);
            panel.add(text3, gdc);
    
            // Row 4: Submit Button
            gdc.gridx = 0;
            gdc.gridy = 3;
            gdc.gridwidth = 2; // Make button span both columns
            gdc.anchor = GridBagConstraints.CENTER;
            JButton submitButton = new JButton("Submit");
            panel.add(submitButton, gdc);
    
            frame.setVisible(true);
          
            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    try{
                    String name=text2.getText();
                    String code=text1.getText();
                    String hours=text3.getText();
                    if(hours.isEmpty()){
                        new ReusableClass().printMessage("enter all values: no credit hours !");
                        return;
                    }
                    if(name.isEmpty()|code.isEmpty()){
                        new ReusableClass().printMessage("cannot accept null input of name or code !");
                        return;
                    }
                    
                    else{
                    
                    new Course().addCourse( code, name, hours);
                    }
                    }
                    catch(Exception ie){
                        new ReusableClass().printMessage("error: gui app error: error running!");
                        ie.printStackTrace();
                    }
                }
            });
    }
    public void modifyCourseGUI() {
        JFrame frame=new JFrame();
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel, BorderLayout.CENTER);
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(8, 8, 8, 8);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Programme Code Label
        gdc.gridx = 0;
        gdc.gridy = 0;
        panel.add(new JLabel("Course Code"), gdc);

        // Row 1: Programme Code TextField
        gdc.gridx = 1;
        JTextField text1 = new JTextField(15);
        gdc.weightx = 1.0; // Allow expansion
        panel.add(text1, gdc);

        // Row 3: Programme Cost Label
        gdc.gridx = 0;
        gdc.gridy = 2;
        panel.add(new JLabel("Credit hours"), gdc);

        // Row 3: Programme Cost TextField
        gdc.gridx = 1;
        JTextField text3 = new JTextField(15);
        panel.add(text3, gdc);

        // Row 4: Submit Button
        gdc.gridx = 0;
        gdc.gridy = 3;
        gdc.gridwidth = 2; // Make button span both columns
        gdc.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton, gdc);

        frame.setVisible(true);
      
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try{
                
                String code=text1.getText();
                String credit_hours=text3.getText();
                if(credit_hours.isEmpty()){
                    new ReusableClass().printMessage("enter all values: you haven't entered the credit hours !");
                    return;
                }
                if(code.isEmpty()){
                    new ReusableClass().printMessage("cannot accept null input of  code !");
                    return;
                }
                
                else{
                
                new Course().modifyCourse( code, credit_hours);
                }
                }
                catch(Exception ie){
                    new ReusableClass().printMessage("error: gui app error: error running!");
                    ie.printStackTrace();
                }
            }
        });
    }
    public void deleteCourseGUI() {
        JFrame frame=new JFrame();
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel, BorderLayout.CENTER);
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(8, 8, 8, 8);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Programme Code Label
        gdc.gridx = 0;
        gdc.gridy = 0;
        panel.add(new JLabel("Course Code"), gdc);

        // Row 1: Programme Code TextField
        gdc.gridx = 1;
        JTextField text1 = new JTextField(15);
        gdc.weightx = 1.0; // Allow expansion
        panel.add(text1, gdc);

        // Row 4: Submit Button
        gdc.gridx = 0;
        gdc.gridy = 3;
        gdc.gridwidth = 2; // Make button span both columns
        gdc.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton, gdc);

        frame.setVisible(true);
      
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try{
                
                String code=text1.getText();
                
                if(code.isEmpty()){
                    new ReusableClass().printMessage("cannot accept null input of  code !");
                    return;
                }
                
                else{
                
                
                new Course().deleteCourse( code);
                }
                }
                catch(Exception ie){
                    new ReusableClass().printMessage("error: gui app error: error running!");
                    ie.printStackTrace();
                }
            }
        });
    }
}


// programme panel
class ProgrammePanel extends JFrame {
    public ProgrammePanel() {
        super("Programme Panel ---");
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        add(panel, BorderLayout.CENTER);

        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(10, 10, 10, 10);
        gdc.fill = GridBagConstraints.HORIZONTAL;
        gdc.gridx = 0;

        JButton button1 = createStyledButton("Add Programme");
        button1.addActionListener(e -> addprogrammeGui());
        gdc.gridy = 1;
        panel.add(button1, gdc);

        JButton button2 = createStyledButton("Modify Programme");
        button2.addActionListener(e -> modifyProgrammeGUI());
        gdc.gridy = 2;
        panel.add(button2, gdc);

        JButton button3 = createStyledButton("Delete Programme");
        button3.addActionListener(e -> deleteProgrammeGUI());
        gdc.gridy = 3;
        panel.add(button3, gdc);

        JButton button4 = createStyledButton("attach courses to programs");
        button4.addActionListener(e -> attachProgrammeCoursesGUI());
        gdc.gridy = 4;
        panel.add(button4, gdc);

        JButton button5 = createStyledButton("extract student registred to a programme");
        button5.addActionListener(e -> new ProgrammeSearchGUI());
        gdc.gridy = 5;
        panel.add(button5, gdc);


        // Exit Button
        JButton exitButton = createExitButton();
        add(exitButton, BorderLayout.SOUTH);

        setVisible(true);
    }
    public void attachProgrammeCoursesGUI(){
        JFrame frame=new JFrame();
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel, BorderLayout.CENTER);
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(8, 8, 8, 8);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Programme Code Label
        gdc.gridx = 0;
        gdc.gridy = 0;
        panel.add(new JLabel("Programme Code"), gdc);

        // Row 1: Programme Code TextField
        gdc.gridx = 1;
        JTextField text1 = new JTextField(15);
        gdc.weightx = 1.0; // Allow expansion
        panel.add(text1, gdc);

        // Row 3: Programme Cost Label
        gdc.gridx = 0;
        gdc.gridy = 2;
        panel.add(new JLabel("Course Code"), gdc);

        // Row 3: Programme Cost TextField
        gdc.gridx = 1;
        JTextField text3 = new JTextField(15);
        panel.add(text3, gdc);

        // Row 4: Submit Button
        gdc.gridx = 0;
        gdc.gridy = 3;
        gdc.gridwidth = 2; // Make button span both columns
        gdc.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton, gdc);

        frame.setVisible(true);
      
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try{
                
                String code=text1.getText();
                String cost=text3.getText();
                if(cost.isEmpty()){
                    new ReusableClass().printMessage("enter all values: no programe cost !");
                    return;
                }
                if(code.isEmpty()){
                    new ReusableClass().printMessage("cannot accept null input of  code !");
                    return;
                }
                
                else{
                App app=new App("programme_data.txt");
                new Programme().attachProgramCourses(app,code,cost);
                }
                }
                catch(Exception ie){
                    new ReusableClass().printMessage("error: gui app error: error running!");
                    ie.printStackTrace();
                }
            }
        });
    }
    public void addprogrammeGui(){
            JFrame frame=new JFrame();
            frame.setSize(1000,700);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel panel = new JPanel(new GridBagLayout());
            frame.add(panel, BorderLayout.CENTER);
            
            GridBagConstraints gdc = new GridBagConstraints();
            gdc.insets = new Insets(8, 8, 8, 8);
            gdc.fill = GridBagConstraints.HORIZONTAL;
    
            // Row 1: Programme Code Label
            gdc.gridx = 0;
            gdc.gridy = 0;
            panel.add(new JLabel("Programme Code"), gdc);
    
            // Row 1: Programme Code TextField
            gdc.gridx = 1;
            JTextField text1 = new JTextField(15);
            gdc.weightx = 1.0; // Allow expansion
            panel.add(text1, gdc);
    
            // Row 2: Programme Name Label
            gdc.gridx = 0;
            gdc.gridy = 1;
            panel.add(new JLabel("Programme Name"), gdc);
    
            // Row 2: Programme Name TextField
            gdc.gridx = 1;
            JTextField text2 = new JTextField(15);
            panel.add(text2, gdc);
    
            // Row 3: Programme Cost Label
            gdc.gridx = 0;
            gdc.gridy = 2;
            panel.add(new JLabel("Programme Cost"), gdc);
    
            // Row 3: Programme Cost TextField
            gdc.gridx = 1;
            JTextField text3 = new JTextField(15);
            panel.add(text3, gdc);
    
            // Row 4: Submit Button
            gdc.gridx = 0;
            gdc.gridy = 3;
            gdc.gridwidth = 2; // Make button span both columns
            gdc.anchor = GridBagConstraints.CENTER;
            JButton submitButton = new JButton("Submit");
            panel.add(submitButton, gdc);
    
            frame.setVisible(true);
          
            submitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    try{
                    String name=text2.getText();
                    String code=text1.getText();
                    String cost=text3.getText();
                    if(cost.isEmpty()){
                        new ReusableClass().printMessage("enter all values: no programe cost !");
                        return;
                    }
                    if(name.isEmpty()|code.isEmpty()){
                        new ReusableClass().printMessage("cannot accept null input of name or code !");
                        return;
                    }
                    
                    else{
                    BigDecimal cost1=new BigDecimal(cost);
                    App app=new App("programme_data.txt");
                    new Programme().addProgram(app, name,code, cost1);
                    }
                    }
                    catch(Exception ie){
                        new ReusableClass().printMessage("error: gui app error: error running!");
                        ie.printStackTrace();
                    }
                }
            });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        return button;
    }

    private JButton createExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setFocusPainted(false);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> dispose());
        return exitButton;
    }

    public void deleteProgrammeGUI() {
        
        JFrame frame=new JFrame();
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel, BorderLayout.CENTER);
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(8, 8, 8, 8);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Programme Code Label
        gdc.gridx = 0;
        gdc.gridy = 0;
        panel.add(new JLabel("Programme Code"), gdc);

        // Row 1: Programme Code TextField
        gdc.gridx = 1;
        JTextField text1 = new JTextField(15);
        gdc.weightx = 1.0; // Allow expansion
        panel.add(text1, gdc);

        // Row 4: Submit Button
        gdc.gridx = 0;
        gdc.gridy = 3;
        gdc.gridwidth = 2; // Make button span both columns
        gdc.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton, gdc);

        frame.setVisible(true);
      
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try{
                
                String code=text1.getText();
                
                if(code.isEmpty()){
                    new ReusableClass().printMessage("cannot accept null input of  code !");
                    return;
                }
                
                else{
                
                App app=new App("programme_data.txt");
                new Programme().removeProgram(app, code);
                }
                }
                catch(Exception ie){
                    new ReusableClass().printMessage("error: gui app error: error running!");
                    ie.printStackTrace();
                }
            }
        });
    }
    public void modifyProgrammeGUI(){
        JFrame frame=new JFrame();
        frame.setSize(1000,700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel, BorderLayout.CENTER);
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(8, 8, 8, 8);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Programme Code Label
        gdc.gridx = 0;
        gdc.gridy = 0;
        panel.add(new JLabel("Programme Code"), gdc);

        // Row 1: Programme Code TextField
        gdc.gridx = 1;
        JTextField text1 = new JTextField(15);
        gdc.weightx = 1.0; // Allow expansion
        panel.add(text1, gdc);

        // Row 3: Programme Cost Label
        gdc.gridx = 0;
        gdc.gridy = 2;
        panel.add(new JLabel("Programme Cost"), gdc);

        // Row 3: Programme Cost TextField
        gdc.gridx = 1;
        JTextField text3 = new JTextField(15);
        panel.add(text3, gdc);

        // Row 4: Submit Button
        gdc.gridx = 0;
        gdc.gridy = 3;
        gdc.gridwidth = 2; // Make button span both columns
        gdc.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton, gdc);

        frame.setVisible(true);
      
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try{
                
                String code=text1.getText();
                String cost=text3.getText();
                if(cost.isEmpty()){
                    new ReusableClass().printMessage("enter all values: no programe cost !");
                    return;
                }
                if(code.isEmpty()){
                    new ReusableClass().printMessage("cannot accept null input of  code !");
                    return;
                }
                
                else{
                BigDecimal cost1=new BigDecimal(cost);
                App app=new App("programme_data.txt");
                new Programme().modifyProgram(app, cost1, code);
                }
                }
                catch(Exception ie){
                    new ReusableClass().printMessage("error: gui app error: error running!");
                    ie.printStackTrace();
                }
            }
        });
}
}

class ReusableClass {
      public void printMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
}

 class StudentEnrollmentGUI {

    public StudentEnrollmentGUI(App app) {
        // Initialize the JFrame
        JFrame frame = new JFrame("Student Course Enrollment");
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Main Panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel, BorderLayout.CENTER);
        
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(8, 8, 8, 8);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        // Row 1: Registration Number Label
        gdc.gridx = 0;
        gdc.gridy = 0;
        panel.add(new JLabel("Registration Number:"), gdc);

        // Row 1: Registration Number TextField
        gdc.gridx = 1;
        JTextField regNumberField = new JTextField(15);
        gdc.weightx = 1.0;
        panel.add(regNumberField, gdc);

        // Row 2: Semester Label
        gdc.gridx = 0;
        gdc.gridy = 1;
        panel.add(new JLabel("Semester:"), gdc);

        // Row 2: Semester Dropdown (JComboBox)
        gdc.gridx = 1;
        String[] semesters = {"Semester 1", "Semester 2", "Semester 3", "Semester 4"};
        JComboBox<String> semesterBox = new JComboBox<>(semesters);
        panel.add(semesterBox, gdc);

        // Initialize course list UI
        gdc.gridx = 0;
        gdc.gridy = 2;
        panel.add(new JLabel("Select Courses (Max 3):"), gdc);

        gdc.gridx = 1;
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> courseJList = new JList<>(listModel);
        courseJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane courseScrollPane = new JScrollPane(courseJList);
        courseScrollPane.setPreferredSize(new Dimension(180, 100));
        panel.add(courseScrollPane, gdc);

        // Row 4: Submit Button
        gdc.gridx = 0;
        gdc.gridy = 3;
        gdc.gridwidth = 2;
        gdc.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton, gdc);

        // Row 5: Output Label
        gdc.gridy = 4;
        JLabel outputLabel = new JLabel("Selected: None");
        panel.add(outputLabel, gdc);

        // Fetch available courses
        try {
            Course cs = new Course();
            ArrayList<String> courseList = cs.showCourses();

            if (courseList.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No courses available!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Populate the JList with courses
            for (String course : courseList) {
                listModel.addElement(course);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading courses!", "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Submit Button ActionListener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values
                String regNumber = regNumberField.getText();
                String selectedSemester = (String) semesterBox.getSelectedItem();
                java.util.List<String> selectedCourses = courseJList.getSelectedValuesList();

                // Validation
                if (regNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a registration number.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (selectedCourses.size() > 3) {
                    JOptionPane.showMessageDialog(frame, "You can select up to 3 courses only!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    Student student = new Student();  // Make sure this class exists and has enrollStudent() method

                            // Regex to extract course code (first part before " - ")
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+"); // Matches alphanumeric characters at the start of the string
        for (String course : selectedCourses) {
            System.out.println("Course String: " + course);
            
            // Extract course code
            Matcher matcher = pattern.matcher(course);
            if (matcher.find()) {
                String courseCode = matcher.group();
                System.out.println("Extracted Course Code: " + courseCode);

                // Enroll student with the extracted course code
                student.enrollStudent( regNumber, courseCode, selectedSemester, "0");
            } else {
                System.out.println("Failed to extract course code from: " + course);
            }
        }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error registering courses!", "Database Error", JOptionPane.ERROR_MESSAGE);
                }

                // Display selected details
                outputLabel.setText("<html>Reg: " + regNumber + "<br>Semester: " + selectedSemester + "<br>Courses: " + selectedCourses + "</html>");
            }
        });

        frame.setVisible(true);
    }
}


 class LecturerPanel extends JFrame {
    public LecturerPanel() {
        super("Lecturer Panel");
        setSize(1000, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));
        setLocationRelativeTo(null);

        // Buttons
        JButton registerButton = createButton("Register a Lecturer", e -> openRegisterForm());
        JButton updateButton = createButton("Update Lecturer Details", e -> openUpdateForm());
        JButton assignButton = createButton("Assign Lecturer Courses", e -> openAssignForm());
        JButton exitButton = createExitButton();

        // Adding buttons to frame
        add(registerButton);
        add(updateButton);
        add(assignButton);
        add(exitButton);

        setVisible(true);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private JButton createExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> dispose());
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        return exitButton;
    }

    private void openRegisterForm() {
        new RegisterLecturerForm();
    }

    private void openUpdateForm() {
        new UpdateLecturerForm();
    }

    private void openAssignForm() {
        new AssignCourseForm();
    }

}

class RegisterLecturerForm extends JFrame {
    public RegisterLecturerForm() {
        super("Register Lecturer");
        setSize(1000, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        GridBagConstraints gdc = new GridBagConstraints();
        gdc.insets = new Insets(10, 10, 10, 10);
        gdc.fill = GridBagConstraints.HORIZONTAL;

        // Lecturer ID
        gdc.gridx = 0; gdc.gridy = 0;
        add(new JLabel("Lecturer ID:"), gdc);
        gdc.gridx = 1;
        JTextField idField = new JTextField(15);
        add(idField, gdc);

        // First Name
        gdc.gridx = 0; gdc.gridy = 1;
        add(new JLabel("First Name:"), gdc);
        gdc.gridx = 1;
        JTextField firstNameField = new JTextField(15);
        add(firstNameField, gdc);

        // Last Name
        gdc.gridx = 0; gdc.gridy = 2;
        add(new JLabel("Last Name:"), gdc);
        gdc.gridx = 1;
        JTextField lastNameField = new JTextField(15);
        add(lastNameField, gdc);

        // Email
        gdc.gridx = 0; gdc.gridy = 3;
        add(new JLabel("Email:"), gdc);
        gdc.gridx = 1;
        JTextField emailField = new JTextField(15);
        add(emailField, gdc);

        // Phone
        gdc.gridx = 0; gdc.gridy = 4;
        add(new JLabel("Phone:"), gdc);
        gdc.gridx = 1;
        JTextField phoneField = new JTextField(15);
        add(phoneField, gdc);

        // Department
        gdc.gridx = 0; gdc.gridy = 5;
        add(new JLabel("Department:"), gdc);
        gdc.gridx = 1;
        JTextField departmentField = new JTextField(15);
        add(departmentField, gdc);

        // Register Button
        gdc.gridx = 0; gdc.gridy = 6; gdc.gridwidth = 2;
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            try {
                // Retrieve input values from the text fields
                String lecturerIdNo = idField.getText().trim();
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String department = departmentField.getText().trim();

                // Validate input
                if (lecturerIdNo.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
                    email.isEmpty() || phone.isEmpty() || department.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Register the lecturer
                App app = new App("lecturer_data.txt");
                Lecturer lecturer = new Lecturer("lecturer_data.txt");
                lecturer.registerLecturer(lecturerIdNo, firstName, lastName, phone, department, email);

                // Show confirmation message
                JOptionPane.showMessageDialog(this, "Lecturer Registered!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error registering lecturer!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(registerButton, gdc);

        setVisible(true);
    }
}

class UpdateLecturerForm extends JFrame {
        public UpdateLecturerForm() {
            super("Update Lecturer Details");
            setSize(1000, 700);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new GridBagLayout());
            setLocationRelativeTo(null);
    
            GridBagConstraints gdc = new GridBagConstraints();
            gdc.insets = new Insets(10, 10, 10, 10);
            gdc.fill = GridBagConstraints.HORIZONTAL;
    
            // Fields
            gdc.gridx = 0; gdc.gridy = 0;
            add(new JLabel("Lecturer ID:"), gdc);
            gdc.gridx = 1;
            JTextField idField = new JTextField(15);
            add(idField, gdc);
    
            gdc.gridx = 0; gdc.gridy = 1;
            add(new JLabel("New Name:"), gdc);
            gdc.gridx = 1;
            JTextField nameField = new JTextField(15);
            add(nameField, gdc);
    
            gdc.gridx = 0; gdc.gridy = 2;
            add(new JLabel("New Email:"), gdc);
            gdc.gridx = 1;
            JTextField emailField = new JTextField(15);
            add(emailField, gdc);
    
            // Update Button
            gdc.gridx = 0; gdc.gridy = 3; gdc.gridwidth = 2;
            JButton updateButton = new JButton("Update");
            updateButton.addActionListener(e -> {
                String lecturerId = idField.getText().trim();
                String newName = nameField.getText().trim();
                String newEmail = emailField.getText().trim();
    
                // Validate fields
                if (lecturerId.isEmpty() || newName.isEmpty() || newEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                } else {
                    // Call the updateLecturer method to update the lecturer details
                    try{
                    boolean success = new Lecturer("lecturer_data.txt").updateLecturer(lecturerId, newName, newEmail);
                     // Show success or error message
                     if (success) {
                        JOptionPane.showMessageDialog(this, "Lecturer Details Updated!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to Update Lecturer Details.");
                    }
                    }
                    catch(Exception ie){
                        ie.printStackTrace();
                    }
    
                   
                }
            });
            add(updateButton, gdc);
    
            setVisible(true);
        }
    }
    


   class AssignCourseForm extends JFrame {
        public AssignCourseForm() {
            super("Assign Lecturer Courses");
            setSize(1000, 700);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new GridBagLayout());
            setLocationRelativeTo(null);
    
            GridBagConstraints gdc = new GridBagConstraints();
            gdc.insets = new Insets(10, 10, 10, 10);
            gdc.fill = GridBagConstraints.HORIZONTAL;
    
            // Fields
            gdc.gridx = 0; gdc.gridy = 0;
            add(new JLabel("Lecturer ID:"), gdc);
            gdc.gridx = 1;
            JTextField idField = new JTextField(15);
            add(idField, gdc);
    
            gdc.gridx = 0; gdc.gridy = 1;
            add(new JLabel("Courses (comma-separated):"), gdc);
            gdc.gridx = 1;
            JTextField coursesField = new JTextField(15);
            add(coursesField, gdc);
    
            // Assign Button
            gdc.gridx = 0; gdc.gridy = 2; gdc.gridwidth = 2;
            JButton assignButton = new JButton("Assign Courses");
            assignButton.addActionListener(e -> {
                String lecturerIdNo = idField.getText().trim();
                String coursesInput = coursesField.getText().trim();
    
                // Validate fields
                if (lecturerIdNo.isEmpty() || coursesInput.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                } else {
                    // Split the comma-separated list of course codes
                    String[] coursesArray = coursesInput.split(",");
                    ArrayList<String> courseCodes = new ArrayList<>();
                    for (String course : coursesArray) {
                        courseCodes.add(course.trim());
                    }
    
                    // Call the assignCourses method to assign courses to the lecturer
                    try{
                    boolean success =new Lecturer("lecturer_data.txt").assignCourses(lecturerIdNo, courseCodes);
    
                    // Show success or error message
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Courses Assigned!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to Assign Courses. Lecturer ID might not exist.");
                    }
                }
                catch(Exception ie){
                    ie.printStackTrace();
                }
                }
            });
            add(assignButton, gdc);
    
            setVisible(true);
        }
    }

 class StudentSearchGUI {
    public StudentSearchGUI(){
        JFrame frame = new JFrame("Student Search");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panel for input
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        JLabel lblId = new JLabel("Enter Student ID:");
        lblId.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField txtId = new JTextField(15);
        txtId.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnSearch = new JButton("🔍 Search");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnSearch.setBackground(new Color(30, 144, 255));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JTextArea resultArea = new JTextArea(8, 30);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to panel
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblId, gbc);

        gbc.gridx = 1;
        panel.add(txtId, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(btnSearch, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);

       // Search Button Action
btnSearch.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String id = txtId.getText();

            // Ensure ID is entered
            if (id.isEmpty()) {
                resultArea.setText("Please enter a student ID.");
                return;
            }

            Student s = new Student();
            App app=new App("student_data.txt");
            ArrayList<String> list = s.searchStudent(id);  // Removed unnecessary App object

            // Convert list to a single string
            StringBuilder resultText = new StringBuilder();
            for (String item : list) {
                resultText.append(item).append("\n");
            }

            // Set the result text in the JTextArea
            resultArea.setText(resultText.toString());

        } catch (Exception ex) {
            ex.printStackTrace();  // Print actual error in console
            resultArea.setText("Error searching for student. Check console for details.");
        }
    }
});


        
    }
}


 class StudentResultGUI {
    public StudentResultGUI() {
        // Create frame
        JFrame frame = new JFrame("Student Course Results");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panel for input
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        JLabel lblId = new JLabel("Enter Student ID:");
        lblId.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField txtId = new JTextField(15);
        txtId.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnSearch = new JButton("🔍 Search");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnSearch.setBackground(new Color(30, 144, 255));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JTextArea resultArea = new JTextArea(8, 30);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to panel
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblId, gbc);

        gbc.gridx = 1;
        panel.add(txtId, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(btnSearch, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        // Search Button Action
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String studentId = txtId.getText();

                    // Ensure ID is entered
                    if (studentId.isEmpty()) {
                        resultArea.setText("Please enter a student ID.");
                        return;
                    }

                    Student student = new Student();
                    App app=new App("student_data.txt");
                    ArrayList<String> list = student.searchStudentResults(studentId);

                    // Convert list to a single string
                    StringBuilder resultText = new StringBuilder();
                    for (String item : list) {
                        resultText.append(item).append("\n");
                    }

                    // Set the result text in the JTextArea
                    resultArea.setText(resultText.toString());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    resultArea.setText("Error retrieving student results.");
                }
            }
        });

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }
}


 class CourseSearchGUI {
    public CourseSearchGUI() {
        // Create frame
        JFrame frame = new JFrame("Course Student Search");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panel for input
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        JLabel lblCourseId = new JLabel("Enter Course ID:");
        lblCourseId.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField txtCourseId = new JTextField(15);
        txtCourseId.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnSearch = new JButton("🔍 Search");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnSearch.setBackground(new Color(30, 144, 255));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JTextArea resultArea = new JTextArea(8, 30);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to panel
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblCourseId, gbc);

        gbc.gridx = 1;
        panel.add(txtCourseId, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(btnSearch, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        // Search Button Action
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String courseId = txtCourseId.getText();

                    // Ensure ID is entered
                    if (courseId.isEmpty()) {
                        resultArea.setText("Please enter a course ID.");
                        return;
                    }

                    Course course = new Course();
                   
                    ArrayList<String> list = course.searchRegisteredStudents(courseId);

                    // Convert list to a single string
                    StringBuilder resultText = new StringBuilder();
                    for (String item : list) {
                        resultText.append(item).append("\n");
                    }

                    // Set the result text in the JTextArea
                    resultArea.setText(resultText.toString());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    resultArea.setText("Error retrieving registered students.");
                }
            }
        });

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }
}


 class ProgrammeSearchGUI {
    public ProgrammeSearchGUI() {
        // Create frame
        JFrame frame = new JFrame("Programme Student Search");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panel for input
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        JLabel lblProgrammeId = new JLabel("Enter Programme ID:");
        lblProgrammeId.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField txtProgrammeId = new JTextField(15);
        txtProgrammeId.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnSearch = new JButton("🔍 Search");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnSearch.setBackground(new Color(34, 139, 34));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JTextArea resultArea = new JTextArea(8, 30);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to panel
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblProgrammeId, gbc);

        gbc.gridx = 1;
        panel.add(txtProgrammeId, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(btnSearch, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        // Search Button Action
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String programmeId = txtProgrammeId.getText();

                    // Ensure ID is entered
                    if (programmeId.isEmpty()) {
                        resultArea.setText("Please enter a programme ID.");
                        return;
                    }

                    Programme programme = new Programme();
                    App app=new App("programme_data.txt");
                    ArrayList<String> list = programme.searchStudentsByProgramme(app,programmeId);

                    // Convert list to a single string
                    StringBuilder resultText = new StringBuilder();
                    for (String item : list) {
                        resultText.append(item).append("\n");
                    }

                    // Set the result text in the JTextArea
                    resultArea.setText(resultText.toString());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    resultArea.setText("Error retrieving enrolled students.");
                }
            }
        });

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }
}



 class LecturerCourseGUI {
    public LecturerCourseGUI() {
        // Create frame
        JFrame frame = new JFrame("Lecturers for a Course");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panel for input
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        JLabel lblCourseId = new JLabel("Enter Course ID:");
        lblCourseId.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField txtCourseId = new JTextField(15);
        txtCourseId.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnSearch = new JButton("🔍 Search");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnSearch.setBackground(new Color(34, 139, 34));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Table setup
        String[] columnNames = {"Lecturer ID", "First Name", "Last Name", "Email"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable lecturerTable = new JTable(tableModel);
        lecturerTable.setFont(new Font("Arial", Font.PLAIN, 14));
        lecturerTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(lecturerTable);

        // No Lecturer Found Label
        JLabel lblNoLecturers = new JLabel("No lecturers assigned to this course.", JLabel.CENTER);
        lblNoLecturers.setFont(new Font("Arial", Font.ITALIC, 14));
        lblNoLecturers.setForeground(Color.RED);
        lblNoLecturers.setVisible(false); // Initially hidden

        // Add components to panel
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblCourseId, gbc);

        gbc.gridx = 1;
        panel.add(txtCourseId, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(btnSearch, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

        gbc.gridy = 3; 
        panel.add(lblNoLecturers, gbc); // Message will show if no lecturers are found

        // Search Button Action
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String courseId = txtCourseId.getText().trim();

                    // Ensure ID is entered
                    if (courseId.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please enter a Course ID.", "Input Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Course course = new Course();
                    
                    ArrayList<String[]> lecturers = course.getLecturersByCourse( courseId);

                    // Clear existing table data
                    tableModel.setRowCount(0);

                    if (lecturers.isEmpty() || (lecturers.size() == 1 && lecturers.get(0)[0].equals("No lecturers found"))) {
                        lblNoLecturers.setVisible(true);  // Show no lecturers found message
                    } else {
                        lblNoLecturers.setVisible(false); // Hide message
                        for (String[] lecturer : lecturers) {
                            tableModel.addRow(lecturer);
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error retrieving lecturers.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }
}
