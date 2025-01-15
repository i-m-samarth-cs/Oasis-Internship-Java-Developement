import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class OnlineExaminationSystem {
    private static HashMap<String, String> users = new HashMap<>();
    private static HashMap<String, String> profiles = new HashMap<>();
    private static String currentUser = "";
    private static JFrame frame;
    private static int questionIndex = 0;
    private static int score = 0;
    private static Timer timer;
    private static int timeRemaining = 30; // 30 seconds timer

    private static String[] questions = {
            "What is the capital of France?",
            "Which planet is known as the Red Planet?",
            "Who wrote 'Hamlet'?"
    };
    private static String[][] options = {
            {"Paris", "London", "Berlin", "Madrid"},
            {"Earth", "Venus", "Mars", "Jupiter"},
            {"Charles Dickens", "William Shakespeare", "Leo Tolstoy", "Mark Twain"}
    };
    private static int[] answers = {0, 2, 1}; // Correct answers (indices)

    public static void main(String[] args) {
        initializeUsers();
        showLoginScreen();
    }

    private static void initializeUsers() {
        // Predefined users (username -> password)
        users.put("user1", "password1");
        profiles.put("user1", "User One");
        users.put("user2", "password2");
        profiles.put("user2", "User Two");
    }

    private static void showLoginScreen() {
        frame = new JFrame("Online Examination System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30);
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 50, 200, 30);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 30);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 30);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 150, 100, 30);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (users.containsKey(username) && users.get(username).equals(password)) {
                currentUser = username;
                JOptionPane.showMessageDialog(frame, "Login Successful. Welcome, " + profiles.get(username) + "!");
                frame.dispose();
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void showMainMenu() {
        frame = new JFrame("Online Examination System - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton updateProfileButton = new JButton("Update Profile & Password");
        JButton startExamButton = new JButton("Start Exam");
        JButton logoutButton = new JButton("Logout");

        updateProfileButton.addActionListener(e -> {
            frame.dispose();
            showUpdateProfileScreen();
        });

        startExamButton.addActionListener(e -> {
            frame.dispose();
            showExamScreen();
        });

        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Logged out successfully.");
            frame.dispose();
            showLoginScreen();
        });

        panel.add(updateProfileButton);
        panel.add(startExamButton);
        panel.add(logoutButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void showUpdateProfileScreen() {
        frame = new JFrame("Update Profile & Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("New Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        panel.add(nameLabel);

        JTextField nameField = new JTextField(profiles.get(currentUser));
        nameField.setBounds(150, 50, 200, 30);
        panel.add(nameField);

        JLabel passwordLabel = new JLabel("New Password:");
        passwordLabel.setBounds(50, 100, 100, 30);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 30);
        panel.add(passwordField);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(150, 150, 100, 30);
        panel.add(updateButton);

        updateButton.addActionListener(e -> {
            String newName = nameField.getText();
            String newPassword = new String(passwordField.getPassword());

            profiles.put(currentUser, newName);
            users.put(currentUser, newPassword);

            JOptionPane.showMessageDialog(frame, "Profile updated successfully!");
            frame.dispose();
            showMainMenu();
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void showExamScreen() {
        frame = new JFrame("Online Examination - Exam");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel questionLabel = new JLabel();
        questionLabel.setBounds(50, 50, 500, 30);
        panel.add(questionLabel);

        ButtonGroup optionsGroup = new ButtonGroup();
        JRadioButton[] optionButtons = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setBounds(50, 100 + (i * 30), 400, 30);
            optionsGroup.add(optionButtons[i]);
            panel.add(optionButtons[i]);
        }

        JButton nextButton = new JButton("Next");
        nextButton.setBounds(400, 300, 100, 30);
        panel.add(nextButton);

        JLabel timerLabel = new JLabel("Time Remaining: " + timeRemaining + " seconds");
        timerLabel.setBounds(50, 300, 200, 30);
        panel.add(timerLabel);

        loadQuestion(questionLabel, optionButtons);

        timer = new Timer(1000, e -> {
            timeRemaining--;
            timerLabel.setText("Time Remaining: " + timeRemaining + " seconds");
            if (timeRemaining == 0) {
                timer.stop();
                JOptionPane.showMessageDialog(frame, "Time's up! Exam auto-submitted.");
                frame.dispose();
                showScore();
            }
        });
        timer.start();

        nextButton.addActionListener(e -> {
            timer.stop();
            for (int i = 0; i < optionButtons.length; i++) {
                if (optionButtons[i].isSelected() && i == answers[questionIndex]) {
                    score++;
                }
            }
            questionIndex++;
            if (questionIndex < questions.length) {
                timeRemaining = 30;
                loadQuestion(questionLabel, optionButtons);
                timer.start();
            } else {
                JOptionPane.showMessageDialog(frame, "Exam completed!");
                frame.dispose();
                showScore();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void loadQuestion(JLabel questionLabel, JRadioButton[] optionButtons) {
        questionLabel.setText("Q" + (questionIndex + 1) + ": " + questions[questionIndex]);
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(options[questionIndex][i]);
        }
    }

    private static void showScore() {
        JOptionPane.showMessageDialog(null, "Your score: " + score + "/" + questions.length);
        score = 0;
        questionIndex = 0;
        showMainMenu();
    }
}
