package Graphique.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Dimension;

import Graphique.Controls.AjoutProduit;
import Graphique.Controls.DropShadowBorder;
import Graphique.Controls.Page;
import Graphique.Controls.ProduitCell;
import Main_app.Main;
import Manager.StockManager;
import Models.Produits.InfoProduit;
import Models.Produits.Produit;
import Utils.IterableUtils;

public class ManageStock extends Page {

    private static final long serialVersionUID = -5300935375873880893L;
    private JScrollPane scrollPane;
    private JTextField textRecherche;
    private JComboBox<String> typeProduit;
    private int i;
    private StockManager stockManager;
    private String[] typesProduits = {
        "BD",
        "CD",
        "Dictionnaire",
        "DVD",
        "Manuel Scolaire",
        "Produit",
        "Roman"
    };

    public ManageStock() {
        super(new BorderLayout());
        this.stockManager = Main.getInstance().getStockManager();

        draw();
    }

    private void draw() {
        JPanel entete = new JPanel();
        JPanel rech = new JPanel();
        textRecherche = new JTextField();
        textRecherche.addActionListener(e -> drawList());
        JButton buttonRecherche = new JButton(new ImageIcon("res/magnify.png"));
        buttonRecherche.addActionListener(e -> drawList());

        typeProduit = new JComboBox<String>(typesProduits);
        typeProduit.setSelectedItem("Produit");
        typeProduit.setPreferredSize(new Dimension(200, 10));
        typeProduit.addActionListener(e -> drawList());

        textRecherche.setColumns(13);
        rech.add(textRecherche, BorderLayout.EAST);
        rech.add(buttonRecherche, BorderLayout.EAST);

        entete.setLayout(new BorderLayout());
        entete.setBorder(new DropShadowBorder(Color.BLACK, 5, 15, 0.3f, 10, true, true, true, true));
        entete.add(typeProduit, BorderLayout.WEST);
        entete.add(rech, BorderLayout.EAST);

        add(entete, BorderLayout.NORTH);

        drawList();

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton buttonAjouter = new JButton("AJOUTER");
        buttonAjouter.addActionListener(e -> ajouterProduit());
        controls.add(buttonAjouter);

        add(controls, BorderLayout.SOUTH);
    }

    private void drawList() {
        i = 0;

        if (scrollPane != null) {
            remove(scrollPane);
        }

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        IterableUtils.toStream(stockManager.getInfoProduits())
                .filter(ip -> textRecherche.getText() == "" ? true
                        : ip.getTitre().toLowerCase().contains(textRecherche.getText().toLowerCase()))
                .filter(ip -> (String) typeProduit.getSelectedItem() == "Produit" ? true
                        : ip.getType() == (String) typeProduit.getSelectedItem())
                .sorted((a, b) -> a.getTitre().compareToIgnoreCase(b.getTitre())).forEach(ip -> {
                    list.add(new ProduitCell(ip, i, () -> diminuerQuantiteProduit(ip),
                            () -> augmenterQuantiteProduit(ip)));
                    i++;
                });

        scrollPane = new JScrollPane(list);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);

        updateUI();
    }

    private void diminuerQuantiteProduit(InfoProduit infoProduit) {
        Optional<Produit> produit = infoProduit.getPremierProduitDisponible();

        if (produit.isPresent()) {
            JLabel question = new JLabel(String.format(
                    "Voulez-vous vraiment supprimer le produit ?                    ", infoProduit.getTitre()));
            if (infoProduit.getQuantiteTotal() == 1) {
                JPanel panelPop = new JPanel(new FlowLayout());
                panelPop.add(question);

                int option = JOptionPane.showConfirmDialog(this, panelPop, "Suppression produit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    if (!stockManager.supprProduit(produit.get())) {
                        JOptionPane.showMessageDialog(this, "        Impossible de supprimer ce produit ! ", "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        drawList();
                    }
                }
            } else {
                stockManager.supprProduit(produit.get());
                drawList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Les produits sont encore emprunt!!           ", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void augmenterQuantiteProduit(InfoProduit infoProduit) {
        Optional<Produit> produit = infoProduit.creerProduit();

        if (produit.isPresent()) {
            stockManager.ajoutProduit(produit.get());

            drawList();
        }
    }

    private void ajouterProduit() {
        AjoutProduit ajoutProduit = new AjoutProduit();

        int option = JOptionPane.showConfirmDialog(this, ajoutProduit, "Ajout produit", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            Optional<Produit> produit = null;

            try {
                produit = ajoutProduit.onCreate.call();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!produit.isPresent()) {
                JOptionPane.showMessageDialog(this, "            Produit invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (stockManager.ajoutProduit(produit.get())) {
                drawList();
            } else {
                JOptionPane.showMessageDialog(this, "        Impossible d'ajouter le produit ! ", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public String getTitre() {
        return "Gestion du stock";
    }

}