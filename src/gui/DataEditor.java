
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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import database.DBConnection;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTabbedPane;

public class DataEditor extends JFrame{
	

	private static final long serialVersionUID = 1L;

	private static DataEditor dataEditor = new DataEditor();
	
	private Color frameColor = new Color(32, 32, 32);
	private Color backgroundColor = new Color(25, 25, 25);
	private Color foregroundColor = new Color(255, 255, 255);
	
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel personTab;
	private JPanel devicesTab;
	private JPanel roomsTab;
	private JPanel dangerSubstTab;
	private JPanel elementPanel;
	private static JPanel tablePanel;
	private JPanel textFieldPanel;
	private JPanel textAreaPanel;
	private JLabel lblEditingElements;
	private JButton btnRefresh;
	private JButton btnAdd;
	private JButton btnDelete;
	private static JScrollPane spTable;
	
	private static JTextField tfName;
	private static JTextField tfPname;
	private static JTextField tfDate;
	private static JTextField tfIfwt;
	private static JTextField tfMNaF;
	private static JTextField tfIntern;
	private static JTextField tfBeschverh;
	private static JTextField tfStart;
	private static JTextField tfEnde;
	private static JTextField tfExtern;
	private static JTextField tfEmail;
	private static JTextArea taInstructions;
	private static JTextArea taLab;
	private static JTextArea taHazard;
	private static JTextField tfDeviceName;
	private static JTextField tfDeviceDescription;
	private static JTextField tfDeviceRoom;
	private static JTextField tfRoomName;
	private static JTextField tfRoomDescription;
	private static JTextField tfHazardousSubstance;
	private JScrollPane spInstr;
	private JScrollPane spLab;
	private JScrollPane spHazard;
	private static int ID = 0;
	private static String name;
	private int confirmed = 0;
	private static DeviceAssignement deviceAssignement;
	private static DangerSubstAssignement dangerSubstAssignement;
	private static RoomAssignement roomAssignement;
	
	private static Connection con = null;
	static PreparedStatement pstmt = null;
	private JLabel lblDescription;
	private JLabel lblRoom;
	
	
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

		//contentPane.setLayout(new MigLayout("", "[grow]", "[65.0][250.0,grow][120.0,grow][100.0,grow]"));
		contentPane.setLayout(new MigLayout("", "[grow]","[grow]"));
		
		//Tabbed Pane
		tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(backgroundColor);
		tabbedPane.setForeground(foregroundColor);
		contentPane.add(tabbedPane, "cell 0 0, grow");
		
		//Personen Tab
		personTab = new JPanel();
		personTab.setBackground(backgroundColor);
		personTab.setForeground(foregroundColor);
		personTab.setLayout(new MigLayout("", "[grow]", "[65.0][250.0,grow][120.0,grow][100.0,grow]"));
		tabbedPane.addTab("Personen", personTab);

		
		// Panel for the editing elements
		elementPanel = new JPanel();
		elementPanel.setBackground(backgroundColor);
		elementPanel.setForeground(foregroundColor);

		personTab.add(elementPanel, "cell 0 0,grow");

		elementPanel.setLayout(new MigLayout("", "[][]", "[]10[]"));
		
		lblEditingElements = new JLabel("Bearbeitungselemente");
		lblEditingElements.setFont(new Font("Dialog", Font.BOLD, 14));
		lblEditingElements.setForeground(foregroundColor);
		elementPanel.add(lblEditingElements, "cell 0 0");
		
