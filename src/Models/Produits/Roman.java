package Models.Produits;

import java.util.UUID;

public final class Roman extends Livre {

	private final int tome;

	public Roman(String titre, double tarif_journa, String auteur, int tome) {
		super(titre, tarif_journa, auteur);

		this.tome = tome;
	}

	public Roman(UUID id, String titre, double tarifJourna, String auteur, int tome, boolean emprunte) {
		super(id, titre, tarifJourna, auteur, emprunte);

		this.tome = tome;
	}

	@Override
	public Produit copy() {
		return new Roman(titre, tarifJourna, auteur, tome);
	}

	@Override
	public String getType() {
		return "Roman";
	}

	@Override
	public int hashCode() {
		return produitHashCode() + this.tome;
	}

	@Override
	public String toString() {
		return String.format("%s - %s vol.%s ", auteur, titre, tome);
	}

	public int getTome() {
		return tome;
	}

}