package Manager;

import java.util.HashMap;
import java.util.HashSet;

import Models.Produits.Produit;
import Models.Produits.InfoProduit;

public class StockManager {

    private final HashSet<Produit> produits;
    private HashMap<Integer, InfoProduit> infoProduits;

    public StockManager() {
        produits = new HashSet<Produit>();
        infoProduits = new HashMap<Integer, InfoProduit>();
    }

    public boolean ajoutProduit(Produit produit) {
        ajouteInfoProduit(produit);
        return produits.add(produit);
    }

    private void ajouteInfoProduit(Produit produit) {
        InfoProduit infoProduit = getInfoProduit(produit);

        if (infoProduit == null) {
            infoProduit = new InfoProduit(produit);
            infoProduits.put(produit.hashCode(), infoProduit);
        }

        infoProduit.ajoute(produit);
    }

    public boolean supprProduit(Produit produit) {
        if (produit.getEmprunte()) {
            return false;
        }

        supprInfoProduit(produit);
        return produits.remove(produit);
    }

    private void supprInfoProduit(Produit produit) {
        InfoProduit infoProduit = getInfoProduit(produit);

        if (infoProduit == null) {
            return;
        }

        infoProduit.suppr(produit);

        if (infoProduit.getQuantiteTotal() < 1) {
            infoProduits.remove(produit.hashCode());
        }
    }

    public int compteProduitStock(Produit produit) {
        InfoProduit infoProduit = getInfoProduit(produit);

        if (infoProduit == null) {
            return 0;
        }

        return infoProduit.getQuantiteDisponible();
    }

    public InfoProduit getInfoProduit(Produit produit) {
        return infoProduits.get(produit.hashCode());
    }

    public Iterable<Produit> getProduits() {
        return produits;
    }

    public Iterable<InfoProduit> getInfoProduits() {
        return infoProduits.values();
    }
}