package Models.Produits;

import java.util.UUID;

public abstract class Livre extends Document {

	protected final String auteur;

	public Livre (String titre, double tarifJourna, String auteur) {
		super(titre, tarifJourna);

		this.auteur = auteur;
	}

	public Livre(UUID id, String titre, double tarifJourna, String auteur, boolean emprunte) {
		super(id, titre, tarifJourna, emprunte);

		this.auteur = auteur;
	}

	@Override
	protected int produitHashCode() {
		return super.produitHashCode() + this.auteur.hashCode();
	}

	public String getAuteur() {
		return auteur;
	}
}
