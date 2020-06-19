package Graphique.Controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

import Models.Client;

public class ClientCell extends JPanel {

    public static final ColorUIResource EVEN_COLOR = new ColorUIResource(32, 34, 40);
    public static final ColorUIResource ODD_COLOR = new ColorUIResource(40, 44, 51);
    public static final ColorUIResource HOVER_COLOR = new ColorUIResource(67, 74, 87);
    private static final long serialVersionUID = 8800677742700304789L;

    private Runnable onRemove;
    private JButton removeButton;

    public ClientCell(Client client, int index, Runnable onRemove) {
        super();
        this.onRemove = onRemove;

        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(2000, 38));
        this.setBorder(new EmptyBorder(5, 20, 5, 0));

        FlowLayout rightPanelLayout = new FlowLayout(FlowLayout.RIGHT);
        rightPanelLayout.setHgap(20);
        JPanel rightPanel = new JPanel(rightPanelLayout);

        JLabel text = new JLabel(client.toString() + "  ");

        removeButton = new JButton(new ImageIcon("res/close-box.png"));
        removeButton.setBorder(BorderFactory.createEmptyBorder());
        removeButton.setContentAreaFilled(false);
        removeButton.setEnabled(false);
        removeButton.addActionListener(e -> deleteClient());

        JCheckBox fideleCheckBox = new JCheckBox("Fidèle : ", client.getFidele());
        fideleCheckBox.setBorder(BorderFactory.createEmptyBorder());
        fideleCheckBox.setContentAreaFilled(false);
        fideleCheckBox.addItemListener(e -> client.setFidele(e.getStateChange() == 1 ? true : false));

        rightPanel.add(fideleCheckBox);
        rightPanel.add(removeButton);

        if (index % 2 == 0) {
            setBackground(EVEN_COLOR);
            rightPanel.setBackground(EVEN_COLOR);
        }
        else {
            setBackground(ODD_COLOR);
            rightPanel.setBackground(ODD_COLOR);
        }

        MouseListener mouseListener = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {

            }

            @Override
            public void mouseReleased(MouseEvent me) {

            }

            @Override
            public void mousePressed(MouseEvent me) {

            }

            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(HOVER_COLOR);
                rightPanel.setBackground(HOVER_COLOR);
                removeButton.setEnabled(true);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if (index % 2 == 0) {
                    setBackground(EVEN_COLOR);
                    rightPanel.setBackground(EVEN_COLOR);
                } else {
                    setBackground(ODD_COLOR);
                    rightPanel.setBackground(ODD_COLOR);
                }
                removeButton.setEnabled(false);
            }

        };

        addMouseListener(mouseListener);
        rightPanel.addMouseListener(mouseListener);
        removeButton.addMouseListener(mouseListener);
        fideleCheckBox.addMouseListener(mouseListener);

        add(text, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    private void deleteClient() {
        onRemove.run();
    }

}