import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OnlinePayment {
    private JPanel onlinePaymentPanel;
    private JComboBox cmbEWalletBank;
    private JTextField txtAccNum;
    private JButton btnConfirmTransac;
    private JTextField txtAmount;
    private JButton btnHeader;
    private JPanel paymentCardPanel;
    private JSeparator line;
    private JSeparator line2;
    private JPanel headerPanel;
    private int busNum;
    private String seatNum;
    private String passengerName;

    public JSeparator getLine() {
        return line;
    }

    public JSeparator getLine2() {
        return line2;
    }

    public JButton getBtnHeader() {
        return btnHeader;
    }

    Payment payment = new BusFarePayment(Main.userType);

    public OnlinePayment(int busNum, String name, String seatNum) {
        this.busNum = busNum;
        this.passengerName = name;
        this.seatNum = seatNum;

        new OnlinePaymentUI(this);

        payment.calculateAmount();
        double amountPaid = payment.getAmount();
        txtAmount.setText("P" + String.format("%.2f", amountPaid));

        btnConfirmTransac.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String payment = (String)cmbEWalletBank.getSelectedItem();
                String accNum = txtAccNum.getText().trim();

                if (name.isEmpty() || accNum.isEmpty()) {
                    JOptionPane.showMessageDialog(onlinePaymentPanel,
                            "Name or Account number cannot be empty.");
                    return;
                }

                boolean saved = TransactionHistory.saveTransaction(
                        busNum,
                        seatNum,
                        name,
                        "Online Payment",
                        payment,
                        accNum,
                        amountPaid
                );

                if (!saved) {
                    JOptionPane.showMessageDialog(
                            onlinePaymentPanel,
                            "Failed to save transaction."
                    );
                    return;
                }

                JOptionPane.showMessageDialog(
                        onlinePaymentPanel,
                        "Transaction recorded successfully."
                );

                String query = """
                            SELECT reservation_id, driver_username, bus_num, seat_num, passenger_name, payment_method, payment_provider, account_number, amount_paid, reserved_at
                            FROM transaction_history
                            WHERE bus_num = ?
                            AND seat_num = ?
                            AND passenger_name = ?
                            """;

                try (
                        Connection con = DatabaseConnection.getConnection();
                        PreparedStatement stmt = con.prepareStatement(query);
                ) {
                    stmt.setInt(1, busNum);
                    stmt.setString(2, seatNum);
                    stmt.setString(3, passengerName);

                    try (ResultSet rs = stmt.executeQuery()) {

                        if (rs.next()) {

                            int reservationId = rs.getInt("reservation_id");
                            String driverUserName = rs.getString("driver_username");
                            int savedBusNum = rs.getInt("bus_num");
                            String savedSeatNum = rs.getString("seat_num");
                            String savedPassengerName = rs.getString("passenger_name");
                            String paymentMethod2 = rs.getString("payment_method");
                            String paymentProvider = rs.getString("payment_provider");
                            String accountNumber = rs.getString("account_number");
                            int amountPaid2 = rs.getInt("amount_paid");
                            String reservedAt = rs.getString("reserved_at");

                            getReceipt(
                                    reservationId,
                                    driverUserName,
                                    savedBusNum,
                                    savedSeatNum,
                                    savedPassengerName,
                                    paymentMethod2,
                                    paymentProvider,
                                    accountNumber,
                                    amountPaid2,
                                    reservedAt
                            );
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "No reservation record found."
                            );
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }

        });
    }

    public void getReceipt(int reservationID, String driverUserName, int busNum, String seatNum, String passengerName, String paymentMethod, String paymentProvider, String accNum, int amountPaid, String reservedAt) {
        try {
            JFrame frame = new JFrame("SELECT A BUS");
            Receipt form = new Receipt(reservationID, driverUserName, busNum, seatNum, passengerName, paymentMethod, paymentProvider, accNum, amountPaid, reservedAt);

            JPanel panel = form.getReceiptPanel();

            if (panel == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: getStudentSignUpPanel returned null. Check BusDetails.java!",
                        "Null Panel Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            frame.setContentPane(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(490, 720);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(onlinePaymentPanel);
            if (currentFrame != null) {
                currentFrame.dispose();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JPanel getOnlinePaymentPanel() {
        return onlinePaymentPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        btnConfirmTransac = new RoundedButton("CONFIRM TRANSACTION");
        btnHeader = new RoundedButton("ONLINE PAYMENT");
        paymentCardPanel = new RoundedPanel();
    }
}
