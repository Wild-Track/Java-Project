package Graphique.Controls;

import java.time.LocalDate;
import java.util.stream.StreamSupport;

import javax.swing.table.AbstractTableModel;

import Manager.CommandeManager;
import Models.Commande;
import Utils.DateUtils;
import Utils.DoubleUtils;

public class CommandeTable extends AbstractTableModel {

    public static final int NOMBRE_COLONNES_MINIMUM = 25;

    private static final long serialVersionUID = -1265694713575884590L;
    private String[] columnNames = { "Client", "Nombre produits", "Réduction", "Prix total", "Date"};
    private Object[][] data;
    private Commande[] commandes;
	private CommandeManager cm;
    private int i;

    public CommandeTable(CommandeManager cm, String search) {
        i = 0;

        this.cm = cm;
        int nombreCommandes = this.cm.getNombreCommandes();
        int nombreLignes = nombreCommandes >= NOMBRE_COLONNES_MINIMUM ? nombreCommandes : NOMBRE_COLONNES_MINIMUM;

        commandes = new Commande[nombreLignes];
        data = new Object[nombreLignes][columnNames.length];

        StreamSupport.stream(cm.getCommandes().spliterator(), false)
        .filter(c -> search == "" ? true : c.getClient().getNom().toLowerCase().contains(search.toLowerCase()))
        .sorted((a, b) -> a.getClient().getNom().compareToIgnoreCase(b.getClient().getNom()))
        .forEach(commande -> {
            data[i][0] = String.format("%s %s", commande.getClient().getNom().toUpperCase(),
                    commande.getClient().getPrenom());
            data[i][1] = commande.getNombreEmprunts();
            data[i][2] = commande.getReduction();
            data[i][3] = commande.getPrix();
            data[i][4] = commande.getDate();
            commandes[i] = commande;
            i++;
        });
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = data[rowIndex][columnIndex];

        // R�duction
        if (columnIndex == 2 && value != null) {
            return DoubleUtils.toPercentage((double)value);
        }

        // Prix
        if (columnIndex == 3 && value != null) {
            return DoubleUtils.toCurrency((double)value);
        }

        // Date
        if (columnIndex == 4 && value != null) {
            return DateUtils.toUsualDate((LocalDate)value);
        }

        return value;
    }

    public Commande getCommandAt(int row) {
        return commandes[row];
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
}