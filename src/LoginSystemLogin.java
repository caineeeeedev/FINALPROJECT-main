import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class LoginSystemLogin {

    static HashMap<String, String> accounts = new HashMap<>();
    static HashMap<String, String> usernames = new HashMap<>();

    static String loggedInUsername = "";
    static String loggedInID = "";

    public static boolean showLogin() {

        JFrame frame = new JFrame("QuitiQUIZ Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("QUITIQUIZ LOGIN");
        title.setBounds(120, 20, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title);



        JLabel idLabel = new JLabel("ID Number:");
        idLabel.setBounds(50, 70, 100, 25);
        panel.add(idLabel);

        JTextField idField = new JTextField("2026-xxxxxxx");
        idField.setBounds(150, 70, 180, 25);
        idField.setForeground(Color.GRAY);
        panel.add(idField);

        idField.addFocusListener(new java.awt.event.FocusAdapter() {

            public void focusGained(java.awt.event.FocusEvent e) {

                if (idField.getText().equals("2026-xxxxxxx")) {
                    idField.setText("");
                    idField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {

                if (idField.getText().trim().isEmpty()) {
                    idField.setText("2026-xxxxxxx");
                    idField.setForeground(Color.GRAY);
                }
            }
        });



        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 100, 25);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 180, 25);
        panel.add(passwordField);



        JButton loginButton = new JButton("LOG IN");
        loginButton.setBounds(70, 170, 110, 35);
        panel.add(loginButton);

        JButton signupButton = new JButton("SIGN UP");
        signupButton.setBounds(210, 170, 110, 35);
        panel.add(signupButton);

        final boolean[] loggedIn = {false};


        loginButton.addActionListener(e -> {

            String id = idField.getText().trim();

            String password =
                    new String(passwordField.getPassword());

            if (!id.matches("2026-\\d{7}")) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Invalid ID Number!\n\n"
                        + "Use this format:\n"
                        + "2026-xxxxxxx\n\n"
                        + "Example: 2026-1234567"
                );

                return;
            }

            if (accounts.containsKey(id)
                    && accounts.get(id).equals(password)) {

                loggedInUsername = usernames.get(id);
                loggedInID = id;

                JOptionPane.showMessageDialog(
                        frame,
                        "Login Successful!\n\n"
                        + "Welcome, " + loggedInUsername
                        + "\nID Number: " + loggedInID
                );

                loggedIn[0] = true;

                frame.dispose();

            } else {

                JOptionPane.showMessageDialog(
                        frame,
                        "Invalid ID Number or Password!"
                );
            }
        });


        signupButton.addActionListener(e -> {

            JTextField idInput =
                    new JTextField("2026-xxxxxxx");

            idInput.setForeground(Color.GRAY);

            idInput.addFocusListener(
                new java.awt.event.FocusAdapter() {

                    public void focusGained(
                            java.awt.event.FocusEvent e) {

                        if (idInput.getText()
                                .equals("2026-xxxxxxx")) {

                            idInput.setText("");
                            idInput.setForeground(Color.BLACK);
                        }
                    }

                    public void focusLost(
                            java.awt.event.FocusEvent e) {

                        if (idInput.getText()
                                .trim().isEmpty()) {

                            idInput.setText("2026-xxxxxxx");
                            idInput.setForeground(Color.GRAY);
                        }
                    }
                }
            );

            JTextField nameInput = new JTextField();

            JPasswordField passwordInput =
                    new JPasswordField();

            Object[] fields = {
                    "ID Number:", idInput,
                    "Username:", nameInput,
                    "Password:", passwordInput
            };

            int result = JOptionPane.showConfirmDialog(
                    frame,
                    fields,
                    "Create Account",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {

                String id =
                        idInput.getText().trim();

                String username =
                        nameInput.getText().trim();

                String password =
                        new String(
                            passwordInput.getPassword()
                        );



                if (id.isEmpty()
                        || username.isEmpty()
                        || password.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Please fill in all fields!"
                    );

                } else if (!id.matches("2026-\\d{7}")) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "Invalid ID Number!\n\n"
                            + "ID must follow this format:\n"
                            + "2026-xxxxxxx\n\n"
                            + "Example: 2026-1234567"
                    );

                } else if (accounts.containsKey(id)) {

                    JOptionPane.showMessageDialog(
                            frame,
                            "ID Number already registered!"
                    );

                } else {

                    accounts.put(id, password);
                    usernames.put(id, username);

                    JOptionPane.showMessageDialog(
                            frame,
                            "Account Created Successfully!"
                    );
                }
            }
        });



        frame.add(panel);
        frame.setVisible(true);

        // Wait until login window closes
        while (frame.isDisplayable()) {

            try {
                Thread.sleep(100);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                break;
            }
        }

        return loggedIn[0];
    }
}