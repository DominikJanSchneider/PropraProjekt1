package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (RaumID) FROM R\u00e4ume;");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM R\u00e4ume;");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] tableData = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				tableData[i][0] = rs.getInt("RaumID");
				tableData[i][1] = rs.getString("Name");
				tableData[i][2] = rs.getString("Beschreibung");
				
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
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM "+tableName+" WHERE Name='"+MainFrame.getSearchTF().getText()+"';");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Name ='"+MainFrame.getSearchTF().getText()+"';");
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
			PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Intern='Ja'");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt = con.prepareStatement("SELECT * FROM Personen WHERE Intern='Ja'");
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
				PreparedStatement pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Extern='Ja'");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt = con.prepareStatement("SELECT * FROM Personen WHERE Extern='Ja'"	);
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