package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DBExporter {
	public static void exportDB() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setFileFilter(new FileNameExtensionFilter("*.db", "db"));
		int status = fc.showSaveDialog(null);
		if (status == JFileChooser.APPROVE_OPTION) {
			String selDir = fc.getSelectedFile().getAbsolutePath();
			try {
				//###Personen Table###
				Connection con = DBConnection.connect();
				PreparedStatement getTable = con.prepareStatement("SELECT * FROM Personen;");
				ResultSet rs = getTable.executeQuery();
				String[] attributes = new String[rs.getMetaData().getColumnCount()];
				String[] attributeTypes = new String[rs.getMetaData().getColumnCount()];
				for (int col = 0; col < rs.getMetaData().getColumnCount(); col++) {
					attributes[col] = rs.getMetaData().getColumnName(col+1);
					attributeTypes[col] = rs.getMetaData().getColumnTypeName(col+1);
				}
				
				Connection conOut = DriverManager.getConnection("jdbc:sqlite:" + selDir); //Creating new empty data base
				//Creating a copy of the default database table personen
				String sqlCreateTable = "CREATE TABLE IF NOT EXISTS Personen " +
						 "("+attributes[0]+" "+attributeTypes[0]+" NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
						    +attributes[1]+" "+attributeTypes[1]+","
						    +attributes[2]+" "+attributeTypes[2]+","
						    +attributes[3]+" "+attributeTypes[3]+","
						    +attributes[4]+" "+attributeTypes[4]+","
						    +attributes[5]+" "+attributeTypes[5]+","
						    +attributes[6]+" "+attributeTypes[6]+","
						    +attributes[7]+" "+attributeTypes[7]+","
						    +attributes[8]+" "+attributeTypes[8]+","
						    +attributes[9]+" "+attributeTypes[9]+","
						    +attributes[10]+" "+attributeTypes[10]+","
						    +"'"+attributes[11]+"'"+" "+attributeTypes[11]+","
						    +"'"+attributes[12]+"'"+" "+attributeTypes[12]+","
						    +attributes[13]+" "+attributeTypes[13]+","
							+attributes[14]+" "+attributeTypes[14]+","
							+attributes[15]+" "+attributeTypes[15]+","
							+attributes[16]+" "+attributeTypes[16]
						    +");";
				PreparedStatement createTable = conOut.prepareStatement(sqlCreateTable);
				createTable.execute();
				
				//Filling the copied table with data from the default data base table
				Statement fillTable = conOut.createStatement();
				while (rs.next()) {
					fillTable.execute("INSERT INTO Personen ("+attributes[0]+","+attributes[1]+","+attributes[2]+","+attributes[3]+","+attributes[4]+","+attributes[5]+","
															 +attributes[6]+","+attributes[7]+","+attributes[8]+","+attributes[9]+","+attributes[10]+",'"+attributes[11]+"','"
															 +attributes[12]+"',"+attributes[13]+","+attributes[14]+","+attributes[15]+","+attributes[16]+") "+
									 "VALUES ("+rs.getString("ID")+",'"+rs.getString("Name")+"','"+rs.getString("Vorname")+"','"+rs.getString("Datum")+"','"+rs.getString("Ifwt")+"','"+rs.getString("MNaF")+"','"
									 		   +rs.getString("Intern")+"','"+rs.getString("Beschaeftigungsverhaeltnis")+"','"+rs.getString("Beginn")+"','"+rs.getString("Ende")+"','"+rs.getString("Extern")+"','"
									 		   +rs.getString("E-Mail Adresse")+"','"+rs.getString("Allgemeine Unterweisung")+"','"+rs.getString("Laboreinrichtungen")+"','"+rs.getString("Gefahrstoffe")+"','"
									 		   +rs.getString("LabKommentar")+"','"+rs.getString("GefKommentar")+"');");	
					Statement update = conOut.createStatement();
					
					if (rs.getString("Beschaeftigungsverhaeltnis") == null) {
						update.execute("UPDATE Personen SET Beschaeftigungsverhaeltnis=NULL WHERE ID="+rs.getString("ID")+";");
					}
					if (rs.getString("Ifwt") == null) {
						update.execute("UPDATE Personen SET Ifwt=NULL WHERE ID="+rs.getString("ID")+";");
					}
					if (rs.getString("MNaF") == null) {
						update.execute("UPDATE Personen SET MNaF=NULL WHERE ID="+rs.getString("ID")+";");
					}
					if (rs.getString("Intern") == null) {
						update.execute("UPDATE Personen SET Intern=NULL WHERE ID="+rs.getString("ID")+";");
					}
					if (rs.getString("Extern") == null) {
						update.execute("UPDATE Personen SET Extern=NULL WHERE ID="+rs.getString("ID")+";");
					}
					if (rs.getString("E-Mail Adresse") == null) {
						update.execute("UPDATE Personen SET 'E-Mail Adresse'=NULL WHERE ID="+rs.getString("ID")+";");
					}
					if (rs.getString("Allgemeine Unterweisung") == null) {
						update.execute("UPDATE Personen SET 'Allgemeine Unterweisung'=NULL WHERE ID="+rs.getString("ID")+";");
					}
					if (rs.getString("Laboreinrichtungen") == null) {
						update.execute("UPDATE Personen SET Laboreinrichtungen=NULL WHERE ID="+rs.getString("ID")+";");
					}
					if (rs.getString("Gefahrstoffe") == null) {
						update.execute("UPDATE Personen SET Gefahrstoffe=NULL WHERE ID="+rs.getString("ID")+";");
					}
					if (rs.getString("LabKommentar") == null) {
						update.execute("UPDATE Personen SET LabKommentar=NULL WHERE ID="+rs.getString("ID")+";");
					}
					if (rs.getString("GefKommentar") == null) {
						update.execute("UPDATE Personen SET GefKommentar=NULL WHERE ID="+rs.getString("ID")+";");
					}
				}
				
				
				//###Table Geraete###
				//Getting table data
				getTable = con.prepareStatement("SELECT * FROM Ger\u00e4te;");
				rs = getTable.executeQuery();
				attributes = new String[rs.getMetaData().getColumnCount()];
				attributeTypes = new String[rs.getMetaData().getColumnCount()];
				for (int col = 0; col < rs.getMetaData().getColumnCount(); col++) {
					attributes[col] = rs.getMetaData().getColumnName(col+1);
					attributeTypes[col] = rs.getMetaData().getColumnTypeName(col+1);
				}
				
				//Creating a copy of the default database table geraete
				sqlCreateTable = "CREATE TABLE IF NOT EXISTS Ger\u00e4te " +
						 "("+attributes[0]+" "+attributeTypes[0]+" NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"
						    +attributes[1]+" "+attributeTypes[1]+","
						    +attributes[2]+" "+attributeTypes[2]+","
						    +attributes[3]+" "+attributeTypes[3]
						    +");";
				createTable = conOut.prepareStatement(sqlCreateTable);
				createTable.execute();
				
				//Filling the copied table with data from the default data base table
				fillTable = conOut.createStatement();
				while (rs.next()) {
					fillTable.execute("INSERT INTO Ger\u00e4te ("+attributes[0]+","+attributes[1]+","+attributes[2]+","+attributes[3]+") "+
									 "VALUES ("+rs.getString("Ger\u00e4teID")+",'"+rs.getString("Name")+"','"+rs.getString("Beschreibung")+
									 "','"+rs.getString("Raum")+"');");	
					Statement update = conOut.createStatement();
					
					if (rs.getString("Beschreibung") == null) {
						update.execute("UPDATE Ger\u00e4te SET Beschreibung=NULL WHERE Ger\u00e4teID="+rs.getString("Ger\u00e4teID")+";");
					}
				}
				
				
				//###Table Raeume###
				//Getting table data
				getTable = con.prepareStatement("SELECT * FROM R\u00e4ume;");
				rs = getTable.executeQuery();
				attributes = new String[rs.getMetaData().getColumnCount()];
				attributeTypes = new String[rs.getMetaData().getColumnCount()];
				for (int col = 0; col < rs.getMetaData().getColumnCount(); col++) {
					attributes[col] = rs.getMetaData().getColumnName(col+1);
					attributeTypes[col] = rs.getMetaData().getColumnTypeName(col+1);
				}
				
				//Creating a copy of the default database table raeume
				sqlCreateTable = "CREATE TABLE IF NOT EXISTS R\u00e4ume " +
						 "("+attributes[0]+" "+attributeTypes[0]+" NOT NULL UNIQUE,"
						    +attributes[1]+" "+attributeTypes[1]+");";
				createTable = conOut.prepareStatement(sqlCreateTable);
				createTable.execute();
				//Filling the copied table with data from the default data base table raeume
				fillTable = conOut.createStatement();
				while (rs.next()) {
					fillTable.execute("INSERT INTO R\u00e4ume ("+attributes[0]+","+attributes[1]+") "+
									 "VALUES ('"+rs.getString("Name")+"','"+rs.getString("Beschreibung")+"');");
					
					Statement update = conOut.createStatement();
					
					if (rs.getString("Beschreibung") == null) {
						update.execute("UPDATE R\u00e4ume SET Beschreibung=NULL WHERE Name='"+rs.getString("Name")+"';");
					}
				}
				
				
				//###Table Geraetezuordnung###
				//Getting table data
				getTable = con.prepareStatement("SELECT * FROM Ger\u00e4tezuordnung;");
				rs = getTable.executeQuery();
				attributes = new String[rs.getMetaData().getColumnCount()];
				attributeTypes = new String[rs.getMetaData().getColumnCount()];
				for (int col = 0; col < rs.getMetaData().getColumnCount(); col++) {
					attributes[col] = rs.getMetaData().getColumnName(col+1);
					attributeTypes[col] = rs.getMetaData().getColumnTypeName(col+1);
				}
				
				//Creating a copy of the default database table Geraetezuordnung
				sqlCreateTable = "CREATE TABLE IF NOT EXISTS Ger\u00e4tezuordnung " +
						 "("+attributes[0]+" "+attributeTypes[0]+","
						    +attributes[1]+" "+attributeTypes[1]+","
						    +attributes[2]+" "+attributeTypes[2]
						    +");";
				createTable = conOut.prepareStatement(sqlCreateTable);
				createTable.execute();
				
				//Filling the copied table with data from the default data base table geraetezuordnung
				fillTable = conOut.createStatement();
				while (rs.next()) {
					fillTable.execute("INSERT INTO Ger\u00e4tezuordnung ("+attributes[0]+","+attributes[1]+","+attributes[2]+") "+
									 "VALUES ("+rs.getString("Ger\u00e4teID")+",'"+rs.getString("PersonenID")+"','"+rs.getString("Nutzungszeit")+"');");	
					
					Statement update = conOut.createStatement();
					
					if (rs.getString("Nutzungszeit") == null) {
						update.execute("UPDATE Ger\u00e4tezuordnung SET Nutzungszeit=NULL WHERE Ger\u00e4teID="+rs.getString("Ger\u00e4teID")+";");
					}
				}
				
				
				//###Table Gefahrstoffe###
				//Getting table data
				getTable = con.prepareStatement("SELECT * FROM Gefahrstoffe;");
				rs = getTable.executeQuery();
				attributes = new String[rs.getMetaData().getColumnCount()];
				attributeTypes = new String[rs.getMetaData().getColumnCount()];
				for (int col = 0; col < rs.getMetaData().getColumnCount(); col++) {
					attributes[col] = rs.getMetaData().getColumnName(col+1);
					attributeTypes[col] = rs.getMetaData().getColumnName(col+1);
				}

				//Creating a copy of the default database table Gefahrstoffe
				sqlCreateTable = "CREATE TABLE IF NOT EXISTS Gefahrstoffe " +
								 "("+attributes[0]+" "+attributeTypes[0]+");";
				createTable = conOut.prepareStatement(sqlCreateTable);
				createTable.execute();
				
				//Filling the copied table with data from the default data base table Gefahrstoffe
				fillTable = conOut.createStatement();
				while (rs.next()) {
					fillTable.execute("INSERT INTO Gefahrstoffe ("+attributes[0]+") "+
									 "VALUES ('"+rs.getString("Name")+"');");							
				}
				
				
				//###Table Gefahrstoffzuordnung###
				//Getting table data
				getTable = con.prepareStatement("SELECT * FROM Gefahrstoffzuordnung;");
				rs = getTable.executeQuery();
				attributes = new String[rs.getMetaData().getColumnCount()];
				attributeTypes = new String[rs.getMetaData().getColumnCount()];
				for (int col = 0; col < rs.getMetaData().getColumnCount(); col++) {
					attributes[col] = rs.getMetaData().getColumnName(col+1);
					attributeTypes[col] = rs.getMetaData().getColumnName(col+1);
				}

				//Creating a copy of the default database table Gefahrstoffzuordnung
				sqlCreateTable = "CREATE TABLE IF NOT EXISTS Gefahrstoffzuordnung" +
								 "("+attributes[0]+" "+attributeTypes[0]+
								 ","+attributes[1]+" "+attributeTypes[1]+");";
				createTable = conOut.prepareStatement(sqlCreateTable);
				createTable.execute();
				
				//Filling the copied table with data from the default data base table Gefahrstoffzuordnung
				fillTable = conOut.createStatement();
				while (rs.next()) {
					fillTable.execute("INSERT INTO Gefahrstoffzuordnung ("+attributes[0]+","+attributes[1]+") "+
									 "VALUES ('"+rs.getString("Gefahrstoff")+"','"+rs.getString("PersonenID")+"');");							
				}
				
				createTable.close();
				fillTable.close();
				getTable.close();
				conOut.close();
				con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
