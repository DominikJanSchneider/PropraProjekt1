package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import database.DBConnection;
import net.miginfocom.swing.MigLayout;
import security.pwEncrypt;

public class UserEditor extends JFrame {

	private static UserEditor userEditor = new UserEditor();
	
	private Color frameColor = new Color(32, 32, 32);
	private Color backgroundColor = new Color(25, 25, 25);
	private Color foregroundColor = new Color(255, 255, 255);
	
	private static int id = 0;
	
	private JPanel contentPane;
	private JPanel elementPanel;
	private JPanel userTablePanel;
	private JPanel updatePanel;
	
	private JScrollPane spUserTable;
	
	private JTable userTable = new JTable();
	
	private DefaultTableModel userTableModel;
	
	private DefaultTableCellRenderer userCellRenderer;
	
	private JLabel lblEditingElements;
	
	private JTextField tfUserName;
	private JTextField tfUserPassword;
	
	private JButton btnRefresh;
	private JButton btnAddUser;
	private JButton btnDeleteUser;
	private JButton btnSave;
	
	private int confirmed = 0;
	
	
	//Method for getting the frame, because of singelton scheme
	public static UserEditor getInstance() {
		return userEditor;
	}
	
	//Creating the frame
	private UserEditor() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Benutzerverwaltung");
		setBackground(frameColor);
		setForeground(foregroundColor);
		setBounds(100, 100, 1200, 600);
		contentPane = new JPanel();
		contentPane.setBackground(backgroundColor);
		contentPane.setForeground(foregroundColor);
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[65.0][370.0,grow][100.0,grow]"));
		
		//Panel for the editing elements
		elementPanel = new JPanel();
		elementPanel.setBackground(backgroundColor);
		elementPanel.setForeground(foregroundColor);
		contentPane.add(elementPanel, "cell 0 0, grow");
		elementPanel.setLayout(new MigLayout("", "[][]","[]10[]"));
		
		lblEditingElements = new JLabel("Bearbeitungselemente");
		lblEditingElements.setFont(new Font("Dialog", Font.BOLD, 14));
		lblEditingElements.setForeground(foregroundColor);
		elementPanel.add(lblEditingElements, "cell 0 0");
	
