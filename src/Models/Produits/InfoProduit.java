package Models.Produits;

import java.util.HashSet;
import java.util.Optional;

import Utils.IterableUtils;

public class InfoProduit {
    private final String titre;
    private final String type;
    private final String produitString;
    private final double tarifJournalier;
    private final HashSet<Produit> produits;

    public InfoProduit(Produit produit) {
        this.titre = produit.getTitre();
        this.type = produit.getType();
        this.produits = new HashSet<Produit>();
        this.produitString = produit.toString();
        this.tarifJournalier = produit.getTarifJourna();
    }

    public void ajoute(Produit produit) {
        produits.add(produit);
    }

    public void suppr(Produit produit) {
        produits.remove(produit);
    }

    public Optional<Produit> creerProduit() {
        for (Produit produit : produits) {
            return Optional.of(produit.copy());
        }

        return Optional.empty();
    }

    public int getQuantiteTotal() {
        return produits.size();
    }

    public int getQuantiteDisponible() {
        int nombre = 0;

        for (Produit produit : produits) {
            if (!produit.getEmprunte()) {
                nombre++;
            }
        }

        return nombre;
    }

    public Optional<Produit> getPremierProduitDisponible() {
        return IterableUtils.toStream(produits).filter(p -> !p.getEmprunte()).findFirst();
    }

    public int getQuantiteIndisponible() {
        return getQuantiteTotal() - getQuantiteDisponible();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s/%s) ", type, produitString, getQuantiteDisponible(), getQuantiteTotal());
    }

    public String toString(boolean quantite) {
        if (quantite) {
            return toString();
        }
        return String.format("[%s] %s ", type, produitString);
    }

    public String getTitre() {
        return titre;
    }

    public double getTarifJournalier() {
        return tarifJournalier;
    }

    public String getType() {
        return type;
    }

}