package Graphique.Controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import Models.Commande;
import Utils.IterableUtils;
import Utils.DateUtils;

public class ImpressionCommande {

	private String fideleTxt;
	private int i;

	public ImpressionCommande(Commande commande) {

		try {
			if (commande.getClient().getFidele()) {
				this.fideleTxt = "Fidèle";
			} else {
				this.fideleTxt = "Non Fidèle";
			}

			FileInputStream file = new FileInputStream(new File("facture/template.xls"));

			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Row dataRow = sheet.createRow(1);

			//Insertion des donn�es
			dataRow.createCell(0).setCellValue(DateUtils.toUsualDate(commande.getDate()));
			dataRow.createCell(1).setCellValue(commande.getClient().getNom());
			dataRow.createCell(2).setCellValue(commande.getClient().getPrenom());
			dataRow.createCell(3).setCellValue(fideleTxt);
			dataRow.createCell(4).setCellValue(commande.getReduction() * 100);
			dataRow.createCell(5).setCellValue(String.format("%.2f", commande.getPrix()));
			dataRow.createCell(6).setCellValue(commande.getPrixBrut());
			dataRow.createCell(7).setCellValue(commande.getID().toString());
			dataRow.createCell(8).setCellValue(commande.getClient().getID().toString());

			i = 10;
			IterableUtils.toStream(commande.getEmprunts())
					.forEach(emprunt -> {
						dataRow.createCell(i).setCellValue(emprunt.getProduit().getTitre());
								i++;
						dataRow.createCell(i).setCellValue(emprunt.getProduit().getTarifJourna());
						i++;
						dataRow.createCell(i).setCellValue("");
						i++;
					});

			file.close();

			FileOutputStream outFile = new FileOutputStream(new File("facture/facture.xls"));
			workbook.write(outFile);
			outFile.close();
			workbook.close();

			JOptionPane.showMessageDialog(null, "            Facture généré !", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "    Fichier temp non trouvé !!", "Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "    Une erreur est survenue !!", "Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}