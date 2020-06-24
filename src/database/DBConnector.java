package database;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gui.MainFrame;
import security.pwEncrypt;


public class DBConnector {
	
	private static String urlCore = "jdbc:sqlite:database/CoreDatabase.db";
	private static String urlLogin = "jdbc:sqlite:database/UserDatabase.db";;
	private static Connection con = null;
		
	public static Connection connectCore() {
		try {
			con = DriverManager.getConnection(urlCore);
			return con;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void deconnect() {
		try {
			if(con != null) {
				con.close();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Object[][] getDeviceData() {
		con = connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("SELECT COUNT (Ger\u00e4teID) FROM Ger\u00e4te;");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt.close();
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
			return tableData;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			deconnect();
       }
	}
	
	public static Object[][] getRoomsData() {
		con = connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("SELECT COUNT (Name) FROM R\u00e4ume;");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt.close();
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
			return tableData;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			deconnect();
       }
	}
	
	public static Object[][] getGefahrstoffeData() {
		con = connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("SELECT COUNT (Name) FROM Gefahrstoffe;");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt.close();
			pstmt = con.prepareStatement("SELECT * FROM Gefahrstoffe;");
			rs = pstmt.executeQuery();
			int columnCount = rs.getMetaData().getColumnCount();
			Object[][] tableData = new Object[rowCount][columnCount];
			int i = 0;
			
			while (rs.next()) {
				tableData[i][0] = rs.getString("Name");
				i++;
			}
			return tableData;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			deconnect();
       }
	}
	
	
	
	
	
	public static Object[][] getName() {
		String tableName = "Personen";
		con = connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("SELECT COUNT (ID) FROM "+tableName+" WHERE Name LIKE '%"+MainFrame.getSearchTF().getText()+"%';");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt.close();
			pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Name LIKE '%"+MainFrame.getSearchTF().getText()+"%';");
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
			return filteredTable;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			deconnect();
       }
	}
	
	// Method that returns all rows of the table where Ifwt is not null
	public static Object[][] getIfwt()  {
		con = connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Ifwt!='' AND NOT Ifwt IS NULL");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt.close();
			pstmt = con.prepareStatement("SELECT * FROM Personen WHERE Ifwt!='' AND Ifwt IS NOT NULL");
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
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			deconnect();
       }
	}
	
