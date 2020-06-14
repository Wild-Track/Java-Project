package Graphique.Controls;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import javax.swing.Box;
import javax.swing.BoxLayout;

import Models.Produits.BD;
import Models.Produits.CD;
import Models.Produits.DVD;
import Models.Produits.Dictionnaire;
import Models.Produits.ManuelScolaire;
import Models.Produits.Produit;
import Models.Produits.Roman;
import Utils.DoubleUtils;
import Utils.IntegerUtils;
import Utils.StringUtils;

public class AjoutProduit extends JPanel {

    public Callable<Optional<Produit>> onCreate;

    private static final long serialVersionUID = -2666725037802495902L;
    private Map<String, Callable<JPanel>> panels = Map.of(
        "", this::panelDefault,
        "BD", this::panelBD,
        "CD", this::panelCD,
        "Dictionnaire", this::panelDictionnaire,
        "DVD", this::panelDVD,
        "Manuel Scolaire", this::panelManuelScolaire,
        "Roman", this::panelRoman
    );
    private JPanel panelSupp;
    private JTextField textTitre;
    private JTextField textTarifJourna;

    public AjoutProduit() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        onCreate = () -> Optional.empty();

        JPanel panelProduit = new JPanel();
        panelProduit.setLayout(new BoxLayout(panelProduit, BoxLayout.Y_AXIS));
        JPanel panelTitre = new JPanel(new BorderLayout());
        JPanel panelTarifJourna = new JPanel(new BorderLayout());
        JLabel labelTitre = new JLabel("Titre :  ");
        JLabel labelTarifJourna = new JLabel("Prix :  ");

        textTitre = new JTextField();
        textTarifJourna = new JTextField();
        JComboBox<String> listProduits = new JComboBox<String>(panels.keySet().stream().sorted().toArray(String[]::new));
        listProduits.setSelectedItem("");
        listProduits.addActionListener(e -> addPanelProduit((String) listProduits.getSelectedItem()));

        panelTitre.add(labelTitre, BorderLayout.WEST);
        panelTitre.add(textTitre, BorderLayout.CENTER);

        panelTarifJourna.add(labelTarifJourna, BorderLayout.WEST);
        panelTarifJourna.add(textTarifJourna, BorderLayout.CENTER);

        panelProduit.add(panelTitre);
        panelProduit.add(Box.createVerticalStrut(5));
        panelProduit.add(panelTarifJourna);
        panelProduit.add(Box.createVerticalStrut(20));
        panelProduit.add(listProduits);
        panelProduit.add(Box.createVerticalStrut(5));

        panelProduit.setMaximumSize(new Dimension(2000, 100));
        add(panelProduit);

        panelSupp = panelDefault();
        add(panelSupp);

