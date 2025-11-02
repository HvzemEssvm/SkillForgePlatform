# SkillFrorge-UML-Design
 Overview

This repository contains the UML analysis and design for the SkillForge online learning platform.
The diagrams illustrate the system’s structure and behavior, focusing on the “Enroll Course” use case.

Included Diagrams

Use Case Diagram — shows all actors (Student, Instructor, Admin) and their main interactions with the system.

Activity Diagram — models the workflow of the Enroll Course process.

Class Diagram — presents the main system classes (User, Course, Lesson, Quiz, Certificate, JsonDatabaseManager, etc.) and their relationships.

Sequence Diagram — demonstrates the message flow between objects when a student enrolls in a course

Description

SkillForge is an online platform where:

Students can browse and enroll in courses, complete lessons and quizzes, and earn certificates.

Instructors can create, upload, and manage courses.

Admins can manage users, approve courses, and monitor analytics.

All data is managed via a JSON-based system controlled by JsonDatabaseManager and accessed through service classes like CourseService and UserService.
