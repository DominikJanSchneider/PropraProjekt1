package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
import net.miginfocom.swing.MigLayout;

public class MainFrame extends JFrame {
	
	private Color frameColor = new Color(32, 32, 32);
	private Color backgroundColor = new Color(25, 25, 25);
	private Color foregroundColor = new Color(255, 255, 255);

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenu menu_1;
	private JMenuItem menuItem;
	private JPanel configPanel;
	private static JPanel tablePanel;
	private JLabel lblConfigPanel;
	private JPanel configElementsPanel;
	private JButton btnStartSearch;
	private JTextField tfSearch;
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
	public static JTable table2 = new JTable();
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
	
	private static Connection conn = null;
	public static DefaultTableModel dtm;

	
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
		menu = new JMenu("Datei");
		menu.setForeground(foregroundColor);
		menuBar.add(menu);
		
		// Menu item (Datei Speichern)
		menuItem = new JMenuItem("Datei Speichern");
		menuItem.setBackground(backgroundColor);
		menuItem.setForeground(foregroundColor);
		menu.add(menuItem);
		
		// Menu item (Datei Importieren)
		menuItem = new JMenuItem("Datei Importieren");
		menuItem.setBackground(backgroundColor);
		menuItem.setForeground(foregroundColor);
		menu.add(menuItem);
		
		// Menu item (Datei Exportieren)
		menuItem = new JMenuItem("Datei Exportieren");
		menuItem.setBackground(backgroundColor);
		menuItem.setForeground(foregroundColor);
		menu.add(menuItem);
		
		// Building the menu (Datenbank Laden)
		menu = new JMenu("Datenbank Laden");
		menu.setBackground(backgroundColor);
		menu.setForeground(foregroundColor);
		menuBar.add(menu);
		
		// Building the menu (Sortieren)
		menu = new JMenu("Sortieren");
		menu.setBackground(backgroundColor);
		menu.setForeground(foregroundColor);
		menuBar.add(menu);
		
		// Building the menu (Daten Bearbeiten)
		menu_1 = new JMenu("Daten Bearbeiten");
		menu_1.addMenuListener(new MenuListener() {
	        @Override
	        public void menuSelected(MenuEvent e) {
	        	Login login = Login.getInstance();
	        }

	        @Override
	        public void menuDeselected(MenuEvent e) {
	            //System.out.println("menuDeselected");

	        }

	        @Override
	        public void menuCanceled(MenuEvent e) {
	            //System.out.println("menuCanceled");

	        }
		});
		menu_1.setBackground(backgroundColor);
		menu_1.setForeground(foregroundColor);
		menuBar.add(menu_1);
		
		// Building the menu (Drucken)
		menu = new JMenu("Drucken");
		menu.setBackground(backgroundColor);
		menu.setForeground(foregroundColor);
		menuBar.add(menu);
		
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
		lblConfigPanel.setForeground(foregroundColor);
		configPanel.add(lblConfigPanel, "cell 0 0");
		
		// Panel that holds the elements form configPanel
		configElementsPanel = new JPanel();
		configElementsPanel.setBackground(backgroundColor);
		configElementsPanel.setForeground(foregroundColor);
		configPanel.add(configElementsPanel, "cell 0 1,grow");
		configElementsPanel.setLayout(new MigLayout("", "[grow]25[grow]25[grow]25[grow]25[grow]", "[grow]8[grow]"));
		
		tfSearch = new JTextField();
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
		configElementsPanel.add(btnMnaf, "cell 2 1,aligny top");
		
		btnIntern = new JButton("Intern");
		configElementsPanel.add(btnIntern, "cell 3 1,aligny top");
		
		btnExtern = new JButton("Extern");
		configElementsPanel.add(btnExtern, "cell 4 1,aligny top");
		
		// Building the panel for the table
		tablePanel = new JPanel();
		tablePanel.setBackground(backgroundColor);
		tablePanel.setForeground(foregroundColor);
		contentPane.add(tablePanel, "cell 0 1,grow");
		tablePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		// Creating a default table model with disabled cell editing


		spTable = new JScrollPane();
		tablePanel.add(spTable, "cell 0 0,grow");
		spTable.setViewportView(table);
		
		getData();
		
		
		
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
		
		spAllgemeineUnterweisungen = new JScrollPane();
		infoPanel.add(spAllgemeineUnterweisungen, "cell 0 1,grow");
		
		taAllgemeineUnterweisung = new JTextArea();
		spAllgemeineUnterweisungen.setViewportView(taAllgemeineUnterweisung);
		
		// Building the (Laboreinrichtungen) informationen text area with title
		lblLaboreinrichtungen = new JLabel("Laboreinrichtungen");
		lblLaboreinrichtungen.setFont(new Font("Dialog", Font.BOLD, 13));
		lblLaboreinrichtungen.setForeground(foregroundColor);
		infoPanel.add(lblLaboreinrichtungen, "cell 1 0");
		