        setBorder(new EmptyBorder(20, 0, 20, 20));
    }

    private void addPanelProduit(String type) {
        if (panelSupp != null) {
            remove(panelSupp);
        }

        try {
            panelSupp = panels.get(type).call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (panelSupp != null) {
            add(panelSupp);
        }

        updateUI();
    }

    private JPanel panelDefault() {
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut(90));

        return panel;
    }

    private JPanel panelBD() {
        JPanel panel = new JPanel();
        JPanel panelAuteur = new JPanel(new BorderLayout());
        JPanel panelVolume = new JPanel(new BorderLayout());
        JPanel panelIllustrateur = new JPanel(new BorderLayout());

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelAuteur = new JLabel("Auteur :  ");
        JLabel labelVolume = new JLabel("Volume :  ");
        JLabel labelIllustrateur = new JLabel("Illustrateur :  ");
        JTextField textAuteur = new JTextField();
        JTextField textVolume = new JTextField();
        JTextField textIllustrateur = new JTextField();

        panelAuteur.add(labelAuteur, BorderLayout.WEST);
        panelAuteur.add(textAuteur, BorderLayout.CENTER);
        panelVolume.add(labelVolume, BorderLayout.WEST);
        panelVolume.add(textVolume, BorderLayout.CENTER);
        panelIllustrateur.add(labelIllustrateur, BorderLayout.WEST);
        panelIllustrateur.add(textIllustrateur, BorderLayout.CENTER);

        panel.setMaximumSize(new Dimension(2000, 90));
        panel.add(Box.createVerticalStrut(5));
        panel.add(panelAuteur);
        panel.add(Box.createVerticalStrut(5));
        panel.add(panelVolume);
        panel.add(Box.createVerticalStrut(5));
        panel.add(panelIllustrateur);
        panel.add(Box.createVerticalStrut(5));

        onCreate = () -> {
            if (StringUtils.isOneEmpty(textTitre, textTarifJourna, textAuteur, textVolume, textIllustrateur)) {
                return Optional.empty();
            }
            if (!DoubleUtils.isParsable(textTarifJourna.getText())) {
                return Optional.empty();
            }
            if (!IntegerUtils.isNumeric(textVolume.getText())) {
                return Optional.empty();
            }

            return Optional.of(new BD(textTitre.getText(), Double.parseDouble(textTarifJourna.getText()), textAuteur.getText(), Integer.parseInt(textVolume.getText()), textIllustrateur.getText()));
        };

        return panel;
    }

    private JPanel panelCD() {
        JPanel panel = new JPanel();
        JPanel panelAnnee = new JPanel(new BorderLayout());

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelAnnee = new JLabel("Année :  ");
        JTextField textAnnee = new JTextField();

        panelAnnee.add(labelAnnee, BorderLayout.WEST);
        panelAnnee.add(textAnnee, BorderLayout.CENTER);

        panel.setMaximumSize(new Dimension(2000, 30));
        panel.add(Box.createVerticalStrut(5));
        panel.add(panelAnnee, BorderLayout.NORTH);

        onCreate = () -> {
            if (StringUtils.isOneEmpty(textTitre, textTarifJourna, textAnnee)) {
                return Optional.empty();
            }
            if (!DoubleUtils.isParsable(textTarifJourna.getText())) {
                return Optional.empty();
            }
            if (!IntegerUtils.isNumeric(textAnnee.getText())) {
                return Optional.empty();
            }

            return Optional.of(new CD(textTitre.getText(), Double.parseDouble(textTarifJourna.getText()), Integer.parseInt(textAnnee.getText())));
        };

        return panel;
    }

    private JPanel panelDictionnaire() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelLangue = new JPanel(new BorderLayout());

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelLangue = new JLabel("Langue :  ");
        JTextField textLangue = new JTextField();

        panelLangue.add(labelLangue, BorderLayout.WEST);
        panelLangue.add(textLangue, BorderLayout.CENTER);

        panel.setMaximumSize(new Dimension(2000, 30));
        panel.add(Box.createVerticalStrut(5));
        panel.add(panelLangue, BorderLayout.NORTH);

        onCreate = () -> {
            if (StringUtils.isOneEmpty(textTitre, textTarifJourna, textLangue)) {
                return Optional.empty();
            }
            if (!DoubleUtils.isParsable(textTarifJourna.getText())) {
                return Optional.empty();
            }

            return Optional.of(new Dictionnaire(textTitre.getText(), Double.parseDouble(textTarifJourna.getText()), textLangue.getText()));
        };

        return panel;
    }

    private JPanel panelDVD() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelRealisateur = new JPanel(new BorderLayout());

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelRealisateur = new JLabel("Réalisateur :  ");
        JTextField textRealisateur = new JTextField();

        panelRealisateur.add(labelRealisateur, BorderLayout.WEST);
        panelRealisateur.add(textRealisateur, BorderLayout.CENTER);

        panel.setMaximumSize(new Dimension(2000, 30));
        panel.add(Box.createVerticalStrut(5));
        panel.add(panelRealisateur, BorderLayout.NORTH);

        onCreate = () -> {
            if (StringUtils.isOneEmpty(textTitre, textTarifJourna, textRealisateur)) {
                return Optional.empty();
            }
            if (!DoubleUtils.isParsable(textTarifJourna.getText())) {
                return Optional.empty();
            }

            return Optional.of(new DVD(textTitre.getText(), Double.parseDouble(textTarifJourna.getText()), textRealisateur.getText()));
        };

        return panel;
    }

    private JPanel panelManuelScolaire() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelAuteur = new JPanel(new BorderLayout());
        JPanel panelAnnee = new JPanel(new BorderLayout());

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelAuteur = new JLabel("Auteur :  ");
        JLabel labelAnnee = new JLabel("Année  :  ");
        JTextField textAuteur = new JTextField();
        JTextField textAnnee = new JTextField();

        panelAuteur.add(labelAuteur, BorderLayout.WEST);
        panelAuteur.add(textAuteur, BorderLayout.CENTER);
        panelAnnee.add(labelAnnee, BorderLayout.WEST);
        panelAnnee.add(textAnnee, BorderLayout.CENTER);

        panel.setMaximumSize(new Dimension(2000, 60));
        panel.add(Box.createVerticalStrut(5));
        panel.add(panelAuteur, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(5));
        panel.add(panelAnnee, BorderLayout.CENTER);

        onCreate = () -> {
            if (StringUtils.isOneEmpty(textTitre, textTarifJourna, textAuteur, textAnnee)) {
                return Optional.empty();
            }
            if (!DoubleUtils.isParsable(textTarifJourna.getText())) {
                return Optional.empty();
            }

            return Optional.of(new ManuelScolaire(textTitre.getText(), Double.parseDouble(textTarifJourna.getText()),
                    textAuteur.getText(), textAnnee.getText()));
        };

        return panel;
    }

    private JPanel panelRoman() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelAuteur = new JPanel(new BorderLayout());
        JPanel panelVolume = new JPanel(new BorderLayout());

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelAuteur = new JLabel("Auteur :  ");
        JLabel labelTome = new JLabel("Tome :  ");
        JTextField textAuteur = new JTextField();
        JTextField textTome = new JTextField();

        panelAuteur.add(labelAuteur, BorderLayout.WEST);
        panelAuteur.add(textAuteur, BorderLayout.CENTER);
        panelVolume.add(labelTome, BorderLayout.WEST);
        panelVolume.add(textTome, BorderLayout.CENTER);

        panel.setMaximumSize(new Dimension(2000, 60));
        panel.add(Box.createVerticalStrut(5));
        panel.add(panelAuteur, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(5));
        panel.add(panelVolume, BorderLayout.CENTER);

        onCreate = () -> {
            if (StringUtils.isOneEmpty(textTitre, textTarifJourna, textAuteur, textTome)) {
                return Optional.empty();
            }
            if (!DoubleUtils.isParsable(textTarifJourna.getText())) {
                return Optional.empty();
            }
            if (!IntegerUtils.isNumeric(textTome.getText())) {
                return Optional.empty();
            }

            return Optional.of(new Roman(textTitre.getText(), Double.parseDouble(textTarifJourna.getText()),
                    textAuteur.getText(), Integer.parseInt(textTome.getText())));
        };

        return panel;
    }

}