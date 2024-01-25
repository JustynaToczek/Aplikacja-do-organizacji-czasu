import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SpecificSelectedDay extends JFrame {
    private JPanel panel1;
    private JList list1;
    private JButton backButton;
    private JLabel dateJLabel;
    private JTextField textField1;
    private JButton enterToListButton;
    private JCheckBox checkBox1;
    private JPanel listPanel;
    private JProgressBar progressBar;
    private JPanel progresPanel;
    private JButton removeFromListButton;
    private JButton saveButton;
    private int progressBarValue;
    private String selectedText;
    private boolean isCheckboxSelected;
    private final String DB_URL = "jdbc:mysql://localhost/TimeApplication?serverTimezone=UTC";
    private final String USERNAME = "root";
    private final String PASSWORD ="";


    public static void main(String[] args) {
        SpecificSelectedDay specificSelectedDay = new SpecificSelectedDay();
      //  specificSelectedDay.setVisible(true);
    }

    public SpecificSelectedDay() {
        if (Login.usernameLOG == null || Calendar.selectedFormattedDate == null)
            throw new NullPointerException("Zaloguj się na konto i wybierz datę w kalendarzu aby otworzyć to okno");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("your plans for day "+Calendar.selectedFormattedDate);
        Dimension min = new Dimension(500,400);
        setMinimumSize(min);
        this.setVisible(true);
        dateJLabel.setText("Enter plans or events for "+Calendar.selectedFormattedDate);
        setLocationRelativeTo(null);
        progresPanel.setVisible(false);


        DefaultListModel listModel = new DefaultListModel<>();
        list1.setModel(listModel); //ustawienie modelu dla JList

        //wczytanie danych z bazy danych
        getPlansFromDatabase(Calendar.id_date, listModel);

        enterToListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField1.getText();
                listModel.addElement(text);
                textField1.setText("");

                addPlansToDatabase(Calendar.id_date,text);
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                removeFromListButton.setEnabled(true);

                selectedText = (String) list1.getSelectedValue();
                checkBox1.setText(selectedText);
                progresPanel.setVisible(true);

                getProgressBarAndCeckboxValue(selectedText, Calendar.id_date);
                progressBar.setValue(progressBarValue);
                progressBar.setString(progressBarValue + "%");
                checkBox1.setSelected(isCheckboxSelected);
            }
        });

        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);  // Włączenie wyświetlania tekstu
        progressBar.setString("0%");  // Ustawienie początkowego tekstu
        progressBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                updateProgressBarValue(e);
            }
        });
        progressBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                updateProgressBarValue(e);
            }
        });
        removeFromListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //usuwam wybrany rekord z bazy danych
                removeRecordFromDatabase(Calendar.id_date, selectedText);

                int selectedIndex = list1.getSelectedIndex();
                if (selectedIndex != -1) {
                    // Usuwam zaznaczony element z listy
                    DefaultListModel listModel = (DefaultListModel) list1.getModel();
                    listModel.remove(selectedIndex);
                } else {
                    throw new IllegalArgumentException("Niepoprawny indeks usuwanego elemntu");
                }

                if (listModel.isEmpty())
                    removeFromListButton.setEnabled(false);
            }
        });
        checkBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox1.isSelected()) {
                    progressBar.setValue(100);
                    progressBar.setString(100 + "%");
                    progressBarValue = 100;
                    checkBox1.setEnabled(false);
                }
            }
        });
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
                addProgressToDatabase(Calendar.id_date,selectedText);
                checkBox1.setEnabled(progressBar.getValue() < 100);
            }
        });
    }

    private void getPlansFromDatabase(int id_date, DefaultListModel listModel) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT to_do FROM progress WHERE id_date=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, id_date);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Dodawanie wczytanych danych do modelu listy
                while (resultSet.next()) {
                    String to_do = resultSet.getString("to_do");
                    listModel.addElement(to_do);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void getProgressBarAndCeckboxValue(String selectedText, int id_date) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT percentage, ischeckboxselected FROM progress WHERE id_date=? AND to_do=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, id_date);
                preparedStatement.setString(2, selectedText);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    progressBarValue = resultSet.getInt("percentage");
                    isCheckboxSelected = resultSet.getBoolean("ischeckboxselected");

                    checkBox1.setEnabled(progressBarValue < 100);
                }
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    private void updateProgressBarValue(MouseEvent e) {
        int progressBarWidth = progressBar.getWidth();
        int mouseX = e.getX();

        // Obliczanie wartości procentowej z zakresu 0-100
        double percent = (double) mouseX / (double) progressBarWidth * 100;

        // Ograniczenie wartości do zakresu 0-100
        percent = Math.max(0, Math.min(100, percent));

        int value = (int) percent;

        progressBar.setValue(value);
        progressBar.setString(value + "%");

        if (value == 100) {
            checkBox1.setSelected(true);
            checkBox1.setEnabled(false);
        } else {
            checkBox1.setSelected(false);
            checkBox1.setEnabled(true);
        }
    }
    public void addPlansToDatabase(int id_date, String to_do) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);) {
            String sql = "INSERT INTO progress (id_date, to_do) VALUES (?,?)";
            try(Statement stmt = conn.createStatement()) {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, id_date);
                preparedStatement.setString(2, to_do);

                preparedStatement.executeUpdate();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void addProgressToDatabase(int id_date, String to_do) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            int acutalProgressBarValue = progressBar.getValue();
            String insertSql = "UPDATE progress SET percentage = ?, ischeckboxselected = ? WHERE id_date=? AND to_do=?";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertSql)) {
                insertStatement.setInt(1, acutalProgressBarValue);
                insertStatement.setBoolean(2, checkBox1.isSelected());
                insertStatement.setInt(3, id_date);
                insertStatement.setString(4, to_do);

                insertStatement.executeUpdate();
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeRecordFromDatabase(int id_date, String to_do) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM progress WHERE id_date=? AND to_do=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, id_date);
                preparedStatement.setString(2, to_do);

                preparedStatement.executeUpdate();
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
