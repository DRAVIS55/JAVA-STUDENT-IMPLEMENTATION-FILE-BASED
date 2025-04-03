-- Create the database
CREATE DATABASE student_management;

-- Switch to the newly created database
USE student_management;

-- Programme Table
CREATE TABLE Programme (
    programme_id INT AUTO_INCREMENT PRIMARY KEY,
    programme_name VARCHAR(100) NOT NULL UNIQUE,
    programme_code VARCHAR(20) NOT NULL UNIQUE
);

-- Student Table
CREATE TABLE Student (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE,
    gender VARCHAR(10) NOT NULL,
    programme_id INT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (programme_id) REFERENCES Programme(programme_id) ON DELETE CASCADE
);

-- Lecturer Table
CREATE TABLE Lecturer (
    lecturer_id INT AUTO_INCREMENT PRIMARY KEY,
    lectureIdNo VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE,
    department VARCHAR(100),
    hire_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Course Table
CREATE TABLE Course (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL UNIQUE,
    course_code VARCHAR(20) NOT NULL UNIQUE,
    credit_hours INT
);

-- Programme_Courses Table (Many-to-Many Relationship)
CREATE TABLE Programme_Courses (
    programme_id INT NOT NULL,
    course_id INT NOT NULL,
    PRIMARY KEY (programme_id, course_id),
    FOREIGN KEY (programme_id) REFERENCES Programme(programme_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE
);

-- Lecturer_Courses Table (Many-to-Many Relationship)
CREATE TABLE Lecturer_Courses (
    lecturer_id INT NOT NULL,
    course_id INT NOT NULL,
    PRIMARY KEY (lecturer_id, course_id),
    FOREIGN KEY (lecturer_id) REFERENCES Lecturer(lecturer_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE
);
