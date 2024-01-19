import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JDialog {
    private JPanel panel1;
    private JTextField emailField;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton goToRegisterButton;
    private JButton cancelButton;
    public static String usernameLOG; //zmienna potrzebna do wyswietlenia w dashboard

    public static void main(String[] args) {
        Login login = new Login(null);
        login.setVisible(true);
    }

    public Login(JFrame parent) {
        super(parent);
        setTitle("Log in to your account");
        setContentPane(panel1);
        int width=500, height=400;
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
        goToRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Register register = new Register(null);
                register.setVisible(true);
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = String.valueOf(passwordField1.getPassword());

                user = getAutenticateUser(email,password);

                if(user != null) {
                    dispose();
                    Dashboard dashboard = new Dashboard(null);
                    dashboard.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(
                            Login.this,
                            "Email or password invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }
    public User user;
    private User getAutenticateUser(String email,String password) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/TimeApplication?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD ="";

        try {
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user = new User();
                user.username = resultSet.getString("username");
                user.email = resultSet.getString("email");
                user.password = resultSet.getString("password");

                usernameLOG = user.username;
            }

            stmt.close();
            conn.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
