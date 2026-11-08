import javax.swing.*;
import java.awt.*;

public class BusDriverLogInUI {

    private final BusDriverLogIn form;

    public BusDriverLogInUI(BusDriverLogIn form) {
        this.form = form;

        customizeUniversityLogo();
        customizeUsernamePanel();
        customizePasswordPanel();
        customizeLoginButton();
        customizeGoldLine();
        customizeLoginUI();

        addPasswordPlaceholder(form.getTxtDriverPassword(), "Enter your password");
    }

    // eu logo
    private void customizeUniversityLogo() {
        JLabel lblUniversityLogo = form.getLblUniversityLogo();

        ImageIcon logoIcon = getIcon("eu-logo.jpg", 35, 35);

        lblUniversityLogo.setIcon(logoIcon);

        lblUniversityLogo.setText(
                "<html><center>" +
                        "<b>Enverga University</b><br>" +
                        "<font color='#888888' size='2'>" +
                        "SCHOOL BUS SEAT RESERVATION" +
                        "</font></center></html>"
        );

        lblUniversityLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblUniversityLogo.setForeground(new Color(128, 0, 32));
        lblUniversityLogo.setFont(new Font("Segoe UI", Font.BOLD, 18));
    }

    // username input panel
    private void customizeUsernamePanel() {
        JPanel usernamePanel = form.getUsernamePanel();
        JTextField txtDriverUsername = form.getTxtDriverUsername();
        JLabel userLabel = new JLabel(getIcon("us.png", 25, 25));

        usernamePanel.removeAll();
        usernamePanel.setLayout(new BorderLayout(12, 0));
        usernamePanel.setBackground(Color.WHITE);
        usernamePanel.add(userLabel, BorderLayout.WEST);
        usernamePanel.add(txtDriverUsername, BorderLayout.CENTER);

        usernamePanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(225, 225, 225)),
                        BorderFactory.createEmptyBorder(5, 12, 5, 12))
        );

        usernamePanel.setPreferredSize(new Dimension(0, 45));
        usernamePanel.setMinimumSize(new Dimension(0, 45));
        usernamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        txtDriverUsername.setBackground(Color.WHITE);
        txtDriverUsername.setForeground(Color.DARK_GRAY);

        txtDriverUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        txtDriverUsername.setBorder(null);
        usernamePanel.revalidate();
        usernamePanel.repaint();
    }


    // Password input panel
    private void customizePasswordPanel() {
        JPanel passwordPanel = form.getPasswordPanel();
        JPasswordField txtDriverPassword = form.getTxtDriverPassword();
        JLabel passwordLabel = new JLabel(getIcon("password_icon.png", 25, 25));

        passwordPanel.removeAll();
        passwordPanel.setLayout(new BorderLayout(12, 0));
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(txtDriverPassword, BorderLayout.CENTER);

        passwordPanel.setBorder(   // set a line border around the txtUsername/password panel
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(225, 225, 225)),   // gray line
                        BorderFactory.createEmptyBorder(8, 16, 8, 16)    // inner border or creates empty space inside the panel
                )
        );

        // setting the panel dimension
        passwordPanel.setPreferredSize(new Dimension(0, 45));
        passwordPanel.setMinimumSize(new Dimension(0, 45));
        passwordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        txtDriverPassword.setBackground(Color.WHITE);
        txtDriverPassword.setForeground(Color.DARK_GRAY);
        txtDriverPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDriverPassword.setBorder(null);

        passwordPanel.revalidate();
        passwordPanel.repaint();
    }


    // login button
    private void customizeLoginButton() {
        JButton btnDriverLogIn = form.getBtnDriverLogIn();
        btnDriverLogIn.setBackground(new Color(128, 0, 32));
        btnDriverLogIn.setForeground(Color.WHITE);

        btnDriverLogIn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnDriverLogIn.setPreferredSize(new Dimension(0, 50));
        btnDriverLogIn.setFocusPainted(false);
        btnDriverLogIn.setBorderPainted(false);
        btnDriverLogIn.setContentAreaFilled(false);
        btnDriverLogIn.setOpaque(false);

        form.getLoginCardPanel().setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
    }

    // gold divider line
    private void customizeGoldLine() {
        JPanel goldLinePanel = form.getGoldLinePanel();

        goldLinePanel.setBackground(new Color(212, 175, 55));
        goldLinePanel.setOpaque(true);
        goldLinePanel.setPreferredSize(new Dimension(0, 2));
        goldLinePanel.setMinimumSize(new Dimension(0, 2));
        goldLinePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
    }

    // overall login screen design
    private void customizeLoginUI() {
        Color burgundy = new Color(128, 0, 32);
        Color warmWhite = new Color(247, 245, 242);
        form.getLoginCardPanel().setBackground(Color.WHITE);
        form.getLblTitle().setForeground(burgundy);
        form.getLblTitle().setFont(new Font("Segoe UI", Font.BOLD, 22));
        form.getLblUniversityLogo().setFont(new Font("Segoe UI", Font.BOLD, 15));
        form.getLblWelcome().setFont(new Font("Segoe UI", Font.PLAIN, 12));
        form.getLblWelcome().setForeground(Color.GRAY);
        form.getBtnDriverLogReturn().setBackground(warmWhite);
    }

    // password placeholder
    private void addPasswordPlaceholder(JPasswordField field, String placeholder) {
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(
                new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {  // detects if user clicks the password field
                        if (String.valueOf(
                                field.getPassword()
                        ).equals(placeholder)) {
                            field.setText("");
                            field.setForeground(Color.DARK_GRAY);
                            field.setEchoChar('•');
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {   // user clicks somewhere else or leave the password field
                        if (field.getPassword().length == 0) {   // check if the user leaves the field empty
                            field.setEchoChar((char) 0);
                            field.setText(placeholder);
                            field.setForeground(Color.GRAY);
                        }
                    }
                }
        );
    }

    // will resize and get icon image
    private ImageIcon getIcon(String filename, int width, int height) {
        java.net.URL imageURL = getClass().getResource("/images/" + filename);

        if (imageURL == null) {
            System.out.println("IMAGE NOT FOUND: " + filename);
            return null;
        }

        // loads images and store in the variable original
        ImageIcon original = new ImageIcon(imageURL);  // ImageIcon - Java swing class to display an icon

        Image scaledImage =
                original.getImage()
                        .getScaledInstance(
                                width,
                                height,
                                Image.SCALE_SMOOTH
                        );

        return new ImageIcon(
                scaledImage
        );
    }
}
