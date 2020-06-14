package Models.Produits;

public final class CD extends Support_numerique {

	private final int annee;

	public CD(String titre, double tarif_journa, int annee) {
		super(titre, tarif_journa);

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
