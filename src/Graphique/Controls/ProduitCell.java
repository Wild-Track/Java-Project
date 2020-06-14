package Graphique.Controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

import Models.Produits.InfoProduit;
import Utils.DoubleUtils;

public class ProduitCell extends JPanel {

    public static final ColorUIResource EVEN_COLOR = new ColorUIResource(32, 34, 40);
    public static final ColorUIResource ODD_COLOR = new ColorUIResource(40, 44, 51);
    public static final ColorUIResource HOVER_COLOR = new ColorUIResource(67, 74, 87);
    private static final long serialVersionUID = 8800677742700304789L;

    private InfoProduit infoProduit;
    private Runnable onDiminuer;
    private Runnable onAugmenter;
    private JButton buttonDiminuer;
    private JButton buttonAugmenter;
    private JLabel textPrix;
    private JLabel textProduit;
    private JLabel quantite;

    public ProduitCell(InfoProduit infoProduit, int index, Runnable onDiminuer, Runnable onAugmenter) {
        super();
        this.onDiminuer = onDiminuer;
        this.onAugmenter = onAugmenter;
        this.infoProduit = infoProduit;

        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(2000, 38));
        this.setBorder(new EmptyBorder(5, 20, 5, 0));

        FlowLayout rightPanelLayout = new FlowLayout(FlowLayout.RIGHT);
        JPanel rightPanel = new JPanel(rightPanelLayout);
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));

        textPrix = new JLabel();
        textPrix.setText(DoubleUtils.toCurrency(infoProduit.getTarifJournalier()));

        textProduit = new JLabel();
        textProduit.setText(infoProduit.toString(false) + "     ");


        quantite = new JLabel(String.format("%s/%s", infoProduit.getQuantiteDisponible(), infoProduit.getQuantiteTotal()));

        buttonDiminuer = new JButton(new ImageIcon("res/minus-box.png"));
        buttonDiminuer.setBorder(BorderFactory.createEmptyBorder());
        buttonDiminuer.setContentAreaFilled(false);
        buttonDiminuer.setEnabled(false);
        buttonDiminuer.addActionListener(e -> produitDiminuer());

        buttonAugmenter = new JButton(new ImageIcon("res/plus-box.png"));
        buttonAugmenter.setBorder(BorderFactory.createEmptyBorder());
        buttonAugmenter.setContentAreaFilled(false);
        buttonAugmenter.setEnabled(false);
        buttonAugmenter.addActionListener(e -> produitAugmenter());

        textPrix.setPreferredSize(new Dimension(100, 20));
        textPrix.setMaximumSize(new Dimension(100, 20));
        leftPanel.add(textPrix);
        leftPanel.add(textProduit);

        rightPanel.add(quantite);
        rightPanel.add(buttonDiminuer);
        rightPanel.add(buttonAugmenter);

        if (index % 2 == 0) {
            setBackground(EVEN_COLOR);
            rightPanel.setBackground(EVEN_COLOR);
            leftPanel.setBackground(EVEN_COLOR);
        }
        else {
            setBackground(ODD_COLOR);
            rightPanel.setBackground(ODD_COLOR);
            leftPanel.setBackground(ODD_COLOR);
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
                leftPanel.setBackground(HOVER_COLOR);
                buttonDiminuer.setEnabled(true);
                buttonAugmenter.setEnabled(true);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if (index % 2 == 0) {
                    setBackground(EVEN_COLOR);
                    rightPanel.setBackground(EVEN_COLOR);
                    leftPanel.setBackground(EVEN_COLOR);
                } else {
                    setBackground(ODD_COLOR);
                    rightPanel.setBackground(ODD_COLOR);
                    leftPanel.setBackground(ODD_COLOR);
                }
                buttonDiminuer.setEnabled(false);
                buttonAugmenter.setEnabled(false);
            }

        };

        addMouseListener(mouseListener);
        rightPanel.addMouseListener(mouseListener);
        leftPanel.addMouseListener(mouseListener);
        buttonDiminuer.addMouseListener(mouseListener);
        buttonAugmenter.addMouseListener(mouseListener);

        add(leftPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    public void redraw() {
        textProduit.setText(infoProduit.toString(false) + "  ");
        quantite.setText(String.format("%s/%s", infoProduit.getQuantiteDisponible(), infoProduit.getQuantiteTotal()));
    }

    private void produitAugmenter() {
        onAugmenter.run();
        redraw();
    }

    private void produitDiminuer() {
        onDiminuer.run();
        redraw();
    }
}