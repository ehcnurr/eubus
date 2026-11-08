import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {   // inherits from the JButton
    private int radius = 30;

    public RoundedPanel() {
        setOpaque(false);        // clear background
        setBackground(new Color(128, 0, 32));
    }

    @Override
    protected void paintComponent(Graphics g) {    // how panel is drawn
        super.paintComponent(g);   // perform nornmal painting first
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // make the curve smoother
        g2.setColor(getBackground());
        g2.fillRoundRect(    // rounded rectangle
                0, 0,
                getWidth(),
                getHeight(),
                radius, radius
        );

        // releases the graphics
        g2.dispose();
    }
}