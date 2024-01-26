import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Notes extends JFrame {
    private JPanel panel1;
    private JButton saveButton;
    private JTextArea textArea1;
    private JButton backButton;
    private final String DB_URL = "jdbc:mysql://localhost/TimeApplication?serverTimezone=UTC";
    private final String USERNAME = "root";
    private final String PASSWORD ="";

    public Notes(JFrame parent) {
        super("Your notes");
        if (Login.usernameLOG == null) throw new NullPointerException("Zaloguj się na konto aby otworzyć to okno");
        setContentPane(panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(450,500);

        getNotesFromDatabase(Login.user_id);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Dashboard dashboard = new Dashboard(null);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea1.getText();
                addOrUpdateNotesToDatabase(Login.user_id, text);
            }
        });

        setLocationRelativeTo(parent);
        this.setVisible(true);
    }
    public void getNotesFromDatabase(int user_id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT notes FROM notes WHERE user_id=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, user_id);
                ResultSet resultSet = preparedStatement.executeQuery();

                //dodawanie wczytanych danych do JTextArea
                if (resultSet.next()) {
                    textArea1.setText(resultSet.getString("notes"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addOrUpdateNotesToDatabase(int user_id, String text) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);) {
            //sprawdzenie czy notatki tego użytkownika już istnieją
            String sql = "SELECT notes FROM notes WHERE user_id=?";
            try(PreparedStatement checkStatement = conn.prepareStatement(sql)) {
                checkStatement.setInt(1, user_id);

                try (ResultSet resultSet = checkStatement.executeQuery()) {

                    //jeśli są już notatki w bazie danych, to je aktualizujemy
                    if (resultSet.next()) {
                        String updateSql = "UPDATE notes SET notes = ? WHERE user_id = ?";
                        try (PreparedStatement updateStatement = conn.prepareStatement(updateSql)) {
                            updateStatement.setString(1, text);
                            updateStatement.setInt(2, user_id);

                            updateStatement.executeUpdate();
                        }
                    }
                    //jeśli nie ma notatek to je dodajemy
                    else {
                        String insertSql = "INSERT INTO notes (user_id, notes) VALUES (?,?)";
                        try (PreparedStatement insertStatement = conn.prepareStatement(insertSql)) {
                            insertStatement.setInt(1, user_id);
                            insertStatement.setString(2, text);

                            insertStatement.executeUpdate();
                        }
                    }
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
