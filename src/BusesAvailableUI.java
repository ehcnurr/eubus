import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class BusesAvailableUI {
    private final BusesAvailable busesAvailable;
    public BusesAvailableUI(BusesAvailable busesAvailable) {
        this.busesAvailable = busesAvailable;

        styleTable();
        styleComponents();
    }

    private void styleComponents() {
        JTable tblBusesAvailable = busesAvailable.getTblBusesAvailable();

        JButton btnSelect = busesAvailable.getBtnSelect();
        JButton btnSelectBus1 = busesAvailable.getBtnSelectBus1();
        JButton btnSelectBus2 = busesAvailable.getBtnSelectBus2();
        JButton btnSelectBus3 = busesAvailable.getBtnSelectBus3();
        JButton btnSelectBus4 = busesAvailable.getBtnSelectBus4();
        JButton btnSelectBus5 = busesAvailable.getBtnSelectBus5();
        JButton btnBusAvReturn = busesAvailable.getBtnBusAvReturn();

        JLabel lblBusesAvailable = busesAvailable.getLblBusesAvailable();
        JLabel lblSubtitle = busesAvailable.getLblSubtitle();
        JLabel lblBusIcon = busesAvailable.getLblBusIcon();

        JPanel headerPanel = busesAvailable.getHeaderPanel();
        JPanel goldLinePanel = busesAvailable.getGoldLinePanel();

        // set table design
        tblBusesAvailable.setRowHeight(75);
        tblBusesAvailable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblBusesAvailable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        tblBusesAvailable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // column widths
        tblBusesAvailable.getColumnModel().getColumn(0).setPreferredWidth(75);
        tblBusesAvailable.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblBusesAvailable.getColumnModel().getColumn(2).setPreferredWidth(105);
        tblBusesAvailable.getColumnModel().getColumn(3).setPreferredWidth(105);
        tblBusesAvailable.getColumnModel().getColumn(4).setPreferredWidth(95);
        tblBusesAvailable.getColumnModel().getColumn(5).setPreferredWidth(85);

        tblBusesAvailable.getTableHeader().setPreferredSize(new Dimension(0, 84));

        // button sizes
        Dimension buttonSize = new Dimension(120, 45);
        btnSelect.setPreferredSize(new Dimension(125, 60));
        btnSelectBus1.setPreferredSize(buttonSize);
        btnSelectBus2.setPreferredSize(buttonSize);
        btnSelectBus3.setPreferredSize(buttonSize);
        btnSelectBus4.setPreferredSize(buttonSize);
        btnSelectBus5.setPreferredSize(buttonSize);

        // center table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tblBusesAvailable.getColumnCount(); i++) {
            tblBusesAvailable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }

        // button styling
        JButton[] buttons = {
                btnSelectBus1,
                btnSelectBus2,
                btnSelectBus3,
                btnSelectBus4,
                btnSelectBus5,
                btnBusAvReturn
        };

        for (JButton button : buttons) {
            button.setBackground(new Color(128, 0, 32));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setOpaque(false);
        }

        // header title
        lblBusesAvailable.setOpaque(false);
        lblBusesAvailable.setForeground(Color.WHITE);
        lblBusesAvailable.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblBusesAvailable.setHorizontalAlignment(SwingConstants.CENTER);

        // gold divider line
        goldLinePanel.setBackground(new Color(212, 175, 55));
        goldLinePanel.setOpaque(true);
        goldLinePanel.setPreferredSize(new Dimension(0, 2));
        goldLinePanel.setMinimumSize(new Dimension(0, 2));
        goldLinePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));

        //bus icon
        lblBusIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblBusIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBusIcon.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        java.net.URL iconURL = getClass().getResource("/images/bus-icon.png");

        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaledImage = icon.getImage().getScaledInstance(
                    35, 35,
                    Image.SCALE_SMOOTH
            );
            lblBusIcon.setIcon(new ImageIcon(scaledImage));
        } else {
            System.out.println("Bus icon not found!");
        }

        // header alignment
        lblBusesAvailable.setHorizontalAlignment(SwingConstants.CENTER);
        lblBusesAvailable.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBusesAvailable.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        // header panel layout
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.removeAll();
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(lblBusIcon);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(lblBusesAvailable);
        headerPanel.add(lblSubtitle);
        headerPanel.revalidate();
        headerPanel.repaint();
    }

    // table design
    private void styleTable() {
        JTable tblBusesAvailable = busesAvailable.getTblBusesAvailable();
        Color burgundy = new Color(128, 0, 32);
        Color gold = new Color(212, 175, 55);
        Color beige = new Color(247, 245, 242);


        tblBusesAvailable.setBackground(Color.WHITE);
        tblBusesAvailable.setForeground(new Color(55, 55, 55));
        tblBusesAvailable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblBusesAvailable.setRowHeight(48);
        tblBusesAvailable.setSelectionBackground(new Color(245, 231, 197));
        tblBusesAvailable.setSelectionForeground(burgundy);
        // table grid
        tblBusesAvailable.setShowVerticalLines(false);
        tblBusesAvailable.setShowHorizontalLines(true);

        tblBusesAvailable.setGridColor(new Color(225, 220, 210));
        tblBusesAvailable.setIntercellSpacing(new Dimension(0, 1));

        // table header
        JTableHeader header = tblBusesAvailable.getTableHeader();
        header.setPreferredSize(new Dimension(0, 55));
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(burgundy);
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);

        // custom header renderer
        DefaultTableCellRenderer headerRenderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {
                        JLabel label = new JLabel(value == null ? "" : value.toString());
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setVerticalAlignment(SwingConstants.CENTER);
                        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
                        label.setForeground(Color.WHITE);
                        label.setBackground(burgundy);
                        label.setOpaque(true);
                        label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, gold));

                        return label;
                    }
                };

        for (int i = 0; i < tblBusesAvailable.getColumnCount(); i++) {
            tblBusesAvailable.getColumnModel()
                    .getColumn(i)
                    .setHeaderRenderer(headerRenderer);
        }

        // alternating row colors
        DefaultTableCellRenderer rowRenderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {
                        JLabel label = (JLabel)
                                super.getTableCellRendererComponent(
                                        table,
                                        value,
                                        isSelected,
                                        hasFocus,
                                        row,
                                        column
                                );

                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                        label.setForeground(new Color(55, 55, 55));
                        label.setBorder(BorderFactory.createEmptyBorder(5, 3, 5, 3));

                        return label;
                    }
                };

        for (int i = 0; i < tblBusesAvailable.getColumnCount(); i++) {
            tblBusesAvailable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(rowRenderer);
        }

        tblBusesAvailable.setFillsViewportHeight(true);
    }
}
