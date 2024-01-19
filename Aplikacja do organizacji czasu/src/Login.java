import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JDialog {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton goToRegisterButton;
    private JButton cancelButton;

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
    }
}
