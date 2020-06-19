package Models.Produits;

import java.util.UUID;

public abstract class Document extends Produit {

	public Document(String titre, double tarifJourna) {
		super(titre, tarifJourna);
	}

	public Document(UUID id, String titre, double tarifJourna, boolean emprunte) {
		super(id, titre, tarifJourna, emprunte);
	}

}
