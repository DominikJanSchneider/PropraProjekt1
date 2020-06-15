package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.MainFrame;

public class DBImporter {
	public static void importDB() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setFileFilter(new FileNameExtensionFilter("*.db", "db"));
		int status = fc.showOpenDialog(null);
		if (status == JFileChooser.APPROVE_OPTION) {
			String selFile = fc.getSelectedFile().getAbsolutePath();
			DBConnection.setURL("jdbc:sqlite:"+selFile);
			Connection con = DBConnection.connect(); // Connecting to existing data base
			try {
				//ResultSet rsTableNames = con.getMetaData().getTables(null, null, null, null); //Getting all tables from data base
				//ArrayList<String> tables = new ArrayList<String>(); //Creating ArrayList for table names
				//while(rsTableNames.next()) {
				//	tables.add(rsTableNames.getString("TABLE_NAME")); //Saving table names from data base in ArrayList
				//}
				//System.out.println(tables.get(0));
				//String query = "SELECT * FROM "+tables.get(0)+";";
				String query = "SELECT * FROM Personen;";
				PreparedStatement getTable = con.prepareStatement(query);
				ResultSet rs = getTable.executeQuery();
				
				//dtm.setRowCount(0);
				MainFrame.getDefaultTableModel().setRowCount(0);
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

					MainFrame.getDefaultTableModel().addRow(new Object[] { id, name, vorname, datum, ifwt, manf, intern, beschverh, beginn, ende, extern,
								email,unterw,labeinr,gefahrst });
				}
				MainFrame.getDefaultTableModel().fireTableDataChanged();
				
				
				//###Geraete Table###
				query = "SELECT * FROM Ger\u00e4te;";
				getTable = con.prepareStatement(query);
				rs = getTable.executeQuery();
				
				MainFrame.getGeraeteTableModel().setRowCount(0);
				while (rs.next()) {
					int deviceID = rs.getInt("Ger\u00e4teID");
					String deviceName = rs.getString("Name");
					String deviceDescription = rs.getString("Beschreibung");
					String deviceRoom = rs.getString("Raum");
					
					MainFrame.getGeraeteTableModel().addRow(new Object[] {deviceID,deviceName,deviceDescription,deviceRoom});
				}
				MainFrame.getGeraeteTableModel().fireTableDataChanged();
				
				
				//###Raeume Table###
				query = "SELECT * FROM R\u00e4ume;";
				getTable = con.prepareStatement(query);
				rs = getTable.executeQuery();
				
				MainFrame.getRaeumeTableModel().setRowCount(0);
				while (rs.next()) {
					String roomName = rs.getString("Name");
					String roomDescription = rs.getString("Beschreibung");
					
					MainFrame.getRaeumeTableModel().addRow(new Object[] {roomName, roomDescription});
				}
				MainFrame.getRaeumeTableModel().fireTableDataChanged();
				
				
				//###Gefahrstoffe Table###
				query = "SELECT * FROM Gefahrstoffe;";
				getTable = con.prepareStatement(query);
				rs = getTable.executeQuery();
				
				MainFrame.getGefahrstoffeTableModel().setRowCount(0);
				while (rs.next()) {
					String hazardousSubstance = rs.getString("Name");
					
					MainFrame.getGefahrstoffeTableModel().addRow(new Object[] {hazardousSubstance});
				}
				MainFrame.getGefahrstoffeTableModel().fireTableDataChanged();
				
				con.close();
				getTable.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
