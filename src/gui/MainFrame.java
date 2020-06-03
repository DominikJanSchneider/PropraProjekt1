package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
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

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Color frameColor = new Color(32, 32, 32);
	private Color backgroundColor = new Color(25, 25, 25);
	//private Color redColor = new Color(255, 0, 0);
	private Color foregroundColor = new Color(255, 255, 255);

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu printMenu;
	private JMenu editMenu;
	private JMenuItem miSave;
	private JMenuItem miImport;
	private JMenuItem miExport;
	private JPanel configPanel;
	private static JPanel tablePanel;
	private JLabel lblConfigPanel;
	private JPanel configElementsPanel;
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
	private static JScrollPane spTable;
	private static JTable table = new JTable();
	private static JTable editorTable = new JTable();
	private JPanel infoPanel;
	private JLabel lblAllgemeineUnterweisung;
	private JLabel lblLaboreinrichtungen;
	private JLabel lblGefahrstoffe;
	private JScrollPane spAllgemeineUnterweisungen;
	private JTextArea taAllgemeineUnterweisung;
	private JScrollPane spLaboreinrichtungen;
	private JTextArea taLaboreinrichtungen;
	private JScrollPane spGefahrstoffe;
	private JTextArea taGefahrstoffe;
	private static DefaultTableCellRenderer cellRenderer;
	private static DefaultTableCellRenderer cellRendererColor;
	private static Login login;
	private FormDocPrinter fPrinter;
	
	private static Connection conn = null;
	private static DefaultTableModel dtm;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
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
		setTitle("Sicherheitsunterweisung am Institut für Werkstofftechnik der Universität Siegen");
		setBackground(frameColor);
		setForeground(foregroundColor);
		setBounds(100, 100, 1200, 600);
		contentPane = new JPanel();
		contentPane.setBackground(backgroundColor);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[1200,grow]", "[150.0,grow][300.0,grow][150.0,grow]"));
		

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
		
		// Building the menu (Datenbank Laden)
		//menu = new JMenu("Datenbank Laden");
		//menu.setBackground(backgroundColor);
		//menu.setForeground(foregroundColor);
		//menuBar.add(menu);
		
		// Building the menu (Sortieren)
		//sortMenu = new JMenu("Sortieren");
		//sortMenu.setBackground(backgroundColor);
		//sortMenu.setForeground(foregroundColor);
		//menuBar.add(sortMenu);
		
		// Building the menu (Daten Bearbeiten)
		editMenu = new JMenu("Daten Bearbeiten");
		editMenu.addMenuListener(new MenuListener() {
	        @Override
	        public void menuSelected(MenuEvent e) {
	        	login = Login.getInstance();
	        	loginToFront();
	        	login.setVisible(true);
	        	//editMenu.setSelected(false);
	        }

	        @Override
	        public void menuDeselected(MenuEvent e) {}
	        @Override
	        public void menuCanceled(MenuEvent e) {}
		});
		editMenu.setBackground(backgroundColor);
		editMenu.setForeground(foregroundColor);
		menuBar.add(editMenu);
		
		// Building the menu (Drucken)
		printMenu = new JMenu("Drucken");
		printMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				printPressed();
				//printMenu.setSelected(false);
			}
			@Override
			public void menuDeselected(MenuEvent e) {}
			@Override
			public void menuCanceled(MenuEvent e) {}
		});
		printMenu.setBackground(backgroundColor);
		printMenu.setForeground(foregroundColor);
		menuBar.add(printMenu);
		
		// Adding the bar to the frame
		setJMenuBar(menuBar);
		
		// Building the panel for all elements like buttons
		configPanel = new JPanel();
		configPanel.setBackground(backgroundColor);
		configPanel.setForeground(foregroundColor);
		contentPane.add(configPanel, "cell 0 0,grow");
		configPanel.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
		
		// Label for the config panel title
		lblConfigPanel = new JLabel("Sicherheitsunterweisung am Institut für Werkstofftechnik und Gerätezentrum MNaF");
		lblConfigPanel.setFont(new Font("Dialog", Font.BOLD, 18));
		lblConfigPanel.setForeground(foregroundColor);// Building the menu (Datenbank Laden)
		//menu = new JMenu("Datenbank Laden");
		//menu.setBackground(backgroundColor);
		//menu.setForeground(foregroundColor);
		//menuBar.add(menu);
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
		tfSearch.setText("Bitte Namen eingeben");
		configElementsPanel.add(tfSearch, "cell 0 0,growx");
		tfSearch.setColumns(10);
		
		lblInstitut = new JLabel("Institut für Werkstoffstechnik (Ifwt)");
		lblInstitut.setFont(new Font("Dialog", Font.BOLD, 13));
		lblInstitut.setForeground(foregroundColor);
		configElementsPanel.add(lblInstitut, "cell 1 0");
		
		lblGeraetezentrum = new JLabel("Gerätezentrum (MNaF)");
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
		contentPane.add(tablePanel, "cell 0 1,grow");
		tablePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));

		spTable = new JScrollPane();
		tablePanel.add(spTable, "cell 0 0,grow");
		spTable.setViewportView(table);
		
		getData();
		
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
		contentPane.add(infoPanel, "cell 0 2,grow");
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
		
		//Creating a formulaPrinter
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
	
	public static JTable getEditorTable() {
		return editorTable;
	}
	
	public static DefaultTableModel getDefaultTableModel() {
		return dtm;
	}
	
	private void printPressed() {
		//getting table and textArea data
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Kein Eintrag ausgewählt!");
			return;
		}
		String familyName = (String)table.getValueAt(row, 1);
		String firstName = (String)table.getValueAt(row, 2);
		String date = (String)table.getValueAt(row, 3);
		String ifwt = (String)table.getValueAt(row, 4);
		String mnaf = (String)table.getValueAt(row, 5);
		String intern = (String)table.getValueAt(row, 6);
		String extern = (String)table.getValueAt(row, 10);
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
	
	// method to fill JTable with Data from Database
	public static void getData() {

		dtm = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Name", "Vorname", "Datum", "Ifwt", "MNaF",
				"Intern", "Beschaeftigungsverhaeltnis", "Beginn", "Ende", "Extern", "E-Mail Adresse", "Allgemeine Unterweisung", "Laboreinrichtungen", "Gefahrstoffe" }) {
			private static final long serialVersionUID = 1L;

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
	
	//method to put login Window in front and grants focus to it
	public void loginToFront() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				login.toFront();
				login.repaint();
				login.setLocation((getWidth()/2)-(login.getWidth()/2), (getHeight()/2)-login.getHeight()/2);
			}
		});
	}
	
	//method that returns the tfSearch JTextField
	public static JTextField getSearchTF() {
		return tfSearch;
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
