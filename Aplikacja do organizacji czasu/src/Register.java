import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Register extends JDialog {

    private JPanel panel1;
    private JButton registerButton;
    private JButton loginButton;
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton cancelButton;

    public static void main(String[] args) {
//        Register register = new Register(null);
//        register.setVisible(true);
    }

    public Register(JFrame parent) {
        super(parent);
        setTitle("Register account");
        setContentPane(panel1);
        int width=550, height=350;
        setMinimumSize(new Dimension(width,height));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login login = new Login(null);
                login.setVisible(true);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
    }
    private void registerUser() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = String.valueOf(passwordField1.getPassword());
        String confirmPassword = String.valueOf(passwordField2.getPassword());

        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!password.equals(confirmPassword)){
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        //walidacja adresu email
        EmailValidator emailValidator = new EmailValidator();
        if(!emailValidator.validate(emailField.getText().trim())) {
            JOptionPane.showMessageDialog(this,
                    "Please enter correct email",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        user = addUserToDatabase(username, email, password);
        if(user != null) {
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;

    private User addUserToDatabase(String username, String email, String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/TimeApplication?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD ="";

        try(Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO users (username, email, password) VALUES (?,?,?)";
            try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                int addedRows = preparedStatement.executeUpdate();
                if (addedRows > 0) {
                    user = new User();
                    user.username = username;
                    user.email = email;
                    user.password = password;
                }
            }
        }
        catch (Exception e) {
                e.printStackTrace();
        }
        return user;
    }
}
