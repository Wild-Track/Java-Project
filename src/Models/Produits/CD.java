package Models.Produits;

import java.util.UUID;

public final class CD extends Support_numerique {

	private final int annee;

	public CD(String titre, double tarifJourna, int annee) {
		super(titre, tarifJourna);

		this.annee = annee;
	}

	public CD(UUID id, String titre, double tarifJourna, boolean emprunte, int annee) {
		super(id, titre, tarifJourna, emprunte);

		this.annee = annee;
	}

	@Override
	public Produit copy() {
		return new CD(titre, tarifJourna, annee);
	}

	@Override
	public String getType() {
		return "CD";
	}

	@Override
	public int hashCode() {
		return produitHashCode() + this.annee;
	}

	@Override
	public String toString() {
		return String.format("%s (%s) "
				+ "", titre, annee);
	}

	public int getAnnee() {
		return annee;
	}

}
