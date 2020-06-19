package Manager;

import java.util.HashSet;

import Main_app.Main;
import Models.Client;
import Models.Commande;
import Utils.IterableUtils;

public final class CommandeManager {

	private final HashSet<Commande> commandes;

	public CommandeManager() {
		commandes = new HashSet<Commande>();
	}

	public boolean ajoutCommande(Commande commande) {
		Main.getInstance().getDataBase().ajoutCommande(commande);
		return commandes.add(commande);
	}

	public boolean ajoutCommandeDB(Commande commande) {
		return commandes.add(commande);
	}

	public boolean supprCommande(Commande commande) {
		if (commande.getNombreEmprunts() > 0) {
			return false;
		}

		Main.getInstance().getDataBase().supprCommande(commande);
		return commandes.remove(commande);
	}

	public Iterable<Commande> getClientCommandes(Client client) {
		HashSet<Commande> list = new HashSet<Commande>();

		for (Commande commande : commandes) {
			if (commande.getClient().equals(client)) {
				list.add(commande);
			}
		}

		return list;
	}

	public long getClientsCommandesCount(Client client) {
		return IterableUtils.toStream(commandes).filter(c -> c.getClient().equals(client)).count();
	}

	public Iterable<Commande> getCommandes() {
		return commandes;
	}

	public int getNombreCommandes() {
		return commandes.size();
	}
}