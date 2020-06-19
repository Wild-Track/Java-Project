package Manager;

import java.util.HashSet;

import Main_app.Main;
import Models.Client;

public final class ClientManager {

	private final HashSet<Client> clients;

	public ClientManager() {
		clients = new HashSet<Client>();
	}

	public boolean ajoutClient(Client client) {
		Main.getInstance().getDataBase().ajoutClient(client);
		return clients.add(client);
	}

	public boolean ajoutClientDB(Client client) {
		return clients.add(client);
	}

	public boolean supprClient(Client client) {
		if (Main.getInstance().getCommandeManager().getClientsCommandesCount(client) > 0) {
			return false;
		}

		Main.getInstance().getDataBase().supprClient(client);
		return clients.remove(client);
	}

	public Iterable<Client> getClients() {
		return clients;
	}
}