		//refresh button
		btnRefresh = new JButton("Aktualisieren");
		ImageIcon refreshIcon = new ImageIcon(DataEditor.class.getResource("/images/refresh.png"));
		btnRefresh.setIcon(refreshIcon);
		elementPanel.add(btnRefresh, "cell 0 1");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getPersonData();
			}
		});
		
		// add Data Button
		btnAdd = new JButton("Neuer Eintrag");
		ImageIcon newIcon = new ImageIcon(DataEditor.class.getResource("/images/new.png"));
		btnAdd.setIcon(newIcon);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newPersonData();
			}
		});
		elementPanel.add(btnAdd, "gapleft 10, cell 1 1");
		
		// delete data button
		btnDelete = new JButton("Eintrag löschen");
		ImageIcon deleteIcon = new ImageIcon(DataEditor.class.getResource("/images/delete.png"));
		btnDelete.setIcon(deleteIcon);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletePersonData();
			}
		});
		elementPanel.add(btnDelete, "gapleft 30, cell 2 1");
		
		// Panel for the table that resembles the database
		tablePanel = new JPanel();
		tablePanel.setBackground(backgroundColor);
		tablePanel.setForeground(foregroundColor);

		personTab.add(tablePanel, "cell 0 1,grow");

		tablePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		spTable = new JScrollPane();
		tablePanel.add(spTable, "cell 0 0,grow");
		
		// cloned table for edit window
		spTable.setViewportView(MainFrame.getEditorTable());
	
		
		// Panel for the update button
		textFieldPanel = new JPanel();
		textFieldPanel.setBackground(backgroundColor);
		textFieldPanel.setForeground(foregroundColor);

		personTab.add(textFieldPanel, "cell 0 2,grow");

		textFieldPanel.setLayout(new MigLayout("", "[right][220][right][220][120]", "[]10[]10[]10[]0[]0[]"));
		
		// name textfield
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblName.setForeground(foregroundColor);
		textFieldPanel.add(lblName, "cell 0 0");
		tfName = new JTextField();
		textFieldPanel.add(tfName, "width 30%, cell 1 0");
		
		// prename textfield
		JLabel lblPname = new JLabel("Vorname:");
		lblPname.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPname.setForeground(foregroundColor);
		textFieldPanel.add(lblPname, "cell 0 1");
		tfPname = new JTextField();
		textFieldPanel.add(tfPname, "width 30%, cell 1 1");
		
		// date textfield
		JLabel lblDate = new JLabel("Datum:");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDate.setForeground(foregroundColor);
		textFieldPanel.add(lblDate, "cell 0 2");
		tfDate = new JTextField();
		textFieldPanel.add(tfDate, "width 30%, cell 1 2");
		
		// Ifwt Textfield
		JLabel lblIfwt = new JLabel("Ifwt:");
		lblIfwt.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblIfwt.setForeground(foregroundColor);
		textFieldPanel.add(lblIfwt, "cell 0 3");
		tfIfwt = new JTextField();
		textFieldPanel.add(tfIfwt, "width 30%, cell 1 3");
		
		// MNaF textfield
		JLabel lblMNaF = new JLabel("MNaF:");
		lblMNaF.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblMNaF.setForeground(foregroundColor);
		textFieldPanel.add(lblMNaF, "cell 0 4");
		tfMNaF = new JTextField();
		textFieldPanel.add(tfMNaF, "width 30%, cell 1 4");
		
		// intern textfield
		JLabel lblIntern = new JLabel("Intern:");
		lblIntern.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblIntern.setForeground(foregroundColor);
		textFieldPanel.add(lblIntern, "cell 0 5");
		tfIntern = new JTextField();
		textFieldPanel.add(tfIntern, "width 30%, cell 1 5");
		
		// employment relationship textfield
		JLabel lblEmpl = new JLabel("   Beschäftigungsverhältnis:");
		lblEmpl.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEmpl.setForeground(foregroundColor);
		textFieldPanel.add(lblEmpl, "cell 2 0");
		tfBeschverh = new JTextField();
		textFieldPanel.add(tfBeschverh, "width 30%, cell 3 0");
		
		// beginning textfield
		JLabel lblStart = new JLabel("   Beginn:");
		lblStart.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblStart.setForeground(foregroundColor);
		textFieldPanel.add(lblStart, "cell 2 1");
		tfStart = new JTextField();
		textFieldPanel.add(tfStart, "width 30%, cell 3 1");
		
		// end textfield
		JLabel lblEnd = new JLabel("   Ende:");
		lblEnd.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEnd.setForeground(foregroundColor);
		textFieldPanel.add(lblEnd, "cell 2 2");
		tfEnde = new JTextField();
		textFieldPanel.add(tfEnde, "width 30%, cell 3 2");
		
		// external textfield
		JLabel lblExternal = new JLabel("   Extern:");
		lblExternal.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblExternal.setForeground(foregroundColor);
		textFieldPanel.add(lblExternal, "cell 2 3");
		tfExtern = new JTextField();
		textFieldPanel.add(tfExtern, "width 30%, cell 3 3");
		
		// e-mail textfield
		JLabel lblMail = new JLabel("   E-Mail:");
		lblMail.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblMail.setForeground(foregroundColor);
		textFieldPanel.add(lblMail, "cell 2 4");
		tfEmail = new JTextField();
		textFieldPanel.add(tfEmail, "width 30%, cell 3 4");
		
		// savebutton
		String twoLines = "Änderungen \n Speichern";
		JButton btnSave = new JButton("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");
		ImageIcon saveIcon = new ImageIcon(DataEditor.class.getResource("/images/save.png"));
		btnSave.setIcon(saveIcon);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfName.getText().isEmpty() | tfPname.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(), "Bitte Name und Vorname eintragen", "Dialog", JOptionPane.ERROR_MESSAGE);
				}
				else {
					savePersonData();
				}
			}
		});
		textFieldPanel.add(btnSave, "width 25%, gapleft 50, cell 4 4");
		
		//edit devices button
		JButton btnEditDevices = new JButton("Geräte bearbeiten");
		btnEditDevices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEditDevicesPressed();
				if(deviceAssignement != null) {
					deviceAssignement.setVisible(true);
				}
			}
		});
		textFieldPanel.add(btnEditDevices, "width 25%, gapleft 50, cell 4 2");
		
		//edit dangerous substances button
		JButton btnEditDangerSubst = new JButton("Gefahrstoffe bearbeiten");
		btnEditDangerSubst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEditDangerSubstPressed();
				if(dangerSubstAssignement != null) {
					dangerSubstAssignement.setVisible(true);
				}
			}
		});
		textFieldPanel.add(btnEditDangerSubst, "cell 4 1,width 25%,gapx 50");
		
		
		textAreaPanel = new JPanel();
		textAreaPanel.setBackground(backgroundColor);
		textAreaPanel.setForeground(foregroundColor);

		personTab.add(textAreaPanel, "cell 0 3,grow");
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
		taInstructions = new JTextArea();
		taInstructions.setLineWrap(true);
		taInstructions.setWrapStyleWord(true);
		spInstr.setViewportView(taInstructions);
				
		// Laboreinrichtungen textfield
		JLabel lblLab = new JLabel("   Laboreinrichtungen (Kommentar):");
		lblLab.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblLab.setForeground(foregroundColor);
		spLab = new JScrollPane(
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textAreaPanel.add(lblLab, "cell 1 0");
		textAreaPanel.add(spLab, "grow, cell 1 1 1 2");
		taLab = new JTextArea();
		taLab.setLineWrap(true);
		taLab.setWrapStyleWord(true);
		spLab.setViewportView(taLab);
		
		// Gefahrstoffe textfield
		JLabel lblHazard = new JLabel("   Gefahrstoffe (Kommentar):");
		lblHazard.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblHazard.setForeground(foregroundColor);
		spHazard = new JScrollPane(
	            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textAreaPanel.add(lblHazard, "cell 2 0");
		textAreaPanel.add(spHazard, "grow, cell 2 1 1 2");
		taHazard = new JTextArea();
		taHazard.setLineWrap(true);
		taHazard.setWrapStyleWord(true);
		spHazard.setViewportView(taHazard);
		
		
		// Mouselistener adds clicked row from table in Textfields and saves ID of clicked row for Deletion
		MainFrame.getEditorTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = MainFrame.getEditorTable().getSelectedRow();
				ID = Integer.parseInt(MainFrame.getEditorTable().getValueAt(i, 0).toString());

				fillPersonFields();
			}
		});
		
		
		//###Geraete Tab###
		devicesTab = new JPanel();
		devicesTab.setBackground(backgroundColor);
		devicesTab.setForeground(foregroundColor);
		devicesTab.setLayout(new MigLayout("", "[grow]", "[65.0][370.0,grow][100.0,grow]"));
		tabbedPane.addTab("Ger\u00e4te", devicesTab);
		
		// Panel for the editing elements
		elementPanel = new JPanel();
		elementPanel.setBackground(backgroundColor);
		elementPanel.setForeground(foregroundColor);
		devicesTab.add(elementPanel, "cell 0 0,grow");
		elementPanel.setLayout(new MigLayout("", "[][]", "[]10[]"));
		
		lblEditingElements = new JLabel("Bearbeitungselemente");
		lblEditingElements.setFont(new Font("Dialog", Font.BOLD, 14));
		lblEditingElements.setForeground(foregroundColor);
		elementPanel.add(lblEditingElements, "cell 0 0");
		
		//refresh button
		btnRefresh = new JButton("Aktualisieren");
		refreshIcon = new ImageIcon(DataEditor.class.getResource("/images/refresh.png"));
		btnRefresh.setIcon(refreshIcon);
		elementPanel.add(btnRefresh, "cell 0 1");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getDeviceData();
			}
		});
		
		// add Data Button
		btnAdd = new JButton("Neuer Eintrag");
		newIcon = new ImageIcon(DataEditor.class.getResource("/images/new.png"));
		btnAdd.setIcon(newIcon);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newDeviceData();
			}
		});
		elementPanel.add(btnAdd, "gapleft 10, cell 1 1");
		
		// delete data button
		btnDelete = new JButton("Eintrag löschen");
		deleteIcon = new ImageIcon(DataEditor.class.getResource("/images/delete.png"));
		btnDelete.setIcon(deleteIcon);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteDeviceData();
			}
		});
		elementPanel.add(btnDelete, "gapleft 30, cell 2 1");
		
		// Panel for the table that resembles the database
		tablePanel = new JPanel();
		tablePanel.setBackground(backgroundColor);
		tablePanel.setForeground(foregroundColor);
		devicesTab.add(tablePanel, "cell 0 1,grow");
		tablePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		spTable = new JScrollPane();
		tablePanel.add(spTable, "cell 0 0,grow");
		
		// cloned table for edit window
		spTable.setViewportView(MainFrame.getDeviceEditorTable());
	
		
		// Panel for the update button
		textFieldPanel = new JPanel();
		textFieldPanel.setBackground(backgroundColor);
		textFieldPanel.setForeground(foregroundColor);
		devicesTab.add(textFieldPanel, "cell 0 2,grow");
		textFieldPanel.setLayout(new MigLayout("", "[right][220][right][220][120]", "[]10[]"));
		
		// name textfield
		lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblName.setForeground(foregroundColor);
		textFieldPanel.add(lblName, "cell 0 0");
		tfDeviceName = new JTextField();
		textFieldPanel.add(tfDeviceName, "width 30%, cell 1 0");
		
		// description textfield
		lblDescription = new JLabel("Beschreibung:");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDescription.setForeground(foregroundColor);
		textFieldPanel.add(lblDescription, "gapleft 20, cell 2 0");
		tfDeviceDescription = new JTextField();
		textFieldPanel.add(tfDeviceDescription, "width 30%, cell 3 0");
		
		// room textfield
		lblRoom = new JLabel("Raum:");
		lblRoom.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblRoom.setForeground(foregroundColor);
		textFieldPanel.add(lblRoom, "cell 0 1");
		tfDeviceRoom = new JTextField();
		textFieldPanel.add(tfDeviceRoom, "width 30%, cell 1 1");
		
		
		//edit rooms button
		JButton btnEditRooms = new JButton("R\u00e4ume bearbeiten");
		btnEditRooms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnEditRoomsPressed();
				if(roomAssignement != null) {
					roomAssignement.setVisible(true);
				}
			}
		});
		textFieldPanel.add(btnEditRooms, "width 25%, gapleft 50, cell 4 1");
		
		
		// savebutton
		twoLines = "Änderungen \n Speichern";
		btnSave = new JButton("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");
		saveIcon = new ImageIcon(DataEditor.class.getResource("/images/save.png"));
		btnSave.setIcon(saveIcon);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfDeviceName.getText().isEmpty() | tfDeviceRoom.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(), "Bitte Ger\u00e4tenamen und Ger\u00e4teraum eintragen", "Dialog", JOptionPane.ERROR_MESSAGE);
				}
				else {
					saveDeviceData();
				}
			}
		});
		textFieldPanel.add(btnSave, "width 25%, gapleft 50, cell 4 2");
		
		
		// Mouselistener adds clicked row from table in Textfields and saves ID of clicked row for Deletion
		MainFrame.getDeviceEditorTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = MainFrame.getDeviceEditorTable().getSelectedRow();
				ID = Integer.parseInt(MainFrame.getDeviceEditorTable().getValueAt(i, 0).toString());

				fillDeviceData();
			}
		});
		
		
		//###Raeume Tab###
		roomsTab = new JPanel();
		roomsTab.setBackground(backgroundColor);
		roomsTab.setForeground(foregroundColor);
		roomsTab.setLayout(new MigLayout("", "[grow]", "[65.0][370.0,grow][100.0,grow]"));
		tabbedPane.addTab("R\u00e4ume", roomsTab);
		
		// Panel for the editing elements
		elementPanel = new JPanel();
		elementPanel.setBackground(backgroundColor);
		elementPanel.setForeground(foregroundColor);
		roomsTab.add(elementPanel, "cell 0 0,grow");
		elementPanel.setLayout(new MigLayout("", "[][]", "[]10[]"));
		
		lblEditingElements = new JLabel("Bearbeitungselemente");
		lblEditingElements.setFont(new Font("Dialog", Font.BOLD, 14));
		lblEditingElements.setForeground(foregroundColor);
		elementPanel.add(lblEditingElements, "cell 0 0");
		
		//refresh button
		btnRefresh = new JButton("Aktualisieren");
		refreshIcon = new ImageIcon(DataEditor.class.getResource("/images/refresh.png"));
		btnRefresh.setIcon(refreshIcon);
		elementPanel.add(btnRefresh, "cell 0 1");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getRoomData();
			}
		});
		
		// add Data Button
		btnAdd = new JButton("Neuer Eintrag");
		newIcon = new ImageIcon(DataEditor.class.getResource("/images/new.png"));
		btnAdd.setIcon(newIcon);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newRoomData();
			}
		});
		elementPanel.add(btnAdd, "gapleft 10, cell 1 1");
		
		// delete data button
		btnDelete = new JButton("Eintrag löschen");
		deleteIcon = new ImageIcon(DataEditor.class.getResource("/images/delete.png"));
		btnDelete.setIcon(deleteIcon);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteRoomData();
			}
		});
		elementPanel.add(btnDelete, "gapleft 30, cell 2 1");
		
		// Panel for the table that resembles the database
		tablePanel = new JPanel();
		tablePanel.setBackground(backgroundColor);
		tablePanel.setForeground(foregroundColor);
		roomsTab.add(tablePanel, "cell 0 1,grow");
		tablePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		spTable = new JScrollPane();
		tablePanel.add(spTable, "cell 0 0,grow");
		
		// cloned table for edit window
		spTable.setViewportView(MainFrame.getRoomsEditorTable());
	
		
		// Panel for the update button
		textFieldPanel = new JPanel();
		textFieldPanel.setBackground(backgroundColor);
		textFieldPanel.setForeground(foregroundColor);
		roomsTab.add(textFieldPanel, "cell 0 2,grow");
		textFieldPanel.setLayout(new MigLayout("", "[right][220][right][220][120]", "[]10[]"));
		
		// name textfield
		lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblName.setForeground(foregroundColor);
		textFieldPanel.add(lblName, "cell 0 0");
		tfRoomName = new JTextField();
		textFieldPanel.add(tfRoomName, "width 30%, cell 1 0");
		
		// description textfield
		lblDescription = new JLabel("Beschreibung:");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDescription.setForeground(foregroundColor);
		textFieldPanel.add(lblDescription, "gapleft 20, cell 2 0");
		tfRoomDescription = new JTextField();
		textFieldPanel.add(tfRoomDescription, "width 30%, cell 3 0");
		
		// savebutton
		twoLines = "Änderungen \n Speichern";
		btnSave = new JButton("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");
		saveIcon = new ImageIcon(DataEditor.class.getResource("/images/save.png"));
		btnSave.setIcon(saveIcon);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfRoomName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(), "Bitte Raumnamen eintragen", "Dialog", JOptionPane.ERROR_MESSAGE);
				}
				else {
					saveRoomData();
				}
			}
		});
		textFieldPanel.add(btnSave, "width 25%, gapleft 50, cell 4 1");
		
		
		// Mouselistener adds clicked row from table in Textfields and saves ID of clicked row for Deletion
		MainFrame.getRoomsEditorTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = MainFrame.getRoomsEditorTable().getSelectedRow();
				name = MainFrame.getRoomsEditorTable().getValueAt(i, 0).toString();

				fillRoomData();
			}
		});
		
		
		//###Gefahrstoffe Tab###
		dangerSubstTab = new JPanel();
		dangerSubstTab.setBackground(backgroundColor);
		dangerSubstTab.setForeground(foregroundColor);
		dangerSubstTab.setLayout(new MigLayout("", "[grow]", "[65.0][370.0,grow][100.0,grow]"));
		tabbedPane.addTab("Gefahrstoffe", dangerSubstTab);
		
		// Panel for the editing elements
		elementPanel = new JPanel();
		elementPanel.setBackground(backgroundColor);
		elementPanel.setForeground(foregroundColor);
		dangerSubstTab.add(elementPanel, "cell 0 0,grow");
		elementPanel.setLayout(new MigLayout("", "[][]", "[]10[]"));
		
		lblEditingElements = new JLabel("Bearbeitungselemente");
		lblEditingElements.setFont(new Font("Dialog", Font.BOLD, 14));
		lblEditingElements.setForeground(foregroundColor);
		elementPanel.add(lblEditingElements, "cell 0 0");
		
		//refresh button
		btnRefresh = new JButton("Aktualisieren");
		refreshIcon = new ImageIcon(DataEditor.class.getResource("/images/refresh.png"));
		btnRefresh.setIcon(refreshIcon);
		elementPanel.add(btnRefresh, "cell 0 1");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainFrame.getDangerSubstData();
			}
		});
		
		// add Data Button
		btnAdd = new JButton("Neuer Eintrag");
		newIcon = new ImageIcon(DataEditor.class.getResource("/images/new.png"));
		btnAdd.setIcon(newIcon);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newHazardousSubstancesData();
			}
		});
		elementPanel.add(btnAdd, "gapleft 10, cell 1 1");
		
		// delete data button
		btnDelete = new JButton("Eintrag löschen");
		deleteIcon = new ImageIcon(DataEditor.class.getResource("/images/delete.png"));
		btnDelete.setIcon(deleteIcon);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteHazardousSubstancesData();
			}
		});
		elementPanel.add(btnDelete, "gapleft 30, cell 2 1");
		
		// Panel for the table that resembles the database
		tablePanel = new JPanel();
		tablePanel.setBackground(backgroundColor);
		tablePanel.setForeground(foregroundColor);
		dangerSubstTab.add(tablePanel, "cell 0 1,grow");
		tablePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		spTable = new JScrollPane();
		tablePanel.add(spTable, "cell 0 0,grow");
		
		// cloned table for edit window
		spTable.setViewportView(MainFrame.getDangerSubstEditorTable());
	
		
		// Panel for the update button
		textFieldPanel = new JPanel();
		textFieldPanel.setBackground(backgroundColor);
		textFieldPanel.setForeground(foregroundColor);
		dangerSubstTab.add(textFieldPanel, "cell 0 2,grow");
		textFieldPanel.setLayout(new MigLayout("", "[right][220][right][220][120]", "[]10[]"));
		
		// name textfield
		lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblName.setForeground(foregroundColor);
		textFieldPanel.add(lblName, "cell 0 0");
		tfHazardousSubstance = new JTextField();
		textFieldPanel.add(tfHazardousSubstance, "width 30%, cell 1 0");
		
		// savebutton
		twoLines = "Änderungen \n Speichern";
		btnSave = new JButton("<html>" + twoLines.replaceAll("\\n", "<br>") + "</html>");
		saveIcon = new ImageIcon(DataEditor.class.getResource("/images/save.png"));
		btnSave.setIcon(saveIcon);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfHazardousSubstance.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(), "Bitte Name und Vorname eintragen", "Dialog", JOptionPane.ERROR_MESSAGE);
				}
				else {
					saveHazardousSubstancesData();
				}
			}
		});
		textFieldPanel.add(btnSave, "width 25%, gapleft 50, cell 4 1");
		
		
		// Mouselistener adds clicked row from table in Textfields and saves ID of clicked row for Deletion
		MainFrame.getDangerSubstEditorTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = MainFrame.getDangerSubstEditorTable().getSelectedRow();
				name = MainFrame.getDangerSubstEditorTable().getValueAt(i, 0).toString();

				fillHazardousSubstancesData();
			}
		});
		
		setVisible(true);
	}
	
	
	// method to add new data into database
	@SuppressWarnings("unused")
	private void newPersonData() {									// used in Actionlistener of JButton btnAdd "Neuer Eintrag"

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
			if (pane.getValue() != null) {
				confirmed = 1;
			}
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
				//MNaF.setText("");
				//Intern.setText("");
				Empl.setText("");
				Start.setText("");
				End.setText("");
				//External.setText("");
				System.out.println(e);
			}
	
			} // End: first Else
			
		} // End: while (g < 0)
		
		MainFrame.getPersonData();
		if (confirmed == 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Eintrag erstellt");
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Bearbeitung abgebrochen");
		}
		confirmed = 0;
	}
	
	
	private void newDeviceData() {
		JTextField deviceName = new JTextField();
		JTextField deviceDescription = new JTextField();
		JTextField deviceRoom = new JTextField();
		int g = -1;
		int h = 1;
		
		while (g < 0) { // while loop does exit when window is closed or new entry is confirmed (g++;)
			//initialize fields for user entrys
			Object[] message = {"Ger\u00e4tename (Text)", deviceName,
								"Ger\u00e4tebeschreibung (Text)", deviceDescription,
								"Ger\u00e4teraum (Text)", deviceRoom};
			
			JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE);
			pane.createDialog(null, "Neues Ger\u00e4t anlegen").setVisible(true);
			if (pane.getValue() != null) {
				confirmed = 1;
			}
			if (pane.getValue() == null) {
				g++;
			} else {
				try {
					String insertDevice = "INSERT INTO Ger\u00e4te (Name,Beschreibung,Raum) "
										+ "VALUES (?,?,?);";
					
					int value = ((Integer) pane.getValue()).intValue();
					//System.out.println(pane.getValue());
					
					if (value == 0) {
						String deviceNameVar;
						String deviceDescriptionVar;
						String deviceRoomVar;
						
						h = 1;
						
						if (deviceName.getText().isEmpty() | deviceRoom.getText().isEmpty()) {
							String warning = "Bitte Ger\u00e4tenamen und Ger\u00e4teraum eingeben\n";
							JOptionPane.showMessageDialog(new JFrame(), warning, "Dialog", JOptionPane.ERROR_MESSAGE);
							h = 0;
						}
						else if (!deviceName.getText().isEmpty() && !deviceRoom.getText().isEmpty() && h != 0) {
							try {
								h = 2;	//h = 2 for first while-loop ; h = 1 for 2nd while-loop ;
										//h = 0 to exit else if (repeats method)
								
								while (h == 2) {
									h = 1;
									
									try {
										deviceNameVar = deviceName.getText();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ger\u00e4tename'", "Dialog", JOptionPane.ERROR_MESSAGE);
										h = 0;
									}
									try {
										deviceDescriptionVar = deviceDescription.getText();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ger\u00e4tebeschreibung'", "Dialog", JOptionPane.ERROR_MESSAGE);
										h = 0;
									}
									try {
										deviceRoomVar = deviceRoom.getText();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Raum'", "Dialog", JOptionPane.ERROR_MESSAGE);
										h = 0;
									}
								} //End: while (h == 2)
							} catch (Exception e) {
								JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
							}
							
							//Execute database command if there are no wrong entries
							while (h == 1) {
								h = 0;
								try {
									deviceNameVar = deviceName.getText();
									deviceDescriptionVar = deviceDescription.getText();
									deviceRoomVar = deviceRoom.getText();
									
									con = DBConnection.connect();
									con.setAutoCommit(false);
									
									PreparedStatement pstmt = con.prepareStatement(insertDevice);
									pstmt.setString(1, deviceNameVar);
									pstmt.setString(2, deviceDescriptionVar);
									pstmt.setString(3, deviceRoomVar);
									
									pstmt.executeUpdate();
									con.commit();
									
									con.close();
									pstmt.close();
									g++;
								} catch (SQLException e) {
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
					deviceName.setText("");
					deviceDescription.setText("");
					deviceRoom.setText("");
					System.out.print(e);
				}
			} //End: first else
		} //End: while (g < 0)
		MainFrame.getDeviceData();
		if (confirmed == 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Eintrag erstellt");
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Bearbeitung abgebrochen");
		}
		confirmed = 0;
	}
	
	
	private void newRoomData() {
		JTextField roomName = new JTextField();
		JTextField roomDescription = new JTextField();
		int g = -1;
		int h = 1;
		
		while (g < 0) { // while loop does exit when window is closed or new entry is confirmed (g++;)
			//initialize fields for user entries
			Object[] message = {"Raumname (Text)", roomName,
								"Raumbeschreibung (Text)", roomDescription};
			
			JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE);
			pane.createDialog(null, "Neuen Raum hinzuf\u00fcgen").setVisible(true);
			if (pane.getValue() != null) {
				confirmed = 1;
			}
			if (pane.getValue() == null) {
				g++;
			} else {
				try {
					String insertDevice = "INSERT INTO R\u00e4ume (Name,Beschreibung) "
										+ "VALUES (?,?);";
					
					int value = ((Integer) pane.getValue()).intValue();
					//System.out.println(pane.getValue());
					
					if (value == 0) {
						String roomNameVar;
						String roomDescriptionVar;
						
						h = 1;
						
						if (roomName.getText().isEmpty()) {
							String warning = "Bitte Raumnamen eingeben\n";
							JOptionPane.showMessageDialog(new JFrame(), warning, "Dialog", JOptionPane.ERROR_MESSAGE);
							h = 0;
						}
						else if (!roomName.getText().isEmpty() && h != 0) {
							try {
								h = 2;	//h = 2 for first while-loop ; h = 1 for 2nd while-loop ;
										//h = 0 to exit else if (repeats method)
								
								while (h == 2) {
									h = 1;
									
									try {
										roomNameVar = roomName.getText();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ger\u00e4tename'", "Dialog", JOptionPane.ERROR_MESSAGE);
										h = 0;
									}
									try {
										roomDescriptionVar = roomDescription.getText();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ger\u00e4tebeschreibung'", "Dialog", JOptionPane.ERROR_MESSAGE);
										h = 0;
									}
								} //End: while (h == 2)
							} catch (Exception e) {
								JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
							}
							
							//Execute database command if there are no wrong entries
							while (h == 1) {
								h = 0;
								try {
									roomNameVar = roomName.getText();
									roomDescriptionVar = roomDescription.getText();
									
									con = DBConnection.connect();
									con.setAutoCommit(false);
									
									PreparedStatement pstmt = con.prepareStatement(insertDevice);
									pstmt.setString(1, roomNameVar);
									pstmt.setString(2, roomDescriptionVar);
									
									pstmt.executeUpdate();
									con.commit();
									
									con.close();
									pstmt.close();
									g++;
								} catch (SQLException e) {
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
					roomName.setText("");
					roomDescription.setText("");
					System.out.print(e);
				}
			} //End: first else
		} //End: while (g < 0)
		MainFrame.getRoomData();
		if (confirmed == 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Eintrag erstellt");
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Bearbeitung abgebrochen");
		}
		confirmed = 0;
	}
	
	
	private void newHazardousSubstancesData() {
		JTextField hazardousSubstance = new JTextField();
		int g = -1;
		int h = 1;
		
		while (g < 0) { // while loop does exit when window is closed or new entry is confirmed (g++;)
			//initialize fields for user entries
			Object[] message = {"Gefahrstoff (Text)", hazardousSubstance};
			
			JOptionPane pane = new JOptionPane(message, JOptionPane.PLAIN_MESSAGE);
			pane.createDialog(null, "Neuen Gefahrstoff hinzuf\u00fcgen").setVisible(true);
			if (pane.getValue() != null) {
				confirmed = 1;
			}
			if (pane.getValue() == null) {
				g++;
			} else {
				try {
					String insertDevice = "INSERT INTO Gefahrstoffe (Name) "
										+ "VALUES (?);";
					
					int value = ((Integer) pane.getValue()).intValue();
					//System.out.println(pane.getValue());
					
					if (value == 0) {
						String hazardousSubstanceVar;
						
						h = 1;
						
						if (hazardousSubstance.getText().isEmpty()) {
							String warning = "Bitte Gefahrstoff eingeben\n";
							JOptionPane.showMessageDialog(new JFrame(), warning, "Dialog", JOptionPane.ERROR_MESSAGE);
							h = 0;
						}
						else if (!hazardousSubstance.getText().isEmpty() && h != 0) {
							try {
								h = 2;	//h = 2 for first while-loop ; h = 1 for 2nd while-loop ;
										//h = 0 to exit else if (repeats method)
								
								while (h == 2) {
									h = 1;
									
									try {
										hazardousSubstanceVar = hazardousSubstance.getText();
									} catch (Exception e) {
										JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Gefahrstoff'", "Dialog", JOptionPane.ERROR_MESSAGE);
										h = 0;
									}
								} //End: while (h == 2)
							} catch (Exception e) {
								JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
							}
							
							//Execute database command if there are no wrong entries
							while (h == 1) {
								h = 0;
								try {
									hazardousSubstanceVar = hazardousSubstance.getText();
									
									con = DBConnection.connect();
									con.setAutoCommit(false);
									
									PreparedStatement pstmt = con.prepareStatement(insertDevice);
									pstmt.setString(1, hazardousSubstanceVar);
									
									pstmt.executeUpdate();
									con.commit();
									
									con.close();
									pstmt.close();
									g++;
								} catch (SQLException e) {
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
					hazardousSubstance.setText("");
					System.out.print(e);
				}
			} //End: first else
		} //End: while (g < 0)
		MainFrame.getDangerSubstData();
		if (confirmed == 1) {
			JOptionPane.showMessageDialog(new JFrame(), "Eintrag erstellt");
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Bearbeitung abgebrochen");
		}
		confirmed = 0;
	}
	
	
	// method to delete entry from database
	private static void deletePersonData() {						// used in ActionListener of JButton btnDel "Eintrag  Löschen"
		
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
				pstmt.executeUpdate();
				con.commit();
				
			    pstmt.close();
			    con.close();

				MainFrame.getPersonData();
				
				JOptionPane.showMessageDialog(new JFrame(), "Eintrag gelöscht", "Dialog", JOptionPane.ERROR_MESSAGE);
				

		} catch (SQLException e) {
			//System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	private static void deleteDeviceData() {
		try {
			// gets currently selected row's ID, compares the Database ID's and deletes matching entry
			String query="DELETE FROM Ger\u00e4te WHERE Ger\u00e4teID='"+ID+"' ";
			
			con = DBConnection.connect();
			pstmt = con.prepareStatement(query);
			con.setAutoCommit(false);
			//System.out.println("Lösche Eintrag...");
			pstmt.execute();
			con.commit();
			pstmt = con.prepareStatement("UPDATE sqlite_sequence SET seq='"+(ID-1)+"' WHERE name='Ger\u00e4te';");
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			
		    pstmt.close();
		    con.close();

			MainFrame.getDeviceData();
			
			JOptionPane.showMessageDialog(new JFrame(), "Eintrag gelöscht", "Dialog", JOptionPane.ERROR_MESSAGE);
			

		} catch (SQLException e) {
			//System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
	}
		
	
	private static void deleteRoomData() {
		try {
			// gets currently selected row's ID, compares the Database ID's and deletes matching entry
			String query="DELETE FROM R\u00e4ume WHERE Name='"+name+"' ";
			
			con = DBConnection.connect();
			pstmt = con.prepareStatement(query);
			con.setAutoCommit(false);
			//System.out.println("Lösche Eintrag...");
			pstmt.execute();
			con.commit();
			//pstmt = con.prepareStatement("UPDATE sqlite_sequence SET seq='"+(ID-1)+"' WHERE name='R\u00e4ume';");
			//con.setAutoCommit(false);
			//pstmt.executeUpdate();
			//con.commit();
			
		    pstmt.close();
		    con.close();

			MainFrame.getRoomData();
			
			JOptionPane.showMessageDialog(new JFrame(), "Eintrag gelöscht", "Dialog", JOptionPane.ERROR_MESSAGE);
			

		} catch (SQLException e) {
			//System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void deleteHazardousSubstancesData() {
		try {
			// gets currently selected row's ID, compares the Database ID's and deletes matching entry
			String query="DELETE FROM Gefahrstoffe WHERE Name='"+name+"' ";
			
			con = DBConnection.connect();
			pstmt = con.prepareStatement(query);
			con.setAutoCommit(false);
			
			pstmt.execute();
			con.commit();
			
		    pstmt.close();
		    con.close();

			MainFrame.getDangerSubstData();
			
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
	public static void fillPersonFields() {							// genutzt in MouseListener von JTable getEditorTable()
		int selRow = MainFrame.getEditorTable().getSelectedRow();
		String fillName = (String) MainFrame.getEditorTable().getValueAt(selRow, 1);
		String fillPname = (String) MainFrame.getEditorTable().getValueAt(selRow, 2);
		String fillDate = (String) MainFrame.getEditorTable().getValueAt(selRow, 3);
		String fillIfwt = (String) MainFrame.getEditorTable().getValueAt(selRow, 4);
		String fillMNaF = (String) MainFrame.getEditorTable().getValueAt(selRow, 5);
		String fillIntern = (String) MainFrame.getEditorTable().getValueAt(selRow, 6);
		String fillEmpl = (String) MainFrame.getEditorTable().getValueAt(selRow, 7);
		String fillStart = (String) MainFrame.getEditorTable().getValueAt(selRow, 8);
		String fillEnd = (String) MainFrame.getEditorTable().getValueAt(selRow, 9);
		String fillExternal = (String) MainFrame.getEditorTable().getValueAt(selRow, 10);
		String fillMail = (String) MainFrame.getEditorTable().getValueAt(selRow, 11);
		String fillInstr = (String) MainFrame.getEditorTable().getModel().getValueAt(selRow, 12);
		String fillLab = (String) MainFrame.getEditorTable().getModel().getValueAt(selRow, 13);
		String fillHazard = (String) MainFrame.getEditorTable().getModel().getValueAt(selRow, 14);
		
		tfName.setText(fillName);
		tfPname.setText(fillPname);
		tfDate.setText(fillDate);
		tfIfwt.setText(fillIfwt);
		tfMNaF.setText(fillMNaF);
		tfIntern.setText(fillIntern);
		tfBeschverh.setText(fillEmpl);
		tfStart.setText(fillStart);
		tfEnde.setText(fillEnd);
		tfExtern.setText(fillExternal);
		tfEmail.setText(fillMail);
		taInstructions.setText(fillInstr);
		taLab.setText(fillLab);
		taHazard.setText(fillHazard);

	}
	
	
	public static void fillDeviceData() {
		int selRow = MainFrame.getDeviceEditorTable().getSelectedRow();
		String fillDeviceName = (String) MainFrame.getDeviceEditorTable().getValueAt(selRow, 1);
		String fillDeviceDescription = (String) MainFrame.getDeviceEditorTable().getValueAt(selRow, 2);
		String fillDeviceRoom = (String) MainFrame.getDeviceEditorTable().getValueAt(selRow, 3);
		
		tfDeviceName.setText(fillDeviceName);
		tfDeviceDescription.setText(fillDeviceDescription);
		tfDeviceRoom.setText(fillDeviceRoom);
	}
	
	
	public static void fillRoomData() {
		int selRow = MainFrame.getRoomsEditorTable().getSelectedRow();
		String fillRoomName = (String) MainFrame.getRoomsEditorTable().getValueAt(selRow, 0);
		String fillRoomDescription = (String) MainFrame.getRoomsEditorTable().getValueAt(selRow, 1);
		
		tfRoomName.setText(fillRoomName);
		tfRoomDescription.setText(fillRoomDescription);
	}
	
	
	public static void fillHazardousSubstancesData() {
		int selRow = MainFrame.getDangerSubstEditorTable().getSelectedRow();
		String fillHazardousSubstance = (String) MainFrame.getDangerSubstEditorTable().getValueAt(selRow, 0);
		
		tfHazardousSubstance.setText(fillHazardousSubstance);
	}
	
	
	// method to save edited data
	@SuppressWarnings("unused")
	public static void savePersonData() {						// used in ActionListener of JButton btnSave "Änderungen Speichern"
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
			
			// queries to disallow wrong database entries
			while (g == 2) {
				g = 1;
				try {
					nameVar = tfName.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Name'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					pnameVar = tfPname.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Vorname'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				if (!tfDate.getText().isEmpty()) {
					try {
						dateVar = format.parse(tfDate.getText());
					} catch (Exception e) {
						JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Datum' \n " + "Datum bitte im Format 'tt.mm.jjjj' eingeben", "Dialog", JOptionPane.ERROR_MESSAGE);
						g = 0;
					}
				}
				try {
					IfwtVar = tfIfwt.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ifwt'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					MNaFVar = tfMNaF.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'MNaF'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					InternVar = tfIntern.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Intern'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					EmplVar = tfBeschverh.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Beschäftigungsverhältnis'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					StartVar = tfStart.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Beginn'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					EndVar = tfEnde.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ende'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					ExternalVar = tfExtern.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Extern'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					MailVar = tfEmail.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'E-Mail'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					InstrVar = taInstructions.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Allgemeine Unterweisung'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					LabVar = taLab.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Laboreinrichtungen'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					HazardVar = taHazard.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Gefahrstoffe'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
			}
			
			
			// execute Database update if there are no wrong entrys
			while (g == 1) {
				g = 0;
				
				String query="Update Personen set Name='" + tfName.getText() + "' ,Vorname='" + tfPname.getText() + "'  ,Datum='" + tfDate.getText() + 
						"' ,Ifwt='" + tfIfwt.getText() + "' ,MNaF='" + tfMNaF.getText() + "' ,Intern='" + tfIntern.getText() + 
						"' ,Beschaeftigungsverhaeltnis='" + tfBeschverh.getText() + "' ,Beginn='" + tfStart.getText() + "' ,Ende='" + tfEnde.getText() +
						"' ,Extern='" + tfExtern.getText() + "' ,'E-Mail Adresse'='" + tfEmail.getText() +  "' ,'Allgemeine Unterweisung'='" + taInstructions.getText() + 
						"' ,'Laboreinrichtungen'='" + taLab.getText() +  "' ,'Gefahrstoffe'='" + taHazard.getText() +  "' ,ID='" + ID + "' where ID='"+ID+"' ";
				
				con = DBConnection.connect();
				pstmt = con.prepareStatement(query);
				con.setAutoCommit(false);
				//System.out.println("Speichert Eintrag...");
				pstmt.execute();
				con.commit();
				
				con.close();
			    pstmt.close();
			    
			    MainFrame.getPersonData();
			    
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
	
	private void btnEditRoomsPressed() {
		int row = MainFrame.getDeviceEditorTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Kein Eintrag ausgew\u00e4hlt!");
			return;
		}
		int dID = (int)MainFrame.getValueByColName(MainFrame.getDeviceEditorTable(), row, "Ger\u00e4teID");
		roomAssignement = RoomAssignement.getInstance(dID);
	}
	
	public static void saveDeviceData() {
		String deviceNameVar;
		String deviceDescriptionVar;
		String deviceRoomVar;
		
		int g = 0;
		
		try {
			g = 2;
			
			// queries to disallow wrong database entries
			while (g == 2) {
				g = 1;
				try {
					deviceNameVar = tfDeviceName.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ger\u00e4tename'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					deviceDescriptionVar = tfDeviceDescription.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ger\u00e4tebeschreibugn'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					deviceRoomVar = tfDeviceRoom.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Raum'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
			}
			
			// execute Database update if there are no wrong entrys
			while (g == 1) {
				g = 0;
				String query="UPDATE Ger\u00e4te SET Name='" + tfDeviceName.getText() + "' ,Beschreibung='" + tfDeviceDescription.getText() + "'  ,Raum='" + tfDeviceRoom.getText() + 
							  "' ,Ger\u00e4teID='" + ID + "' WHERE Ger\u00e4teID='"+ID+"' ";
							
				con = DBConnection.connect();
				pstmt = con.prepareStatement(query);
				con.setAutoCommit(false);
				//System.out.println("Speichert Eintrag...");
				pstmt.execute();
				con.commit();
							
				con.close();
				pstmt.close();
						    
				MainFrame.getDeviceData();
						    
				JOptionPane.showMessageDialog(new JFrame(), "Eintrag geändert", "Dialog", JOptionPane.INFORMATION_MESSAGE);

			}
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static void saveRoomData() {
		String roomNameVar;
		String roomDescriptionVar;
		
		int g = 0;
		
		try {
			g = 2;
			
			// queries to disallow wrong database entries
			while (g == 2) {
				g = 1;
				try {
					roomNameVar = tfRoomName.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ger\u00e4tename'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
				try {
					roomDescriptionVar = tfRoomDescription.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ger\u00e4tebeschreibugn'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
			}
			
			// execute Database update if there are no wrong entrys
			while (g == 1) {
				g = 0;
				String query="UPDATE R\u00e4ume SET Name='" + tfRoomName.getText() + "' ,Beschreibung='" + tfRoomDescription.getText() + 
							 "' WHERE Name='"+name+"' ";
							
				con = DBConnection.connect();
				pstmt = con.prepareStatement(query);
				con.setAutoCommit(false);
				//System.out.println("Speichert Eintrag...");
				pstmt.execute();
				con.commit();
							
				con.close();
				pstmt.close();
						    
				MainFrame.getRoomData();
						    
				JOptionPane.showMessageDialog(new JFrame(), "Eintrag geändert", "Dialog", JOptionPane.INFORMATION_MESSAGE);

			}
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static void saveHazardousSubstancesData() {
		String hazardousSubstanceVar;
		
		int g = 0;
		
		try {
			g = 2;
			
			// queries to disallow wrong database entries
			while (g == 2) {
				g = 1;
				try {
					hazardousSubstanceVar = tfHazardousSubstance.getText();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(new JFrame(), "Fehlerhafter Eintrag im Feld 'Ger\u00e4tename'", "Dialog", JOptionPane.ERROR_MESSAGE);
					g = 0;
				}
			}
			
			// execute Database update if there are no wrong entrys
			while (g == 1) {
				g = 0;
				String query="UPDATE Gefahrstoffe SET Name='" + tfHazardousSubstance.getText() + 
							 "' WHERE Name='"+name+"' ";
							
				con = DBConnection.connect();
				pstmt = con.prepareStatement(query);
				con.setAutoCommit(false);
				pstmt.execute();
				con.commit();
							
				con.close();
				pstmt.close();
						    
				MainFrame.getDangerSubstData();
						    
				JOptionPane.showMessageDialog(new JFrame(), "Eintrag geändert", "Dialog", JOptionPane.INFORMATION_MESSAGE);

			}
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static String getDangerSubstTxt(int pID)
	{
		String comment = taHazard.getText();
		String res = comment;
		Object[][] data = DBConnection.getDangerSubstAssignedData(pID);
		if(!comment.isEmpty())
		{
			res = res.concat("\nGefahrstoffe mit denen gearbeitet wird:");
		}
		else
		{
			res = res.concat("Gefahrstoffe mit denen gearbeitet wird:");
		}
		for(int i = 0; i < data.length; i++)
		{
			res = res.concat("\n-"+data[i][0]);
		}
		return res;
	}
	
	private static String getLabSetupTxt(int pID)
	{
		String comment = taLab.getText();
		String res = comment;
		Object[][] data = DBConnection.getDeviceAssignedData(pID);
		if(!comment.isEmpty())
		{
			res = res.concat("\nGer\u00e4te mit denen gearbeitet wird:");
		}
		else
		{
			res = res.concat("Ger\u00e4te mit denen gearbeitet wird:");
		}
		for(int i = 0; i < data.length; i++)
		{
			res = res.concat("\n-Ger\u00e4teID: "+data[i][0]+", Name: "+data[i][1]+", Raum: "+data[i][3]);
		}
		return res;
	}
}