import javax.swing.*;
import java.awt.*;

public class LoadingScreen {

    private JFrame frame;
    private Timer timer;

    public LoadingScreen() {

        frame = new JFrame();
        frame.setSize(490, 720);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        java.net.URL imageURL = getClass().getResource("/images/icon2.jpg");

        if (imageURL == null) {
            throw new RuntimeException("Cannot find images/icon2.jpg!");
        }

        ImageIcon originalIcon = new ImageIcon(imageURL);
        Image scaledImage = originalIcon.getImage()
                .getScaledInstance(200, 200, Image.SCALE_SMOOTH);

        JLabel lblLogo = new JLabel(new ImageIcon(scaledImage));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        LoadingSpinner spinner = new LoadingSpinner();
        spinner.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel loadingLabel = new JLabel("Loading...");
        loadingLabel.setForeground(Color.GRAY);
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(lblLogo);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(spinner);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(loadingLabel);


        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.add(centerPanel);

        mainPanel.add(wrapperPanel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        timer = new Timer(4000, e -> {
            timer.stop();
            spinner.stopSpinner();
            frame.dispose();
            Main.main(new String[0]);
        });

        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoadingScreen::new);
    }
}

class LoadingSpinner extends JPanel {

    private static final int SIZE = 50;
    private static final int STROKE = 4;

    private int angle = 0;
    private final Timer timer;

    public LoadingSpinner() {
        setOpaque(false);

        Dimension size = new Dimension(SIZE, SIZE);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size); // prevents BoxLayout from stretching it

        timer = new Timer(10, e -> {
            angle = (angle + 6) % 360;
            repaint();
        });
        timer.start();
    }

    public void stopSpinner() {
        timer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(STROKE,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));


        int diameter = Math.min(getWidth(), getHeight()) - STROKE * 2;
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        g2.setColor(new Color(230, 230, 230));
        g2.drawArc(x, y, diameter, diameter, 0, 360);

        g2.setColor(new Color(128, 0, 32));
        g2.drawArc(x, y, diameter, diameter, angle, 100);

        g2.dispose();
    }
}