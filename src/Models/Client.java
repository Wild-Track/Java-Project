package Models;

import java.util.UUID;

import Main_app.Main;

public final class Client {

	public static final double REDUCTION_FIDELE = 0.1;

	private final UUID id;
	private final String nom;
	private final String prenom;
	private boolean fidele;

	public Client(String n, String p, boolean fidele) {
		this.id = UUID.randomUUID();
		this.nom = n;
		this.prenom = p;
		this.fidele = fidele;
	}

	public Client(String n, String p, boolean fidele, UUID uuid) {
		this.id = uuid;
		this.nom = n;
		this.prenom = p;
		this.fidele = fidele;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Client)) {
			return false;
		}

		Client autre = (Client)obj;

		return this.hashCode() == autre.hashCode();
	}

	@Override
	public int hashCode() {
		return nom.hashCode() + prenom.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s %s", nom.toUpperCase(), prenom);
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public UUID getID() {
		return id;
	}

	public boolean getFidele() {
		return fidele;
	}

	public void setFidele(boolean fidele) {
		this.fidele = fidele;
		Main.getInstance().getDataBase().setFidele(fidele, id);
	}

}