package Graphique.Pages;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Manager.ClientManager;
import Models.Client;
import Utils.IterableUtils;
import Graphique.Controls.ClientCell;
import Graphique.Controls.DropShadowBorder;
import Graphique.Controls.Page;
import Main_app.Main;

public class ManageClients extends Page {

    private static final long serialVersionUID = -5300935375873880893L;
    private ClientManager clientManager;
    private JScrollPane scrollPane;
    private JTextField textRecherche;
    private int i;

    public ManageClients() {
        super(new BorderLayout());
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

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton buttonAjouter = new JButton("AJOUTER");
        buttonAjouter.addActionListener(e -> ajouterClient());
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

        IterableUtils.toStream(clientManager.getClients())
                .filter(c -> textRecherche.getText() == "" ? true : c.getNom().toLowerCase().contains(textRecherche
                        .getText().toLowerCase()))
                .sorted((a, b) -> a.getNom().compareToIgnoreCase(b.getNom()))
                .forEach(client -> {
                    list.add(new ClientCell(client, i, () -> supprimerClient(client)));
                    i++;
                });

        scrollPane = new JScrollPane(list);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);

        updateUI();
    }

    private void supprimerClient(Client client) {

        JLabel question = new JLabel(
                String.format("Voulez-vous vraiment supprimer le client  %s %s?                    ", client.getNom(),client.getPrenom()));
        JPanel panelPop = new JPanel(new FlowLayout());
        panelPop.add(question);

        int option = JOptionPane.showConfirmDialog(this, panelPop, "Suppression client", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            if (!clientManager.supprClient(client)) {
                JOptionPane.showMessageDialog(this, "        Ce client a encore des commandes en cours! ", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else {
                drawList();
                updateUI();
            }
        }
    }

    private void ajouterClient() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelPrenom = new JPanel(new BorderLayout());
        JPanel panelNom = new JPanel(new BorderLayout());
        JPanel panelFidele = new JPanel(new BorderLayout());
        JTextField textPrenom = new JTextField();
        JTextField textNom = new JTextField();
        JLabel labelPrenom = new JLabel("Prénom : ");
        JLabel labelNom = new JLabel("Nom : ");
        JLabel labelFidele = new JLabel("Fidèle :");
        JCheckBox fideleCheckBox = new JCheckBox();

        panelPrenom.add(textPrenom, BorderLayout.CENTER);
        panelPrenom.add(labelPrenom, BorderLayout.WEST);
        panelNom.add(textNom, BorderLayout.CENTER);
        panelNom.add(labelNom, BorderLayout.WEST);
        panelFidele.add(fideleCheckBox, BorderLayout.CENTER);
        panelFidele.add(labelFidele, BorderLayout.WEST);
        panel.add(panelPrenom, BorderLayout.NORTH);
        panel.add(panelNom, BorderLayout.CENTER);
        panel.add(panelFidele, BorderLayout.SOUTH);

        panel.setBorder(new EmptyBorder(20, 0, 20, 20));

        int option = JOptionPane.showConfirmDialog(this, panel, "Ajout client", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            if (!(textNom.getText().isBlank()) && !(textPrenom.getText().isBlank())) {
                if (clientManager.ajoutClient(new Client(textNom.getText(), textPrenom.getText(), fideleCheckBox.isSelected()))) {
                    drawList();
                    updateUI();
                }
                else {
                    JOptionPane.showMessageDialog(this, "        Le client existe déja! ", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez renseigner tout les champs !             ", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public String getTitre() {
        return "Gestion des clients";
    }

}