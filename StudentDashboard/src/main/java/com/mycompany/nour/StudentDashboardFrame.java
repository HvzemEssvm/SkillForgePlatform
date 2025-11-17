package com.mycompany.nour;

import javax.swing.*;
import java.awt.*;
import com.mycompany.CourseManagement.Course;
import com.mycompany.UserAccountManagement.Student;
import com.mycompany.frontend.MainFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class StudentDashboardFrame extends JFrame {
    private Student student;
    private JTabbedPane tabbedPane;
    private JPanel enrolledCoursesPanel;
    private JPanel browseCoursesPanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel dashboardPanel;

    public StudentDashboardFrame(Student student) {
        this.student = student;
        initializeFrame();
        createDashboard();
    }

    private void initializeFrame() {
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                JOptionPane.showMessageDialog(mainFrame,"Logged Out Successfully","Successful Operation!", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        setTitle("Student Dashboard - Skill Forge");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);
    }

    private void createDashboard() {
        dashboardPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();
        dashboardPanel.add(tabbedPane);

        createEnrolledCoursesTab();
        createBrowseCoursesTab();

        mainPanel.add(dashboardPanel, "DASHBOARD");
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    private void showCourseDetails(Course course) {
        CourseDetailsFrame detailsPanel = new CourseDetailsFrame(course, student, () -> {
            cardLayout.show(mainPanel, "DASHBOARD");
        });
        mainPanel.add(detailsPanel, "DETAILS");
        cardLayout.show(mainPanel, "DETAILS");
    }

    private void createEnrolledCoursesTab() {
        enrolledCoursesPanel = new JPanel(new BorderLayout());
        enrolledCoursesPanel.setBackground(new Color(245, 245, 250));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel("My Enrolled Courses", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        enrolledCoursesPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));
        coursesPanel.setBackground(new Color(245, 245, 250));
        coursesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        try {
            ArrayList<Course> enrolledCourses = student.getMyEnrolledCourses();
            if (enrolledCourses.isEmpty()) {
                JLabel emptyLabel = new JLabel("No Enrolled Courses Yet");
                emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                emptyLabel.setForeground(Color.GRAY);
                emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                coursesPanel.add(Box.createVerticalGlue());
                coursesPanel.add(emptyLabel);
                coursesPanel.add(Box.createVerticalGlue());
            } else {
                for (Course course : enrolledCourses) {
                    JPanel courseCard = createCourseCard(course);
                    courseCard.setAlignmentX(Component.CENTER_ALIGNMENT);
                    coursesPanel.add(courseCard);
                    coursesPanel.add(Box.createVerticalStrut(15));
                }
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error loading enrolled courses: " + e.getMessage());
            errorLabel.setForeground(Color.RED);
            errorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            coursesPanel.add(errorLabel);
        }

        JScrollPane scrollPane = new JScrollPane(coursesPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        enrolledCoursesPanel.add(scrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("My Courses", enrolledCoursesPanel);
    }

    private void createBrowseCoursesTab() {
        browseCoursesPanel = new JPanel(new BorderLayout());
        browseCoursesPanel.setBackground(new Color(245, 245, 250));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel("Browse Available Courses", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        browseCoursesPanel.add(headerPanel, BorderLayout.NORTH);

        JTable coursesTable = new JTable();
        coursesTable.setFont(new Font("Arial", Font.PLAIN, 13));
        coursesTable.setRowHeight(35);
        coursesTable.setSelectionBackground(new Color(70, 130, 180));
        coursesTable.setSelectionForeground(Color.WHITE);
        coursesTable.setGridColor(new Color(230, 230, 230));

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
                new String[]{"Title", "Instructor", "Description"}
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });

            coursesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
            coursesTable.getTableHeader().setBackground(new Color(70, 130, 180));
            coursesTable.getTableHeader().setForeground(Color.WHITE);
            coursesTable.getTableHeader().setReorderingAllowed(false);
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error in loading courses: " + e.getMessage());
            errorLabel.setForeground(Color.RED);
            errorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            browseCoursesPanel.add(errorLabel, BorderLayout.CENTER);
        }

        JScrollPane scrollPane = new JScrollPane(coursesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        browseCoursesPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        buttonPanel.setBackground(new Color(245, 245, 250));

        JButton enrollButton = new JButton("Enroll in Selected Course");
        enrollButton.setFont(new Font("Arial", Font.BOLD, 14));
        enrollButton.setBackground(new Color(40, 167, 69));
        enrollButton.setForeground(Color.WHITE);
        enrollButton.setFocusPainted(false);
        enrollButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        enrollButton.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));

        enrollButton.addActionListener(e -> {
            int selectedRow = coursesTable.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    ArrayList<Course> availableCourses = student.viewAvailableCourses();
                    student.enroll(availableCourses.get(selectedRow).getCourseId());
                    JOptionPane.showMessageDialog(this, "Enrollment successful!");
                    refreshEnrolledCoursesTab();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Enrollment failed: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course to enroll");
            }
        });

        buttonPanel.add(enrollButton);
        browseCoursesPanel.add(buttonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Browse Courses", browseCoursesPanel);
    }

private JPanel createCourseCard(Course course) {
    JPanel card = new JPanel(new BorderLayout(15, 15));
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));
    card.setBackground(Color.WHITE);
    card.setMaximumSize(new Dimension(700, 150));
    card.setPreferredSize(new Dimension(700, 150));

    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
    infoPanel.setBackground(Color.WHITE);

    JLabel titleLabel = new JLabel(course.getTitle());
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    titleLabel.setForeground(new Color(40, 40, 40));
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel descLabel = new JLabel("<html>" + course.getDescription() + "</html>");
    descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    descLabel.setForeground(new Color(100, 100, 100));
    descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    infoPanel.add(titleLabel);
    infoPanel.add(Box.createVerticalStrut(10));
    infoPanel.add(descLabel);

    card.add(infoPanel, BorderLayout.CENTER);

    JButton detailsButton = new JButton("View Details →");
    detailsButton.setFont(new Font("Arial", Font.BOLD, 13));
    detailsButton.setBackground(new Color(70, 130, 180));
    detailsButton.setForeground(Color.WHITE);
    detailsButton.setFocusPainted(false);
    detailsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    detailsButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
    detailsButton.addActionListener(e -> {
        showCourseDetails(course);
    });

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    buttonPanel.setBackground(Color.WHITE);
    buttonPanel.add(detailsButton);

    card.add(buttonPanel, BorderLayout.EAST);
    return card;
}

 private void refreshEnrolledCoursesTab() {
    enrolledCoursesPanel.removeAll();

    // Header panel
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(Color.WHITE);
    headerPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));

    JLabel titleLabel = new JLabel("My Enrolled Courses", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    titleLabel.setForeground(new Color(50, 50, 50));
    headerPanel.add(titleLabel, BorderLayout.CENTER);
    enrolledCoursesPanel.add(headerPanel, BorderLayout.NORTH);

    JPanel coursesPanel = new JPanel();
    coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));
    coursesPanel.setBackground(new Color(245, 245, 250));
    coursesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    try {
        ArrayList<Course> enrolledCourses = student.getMyEnrolledCourses();
        if (enrolledCourses.isEmpty()) {
            JLabel emptyLabel = new JLabel("No Enrolled Courses Yet");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            coursesPanel.add(Box.createVerticalGlue());
            coursesPanel.add(emptyLabel);
            coursesPanel.add(Box.createVerticalGlue());
        } else {
            for (Course course : enrolledCourses) {
                JPanel courseCard = createCourseCard(course);
                courseCard.setAlignmentX(Component.CENTER_ALIGNMENT);
                coursesPanel.add(courseCard);
                coursesPanel.add(Box.createVerticalStrut(15));
            }
        }
    } catch (Exception e) {
        JLabel errorLabel = new JLabel("Error loading courses");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        coursesPanel.add(errorLabel);
    }

    JScrollPane scrollPane = new JScrollPane(coursesPanel);
    scrollPane.setBorder(null);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    enrolledCoursesPanel.add(scrollPane, BorderLayout.CENTER);

    enrolledCoursesPanel.revalidate();
    enrolledCoursesPanel.repaint();
}
}