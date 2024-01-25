import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private JPanel panel1;
    private JButton logOutButton;
    private JLabel usernameLabel;
    private JRadioButton calendarRadioButton;
    private JRadioButton notesRadioButton;
    private JButton OKButton;


    public Dashboard(JFrame parent) {
        super("Choose what you want to do");
        if (Login.usernameLOG == null) throw new NullPointerException("Zaloguj się na konto aby otworzyć to okno");

        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,400);
        setLocationRelativeTo(parent);


        usernameLabel.setText("Hi "+ Login.usernameLOG+"!");
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login login = new Login(null);
            }
        });
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (calendarRadioButton.isSelected()) {
                    Calendar calendar = new Calendar();
                }
                else if (notesRadioButton.isSelected()) {
                    Notes notes = new Notes(null);
                }
                dispose();
            }
        });
        this.setVisible(true);
    }

}
