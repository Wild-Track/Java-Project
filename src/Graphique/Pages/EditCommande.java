package Graphique.Pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;
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
import javax.swing.border.EmptyBorder;

import Graphique.Controls.DropShadowBorder;
import Graphique.Controls.EmpruntCell;
import Graphique.Controls.ImpressionCommande;
import Graphique.Controls.Page;
import Main_app.Main;
import Manager.StockManager;
import Models.Commande;
import Models.Emprunt;
import Models.Produits.InfoProduit;
import Models.Produits.Produit;
import Utils.IntegerUtils;
import Utils.IterableUtils;

public class EditCommande extends Page {

    private static final long serialVersionUID = -5300935375873880893L;
    private JScrollPane scrollPane;
    private JTextField textRecherche;
    private int i;
    private Commande commande;
    private StockManager stockManager;

    public EditCommande(Commande commande) {
        super(new BorderLayout());
        this.commande = commande;
        this.stockManager = Main.getInstance().getStockManager();

        draw();
    }

    private void draw() {
        JPanel entete = new JPanel();
        JPanel rech = new JPanel();
        JPanel panelImprimer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        textRecherche = new JTextField();
        textRecherche.addActionListener(e -> drawList());
        JButton buttonRecherche = new JButton(new ImageIcon("res/magnify.png"));
        buttonRecherche.addActionListener(e -> drawList());
        JButton buttonImprimer = new JButton(new ImageIcon("res/printer.png"));
        buttonImprimer.setMaximumSize(new Dimension(15, 30));
        buttonImprimer.setFocusable(false);
        buttonImprimer.addActionListener(e -> impressionCommande());

        JLabel infos = new JLabel(String.format("Client : %s  | Commande n°: %s   " , commande.getClient().toString(), commande.getID()));

        textRecherche.setColumns(13);
        rech.add(textRecherche, BorderLayout.EAST);
        rech.add(buttonRecherche, BorderLayout.EAST);
        panelImprimer.add(buttonImprimer);

        entete.setLayout(new BorderLayout());
        entete.setBorder(new DropShadowBorder(Color.BLACK, 5, 15, 0.3f, 10, true, true, true, true));
        entete.add(infos, BorderLayout.WEST);
        entete.add(panelImprimer, BorderLayout.CENTER);
        entete.add(rech, BorderLayout.EAST);

        add(entete, BorderLayout.NORTH);

        drawList();

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton buttonAjouter = new JButton("AJOUTER");
        buttonAjouter.addActionListener(e -> ajouterEmprunt());
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

    IterableUtils.toStream(commande.getEmprunts()).filter(e -> textRecherche.getText() == "" ? true
    : e.getProduit().getTitre().toLowerCase().contains(textRecherche.getText().toLowerCase()))
    .sorted((a, b) -> a.getProduit().getTitre().compareToIgnoreCase(b.getProduit().getTitre()))
            .forEach(emprunt -> {
                list.add(new EmpruntCell(emprunt, i, () -> supprimerEmprunt(emprunt)));
                i++;
            });

            scrollPane = new JScrollPane(list);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            add(scrollPane, BorderLayout.CENTER);

            updateUI();
        }

    private void supprimerEmprunt(Emprunt emprunt) {

        JLabel question = new JLabel(
        String.format("Voulez-vous vraiment supprimer l'emprunt '%s'?                    ", emprunt.getProduit()));
        JPanel panelPop = new JPanel(new FlowLayout());
        panelPop.add(question);

        int option = JOptionPane.showConfirmDialog(this, panelPop, "Suppression emprunt", JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            emprunt.getCommande().supprEmprunt(emprunt);

            drawList();
            updateUI();
        }
    }

    private void ajouterEmprunt() {

        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelProduit = new JPanel(new BorderLayout());
        JPanel panelDate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<InfoProduit> listProduits = new JComboBox<InfoProduit>(IterableUtils.toStream(stockManager.getInfoProduits())
        .filter(p -> p.getQuantiteDisponible() > 0)
        .toArray(InfoProduit[]::new));
        JTextField textJour = new JTextField("0");
        JTextField textSemaine = new JTextField("0");
        JTextField textMois = new JTextField("0");
        JLabel labelProduit = new JLabel("Produit : ");
        JLabel labelJour = new JLabel("Jours : ");
        JLabel labelSemaine = new JLabel("Semaine : ");
        JLabel labelMois = new JLabel("Mois : ");

        textJour.setColumns(5);
        textSemaine.setColumns(5);
        textMois.setColumns(5);

        panelProduit.add(labelProduit, BorderLayout.WEST);
        panelProduit.add(listProduits, BorderLayout.CENTER);
        panelDate.add(labelJour);
        panelDate.add(textJour);
        panelDate.add(labelSemaine);
        panelDate.add(textSemaine);
        panelDate.add(labelMois);
        panelDate.add(textMois);
        panel.add(panelProduit, BorderLayout.NORTH);
        panel.add(panelDate, BorderLayout.CENTER);

        panel.setBorder(new EmptyBorder(20, 0, 20, 20));
        int option = JOptionPane.showConfirmDialog(this, panel, "Ajout client", JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE);

        String jours = textJour.getText();
        String semaines = textSemaine.getText();
        String mois = textMois.getText();

        if (option == JOptionPane.YES_OPTION) {
            if (IntegerUtils.isNumeric(jours) && IntegerUtils.isNumeric(semaines) && IntegerUtils.isNumeric(mois)) {
                Optional<Produit> produit = ((InfoProduit)listProduits.getSelectedItem()).getPremierProduitDisponible();

                if (!produit.isPresent()) {
                    JOptionPane.showMessageDialog(this, "        Ce produit n'est pas disponible ! ", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                long j = Long.parseLong(jours);
                long s = Long.parseLong(semaines);
                long m = Long.parseLong(mois);

                if (j + s + m < 1) {
                    JOptionPane.showMessageDialog(this, "        Durée invalide ! ", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate dateFin = LocalDate.now().plusDays(j).plusWeeks(s).plusMonths(m);

                if (commande.ajoutEmprunt(new Emprunt(dateFin, produit.get(), commande))) {
                    drawList();
                    updateUI();
                } else {
                    JOptionPane.showMessageDialog(this, "        L'emprunt existe déja ! ", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez renseigner tout les champs !             ", "Erreur",
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void impressionCommande() {
        new ImpressionCommande(this.commande);
    }

    @Override
    public String getTitre() {
        return "une commande";
    }

}