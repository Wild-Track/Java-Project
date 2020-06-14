package Models.Produits;

public abstract class Livre extends Document {

	protected final String auteur;

	public Livre (String titre, double tarif_journa, String auteur) {
		super(titre, tarif_journa);

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
