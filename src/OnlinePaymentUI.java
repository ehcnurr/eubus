import javax.swing.*;
import java.awt.*;

public class OnlinePaymentUI {

    private final OnlinePayment onlinePayment;

    public OnlinePaymentUI(OnlinePayment onlinePayment) {
        this.onlinePayment = onlinePayment;

        styleComponents();
    }

    private void styleComponents() {
        // get components from getters
        JSeparator line = onlinePayment.getLine();
        JSeparator line2 = onlinePayment.getLine2();
        JButton btnHeader = onlinePayment.getBtnHeader();

        Color gold = new Color(212, 175, 55);
        Color burgundy = new Color(128, 0, 32);

        line.setOrientation(SwingConstants.HORIZONTAL); // gold line divider
        line.setForeground(gold);
        line.setBackground(gold);

        line2.setOrientation(SwingConstants.HORIZONTAL);
        line2.setForeground(gold);
        line2.setBackground(gold);

        btnHeader.setBackground(burgundy);   // design header button
        btnHeader.setForeground(Color.WHITE);
        btnHeader.setFont(new Font("Segoe UI", Font.BOLD, 18));

        btnHeader.setFocusPainted(false);
        btnHeader.setBorderPainted(false);
    }
}