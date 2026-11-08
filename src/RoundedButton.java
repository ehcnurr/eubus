import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {    // means RoundedButton is a custom version of JButton (inherits from the JButton)
    private int radius = 25;     // how rounded the corners are

    public RoundedButton(String text) {
        super(text);        // receives the button's text
        // removing default appearance before drawing
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {    // how the button drawn on the screen
        Graphics2D g2 = (Graphics2D) g.create();   // creates a copy the graphics object and customize it
        g2.setRenderingHint(                                  // make the edges of the rounded button smoother
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (getModel().isPressed()) {        //  changing the button color based on its state
            g2.setColor(new Color(90, 0, 25));
        } else if (getModel().isRollover()) {
            g2.setColor(new Color(150, 0, 40));
        } else {
            g2.setColor(new Color(128, 0, 32));
        }

        // drawing the rounded button
        g2.fillRoundRect(
                0, 0,
                getWidth(),
                getHeight(),
                radius,
                radius
        );

        // release graphics
        g2.dispose();
        super.paintComponent(g);
    }
}
