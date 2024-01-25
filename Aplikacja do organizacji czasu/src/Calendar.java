import com.toedter.calendar.JCalendar; //import biblioteki zewnętrznej pobranej ze strony https://toedter.com/jcalendar/
                                        //umożliwiającej działania na kalendarzu
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendar extends JFrame {
    private Date selectedDate;
    public static String selectedFormattedDate;
    public static int id_date;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calendar().setVisible(true);
        });
    }
    public Calendar() {
        initComponents();
    }
    private void initComponents() {
        if (Login.usernameLOG == null) throw new NullPointerException("Zaloguj się na konto aby otworzyć to okno");
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
                addInfoToDatabase(Login.user_id,selectedFormattedDate);

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
    public void addInfoToDatabase(int user_id, String date) {
        final String DB_URL = "jdbc:mysql://localhost/TimeApplication?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            //Sprawdzam, czy wybrana data dla konkretnego użytkownika już istnieje
            String checkSql = "SELECT id_date FROM date WHERE date=? AND user_id=?";
            try (PreparedStatement checkStatement = conn.prepareStatement(checkSql)) {
                checkStatement.setString(1, date);
                checkStatement.setInt(2, user_id);
                ResultSet resultSet = checkStatement.executeQuery();

                if (!resultSet.next()) {
                    //Jeśli data nie istnieje, dodaję nowy wiersz
                    String insertSql = "INSERT INTO date (user_id, date) VALUES (?,?)";
                    try (PreparedStatement insertStatement = conn.prepareStatement(insertSql)) {
                        insertStatement.setInt(1, user_id);
                        insertStatement.setString(2, date);

                        insertStatement.executeUpdate();
                    }
                }
                // Pobieram info o id_date
                String sql2 = "SELECT * FROM date WHERE date=? AND user_id=?";
                try (PreparedStatement preparedStatement1 = conn.prepareStatement(sql2)) {
                    preparedStatement1.setString(1, date);
                    preparedStatement1.setInt(2, user_id);
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    if (resultSet1.next()) {
                        id_date = resultSet1.getInt("id_date");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
