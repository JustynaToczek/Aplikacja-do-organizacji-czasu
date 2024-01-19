import javax.swing.*;

public class Dashboard extends JFrame {
    private JPanel panel1;
    private JButton logOutButton;
    private JLabel usernameLabel;
    private JRadioButton calendarRadioButton;
    private JRadioButton yourGoalsRadioButton;
    private JRadioButton toDoListRadioButton;
    private JRadioButton notesRadioButton;

    public static void main(String[] args) {
        Dashboard dashboard = new Dashboard(null);
    }

    public Dashboard(JFrame parent) {
        super("Choose what you want to do");
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,450);
        setLocationRelativeTo(parent);
        this.setVisible(true);

        usernameLabel.setText("Hi "+ Login.usernameLOG+"!");
    }

}
