package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import database.DBConnection;
import net.miginfocom.swing.MigLayout;

public class RoomAssignement extends JFrame {
	private static final long serialVersionUID = 1L;
	private Color frameColor;
	private Color backgroundColor;
	private Color foregroundColor;
	
	private static JTable unassignedTable;
	private static JTable assignedTable;
	private static DefaultTableModel unassignedTableModel;
	private static DefaultTableModel assignedTableModel;
	private JPanel tablePanel;
	private JPanel assignButtons;
	private static DefaultTableCellRenderer TableCellRenderer;
	private Container contentPane;
	private static RoomAssignement roomAssignement;
	private JScrollPane spUnassigned;
	private JScrollPane spAssigned;
	private static int geraeteID;
	
	private JButton btnAssign;
	private JButton btnUnassign;
	
	private RoomAssignement(int gGeraeteID) {
		geraeteID = gGeraeteID;
		frameColor = new Color(32, 32, 32);
		backgroundColor = new Color(25, 25, 25);
		foregroundColor = new Color(255, 255, 255);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Raumzuordnung");
		setBackground(frameColor);
		setForeground(foregroundColor);
		setBounds(100, 100, 1200, 750);
		contentPane = getContentPane();
		contentPane.setBackground(backgroundColor);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		//tablePanel
		tablePanel = new JPanel(new MigLayout("", "[grow][25][grow]", "[10][grow]"));
		tablePanel.setBackground(backgroundColor);
		
		//UnassignedTable
		JLabel unassignedLabel = new JLabel("Nicht zugewiesen");
		unassignedLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		unassignedLabel.setForeground(foregroundColor);
		unassignedTable = new JTable();
		unassignedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getUnassignedData(geraeteID);
		spUnassigned = new JScrollPane(unassignedTable);
		tablePanel.add(unassignedLabel, "cell 0 0, grow");
		tablePanel.add(spUnassigned, "cell 0 1, grow");
		
		//AssignedTable
		JLabel assignedLabel = new JLabel("Zugewiesen");
		assignedLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		assignedLabel.setForeground(foregroundColor);
		assignedTable = new JTable();
		assignedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getAssignedData(geraeteID);
		spAssigned = new JScrollPane(assignedTable);
		tablePanel.add(assignedLabel, "cell 2 0, grow");
		tablePanel.add(spAssigned, "cell 2 1, grow");
		
		//assign buttons
		assignButtons = new JPanel(new MigLayout("", "[grow]", "[10][10]"));
		assignButtons.setBackground(backgroundColor);
		btnAssign = new JButton(">");
		btnAssign.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				assignButtonPressed();
			}
			
		});
		assignButtons.add(btnAssign, "cell 0 0");
		
		btnUnassign = new JButton("<");
		btnUnassign.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				unassignButtonPressed();
			}
			
		});
		assignButtons.add(btnUnassign, "cell 0 1");
		tablePanel.add(assignButtons, "cell 1 1");
		
		
		contentPane.add(tablePanel, "cell 0 0, grow");
		setVisible(true);
		
	}
	
	public static RoomAssignement getInstance(int gGeraeteID)
	{
		if(roomAssignement == null)
		{
			roomAssignement = new RoomAssignement(gGeraeteID);
		}
		else
		{
			geraeteID = gGeraeteID; 
			getAssignedData(geraeteID);
			getUnassignedData(geraeteID);
		}
		return roomAssignement;
	}
	
	public static void getAssignedData(int gGeraeteID) {
		assignedTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Name","Beschreibung"}) {
			private static final long serialVersionUID = 3L;
			
			public boolean isCellEditable(int row, int column) {
					return false;
			}
		
			public Class getColumnClass(int column) {
					return String.class;
			}
		};

		assignedTable.setModel(assignedTableModel);
		assignedTable.setRowSorter(new TableRowSorter<DefaultTableModel>(assignedTableModel));
		
		Object[][] data = DBConnection.getRoomAssignedData(geraeteID);
		for (int i = 0; i < data.length; i++) {
			assignedTableModel.addRow(new Object[] {
													data[i][0]
			});
		}
		assignedTableModel.fireTableDataChanged();
		
		TableCellRenderer = new DefaultTableCellRenderer();
		TableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		assignedTable.getColumnModel().getColumn(0).setCellRenderer(TableCellRenderer);
		assignedTable.setRowHeight(20);
	}
	
	public static void getUnassignedData(int gGeraeteID) {
		unassignedTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Beschreibung"}) {
			private static final long serialVersionUID = 3L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		
			public Class getColumnClass(int column) {
				return String.class;
			}
		};
		
		unassignedTable.setModel(unassignedTableModel);
		unassignedTable.setRowSorter(new TableRowSorter<DefaultTableModel>(unassignedTableModel));
		
		Object[][] data = DBConnection.getRoomUnassignedData(geraeteID);
		for (int i = 0; i < data.length; i++) {
			unassignedTableModel.addRow(new Object[] {
													data[i][0]
			});
		}
		unassignedTableModel.fireTableDataChanged();
		
		TableCellRenderer = new DefaultTableCellRenderer();
		TableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		unassignedTable.getColumnModel().getColumn(0).setCellRenderer(TableCellRenderer);
		unassignedTable.setRowHeight(20);
	}
	
	private void assignButtonPressed() {
		int row = unassignedTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Kein Eintrag ausgew\u00e4hlt!");
			return;
		}
		String room = (String)MainFrame.getValueByColName(unassignedTable, row, "Name");
		DBConnection.assignRoom(geraeteID, room);
		getAssignedData(geraeteID);
		getUnassignedData(geraeteID);
		tablePanel.updateUI();
		MainFrame.getDeviceData();
	}
	
	private void unassignButtonPressed() {
		int row = assignedTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Kein Eintrag ausgew\u00e4hlt!");
			return;
		}
		//String room = (String)MainFrame.getValueByColName(assignedTable, row, "Raum");
		DBConnection.unassignRoom(geraeteID/*, room*/);
		getAssignedData(geraeteID);
		getUnassignedData(geraeteID);
		tablePanel.updateUI();
		MainFrame.getDeviceData();
	}
}
