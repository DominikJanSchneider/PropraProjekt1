package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.table.*;
import database.DBConnector;
import net.miginfocom.swing.MigLayout;

public class DeviceStatistics extends JFrame{
	private static final long serialVersionUID = 1L;
	private Color frameColor;
	private Color backgroundColor;
	private Color foregroundColor;
	
	
	private static JTable statTable;
	private static DefaultTableModel statTableModel;
	private JPanel statTablePanel;
	private static DefaultTableCellRenderer statCellRenderer;
	private Container contentPane;
	private static DeviceStatistics deviceStats;
	private JScrollPane spStats;
	private JTextField tfSearch;
	private JPanel filterPanel;
	private JButton btnSearch;
	private static int gID;
	
	public DeviceStatistics(int pgID)
	{
		gID = pgID;
		frameColor = new Color(32, 32, 32);
		backgroundColor = new Color(25, 25, 25);
		foregroundColor = new Color(255, 255, 255);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Geräte-Statistik");
		setBackground(frameColor);
		setForeground(foregroundColor);
		setBounds(100, 100, 1200, 750);
		contentPane = getContentPane();
		contentPane.setBackground(backgroundColor);
		contentPane.setLayout(new MigLayout("", "[grow]", "[20][grow]"));
		
		//search
		filterPanel = new JPanel(new MigLayout("", "[200,grow][grow]", "[10][10]"));
		filterPanel.setBackground(backgroundColor);
		tfSearch = new JTextField();
		tfSearch.setColumns(20);
		tfSearch.setText("Bitte Namen eingeben");
		filterPanel.add(tfSearch, "cell 0 0, alignx left");
		btnSearch = new JButton("Suche Starten");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				triggerSearch();
			}
			
		});
		tfSearch.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				tfSearch.selectAll();
			}
		});
		tfSearch.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
					 triggerSearch();
				 }
			}
			public void keyReleased(KeyEvent e) {}
		});
		filterPanel.add(btnSearch, "cell 0 1, alignx left");
		contentPane.add(filterPanel, "cell 0 0, grow");
		
		//statistic table
		statTablePanel = new JPanel(new BorderLayout());
		statTablePanel.setBackground(backgroundColor);
				
		contentPane.add(statTablePanel, "cell 0 1, grow");
		statTable = new JTable();
		spStats = new JScrollPane(statTable);
		statTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		statTablePanel.add(spStats);
		
		getStatsData(gID);
		setVisible(true);
	}
	
	public static DeviceStatistics getInstance(int pgID)
	{
		if(deviceStats == null)
		{
			deviceStats = new DeviceStatistics(pgID);
		}
		else
		{
			gID = pgID; 
			getStatsData(gID);
		}
		return deviceStats;
	}
	
	public static void getStatsData(int gID) {
		statTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Name", "Vorname", "Nutzungszeit (in Stunden)"}) {
			private static final long serialVersionUID = 3L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		
			public Class<?> getColumnClass(int column) {
				String name = statTableModel.getColumnName(column);
				switch(name)
				{
					case "ID":
						return Integer.class;
					case "Nutzungszeit":
						return Double.class;
					default:
						return String.class;
				}
			}
		};
		
		statTable.setModel(statTableModel);
		statTable.setRowSorter(new TableRowSorter<DefaultTableModel>(statTableModel));
		
		Object[][] statsData = DBConnector.getDeviceStatsData(gID);
		for (int i = 0; i < statsData.length; i++) {
			statTableModel.addRow(new Object[] {
													statsData[i][0],
													statsData[i][1],
													statsData[i][2],
													statsData[i][3]
			});
		}
		statTableModel.fireTableDataChanged();
		
		statCellRenderer = new DefaultTableCellRenderer();
		statCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		statTable.getColumnModel().getColumn(0).setCellRenderer(statCellRenderer);
		statTable.setRowHeight(20);
	}
	
	public void loadFilterStats(Object[][] filteredTable) {
		statTableModel.setRowCount(0);
		
		for (int i = 0; i < filteredTable.length; i++) {
			
			statTableModel.addRow(new Object[] {
									filteredTable[i][0],
									filteredTable[i][1],
									filteredTable[i][2],
									filteredTable[i][3]});	 
		}
		
	}
	
	private void triggerSearch()
	{
		String name = tfSearch.getText();
		Object[][] filteredTable = DBConnector.getDeviceStatsFilteredData(gID, name);
		loadFilterStats(filteredTable);
	}
}