	// Method that returns all rows of the table where Ifwt equals LMN
	public static Object[][] getLMN()  {
		con = connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LMN'");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt.close();
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
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			deconnect();
       }
	}
	
	// Method that returns all rows of the table where Ifwt equals LMW
	public static Object[][] getLMW()  {
		con = connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LMW'");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt.close();
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
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			deconnect();
       }
	}
	
	// Method that returns all rows of the table where Ifwt equals LOT
	public static Object[][] getLOT()  {
		con = connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LOT'");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt.close();
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
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			deconnect();
       }
	}
	
	// Method that returns all rows of the table where Ifwt equals LWF
	public static Object[][] getLWF()  {
		con = connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Ifwt='LWF'");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt.close();
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
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			deconnect();
       }
	}
	
	//TODO Method that returns all MNaF that are not null !MUSS ICH NOCH UEBERARBEITEN! Dominik
	public static Object[][] getMNaF()  {
		con = connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE MNaF!='' AND NOT MNaF IS NULL");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt.close();
			pstmt = con.prepareStatement("SELECT * FROM Personen WHERE MNaF!='' AND NOT MNaF IS NULL");
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
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			deconnect();
       }
	}
	
	//Method that returns all rows of the table where intern is set
	public static Object[][] getIntern()  {
		con = connectCore();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Intern!='' AND NOT Intern IS NULL");
			ResultSet rs = pstmt.executeQuery();
			int rowCount = rs.getInt(1);
			pstmt.close();
			pstmt = con.prepareStatement("SELECT * FROM Personen WHERE Intern!='' AND NOT Intern IS NULL");
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
			return filteredTable;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			deconnect();
       }
	}
	
	//Method that returns all rows of the table where extern is set
		public static Object[][] getExtern()  {
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Personen WHERE Extern!='' AND NOT Extern IS NULL");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
				pstmt = con.prepareStatement("SELECT * FROM Personen WHERE Extern!='' AND NOT Extern IS NULL");
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
				return filteredTable;
				
			} catch(SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		//User data base connection
		public static Object[][] getDeviceByID(String id) {
			String tableName = "Ger\u00e4te";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName+" WHERE Ger\u00e4teID LIKE '%"+id+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getDeviceByName(String name) {
			String tableName = "Ger\u00e4te";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName+" WHERE Name LIKE '%"+name+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getDeviceByDescript(String descript) {
			String tableName = "Ger\u00e4te";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName+" WHERE Beschreibung LIKE '%"+descript+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getDeviceByRoom(String geraeteRaum) {
			String tableName = "Ger\u00e4te";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName+" WHERE Raum LIKE '%"+geraeteRaum+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getRoomByName(String name) {
			String tableName = "R\u00e4ume";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Name) FROM "+tableName+" WHERE Name LIKE '%"+name+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getRoomByDescript(String descript) {
			String tableName = "R\u00e4ume";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Name) FROM "+tableName+" WHERE Beschreibung LIKE '%"+descript+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getDangerSubstByName(String name) {
			String tableName = "Gefahrstoffe";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Name) FROM "+tableName+" WHERE Name LIKE '%"+name+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
				pstmt = con.prepareStatement("SELECT * FROM "+tableName+" WHERE Name LIKE '%"+name+"%';");
				
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getString("Name");
					i++;
				}
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getDeviceUnassignedData(int pID) {
			PreparedStatement pstmt = null;
			String tableName1 = "Ger\u00e4tezuordnung";
			String tableName2 = "Ger\u00e4te";
			con = connectCore();
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName2+" WHERE "
																+ "("+tableName2+".Ger\u00e4teID IN ("
																	+"SELECT "+tableName2+".Ger\u00e4teID FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Ger\u00e4teID FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getDeviceAssignedData(int pID) {
			String tableName1 = "Ger\u00e4tezuordnung";
			String tableName2 = "Ger\u00e4te";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(PersonenID) FROM "+tableName1+" WHERE PersonenID='"+ pID +"'");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getDangerSubstUnassignedData(int pID) {
			String tableName1 = "Gefahrstoffzuordnung";
			String tableName2 = "Gefahrstoffe";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Name) FROM "+tableName2+" WHERE "
																+ "("+tableName2+".Name IN ("
																	+"SELECT "+tableName2+".Name FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Gefahrstoff FROM "+tableName1+" WHERE PersonenID='"+pID+"'))");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getDangerSubstAssignedData(int pID) {
			String tableName1 = "Gefahrstoffzuordnung";
			String tableName2 = "Gefahrstoffe";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(PersonenID) FROM "+tableName1+" WHERE PersonenID='"+ pID +"'");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
				pstmt = con.prepareStatement("SELECT "+tableName1+".Gefahrstoff FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Gefahrstoff = "+tableName2+".Name WHERE PersonenID='"+ pID +"'");
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getString("Gefahrstoff");
					i++;
				}
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getRoomUnassignedData(int dID) {
			String tableName1 = "Ger\u00e4te";
			String tableName2 = "R\u00e4ume";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				 pstmt = con.prepareStatement("SELECT COUNT(Name) FROM "+tableName2+" WHERE "
																+ "("+tableName2+".Name IN ("
																	+"SELECT "+tableName2+".Name FROM "+tableName2+" EXCEPT SELECT "+tableName1+".Raum FROM "+tableName1+" WHERE Ger\u00e4teID='"+dID+"'))");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getRoomAssignedData(int dID) {
			String tableName1 = "Ger\u00e4te";
			String tableName2 = "R\u00e4ume";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName1+" WHERE Ger\u00e4teID='"+ dID +"'");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
				pstmt = con.prepareStatement("SELECT "+tableName1+".Raum FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".Raum = "+tableName2+".Name WHERE Ger\u00e4teID='"+ dID +"'");
				rs = pstmt.executeQuery();
				int columnCount = rs.getMetaData().getColumnCount();
				Object[][] filteredTable = new Object[rowCount][columnCount];
				int i = 0;
				
				while (rs.next()) {
					filteredTable[i][0] = rs.getString("Raum");
					i++;
				}
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static String getLabComment(int pID)
		{
			String tableName ="Personen";
			con = connectCore();
			PreparedStatement pstmt = null;
			try
			{
				pstmt = con.prepareStatement("SELECT LabKommentar FROM "+tableName+" WHERE ID='"+pID+"'");
				ResultSet rs = pstmt.executeQuery();
				String res = rs.getString("LabKommentar");
				return res;
			} 
			catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static String getDangerSubstComment(int pID)
		{
			String tableName = "Personen";
			con = connectCore();
			PreparedStatement pstmt = null;
			try
			{
				pstmt = con.prepareStatement("SELECT GefKommentar FROM "+tableName+" WHERE ID='"+pID+"'");
				ResultSet rs = pstmt.executeQuery();
				String res = rs.getString("GefKommentar");
				return res;
			} 
			catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static void assignDevice(int dID, int pID){
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				String stmt = "INSERT INTO Ger\u00e4tezuordnung (Ger\u00e4teID, PersonenID) "
						+ "VALUES (?, ?)";
				pstmt = con.prepareStatement(stmt);
				pstmt.setInt(1, dID);
				pstmt.setInt(2, pID);
				pstmt.executeUpdate();
				con.commit();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static void unassignDevice(int dID, int pID) {
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("delete from Ger\u00e4tezuordnung where Ger\u00e4teID='"+dID+"' AND PersonenID='"+pID+"'");
				con.setAutoCommit(false);
				pstmt.execute();
				con.commit();
			}
		    catch (SQLException e) {
		    	e.printStackTrace();
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static void assignRoom(int dID, String room){
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement("UPDATE Ger\u00e4te SET Raum='"+room+"' WHERE Ger\u00e4teID="+dID+" ;");
				pstmt.execute();
				con.commit();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static void unassignRoom(int dID) {
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("UPDATE Ger\u00e4te SET Raum='' WHERE Ger\u00e4teID="+dID+";");
				con.setAutoCommit(false);
				pstmt.executeUpdate();
				con.commit();
			}
		    catch (SQLException e) {
		    	e.printStackTrace();
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static void assignDangerSubst(String dangerSubst, int pID){
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				String stmt = "INSERT INTO Gefahrstoffzuordnung (Gefahrstoff, PersonenID) "
						+ "VALUES (?, ?)";
				pstmt = con.prepareStatement(stmt);
				pstmt.setString(1, dangerSubst);
				pstmt.setInt(2, pID);
				pstmt.executeUpdate();
				con.commit();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static void unassignDangerSubst(String dangerSubst, int pID) {
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				String stmt="delete from Gefahrstoffzuordnung where Gefahrstoff='"+dangerSubst+"' AND PersonenID='"+pID+"'";
				pstmt = con.prepareStatement(stmt);
				con.setAutoCommit(false);
				pstmt.execute();
				con.commit();
			}
		    catch (SQLException e) {
		    	e.printStackTrace();
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static void setUseTime(int dID, int pID, double useTime)
		{
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				String stmt = "UPDATE Ger\u00e4tezuordnung SET Nutzungszeit="+useTime+" WHERE Ger\u00e4teID='"+dID+"' AND PersonenID='"+pID+"'";
				pstmt = con.prepareStatement(stmt);
				pstmt.executeUpdate();
				con.commit();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		
		//User data base methods
		public static Connection connectLogin() {
		       try {
		    	   con = DriverManager.getConnection(urlLogin);
		    	   return con;
		       }catch(Exception e) {
		    	   System.out.println(e.getMessage());
		    	   return null;
		       }
		}
		
		public static Object[][] getUserData() {
			con = connectLogin();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT (ID) FROM Benutzer;");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return tableData;
			} catch(SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static boolean checkLogin(String name, String pswrt) {
			con = connectLogin();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT Benutzername, Passwort FROM Benutzer WHERE Benutzername='"+name+"';");
				ResultSet rs = pstmt.executeQuery();
				if(rs.isAfterLast())
				{
					return false;
				}
				String benutzername = rs.getString("Benutzername");
				String passwort = rs.getString("Passwort");
				try {
					if (name.equals(benutzername) && pwEncrypt.toHexString(pwEncrypt.getSHA(pswrt)).equals(passwort)) {			// check if enrypted pw matches database entry
						return true;
					}
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				return false;
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static Object[][] getDeviceStatsData(int gID)
		{
			String tableName1 = "Ger\u00e4tezuordnung";
			String tableName2 = "Personen";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName1+" WHERE Ger\u00e4teID='"+ gID +"'");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
			
		}
		
		public static Object[][] getDeviceStatsFilteredData(int gID, String name)
		{
			String tableName1 = "Ger\u00e4tezuordnung";
			String tableName2 = "Personen";
			con = connectCore();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT COUNT(Ger\u00e4teID) FROM "+tableName1+" INNER JOIN "+tableName2+" ON "+tableName1+".PersonenID = "+tableName2+".ID WHERE Ger\u00e4teID='"+ gID +"' AND Name LIKE '%"+name+"%';");
				ResultSet rs = pstmt.executeQuery();
				int rowCount = rs.getInt(1);
				pstmt.close();
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
				return filteredTable;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		public static boolean checkAdmin(String name) {
			con = connectLogin();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement("SELECT ID FROM Benutzer WHERE Benutzername='"+name+"';");
				ResultSet rs = pstmt.executeQuery();
				int id =  rs.getInt("ID");
				//System.out.println("ID: "+id);
				return id==1?true:false; //Equals if (id == 1){ return true } else { return false };
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
			finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				deconnect();
	       }
		}
		
		
		//Getter and Setter
		public static String getURLCore() {
			return urlCore;
		}
		
		public static void setURLCore(String path) {
			urlCore = path;
		}
}