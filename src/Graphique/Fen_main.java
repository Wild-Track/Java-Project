package Graphique;

import javax.swing.*;
import javax.swing.border.Border;

import Graphique.Controls.*;

import java.util.Stack;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import Graphique.Pages.*;
import mdlaf.*;
import mdlaf.themes.JMarsDarkTheme;
import mdlaf.utils.MaterialColors;

public class Fen_main extends JFrame {

    private static final long serialVersionUID = 9085551139478731152L;

    private static Fen_main instance;

    private JPanel page;
    private Accueil accueil;
    private FilNavigation filNavigation;
    private Stack<Page> navigationStack;

    public Fen_main() {
        instance = this;

        this.setIconImage(new ImageIcon("res/logo.png").getImage());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
            MaterialLookAndFeel.changeTheme(new JMarsDarkTheme());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        ///////////////
        // Lien du thÃ¨me utilisÃ© https://github.com/atarw/material-ui-swing
        // Comprend la classe JHyperlinkLabel.java, DropShadowBorder ainsi que
        // material-ui-swing-1.1.1_pre-release_6.1-jar-with-dependencies.jar

        this.navigationStack = new Stack<Page>();
        this.filNavigation = new FilNavigation();
        this.page = new JPanel(new BorderLayout());
        this.page.setBorder(
                (Border) new DropShadowBorder(MaterialColors.RED_600, 0, 15, 0.3f, 40, true, true, true, true));
        this.accueil = new Accueil();

        this.setLayout(new BorderLayout());
        this.setTitle("Gestion Vidéothèque");
        this.setSize(1150, 700);
        this.setLocation(((int) (screenSize.getWidth() - 1150)/2), (int) ((screenSize.getHeight() - 700)/2));

        this.add(filNavigation, BorderLayout.NORTH);

        this.add(page, BorderLayout.CENTER);

        pushPage(accueil);
    }

    public void pushPage(Page page) {
        this.navigationStack.add(page);
        this.filNavigation.push(page);

        this.page.removeAll();
        this.page.add(page);
        this.page.updateUI();
    }

    public void popPage() {
        this.navigationStack.pop();
        this.filNavigation.pop();

        if (navigationStack.size() < 1) {
            clearNavigation();
        }
        else {
            Page pageToPush = navigationStack.peek();

            this.page.removeAll();

            this.page.add(pageToPush);
            pageToPush.onRefresh();

            this.page.updateUI();
        }
    }

    public void clearNavigation() {
        this.navigationStack.clear();
        this.filNavigation.clear();

        pushPage(accueil);
    }

    public static Fen_main getInstance() {
        return instance;
    }

    public JPanel getCurrentPage() {
        return this.navigationStack.peek();
    }

}