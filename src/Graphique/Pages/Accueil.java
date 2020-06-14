package Graphique.Pages;

import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

import Graphique.Fen_main;
import Graphique.Controls.DropShadowBorder;
import Graphique.Controls.Page;
import mdlaf.utils.MaterialColors;

public class Accueil extends Page {

    private static final long serialVersionUID = 2894069837824203650L;
    private JButton bClient, bCommande, bStock;

    public Accueil() {
        super();
        Accueil instance = this;

        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        layout.setHgap(100);
        layout.setVgap(202);
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                layout.setVgap(instance.getHeight() / 3);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
        this.setLayout(layout);

        bClient = new JButton(new ImageIcon("res/Icon_Manage_Client.png"));
        bClient.setBorder(
                (Border) new DropShadowBorder(MaterialColors.RED_600, 0, 12, 0.3f, 40, true, true, true, true));
        bClient.setFocusable(false);
        this.add(bClient);

        bCommande = new JButton(new ImageIcon("res/Icon_Manage_Command.png"));
        bCommande.setBorder(
                (Border) new DropShadowBorder(MaterialColors.RED_600, 0, 12, 0.3f, 40, true, true, true, true));
        bCommande.setFocusable(false);
        this.add(bCommande);

        bStock = new JButton(new ImageIcon("res/Icon_Manage_Stock.png"));
        bStock.setBorder(
                (Border) new DropShadowBorder(MaterialColors.RED_600, 0, 12, 0.3f, 40, true, true, true, true));
        bStock.setFocusable(false);
        this.add(bStock);

        // Evenements
        bClient.addActionListener(e -> Fen_main.getInstance().pushPage(new ManageClients()));
        bCommande.addActionListener(e -> Fen_main.getInstance().pushPage(new ManageCommandes()));
        bStock.addActionListener(e -> Fen_main.getInstance().pushPage(new ManageStock()));
    }

    @Override
    public String getTitre() {
        return "Accueil";
    }
}