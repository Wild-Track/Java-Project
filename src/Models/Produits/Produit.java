package Models.Produits;

import java.util.UUID;

import Main_app.Main;

public abstract class Produit {

	protected final UUID id;
	protected final String titre;
	protected final double tarifJourna;
	protected boolean emprunte;

	public Produit(String titre, double tarif_journa) {
		this.id = UUID.randomUUID();
		this.titre = titre;
		this.tarifJourna = tarif_journa;
		this.emprunte = false;
	}

	public Produit(UUID id, String titre, double tarifJourna, boolean emprunte) {
		this.id = id;
		this.titre = titre;
		this.tarifJourna = tarifJourna;
		this.emprunte = emprunte;
	}

	protected int produitHashCode() {
		return (int)(titre.hashCode() * tarifJourna) + this.getClass().hashCode();
	}

	public boolean equals(Produit autre) {
		return hashCode() == autre.hashCode();
	}

	public abstract int hashCode();

	public abstract String getType();

	public abstract String toString();

	public abstract Produit copy();

	public UUID getID() {
		return id;
	}

	public String getTitre() {
		return titre;
	}

	public double getTarifJourna() {
		return tarifJourna;
	}

	public boolean getEmprunte() {
		return emprunte;
	}

	public void setEmprunte(boolean emprunte) {
		this.emprunte = emprunte;
		// Main.getInstance().getDataBase().setEmprunte(emprunte, id);
	}
}