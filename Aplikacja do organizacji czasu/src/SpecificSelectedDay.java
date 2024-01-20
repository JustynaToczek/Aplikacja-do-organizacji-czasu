import javax.swing.*;

public class SpecificSelectedDay extends JFrame {
    private JPanel panel1;
    private JList list1;
    private JButton backButton;
    private JLabel dateJLabel;

    public static void main(String[] args) {
        SpecificSelectedDay specificSelectedDay = new SpecificSelectedDay();
      //  specificSelectedDay.setVisible(true);
    }

    public SpecificSelectedDay() {
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("your plans for day "+Calendar.selectedFormattedDate);
        setSize(400,600);
        this.setVisible(true);

        dateJLabel.setText("Enter plans or events for "+Calendar.selectedFormattedDate);

       // pack();
        setLocationRelativeTo(null);
    }
}
