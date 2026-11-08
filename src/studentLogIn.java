import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class studentLogIn {
    private JTextField txtStudentUsername;
    private JPasswordField txtStudentPassword;
    private JButton btnStudentLogIn;
    private JButton btnStudentLogReturn;
    private JPanel studentLoginPanel;
    private JPanel usernamePanel;
    private JPanel passwordPanel;
    private JLabel lblTitle;
    private JPanel loginCardPanel;
    private JLabel lblSignUpLink;
    private JLabel lblWelcome;
    private JPanel goldLinePanel;
    private JLabel lblUniversityLogo;

    public JTextField getTxtStudentUsername() {
        return txtStudentUsername;
    }

    public JPasswordField getTxtStudentPassword() {
        return txtStudentPassword;
    }

    public JButton getBtnStudentLogIn() {
        return btnStudentLogIn;
    }

    public JPanel getUsernamePanel() {
        return usernamePanel;
    }

    public JPanel getPasswordPanel() {
        return passwordPanel;
    }

    public JLabel getLblTitle() {
        return lblTitle;
    }

    public JPanel getLoginCardPanel() {
        return loginCardPanel;
    }

    public JLabel getLblSignUpLink() {
        return lblSignUpLink;
    }

    public JLabel getLblWelcome() {
        return lblWelcome;
    }

    public JPanel getGoldLinePanel() {
        return goldLinePanel;
    }

    public JLabel getLblUniversityLogo() {
        return lblUniversityLogo;
    }

    public studentLogIn() {
        new StudentLogInUI(this);

        btnStudentLogReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = new JFrame("MAIN");
                    Main form = new Main();

                    JPanel panel = form.getMainPanel();
                    if (panel == null) {
                        JOptionPane.showMessageDialog(null,
                                "Error: getMainPanel returned null. Check Main.java!",
                                "Null Panel Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    frame.setContentPane(panel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(490, 720);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(studentLoginPanel);
                    if (currentFrame != null) {
                        currentFrame.dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnStudentLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUsername = txtStudentUsername.getText().trim();
                String inputPassword = txtStudentPassword.getText().trim();

                String query = """
                        SELECT * FROM students_or_staff
                        WHERE username = ? AND password = ?
                        """;

                try (
                    Connection con = DatabaseConnection.getConnection();
                    PreparedStatement stmt = con.prepareStatement(query);
                ) {
                    stmt.setString(1, inputUsername);
                    stmt.setString(2, inputPassword);

                    ResultSet rs = stmt.executeQuery();

                    if (!(rs.next())) {
                        JOptionPane.showMessageDialog(null,
                                "Invalid username or password.",
                                "ERROR",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(null, "Login Successful!");

                    // if student have reserve already, it will show the receipt form
                    Main.loggedInUsername = inputUsername;

                    if (hasExistingReservation(inputUsername)) {
                        String checkQuery = """
                                SELECT reservation_id, driver_username, bus_num,
                                        seat_num, passenger_name, payment_method,
                                        payment_provider, account_number, 
                                        amount_paid, reserved_at
                                FROM transaction_history
                                WHERE student_username = ?
                                LIMIT 1
                                """;

                        try (
                            Connection conn = DatabaseConnection.getConnection();
                            PreparedStatement stmtt = conn.prepareStatement(checkQuery)
                        ) {
                            stmtt.setString(1, inputUsername);

                            try (ResultSet rss = stmtt.executeQuery()) {
                                if (rss.next()) {
                                    Receipt receipt = new Receipt(
                                            rss.getInt("reservation_id"),
                                            rss.getString("driver_username"),
                                            rss.getInt("bus_num"),
                                            rss.getString("seat_num"),
                                            rss.getString("passenger_name"),
                                            rss.getString("payment_method"),
                                            rss.getString("payment_provider"),
                                            rss.getString("account_number"),
                                            rss.getInt("amount_paid"),
                                            rss.getString("reserved_at")
                                    );

                                    JFrame frame = new JFrame("YOUR RECEIPT");

                                    frame.setContentPane(receipt.getReceiptPanel());
                                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    frame.setSize(490, 720);
                                    frame.setLocationRelativeTo(null);
                                    frame.setVisible(true);

                                    JFrame currentFrame = (JFrame)
                                            SwingUtilities.getWindowAncestor(studentLoginPanel);

                                    if (currentFrame != null) {
                                        currentFrame.dispose();
                                    }
                                }
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                        }
                    } else {
                        JFrame frame = new JFrame("AVAILABLE BUSES");

                        BusesAvailable form = new BusesAvailable();

                        JPanel panel = form.getAvailableBusPanel();

                        frame.setContentPane(panel);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setSize(490, 720);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);

                        JFrame currentFrame = (JFrame)
                                SwingUtilities.getWindowAncestor(studentLoginPanel);

                        if (currentFrame != null) {
                            currentFrame.dispose();
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
            }
        });
    }

    private boolean hasExistingReservation(String username) throws SQLException {
        String query = """
                SELECT reservation_id
                FROM transaction_history
                WHERE student_username = ?
                LIMIT 1
                """;

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
        ) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public JPanel getStudentLoginPanel() {
        return studentLoginPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        btnStudentLogIn = new RoundedButton("LOG IN");
        btnStudentLogReturn = new RoundedButton("RETURN");
        loginCardPanel = new RoundedPanel();


        txtStudentUsername =
                new PlaceholderTextField("Enter your name");
    }
}
