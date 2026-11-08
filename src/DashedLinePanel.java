import javax.swing.*;
import java.awt.*;

public class DashedLinePanel extends JPanel {
    private Color lineColor = new Color(200, 200, 200); // Subtle gray divider

    public DashedLinePanel() {
        setOpaque(false); // Make sure the background doesn't overwrite the dash
        setPreferredSize(new Dimension(0, 10)); // Gives it a proper height layout cushion
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smooth rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(lineColor);

        // Define the pattern: 6px line, 6px blank gap
        float[] dashPattern = {6f, 6f};
        BasicStroke dashedStroke = new BasicStroke(
                1.5f,                      // Line thickness
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                1.0f,
                dashPattern,
                0.0f
        );

        g2d.setStroke(dashedStroke);

        // Draw horizontally centered across the panel width
        int y = getHeight() / 2;
        g2d.drawLine(0, y, getWidth(), y);

        g2d.dispose();
    }
}
