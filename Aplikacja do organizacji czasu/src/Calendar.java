import com.toedter.calendar.JCalendar; //import biblioteki zewnętrznej pobranej ze strony https://toedter.com/jcalendar/
                                        //umożliwiającej działania na kalendarzu
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendar extends JFrame {
    private Date selectedDate;
    public static String selectedFormattedDate;

    public Calendar() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Calendar");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(panel);

        JCalendar calendar = new JCalendar();
        panel.add(calendar, BorderLayout.CENTER);

        //backbutton
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dashboard dashboard = new Dashboard(null);
            }
        });

        //okbutton
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedDate = calendar.getDate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); //zmieniam format przechowywanej daty (dzień-miesiąc-rok) bez godzin
                selectedFormattedDate = dateFormat.format(selectedDate);
                JOptionPane.showMessageDialog(Calendar.this,"Wybrana data: "+ selectedFormattedDate);

                dispose();
                SpecificSelectedDay specificSelectedDay = new SpecificSelectedDay();
            }
        });

        // Dodanie listenera do monitorowania zmian w dacie
        calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getNewValue() instanceof Date) {
                    selectedDate = (Date) evt.getNewValue();
                }
            }
        });
        //stworzenie drugiego JPanel z przyciskami
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(backButton);
        buttonsPanel.add(okButton);

        panel.add(calendar, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calendar().setVisible(true);
        });
    }
}
