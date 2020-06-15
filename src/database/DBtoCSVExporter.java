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
				PreparedStatement getTable = con.prepareStatement("SELECT * FROM Personen");
				ResultSet rs = getTable.executeQuery();
				BufferedWriter fileWriter = new BufferedWriter(new FileWriter(selDir)); //Creating CSV file
				String line;
				
				String[] columnNames = new String[rs.getMetaData().getColumnCount()];
				for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
					columnNames[i] = rs.getMetaData().getColumnName(i+1);
				}
				String head = String.format("%s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s;%n",
						columnNames[0], columnNames[1], columnNames[2], columnNames[3], columnNames[4], columnNames[5], columnNames[6],
						columnNames[7], columnNames[8], columnNames[9], columnNames[10], columnNames[11], columnNames[12], columnNames[13]);
				fileWriter.write(head);
				
				while (rs.next()) {
					int id = rs.getInt("ID");
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
					
					line = String.format("%d; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s; %s;%n",
												id, name, vorname, datum, ifwt, manf, intern, beschverh, 
												ende, extern, email, unterw, labeinr, gefahrst);
					fileWriter.write(line);
				}
				fileWriter.write("\n\n");
					
				
					//Geraete Table
					getTable = con.prepareStatement("SELECT * FROM Ger\u00e4te;");
					rs = getTable.executeQuery();
					
					columnNames = new String[rs.getMetaData().getColumnCount()];
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
						columnNames[i] = rs.getMetaData().getColumnName(i+1);
					}
					
					head = String.format("%s; %s; %s; %s;%n",
							  columnNames[0], columnNames[1], columnNames[2], columnNames[3]);
					fileWriter.write(head);
					
					while(rs.next()) {
						int id = rs.getInt("Ger\u00e4teID");
						String name = rs.getString("Name");
						String beschreibung = rs.getString("Beschreibung");
						String raum = rs.getString("Raum");
						
						line = String.format("%d, %s; %s; %s;%n",
											  id, name, beschreibung, raum);
						fileWriter.write(line);
					}
					fileWriter.write("\n\n");
					
					
					//Raeume Table
					getTable = con.prepareStatement("SELECT * FROM R\u00e4ume;");
					rs = getTable.executeQuery();
					
					columnNames = new String[rs.getMetaData().getColumnCount()];
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
						columnNames[i] = rs.getMetaData().getColumnName(i+1);
					}
					
					head = String.format("%s; %s;%n",
							  columnNames[0], columnNames[1]);
					fileWriter.write(head);
					
					while(rs.next()) {
						String name = rs.getString("Name");
						String beschreibung = rs.getString("Beschreibung");
						
						line = String.format("%s; %s;%n",
											  name, beschreibung);
						fileWriter.write(line);
					}
					fileWriter.write("\n\n");
					
					//Geraetezuordnung Table
					getTable = con.prepareStatement("SELECT * FROM Ger\u00e4tezuordnung;");
					rs = getTable.executeQuery();
					
					columnNames = new String[rs.getMetaData().getColumnCount()];
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
						columnNames[i] = rs.getMetaData().getColumnName(i+1);
					}
					
					head = String.format("%s; %s; %s;%n",
							  columnNames[0], columnNames[1], columnNames[2]);
					fileWriter.write(head);
					
					while(rs.next()) {
						int geraeteID = rs.getInt("Ger\u00e4teID");
						int personenID = rs.getInt("PersonenID");
						double nutzungszeit = rs.getDouble("Nutzungszeit");
						
						line = String.format("%d, %d; %s;%n",
											  geraeteID, personenID, nutzungszeit);
						fileWriter.write(line);
					}
					fileWriter.write("\n\n");
					
					
					//Gefahrstoffe Table
					getTable = con.prepareStatement("SELECT * FROM Gefahrstoffe;");
					rs = getTable.executeQuery();
					
					columnNames = new String[rs.getMetaData().getColumnCount()];
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
						columnNames[i] = rs.getMetaData().getColumnName(i+1);
					}
					
					head = String.format("%s;%n",columnNames[0]);
					fileWriter.write(head);
					
					while(rs.next()) {
						String hazardousSubstance = rs.getString("Name");
						
						line = String.format("%s;%n", hazardousSubstance);
						fileWriter.write(line);
					}
					fileWriter.write("\n\n");
					
					
					//sqlite_sequence Table ###NEEDED FOR AUTOINCREMENT###
					getTable = con.prepareStatement("SELECT * FROM sqlite_sequence;");
					rs = getTable.executeQuery();
					
					columnNames = new String[rs.getMetaData().getColumnCount()];
					for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
						columnNames[i] = rs.getMetaData().getColumnName(i+1);
					}
					
					head = String.format("%s; %s;%n",
							  columnNames[0], columnNames[1]);
					fileWriter.write(head);
					
					while(rs.next()) {
						String name = rs.getString("name");
						int seq = rs.getInt("seq");
						
						line = String.format("%s, %d;%n",
											  name, seq);
						fileWriter.write(line);
					}
					fileWriter.write("\n\n");
					
				
				
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
