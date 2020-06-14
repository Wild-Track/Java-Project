package Models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.UUID;
import java.lang.StringBuilder;

public final class Commande {

	private final UUID id;
	private final LocalDate date;
	private double reduction;
	private final HashSet<Emprunt> emprunts;
	private final Client client;

	public Commande(double reduction, Client client) {
		this.id = UUID.randomUUID();
		this.date = LocalDate.now();
		this.reduction = reduction;
		this.emprunts = new HashSet<Emprunt>();
		this.client = client;
	}

	public boolean ajoutEmprunt(Emprunt emprunt) {
		emprunt.getProduit().setEmprunte(true);

		return emprunts.add(emprunt);
	}

	public boolean supprEmprunt(Emprunt emprunt) {
		emprunt.getProduit().setEmprunte(false);

		return emprunts.remove(emprunt);
	}

	private double calculPrixEmprunt(Emprunt emprunt) {
		long nombreJours = ChronoUnit.DAYS.between(emprunt.getDateDebut(), emprunt.getDateFin());

		return emprunt.getProduit().getTarifJourna() * nombreJours;
	}

	public double calculPrix() {
		double prixTotal = 0;

		for (Emprunt emprunt : emprunts) {
			prixTotal += calculPrixEmprunt(emprunt);
		}

		return prixTotal * (1.0 - reduction) * (client.getFidele() ? (1.0 - Client.REDUCTION_FIDELE) : 1.0);
	}

	public double calculPrixBrut() {
		double prixTotal = 0;

		for (Emprunt emprunt : emprunts) {
			prixTotal += calculPrixEmprunt(emprunt);
		}

		return prixTotal;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(String.format("Commande n°\n", id));
		builder.append(String.format(" - Client: %s\n", client));
		builder.append(String.format(" - Date: %s\n", date));
		builder.append("\n");

		builder.append("Emprunts:\n");
		for(Emprunt emprunt : emprunts) {
			builder.append(String.format(" - %s\n", emprunt));
		}

		builder.append("\n");
		builder.append("Prix:\n");
		builder.append(String.format(" - Total: %s\n", getPrixBrut()));
		builder.append(String.format(" - Réduction -%s\n", getPrixBrut() - getPrix()));
		builder.append(String.format(" - Après réduction %s\n", getPrix()));

		return builder.toString();
	}

	public UUID getID() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getReduction() {
		return reduction;
	}

	public Client getClient() {
		return client;
	}

	public double getPrix() {
		return calculPrix();
	}

	public double getPrixBrut() {
		return calculPrixBrut();
	}

	public Iterable<Emprunt> getEmprunts() {
		return emprunts;
	}

	public int getNombreEmprunts() {
		return emprunts.size();
	}

	public void setReduction(double reduction) {
		this.reduction = reduction;
	}

}