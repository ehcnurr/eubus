import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Receipt {
    private JButton btnOkay;
    private JPanel receiptPanel;
    private JLabel lblName;
    private JLabel lblBusNum;
    private JLabel lblSeatNum;
    private JLabel lblDriver;
    private JLabel lblPayMethod;
    private JLabel lblPaymentProv;
    private JLabel lblAccNum;
    private JLabel lblReservedAt;
    private JLabel lblAmountPaid;
    private JPanel headerPanel;
    private JPanel receiptCardPanel;
    private JPanel dashedLine1;
    private JPanel dashedLine2;
    private JPanel dashedLine3;
    private JPanel dashedLine4;
    private JPanel dashedLine5;
    private JPanel dashedLine6;
    private JPanel dashedLine7;
    private JPanel dashedLine8;
    private JPanel line2Panel;
    private JPanel line1Panel;
    private JLabel lblSecuredTicket;
    private JLabel lblBarcode;
    private JLabel lblNote;

    public JLabel getLblName() {
        return lblName;
    }

    public JLabel getLblBusNum() {
        return lblBusNum;
    }

    public JLabel getLblSeatNum() {
        return lblSeatNum;
    }

    public JLabel getLblDriver() {
        return lblDriver;
    }

    public JLabel getLblPayMethod() {
        return lblPayMethod;
    }

    public JLabel getLblPaymentProv() {
        return lblPaymentProv;
    }

    public JLabel getLblAccNum() {
        return lblAccNum;
    }

    public JLabel getLblReservedAt() {
        return lblReservedAt;
    }

    public JLabel getLblAmountPaid() {
        return lblAmountPaid;
    }

    public JPanel getLine1Panel() {
        return line1Panel;
    }

    public JPanel getLine2Panel() {
        return line2Panel;
    }

    public JLabel getLblSecuredTicket() {
        return lblSecuredTicket;
    }

    public JLabel getLblBarcode() {
        return lblBarcode;
    }

    public JLabel getLblNote() {
        return lblNote;
    }

    private int reservationID;
    private String driverUserName;
    private int busNum;
    private String seatNum;
    private String passengerName;
    private String paymentMethod;
    private String paymentProvider;
    private String accNum;
    private int amountPaid;
    private String reservedAt;

    public Receipt(int reservationID, String driverUserName, int busNum, String seatNum, String passengerName, String paymentMethod, String paymentProvider, String accNum, int amountPaid, String reservedAt) {
        this.reservationID = reservationID;
        this.driverUserName = driverUserName;
        this.busNum = busNum;
        this.seatNum = seatNum;
        this.passengerName = passengerName;
        this.paymentMethod = paymentMethod;
        this.paymentProvider = paymentProvider;
        this.accNum = accNum;
        this.amountPaid = amountPaid;
        this.reservedAt = reservedAt;

        new ReceiptUI(
                this,
                passengerName,
                busNum,
                seatNum,
                driverUserName,
                paymentMethod,
                paymentProvider,
                accNum,
                amountPaid,
                reservedAt
        );

        btnOkay.addActionListener(new ActionListener() {
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

                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(receiptPanel);
                    if (currentFrame != null) {
                        currentFrame.dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }



    public JPanel getReceiptPanel() {
        return receiptPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        btnOkay = new RoundedButton("OKAY");

        dashedLine1 = new DashedLinePanel();
        dashedLine2 = new DashedLinePanel();
        dashedLine3 = new DashedLinePanel();
        dashedLine4 = new DashedLinePanel();
        dashedLine5 = new DashedLinePanel();
        dashedLine6 = new DashedLinePanel();
        dashedLine7 = new DashedLinePanel();
        dashedLine8 = new DashedLinePanel();
    }
}
