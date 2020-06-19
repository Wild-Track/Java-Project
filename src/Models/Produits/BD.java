package Models.Produits;

import java.util.UUID;

public final class BD extends Livre {

	private final int volume;
	private final String illustrateur;

	public BD(String titre, double tarifJourna, String auteur, int volume, String illustrateur) {
		super(titre, tarifJourna, auteur);

		this.volume = volume;
		this.illustrateur = illustrateur;
	}

	public BD(UUID id, String titre, double tarifJourna, boolean emprunte, String auteur, int volume, String illustrateur) {
		super(id, titre, tarifJourna, auteur, emprunte);

		this.volume = volume;
		this.illustrateur = illustrateur;
	}

	@Override
	public Produit copy() {
		return new BD(titre, tarifJourna, auteur, volume, illustrateur);
	}

	@Override
	public String getType() {
		return "BD";
	}

	@Override
	public int hashCode() {
		return produitHashCode() + volume + this.illustrateur.hashCode();
	}

	@Override
	public String toString() {
		return String.format("%s - %s n° %s ", auteur, titre, volume);
	}

	public int getVolume() {
		return volume;
	}

	public String getIllustrateur(){
		return illustrateur;
	}

}