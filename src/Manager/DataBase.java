package Manager;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.StreamSupport;

import Main_app.Main;
import Models.Client;
import Models.Commande;
import Models.Emprunt;
import Models.Produits.*;
import Models.Produits.Produit;
import Utils.DateUtils;

public class DataBase {

    private boolean existe;
    private Connection connection;
    private Statement statement;
    private Produit produit;

    public DataBase() {

        Connection connection = null;
        Statement statement = null;

        File tempFile = new File("BdD.db");
        existe = tempFile.exists();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");

            if (!existe) {
                statement = connection.createStatement();
                String setup = "CREATE TABLE client ( id CHAR(50) PRIMARY KEY NOT NULL, "
                        + "nom CHAR(20) NOT NULL, prenom CHAR(20) NOT NULL, fidele  BOOLEAN NOT NULL);"

                        + "CREATE TABLE commande ( id CHAR(50) PRIMARY KEY NOT NULL, "
                        + "date CHAR(12) NOT NULL, reduction DECIMAL(6,3) NOT NULL, idClient CHAR(50) NOT NULL);"

                        + "CREATE TABLE emprunt ( idProduit CHAR(50) NOT NULL, "
                        + "idCommande CHAR(50) NOT NULL, datefin DATE NOT NULL, "
                        + "PRIMARY KEY(idProduit, idCommande));"

                        + "CREATE TABLE produit (id CHAR(50) PRIMARY KEY NOT NULL, "
                        + "tarifJourna DECIMAL(6,3) NOT NULL, titre CHAR(50) NOT NULL, "
                        + "emprunte BOOLEAN NOT NULL, type CHAR(20) NOT NULL, auteur CHAR(25), "
                        + "volume INT, illustrateur CHAR(50), tome INT, anneeManuelScolaire CHAR(25), "
                        + "langue CHAR(25), anneeCD INT, realisateur CHAR(25));";
                statement.executeUpdate(setup);
                statement.close();
                System.out.println("test");
            }

            connection.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    /// Client
    public void loadClient() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String ajoutClientDB = "SELECT * FROM client";
            ResultSet result = statement.executeQuery(ajoutClientDB);

            while (result.next()) {
                UUID id = UUID.fromString(result.getString("id"));
                String nom = result.getString("nom");
                String prenom = result.getString("prenom");
                boolean fidele = result.getBoolean("fidele");

                Main.getInstance().getClientManager().ajoutClientDB(new Client(nom, prenom, fidele, id));
            }

            result.close();
            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ajoutClient(Client client) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String ajoutClient = String.format(
                    "INSERT INTO client(id, nom, prenom, fidele) " + "VALUES('%s', '%s', '%s', %s);",
                    client.getID().toString(), client.getNom(), client.getPrenom(), client.getFidele());
            statement.executeUpdate(ajoutClient);

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void supprClient(Client client) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String ajoutClient = String.format("DELETE FROM CLIENT WHERE id = '%s';", client.getID().toString());
            statement.executeUpdate(ajoutClient);

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /// Commande
    public void loadCommande() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String ajoutCommandeDB = "SELECT * FROM commande";
            ResultSet result = statement.executeQuery(ajoutCommandeDB);

            while (result.next()) {
                UUID id = UUID.fromString(result.getString("id"));
                LocalDate date = DateUtils.numericToLocalDate(result.getString("date"));
                double reduction = result.getDouble("reduction");
                UUID idClient = UUID.fromString(result.getString("idClient"));

                Client client = StreamSupport
                        .stream(Main.getInstance().getClientManager().getClients().spliterator(), false)
                        .filter(c -> c.getID().equals(idClient)).findFirst().get();


                Commande commande = new Commande(id, date, reduction, client);
                Main.getInstance().getCommandeManager().ajoutCommandeDB(commande);
            }

            result.close();
            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ajoutCommande(Commande commande) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String ajoutCommande = String.format(
                "INSERT INTO commande(id, date, reduction, idClient) " + "VALUES('%s', '%s', %s, '%s');",
                commande.getID().toString(), DateUtils.toUsualDateNumeric(
                commande.getDate()), commande.getReduction(), commande.getClient().getID());
            System.out.println(DateUtils.toUsualDateNumeric(commande.getDate()));
            statement.executeUpdate(ajoutCommande);

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void supprCommande(Commande commande) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String supprCommande = String.format(
                    "DELETE FROM commande WHERE id = '%s';", commande.getID());
            statement.executeUpdate(supprCommande);

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ///Emprunt
    public void loadEmprunt() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String ajoutEmpruntDB = "SELECT * FROM emprunt";
            ResultSet result = statement.executeQuery(ajoutEmpruntDB);

            while (result.next()) {
                UUID idCommande = UUID.fromString(result.getString("idCommande"));
                UUID idProduit = UUID.fromString(result.getString("idProduit"));
                LocalDate dateFin = DateUtils.numericToLocalDate(result.getString("dateFin"));


                Produit produit = StreamSupport
                        .stream(Main.getInstance().getStockManager().getProduits().spliterator(), false)
                        .filter(p -> p.getID().equals(idProduit)).findFirst().get();

                Commande commande = StreamSupport
                        .stream(Main.getInstance().getCommandeManager().getCommandes().spliterator(), false)
                        .filter(c -> c.getID().equals(idCommande)).findFirst().get();

                Emprunt emprunt = new Emprunt(dateFin, produit, commande);
                commande.ajoutEmpruntDB(emprunt);
            }

            result.close();
            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void ajoutEmprunt(Emprunt emprunt) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String ajoutEmprunt = String.format(
                "INSERT INTO emprunt(idProduit, idCommande, dateFin) " + "VALUES('%s', '%s', '%s');",
                emprunt.getProduit().getID().toString(),
                emprunt.getCommande().getID().toString(),
                DateUtils.toUsualDateNumeric(emprunt.getDateFin()));
            statement.executeUpdate(ajoutEmprunt);

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void supprEmprunt(Emprunt emprunt) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String supprEmprunt = String.format("DELETE FROM emprunt WHERE idProduit = '%s' AND idCommande = '%s';",
            emprunt.getProduit().getID(),
            emprunt.getCommande().getID());
            System.out.println(emprunt.getProduit().getID().toString());
            statement.executeUpdate(supprEmprunt);

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Produit
    public void loadProduit() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String ajoutProduitDB = "SELECT * FROM produit";
            ResultSet result = statement.executeQuery(ajoutProduitDB);

            while (result.next()) {
                UUID id = UUID.fromString(result.getString("id"));
                double tarifJourna = result.getDouble("tarifJourna");
                String titre = result.getString("titre");
                boolean emprunte = result.getBoolean("emprunte");
                String type = result.getString("type");

                switch(type) {
                    case "BD":
                        String auteurBD = result.getString("auteur");
                        int volume = result.getInt("volume");
                        String illustrateur = result.getString("illustrateur");
                        BD bd = new BD(id ,titre, tarifJourna, emprunte, auteurBD, volume, illustrateur);
                        this.produit = bd;
                        break;

                    case "CD":
                        int anneeCD = result.getInt("anneeCD");
                        CD cd = new CD(id, titre, tarifJourna, emprunte, anneeCD);
                        this.produit = cd;
                        break;

                    case "Dictionnaire":
                        String langue = result.getString("langue");
                        Dictionnaire dictionnaire = new Dictionnaire(id, titre, tarifJourna, emprunte, langue);
                        this.produit = dictionnaire;
                        break;

                    case "DVD":
                        String realisateur = result.getString("realisateur");
                        DVD dvd = new DVD(id, titre, tarifJourna, emprunte, realisateur);
                        this.produit = dvd;
                        break;

                    case "Manuel Scolaire":
                        String anneeManuelScolaire = result.getString("anneeManuelScolaire");
                        String auteurManuelScolaire = result.getString("auteur");
                        ManuelScolaire manuelScolaire = new ManuelScolaire(id, titre, tarifJourna, auteurManuelScolaire, emprunte, anneeManuelScolaire);
                        this.produit = manuelScolaire;
                        break;

                    case "Roman":
                        String auteurRoman = result.getString("auteur");
                        int tome = result.getInt("tome");
                        Roman roman = new Roman(id, titre, tarifJourna, auteurRoman, tome, emprunte);
                        this.produit = roman;
                        break;
                }

                Main.getInstance().getStockManager().ajoutProduitDB(produit);
            }

            result.close();
            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void ajoutProduit(Produit produit) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String ajoutProduit = "";
            String ajoutProduit0 =
                "INSERT INTO produit(id, tarifJourna, titre, emprunte, type, ";
            String ajoutProduit1 = String.format(
                "VALUES('%s', %s, '%s', %s, '%s', ",
                produit.getID(), produit.getTarifJourna(), produit.getTitre(), produit.getEmprunte(),
                produit.getType());

                switch(produit.getType()) {
                    case "BD":
                        ajoutProduit0 += "auteur, volume, illustrateur)";
                        ajoutProduit1 += String.format("'%s', %s, '%s');", ((BD) produit).getAuteur(),
                        ((BD) produit).getVolume(), ((BD) produit).getIllustrateur());
                        break;

                    case "CD":
                        ajoutProduit0 += "anneeCD)";
                        ajoutProduit1 += String.format("'%s');", ((CD) produit).getAnnee());
                        break;

                    case "Dictionnaire":
                        ajoutProduit0 += "langue)";
                        ajoutProduit1 += String.format("'%s');", ((Dictionnaire) produit).getLangue());
                        break;

                    case "DVD":
                        ajoutProduit0 += "realisateur)";
                        ajoutProduit1 += String.format("'%s');", ((DVD) produit).getRealisateur());
                        break;

                    case "Manuel Scolaire":
                        ajoutProduit0 += "auteur, anneeManuelScolaire)";
                        ajoutProduit1 += String.format("'%s', '%s');", ((ManuelScolaire) produit).getAuteur(),
                        ((ManuelScolaire) produit).getAnnee());
                        break;

                    case "Roman":
                        ajoutProduit0 += "auteur, tome)";
                        ajoutProduit1 += String.format("'%s', %s);", ((Roman) produit).getAuteur(),
                        ((Roman) produit).getTome());
                        break;
                    }

            ajoutProduit = ajoutProduit0 + ajoutProduit1;
            statement.executeUpdate(ajoutProduit);

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void supprProduit(Produit produit) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String supprProduit = String.format("DELETE FROM produit WHERE id = '%s';",
                    produit.getID());
            statement.executeUpdate(supprProduit);

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ///Load de la BdD
    public void load() {
        loadClient();
        loadCommande();
        loadProduit();
        loadEmprunt();
    }


    public void setEmprunte(boolean emprunte, UUID id) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String setEmprunte = String.format("UPDATE produit set emprunte = %s WHERE id = '%s'", emprunte, id);
            statement.executeUpdate(setEmprunte);

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setFidele(boolean fidele, UUID id) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = (Connection) DriverManager.getConnection("jdbc:sqlite:BdD.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            String setFidele = String.format("UPDATE client set fidele = %s WHERE id = '%s'", fidele, id);
            statement.executeUpdate(setFidele);

            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}