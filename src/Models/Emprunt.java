package Models;

import java.time.LocalDate;

import Models.Produits.Produit;

public final class Emprunt {

	private LocalDate dateFin;
	private Produit produit;
	private Commande commande;

	public Emprunt(LocalDate dateFin, Produit produit, Commande commande) {

		this.dateFin = dateFin;
		this.produit = produit;
		this.commande = commande;
	}

	@Override
	public String toString() {
		return String.format("%s - %s ", produit.toString(), dateFin);
	}

	public LocalDate getDateDebut() {
		return commande.getDate();
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public Produit getProduit() {
		return produit;
	}

	public Commande getCommande() {
		return commande;
	}
}