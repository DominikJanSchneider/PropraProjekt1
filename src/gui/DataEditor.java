
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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import database.DBConnection;
import net.miginfocom.swing.MigLayout;

public class DataEditor extends JFrame{
	

	private static final long serialVersionUID = 1L;

	private static DataEditor dataEditor = new DataEditor();
	
	private Color frameColor = new Color(32, 32, 32);
	private Color backgroundColor = new Color(25, 25, 25);
	private Color foregroundColor = new Color(255, 255, 255);
	
	private JPanel contentPane;
	private JPanel elementPanel;
	private static JPanel tablePanel;
	private JPanel textFieldPanel;
	private JPanel textAreaPanel;
	private JLabel lblBearbeitungselemente;
	private JButton btnRefresh;
	private JButton btnAdd;
	private JButton btnDelete;
	private static JScrollPane spTable;
	
	private static JTextField textField;
	private static JTextField textField_2;
	private static JTextField textField_3;
	private static JTextField textField_4;
	private static JTextField textField_5;
	private static JTextField textField_6;
	private static JTextField textField_7;
	private static JTextField textField_8;
	private static JTextField textField_9;
	private static JTextField textField_10;
	private static JTextField textField_11;
	private static JTextArea textArea_1;
	private static JTextArea textArea_2;
	private static JTextArea textArea_3;
	private JScrollPane spInstr;
	private JScrollPane spLab;
	private JScrollPane spHazard;
	private static int ID = 0;
	private static DeviceAssignement deviceAssignement;
	private static DangerSubstAssignement dangerSubstAssignement;
	
	private static Connection con = null;
	static PreparedStatement pstmt = null;
	
	
	// Method for getting the frame, because of singelton scheme
	public static DataEditor getInstance() {
		return dataEditor;
	}
	
	// Creating the frame
	private DataEditor() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Datenbank-Editor");
		setBackground(frameColor);
		setForeground(foregroundColor);
		setBounds(100, 100, 1200, 750);
		contentPane = new JPanel();
		contentPane.setBackground(backgroundColor);
		contentPane.setForeground(foregroundColor);
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[65.0][250.0,grow][120.0,grow][100.0,grow]"));
		
		// Panel for the editing elements
		elementPanel = new JPanel();
		elementPanel.setBackground(backgroundColor);
		elementPanel.setForeground(foregroundColor);
		contentPane.add(elementPanel, "cell 0 0,grow");
		elementPanel.setLayout(new MigLayout("", "[][]", "[]10[]"));
		
		lblBearbeitungselemente = new JLabel("Bearbeitungselemente");
		lblBearbeitungselemente.setFont(new Font("Dialog", Font.BOLD, 14));
		lblBearbeitungselemente.setForeground(foregroundColor);
		elementPanel.add(lblBearbeitungselemente, "cell 0 0");
		