		spLaboreinrichtungen = new JScrollPane();
		infoPanel.add(spLaboreinrichtungen, "cell 1 1,grow");
		
		taLaboreinrichtungen = new JTextArea();
		spLaboreinrichtungen.setViewportView(taLaboreinrichtungen);
		
		// Building the (Gefahrstoffe) information text area with title
		lblGefahrstoffe = new JLabel("Gefahrstoffe");
		lblGefahrstoffe.setFont(new Font("Dialog", Font.BOLD, 13));
		lblGefahrstoffe.setForeground(foregroundColor);
		infoPanel.add(lblGefahrstoffe, "cell 2 0");
		
		spGefahrstoffe = new JScrollPane();
		infoPanel.add(spGefahrstoffe, "cell 2 1,grow");
		
		taGefahrstoffe = new JTextArea();
		spGefahrstoffe.setViewportView(taGefahrstoffe);
	}
	
	public void loadFilter(String[][] filteredTable) {
		dtm.setRowCount(0);
		for (int i = 0; i < filteredTable.length; i++) {
			dtm.addRow(new String[] {filteredTable[i][0],
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
									 filteredTable[i][11]});
		}
	}
	
	public static DefaultTableModel getDefaultTableModel() {
		return dtm;
	}
	
	
	// method to fill JTable with Data from Database
	public static void getData() {

		dtm = new DefaultTableModel(new Object[][] {}, new Object[] { "ID", "Name", "Vorname", "Datum", "Ifwt", "MNaF",
				"Intern", "Beschaeftigungsverhaeltnis", "Beginn", "Ende", "Extern", "E-Mail Adresse" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			

	            public Class getColumnClass(int column)
	            {
	                // Lookup first non-null data on column
	                for (int row = 0; row < getRowCount(); row++) 
	                {
	                    Object o = getValueAt(row, column);

	                    if (o != null)
	                        return o.getClass();
	                }

	                return Object.class;
	            }
	        
		};
		//beide Varianten möglich
		table.setModel(dtm);
		table2.setModel(table.getModel());
		dtm = (DefaultTableModel) table.getModel();
		table.setRowSorter(new TableRowSorter<DefaultTableModel> (dtm));
		//table.setAutoCreateRowSorter(true);
		//table2.setAutoCreateRowSorter(true);
			//von Fiti: die Tabelle ist schonmal mit der Datenbank verbunden.
			//wir müssen halt noch schauen ob sie direkt geladen werden soll, oder auf Befehl des Users
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

				dtm.addRow(new Object[] { id, name, vorname, datum, ifwt, manf, intern, beschverh, beginn, ende, extern,
						email });
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

			table.setRowHeight(20);

			cellRenderer = new DefaultTableCellRenderer();
			cellRenderer.setHorizontalAlignment(JLabel.CENTER);
			table.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
			table.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);
			table.getColumnModel().getColumn(5).setCellRenderer(cellRenderer);
			table.getColumnModel().getColumn(6).setCellRenderer(cellRenderer);
			table.getColumnModel().getColumn(8).setCellRenderer(cellRenderer);
			table.getColumnModel().getColumn(9).setCellRenderer(cellRenderer);
			table.getColumnModel().getColumn(10).setCellRenderer(cellRenderer);
			
			table2.getColumnModel().getColumn(0).setPreferredWidth(5);
			table2.getColumnModel().getColumn(1).setPreferredWidth(65);
			table2.getColumnModel().getColumn(2).setPreferredWidth(65);
			table2.getColumnModel().getColumn(3).setPreferredWidth(35);
			table2.getColumnModel().getColumn(4).setPreferredWidth(28);
			table2.getColumnModel().getColumn(5).setPreferredWidth(28);
			table2.getColumnModel().getColumn(6).setPreferredWidth(28);
			table2.getColumnModel().getColumn(7).setPreferredWidth(145);
			table2.getColumnModel().getColumn(8).setPreferredWidth(30);
			table2.getColumnModel().getColumn(9).setPreferredWidth(30);
			table2.getColumnModel().getColumn(10).setPreferredWidth(28);
			table2.getColumnModel().getColumn(11).setPreferredWidth(200);

			table2.setRowHeight(20);

			cellRenderer = new DefaultTableCellRenderer();
			cellRenderer.setHorizontalAlignment(JLabel.CENTER);
			table2.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
			table2.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);
			table2.getColumnModel().getColumn(5).setCellRenderer(cellRenderer);
			table2.getColumnModel().getColumn(6).setCellRenderer(cellRenderer);
			table2.getColumnModel().getColumn(8).setCellRenderer(cellRenderer);
			table2.getColumnModel().getColumn(9).setCellRenderer(cellRenderer);
			table2.getColumnModel().getColumn(10).setCellRenderer(cellRenderer);

			
			pst.close();
			conn.close();

		} catch (Exception e) {
			e.getMessage();
		}

	}
	
}
