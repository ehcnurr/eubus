import javax.swing.*;
import java.awt.*;

public class ReceiptUI {

    public ReceiptUI(Receipt form, String passengerName, int busNum,
                     String seatNum, String driverUserName,
                     String paymentMethod, String paymentProvider,
                     String accNum, int amountPaid, String reservedAt) {

        // get components
        JPanel line1Panel = form.getLine1Panel();
        JPanel line2Panel = form.getLine2Panel();

        JLabel lblName = form.getLblName();
        JLabel lblBusNum = form.getLblBusNum();
        JLabel lblSeatNum = form.getLblSeatNum();
        JLabel lblDriver = form.getLblDriver();
        JLabel lblPayMethod = form.getLblPayMethod();
        JLabel lblPaymentProv = form.getLblPaymentProv();
        JLabel lblAccNum = form.getLblAccNum();
        JLabel lblReservedAt = form.getLblReservedAt();
        JLabel lblAmountPaid = form.getLblAmountPaid();

        JLabel lblSecuredTicket = form.getLblSecuredTicket();
        JLabel lblBarcode = form.getLblBarcode();
        JLabel lblNote = form.getLblNote();

        // divider line
        line1Panel.setBackground(new Color(221, 221, 221));
        line1Panel.setOpaque(true);
        line1Panel.setPreferredSize(new Dimension(0, 0));
        line1Panel.setMinimumSize(new Dimension(0, 0));
        line1Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 0));

        line2Panel.setBackground(new Color(221, 221, 221));
        line2Panel.setOpaque(true);
        line2Panel.setPreferredSize(new Dimension(0, 1));
        line2Panel.setMinimumSize(new Dimension(0, 1));
        line2Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        lblName.setText(passengerName);   // display receipt info to the Jlabel
        lblBusNum.setText("Bus " + busNum);
        lblSeatNum.setText(seatNum);

        String username = driverUserName;

        if (username != null && username.contains("@")) {
            username = username.substring(0, username.indexOf("@"));
        }

        // display receipt info to the Jlabel
        lblDriver.setText(username);
        lblPayMethod.setText(paymentMethod);
        lblPaymentProv.setText(paymentProvider);
        lblAccNum.setText(accNum);
        lblReservedAt.setText(reservedAt);

        Color textPrimary = new Color(30, 30, 40);
        Color accentMaroon = new Color(122, 0, 22);

        Font valueFont = new Font("SansSerif", Font.BOLD, 14);

        lblName.setFont(valueFont);
        lblName.setForeground(textPrimary);

        lblBusNum.setFont(valueFont);
        lblBusNum.setForeground(accentMaroon);

        lblAmountPaid.setText("₱" + amountPaid + ".00");
        lblAmountPaid.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblAmountPaid.setForeground(accentMaroon);

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/green_chk.png")); // secured ticket icon

        Image scaledImage = originalIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);

        lblSecuredTicket.setIcon(new ImageIcon(scaledImage));
        lblSecuredTicket.setText("Secured Digital Ticket");
        lblSecuredTicket.setForeground(new Color(138, 141, 147));

       // bar code
        ImageIcon barIcon = new ImageIcon(getClass().getResource("/images/barcode.png"));

        Image scaledBarcode = barIcon.getImage().getScaledInstance(180, 70, Image.SCALE_SMOOTH);

        lblBarcode.setIcon(new ImageIcon(scaledBarcode));

        // receipt notice
        lblNote.setText(
                "<html><div style='text-align: center;'>" +
                        "NOTICE: Present this official digital reservation<br>" +
                        "receipt to the bus driver for verification prior to riding." +
                        "</div></html>"
        );

        lblNote.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblNote.setForeground(new Color(140, 145, 155));
        lblNote.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
