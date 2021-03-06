package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			DBConnector.setURLCore("jdbc:sqlite:"+selFile);
			Connection con = DBConnector.connectCore(); // Connecting to existing data base
			try {
				
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
					String labKommentar = rs.getString("LabKommentar");
					String gefKommentar = rs.getString("GefKommentar");

					MainFrame.getDefaultTableModel().addRow(new Object[] { id, name, vorname, datum, ifwt, manf, intern, beschverh, beginn, ende, extern,
								email,unterw,labeinr,gefahrst,labKommentar,gefKommentar});
				}
				MainFrame.getDefaultTableModel().fireTableDataChanged();
				
				
				//###Geraete Table###
				query = "SELECT * FROM Ger\u00e4te;";
				getTable = con.prepareStatement(query);
				rs = getTable.executeQuery();
				
				MainFrame.getDeviceTableModel().setRowCount(0);
				while (rs.next()) {
					int deviceID = rs.getInt("Ger\u00e4teID");
					String deviceName = rs.getString("Name");
					String deviceDescription = rs.getString("Beschreibung");
					String deviceRoom = rs.getString("Raum");
					
					MainFrame.getDeviceTableModel().addRow(new Object[] {deviceID,deviceName,deviceDescription,deviceRoom});
				}
				MainFrame.getDeviceTableModel().fireTableDataChanged();
				
				
				//###Raeume Table###
				query = "SELECT * FROM R\u00e4ume;";
				getTable = con.prepareStatement(query);
				rs = getTable.executeQuery();
				
				MainFrame.getRoomsTableModel().setRowCount(0);
				while (rs.next()) {
					String roomName = rs.getString("Name");
					String roomDescription = rs.getString("Beschreibung");
					
					MainFrame.getRoomsTableModel().addRow(new Object[] {roomName, roomDescription});
				}
				MainFrame.getRoomsTableModel().fireTableDataChanged();
				
				
				//###Gefahrstoffe Table###
				query = "SELECT * FROM Gefahrstoffe;";
				getTable = con.prepareStatement(query);
				rs = getTable.executeQuery();
				
				MainFrame.getDangerSubstTableModel().setRowCount(0);
				while (rs.next()) {
					String hazardousSubstance = rs.getString("Name");
					
					MainFrame.getDangerSubstTableModel().addRow(new Object[] {hazardousSubstance});
				}
				MainFrame.getDangerSubstTableModel().fireTableDataChanged();
				
				con.close();
				getTable.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
