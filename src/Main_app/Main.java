package Main_app;

import java.time.LocalDate;

import Graphique.Fen_main;
import Manager.ClientManager;
import Manager.CommandeManager;
import Manager.StockManager;
import Models.Client;
import Models.Commande;
import Models.Emprunt;
import Models.Produits.CD;
import Models.Produits.DVD;
import Models.Produits.InfoProduit;
import Models.Produits.ManuelScolaire;

public class Main {
	private static Main instance;

	private ClientManager clientManager;
	private CommandeManager	commandeManager;
	private StockManager stockManager;

	public static void main(String[] args) {
		new Main().main();
	}

	public void main() {
		instance = this;

		clientManager = new ClientManager();
		commandeManager = new CommandeManager();
		stockManager = new StockManager();

		Test();

		Fen_main f = new Fen_main();
		f.setVisible(true);
	}

	private void Test() {
		CD p0 = new CD("Wisp X - Loner", 5.0, 2019);
		DVD p1 = new DVD("Apprendre le Java ", 10, "WildTrack");
		CD p2 = new CD("Wisp X - Loner", 5.0, 2019);
		ManuelScolaire p3 = new ManuelScolaire("Apprendre le Java ", 10, "WildTrack", "1A DUT");
		stockManager.ajoutProduit(p0);
		stockManager.ajoutProduit(p1);
		stockManager.ajoutProduit(p2);
		stockManager.ajoutProduit(p3);


		Client eveldee = new Client("Evel", "Dee", true);
		clientManager.ajoutClient(eveldee);
		Commande c0 = new Commande(0.2, eveldee);
		c0.ajoutEmprunt(new Emprunt(LocalDate.now().plusDays(2), p0, c0));

		Client wildtrack = new Client("Wild", "Track", true);
		clientManager.ajoutClient(wildtrack);
		Commande c1 = new Commande(0.55, wildtrack);
		c1.ajoutEmprunt(new Emprunt(LocalDate.now().plusDays(20), p1, c1));

		Commande c3 = new Commande(0.30, wildtrack);
		c3.ajoutEmprunt(new Emprunt(LocalDate.now().plusDays(50), p3, c3));

		Client cl2 = new Client("Myo", "Nyx", false);
		clientManager.ajoutClient(cl2);
		Commande c2 = new Commande(0.0, cl2);
		c2.ajoutEmprunt(new Emprunt(LocalDate.now().plusDays(4), p2, c2));

		clientManager.ajoutClient(new Client("Prenom", "Nom", false));

		commandeManager.ajoutCommande(c0);
		commandeManager.ajoutCommande(c1);
		commandeManager.ajoutCommande(c2);
		commandeManager.ajoutCommande(c3);

		System.out.println(eveldee);
		System.out.println(c0);

		stockManager.ajoutProduit(new CD("Wisp X - Loner", 5.0, 2019));
		stockManager.ajoutProduit(new CD("Wisp X - Loner", 5.0, 2020));
		stockManager.ajoutProduit(new DVD("Loner", 5.0, "Wisp X"));

		for (InfoProduit infoProduit : stockManager.getInfoProduits()) {
			System.out.println(infoProduit);
		}

	}

	public static Main getInstance() {
		return instance;
	}

	public ClientManager getClientManager() {
		return clientManager;
	}

	public CommandeManager getCommandeManager() {
		return commandeManager;
	}

	public StockManager getStockManager() {
		return stockManager;
	}
}