		//refresh button
		btnRefresh = new JButton("Aktualisieren");
		ImageIcon refreshIcon = new ImageIcon(DataEditor.class.getResource("/images/refresh.png"));
		btnRefresh.setIcon(refreshIcon);
		elementPanel.add(btnRefresh, "cell 0 1");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getData();
			}
		});
		
		// add Data Button
		btnAdd = new JButton("Neuer Eintrag");
		ImageIcon newIcon = new ImageIcon(DataEditor.class.getResource("/images/new.png"));
		btnAdd.setIcon(newIcon);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newData();
			}
		});
		elementPanel.add(btnAdd, "gapleft 10, cell 1 1");
		
		// delete data button
		btnDelete = new JButton("Eintrag löschen");
		ImageIcon deleteIcon = new ImageIcon(DataEditor.class.getResource("/images/delete.png"));
		btnDelete.setIcon(deleteIcon);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteData();
			}
		});
		elementPanel.add(btnDelete, "gapleft 30, cell 2 1");
		
		// Panel for the table that resembles the database
		tablePanel = new JPanel();
		tablePanel.setBackground(backgroundColor);
		tablePanel.setForeground(foregroundColor);
		contentPane.add(tablePanel, "cell 0 1,grow");
		tablePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		spTable = new JScrollPane();
		tablePanel.add(spTable, "cell 0 0,grow");
		
		// cloned table for edit window
		spTable.setViewportView(MainFrame.getEditorTable());
	
		
		// Panel for the update button
		textFieldPanel = new JPanel();
		textFieldPanel.setBackground(backgroundColor);
		textFieldPanel.setForeground(foregroundColor);
		contentPane.add(textFieldPanel, "cell 0 2,grow");
		textFieldPanel.setLayout(new MigLayout("", "[right][220][right][220][120]", "[]10[]10[]10[]0[]0[]"));
		
		// name textfield
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblName.setForeground(foregroundColor);
		textFieldPanel.add(lblName, "cell 0 0");
		textField = new JTextField();
		textFieldPanel.add(textField, "width 30%, cell 1 0");
		
		// prename textfield
		JLabel lblPname = new JLabel("Vorname:");
		lblPname.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPname.setForeground(foregroundColor);
		textFieldPanel.add(lblPname, "cell 0 1");
		textField_2 = new JTextField();
		textFieldPanel.add(textField_2, "width 30%, cell 1 1");
		
		// date textfield
		JLabel lblDate = new JLabel("Datum:");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDate.setForeground(foregroundColor);
		textFieldPanel.add(lblDate, "cell 0 2");
		textField_3 = new JTextField();
		textFieldPanel.add(textField_3, "width 30%, cell 1 2");
		
		// Ifwt Textfield
		JLabel lblIfwt = new JLabel("Ifwt:");
		lblIfwt.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblIfwt.setForeground(foregroundColor);
		textFieldPanel.add(lblIfwt, "cell 0 3");
		textField_4 = new JTextField();
		textFieldPanel.add(textField_4, "width 30%, cell 1 3");
		
		// MNaF textfield
		JLabel lblMNaF = new JLabel("MNaF:");
		lblMNaF.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblMNaF.setForeground(foregroundColor);
		textFieldPanel.add(lblMNaF, "cell 0 4");
		textField_5 = new JTextField();
		textFieldPanel.add(textField_5, "width 30%, cell 1 4");
		
		// intern textfield
		JLabel lblIntern = new JLabel("Intern:");
		lblIntern.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblIntern.setForeground(foregroundColor);
		textFieldPanel.add(lblIntern, "cell 0 5");
		textField_6 = new JTextField();
		textFieldPanel.add(textField_6, "width 30%, cell 1 5");
		
		// employment relationship textfield
		JLabel lblEmpl = new JLabel("   Beschäftigungsverhältnis:");
		lblEmpl.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEmpl.setForeground(foregroundColor);
		textFieldPanel.add(lblEmpl, "cell 2 0");
		textField_7 = new JTextField();
		textFieldPanel.add(textField_7, "width 30%, cell 3 0");
		
		// beginning textfield
		JLabel lblStart = new JLabel("   Beginn:");
		lblStart.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblStart.setForeground(foregroundColor);
		textFieldPanel.add(lblStart, "cell 2 1");
		textField_8 = new JTextField();
		textFieldPanel.add(textField_8, "width 30%, cell 3 1");
		
		// end textfield
		JLabel lblEnd = new JLabel("   Ende:");
		lblEnd.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEnd.setForeground(foregroundColor);
		textFieldPanel.add(lblEnd, "cell 2 2");
		textField_9 = new JTextField();
		textFieldPanel.add(textField_9, "width 30%, cell 3 2");
		
		// external textfield
		JLabel lblExternal = new JLabel("   Extern:");
		lblExternal.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblExternal.setForeground(foregroundColor);
		textFieldPanel.add(lblExternal, "cell 2 3");
		textField_10 = new JTextField();
		textFieldPanel.add(textField_10, "width 30%, cell 3 3");
		
		// e-mail textfield
		JLabel lblMail = new JLabel("   E-Mail:");
		lblMail.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblMail.setForeground(foregroundColor);
		textFieldPanel.add(lblMail, "cell 2 4");
		textField_11 = new JTextField();
		textFieldPanel.add(textField_11, "width 30%, cell 3 4");
		
		// savebutton
		String twoLines = "Änderungen \n Speichern";
		JButton btnSave = new JButton("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");
		ImageIcon saveIcon = new ImageIcon(DataEditor.class.getResource("/images/save.png"));
		btnSave.setIcon(saveIcon);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().isEmpty() | textField_2.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(), "Bitte Name und Vorname eintragen", "Dialog", JOptionPane.ERROR_MESSAGE);
				}
				else {
					saveData();
				}
			}
		});
		textFieldPanel.add(btnSave, "width 25%, gapleft 50, cell 4 4");
		
		//edit devices button
		JButton btnEditDevices = new JButton("Geräte bearbeiten");
		btnEditDevices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEditDevicesPressed();
				deviceAssignement.setVisible(true);
			}
		});
		textFieldPanel.add(btnEditDevices, "width 25%, gapleft 50, cell 4 2");
		
		//edit dangerous substances button
		JButton btnEditDangerSubst = new JButton("Gefahrstoffe bearbeiten");
		btnEditDangerSubst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEditDangerSubstPressed();
				dangerSubstAssignement.setVisible(true);
			}
		});
		textFieldPanel.add(btnEditDangerSubst, "cell 4 1,width 25%,gapx 50");
		
		
		textAreaPanel = new JPanel();
		textAreaPanel.setBackground(backgroundColor);
		textAreaPanel.setForeground(foregroundColor);
		contentPane.add(textAreaPanel, "cell 0 3,grow");
		textAreaPanel.setLayout(new MigLayout("", "[grow]", "[]2[grow]"));
		
		
		// Allgemeine Unterweisung textfield
		JLabel lblInstr = new JLabel("Allg. Unterweisung:");
		lblInstr.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblInstr.setForeground(foregroundColor);
		spInstr = new JScrollPane(
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textAreaPanel.add(lblInstr, "cell 0 0");
		textAreaPanel.add(spInstr, "grow, cell 0 1 1 2");
		textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		textArea_1.setWrapStyleWord(true);
		spInstr.setViewportView(textArea_1);
				
		// Laboreinrichtungen textfield
		JLabel lblLab = new JLabel("   Laboreinrichtungen:");
		lblLab.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblLab.setForeground(foregroundColor);
		spLab = new JScrollPane(
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textAreaPanel.add(lblLab, "cell 1 0");
		textAreaPanel.add(spLab, "grow, cell 1 1 1 2");
		textArea_2 = new JTextArea();
		textArea_2.setLineWrap(true);
		textArea_2.setWrapStyleWord(true);
		spLab.setViewportView(textArea_2);
		
		// Gefahrstoffe textfield
		JLabel lblHazard = new JLabel("   Gefahrstoffe:");
		lblHazard.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblHazard.setForeground(foregroundColor);
		spHazard = new JScrollPane(
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textAreaPanel.add(lblHazard, "cell 2 0");
		textAreaPanel.add(spHazard, "grow, cell 2 1 1 2");
		textArea_3 = new JTextArea();
		textArea_3.setLineWrap(true);
		textArea_3.setWrapStyleWord(true);
		spHazard.setViewportView(textArea_3);
		
		
		// Mouselistener adds clicked row from table in Textfields and saves ID of clicked row for Deletion
		MainFrame.getEditorTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = MainFrame.getEditorTable().getSelectedRow();
				ID = Integer.parseInt(MainFrame.getEditorTable().getValueAt(i, 0).toString());

				fillFields();
			}
		});
		
		
		
		setVisible(true);
	}
	
	
	// method to add new data into database
	@SuppressWarnings("unused")
	private void newData() {									// used in Actionlistener of JButton btnAdd "Neuer Eintrag"

		JTextField Name = new JTextField();
		JTextField PName = new JTextField();
		JTextField Date = new JTextField();
		JTextField Ifwt = new JTextField();
		JTextField MNaF = new JTextField();
		JTextField Intern = new JTextField();
		JTextField Empl = new JTextField();
		JTextField Start = new JTextField();
		JTextField End = new JTextField();
		JTextField External = new JTextField();
		JTextField Mail = new JTextField();
		JTextField Instr = new JTextField();
		JTextField Lab = new JTextField();
		JTextField Hazard = new JTextField();
		int g = -1;
		int h = 1;

		while (g < 0) {							// while loop does exit when window is closed or new entry is confirmed (g++;)
			
			// initialize fields for user entrys
			Object[] message = {"Name (Text)", Name,
								"Vorname (Text)", PName, 
								"Datum (Date)", Date, 
								"Ifwt (Text)", Ifwt, 
								"MNaF (Text)", MNaF, 
								"Intern (Text)", Intern, 
								"Beschäftigungsverhältnis (Text)", Empl, 
								"Beginn (Text)", Start, 
								"Ende (Text)", End, 
								"Extern (Text)", External, 
								"E-Mail Adresse (Text)", Mail, 
								"Allgemeine Unterweisung (Text)", Instr, 
								"Laboreinrichtungen (Text)", Lab, 
								"Gefahrstoffe (Text)", Hazard};

			JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
			pane.createDialog(null, "Neue Person anlegen").setVisible(true);
			
			if (pane.getValue() == null) {

				g++;
			}
			
			else {
			try {
				
				String iv = "INSERT INTO Personen (Name,Vorname,Datum,Ifwt,MNaF,Intern,Beschaeftigungsverhaeltnis,"
						+ "Beginn,Ende,Extern,'E-Mail Adresse','Allgemeine Unterweisung', Laboreinrichtungen, Gefahrstoffe) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";	
				
				int value = ((Integer) pane.getValue()).intValue();
				//System.out.println(pane.getValue());

				
				if (value == 0) {
					
					SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

					String dateString = format.format(new Date());

					String nameVar;
					String pnameVar;
					Date dateVar;
					String dateVar2;
					String IfwtVar;
					String MNaFVar;
					String InternVar;
					String EmplVar;
					String StartVar;
					String EndVar;
					String ExternalVar;
					String MailVar;
					String InstrVar;
					String LabVar;
					String HazardVar;
					
					h = 1;
					
					// if and else if querys to disallow wrong database entrys
					if (!Date.getText().isEmpty()) {
						try {
							dateVar = format.parse(Date.getText());
						} catch (Exception e) {
							JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Datum' \n" + "Bitte im Format 'tt.mm.jjjj' eingeben", "Dialog", JOptionPane.ERROR_MESSAGE);
							h = 0;
						}
					}
					
					if (Name.getText().isEmpty() | PName.getText().isEmpty()) {
						String warning = "Bitte Name und Vorname eingeben\n";
						JOptionPane.showMessageDialog(new JFrame(), warning, "Dialog", JOptionPane.ERROR_MESSAGE);
						h = 0;
					}

					else if (!Name.getText().isEmpty() && !PName.getText().isEmpty() && h != 0) {
						try {
							h = 2;									// h = 2 for first while-loop ; h = 1 for 2nd while-loop ; 
																	// h = 0 to exit else if (repeats method)
							
							while (h == 2) {
								h = 1;
								
								try {
									nameVar = Name.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Name'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									pnameVar = PName.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Vorname'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									IfwtVar = Ifwt.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ifwt'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									MNaFVar = MNaF.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'MNaF'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									InternVar = Intern.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Intern'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									EmplVar = Empl.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Beschaeftigungsverhaeltnis'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									StartVar = Start.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Beginn'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									EndVar = End.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ende'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									ExternalVar = External.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Extern'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									MailVar = Mail.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'E-Mail Adresse'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									InstrVar = Instr.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Allgemeine Unterweisung'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									LabVar = Lab.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Laboreinrichtungen'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
								try {
									HazardVar = Hazard.getText();
								} catch (Exception e) {
									JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Gefahrstoffe'", "Dialog", JOptionPane.ERROR_MESSAGE);
									h = 0;
								}
								
							} // End: while (h == 2)

						} catch (Exception e) {
							//String warning = "Bitte richtiges Datenformat eingeben";
							JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
						}
						
						
						
						
						
						// execute database command if there are no wrong entrys
						while (h == 1) {
							h = 0;
							try {
								MNaFVar = MNaF.getText();
								InternVar = Intern.getText();
								EmplVar = Empl.getText();
								StartVar = Start.getText();
								EndVar = End.getText();
								pnameVar = PName.getText();
								dateVar2 = Date.getText();
								IfwtVar = Ifwt.getText();
								nameVar = Name.getText();
								ExternalVar = External.getText();
								MailVar = Mail.getText();
								InstrVar = Instr.getText();
								LabVar = Lab.getText();
								HazardVar = Hazard.getText();
								
								
								
								con = DBConnection.connect();

								con.setAutoCommit(false);

								pstmt = con.prepareStatement(iv);
								pstmt.setString(1, nameVar);
								pstmt.setString(2, pnameVar);
								pstmt.setString(3, dateVar2);
								pstmt.setString(4, IfwtVar);
								pstmt.setString(5, MNaFVar);
								pstmt.setString(6, InternVar);
								pstmt.setString(7, EmplVar);
								pstmt.setString(8, StartVar);
								pstmt.setString(9, EndVar);
								pstmt.setString(10, ExternalVar);
								pstmt.setString(11, MailVar);
								pstmt.setString(12, InstrVar);
								pstmt.setString(13, LabVar);
								pstmt.setString(14, HazardVar);

								
								pstmt.executeUpdate();
								con.commit();
								
								/*System.out.println("Person erstellt \n" + 
										"Name: " + nameVar + 
										", Vorname: " + pnameVar + 
										", Datum: " + dateVar2 + 
										", Ifwt: " + IfwtVar + 
										", MNaF: " + MNaFVar + 
										", Intern: " + InternVar +
										", Beschaeftigungsverhaeltnis: " + EmplVar + 
										", Beginn: " + StartVar + 
										", Ende: " + EndVar + 
										", Extern: " + ExternalVar + 
										", E-Mail Adresse: " + MailVar + 
										", Ende: " + InstrVar + 
										", Extern: " + LabVar + 
										", E-Mail Adresse: " + HazardVar);
								*/
								con.close();
							    pstmt.close();
							    g++;
							} catch (SQLException e) {
					            //System.out.println(e.getMessage());
					            JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
							} catch (Exception e) {
								//String warning = "Bitte richtiges Datenformat eingeben";
								JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
							}
							
						} // End: while (h == 1)
						
					} // End: else if
					

					else {
						//String warning = "Bitte richtiges Datenformat eingeben";
						//JOptionPane.showMessageDialog(new JFrame(), warning, "Dialog", JOptionPane.ERROR_MESSAGE);
					}

				} // End: if (Value == 0)

				
				else {
					g++;
				}
				
				
			} catch (NullPointerException e) {
				Name.setText("");
				MNaF.setText("");
				Intern.setText("");
				Empl.setText("");
				Start.setText("");
				End.setText("");
				External.setText("");
				System.out.println(e);
			}
	
			} // End: first Else
			
		} // End: while (g < 0)
		
		MainFrame.getData();
		
		JOptionPane.showMessageDialog(new JFrame(), "Eintrag erstellt", "Dialog", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	
	// method to delete entry from database
	private static void deleteData() {						// used in ActionListener of JButton btnDel "Eintrag  Löschen"
		
		try {
				// gets currently selected row's ID, compares the Database ID's and deletes matching entry
				String query="delete from Personen where ID='"+ID+"' ";
				
				con = DBConnection.connect();
				pstmt = con.prepareStatement(query);
				con.setAutoCommit(false);
				//System.out.println("Lösche Eintrag...");
				pstmt.execute();
				con.commit();
				pstmt = con.prepareStatement("UPDATE sqlite_sequence SET seq='"+(ID-1)+"' WHERE name='Personen';");
				con.setAutoCommit(false);
				pstmt.execute();
				con.commit();
				
			    pstmt.close();
			    con.close();

				MainFrame.getData();
				
				JOptionPane.showMessageDialog(new JFrame(), "Eintrag gelöscht", "Dialog", JOptionPane.ERROR_MESSAGE);
				

		} catch (SQLException e) {
			//System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	// method to fill textfields with column-entrys of selected row
	public static void fillFields() {							// genutzt in MouseListener von JTable getEditorTable()
		int a = MainFrame.getEditorTable().getSelectedRow();
		String fillName = (String) MainFrame.getEditorTable().getValueAt(a, 1);
		String fillPname = (String) MainFrame.getEditorTable().getValueAt(a, 2);
		String fillDate = (String) MainFrame.getEditorTable().getValueAt(a, 3);
		String fillIfwt = (String) MainFrame.getEditorTable().getValueAt(a, 4);
		String fillMNaF = (String) MainFrame.getEditorTable().getValueAt(a, 5);
		String fillIntern = (String) MainFrame.getEditorTable().getValueAt(a, 6);
		String fillEmpl = (String) MainFrame.getEditorTable().getValueAt(a, 7);
		String fillStart = (String) MainFrame.getEditorTable().getValueAt(a, 8);
		String fillEnd = (String) MainFrame.getEditorTable().getValueAt(a, 9);
		String fillExternal = (String) MainFrame.getEditorTable().getValueAt(a, 10);
		String fillMail = (String) MainFrame.getEditorTable().getValueAt(a, 11);
		String fillInstr = (String) MainFrame.getEditorTable().getModel().getValueAt(a, 12);
		String fillLab = (String) MainFrame.getEditorTable().getModel().getValueAt(a, 13);
		String fillHazard = (String) MainFrame.getEditorTable().getModel().getValueAt(a, 14);
		
		textField.setText(fillName);
		textField_2.setText(fillPname);
		textField_3.setText(fillDate);
		textField_4.setText(fillIfwt);
		textField_5.setText(fillMNaF);
		textField_6.setText(fillIntern);
		textField_7.setText(fillEmpl);
		textField_8.setText(fillStart);
		textField_9.setText(fillEnd);
		textField_10.setText(fillExternal);
		textField_11.setText(fillMail);
		textArea_1.setText(fillInstr);
		textArea_2.setText(fillLab);
		textArea_3.setText(fillHazard);

	}
	
	
	// method to save edited data
	@SuppressWarnings("unused")
	public static void saveData() {						// used in ActionListener of JButton btnSave "Änderungen Speichern"
		String nameVar;
		String pnameVar;
		Date dateVar;
		String dateVar2;
		String IfwtVar;
		String MNaFVar;
		String InternVar;
		String EmplVar;
		String StartVar;
		String EndVar;
		String ExternalVar;
		String MailVar;
		String InstrVar;
		String LabVar;
		String HazardVar;
		
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");		// needed to check if timestamp (Datum) is in correct format
		
		int g = 0;
		
		try {
			g = 2;
			
			// querys to disallow wrong database entrys
			while (g == 2) {
				g = 1;
				try {
					nameVar = textField.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Name'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					pnameVar = textField_2.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Vorname'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				if (!textField_3.getText().isEmpty()) {
					try {
						dateVar = format.parse(textField_3.getText());
					} catch (Exception e) {
						JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Datum' \n " + "Datum bitte im Format 'tt.mm.jjjj' eingeben", "Dialog", JOptionPane.ERROR_MESSAGE);
						g = 0;
					}
				}
				try {
					IfwtVar = textField_4.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ifwt'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					MNaFVar = textField_5.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'MNaF'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					InternVar = textField_6.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Intern'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					EmplVar = textField_7.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Beschäftigungsverhältnis'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					StartVar = textField_8.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Beginn'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					EndVar = textField_9.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ende'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					ExternalVar = textField_10.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Extern'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					MailVar = textField_11.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'E-Mail'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					InstrVar = textArea_1.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Allgemeine Unterweisung'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					LabVar = textArea_2.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Laboreinrichtungen'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					HazardVar = textArea_3.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Gefahrstoffe'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
			}
			
			
			// execute Database update if there are no wrong entrys
			while (g == 1) {
				g = 0;
				String query="Update Personen set Name='" + textField.getText() + "' ,Vorname='" + textField_2.getText() + "'  ,Datum='" + textField_3.getText() + 
						"' ,Ifwt='" + textField_4.getText() + "' ,MNaF='" + textField_5.getText() + "' ,Intern='" + textField_6.getText() + 
						"' ,Beschaeftigungsverhaeltnis='" + textField_7.getText() + "' ,Beginn='" + textField_8.getText() + "' ,Ende='" + textField_9.getText() +
						"' ,Extern='" + textField_10.getText() + "' ,'E-Mail Adresse'='" + textField_11.getText() +  "' ,'Allgemeine Unterweisung'='" + textArea_1.getText() + 
						"' ,'Laboreinrichtungen'='" + textArea_2.getText() +  "' ,'Gefahrstoffe'='" + textArea_3.getText() +  "' ,ID='" + ID + "' where ID='"+ID+"' ";
				
				con = DBConnection.connect();
				pstmt = con.prepareStatement(query);
				con.setAutoCommit(false);
				//System.out.println("Speichert Eintrag...");
				pstmt.execute();
				con.commit();
				
				con.close();
			    pstmt.close();
			    
			    MainFrame.getData();
			    
			    JOptionPane.showMessageDialog(new JFrame(), "Eintrag geändert", "Dialog", JOptionPane.INFORMATION_MESSAGE);

			}
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void btnEditDevicesPressed()
	{
		int row = MainFrame.getEditorTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Kein Eintrag ausgew\u00e4hlt!");
			return;
		}
		int pID = (int)MainFrame.getValueByColName(MainFrame.getEditorTable(), row, "ID");
		deviceAssignement = DeviceAssignement.getInstance(pID);
	}
	
	private void btnEditDangerSubstPressed()
	{
		int row = MainFrame.getEditorTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Kein Eintrag ausgew\u00e4hlt!");
			return;
		}
		int pID = (int)MainFrame.getValueByColName(MainFrame.getEditorTable(), row, "ID");
		dangerSubstAssignement = DangerSubstAssignement.getInstance(pID);
	}
	
}