package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import database.DBConnection;
import database.DBExporter;
import database.DBImporter;
import database.DBtoCSVExporter;
import net.miginfocom.swing.MigLayout;
import printer.FormDocPrinter;
import printer.PrintData;



public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private Color frameColor = new Color(32, 32, 32);
	private Color backgroundColor = new Color(25, 25, 25);
	//private Color redColor = new Color(255, 0, 0);
	private Color foregroundColor = new Color(255, 255, 255);

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel personTab;
	private JPanel configPanel;
	private JPanel configElementsPanel;
	private static JPanel tablePanel;
	private JPanel infoPanel;
	private JPanel deviceTab;
	private static JPanel deviceTablePanel;
	private JPanel roomsTab;
	private static JPanel roomsTablePanel;
	private JPanel dangerSubstTab;
	private static JPanel dangerSubstTablePanel;
	
	private static JScrollPane spTable;
	private JScrollPane spGeneralInstruction;
	private JScrollPane spLabSetup;
	private JScrollPane spDangerSubst;
	private JScrollPane spDevices;
	private JScrollPane spRooms;
	private JScrollPane spDangerSubstTab;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JButton btnPrint;
	private JButton btnEditData;
	private JMenuItem miSave;
	private JMenuItem miImport;
	private JMenuItem miExport;
	
	private JLabel lblConfigPanel;
	private JButton btnStartSearch;
	private static JTextField tfSearch;
	private JLabel lblInstitut;
	private JButton btnIfwt;
	private JButton btnLmn;
	private JButton btnLmw;
	private JButton btnLot;
	private JButton btnLwf;
	private JLabel lblDeviceCenter;
	private JButton btnMnaf;
	private JLabel lblIntern;
	private JButton btnIntern;
	private JLabel lblExtern;
	private JButton btnExtern;
	private JButton btnEditUser;
	
	private static JTable table = new JTable();
	private static JTable editorTable = new JTable();
	private static JTable deviceEditorTable = new JTable();
	private static JTable deviceTable = new JTable();
	private static JTable roomEditorTable = new JTable();
	private static JTable roomsTable = new JTable();
	private static JTable dangerSubstEditorTable = new JTable();
	private static JTable dangerSubstTable = new JTable();
	
	
	private static DefaultTableModel dtm;
	private static DefaultTableModel deviceTableModel;
	private static DefaultTableModel roomsTableModel;
	private static DefaultTableModel dangerSubstTableModel;
	
	private static DefaultTableCellRenderer cellRenderer;
	private static DefaultTableCellRenderer cellRendererColor;
	private static DefaultTableCellRenderer deviceCellRenderer;
	private static DefaultTableCellRenderer roomsCellRenderer;
	private static DefaultTableCellRenderer dangerSubstCellRenderer;
	
	private JLabel lblGeneralInstruction;
	private JLabel lblLabSetup;
	private JLabel lblDangerSubst;
	
	private JTextArea taGeneralInstruction;
	private JTextArea taLabSetup;
	private JTextArea taDangerSubst;
	
	private static Login login;
	private static DataEditor dataEditor;
	private static UserEditor userEditor;
	private static DeviceStatistics deviceStats;
	private FormDocPrinter fPrinter;
	
	private static Connection conn = null;
	private JPanel filterPanelDeviceTab;
	private JPanel filterPanelRoomTab;
	private JPanel filterPanelDangerSubstTab;
	private JButton btnSearchDeviceID;
	private JButton btnSearchDeviceName;
	private JButton btnSearchDeviceDescript;
	private JButton btnSearchDeviceRoom;
	private static JTextField tfRoomName;
	private static JTextField tfRoomDescript;
	private JButton btnSearchRoomName;
	private JButton btnSearchRoomDescript;
	private static JTextField tfDeviceID;
	private static JTextField tfDeviceName;
	private static JTextField tfDeviceDescript;
	private static JTextField tfDeviceRoom;
	private JButton btnAllDevices;
	private JButton btnAllRooms;
	private JButton btnDeviceStats;
	private static JTextField tfDangerSubstName;
	private JButton searchButtonDangerSubstName;
	
	
	public static void main(String[] args) {
		login = Login.getInstance();
	}
	
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					if (!login.checkAdmin()) {
						//System.out.println("Isnt Admin");
						frame.fileMenu.setVisible(false);
						frame.btnEditUser.setVisible(false);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Sicherheitsunterweisung am Institut f\u00fcr Werkstofftechnik der Universit\u00e4t Siegen");
		setBackground(frameColor);
		setForeground(foregroundColor);
		setBounds(100, 100, 1200, 600);
		contentPane = new JPanel();
		contentPane.setBackground(backgroundColor);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//contentPane.setLayout(new MigLayout("", "[1200,grow]", "[150.0,grow][300.0,grow][150.0,grow]"));
		contentPane.setLayout(new MigLayout("","[grow]","[grow]"));

		// Building the menu bar
		menuBar = new JMenuBar();
		menuBar.setBackground(frameColor);
		menuBar.setForeground(foregroundColor);
		
		// Building the menu (Datei)
		fileMenu = new JMenu("Datei");
		fileMenu.setForeground(foregroundColor);
		menuBar.add(fileMenu);
		
		// Menu item (Datenbank Speichern)
		miSave = new JMenuItem("Datenbank Speichern");
		miSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				DBExporter.exportDB();
			}
		});
		miSave.setBackground(backgroundColor);
		miSave.setForeground(foregroundColor);
		fileMenu.add(miSave);
		
		// Menu item (Datenbank Importieren)
		miImport = new JMenuItem("Datenbank Importieren");
		miImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				DBImporter.importDB();
			}
		});
		miImport.setBackground(backgroundColor);
		miImport.setForeground(foregroundColor);
		fileMenu.add(miImport);
		
		// Menu item (Datenbank Exportieren)
		miExport = new JMenuItem("Datenbank als CSV exportieren");
		miExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				DBtoCSVExporter.export();
			}
		});
		miExport.setBackground(backgroundColor);
		miExport.setForeground(foregroundColor);
		fileMenu.add(miExport);
		
		JLabel space = new JLabel(" ");
		menuBar.add(space);
		
		// Building the Button (Daten Bearbeiten)
		btnEditData = new JButton("Daten Bearbeiten");
		btnEditData.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	dataEditor = DataEditor.getInstance();
	        	dataEditor.setVisible(true);
	        	
	        }
		});
		btnEditData.setBorder(null);
		btnEditData.setBackground(frameColor);
		btnEditData.setForeground(foregroundColor);
		menuBar.add(btnEditData);
		
		space = new JLabel("  ");
		menuBar.add(space);
		
		// Building the Button (Drucken)
		btnPrint = new JButton("Drucken");
		btnPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				printPressed();
			}
		});
		btnPrint.setBorder(null);
		btnPrint.setBackground(frameColor);
		btnPrint.setForeground(foregroundColor);
		menuBar.add(btnPrint);
		
		space = new JLabel("  ");
		menuBar.add(space);
		
		//Building the Button (Benutzer Verwalten)
		btnEditUser = new JButton("Benutzer Verwalten");
		btnEditUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				userEditor = UserEditor.getInstance();
				userEditor.setVisible(true);
			}
		});
		btnEditUser.setBorder(null);
		btnEditUser.setBackground(frameColor);
		btnEditUser.setForeground(foregroundColor);
		menuBar.add(btnEditUser);
		
		// Adding the bar to the frame
		setJMenuBar(menuBar);
		
		//Tabbed Pane
		tabbedPane = new JTabbedPane();
		tabbedPane.setBackground(backgroundColor);
		tabbedPane.setForeground(foregroundColor);
		
		//tabbedPane.borderHightlightColor(Color.WHITE);
		contentPane.add(tabbedPane, "cell 0 0,grow");
		
		//###Tab Personen###
		personTab = new JPanel();
		personTab.setBackground(backgroundColor);
		personTab.setLayout(new MigLayout("", "[1200,grow]", "[150.0,grow][300.0,grow][150.0,grow]"));
				
		tabbedPane.addTab("Personen", personTab);
		// Building the panel for all elements like buttons
		configPanel = new JPanel();
		configPanel.setBackground(backgroundColor);
		configPanel.setForeground(foregroundColor);
		personTab.add(configPanel, "cell 0 0,grow");
		configPanel.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
				
		// Label for the config panel title
		lblConfigPanel = new JLabel("Sicherheitsunterweisung am Institut f\u00fcr Werkstofftechnik und Ger\u00e4tezentrum MNaF");
		lblConfigPanel.setFont(new Font("Dialog", Font.BOLD, 18));
		lblConfigPanel.setForeground(foregroundColor);
		configPanel.add(lblConfigPanel, "cell 0 0");
				
		// Panel that holds the elements form configPanel
		configElementsPanel = new JPanel();
		configElementsPanel.setBackground(backgroundColor);
		configElementsPanel.setForeground(foregroundColor);
		configPanel.add(configElementsPanel, "cell 0 1,grow");
		configElementsPanel.setLayout(new MigLayout("", "[grow]25[grow]25[grow]25[grow]25[grow]", "[grow]8[grow]"));
				
		tfSearch = new JTextField();
		tfSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent evt) {
				tfSearch.selectAll();
			}
		});
		tfSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFilter(DBConnection.getName());
			}
		});
		tfSearch.setText("Bitte Namen eingeben");
		configElementsPanel.add(tfSearch, "cell 0 0,growx");
		tfSearch.setColumns(10);
				

		lblInstitut = new JLabel("Institut f\u00fcr Werkstoffstechnik (Ifwt)");

		lblInstitut.setFont(new Font("Dialog", Font.BOLD, 13));
		lblInstitut.setForeground(foregroundColor);
		configElementsPanel.add(lblInstitut, "cell 1 0");
				
		lblDeviceCenter = new JLabel("Ger\u00e4tezentrum (MNaF)");
		lblDeviceCenter.setFont(new Font("Dialog", Font.BOLD, 13));
		lblDeviceCenter.setForeground(foregroundColor);
		configElementsPanel.add(lblDeviceCenter, "cell 2 0");
				
		lblIntern = new JLabel("Intern");
		lblIntern.setFont(new Font("Dialog", Font.BOLD, 13));
		lblIntern.setForeground(foregroundColor);
		configElementsPanel.add(lblIntern, "cell 3 0");
				
		lblExtern = new JLabel("Extern");
		lblExtern.setFont(new Font("Dialog", Font.BOLD, 13));
		lblExtern.setForeground(foregroundColor);
		configElementsPanel.add(lblExtern, "cell 4 0");
				
		btnStartSearch = new JButton("Suche Starten");
		btnStartSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadFilter(DBConnection.getName());
			}
		});
		configElementsPanel.add(btnStartSearch, "cell 0 1,aligny top");
				
		btnIfwt = new JButton("Ifwt");
		btnIfwt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadFilter(DBConnection.getIfwt());
			}
		});
		configElementsPanel.add(btnIfwt, "flowx,cell 1 1,aligny top");
				
		btnLmn = new JButton("LMN");
		btnLmn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadFilter(DBConnection.getLMN());
			}
		});
		configElementsPanel.add(btnLmn, "cell 1 1,aligny top");
				
		btnLmw = new JButton("LMW");
		btnLmw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadFilter(DBConnection.getLMW());
			}
		});
		configElementsPanel.add(btnLmw, "cell 1 1,aligny top");
				
		btnLot = new JButton("LOT");
		btnLot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadFilter(DBConnection.getLOT());
			}
		});
		configElementsPanel.add(btnLot, "cell 1 1,aligny top");
				
		btnLwf = new JButton("LWF");
		btnLwf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadFilter(DBConnection.getLWF());
			}
		});
		configElementsPanel.add(btnLwf, "cell 1 1,aligny top");
				
		btnMnaf = new JButton("MNaF");
		btnMnaf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadFilter(DBConnection.getMNaF());
			}
		});
		configElementsPanel.add(btnMnaf, "cell 2 1,aligny top");
				
		btnIntern = new JButton("Intern");
		btnIntern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadFilter(DBConnection.getIntern());
			}
		});
		configElementsPanel.add(btnIntern, "cell 3 1,aligny top");
		
		btnExtern = new JButton("Extern");
		btnExtern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				loadFilter(DBConnection.getExtern());
			}
		});
		configElementsPanel.add(btnExtern, "cell 4 1,aligny top");
				
		// Building the panel for the table
		tablePanel = new JPanel();
		tablePanel.setBackground(backgroundColor);
		tablePanel.setForeground(foregroundColor);
		personTab.add(tablePanel, "cell 0 1,grow");
		tablePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));

		spTable = new JScrollPane();
		tablePanel.add(spTable, "cell 0 0,grow");
		spTable.setViewportView(table);
		
		getPersonData();
		
		// Fill JTextAreas (Allgemeine Unterweisung, Laboreinrichtungen, Gefahrstoffe)  with data from selected row
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				String fillInstr = (String) table.getModel().getValueAt(i, 12);
				String fillLab =  (String) table.getModel().getValueAt(i, 13);
				String fillHazard =  (String) table.getModel().getValueAt(i, 14);
				taGeneralInstruction.setText(fillInstr);
				taLabSetup.setText(fillLab);
				taDangerSubst.setText(fillHazard);
			}
		});
				
		// Building the panel for the informations that will be displayed
		infoPanel = new JPanel();
		infoPanel.setBackground(backgroundColor);
		infoPanel.setForeground(foregroundColor);
		personTab.add(infoPanel, "cell 0 2,grow");
		infoPanel.setLayout(new MigLayout("", "[grow]25[grow]25[grow]", "[][grow]"));
				
		// Building the (Allgemeine Unterweisung) information text area with title
		lblGeneralInstruction = new JLabel("Allgemeine Unterweisung (Datum s.o.)");
		lblGeneralInstruction.setFont(new Font("Dialog", Font.BOLD, 13));
		lblGeneralInstruction.setForeground(foregroundColor);
		infoPanel.add(lblGeneralInstruction, "cell 0 0");
				
		spGeneralInstruction = new JScrollPane(
			          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			          JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		infoPanel.add(spGeneralInstruction, "width 25%, cell 0 1,grow");
				
		taGeneralInstruction = new JTextArea();
		taGeneralInstruction.setLineWrap(true);
		taGeneralInstruction.setWrapStyleWord(true);
		taGeneralInstruction.setEditable(false);
		//taAllgemeineUnterweisung.setBackground(redColor);
		spGeneralInstruction.setViewportView(taGeneralInstruction);
				
		// Building the (Laboreinrichtungen) informationen text area with title
		lblLabSetup = new JLabel("Laboreinrichtungen");
		lblLabSetup.setFont(new Font("Dialog", Font.BOLD, 13));
		lblLabSetup.setForeground(foregroundColor);
		infoPanel.add(lblLabSetup, "cell 1 0");
				
		spLabSetup = new JScrollPane(
			          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			          JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		infoPanel.add(spLabSetup, "width 25%, cell 1 1,grow");
				
		taLabSetup = new JTextArea();
		taLabSetup.setLineWrap(true);
		taLabSetup.setWrapStyleWord(true);
		taLabSetup.setEditable(false);
		spLabSetup.setViewportView(taLabSetup);
				
		// Building the (Gefahrstoffe) information text area with title
		lblDangerSubst = new JLabel("Gefahrstoffe");
		lblDangerSubst.setFont(new Font("Dialog", Font.BOLD, 13));
		lblDangerSubst.setForeground(foregroundColor);
		infoPanel.add(lblDangerSubst, "cell 2 0");
				
		spDangerSubst = new JScrollPane(
			            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		infoPanel.add(spDangerSubst, "width 25%, cell 2 1,grow");
				
		taDangerSubst = new JTextArea();
		taDangerSubst.setLineWrap(true);
		taDangerSubst.setWrapStyleWord(true);
		taDangerSubst.setEditable(false);
		spDangerSubst.setViewportView(taDangerSubst);
				
		taDangerSubst.setLineWrap(true);
		taDangerSubst.setWrapStyleWord(true);
		taDangerSubst.setEditable(false);
		spDangerSubst.setViewportView(taDangerSubst);
				
		getDeviceData();
	
		//###Tab Geraete###
		deviceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		deviceTab = new JPanel();
		deviceTab.setBackground(backgroundColor);
		deviceTab.setLayout(new MigLayout("", "[1200,grow]", "[150.0,grow][450.0,grow][]"));
		tabbedPane.addTab("Ger\u00e4te", deviceTab);
		
		
		filterPanelDeviceTab = new JPanel();
		filterPanelDeviceTab.setBackground(backgroundColor);
		filterPanelDeviceTab.setForeground(foregroundColor);
		deviceTab.add(filterPanelDeviceTab, "cell 0 0,grow");
		filterPanelDeviceTab.setLayout(new MigLayout("", "[300,grow][300,grow][300,grow][300,grow]", "[][grow][][grow]"));
		
		tfDeviceID = new JTextField();
		tfDeviceID.setText("Bitte Geräte-ID eingeben");
		filterPanelDeviceTab.add(tfDeviceID, "cell 0 0,growx");
		tfDeviceID.setColumns(10);
		tfDeviceID.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfDeviceID.selectAll();
			}
		});
		tfDeviceID.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerDeviceIDSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		tfDeviceName = new JTextField();
		tfDeviceName.setText("Bitte Gerätenamen eingeben");
		filterPanelDeviceTab.add(tfDeviceName, "cell 1 0,growx");
		tfDeviceName.setColumns(10);
		tfDeviceName.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfDeviceName.selectAll();
			}
		});
		tfDeviceName.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerDeviceNameSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		tfDeviceDescript = new JTextField();
		tfDeviceDescript.setText("Bitte Gerätebeschreibung eingeben");
		filterPanelDeviceTab.add(tfDeviceDescript, "cell 2 0,growx");
		tfDeviceDescript.setColumns(10);
		tfDeviceDescript.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfDeviceDescript.selectAll();
			}
		});
		tfDeviceDescript.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerDeviceDescriptSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		tfDeviceRoom = new JTextField();
		tfDeviceRoom.setText("Bitte Geräteraum eingeben");
		filterPanelDeviceTab.add(tfDeviceRoom, "cell 3 0,growx");
		tfDeviceRoom.setColumns(10);
		tfDeviceRoom.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfDeviceRoom.selectAll();
			}
		});
		tfDeviceRoom.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerDeviceRoomSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		btnSearchDeviceID = new JButton("Geräte-ID suchen");
		btnSearchDeviceID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerDeviceIDSearch();
			}
		});
		
		filterPanelDeviceTab.add(btnSearchDeviceID, "cell 0 2,alignx left");
		
		btnSearchDeviceName = new JButton("Gerätenamen suchen");
		btnSearchDeviceName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerDeviceNameSearch();
			}
		});
		filterPanelDeviceTab.add(btnSearchDeviceName, "cell 1 2,alignx left");
		
		btnSearchDeviceDescript = new JButton("Beschreibung suchen");
		btnSearchDeviceDescript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerDeviceDescriptSearch();
			}
		});
		filterPanelDeviceTab.add(btnSearchDeviceDescript, "cell 2 2");
		
		
		
		btnSearchDeviceRoom = new JButton("Raum suchen");
		btnSearchDeviceRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerDeviceRoomSearch();
			}
		});
		filterPanelDeviceTab.add(btnSearchDeviceRoom, "cell 3 2");
		
		deviceTablePanel = new JPanel();
		deviceTablePanel.setBackground(backgroundColor);
		deviceTab.add(deviceTablePanel, "cell 0 1, grow");
		deviceTablePanel.setLayout(new MigLayout("", "[1200,grow]", "[grow][grow][][][]"));
		
		spDevices = new JScrollPane();
		deviceTablePanel.add(spDevices, "cell 0 0,grow");
		spDevices.setViewportView(deviceTable);
		
		btnAllDevices = new JButton("Alle Geräte anzeigen");
		btnAllDevices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFilterDevices(DBConnection.getDeviceData());
			}
		});
		deviceTablePanel.add(btnAllDevices, "cell 0 3");
		
		btnDeviceStats = new JButton("Gerätestatistik");
		btnDeviceStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deviceStatsButtonPressed();
			}
		});
		deviceTablePanel.add(btnDeviceStats, "cell 0 3");
		
		
		
		//###Tab Raeume###
		roomsTab = new JPanel();
		roomsTab.setBackground(backgroundColor);
		roomsTab.setLayout(new MigLayout("", "[1200,grow]", "[150.0,grow][450.0,grow]"));
		tabbedPane.addTab("R\u00e4ume", roomsTab);
		
		filterPanelRoomTab = new JPanel();
		filterPanelRoomTab.setBackground(backgroundColor);
		filterPanelRoomTab.setForeground(foregroundColor);
		filterPanelRoomTab.setLayout(new MigLayout("", "[400,grow][400,grow][400,grow]", "[grow][][][grow]"));
		roomsTab.add(filterPanelRoomTab, "cell 0 0,grow");
		
		tfRoomName = new JTextField();
		tfRoomName.setText("Bitte Raumnamen eingeben");
		filterPanelRoomTab.add(tfRoomName, "cell 0 1,growx");
		tfRoomName.setColumns(10);
		tfRoomName.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfRoomName.selectAll();
			}
		});
		tfRoomName.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerRoomNameSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		tfRoomDescript = new JTextField();
		tfRoomDescript.setText("Bitte Beschreibung eingeben");
		filterPanelRoomTab.add(tfRoomDescript, "cell 1 1,growx");
		tfRoomDescript.setColumns(10);
		tfRoomDescript.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfRoomDescript.selectAll();
			}
		});
		tfRoomDescript.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerRoomDescriptSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		btnSearchRoomName = new JButton("Raumnamen suchen");
		btnSearchRoomName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerRoomNameSearch();
			}
		});
		filterPanelRoomTab.add(btnSearchRoomName, "cell 0 2");
		
		btnSearchRoomDescript = new JButton("Beschreibung suchen");
		btnSearchRoomDescript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerRoomDescriptSearch();
			}
		});
		filterPanelRoomTab.add(btnSearchRoomDescript, "cell 1 2");
		
		roomsTablePanel = new JPanel();
		roomsTablePanel.setBackground(backgroundColor);
		roomsTab.add(roomsTablePanel, "cell 0 1, grow");
		roomsTablePanel.setLayout(new MigLayout("", "[1200,grow]", "[grow][][]"));
		
		spRooms = new JScrollPane();
		roomsTablePanel.add(spRooms, "cell 0 1, grow");
		spRooms.setViewportView(roomsTable);
		
		btnAllRooms = new JButton("Alle Räume anzeigen");
		btnAllRooms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFilterRooms(DBConnection.getRoomsData());
			}
		});
		roomsTablePanel.add(btnAllRooms, "cell 0 2");
		
		getRoomData();
		
		//###Tab Gefahrstoffe###
		dangerSubstTab = new JPanel();
		dangerSubstTab.setBackground(backgroundColor);
		dangerSubstTab.setLayout(new MigLayout("", "[1200,grow]", "[150.0,grow][450.0,grow][]"));
		tabbedPane.addTab("Gefahrstoffe", dangerSubstTab);
				
		filterPanelDangerSubstTab = new JPanel();
		filterPanelDangerSubstTab.setBackground(backgroundColor);
		filterPanelDangerSubstTab.setForeground(foregroundColor);
		filterPanelDangerSubstTab.setLayout(new MigLayout("", "[400,grow][400,grow][400,grow]", "[grow][][][grow]"));
		dangerSubstTab.add(filterPanelDangerSubstTab, "cell 0 0,grow");

		tfDangerSubstName = new JTextField();
		tfDangerSubstName.setText("Bitte Gefahrstoffnamen eingeben");
		tfDangerSubstName.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfDangerSubstName.selectAll();
			}
		});
		tfDangerSubstName.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					triggerDangerSubstNameSearch();
					}
			}
			public void keyReleased(KeyEvent e) {}
		});
		filterPanelDangerSubstTab.add(tfDangerSubstName, "cell 0 0,growx");
		tfDangerSubstName.setColumns(10);
	
		searchButtonDangerSubstName = new JButton("Gefahrstoffenamen suchen");
		searchButtonDangerSubstName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						triggerDangerSubstNameSearch();
			}
		});
		filterPanelDangerSubstTab.add(searchButtonDangerSubstName, "cell 0 2,alignx left");
				
		dangerSubstTablePanel = new JPanel();
		dangerSubstTablePanel.setBackground(backgroundColor);
		dangerSubstTab.add(dangerSubstTablePanel, "cell 0 1, grow");
		dangerSubstTablePanel.setLayout(new MigLayout("", "[1200,grow]", "[grow][grow][][][]"));
				
		spDangerSubstTab = new JScrollPane();
		dangerSubstTablePanel.add(spDangerSubstTab, "cell 0 0,grow");
		spDangerSubstTab.setViewportView(dangerSubstTable);
				
		getDangerSubstData();

		//Creating a formPrinter
		fPrinter = new FormDocPrinter();

	}
	
	public void loadFilter(Object[][] filteredTable) {
		dtm.setRowCount(0);
		
		for (int i = 0; i < filteredTable.length; i++) {
			
			dtm.addRow(new Object[] {filteredTable[i][0],
									 filteredTable[i][1],
									 filteredTable[i][2],
									 filteredTable[i][3],
									 filteredTable[i][4],
									 filteredTable[i][5],
									 filteredTable[i][6],
									 filteredTable[i][7],
									 filteredTable[i][8],
									 filteredTable[i][9],
									 filteredTable[i][10],
									 filteredTable[i][11],
									 filteredTable[i][12],
									 filteredTable[i][13],
									 filteredTable[i][14]});
									 
		}
	}
	
	public void loadFilterDevices(Object[][] filteredTable) {
		deviceTableModel.setRowCount(0);
		
		for (int i = 0; i < filteredTable.length; i++) {
			
			deviceTableModel.addRow(new Object[] {
									filteredTable[i][0],
									filteredTable[i][1],
									filteredTable[i][2],
									filteredTable[i][3]});	 
		}
		
	}
	
	public void loadFilterRooms(Object[][] filteredTable) {
		roomsTableModel.setRowCount(0);
		
		for (int i = 0; i < filteredTable.length; i++) {
			
			roomsTableModel.addRow(new Object[] {
									filteredTable[i][0],
									filteredTable[i][1]});			 
		}
		
	}
	
	public void loadFilterDangerSubst(Object[][] filteredTable) {
		dangerSubstTableModel.setRowCount(0);
		
		for (int i = 0; i < filteredTable.length; i++) {
			
			dangerSubstTableModel.addRow(new Object[] {
									filteredTable[i][0]});			 
		}
	}
	
	public static JTable getEditorTable() {
		return editorTable;
	}
	
	public static DefaultTableModel getDefaultTableModel() {
		return dtm;
	}
	
	public static JTable getDeviceEditorTable() {
		return deviceEditorTable;
	}
	
	public static DefaultTableModel getDeviceTableModel() {
		return deviceTableModel;
	}
	
	public static JTable getRoomsEditorTable() {
		return roomEditorTable;
	}
	
	public static DefaultTableModel getRoomsTableModel() {
		return roomsTableModel;
	}
	
	public static JTable getDangerSubstEditorTable() {
		return dangerSubstEditorTable;
	}
	
	public static DefaultTableModel getDangerSubstTableModel() {
		return dangerSubstTableModel;
	}
	
	private void printPressed() {
		//getting table and textArea data
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Kein Eintrag ausgew\u00e4hlt!");
			return;
		}
		String familyName = (String)getValueByColName(table, row, "Name");
		String firstName = (String)getValueByColName(table, row, "Vorname");
		String date = (String)getValueByColName(table, row, "Datum");
		String ifwt = (String)getValueByColName(table, row, "Ifwt");
		String mnaf = (String)getValueByColName(table, row, "MNaF");
		String intern = (String)getValueByColName(table, row, "Intern");
		String extern = (String)getValueByColName(table, row, "Extern");
		String genInstr = taGeneralInstruction.getText();
		String labSetup = taLabSetup.getText();
		String dangerSubst = taDangerSubst.getText();
		
		//setup of printData
		PrintData printData = new PrintData();
		printData.setFamilyName(familyName);
		printData.setFirstName(firstName);
		printData.setDate(date);
		printData.setIfwt(ifwt);
		printData.setMNaF(mnaf);
		printData.setIntern(intern);
		printData.setExtern(extern);
		printData.setGeneralInstructions(genInstr);
		printData.setLabSetup(labSetup);
		printData.setDangerousSubstances(dangerSubst);
		
		//start printing process
		try {
			fPrinter.print(printData);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(PrinterException e) {
			e.printStackTrace();
		}
	}
	
	private void triggerDeviceIDSearch()
	{
		String id = getDeviceIDFilterTxt();
		loadFilterDevices(DBConnection.getDeviceByID(id));
	}
	
	private void triggerDeviceNameSearch()
	{
		String name = getDeviceNameFilterTxt();
		loadFilterDevices(DBConnection.getDeviceByName(name));
	}
	
	private void triggerDeviceDescriptSearch()
	{
		String descript = getDeviceDescriptFilterTxt();
		loadFilterDevices(DBConnection.getDeviceByDescript(descript));
	}
	
	private void triggerDeviceRoomSearch()
	{
		String raum = getDeviceRoomFilterTxt();
		loadFilterDevices(DBConnection.getDeviceByRoom(raum));
	}
	
	private void triggerRoomNameSearch()
	{
		String name = getRoomNameFilterTxt();
		loadFilterRooms(DBConnection.getRoomByName(name));
	}
	
	private void triggerRoomDescriptSearch()
	{
		String descript = getRoomDescriptFilterTxt();
		loadFilterRooms(DBConnection.getRoomByDescript(descript));
	}
	
	private void triggerDangerSubstNameSearch()
	{
		String name = getDangerSubstNameFilterTxt();
		loadFilterDangerSubst(DBConnection.getDangerSubstByName(name));
	}
	
	private void deviceStatsButtonPressed() {
		int row = deviceTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Kein Eintrag ausgew\u00e4hlt!");
			return;
		}
		int gID = (int)getValueByColName(deviceTable, row, "Ger\u00e4teID");
		deviceStats = DeviceStatistics.getInstance(gID);
		if(deviceStats != null) {
			deviceStats.setVisible(true);
		}
	}
	
	public static Object getValueByColName(JTable table, int row, String colName) {
		int colCount = table.getColumnCount();
		Object res = null;
		for(int i = 0; i < colCount; i++) {
			if(table.getColumnName(i) == colName) {
				res = table.getValueAt(row, i);
			}
		}
		return res;
	}
	
	public static int getColByColName(JTable table, String colName)
	{
		int colCount = table.getColumnCount();
		int res = 0;
		for(int i = 0; i < colCount; i++) {
			if(table.getColumnName(i) == colName) {
				res = i;
			}
		}
		return res;
	}
	
	// method to fill JTable with Data from Database
	public static void getPersonData() {

		dtm = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Name", "Vorname", "Datum", "Ifwt", "MNaF",
				"Intern", "Beschaeftigungsverhaeltnis", "Beginn", "Ende", "Extern", "E-Mail Adresse", "Allgemeine Unterweisung", "Laboreinrichtungen", "Gefahrstoffe" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class getColumnClass(int column) { //Modified that ID will be sorted correctly
				String name = dtm.getColumnName(column);
				switch(name)
				{
					case "ID":
						return Integer.class;
					default:
						return String.class;
				}
			}
		};
		table.setModel(dtm);
		editorTable.setModel(table.getModel());
		dtm = (DefaultTableModel) table.getModel();
		table.setRowSorter(new TableRowSorter<DefaultTableModel>(dtm)); // enabling sorting of the table
		editorTable.setRowSorter(new TableRowSorter<DefaultTableModel>(dtm));
		
		try {
			conn = DBConnection.connect();

			String query = "SELECT * FROM Personen";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet resultSet = pst.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("ID");
				String name = resultSet.getString("Name");
				String vorname = resultSet.getString("Vorname");
				String datum = resultSet.getString("Datum");
				String ifwt = resultSet.getString("Ifwt");
				String manf = resultSet.getString("MNaF");
				String intern = resultSet.getString("Intern");
				String beschverh = resultSet.getString("Beschaeftigungsverhaeltnis");
				String beginn = resultSet.getString("Beginn");
				String ende = resultSet.getString("Ende");
				String extern = resultSet.getString("Extern");
				String email = resultSet.getString("E-Mail Adresse");
				String unterw = resultSet.getString("Allgemeine Unterweisung");
				String labeinr = resultSet.getString("Laboreinrichtungen");
				String gefahrst = resultSet.getString("Gefahrstoffe");

				dtm.addRow(new Object[] { id, name, vorname, datum, ifwt, manf, intern, beschverh, beginn, ende, extern,
						email,unterw,labeinr,gefahrst });
			}
			dtm.fireTableDataChanged();
			
			//jtable structure formatting
			table.getColumnModel().getColumn(0).setPreferredWidth(5);
			table.getColumnModel().getColumn(1).setPreferredWidth(65);
			table.getColumnModel().getColumn(2).setPreferredWidth(65);
			table.getColumnModel().getColumn(3).setPreferredWidth(35);
			table.getColumnModel().getColumn(4).setPreferredWidth(28);
			table.getColumnModel().getColumn(5).setPreferredWidth(28);
			table.getColumnModel().getColumn(6).setPreferredWidth(28);
			table.getColumnModel().getColumn(7).setPreferredWidth(145);
			table.getColumnModel().getColumn(8).setPreferredWidth(30);
			table.getColumnModel().getColumn(9).setPreferredWidth(30);
			table.getColumnModel().getColumn(10).setPreferredWidth(28);
			table.getColumnModel().getColumn(11).setPreferredWidth(200);
			//table.getColumnModel().getColumn(12).setMinWidth(0);
			//table.getColumnModel().getColumn(12).setMaxWidth(0);
			//table.getColumnModel().getColumn(13).setMinWidth(0);
			//table.getColumnModel().getColumn(13).setMaxWidth(0);
			//table.getColumnModel().getColumn(14).setMinWidth(0);
			//table.getColumnModel().getColumn(14).setMaxWidth(0);
			//TableColumnModel tcm = table.getColumnModel();
			//tcm.removeColumn(tcm.getColumn(12));
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(12));		// make column invisible but still accessible
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(12));		// make column invisible but still accessible
			table.getColumnModel().removeColumn(table.getColumnModel().getColumn(12));		// make column invisible but still accessible

			table.setRowHeight(20);
			
			cellRendererColor = new ColorTable();
			
			//table.setDefaultRenderer(Object.class,  cellRendererColor);

			cellRenderer = new DefaultTableCellRenderer();
			cellRenderer.setHorizontalAlignment(JLabel.CENTER);
			cellRendererColor.setHorizontalAlignment(JLabel.CENTER);
			table.getColumnModel().getColumn(3).setCellRenderer(cellRendererColor);
			table.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);
			table.getColumnModel().getColumn(5).setCellRenderer(cellRenderer);
			table.getColumnModel().getColumn(6).setCellRenderer(cellRenderer);
			table.getColumnModel().getColumn(8).setCellRenderer(cellRenderer);
			table.getColumnModel().getColumn(9).setCellRenderer(cellRenderer);
			table.getColumnModel().getColumn(10).setCellRenderer(cellRenderer);
			
			editorTable.getColumnModel().getColumn(0).setPreferredWidth(5);
			editorTable.getColumnModel().getColumn(1).setPreferredWidth(65);
			editorTable.getColumnModel().getColumn(2).setPreferredWidth(65);
			editorTable.getColumnModel().getColumn(3).setPreferredWidth(35);
			editorTable.getColumnModel().getColumn(4).setPreferredWidth(28);
			editorTable.getColumnModel().getColumn(5).setPreferredWidth(28);
			editorTable.getColumnModel().getColumn(6).setPreferredWidth(28);
			editorTable.getColumnModel().getColumn(7).setPreferredWidth(145);
			editorTable.getColumnModel().getColumn(8).setPreferredWidth(30);
			editorTable.getColumnModel().getColumn(9).setPreferredWidth(30);
			editorTable.getColumnModel().getColumn(10).setPreferredWidth(28);
			editorTable.getColumnModel().getColumn(11).setPreferredWidth(200);

			editorTable.setRowHeight(20);

			editorTable.getColumnModel().getColumn(3).setCellRenderer(cellRendererColor);
			editorTable.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);
			editorTable.getColumnModel().getColumn(5).setCellRenderer(cellRenderer);
			editorTable.getColumnModel().getColumn(6).setCellRenderer(cellRenderer);
			editorTable.getColumnModel().getColumn(8).setCellRenderer(cellRenderer);
			editorTable.getColumnModel().getColumn(9).setCellRenderer(cellRenderer);
			editorTable.getColumnModel().getColumn(10).setCellRenderer(cellRenderer);
			

			editorTable.getColumnModel().removeColumn(editorTable.getColumnModel().getColumn(12));		// make column invisible but still accessible
			editorTable.getColumnModel().removeColumn(editorTable.getColumnModel().getColumn(12));		// make column invisible but still accessible
			editorTable.getColumnModel().removeColumn(editorTable.getColumnModel().getColumn(12));		// make column invisible but still accessible

			
			pst.close();
			conn.close();

		} catch (Exception e) {
			e.getMessage();
		}

	}
	
	public static void getDeviceData() {
		deviceTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Ger\u00e4teID", "Name", "Beschreibung", "Raum"}) {
			private static final long serialVersionUID = 2L;
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class getColumnClass(int column) { //Modified that ID will be sorted correctly
				String name = deviceTableModel.getColumnName(column);
				switch(name)
				{
					case "Ger\u00e4teID":
						return Integer.class;
					default:
						return String.class;
				}
			}
		};
		
		deviceTable.setModel(deviceTableModel);
		deviceTable.setRowSorter(new TableRowSorter<DefaultTableModel>(deviceTableModel));
		
		deviceEditorTable.setModel(deviceTableModel);
		deviceEditorTable.setRowSorter(new TableRowSorter<DefaultTableModel>(deviceTableModel));
		
		/*
		for (int i=0; i < DBConnection.getGeraeteData().length; i++) {
			geraeteTableModel.addRow(new Object[] {DBConnection.getGeraeteData()[i][0],
												   DBConnection.getGeraeteData()[i][1],
												   DBConnection.getGeraeteData()[i][2],
												   DBConnection.getGeraeteData()[i][3]*/
		Object[][] data = DBConnection.getDeviceData();
		for (int i=0; i < data.length; i++) {
			deviceTableModel.addRow(new Object[] {
													data[i][0],
													data[i][1],
													data[i][2],
													data[i][3]
			});
		}
		deviceTableModel.fireTableDataChanged();
		
		deviceCellRenderer = new DefaultTableCellRenderer();
		deviceCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		deviceTable.getColumnModel().getColumn(0).setCellRenderer(deviceCellRenderer);
		deviceTable.setRowHeight(20);
		
		deviceEditorTable.getColumnModel().getColumn(0).setCellRenderer(deviceCellRenderer);
		deviceEditorTable.setRowHeight(20);
	}
	
	public static void getRoomData() {
		roomsTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Beschreibung"}) {
			private static final long serialVersionUID = 3L;
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class getColumnClass(int column) { //Modified that ID will be sorted correctly
				return String.class;
			}
		};
		
		roomsTable.setModel(roomsTableModel);
		roomsTable.setRowSorter(new TableRowSorter<DefaultTableModel>(roomsTableModel));
		roomEditorTable.setModel(roomsTableModel);
		roomEditorTable.setRowSorter(new TableRowSorter<DefaultTableModel>(roomsTableModel));
		
		Object[][] data = DBConnection.getRoomsData();
		for (int i = 0; i < data.length; i++) {
			roomsTableModel.addRow(new Object[] {
													data[i][0],
													data[i][1]
			});
		}
		roomsTableModel.fireTableDataChanged();
		
		roomsCellRenderer = new DefaultTableCellRenderer();
		roomsCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		roomsTable.getColumnModel().getColumn(0).setCellRenderer(roomsCellRenderer);
		roomsTable.setRowHeight(20);
		
	}
	
	public static void getDangerSubstData() {
		dangerSubstTableModel = new DefaultTableModel(new Object[][] {}, new String[] {"Gefahrstoffe"}) {
			private static final long serialVersionUID = 3L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		
			public Class getColumnClass(int column) {
				return String.class;
			}
		};
		
		dangerSubstTable.setModel(dangerSubstTableModel);
		dangerSubstTable.setRowSorter(new TableRowSorter<DefaultTableModel>(dangerSubstTableModel));
		dangerSubstEditorTable.setModel(dangerSubstTableModel);
		dangerSubstEditorTable.setRowSorter(new TableRowSorter<DefaultTableModel>(dangerSubstTableModel));
		
		Object[][] data = DBConnection.getGefahrstoffeData();
		for (int i = 0; i < data.length; i++) {
			dangerSubstTableModel.addRow(new Object[] {
													data[i][0]
			});
		}
		dangerSubstTableModel.fireTableDataChanged();
		
		dangerSubstCellRenderer = new DefaultTableCellRenderer();
		dangerSubstCellRenderer.setHorizontalAlignment(JLabel.LEFT);
		
		dangerSubstTable.getColumnModel().getColumn(0).setCellRenderer(dangerSubstCellRenderer);
		dangerSubstTable.setRowHeight(20);
		
	}
	
	//method to put login Window in front and grants focus to it
	/*public void loginToFront() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				login.toFront();
				login.repaint();
				login.setLocation((getWidth()/2)-(login.getWidth()/2), (getHeight()/2)-login.getHeight()/2);
			}
		});
	}*/
	
	//method that returns the tfSearch JTextField
	public static JTextField getSearchTF() {
		return tfSearch;
	}
	
	public static String getDeviceIDFilterTxt() {
		return tfDeviceID.getText();
	}

	public static void setTxtDeviceID(JTextField tfDeviceID) {
		MainFrame.tfDeviceID = tfDeviceID;
	}

	public static String getDeviceNameFilterTxt() {
		return tfDeviceName.getText();
	}

	public static void setTxtDeviceName(JTextField tfDeviceName) {
		MainFrame.tfDeviceName = tfDeviceName;
	}

	public static String getDeviceDescriptFilterTxt() {
		return tfDeviceDescript.getText();
	}

	public static void setTxtDeviceDescript(JTextField tfDeviceDescript) {
		MainFrame.tfDeviceDescript = tfDeviceDescript;
	}

	public static String getDeviceRoomFilterTxt() {
		return tfDeviceRoom.getText();
	}

	public static void setTxtDeviceRoom(JTextField tfDeviceRoom) {
		MainFrame.tfDeviceRoom = tfDeviceRoom;
	}

	public static String getRoomNameFilterTxt() {
		return tfRoomName.getText();
	}

	public static void setTfRoomName(JTextField tfRoomName) {
		MainFrame.tfRoomName = tfRoomName;
	}

	public static String getRoomDescriptFilterTxt() {
		return tfRoomDescript.getText();
	}

	public static void setTfRoomDescript(JTextField tfRoomDescript) {
		MainFrame.tfRoomDescript = tfRoomDescript;
	}
	
	public static String getDangerSubstNameFilterTxt() {
		return tfDangerSubstName.getText();
	}
}


//class to paint cells in jtable depending on instruction expiry date
class ColorTable extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	String date = null;
	int daysDiff = 0;

 public Component getTableCellRendererComponent(JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {

     super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
     	
 		date = (String) table.getModel().getValueAt(row, 3);
 		if (!date.isEmpty()) {
 			//System.out.println(row);
 			//System.out.println(date);
 			daysDiff = CalcDateDiff.date(date);		// check difference between given date and actual date in CalcDateDiff-Class
 			//System.out.println(daysDiff);
 			if (daysDiff > 168 && daysDiff < 182) {		// paint yellow if instruction is outdated in less than 2 weeks
 				setBackground(Color.yellow);
 			}
 			else if (daysDiff > 182) {			// paint red if instruction is outdated
 				setBackground(Color.red);
 			}
 			else {
 				setBackground(Color.green);		// paint green if instruction is up-to-date
 			}
 		}
 		else {
 			setBackground(Color.white);			// paint white if no expiry date is given
 		}
         
     return this;
     
 }
 
 
}
