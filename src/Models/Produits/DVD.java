package Models.Produits;

import java.util.UUID;

public final class DVD extends Support_numerique {

	private final String realisateur;

	public DVD(String titre, double tarif_journa, String realisateur) {
		super(titre, tarif_journa);

		this.realisateur = realisateur;
	}

	public DVD(UUID id, String titre, double tarifJourna, boolean emprunte, String realisateur) {
		super(id, titre, tarifJourna, emprunte);

		this.realisateur = realisateur;
	}

	@Override
	public Produit copy() {
		return new DVD(titre, tarifJourna, realisateur);
	}

	@Override
	public String getType() {
		return "DVD";
	}

	@Override
	public int hashCode() {
		return produitHashCode() + this.realisateur.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s - %s ", realisateur, titre);
	}

	public String getRealisateur() {
		return realisateur;
	}

}
