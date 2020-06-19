package Models.Produits;

import java.util.UUID;

public abstract class Support_numerique extends Produit{

	public Support_numerique(String titre, double tarifJourna) {
		super(titre, tarifJourna);
	}

	public Support_numerique(UUID id, String titre, double tarifJourna, boolean emprunte) {
		super(id, titre, tarifJourna, emprunte);
	}
}