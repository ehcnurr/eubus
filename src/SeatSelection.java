import javax.print.attribute.standard.PresentationDirection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.Color;
import javax.swing.JCheckBox;


public class SeatSelection {
    private JCheckBox chkS11;
    private JCheckBox chkS9;
    private JCheckBox chkS12;
    private JCheckBox chkS10;
    private JCheckBox chkS7;
    private JCheckBox chkS8;
    private JCheckBox chkS5;
    private JCheckBox chkS6;
    private JCheckBox chkS3;
    private JCheckBox chkS4;
    private JCheckBox chkS2;
    private JCheckBox chkS23;
    private JCheckBox chkS25;
    private JCheckBox chkS21;
    private JCheckBox chkS19;
    private JCheckBox chkS17;
    private JCheckBox chkS15;
    private JCheckBox chkS26;
    private JCheckBox chkS24;
    private JCheckBox chkS22;
    private JCheckBox chkS20;
    private JCheckBox chkS18;
    private JCheckBox chkS16;
    private JCheckBox chkS13;
    private JCheckBox chkS14;
    private JCheckBox chkDriver;
    private JCheckBox chkS1;
    private JButton btnSeatSelectReturn;
    private JPanel seatSelectionPanel;
    private int selectedBusNum, busSeat;

    Payment payment = new BusFarePayment(Main.userType);

    private Map<String, JCheckBox> seatBoxes = new LinkedHashMap<>();

