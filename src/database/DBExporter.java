package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
				Connection con = DBConnection.connect();
				ResultSet rsTableNames = con.getMetaData().getTables(null, null, null, null);
				ArrayList<String> tables = new ArrayList<String>(); //Creating ArrayList for table names
				while (rsTableNames.next()) {
					tables.add(rsTableNames.getString("TABLE_NAME")); // Saving table names from data base in ArrayList
				}
				String query = "SELECT * FROM "+tables.get(0)+";";
				PreparedStatement getTable = con.prepareStatement(query);
				ResultSet rs = getTable.executeQuery();
				String[] attributes = new String[rs.getMetaData().getColumnCount()];
				String[] attributeTypes = new String[rs.getMetaData().getColumnCount()];
				for (int col = 0; col < rs.getMetaData().getColumnCount(); col++) {
					attributes[col] = rs.getMetaData().getColumnName(col+1);
					attributeTypes[col] = rs.getMetaData().getColumnTypeName(col+1);
				}
				
				Connection conOut = DriverManager.getConnection("jdbc:sqlite:" + selDir); //Creating new empty data base
				//Creating a copy of the default database table
				String sqlCreateTable = "CREATE TABLE IF NOT EXISTS "+ tables.get(0) +
						 "("+attributes[0]+" "+attributeTypes[0]+" PRIMARY KEY NOT NULL UNIQUE,"
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
							+attributes[14]+" "+attributeTypes[14]
						    +");";
				Statement createTable = conOut.createStatement();
				createTable.execute(sqlCreateTable);
				
				//Filling the copied table with data from the default data base table
				Statement fillTable = conOut.createStatement();
				while (rs.next()) {
					fillTable.execute("INSERT INTO "+tables.get(0)+" ("+attributes[0]+","+attributes[1]+","+attributes[2]+","+attributes[3]+","+attributes[4]+","+attributes[5]+","
												   +attributes[6]+","+attributes[7]+","+attributes[8]+","+attributes[9]+","+attributes[10]+",'"+attributes[11]+"','"
												   +attributes[12]+"',"+attributes[13]+","+attributes[14]+") "+
									 "VALUES ("+rs.getString("ID")+",'"+rs.getString("Name")+"','"+rs.getString("Vorname")+"','"+rs.getString("Datum")+"','"+rs.getString("Ifwt")+"','"+rs.getString("MNaF")+"','"
									 		   +rs.getString("Intern")+"','"+rs.getString("Beschaeftigungsverhaeltnis")+"','"+rs.getString("Beginn")+"','"+rs.getString("Ende")+"','"+rs.getString("Extern")+"','"
									 		   +rs.getString("E-Mail Adresse")+"','"+rs.getString("Allgemeine Unterweisung")+"','"+rs.getString("Laboreinrichtungen")+"','"+rs.getString("Gefahrstoffe")+"');");							
				}
				
				//Table Geraete
				
				//Table Raume
				
				//Table Geraetezuweisung
				
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
