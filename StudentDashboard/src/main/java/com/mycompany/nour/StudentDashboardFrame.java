package com.mycompany.nour;

import javax.swing.*;
import java.awt.*;
import com.mycompany.CourseManagement.Course;
import com.mycompany.CourseManagement.ProgressManager;
import com.mycompany.UserAccountManagement.Student;
import com.mycompany.nour.CourseDetailsFrame;
import java.util.ArrayList;

public class StudentDashboardFrame extends JFrame {
    private Student student;
    private JTabbedPane tabbedPane;
    private JPanel enrolledCoursesPanel;
    private JPanel browseCoursesPanel;

    public StudentDashboardFrame(Student student) {
        this.student = student;
        initializeFrame();
        createEnrolledCoursesTab();
        createBrowseCoursesTab();
    }

    private void initializeFrame() {
        setTitle("Student Dashboard - Skill Forge");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();
        add(tabbedPane);
    }

    private void createEnrolledCoursesTab() {
        enrolledCoursesPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Enrolled Courses", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        enrolledCoursesPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));

        try {
            ArrayList<Course> enrolledCourses = student.getMyEnrolledCourses();
            if (enrolledCourses.isEmpty()) {
                coursesPanel.add(new JLabel("No Enrolled Courses"));
            } else {
                for (Course course : enrolledCourses) {
                    JPanel courseCard = createCourseCard(course);
                    coursesPanel.add(courseCard);
                    coursesPanel.add(Box.createVerticalStrut(10));
                }
            }
        } catch (Exception e) {
            coursesPanel.add(new JLabel("Error loading enrolled courses: " + e.getMessage()));
        }

        JScrollPane scrollPane = new JScrollPane(coursesPanel);
        enrolledCoursesPanel.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("My Courses", enrolledCoursesPanel);
    }

    private void createBrowseCoursesTab() {
        browseCoursesPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Available Courses", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        browseCoursesPanel.add(titleLabel, BorderLayout.NORTH);

        JTable coursesTable = new JTable();

        try {
            ArrayList<Course> allCourses = student.viewAvailableCourses();
            Object[][] data = new Object[allCourses.size()][3];

            for (int i = 0; i < allCourses.size(); i++) {
                Course c = allCourses.get(i);
                data[i][0] = c.getTitle();
                data[i][1] = c.getInstructorId();
                data[i][2] = c.getDescription();
            }

            coursesTable.setModel(new javax.swing.table.DefaultTableModel(
                data,
                new String[]{"title", "instructor", "description"}
            ));
        } catch (Exception e) {
            browseCoursesPanel.add(new JLabel("Error in loading courses: " + e.getMessage()));
        }

        JScrollPane scrollPane = new JScrollPane(coursesTable);
        browseCoursesPanel.add(scrollPane, BorderLayout.CENTER);

        JButton enrollButton = new JButton("Enrollment");
        enrollButton.addActionListener(e -> {
            int selectedRow = coursesTable.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    ArrayList<Course> availableCourses = student.viewAvailableCourses();
                    student.enroll(availableCourses.get(selectedRow).getCourseId());
                    JOptionPane.showMessageDialog(this, "Enrollment successful");
                    refreshEnrolledCoursesTab();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Enrollment failed" + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "choose a course to enrollً");
            }
        });

        browseCoursesPanel.add(enrollButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Browse Available Courses", browseCoursesPanel);
    }

private JPanel createCourseCard(Course course) {
    JPanel card = new JPanel(new BorderLayout());
    card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    card.setBackground(Color.WHITE);
    card.setPreferredSize(new Dimension(300, 120));

    JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
    JLabel titleLabel = new JLabel(course.getTitle());
    titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
    JLabel descLabel = new JLabel(course.getDescription());
    descLabel.setFont(new Font("Arial", Font.PLAIN, 12));

    infoPanel.add(titleLabel);
    infoPanel.add(descLabel);

    card.add(infoPanel, BorderLayout.CENTER);

    JButton detailsButton = new JButton("Show Details");
    detailsButton.addActionListener(e -> {
        new CourseDetailsFrame(course, student).setVisible(true);
    });

    card.add(detailsButton, BorderLayout.SOUTH);
    return card;
}

 private void refreshEnrolledCoursesTab() {

    enrolledCoursesPanel.removeAll();

    JLabel titleLabel = new JLabel("Enrollment courses", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    enrolledCoursesPanel.add(titleLabel, BorderLayout.NORTH);

    JPanel coursesPanel = new JPanel();
    coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));

    try {
        ArrayList<Course> enrolledCourses = student.getMyEnrolledCourses();
        if (enrolledCourses.isEmpty()) {
            coursesPanel.add(new JLabel("No Enrolled Courses"));
        } else {
            for (Course course : enrolledCourses) {
                JPanel courseCard = createCourseCard(course);
                coursesPanel.add(courseCard);
                coursesPanel.add(Box.createVerticalStrut(10));
            }
        }
    } catch (Exception e) {
        coursesPanel.add(new JLabel("Error in loading courses"));
    }

    JScrollPane scrollPane = new JScrollPane(coursesPanel);
    enrolledCoursesPanel.add(scrollPane, BorderLayout.CENTER);
    
 
    enrolledCoursesPanel.revalidate();
    enrolledCoursesPanel.repaint();
}
}