    public SeatSelection() {
        initializeSeatBoxes();
        setupSeatListeners();

        btnSeatSelectReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame frame = new JFrame();
                    BusesAvailable form = new BusesAvailable();

                    JPanel panel = form.getAvailableBusPanel();

                    if (panel == null) {
                        JOptionPane.showMessageDialog(null,
                                "Error: getAvailableBusPanel returned null. Check BusesAvailable.java!",
                                "Null Panel Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    frame.setContentPane(panel);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(490, 720);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(seatSelectionPanel);
                    if (currentFrame != null) {
                        currentFrame.dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public JPanel getSeatSelectionPanel() {
        return seatSelectionPanel;
    }

    private void initializeSeatBoxes() {
        seatBoxes.put("S1", chkS1);
        seatBoxes.put("S2", chkS2);
        seatBoxes.put("S3", chkS3);
        seatBoxes.put("S4", chkS4);
        seatBoxes.put("S5", chkS5);
        seatBoxes.put("S6", chkS6);
        seatBoxes.put("S7", chkS7);
        seatBoxes.put("S8", chkS8);
        seatBoxes.put("S9", chkS9);
        seatBoxes.put("S10", chkS10);
        seatBoxes.put("S11", chkS11);
        seatBoxes.put("S12", chkS12);
        seatBoxes.put("S13", chkS13);
        seatBoxes.put("S14", chkS14);
        seatBoxes.put("S15", chkS15);
        seatBoxes.put("S16", chkS16);
        seatBoxes.put("S17", chkS17);
        seatBoxes.put("S18", chkS18);
        seatBoxes.put("S19", chkS19);
        seatBoxes.put("S20", chkS20);
        seatBoxes.put("S21", chkS21);
        seatBoxes.put("S22", chkS22);
        seatBoxes.put("S23", chkS23);
        seatBoxes.put("S24", chkS24);
        seatBoxes.put("S25", chkS25);
        seatBoxes.put("S26", chkS26);

    }

    public void loadBusSeats(int busNum) {
        this.selectedBusNum = busNum;

        for (JCheckBox seat: seatBoxes.values()) {
            seat.setSelected(false);
            seat.setEnabled(true);
        }

        String query = """
                SELECT seat_num 
                FROM seat_reservations
                WHERE bus_num = ?
                """;

        try (
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(query)
        ) {
            stmt.setInt(1, busNum);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String seatNum = rs.getString("seat_num");
                    JCheckBox seat = seatBoxes.get(seatNum);

                    if (seat != null) {
                        seat.setSelected(true);
                        seat.setEnabled(false);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
            return;
        }
    }

    private boolean reserveSeat(String seatNum) {
        String insertQuery = """
                INSERT INTO seat_reservations (bus_num, seat_num)
                VALUES (?, ?)
                """;

        String updateSlotsQuery = """
                UPDATE buses 
                SET available_slots = available_slots - 1
                WHERE bus_num = ?
                AND available_slots > 0
                """;

        try (Connection con = DatabaseConnection.getConnection();) {
            con.setAutoCommit(false);

            try (
                PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                PreparedStatement updateStmt = con.prepareStatement(updateSlotsQuery);
            ) {
                insertStmt.setInt(1, selectedBusNum);
                insertStmt.setString(2, seatNum);

                insertStmt.executeUpdate();

                updateStmt.setInt(1, selectedBusNum);

                int updatedRows = updateStmt.executeUpdate();

                if (updatedRows > 0) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();

                    JOptionPane.showMessageDialog(seatSelectionPanel,
                            "No available slots remaining for this bus.");
                    return false;
                }
            } catch (SQLException e) {
                con.rollback();
                JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
            "Failed to reserve seat. It may already be taken.",
                    "Reservation Failed",
                    JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }

    public void handleSeatSelection(JCheckBox seat) {

        if (seat.isSelected()) {
            String seatNum = seat.getText();

            int choice = JOptionPane.showConfirmDialog(
                    seatSelectionPanel,
                    "Do you want to reserve " + seatNum + " on Bus " + selectedBusNum+"?",
                    "Confirm seat reservation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {

                payment.calculateAmount();
                double amountPaid = payment.getAmount();

                String passengerName = JOptionPane.showInputDialog(
                        null,
                        "Enter passenger name:"
                );

                if (passengerName == null || passengerName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Passenger name is required."
                    );

                    seat.setSelected(false);
                    return;
                }

                passengerName = passengerName.trim();

                String[] options = {"Cash", "Online Payment"};

                int paymentMethod = JOptionPane.showOptionDialog(
                        null,
                        "Choose a payment method:",
                        "Choose payment method",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (paymentMethod == 0) {
                    // Reserve seat only after valid name
                    if (!reserveSeat(seatNum)) {
                        seat.setSelected(false);
                        return;
                    }

                    boolean saved = TransactionHistory.saveTransaction(
                            selectedBusNum,
                            seatNum,
                            passengerName,
                            "Cash",
                            "N/A",
                            "N/A",
                            amountPaid
                    );

                    if (!saved) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Failed to save transaction."
                        );
                        return;
                    }

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
                        stmt.setInt(1, selectedBusNum);
                        stmt.setString(2, seatNum);
                        stmt.setString(3, passengerName);

                        try (ResultSet rs = stmt.executeQuery()) {

                            if (rs.next()) {

                                int reservationId = rs.getInt("reservation_id");
                                String driverUserName =
                                        rs.getString("driver_username");
                                int savedBusNum =
                                        rs.getInt("bus_num");
                                String savedSeatNum =
                                        rs.getString("seat_num");
                                String savedPassengerName =
                                        rs.getString("passenger_name");
                                String paymentMethod2 =
                                        rs.getString("payment_method");
                                String paymentProvider =
                                        rs.getString("payment_provider");
                                String accountNumber =
                                        rs.getString("account_number");
                                int amountPaid2 =
                                        rs.getInt("amount_paid");
                                String reservedAt =
                                        rs.getString("reserved_at");

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

                } else if (paymentMethod == 1) {
                    if (!reserveSeat(seatNum)) {
                        seat.setSelected(false);
                        return;
                    }
                    OnlinePayment onlinePayment =
                            new OnlinePayment(selectedBusNum, passengerName, seatNum);

                    JPanel panel = onlinePayment.getOnlinePaymentPanel();

                    JFrame frame = new JFrame("Online Payment");
                    frame.setContentPane(panel);
                    frame.setSize(380, 400);
                    frame.setLocationRelativeTo(null);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);

                    SwingUtilities.getWindowAncestor(seatSelectionPanel).dispose();

                } else {
                    seat.setSelected(false);
                    return;
                }

                seat.setEnabled(false);

            } else {
                seat.setSelected(false);
            }
        }
    }
    public void getReceipt(int reservationID, String driverUserName,
                           int busNum, String seatNum, String passengerName,
                           String paymentMethod, String paymentProvider,
                           String accNum, int amountPaid, String reservedAt) {
        try {
            JFrame frame = new JFrame("BUS RECEIPT");

            Receipt form = new Receipt(
                    reservationID, driverUserName, busNum, seatNum,
                    passengerName, paymentMethod, paymentProvider,
                    accNum, amountPaid, reservedAt
            );

            JPanel panel = form.getReceiptPanel();

            if (panel == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Receipt panel is null!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            frame.setContentPane(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setSize(490, 720);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);

            frame.setVisible(true);

            // Debugging
            System.out.println("Frame size: " + frame.getSize());
            System.out.println("Content size: " +
                    frame.getContentPane().getSize());

            JFrame currentFrame = (JFrame)
                    SwingUtilities.getWindowAncestor(seatSelectionPanel);

            if (currentFrame != null) {
                currentFrame.dispose();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setupSeatListeners() {
        JCheckBox[] seats = {
                chkS1, chkS2, chkS3, chkS4, chkS5, chkS6,
                chkS7, chkS8, chkS9, chkS10, chkS11, chkS12,
                chkS13, chkS14, chkS15, chkS16, chkS17, chkS18,
                chkS19, chkS20, chkS21, chkS22, chkS23, chkS24,
                chkS25, chkS26
        };

        for (JCheckBox seat : seats) {
            seat.addActionListener(e -> handleSeatSelection(seat)) ;
        }
    }

}
