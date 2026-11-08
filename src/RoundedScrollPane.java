import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedScrollPane extends JScrollPane {    // inherits from the JScrolPane
    public RoundedScrollPane(Component view) {
        super(view);           // receiving the component table

        // removing default appearance before drawing
        setOpaque(false);
        setBorder(new RoundedBorder(15));    // custom rounded border
        getViewport().setOpaque(false);     // allows the appearance to show through by removing the viewing area

        getViewport().setBackground(Color.WHITE);
        setBackground(Color.WHITE);
    }

    private static class RoundedBorder extends AbstractBorder { // abtractborder allows to define exactly how the border should be drawn
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(   // how the border is drawn
                Component c,
                Graphics g,
                int x,
                int y,
                int width,
                int height) {

            Graphics2D g2 = (Graphics2D) g.create();  // creates copy of graphics object
            g2.setRenderingHint(    // make border smooth
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(212, 175, 55));
            g2.setStroke(new BasicStroke(1.5f));   // set border thickness
            g2.draw(new RoundRectangle2D.Double(
                    x + 1,
                    y + 1,
                    width - 3,
                    height - 3,
                    radius,
                    radius
            ));

            // releases the graphics
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {         // border spacing
            return new Insets(5, 5, 5, 5);
        }
    }
}
