import javax.swing.*;
import java.awt.*;

public class MainUI {

    private Main main;

    // from main.java
    private JLabel lblUniversity;
    private JLabel lblSubtitle;

    private JButton btnStudentStaff;
    private JButton btnDriver;
    private JButton btnGuest;

    private JPanel mainPanel;
    private JPanel instructionPanel;

    private JLabel lblChooseUserType;
    private JLabel lblSelectOption;

    public MainUI(Main main) {
        this.main = main;  // received an object so this class can use it

        // get components using getters from Main.java
        lblUniversity = main.getLblUniversity();
        lblSubtitle = main.getLblSubtitle();

        btnStudentStaff = main.getBtnStudentStaff();
        btnDriver = main.getBtnDriver();
        btnGuest = main.getBtnGuest();

        mainPanel = main.getMainPanel();
        instructionPanel = main.getInstructionPanel();

        lblChooseUserType = main.getLblChooseUserType();
        lblSelectOption = main.getLblSelectOption();

        customizeUniversity();
        customizeInstructions();
        customizeButtons();
        buildCenteredLayout();
    }

    // univ logo
    private void customizeUniversity() {
        lblUniversity.setIcon(getIcon("mseuf-logo.png", 95, 95));
        lblUniversity.setText("MSEUF");
        lblUniversity.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblUniversity.setForeground(new Color(128, 0, 32));
        lblUniversity.setHorizontalAlignment(SwingConstants.CENTER);
        lblUniversity.setHorizontalTextPosition(SwingConstants.CENTER);
        lblUniversity.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblUniversity.setIconTextGap(10);

        lblSubtitle.setText("SCHOOL BUS SEAT RESERVATION");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubtitle.setForeground(Color.GRAY);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    //  instructions design   -- Choose User Type & Select an option label
    private void customizeInstructions() {
        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));  // arrange components vertically, from top to bottom
        instructionPanel.setOpaque(false);   // make panel transparent
        lblChooseUserType.setAlignmentX(Component.CENTER_ALIGNMENT);   // center horizontally
        lblSelectOption.setAlignmentX(Component.CENTER_ALIGNMENT);   // both label will be centered
        instructionPanel.add(lblChooseUserType);
        instructionPanel.add(Box.createVerticalStrut(5));   // high gap between the labels
        instructionPanel.add(lblSelectOption);
    }

    // get the image icon
    private ImageIcon getIcon(String filename, int width, int height) {
        java.net.URL imageURL = getClass().getResource("/images/" + filename);

        if (imageURL == null) {
            System.out.println("IMAGE NOT FOUND: " + filename);
            return null;
        }

        ImageIcon icon = new ImageIcon(imageURL);  // ImageIcon - Java swing class to display an icon

        // scaling image
        Image image = icon.getImage().getScaledInstance(
                width,
                height,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(image);
    }

    private void customizeButtons() {   // for buttons
        Color burgundy = new Color(128, 0, 32);

        JButton[] buttons = {
                btnStudentStaff,
                btnDriver,
                btnGuest
        };

        for (JButton button : buttons) {
            button.setBackground(burgundy);
            button.setForeground(Color.WHITE);

            button.setFont(new Font("Segoe UI", Font.BOLD, 16));
            button.setPreferredSize(new Dimension(420, 55));
            button.setMinimumSize(new Dimension(100, 55));
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));

            button.setFocusPainted(false);  // remove focus indicator around the button so tthe button will look cleaner
            button.setBorderPainted(false);  // remove border
        }
    }

    private void buildCenteredLayout() { // will center the contents
        JPanel contentPanel = new JPanel();   // create panel to arrange everything vertically  (container for everything on the screen)
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));  // make everything vertical
        contentPanel.setOpaque(false);  // clear background
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));  // spaces around

        lblUniversity.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton[] buttons = {
                btnStudentStaff,
                btnDriver,
                btnGuest
        };

        // center every button
        for (JButton button : buttons) {
            button.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );
        }

        contentPanel.add(lblUniversity);
        contentPanel.add(Box.createVerticalStrut(5));  // 5px vertical space
        contentPanel.add(lblSubtitle);
        contentPanel.add(Box.createVerticalStrut(20)); // 20px space divider

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);  // line separator

        separator.setForeground(Color.BLACK);
        separator.setBackground(Color.BLACK);

        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));

        contentPanel.add(separator);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(instructionPanel);
        contentPanel.add(Box.createVerticalStrut(50));
        contentPanel.add(btnStudentStaff);
        contentPanel.add(Box.createVerticalStrut(25));
        contentPanel.add(btnDriver);
        contentPanel.add(Box.createVerticalStrut(25));
        contentPanel.add(btnGuest);

        // center everything
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();  // will use this to control the position

        //sets grid position column, row
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;  // allow the component to expand horizontally
        gbc.weightx = 1.0;    // it can take available horizontal space
        gbc.anchor = GridBagConstraints.CENTER;

        mainPanel.add(contentPanel, gbc);   // adding it to the main

        // update ui
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}