<div style="display: flex; justify-content: center; align-items: center;">
<img width="300" height="200" alt="logo-removebg-preview" src="https://github.com/user-attachments/assets/5fa0de4e-767e-4be8-9396-43281d8999fe" style="filter: drop-shadow(0 0 10px rgba(255,255,255,0.8));" />
<img width="300" height="300" alt="java_logo-e1539639892954-768x313-removebg-preview" src="https://github.com/user-attachments/assets/ce92548c-e9aa-4881-96ad-c7774e8ef53d" style="filter: drop-shadow(0 0 10px rgba(255,255,255,0.8));" />
</div>

### Architecture Overview

The SkillForgePlatform is a Java/Swing-based Learning Management System (LMS) prototype designed for the CC272 Programming II course at Alexandria University. The platform is built with core features, JSON-based data persistence, and comprehensive UML documentation. The primary components of the system include:

- **Students**: Can browse and enroll in courses, complete lessons and quizzes, and earn certificates.
- **Instructors**: Can create, upload, and manage courses.
- **Admins**: Can manage users, approve courses, and monitor analytics.
- **Data Management**: JSON-based system controlled by `JsonDatabaseManager` and accessed through service classes like `CourseService` and `UserService`.

### API Documentation

The SkillForgePlatform uses JSON for data persistence and does not expose a REST API. However, the following JSON files are used for data storage:

- **courses.json**: Contains course information.
- **users.json**: Contains user information.

### Configuration

The configuration for the SkillForgePlatform is managed through the `pom.xml` file. Key configurations include:

- **Java Version**: 11
- **Maven Compiler Plugin**: Version 3.11.0
- **Dependencies**: Jackson Databind for JSON processing

### Additional Diagrams
| Diagram | Description |
| ------  | :---------: |
| Use Case Diagram | shows all actors (Student, Instructor, Admin) and their main interactions with the system |
| Activity Diagram | models the workflow of the Enroll Course process |
| Class Diagram    | presents the main system classes (User, Course, Lesson, Quiz, Certificate, JsonDatabaseManager, etc.) and their relationships|
|Sequence Diagram  | demonstrates the message flow between objects when a student enrolls in a course |


