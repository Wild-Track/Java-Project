package Models.Produits;

public final class ManuelScolaire extends Livre {

	private final String annee;

	public ManuelScolaire(String titre, double tarif_journa, String auteur, String annee) {
		super(titre, tarif_journa, auteur);

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
