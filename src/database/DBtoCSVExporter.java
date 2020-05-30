package database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DBtoCSVExporter {
	public static void export() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setFileFilter(new FileNameExtensionFilter("*.csv", "csv"));
		int status = fc.showSaveDialog(null);
		if (status == JFileChooser.APPROVE_OPTION) {
			String selDir = fc.getSelectedFile().getAbsolutePath();
			Connection con = DBConnection.connect();
			try {
				String query = "SELECT * FROM Personen";
				PreparedStatement getTable = con.prepareStatement(query);
				ResultSet rs = getTable.executeQuery();
				BufferedWriter fileWriter = new BufferedWriter(new FileWriter(selDir)); //Creating CSV file
				
				while (rs.next()) {
					String id = rs.getString("ID");
					String name = rs.getString("Name");
					String vorname = rs.getString("Vorname");
					String datum = rs.getString("Datum");
					String ifwt = rs.getString("Ifwt");
					String manf = rs.getString("MNaF");
					String intern = rs.getString("Intern");
					String beschverh = rs.getString("Beschaeftigungsverhaeltnis");
					String beginn = rs.getString("Beginn");
					String ende = rs.getString("Ende");
					String extern = rs.getString("Extern");
					String email = rs.getString("E-Mail Adresse");
					String unterw = rs.getString("Allgemeine Unterweisung");
					String labeinr = rs.getString("Laboreinrichtungen");
					String gefahrst = rs.getString("Gefahrstoffe");
					
					String[] columnNames = new String[rs.getMetaData().getColumnCount()];
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
						columnNames[i] = rs.getMetaData().getColumnName(i+1);
					}
					String head = String.format("%s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s;%n",
												columnNames[0], columnNames[1], columnNames[2], columnNames[3], columnNames[4], columnNames[5], columnNames[6],
												columnNames[7], columnNames[8], columnNames[9], columnNames[10], columnNames[11], columnNames[12], columnNames[13]);
					String line = String.format("%s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s;%n",
												id, name, vorname, datum, ifwt, manf, intern, beschverh, 
												ende, extern, email, unterw, labeinr, gefahrst);
					fileWriter.write(head);
					fileWriter.write(line);
				}
				getTable.close();
				fileWriter.close();
			} catch(SQLException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
