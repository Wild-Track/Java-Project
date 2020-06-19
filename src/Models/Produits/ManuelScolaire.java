package Models.Produits;

import java.util.UUID;

public final class ManuelScolaire extends Livre {

	private final String annee;

	public ManuelScolaire(String titre, double tarifJourna, String auteur, String annee) {
		super(titre, tarifJourna, auteur);

		this.annee = annee;
	}

	public ManuelScolaire(UUID id, String titre, double tarifJourna, String auteur, boolean emprunte, String annee) {
		super(id, titre, tarifJourna, auteur, emprunte);

		this.annee = annee;
	}

	@Override
	public Produit copy() {
		return new ManuelScolaire(titre, tarifJourna, auteur, annee);
	}

	@Override
	public String getType() {
		return "Manuel Scolaire";
	}

	@Override
	public int hashCode() {
		return produitHashCode() + this.annee.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s - %s (%s) ", auteur, titre, annee);
	}

	public String getAnnee() {
		return annee;
	}


}
