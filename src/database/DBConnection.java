package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gui.MainFrame;


public class DBConnection {
	
	private static String url = "jdbc:sqlite:database/Personen.db";
	private static String tableName = "Personen";
	private static Connection con;
	
	public static Connection connect() {
       try {
    	   Class.forName("org.sqlite.JDBC");
    	   con = DriverManager.getConnection(url);
    	   //System.out.println("Connection SuccesFul");
    	   //JOptionPane.showMessageDialog(null, "Verbindung zur Datenbank hergestellt.");
    	   return con;
       }catch(Exception e) {
    	   System.out.println(e.getMessage());
    	   return null;
       }
	}
	
	public static Object[][] getGeraeteData() {
		try {
			con = DriverManager.getConnection(url);
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (Ger\u00e4teID) FROM Ger\u00e4te;");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM Ger\u00e4te;");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] tableData = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				tableData[i][0] = rs.getInt("Ger\u00e4teID");
				tableData[i][1] = rs.getString("Name");
				tableData[i][2] = rs.getString("Beschreibung");
				tableData[i][3] = rs.getString("Raum");
				
				i++;
			}
			pstmt.close();
			con.close();
			return tableData;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object[][] getRaeumeData() {
		try {
			con = DriverManager.getConnection(url);
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (Name) FROM R\u00e4ume;");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM R\u00e4ume;");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] tableData = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				tableData[i][0] = rs.getString("Name");
				tableData[i][1] = rs.getString("Beschreibung");
				i++;
			}
			pstmt.close();
			con.close();
			return tableData;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object[][] getGefahrstoffeData() {
		try {
			con = DriverManager.getConnection(url);
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (Name) FROM Gefahrstoffe;");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM Gefahrstoffe;");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] tableData = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				tableData[i][0] = rs.getString("Name");
				i++;
			}
			pstmt.close();
			con.close();
			return tableData;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	public static Object[][] getName() {
		try {
			con = DriverManager.getConnection(url);
			//Statement stmt = con.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT COUNT (ID) FROM "+tableName+" WHERE Name='"+MainFrame.getSearchTF().getText()+"';");
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM "+tableName+" WHERE Name LIKE '%"+MainFrame.getSearchTF().getText()+"%';");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Name LIKE '%"+MainFrame.getSearchTF().getText()+"%';");
			//rs = stmt.executeQuery("SELECT * FROM "+tableName+" WHERE Name ='"+MainFrame.getSearchTF().getText()+"';");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] filteredTable = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				filteredTable[i][0] = rs.getInt("ID");
				filteredTable[i][1] = rs.getString("Name");
				filteredTable[i][2] = rs.getString("Vorname");
				filteredTable[i][3] = rs.getString("Datum");
				filteredTable[i][4] = rs.getString("Ifwt");
				filteredTable[i][5] = rs.getString("MNaF");
				filteredTable[i][6] = rs.getString("Intern");
				filteredTable[i][7] = rs.getString("Beschaeftigungsverhaeltnis");
				filteredTable[i][8] = rs.getString("Beginn");
				filteredTable[i][9] = rs.getString("Ende");
				filteredTable[i][10] = rs.getString("Extern");
				filteredTable[i][11] = rs.getString("E-Mail Adresse");
				filteredTable[i][12] = rs.getString("Allgemeine Unterweisung");
				filteredTable[i][13] = rs.getString("Laboreinrichtungen");
				filteredTable[i][14] = rs.getString("Gefahrstoffe");
				
				i++;
			}
			pstmt.close();
			con.close();
			return filteredTable;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Method that returns all rows of the table where Ifwt is not null
	public static Object[][] getIfwt()  {
		try {
			con = DriverManager.getConnection(url);
			//Statement stmt = con.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT COUNT (ID) FROM Personen WHERE NOT Ifwt IS NULL");
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE NOT Ifwt IS NULL");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM Personen WHERE NOT Ifwt IS NULL");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] filteredTable = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				filteredTable[i][0] = rs.getInt("ID");
				filteredTable[i][1] = rs.getString("Name");
				filteredTable[i][2] = rs.getString("Vorname");
				filteredTable[i][3] = rs.getString("Datum");
				filteredTable[i][4] = rs.getString("Ifwt");
				filteredTable[i][5] = rs.getString("MNaF");
				filteredTable[i][6] = rs.getString("Intern");
				filteredTable[i][7] = rs.getString("Beschaeftigungsverhaeltnis");
				filteredTable[i][8] = rs.getString("Beginn");
				filteredTable[i][9] = rs.getString("Ende");
				filteredTable[i][10] = rs.getString("Extern");
				filteredTable[i][11] = rs.getString("E-Mail Adresse");
				filteredTable[i][12] = rs.getString("Allgemeine Unterweisung");
				filteredTable[i][13] = rs.getString("Laboreinrichtungen");
				filteredTable[i][14] = rs.getString("Gefahrstoffe");
				
				i++;
			}
			pstmt.close();
			con.close();
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Method that returns all rows of the table where Ifwt equals LMN
	public static Object[][] getLMN()  {
		try {
			con = DriverManager.getConnection(url);
			//Statement stmt = con.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LMN'");
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LMN'");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM Personen WHERE Ifwt='LMN'");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] filteredTable = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				filteredTable[i][0] = rs.getInt("ID");
				filteredTable[i][1] = rs.getString("Name");
				filteredTable[i][2] = rs.getString("Vorname");
				filteredTable[i][3] = rs.getString("Datum");
				filteredTable[i][4] = rs.getString("Ifwt");
				filteredTable[i][5] = rs.getString("MNaF");
				filteredTable[i][6] = rs.getString("Intern");
				filteredTable[i][7] = rs.getString("Beschaeftigungsverhaeltnis");
				filteredTable[i][8] = rs.getString("Beginn");
				filteredTable[i][9] = rs.getString("Ende");
				filteredTable[i][10] = rs.getString("Extern");
				filteredTable[i][11] = rs.getString("E-Mail Adresse");
				filteredTable[i][12] = rs.getString("Allgemeine Unterweisung");
				filteredTable[i][13] = rs.getString("Laboreinrichtungen");
				filteredTable[i][14] = rs.getString("Gefahrstoffe");
				
				i++;
			}
			pstmt.close();
			con.close();
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Method that returns all rows of the table where Ifwt equals LMW
	public static Object[][] getLMW()  {
		try {
			con = DriverManager.getConnection(url);
			//Statement stmt = con.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LMW'");
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LMW'");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM Personen WHERE Ifwt='LMW'");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] filteredTable = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				filteredTable[i][0] = rs.getInt("ID");
				filteredTable[i][1] = rs.getString("Name");
				filteredTable[i][2] = rs.getString("Vorname");
				filteredTable[i][3] = rs.getString("Datum");
				filteredTable[i][4] = rs.getString("Ifwt");
				filteredTable[i][5] = rs.getString("MNaF");
				filteredTable[i][6] = rs.getString("Intern");
				filteredTable[i][7] = rs.getString("Beschaeftigungsverhaeltnis");
				filteredTable[i][8] = rs.getString("Beginn");
				filteredTable[i][9] = rs.getString("Ende");
				filteredTable[i][10] = rs.getString("Extern");
				filteredTable[i][11] = rs.getString("E-Mail Adresse");
				filteredTable[i][12] = rs.getString("Allgemeine Unterweisung");
				filteredTable[i][13] = rs.getString("Laboreinrichtungen");
				filteredTable[i][14] = rs.getString("Gefahrstoffe");
				
				i++;
			}
			pstmt.close();
			con.close();
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Method that returns all rows of the table where Ifwt equals LOT
	public static Object[][] getLOT()  {
		try {
			con = DriverManager.getConnection(url);
			//Statement stmt = con.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LOT'");
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LOT'");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM Personen WHERE Ifwt='LOT'");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] filteredTable = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				filteredTable[i][0] = rs.getInt("ID");
				filteredTable[i][1] = rs.getString("Name");
				filteredTable[i][2] = rs.getString("Vorname");
				filteredTable[i][3] = rs.getString("Datum");
				filteredTable[i][4] = rs.getString("Ifwt");
				filteredTable[i][5] = rs.getString("MNaF");
				filteredTable[i][6] = rs.getString("Intern");
				filteredTable[i][7] = rs.getString("Beschaeftigungsverhaeltnis");
				filteredTable[i][8] = rs.getString("Beginn");
				filteredTable[i][9] = rs.getString("Ende");
				filteredTable[i][10] = rs.getString("Extern");
				filteredTable[i][11] = rs.getString("E-Mail Adresse");
				filteredTable[i][12] = rs.getString("Allgemeine Unterweisung");
				filteredTable[i][13] = rs.getString("Laboreinrichtungen");
				filteredTable[i][14] = rs.getString("Gefahrstoffe");
				
				i++;
			}
			pstmt.close();
			con.close();
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Method that returns all rows of the table where Ifwt equals LWF
	public static Object[][] getLWF()  {
		try {
			con = DriverManager.getConnection(url);
			//Statement stmt = con.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LWF'");
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LWF'");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM Personen WHERE Ifwt='LWF'");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] filteredTable = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				filteredTable[i][0] = rs.getInt("ID");
				filteredTable[i][1] = rs.getString("Name");
				filteredTable[i][2] = rs.getString("Vorname");
				filteredTable[i][3] = rs.getString("Datum");
				filteredTable[i][4] = rs.getString("Ifwt");
				filteredTable[i][5] = rs.getString("MNaF");
				filteredTable[i][6] = rs.getString("Intern");
				filteredTable[i][7] = rs.getString("Beschaeftigungsverhaeltnis");
				filteredTable[i][8] = rs.getString("Beginn");
				filteredTable[i][9] = rs.getString("Ende");
				filteredTable[i][10] = rs.getString("Extern");
				filteredTable[i][11] = rs.getString("E-Mail Adresse");
				filteredTable[i][12] = rs.getString("Allgemeine Unterweisung");
				filteredTable[i][13] = rs.getString("Laboreinrichtungen");
				filteredTable[i][14] = rs.getString("Gefahrstoffe");
				
				i++;
			}
			pstmt.close();
			con.close();
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Method that returns all MNaF that are not null !MUSS ICH NOCH UEBERARBEITEN! Dominik
	public static Object[][] getMNaF()  {
		try {
			con = DriverManager.getConnection(url);
			//Statement stmt = con.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT COUNT (ID) FROM Personen WHERE NOT MNaF IS NULL");
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE NOT MNaF IS NULL");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM Personen WHERE NOT MNaF IS NULL");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] filteredTable = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				filteredTable[i][0] = rs.getInt("ID");
				filteredTable[i][1] = rs.getString("Name");
				filteredTable[i][2] = rs.getString("Vorname");
				filteredTable[i][3] = rs.getString("Datum");
				filteredTable[i][4] = rs.getString("Ifwt");
				filteredTable[i][5] = rs.getString("MNaF");
				filteredTable[i][6] = rs.getString("Intern");
				filteredTable[i][7] = rs.getString("Beschaeftigungsverhaeltnis");
				filteredTable[i][8] = rs.getString("Beginn");
				filteredTable[i][9] = rs.getString("Ende");
				filteredTable[i][10] = rs.getString("Extern");
				filteredTable[i][11] = rs.getString("E-Mail Adresse");
				filteredTable[i][12] = rs.getString("Allgemeine Unterweisung");
				filteredTable[i][13] = rs.getString("Laboreinrichtungen");
				filteredTable[i][14] = rs.getString("Gefahrstoffe");
				
				i++;
			}
			pstmt.close();
			con.close();
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Method that returns all rows of the table where intern equals 'ja'
	public static Object[][] getIntern()  {
		try {
			con = DriverManager.getConnection(url);
			//Statement stmt = con.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT COUNT (ID) FROM Personen WHERE Intern='Ja'");
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE NOT Intern IS NULL");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM Personen WHERE NOT Intern IS NULL");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] filteredTable = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				filteredTable[i][0] = rs.getInt("ID");
				filteredTable[i][1] = rs.getString("Name");
				filteredTable[i][2] = rs.getString("Vorname");
				filteredTable[i][3] = rs.getString("Datum");
				filteredTable[i][4] = rs.getString("Ifwt");
				filteredTable[i][5] = rs.getString("MNaF");
				filteredTable[i][6] = rs.getString("Intern");
				filteredTable[i][7] = rs.getString("Beschaeftigungsverhaeltnis");
				filteredTable[i][8] = rs.getString("Beginn");
				filteredTable[i][9] = rs.getString("Ende");
				filteredTable[i][10] = rs.getString("Extern");
				filteredTable[i][11] = rs.getString("E-Mail Adresse");
				filteredTable[i][12] = rs.getString("Allgemeine Unterweisung");
				filteredTable[i][13] = rs.getString("Laboreinrichtungen");
				filteredTable[i][14] = rs.getString("Gefahrstoffe");
				
				i++;
			}
			pstmt.close();
			con.close();
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Method that returns all rows of the table where extern equals 'ja'
		public static Object[][] getExtern()  {
			try {
				con = DriverManager.getConnection(url);
				//Statement stmt = con.createStatement();
				//ResultSet rs = stmt.executeQuery("SELECT COUNT (ID) FROM Personen WHERE Extern='Ja'");
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE NOT Extern IS NULL");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT * FROM Personen WHERE NOT Extern IS NULL"	);
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getInt("ID");
					filteredTable[i][1] = rs.getString("Name");
					filteredTable[i][2] = rs.getString("Vorname");
					filteredTable[i][3] = rs.getString("Datum");
					filteredTable[i][4] = rs.getString("Ifwt");
					filteredTable[i][5] = rs.getString("MNaF");
					filteredTable[i][6] = rs.getString("Intern");
					filteredTable[i][7] = rs.getString("Beschaeftigungsverhaeltnis");
					filteredTable[i][8] = rs.getString("Beginn");
					filteredTable[i][9] = rs.getString("Ende");
					filteredTable[i][10] = rs.getString("Extern");
					filteredTable[i][11] = rs.getString("E-Mail Adresse");
					filteredTable[i][12] = rs.getString("Allgemeine Unterweisung");
					filteredTable[i][13] = rs.getString("Laboreinrichtungen");
					filteredTable[i][14] = rs.getString("Gefahrstoffe");
					
					i++;
				}
				pstmt.close();
				con.close();
				return filteredTable;
				
			} catch(SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		//User data base connection
		public static Object[][] getGeraeteByID(String id) {
			try {
				
				tableName = "Ger\u00e4te";
				con = DriverManager.getConnection(url);
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName+" WHERE Ger\u00e4teID LIKE '%"+id+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Ger\u00e4teID LIKE '%"+id+"%';");
				
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getInt("Ger\u00e4teID");
					filteredTable[i][1] = rs.getString("Name");
					filteredTable[i][2] = rs.getString("Beschreibung");
					filteredTable[i][3] = rs.getString("Raum");
					
					
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getGeraeteByName(String name) {
			try {
				
				tableName = "Ger\u00e4te";
				con = DriverManager.getConnection(url);
				
				
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName+" WHERE Name LIKE '%"+name+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Name LIKE '%"+name+"%';");
				
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getInt("Ger\u00e4teID");
					filteredTable[i][1] = rs.getString("Name");
					filteredTable[i][2] = rs.getString("Beschreibung");
					filteredTable[i][3] = rs.getString("Raum");
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getGeraeteByDescript(String descript) {
			try {
				
				tableName = "Ger\u00e4te";
				con = DriverManager.getConnection(url);
				
				
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName+" WHERE Beschreibung LIKE '%"+descript+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Beschreibung LIKE '%"+descript+"%';");
				
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getInt("Ger\u00e4teID");
					filteredTable[i][1] = rs.getString("Name");
					filteredTable[i][2] = rs.getString("Beschreibung");
					filteredTable[i][3] = rs.getString("Raum");
					
					
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getGeraeteByRaum(String geraeteRaum) {
			try {
				
				tableName = "Ger\u00e4te";
				con = DriverManager.getConnection(url);
				
				
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName+" WHERE Raum LIKE '%"+geraeteRaum+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Raum LIKE '%"+geraeteRaum+"%';");
				
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getInt("Ger\u00e4teID");
					filteredTable[i][1] = rs.getString("Name");
					filteredTable[i][2] = rs.getString("Beschreibung");
					filteredTable[i][3] = rs.getString("Raum");
					
					
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getRaeumeByName(String name) {
			try {
				
				tableName = "R\u00e4ume";
				con = DriverManager.getConnection(url);
				
				
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Name) FROM "+tableName+" WHERE Name LIKE '%"+name+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Name LIKE '%"+name+"%';");
				
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getString("Name");
					filteredTable[i][1] = rs.getString("Beschreibung");
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getRaeumeByDescript(String descript) {
			try {
				
				tableName = "R\u00e4ume";
				con = DriverManager.getConnection(url);
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Name) FROM "+tableName+" WHERE Beschreibung LIKE '%"+descript+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Beschreibung LIKE '%"+descript+"%';");
				
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getString("Name");
					filteredTable[i][1] = rs.getString("Beschreibung");
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getGefahrstoffByName(String name) {
			try {
				
				tableName = "Gefahrstoffe";
				con = DriverManager.getConnection(url);
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Name) FROM "+tableName+" WHERE Name LIKE '%"+name+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Name LIKE '%"+name+"%';");
				
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getString("Name");
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getDeviceUnassignedData(int pID) {
			try {
				
				String tableName1 = "Ger\u00e4tezuordnung";
				String tableName2 = "Ger\u00e4te";
				con = DriverManager.getConnection(url);
				 
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName2+" WHERE "
																+ "("+tableName2+".Ger\u00e4teID IN ("
																	+"SELECT "+tableName2+".Ger\u00e4teID FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Ger\u00e4teID FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT Ger\u00e4teID, Name, Beschreibung, Raum FROM "+tableName2+" WHERE "
							+ "("+tableName2+".Ger\u00e4teID IN ("
								+"SELECT "+tableName2+".Ger\u00e4teID FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Ger\u00e4teID FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
				rs = pstmt.executeQuery();
				
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getInt("Ger\u00e4teID");
					filteredTable[i][1] = rs.getString("Name");
					if(filteredTable[i][1] == null)
						filteredTable[i][1] = "";
					filteredTable[i][2] = rs.getString("Beschreibung");
					if(filteredTable[i][2] == null)
						filteredTable[i][2] = "";
					filteredTable[i][3] = rs.getString("Raum");
					if(filteredTable[i][3] == null)
						filteredTable[i][3] = "";
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getDeviceAssignedData(int pID) {
			try {
				
				String tableName1 = "Ger\u00e4tezuordnung";
				String tableName2 = "Ger\u00e4te";
				con = DriverManager.getConnection(url);
				
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(PersonenID) FROM "+tableName1+" WHERE PersonenID='"+ pID +"'");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT "+tableName1+".Ger\u00e4teID, Name, Beschreibung, Raum, Nutzungszeit FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Ger\u00e4teID = "+tableName2+".Ger\u00e4teID WHERE PersonenID='"+ pID +"'");
				
				rs = pstmt.executeQuery();
				
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getInt("Ger\u00e4teID");
					filteredTable[i][1] = rs.getString("Name");
					if(filteredTable[i][1] == null)
						filteredTable[i][1] = "";
					filteredTable[i][2] = rs.getString("Beschreibung");
					if(filteredTable[i][2] == null)
						filteredTable[i][2] = "";
					filteredTable[i][3] = rs.getString("Raum");
					if(filteredTable[i][3] == null)
						filteredTable[i][3] = "";
					filteredTable[i][4] = rs.getInt("Nutzungszeit");
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getDangerSubstUnassignedData(int pID) {
			try {
				
				String tableName1 = "Gefahrstoffzuordnung";
				String tableName2 = "Gefahrstoffe";
				con = DriverManager.getConnection(url);
				 
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Name) FROM "+tableName2+" WHERE "
																+ "("+tableName2+".Name IN ("
																	+"SELECT "+tableName2+".Name FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Gefahrstoff FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT Name FROM "+tableName2+" WHERE "
							+ "("+tableName2+".Name IN ("
								+"SELECT "+tableName2+".Name FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Gefahrstoff FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
				rs = pstmt.executeQuery();
				
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getString("Name");
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getDangerSubstAssignedData(int pID) {
			try {
				
				String tableName1 = "Gefahrstoffzuordnung";
				String tableName2 = "Gefahrstoffe";
				con = DriverManager.getConnection(url);
				
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(PersonenID) FROM "+tableName1+" WHERE PersonenID='"+ pID +"'");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT "+tableName1+".Gefahrstoff FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Gefahrstoff = "+tableName2+".Name WHERE PersonenID='"+ pID +"'");
				
				rs = pstmt.executeQuery();
				
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getString("Gefahrstoff");
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getRoomUnassignedData(int dID) {
			try {
				
				String tableName1 = "Ger\u00e4te";
				String tableName2 = "R\u00e4ume";
				con = DriverManager.getConnection(url);
				 
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Name) FROM "+tableName2+" WHERE "
																+ "("+tableName2+".Name IN ("
																	+"SELECT "+tableName2+".Name FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Raum FROM "+tableName1+" WHERE Ger\u00e4teID='"+dID+"'))");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT Name FROM "+tableName2+" WHERE "
							+ "("+tableName2+".Name IN ("
								+"SELECT "+tableName2+".Name FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Raum FROM "+tableName1+" WHERE Ger\u00e4teID='"+dID+"'))");
				rs = pstmt.executeQuery();
				
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getString("Name");
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getRoomAssignedData(int dID) {
			try {
				
				String tableName1 = "Ger\u00e4te";
				String tableName2 = "R\u00e4ume";
				con = DriverManager.getConnection(url);
				
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName1+" WHERE Ger\u00e4teID='"+ dID +"'");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT "+tableName1+".Raum FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Raum = "+tableName2+".Name WHERE Ger\u00e4teID='"+ dID +"'");
				
				rs = pstmt.executeQuery();
				
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getString("Raum");
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static void assignDevice(int dID, int pID){
			try {
				con = DriverManager.getConnection(url);
				con.setAutoCommit(false);
				String stmt = "INSERT INTO Ger\u00e4tezuordnung (Ger\u00e4teID, PersonenID) "
						+ "VALUES (?, ?)";
				PreparedStatement pstmt = con.prepareStatement(stmt);
				pstmt.setInt(1, dID);
				pstmt.setInt(2, pID);
				pstmt.executeUpdate();
				con.commit();
				pstmt.close();
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void unassignDevice(int dID, int pID) {
			try {
				String stmt="delete from Ger\u00e4tezuordnung where Ger\u00e4teID='"+dID+"' AND PersonenID='"+pID+"'";
				
				con = DBConnection.connect();
				PreparedStatement pstmt = con.prepareStatement(stmt);
				con.setAutoCommit(false);
				pstmt.execute();
				con.commit();
			    pstmt.close();
			    con.close();
			}
		    catch (SQLException e) {
		    	e.printStackTrace();
			}
		}
		
		public static void assignRoom(int dID, String room){
			try {
				con = DriverManager.getConnection(url);
				con.setAutoCommit(false);
				String stmt = "UPDATE Ger\u00e4te SET Raum='"+room+"' WHERE Ger\u00e4teID="+dID+" ;";
				PreparedStatement pstmt = con.prepareStatement(stmt);
				pstmt.executeUpdate();
				con.commit();
				pstmt.close();
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void unassignRoom(int dID) {
			try {
				String stmt="UPDATE Ger\u00e4te SET Raum='' WHERE Ger\u00e4teID="+dID+";";
				
				con = DBConnection.connect();
				PreparedStatement pstmt = con.prepareStatement(stmt);
				con.setAutoCommit(false);
				pstmt.executeUpdate();
				con.commit();
			    pstmt.close();
			    con.close();
			}
		    catch (SQLException e) {
		    	e.printStackTrace();
			}
		}
		
		public static void assignDangerSubst(String dangerSubst, int pID){
			try {
				con = DriverManager.getConnection(url);
				con.setAutoCommit(false);
				String stmt = "INSERT INTO Gefahrstoffzuordnung (Gefahrstoff, PersonenID) "
						+ "VALUES (?, ?)";
				PreparedStatement pstmt = con.prepareStatement(stmt);
				pstmt.setString(1, dangerSubst);
				pstmt.setInt(2, pID);
				pstmt.executeUpdate();
				con.commit();
				pstmt.close();
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void unassignDangerSubst(String dangerSubst, int pID) {
			try {
				String stmt="delete from Gefahrstoffzuordnung where Gefahrstoff='"+dangerSubst+"' AND PersonenID='"+pID+"'";
				
				con = DBConnection.connect();
				PreparedStatement pstmt = con.prepareStatement(stmt);
				con.setAutoCommit(false);
				pstmt.execute();
				con.commit();
			    pstmt.close();
			    con.close();
			}
		    catch (SQLException e) {
		    	e.printStackTrace();
			}
		}
		
		public static void setUseTime(int dID, int pID, double useTime)
		{
			try {
				con = DriverManager.getConnection(url);
				con.setAutoCommit(false);
				String stmt = "UPDATE Ger\u00e4tezuordnung SET Nutzungszeit="+useTime+" WHERE Ger\u00e4teID='"+dID+"' AND PersonenID='"+pID+"'";
				PreparedStatement pstmt = con.prepareStatement(stmt);
				pstmt.executeUpdate();
				con.commit();
				pstmt.close();
				con.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		//User data base methods
		public static Connection connectLogin() {
		       try {
		    	   Class.forName("org.sqlite.JDBC");
		    	   con = DriverManager.getConnection("jdbc:sqlite:database/Benutzer.db");
		    	   //System.out.println("Connection SuccesFul");
		    	   //JOptionPane.showMessageDialog(null, "Verbindung zur Datenbank hergestellt.");
		    	   return con;
		       }catch(Exception e) {
		    	   System.out.println(e.getMessage());
		    	   return null;
		       }
			}
		
		public static Object[][] getUserData() {
			try {
				con = connectLogin();
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Benutzer;");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT * FROM Benutzer;");
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] tableData = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					tableData[i][0] = rs.getInt("ID");
					tableData[i][1] = rs.getString("Benutzername");
					tableData[i][2] = rs.getString("Passwort");
					
					i++;
				}
				pstmt.close();
				con.close();
				return tableData;
			} catch(SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static boolean checkLogin(String name, String pswrt) {
			try {
				con = connectLogin();
				//Statement stmt = con.createStatement();
				//ResultSet rs = stmt.executeQuery("SELECT Benutzername, Passwort FROM Benutzer WHERE Benutzername='"+name+"';");
				PreparedStatement pstmt = con.prepareStatement("SELECT Benutzername, Passwort FROM Benutzer WHERE Benutzername='"+name+"';");
				ResultSet rs = pstmt.executeQuery();
				String benutzername = rs.getString("Benutzername");
				String passwort = rs.getString("Passwort");
				//System.out.println("usrname: " + benutzername);
				//System.out.println("passwort: " + passwort);
				if (name.equals(benutzername) && pswrt.equals(passwort)) {
					pstmt.close();
					con.close();
					return true;
				}
				pstmt.close();
				con.close();
				return false;
			} catch(SQLException e) {
				//e.printStackTrace();
				return false;
			}
		}
		
		public static Object[][] getDeviceStatsData(int gID)
		{
			try {
				
				String tableName1 = "Ger\u00e4tezuordnung";
				String tableName2 = "Personen";
				con = DriverManager.getConnection(url);
				
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName1+" WHERE Ger\u00e4teID='"+ gID +"'");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT ID, Name, Vorname, Nutzungszeit FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".PersonenID = "+tableName2+".ID WHERE Ger\u00e4teID='"+ gID +"'");
				
				rs = pstmt.executeQuery();
				
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getInt("ID");
					filteredTable[i][1] = rs.getString("Name");
					filteredTable[i][2] = rs.getString("Vorname");
					filteredTable[i][3] = rs.getInt("Nutzungszeit");
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static Object[][] getDeviceStatsFilteredData(int gID, String name)
		{
			try {
				
				String tableName1 = "Ger\u00e4tezuordnung";
				String tableName2 = "Personen";
				con = DriverManager.getConnection(url);
				
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".PersonenID = "+tableName2+".ID WHERE Ger\u00e4teID='"+ gID +"' AND Name LIKE '%"+name+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT ID, Name, Vorname, Nutzungszeit FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".PersonenID = "+tableName2+".ID WHERE Ger\u00e4teID='"+ gID +"' AND Name LIKE '%"+name+"%';");
				
				rs = pstmt.executeQuery();
				
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getInt("ID");
					filteredTable[i][1] = rs.getString("Name");
					filteredTable[i][2] = rs.getString("Vorname");
					filteredTable[i][3] = rs.getInt("Nutzungszeit");
					i++;
				}
				
				tableName = "Personen";
				pstmt.close();
				
				con.close();
				
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public static boolean checkAdmin(String name) {
			try {
				con = connectLogin();
				//Statement stmt = con.createStatement();
				//ResultSet rs = stmt.executeQuery("SELECT ID FROM Benutzer WHERE Benutzername='"+name+"';");
				PreparedStatement pstmt = con.prepareStatement("SELECT ID FROM Benutzer WHERE Benutzername='"+name+"';");
				ResultSet rs = pstmt.executeQuery();
				int id =  rs.getInt("ID");
				//System.out.println("ID: "+id);
				pstmt.close();
				con.close();
				return id==1?true:false; //Equals if (id == 1){ return true } else { return false };
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		
		//Getter and Setter
		public static String getURL() {
			return url;
		}
		
		public static void setURL(String path) {
			url = path;
		}
		
		public static String getTableName() {
			return tableName;
		}
		
		public static void setTableName(String name) {
			tableName = name;
		}
}