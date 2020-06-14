package Graphique.Pages;

import Graphique.Controls.Page;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Manager.ClientManager;
import Manager.CommandeManager;
import Models.Client;
import Models.Commande;
import Utils.DoubleUtils;
import Utils.IterableUtils;
import Graphique.Fen_main;
import Graphique.Controls.CommandeTable;
import Graphique.Controls.DropShadowBorder;
import Main_app.Main;

public class ManageCommandes extends Page {

    private static final long serialVersionUID = -3917086846757739397L;
    private CommandeManager commandManager;
    private ClientManager clientManager;
    private JScrollPane scrollPane;
    private JTextField textRecherche;
    private JTable table;
    private CommandeTable tableModel;

    public ManageCommandes() {
        super(new BorderLayout());
        commandManager = Main.getInstance().getCommandeManager();
        clientManager = Main.getInstance().getClientManager();

        draw();
    }

    private void draw() {
        JPanel rech = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        textRecherche = new JTextField();
        textRecherche.addActionListener(e -> drawList());
        JButton buttonRecherche = new JButton(new ImageIcon("res/magnify.png"));
        buttonRecherche.addActionListener(e -> drawList());

        textRecherche.setColumns(13);
        rech.setBorder(new DropShadowBorder(Color.BLACK, 5, 15, 0.3f, 10, true, true, true, true));
        rech.add(textRecherche, BorderLayout.EAST);
        rech.add(buttonRecherche, BorderLayout.EAST);
        add(rech, BorderLayout.NORTH);

        drawList();

        FlowLayout layout = new FlowLayout(FlowLayout.RIGHT);
        layout.setHgap(50);
        JPanel controls = new JPanel(layout);

        JPanel controls2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton buttonSupprimer = new JButton("SUPPRIMER");
        buttonSupprimer.addActionListener(e -> supprimerCommande());
        controls2.add(buttonSupprimer);

        JButton buttonModifier = new JButton("MODIFIER");
        buttonModifier.addActionListener(e -> modifierCommande());
        controls2.add(buttonModifier);
        controls.add(controls2);

        JButton buttonAjouter = new JButton("AJOUTER");
        buttonAjouter.setMargin(new Insets(0, 50, 0, 0));
        buttonAjouter.addActionListener(e -> ajouterCommande());
        controls.add(buttonAjouter);

        add(controls, BorderLayout.SOUTH);
    }


    private void drawList() {
        CommandeTable commandeTable = new CommandeTable(commandManager, textRecherche.getText());
        JTable tableCommande = new JTable(commandeTable);

        if (scrollPane != null) {
            remove(scrollPane);
        }

        tableCommande.setFillsViewportHeight(true);
        tableCommande.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableCommande.getTableHeader().setReorderingAllowed(false);
        scrollPane = new JScrollPane(tableCommande);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        tableCommande.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount() == 2 && tableCommande.getSelectedRow() != -1) {
                    modifierCommande();
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        table = tableCommande;
        tableModel = commandeTable;

        updateUI();
    }

    private void supprimerCommande() {
        int selected = table.getSelectedRow();

        if (selected == -1) {
            return;
        }
        Commande commande = tableModel.getCommandAt(selected);

        if (commande == null) {
            return;
        }

        if (commandManager.supprCommande(commande)) {
            drawList();
        }
        else {
            JOptionPane.showMessageDialog(null, "Veuillez supprimer tout les emprunts!            ", "Erreur",
            JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifierCommande() {
        int selected = table.getSelectedRow();

        if (selected == -1) {
            return;
        }

        Commande commande = tableModel.getCommandAt(selected);

        if (commande != null) {
            Fen_main.getInstance().pushPage(new EditCommande(commande));
        }
    }

    private void ajouterCommande() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelClient = new JPanel(new BorderLayout());
        JPanel panelReduction = new JPanel(new BorderLayout());
        JComboBox<Client> listClients = new JComboBox<Client>(IterableUtils.toArray(clientManager.getClients(), Client[]::new));
        JTextField textReduction = new JTextField();
        JLabel labelClient = new JLabel("Client : ");
        JLabel labelReduction = new JLabel("Réduction (en %) : ");

        listClients.setEditable(true);
        listClients.setSelectedItem("");
        AutoCompleteDecorator.decorate(listClients);

        panelClient.add(listClients, BorderLayout.CENTER);
        panelClient.add(labelClient, BorderLayout.WEST);
        panelReduction.add(textReduction, BorderLayout.CENTER);
        panelReduction.add(labelReduction, BorderLayout.WEST);
        panel.add(panelClient, BorderLayout.NORTH);
        panel.add(panelReduction, BorderLayout.CENTER);

        panel.setBorder(new EmptyBorder(20, 0, 20, 20));

        int option = JOptionPane.showConfirmDialog(this, panel, "Ajout commande", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            if (listClients.getSelectedIndex() != -1 && !(textReduction.getText().isBlank()) && DoubleUtils.isParsable(textReduction.getText())) {
                double reduction = Double.parseDouble(textReduction.getText());

                if (reduction < 0 || reduction > 100) {
                    JOptionPane.showMessageDialog(this, "           Réduction invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Commande commande = new Commande(reduction / 100, (Client)listClients.getSelectedItem());

                if (commandManager.ajoutCommande(commande)) {
                    drawList();

                    Fen_main.getInstance().pushPage(new EditCommande(commande));
                }
                else {
                    JOptionPane.showMessageDialog(this, "        Cette commande exisste déja ! ", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Veuillez renseigner tout les champs !             ", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void onRefresh() {
        if (commandManager != null) {
            drawList();
            System.out.println(String.join("\n", IterableUtils.toStream(commandManager.getCommandes()).map(c -> c.toString()).toArray(String[]::new)));
        }
    }

    @Override
    public String getTitre() {
        return "Gestion des commandes";
    }

    public String getSearch() {
        return textRecherche.getText();
    }
}