package Main_app;

import Graphique.Fen_main;
import Manager.ClientManager;
import Manager.CommandeManager;
import Manager.DataBase;
import Manager.StockManager;

public class Main {
	private static Main instance;

	private DataBase dataBase;
	private ClientManager clientManager;
	private CommandeManager	commandeManager;
	private StockManager stockManager;

	public static void main(String[] args) {
		new Main().main();
	}

	public void main() {
		instance = this;

		dataBase = new DataBase();

		clientManager = new ClientManager();
		commandeManager = new CommandeManager();
		stockManager = new StockManager();

		dataBase.load();

		Fen_main f = new Fen_main();
		f.setVisible(true);
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

	public DataBase getDataBase() {
		return dataBase;
	}
}