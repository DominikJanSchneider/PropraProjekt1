package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
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
	private JPanel personenTab;
	private JPanel configPanel;
	private JPanel configElementsPanel;
	private static JPanel tablePanel;
	private JPanel infoPanel;
	private JPanel geraeteTab;
	private static JPanel geraeteTablePanel;
	private JPanel raeumeTab;
	private static JPanel raeumeTablePanel;
	private JPanel gefahrstoffeTab;
	private static JPanel gefahrstoffeTablePanel;
	
	private static JScrollPane spTable;
	private JScrollPane spAllgemeineUnterweisungen;
	private JScrollPane spLaboreinrichtungen;
	private JScrollPane spGefahrstoffe;
	private JScrollPane spGeraete;
	private JScrollPane spRaeume;
	private JScrollPane spGefahrstoffeTab;
	
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
	private JLabel lblGeraetezentrum;
	private JButton btnMnaf;
	private JLabel lblIntern;
	private JButton btnIntern;
	private JLabel lblExtern;
	private JButton btnExtern;
	private JButton btnEditUser;
	
	private static JTable table = new JTable();
	private static JTable editorTable = new JTable();
	private static JTable geraeteEditorTable = new JTable();
	private static JTable geraeteTable = new JTable();
	private static JTable raeumeEditorTable = new JTable();
	private static JTable raeumeTable = new JTable();
	private static JTable gefahrstoffeEditorTable = new JTable();
	private static JTable gefahrstoffeTable = new JTable();
	
	
	private static DefaultTableModel dtm;
	private static DefaultTableModel geraeteTableModel;
	private static DefaultTableModel raeumeTableModel;
	private static DefaultTableModel gefahrstoffeTableModel;
	
	private static DefaultTableCellRenderer cellRenderer;
	private static DefaultTableCellRenderer cellRendererColor;
	private static DefaultTableCellRenderer geraeteCellRenderer;
	private static DefaultTableCellRenderer raeumeCellRenderer;
	private static DefaultTableCellRenderer gefahrstoffeCellRenderer;
	
	private JLabel lblAllgemeineUnterweisung;
	private JLabel lblLaboreinrichtungen;
	private JLabel lblGefahrstoffe;
	
	private JTextArea taAllgemeineUnterweisung;
	private JTextArea taLaboreinrichtungen;
	private JTextArea taGefahrstoffe;
	
	private static Login login;
	private static DataEditor dataEditor;
	private static UserEditor userEditor;
	private static DeviceStatistics deviceStats;
	private FormDocPrinter fPrinter;
	
	private static Connection conn = null;
	private JPanel filterPanelGeraeteTab;
	private JPanel filterPanelRaeumeTab;
	private JPanel filterPanelGefahrstoffeTab;
	private JButton btnSearchGeraeteID;
	private JButton btnSearchGeraeteName;
	private JButton btnSearchGeraeteDescript;
	private JButton btnSearchGeraeteraum;
	private static JTextField tfRaumName;
	private static JTextField tfRaumDescript;
	private JButton btnSearchRaumName;
	private JButton btnSearchRaumDescript;
	private static JTextField tfGeraeteID;
	private static JTextField tfGeraeteName;
	private static JTextField tfGeraeteDescript;
	private static JTextField tfGeraeteraum;
	private JButton btnAllGeraete;
	private JButton btnAllRaeume;
	private JButton btnDeviceStats;
	private static JTextField tfGefahrstoffName;
	private JButton searchButtonGefahrstoffName;
	
	
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
		personenTab = new JPanel();
		personenTab.setBackground(backgroundColor);
		personenTab.setLayout(new MigLayout("", "[1200,grow]", "[150.0,grow][300.0,grow][150.0,grow]"));
				
		tabbedPane.addTab("Personen", personenTab);
		// Building the panel for all elements like buttons
		configPanel = new JPanel();
		configPanel.setBackground(backgroundColor);
		configPanel.setForeground(foregroundColor);
		personenTab.add(configPanel, "cell 0 0,grow");
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
				
		lblGeraetezentrum = new JLabel("Ger\u00e4tezentrum (MNaF)");
		lblGeraetezentrum.setFont(new Font("Dialog", Font.BOLD, 13));
		lblGeraetezentrum.setForeground(foregroundColor);
		configElementsPanel.add(lblGeraetezentrum, "cell 2 0");
				
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
		personenTab.add(tablePanel, "cell 0 1,grow");
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
				taAllgemeineUnterweisung.setText(fillInstr);
				taLaboreinrichtungen.setText(fillLab);
				taGefahrstoffe.setText(fillHazard);
			}
		});
				
		// Building the panel for the informations that will be displayed
		infoPanel = new JPanel();
		infoPanel.setBackground(backgroundColor);
		infoPanel.setForeground(foregroundColor);
		personenTab.add(infoPanel, "cell 0 2,grow");
		infoPanel.setLayout(new MigLayout("", "[grow]25[grow]25[grow]", "[][grow]"));
				
		// Building the (Allgemeine Unterweisung) information text area with title
		lblAllgemeineUnterweisung = new JLabel("Allgemeine Unterweisung (Datum s.o.)");
		lblAllgemeineUnterweisung.setFont(new Font("Dialog", Font.BOLD, 13));
		lblAllgemeineUnterweisung.setForeground(foregroundColor);
		infoPanel.add(lblAllgemeineUnterweisung, "cell 0 0");
				
		spAllgemeineUnterweisungen = new JScrollPane(
			          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			          JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		infoPanel.add(spAllgemeineUnterweisungen, "width 25%, cell 0 1,grow");
				
		taAllgemeineUnterweisung = new JTextArea();
		taAllgemeineUnterweisung.setLineWrap(true);
		taAllgemeineUnterweisung.setWrapStyleWord(true);
		taAllgemeineUnterweisung.setEditable(false);
		//taAllgemeineUnterweisung.setBackground(redColor);
		spAllgemeineUnterweisungen.setViewportView(taAllgemeineUnterweisung);
				
		// Building the (Laboreinrichtungen) informationen text area with title
		lblLaboreinrichtungen = new JLabel("Laboreinrichtungen");
		lblLaboreinrichtungen.setFont(new Font("Dialog", Font.BOLD, 13));
		lblLaboreinrichtungen.setForeground(foregroundColor);
		infoPanel.add(lblLaboreinrichtungen, "cell 1 0");
				
		spLaboreinrichtungen = new JScrollPane(
			          JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			          JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		infoPanel.add(spLaboreinrichtungen, "width 25%, cell 1 1,grow");
				
		taLaboreinrichtungen = new JTextArea();
		taLaboreinrichtungen.setLineWrap(true);
		taLaboreinrichtungen.setWrapStyleWord(true);
		taLaboreinrichtungen.setEditable(false);
		spLaboreinrichtungen.setViewportView(taLaboreinrichtungen);
				
		// Building the (Gefahrstoffe) information text area with title
		lblGefahrstoffe = new JLabel("Gefahrstoffe");
		lblGefahrstoffe.setFont(new Font("Dialog", Font.BOLD, 13));
		lblGefahrstoffe.setForeground(foregroundColor);
		infoPanel.add(lblGefahrstoffe, "cell 2 0");
				
		spGefahrstoffe = new JScrollPane(
			            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		infoPanel.add(spGefahrstoffe, "width 25%, cell 2 1,grow");
				
		taGefahrstoffe = new JTextArea();
		taGefahrstoffe.setLineWrap(true);
		taGefahrstoffe.setWrapStyleWord(true);
		taGefahrstoffe.setEditable(false);
		spGefahrstoffe.setViewportView(taGefahrstoffe);
				
		taGefahrstoffe.setLineWrap(true);
		taGefahrstoffe.setWrapStyleWord(true);
		taGefahrstoffe.setEditable(false);
		spGefahrstoffe.setViewportView(taGefahrstoffe);
				
		getGeraeteData();
	
		//###Tab Geraete###
		geraeteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		geraeteTab = new JPanel();
		geraeteTab.setBackground(backgroundColor);
		geraeteTab.setLayout(new MigLayout("", "[1200,grow]", "[150.0,grow][450.0,grow][]"));
		tabbedPane.addTab("Ger\u00e4te", geraeteTab);
		
		
		filterPanelGeraeteTab = new JPanel();
		filterPanelGeraeteTab.setBackground(backgroundColor);
		filterPanelGeraeteTab.setForeground(foregroundColor);
		geraeteTab.add(filterPanelGeraeteTab, "cell 0 0,grow");
		filterPanelGeraeteTab.setLayout(new MigLayout("", "[300,grow][300,grow][300,grow][300,grow]", "[][grow][][grow]"));
		
		tfGeraeteID = new JTextField();
		tfGeraeteID.setText("Bitte Geräte-ID eingeben");
		filterPanelGeraeteTab.add(tfGeraeteID, "cell 0 0,growx");
		tfGeraeteID.setColumns(10);
		tfGeraeteID.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfGeraeteID.selectAll();
			}
		});
		tfGeraeteID.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerGeraeteIDSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		tfGeraeteName = new JTextField();
		tfGeraeteName.setText("Bitte Gerätenamen eingeben");
		filterPanelGeraeteTab.add(tfGeraeteName, "cell 1 0,growx");
		tfGeraeteName.setColumns(10);
		tfGeraeteName.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfGeraeteName.selectAll();
			}
		});
		tfGeraeteName.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerGeraeteNameSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		tfGeraeteDescript = new JTextField();
		tfGeraeteDescript.setText("Bitte Gerätebeschreibung eingeben");
		filterPanelGeraeteTab.add(tfGeraeteDescript, "cell 2 0,growx");
		tfGeraeteDescript.setColumns(10);
		tfGeraeteDescript.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfGeraeteDescript.selectAll();
			}
		});
		tfGeraeteDescript.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerGeraeteDescriptSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		tfGeraeteraum = new JTextField();
		tfGeraeteraum.setText("Bitte Geräteraum eingeben");
		filterPanelGeraeteTab.add(tfGeraeteraum, "cell 3 0,growx");
		tfGeraeteraum.setColumns(10);
		tfGeraeteraum.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfGeraeteraum.selectAll();
			}
		});
		tfGeraeteraum.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerGeraeteraumSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		btnSearchGeraeteID = new JButton("Geräte-ID suchen");
		btnSearchGeraeteID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerGeraeteIDSearch();
			}
		});
		
		filterPanelGeraeteTab.add(btnSearchGeraeteID, "cell 0 2,alignx left");
		
		btnSearchGeraeteName = new JButton("Gerätenamen suchen");
		btnSearchGeraeteName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerGeraeteNameSearch();
			}
		});
		filterPanelGeraeteTab.add(btnSearchGeraeteName, "cell 1 2,alignx left");
		
		btnSearchGeraeteDescript = new JButton("Beschreibung suchen");
		btnSearchGeraeteDescript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerGeraeteDescriptSearch();
			}
		});
		filterPanelGeraeteTab.add(btnSearchGeraeteDescript, "cell 2 2");
		
		
		
		btnSearchGeraeteraum = new JButton("Raum suchen");
		btnSearchGeraeteraum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerGeraeteraumSearch();
			}
		});
		filterPanelGeraeteTab.add(btnSearchGeraeteraum, "cell 3 2");
		
		geraeteTablePanel = new JPanel();
		geraeteTablePanel.setBackground(backgroundColor);
		geraeteTab.add(geraeteTablePanel, "cell 0 1, grow");
		geraeteTablePanel.setLayout(new MigLayout("", "[1200,grow]", "[grow][grow][][][]"));
		
		spGeraete = new JScrollPane();
		geraeteTablePanel.add(spGeraete, "cell 0 0,grow");
		spGeraete.setViewportView(geraeteTable);
		
		btnAllGeraete = new JButton("Alle Geräte anzeigen");
		btnAllGeraete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFilterGeraete(DBConnection.getGeraeteData());
			}
		});
		geraeteTablePanel.add(btnAllGeraete, "cell 0 3");
		
		btnDeviceStats = new JButton("Gerätestatistik");
		btnDeviceStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deviceStatsButtonPressed();
			}
		});
		geraeteTablePanel.add(btnDeviceStats, "cell 0 3");
		
		
		
		//###Tab Raeume###
		raeumeTab = new JPanel();
		raeumeTab.setBackground(backgroundColor);
		raeumeTab.setLayout(new MigLayout("", "[1200,grow]", "[150.0,grow][450.0,grow]"));
		tabbedPane.addTab("R\u00e4ume", raeumeTab);
		
		filterPanelRaeumeTab = new JPanel();
		filterPanelRaeumeTab.setBackground(backgroundColor);
		filterPanelRaeumeTab.setForeground(foregroundColor);
		filterPanelRaeumeTab.setLayout(new MigLayout("", "[400,grow][400,grow][400,grow]", "[grow][][][grow]"));
		raeumeTab.add(filterPanelRaeumeTab, "cell 0 0,grow");
		
		tfRaumName = new JTextField();
		tfRaumName.setText("Bitte Raumnamen eingeben");
		filterPanelRaeumeTab.add(tfRaumName, "cell 0 1,growx");
		tfRaumName.setColumns(10);
		tfRaumName.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfRaumName.selectAll();
			}
		});
		tfRaumName.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerRaumNameSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		tfRaumDescript = new JTextField();
		tfRaumDescript.setText("Bitte Beschreibung eingeben");
		filterPanelRaeumeTab.add(tfRaumDescript, "cell 1 1,growx");
		tfRaumDescript.setColumns(10);
		tfRaumDescript.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfRaumDescript.selectAll();
			}
		});
		tfRaumDescript.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerRaumDescriptSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		
		btnSearchRaumName = new JButton("Raumnamen suchen");
		btnSearchRaumName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerRaumNameSearch();
			}
		});
		filterPanelRaeumeTab.add(btnSearchRaumName, "cell 0 2");
		
		btnSearchRaumDescript = new JButton("Beschreibung suchen");
		btnSearchRaumDescript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerRaumDescriptSearch();
			}
		});
		filterPanelRaeumeTab.add(btnSearchRaumDescript, "cell 1 2");
		
		raeumeTablePanel = new JPanel();
		raeumeTablePanel.setBackground(backgroundColor);
		raeumeTab.add(raeumeTablePanel, "cell 0 1, grow");
		raeumeTablePanel.setLayout(new MigLayout("", "[1200,grow]", "[grow][][]"));
		
		spRaeume = new JScrollPane();
		raeumeTablePanel.add(spRaeume, "cell 0 1, grow");
		spRaeume.setViewportView(raeumeTable);
		
		btnAllRaeume = new JButton("Alle Räume anzeigen");
		btnAllRaeume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFilterRaeume(DBConnection.getRaeumeData());
			}
		});
		raeumeTablePanel.add(btnAllRaeume, "cell 0 2");
		
		getRaeumeData();
		
		//###Tab Gefahrstoffe###
		gefahrstoffeTab = new JPanel();
		gefahrstoffeTab.setBackground(backgroundColor);
		gefahrstoffeTab.setLayout(new MigLayout("", "[1200,grow]", "[150.0,grow][450.0,grow][]"));
		tabbedPane.addTab("Gefahrstoffe", gefahrstoffeTab);
				
		filterPanelGefahrstoffeTab = new JPanel();
		filterPanelGefahrstoffeTab.setBackground(backgroundColor);
		filterPanelGefahrstoffeTab.setForeground(foregroundColor);
		filterPanelGefahrstoffeTab.setLayout(new MigLayout("", "[400,grow][400,grow][400,grow]", "[grow][][][grow]"));
		gefahrstoffeTab.add(filterPanelGefahrstoffeTab, "cell 0 0,grow");

		tfGefahrstoffName = new JTextField();
		tfGefahrstoffName.setText("Bitte Gefahrstoffnamen eingeben");
		tfGefahrstoffName.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfGefahrstoffName.selectAll();
			}
		});
		tfGefahrstoffName.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					triggerGefahrstoffNameSearch();
					}
			}
			public void keyReleased(KeyEvent e) {}
		});
		filterPanelGefahrstoffeTab.add(tfGefahrstoffName, "cell 0 0,growx");
		tfGefahrstoffName.setColumns(10);
	
		searchButtonGefahrstoffName = new JButton("Gefahrstoffenamen suchen");
		searchButtonGefahrstoffName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						triggerGefahrstoffNameSearch();
			}
		});
		filterPanelGefahrstoffeTab.add(searchButtonGefahrstoffName, "cell 0 2,alignx left");
				
		gefahrstoffeTablePanel = new JPanel();
		gefahrstoffeTablePanel.setBackground(backgroundColor);
		gefahrstoffeTab.add(gefahrstoffeTablePanel, "cell 0 1, grow");
		gefahrstoffeTablePanel.setLayout(new MigLayout("", "[1200,grow]", "[grow][grow][][][]"));
				
		spGefahrstoffeTab = new JScrollPane();
		gefahrstoffeTablePanel.add(spGefahrstoffeTab, "cell 0 0,grow");
		spGefahrstoffeTab.setViewportView(gefahrstoffeTable);
				
		getGefahrstoffeData();

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
	
	public void loadFilterGeraete(Object[][] filteredTable) {
		geraeteTableModel.setRowCount(0);
		
		for (int i = 0; i < filteredTable.length; i++) {
			
			geraeteTableModel.addRow(new Object[] {
									filteredTable[i][0],
									filteredTable[i][1],
									filteredTable[i][2],
									filteredTable[i][3]});	 
		}
		
	}
	
	public void loadFilterRaeume(Object[][] filteredTable) {
		raeumeTableModel.setRowCount(0);
		
		for (int i = 0; i < filteredTable.length; i++) {
			
			raeumeTableModel.addRow(new Object[] {
									filteredTable[i][0],
									filteredTable[i][1]});			 
		}
		
	}
	
	public void loadFilterGefahrstoffe(Object[][] filteredTable) {
		gefahrstoffeTableModel.setRowCount(0);
		
		for (int i = 0; i < filteredTable.length; i++) {
			
			gefahrstoffeTableModel.addRow(new Object[] {
									filteredTable[i][0]});			 
		}
	}
	
	public static JTable getEditorTable() {
		return editorTable;
	}
	
	public static DefaultTableModel getDefaultTableModel() {
		return dtm;
	}
	
	public static JTable getGeraeteEditorTable() {
		return geraeteEditorTable;
	}
	
	public static DefaultTableModel getGeraeteTableModel() {
		return geraeteTableModel;
	}
	
	public static JTable getRaeumeEditorTable() {
		return raeumeEditorTable;
	}
	
	public static JTable getGefahrstoffeEditorTable() {
		return gefahrstoffeEditorTable;
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
		String genInstr = taAllgemeineUnterweisung.getText();
		String labSetup = taLaboreinrichtungen.getText();
		String dangerSubst = taGefahrstoffe.getText();
		
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
	
	private void triggerGeraeteIDSearch()
	{
		String id = getGeraeteIDFilterTxt();
		loadFilterGeraete(DBConnection.getGeraeteByID(id));
	}
	
	private void triggerGeraeteNameSearch()
	{
		String name = getGeraeteNameFilterTxt();
		loadFilterGeraete(DBConnection.getGeraeteByName(name));
	}
	
	private void triggerGeraeteDescriptSearch()
	{
		String descript = getGeraeteDescriptFilterTxt();
		loadFilterRaeume(DBConnection.getGeraeteByDescript(descript));
	}
	
	private void triggerGeraeteraumSearch()
	{
		String raum = getGeraeteraumFilterTxt();
		loadFilterGeraete(DBConnection.getGeraeteByRaum(raum));
	}
	
	private void triggerRaumNameSearch()
	{
		String name = getRaumNameFilterTxt();
		loadFilterRaeume(DBConnection.getRaeumeByName(name));
	}
	
	private void triggerRaumDescriptSearch()
	{
		String descript = getRaumDescriptFilterTxt();
		loadFilterRaeume(DBConnection.getRaeumeByDescript(descript));
	}
	
	private void triggerGefahrstoffNameSearch()
	{
		String name = getGefahrstoffNameFilterTxt();
		loadFilterGefahrstoffe(DBConnection.getGefahrstoffByName(name));
	}
	
	private void deviceStatsButtonPressed() {
		int row = geraeteTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Kein Eintrag ausgew\u00e4hlt!");
			return;
		}
		int gID = (int)getValueByColName(geraeteTable, row, "Ger\u00e4teID");
		deviceStats = DeviceStatistics.getInstance(gID);
		deviceStats.setVisible(true);
	}
	
	private Object getValueByColName(JTable table, int row, String colName) {
		int colCount = table.getColumnCount();
		Object res = null;
		for(int i = 0; i < colCount; i++) {
			if(table.getColumnName(i) == colName) {
				res = table.getValueAt(row, i);
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
	
	public static void getGeraeteData() {
		geraeteTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Ger\u00e4teID", "Name", "Beschreibung", "Raum"}) {
			private static final long serialVersionUID = 2L;
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class getColumnClass(int column) { //Modified that ID will be sorted correctly
				String name = geraeteTableModel.getColumnName(column);
				switch(name)
				{
					case "Ger\u00e4teID":
						return Integer.class;
					default:
						return String.class;
				}
			}
		};
		
		geraeteTable.setModel(geraeteTableModel);
		geraeteTable.setRowSorter(new TableRowSorter<DefaultTableModel>(geraeteTableModel));
		
		geraeteEditorTable.setModel(geraeteTableModel);
		geraeteEditorTable.setRowSorter(new TableRowSorter<DefaultTableModel>(geraeteTableModel));
		
		/*
		for (int i=0; i < DBConnection.getGeraeteData().length; i++) {
			geraeteTableModel.addRow(new Object[] {DBConnection.getGeraeteData()[i][0],
												   DBConnection.getGeraeteData()[i][1],
												   DBConnection.getGeraeteData()[i][2],
												   DBConnection.getGeraeteData()[i][3]*/
		Object[][] data = DBConnection.getGeraeteData();
		for (int i=0; i < data.length; i++) {
			geraeteTableModel.addRow(new Object[] {
													data[i][0],
													data[i][1],
													data[i][2],
													data[i][3]
			});
		}
		geraeteTableModel.fireTableDataChanged();
		
		geraeteCellRenderer = new DefaultTableCellRenderer();
		geraeteCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		geraeteTable.getColumnModel().getColumn(0).setCellRenderer(geraeteCellRenderer);
		geraeteTable.setRowHeight(20);
		
		geraeteEditorTable.getColumnModel().getColumn(0).setCellRenderer(geraeteCellRenderer);
		geraeteEditorTable.setRowHeight(20);
	}
	
	public static void getRaeumeData() {
		raeumeTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Beschreibung"}) {
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
		
		raeumeTable.setModel(raeumeTableModel);
		raeumeTable.setRowSorter(new TableRowSorter<DefaultTableModel>(raeumeTableModel));
		raeumeEditorTable.setModel(raeumeTableModel);
		raeumeEditorTable.setRowSorter(new TableRowSorter<DefaultTableModel>(raeumeTableModel));
		
		Object[][] data = DBConnection.getRaeumeData();
		for (int i = 0; i < data.length; i++) {
			raeumeTableModel.addRow(new Object[] {
													data[i][0],
													data[i][1]
			});
		}
		raeumeTableModel.fireTableDataChanged();
		
		raeumeCellRenderer = new DefaultTableCellRenderer();
		raeumeCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		raeumeTable.getColumnModel().getColumn(0).setCellRenderer(raeumeCellRenderer);
		raeumeTable.setRowHeight(20);
		
	}
	
	public static void getGefahrstoffeData() {
		gefahrstoffeTableModel = new DefaultTableModel(new Object[][] {}, new String[] {"Gefahrstoffe"}) {
			private static final long serialVersionUID = 3L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		
			public Class getColumnClass(int column) {
				return String.class;
			}
		};
		
		gefahrstoffeTable.setModel(gefahrstoffeTableModel);
		gefahrstoffeTable.setRowSorter(new TableRowSorter<DefaultTableModel>(gefahrstoffeTableModel));
		gefahrstoffeEditorTable.setModel(gefahrstoffeTableModel);
		gefahrstoffeEditorTable.setRowSorter(new TableRowSorter<DefaultTableModel>(gefahrstoffeTableModel));
		
		Object[][] data = DBConnection.getGefahrstoffeData();
		for (int i = 0; i < data.length; i++) {
			gefahrstoffeTableModel.addRow(new Object[] {
													data[i][0]
			});
		}
		gefahrstoffeTableModel.fireTableDataChanged();
		
		gefahrstoffeCellRenderer = new DefaultTableCellRenderer();
		gefahrstoffeCellRenderer.setHorizontalAlignment(JLabel.LEFT);
		
		gefahrstoffeTable.getColumnModel().getColumn(0).setCellRenderer(gefahrstoffeCellRenderer);
		gefahrstoffeTable.setRowHeight(20);
		
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
	
	public static String getGeraeteIDFilterTxt() {
		return tfGeraeteID.getText();
	}

	public static void setTxtGeraeteID(JTextField tfGeraeteID) {
		MainFrame.tfGeraeteID = tfGeraeteID;
	}

	public static String getGeraeteNameFilterTxt() {
		return tfGeraeteName.getText();
	}

	public static void setTxtGeraeteName(JTextField tfGeraeteName) {
		MainFrame.tfGeraeteName = tfGeraeteName;
	}

	public static String getGeraeteDescriptFilterTxt() {
		return tfGeraeteDescript.getText();
	}

	public static void setTxtGeraeteDescript(JTextField tfGeraeteDescript) {
		MainFrame.tfGeraeteDescript = tfGeraeteDescript;
	}

	public static String getGeraeteraumFilterTxt() {
		return tfGeraeteraum.getText();
	}

	public static void setTxtGeraeteraum(JTextField tfGeraeteraum) {
		MainFrame.tfGeraeteraum = tfGeraeteraum;
	}

	public static String getRaumNameFilterTxt() {
		return tfRaumName.getText();
	}

	public static void setTfRaumName(JTextField tfRaumName) {
		MainFrame.tfRaumName = tfRaumName;
	}

	public static String getRaumDescriptFilterTxt() {
		return tfRaumDescript.getText();
	}

	public static void setTfRaumDescript(JTextField tfRaumDescript) {
		MainFrame.tfRaumDescript = tfRaumDescript;
	}
	
	public static String getGefahrstoffNameFilterTxt() {
		return tfGefahrstoffName.getText();
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
