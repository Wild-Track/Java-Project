package Models.Produits;

import java.util.UUID;

public final class Dictionnaire extends Document {

	private final String langue;

	public Dictionnaire(String titre, double tarif_journa, String langue) {
		super(titre, tarif_journa);

		this.langue = langue;
	}

	public Dictionnaire(UUID id, String titre, double tarifJourna, boolean emprunte, String langue) {
		super(id, titre, tarifJourna, emprunte);

		this.langue = langue;
	}

	@Override
	public Produit copy() {
		return new Dictionnaire(titre, tarifJourna, langue);
	}

	@Override
	public String getType() {
		return "Dictionnaire";
	}

	@Override
	public int hashCode() {
		return produitHashCode() + this.langue.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s - %s ", titre, langue);
	}

	public String getLangue() {
		return langue;
	}
}