		//Refresh button
		btnRefresh = new JButton("Aktualisieren");
		ImageIcon refreshIcon = new ImageIcon(UserEditor.class.getResource("/images/refresh.png"));
		btnRefresh.setIcon(refreshIcon);
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getUserData();
			}
		});
		elementPanel.add(btnRefresh, "cell 0 1");
		
		//add data button
		btnAddUser = new JButton("Neuer Nutzer");
		ImageIcon newIcon = new ImageIcon(UserEditor.class.getResource("/images/new.png"));
		btnAddUser.setIcon(newIcon);
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addUser();
			}
		});
		elementPanel.add(btnAddUser,"cell 1 1");
		
		//delete data button
		btnDeleteUser = new JButton("Nutzer Entfernen");
		ImageIcon deleteIcon = new ImageIcon(UserEditor.class.getResource("/images/delete.png"));
		btnDeleteUser.setIcon(deleteIcon);
		btnDeleteUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteUser();
			}
		});
		elementPanel.add(btnDeleteUser,"gapleft 15, cell 2 1");
		
		//Panel for the user table
		userTablePanel = new JPanel();
		userTablePanel.setBackground(backgroundColor);
		userTablePanel.setForeground(foregroundColor);
		contentPane.add(userTablePanel, "cell 0 1, grow");
		userTablePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		spUserTable = new JScrollPane();
		userTablePanel.add(spUserTable, "cell 0 0, grow");
		spUserTable.setViewportView(userTable);
		
		getUserData(); //Loads the table
		
		//Panel for the update data
		updatePanel = new JPanel();
		updatePanel.setBackground(backgroundColor);
		updatePanel.setForeground(foregroundColor);
		contentPane.add(updatePanel, "cell 0 2, grow");
		updatePanel.setLayout(new MigLayout("", "[right][220]50[right][220][120]", "20[]"));
		
		//userName textfield
		JLabel lblUserName = new JLabel("Benutzername:");
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblUserName.setForeground(foregroundColor);
		updatePanel.add(lblUserName, "cell 0 0");
		tfUserName = new JTextField();
		updatePanel.add(tfUserName, "width 30%, cell 1 0");
		
		//userPassword textfield
		JLabel lblUserPassword = new JLabel("Passwort:");
		lblUserPassword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblUserPassword.setForeground(foregroundColor);
		updatePanel.add(lblUserPassword, "cell 2 0");
		tfUserPassword = new JTextField();
		updatePanel.add(tfUserPassword, "width 30%, cell 3 0");
		
		//Save button
		String btnTitle = "\u00c4nderungen \n Speichern";
		btnSave = new JButton("<html>"+btnTitle.replaceAll("\\n", "<br>")+"</html>");
		ImageIcon saveIcon = new ImageIcon(UserEditor.class.getResource("/images/save.png"));
		btnSave.setIcon(saveIcon);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tfUserName.getText().isEmpty() | tfUserPassword.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(), "Bitte Benutzernamen und Passwort eintragen", "Dialog", JOptionPane.ERROR);
				} else {
					saveUser();
				}
			}
		});
		updatePanel.add(btnSave, "width 25%, gapleft 50, cell 4 0");
		
		//Mouslistener add clicked row from table in textfields and saves id of clicked row for Deletion
		userTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = userTable.getSelectedRow();
				id = Integer.parseInt(userTable.getValueAt(i, 0).toString());
				fillTextFields();
			}
		});
		
		setVisible(true);
	}
	
	public void getUserData() {
		userTableModel = new DefaultTableModel(new Object[][] {}, new String[] {"ID", "Benutzername", "Passwort"}) {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class getColumnClass(int column) { //Modified that ID will be sorted correctly
				if (column == 0) {
					return Integer.class;
				} else {
					return String.class;
				}
			}
		};
		
		userTable.setModel(userTableModel);
		userTable.setRowSorter(new TableRowSorter<DefaultTableModel>(userTableModel));
		
		Object[][] userData = DBConnection.getUserData();
		for (int i = 0; i < userData.length; i++) {
			userTableModel.addRow(new Object[] {userData[i][0],
												userData[i][1],
												userData[i][2]
					
			});
		}
		userTableModel.fireTableDataChanged();
		
		userCellRenderer = new DefaultTableCellRenderer();
		userCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		userTable.getColumnModel().getColumn(0).setCellRenderer(userCellRenderer);
		userTable.setRowHeight(20);
	}
	
	private void addUser() {
		JTextField userName = new JTextField();
		JTextField userPassword = new JTextField();
		int g = -1;
		int h = 1;
		
		while (g < 0) { // while loop does exit when window is closed or new entry is confirmed (g++;)
			//initialize fields for user entrys
			Object[] message = {"Benutzername (Text)", userName,
								"Benutzerpasswort (Text)", userPassword};
			
			JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
			pane.createDialog(null, "Neuen Nutzer anlegen").setVisible(true);

			
			

			if (pane.getValue() != null) {
				confirmed = 1;
			}

			if (pane.getValue() == null) {
				g++;
			} else {
				try {
					String insertUser = "INSERT INTO Benutzer (Benutzername,Passwort) "
									  + "VALUES (?,?);";	
					
					int value = ((Integer) pane.getValue()).intValue();
					//System.out.println(pane.getValue());
					
					if (value == 0) {
						String userNameVar;
						String userPasswordVar;
						
						h = 1;
						
						if (userName.getText().isEmpty() | userPassword.getText().isEmpty()) {
							String warning = "Bitte Benutzernamen und Passwort eingeben\n";
							JOptionPane.showMessageDialog(new JFrame(), warning, "Dialog", JOptionPane.ERROR_MESSAGE);
							h = 0;
						}
						
						else if (!userName.getText().isEmpty() && !userPassword.getText().isEmpty() && h != 0) {
							try {
								h = 2;	//h = 2 for first while-loop ; h = 1 for 2nd while-loop ;
										//h = 0 to exit else if (repeats method)
								
								while ( h == 2) {
									h = 1;
									
									try {
										userNameVar = userName.getText();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Benutzername'", "Dialog", JOptionPane.ERROR_MESSAGE);
										h = 0;
									}
									
									try {
										userPasswordVar = userPassword.getText();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Passwort'", "Dialog", JOptionPane.ERROR_MESSAGE);
										h = 0;
									}
								}//End: while (h == 2)
							} catch (Exception e) {
								JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
							}
							
							//Execute database command if there are no wrong entries
							while (h == 1) {
								h = 0;
								try {
									userNameVar = userName.getText();
									userPasswordVar = userPassword.getText();
								
									Connection con = DBConnection.connectLogin();
									con.setAutoCommit(false);
									
									PreparedStatement pstmt = con.prepareStatement(insertUser);
									pstmt.setString(1, userNameVar);
									pstmt.setString(2, pwEncrypt.toHexString(pwEncrypt.getSHA(userPasswordVar)));		// save pw with SHA-256 encryption
									
									pstmt.executeUpdate();
									con.commit();
									
									/*System.out.println("Benutzer erstellt \n" +
													   "Benutzername: " + userNameVar +
													   "Passwort: " + userPasswordVar);
									*/
									pstmt.close();
									con.close();
									g++;
								} catch (SQLException e) {
									//System.out.println(e.getMessage());
									JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
								}
							} //End: while (h == 1)
						} //End: else if
						
						else {
							
						}
					} //End: if (value == 0)
					
					else {
						g++;
					}
				} catch (NullPointerException e) {
					userName.setText("");
					userPassword.setText("");
					System.out.println(e);
				}
			} //End: first else
		} //End: while (g < 0)
		

		getUserData();

		

		if (confirmed == 1) {

			JOptionPane.showMessageDialog(new JFrame(), "Eintrag erstellt");
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Bearbeitung abgebrochen");
		}
		confirmed = 0;

	}
	
	//Method to delete user entry from database
	private void deleteUser() {
		try {
			//Gets current selected row ID, compares the Database ID's and deletes matching entry
			String query = "DELETE FROM Benutzer WHERE ID='"+id+"'";
			
			Connection con = DBConnection.connectLogin();
			PreparedStatement pstmt = con.prepareStatement(query);
			con.setAutoCommit(false);
			//System.out.println("Lösche Eintrag...");
			pstmt.execute();
			con.commit();
			pstmt = con.prepareStatement("UPDATE sqlite_sequence SET seq='"+(id-1)+"' WHERE name='Benutzer';");
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			
			pstmt.close();
			con.close();
			
			getUserData();
			JOptionPane.showMessageDialog(new JFrame(), "Benutzer gelöscht", "Dialog", JOptionPane.ERROR_MESSAGE);
		
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Method that saves edited user data
	public void saveUser() {
		String userNameVar;
		String userPasswordVar;
		
		int g = 0;
		
		try {
			g = 2;
			
			//Queries to disallow wrong database entries
			while (g == 2) {
				g = 1;
				
				try {
					userNameVar = tfUserName.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Benutzername'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				
				try {
					userPasswordVar = tfUserPassword.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Passwort'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
			} //End: while (g == 2)
			
			//Execute database update if there are no wrong entries
			while (g == 1) {
				g = 0;
				String query = "UPDATE Benutzer SET Benutzername='"+tfUserName.getText()+"', Passwort='"+pwEncrypt.toHexString(pwEncrypt.getSHA(tfUserPassword.getText()))+"', ID='"+id+"' WHERE ID='"+id+"';";
				
				Connection con = DBConnection.connectLogin();
				PreparedStatement pstmt = con.prepareStatement(query);
				con.setAutoCommit(false);
				//System.out.println("Speichert Eintrag...");
				pstmt.execute();
				con.commit();
				
				pstmt.close();
				con.close();
				
				getUserData();
				JOptionPane.showMessageDialog(new JFrame(), "Eintrag ge\u00e4ndert", "Dialog", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Method to fill textfields with column-entries of selected row
	public void fillTextFields() {
		int selRow = userTable.getSelectedRow();
		String fillUserName = (String) userTable.getValueAt(selRow, 1);
		String fillUserPassword = (String) userTable.getValueAt(selRow, 2);
		
		tfUserName.setText(fillUserName);
		tfUserPassword.setText(fillUserPassword);
	}
}
