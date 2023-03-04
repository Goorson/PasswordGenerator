import java.util.Random;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.*;

public class PasswordGenerator extends JFrame {

    private JTextField passwordField;
    private JSlider lengthSlider;
    private JCheckBox uppercaseBox, lowercaseBox, numbersBox, symbolsBox;

    private final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private final String NUMBERS = "0123456789";
    private final String SYMBOLS = "!@#$%^&*()-_=+[]{}\\|;:'\",.<>/?";

    private final int MIN_LENGTH = 6;
    private final int MAX_LENGTH = 20;

    private final Color WEAK_COLOR = new Color(255, 50, 50);
    private final Color MEDIUM_COLOR = new Color(255, 150, 50);
    private final Color STRONG_COLOR = new Color(50, 255, 50);

    public PasswordGenerator() {
        setTitle("Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JTextField(20);
        passwordField.setEditable(false);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lengthLabel = new JLabel("Length: ");
        lengthSlider = new JSlider(MIN_LENGTH, MAX_LENGTH, MIN_LENGTH);
        lengthSlider.setMajorTickSpacing(2);
        lengthSlider.setMinorTickSpacing(1);
        lengthSlider.setPaintTicks(true);
        lengthSlider.setPaintLabels(true);
        lengthPanel.add(lengthLabel);
        lengthPanel.add(lengthSlider);

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uppercaseBox = new JCheckBox("Uppercase Letters");
        lowercaseBox = new JCheckBox("Lowercase Letters");
        numbersBox = new JCheckBox("Numbers");
        symbolsBox = new JCheckBox("Symbols");
        optionsPanel.add(uppercaseBox);
        optionsPanel.add(lowercaseBox);
        optionsPanel.add(numbersBox);
        optionsPanel.add(symbolsBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generateButton = new JButton("Generate Password");
        generateButton.addActionListener(e -> generatePassword());
        JButton copyButton = new JButton("Copy to Clipboard");
        copyButton.addActionListener(e -> copyPasswordToClipboard());
        buttonPanel.add(generateButton);
        buttonPanel.add(copyButton);

        JPanel middlePanel = new JPanel(new GridLayout(2,1));
        middlePanel.add(lengthPanel);
        middlePanel.add(optionsPanel);


        add(passwordPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void generatePassword() {
        int length = lengthSlider.getValue();
        boolean uppercase = uppercaseBox.isSelected();
        boolean lowercase = lowercaseBox.isSelected();
        boolean numbers = numbersBox.isSelected();
        boolean symbols = symbolsBox.isSelected();

        String password = "";
        String characters = "";
        Random random = new Random();

        if (uppercase) {
            characters += UPPER_CASE;
        }
        if (lowercase) {
            characters += LOWER_CASE;
        }
        if (numbers) {
            characters += NUMBERS;
        }
        if (symbols) {
            characters += SYMBOLS;
        }

        if (!characters.isEmpty()) {
            for (int i = 0; i < length; i++) {
                password += characters.charAt(random.nextInt(characters.length()));
            }
        }

        passwordField.setText(password);
        showPasswordStrength(password);
    }

    private void copyPasswordToClipboard() {
        String password = passwordField.getText();
        if (!password.isEmpty()) {
            StringSelection stringSelection = new StringSelection(password);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }

    private int getPasswordStrength(String password) {
        int score = 0;

        if (password.length() >= 8) {
            score++;
        }
        if (password.matches("(?=.*[A-Z]).*")) {
            score++;
        }
        if (password.matches("(?=.*[a-z]).*")) {
            score++;
        }
        if (password.matches("(?=.*\\d).*")) {
            score++;
        }
        if (password.matches("(?=.*[@#$%^&+=]).*")) {
            score++;
        }

        return score;
    }

    void showPasswordStrength(String password){
        int strength = getPasswordStrength(password);
        if (strength<=2){
            passwordField.setBackground(WEAK_COLOR);
        }
        else if (strength<=4 && strength>2){
            passwordField.setBackground(MEDIUM_COLOR);
        }
        else{
            passwordField.setBackground(STRONG_COLOR);
        }
    }

    public static void main(String[] args) {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        passwordGenerator.setVisible(true);
    }
}
