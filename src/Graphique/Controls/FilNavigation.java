package Graphique.Controls;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Graphique.Fen_main;
import mdlaf.shadows.DropShadowBorder;
import mdlaf.utils.MaterialColors;

import java.awt.Color;
import java.awt.Component;
import java.util.Stack;


public class FilNavigation extends JPanel {

    private static final long serialVersionUID = 1389233103693651520L;
    Stack<Component> components;
    Stack<Page> pages;
    JButton buttonRetour;
    JButton buttonHome;

    public FilNavigation() {
        super(new FlowLayout(FlowLayout.LEFT));

        setBorder(new DropShadowBorder (MaterialColors.RED_600, 0, 15, 0.3f, 40, false, true, true, true));
        components = new Stack<Component>();
        pages = new Stack<Page>();

        // Autres controls
        buttonRetour = new JButton(new ImageIcon("res/chevron-left(2).png"));
        buttonRetour.setFocusable(false);
        buttonRetour.setBorder(BorderFactory.createEmptyBorder());
        buttonRetour.setContentAreaFilled(false);
        buttonRetour.addActionListener(e -> Fen_main.getInstance().popPage());

        buttonHome = new JButton(new ImageIcon("res/home-outline.png"));
        buttonHome.setFocusable(false);
        buttonHome.setBorder(BorderFactory.createEmptyBorder());
        buttonHome.setContentAreaFilled(false);
        buttonHome.addActionListener(e -> Fen_main.getInstance().clearNavigation());

        add(buttonRetour);
        add(buttonHome);
    }

    public void push(Page page) {
        JLabel fleche = new JLabel(" > ");
        JHyperlinkLabel titre = new JHyperlinkLabel(page.getTitre() + "  ", Color.GRAY, () -> navigate(page));

        titre.setNormalColor(MaterialColors.ORANGE_700);
        pages.add(page);
        components.add(fleche);
        components.add(titre);
        add(fleche);
        add(titre);
        updateUI();
    }

    public void pop() {
        pages.pop();

        remove(components.pop());
        remove(components.pop());
        updateUI();
    }

    public void clear() {
        pages.clear();
        components.clear();
        removeAll();

        add(buttonRetour);
        add(buttonHome);
    }

    private void navigate(Page page) {
        Page stackPage = pages.peek();

        while ((pages.size() > 0) && (page.getTitre() != stackPage.getTitre())) {
            Fen_main.getInstance().popPage();
            stackPage = pages.peek();
        }
    }

}