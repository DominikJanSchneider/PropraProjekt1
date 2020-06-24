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
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableRowSorter;
import database.DBConnector;
import net.miginfocom.swing.MigLayout;

public class DeviceAssignement extends JFrame{
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
	private static DeviceAssignement deviceAssignement;
	private JScrollPane spUnassigned;
	private JScrollPane spAssigned;
	private static int personID;
	
	private JButton btnAssign;
	private JButton btnUnassign;
	
	private DeviceAssignement(int pPersonenID)
	{
		personID = pPersonenID;
		frameColor = new Color(32, 32, 32);
		backgroundColor = new Color(25, 25, 25);
		foregroundColor = new Color(255, 255, 255);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Gerätezuordnung");
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
		getUnassignedData(personID);
		spUnassigned = new JScrollPane(unassignedTable);
		tablePanel.add(unassignedLabel, "cell 0 0, grow");
		tablePanel.add(spUnassigned, "cell 0 1, grow");
		
		//AssignedTable
		JLabel assignedLabel = new JLabel("Zugewiesen");
		assignedLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		assignedLabel.setForeground(foregroundColor);
		assignedTable = new JTable();
		assignedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getAssignedData(personID);
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
	
	public static DeviceAssignement getInstance(int pPersonenID)
	{
		if(deviceAssignement == null)
		{
			deviceAssignement = new DeviceAssignement(pPersonenID);
		}
		else
		{
			personID = pPersonenID; 
			getAssignedData(personID);
			getUnassignedData(personID);
		}
		return deviceAssignement;
	}
	
	public static void getAssignedData(int pPersonID) {
		assignedTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Ger\u00e4teID", "Name", "Beschreibung", "Raum", "Nutzungszeit (in Stunden)"}) {
			private static final long serialVersionUID = 3L;
			
			public boolean isCellEditable(int row, int column) {
				String name = assignedTableModel.getColumnName(column);
				switch(name)
				{
					case "Nutzungszeit (in Stunden)":
						return true;
					default:
						return false;
				}
			}
		
			public Class<?> getColumnClass(int column) {
				String name = assignedTableModel.getColumnName(column);
				switch(name)
				{
					case "Ger\u00e4teID":
						return Integer.class;
					case "Nutzungszeit":
						return Double.class;
					default:
						return String.class;
				}
			}
		};
		
		CellEditorListener listener = new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				assignedTableEditingStopped();
			}
			@Override
			public void editingCanceled(ChangeEvent e) {}
		};
		
		
		
		
		assignedTable.setModel(assignedTableModel);
		assignedTable.setRowSorter(new TableRowSorter<DefaultTableModel>(assignedTableModel));
		
		Object[][] data = DBConnector.getDeviceAssignedData(personID);
		for (int i = 0; i < data.length; i++) {
			assignedTableModel.addRow(new Object[] {
													data[i][0],
													data[i][1],
													data[i][2],
													data[i][3],
													data[i][4]
			});
			int col = MainFrame.getColByColName(assignedTable, "Nutzungszeit (in Stunden)");
			TableCellEditor cellEditor = assignedTable.getCellEditor(i, col);
			cellEditor.addCellEditorListener(listener);
		}
		assignedTableModel.fireTableDataChanged();
		
		TableCellRenderer = new DefaultTableCellRenderer();
		TableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		assignedTable.getColumnModel().getColumn(0).setCellRenderer(TableCellRenderer);
		assignedTable.setRowHeight(20);
	}
	
	public static void getUnassignedData(int pPersonID) {
		unassignedTableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Ger\u00e4teID", "Name", "Beschreibung", "Raum"}) {
			private static final long serialVersionUID = 3L;
			
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		
			public Class<?> getColumnClass(int column) {
				String name = unassignedTableModel.getColumnName(column);
				switch(name)
				{
					case "Ger\u00e4teID":
						return Integer.class;
					default:
						return String.class;
				}
			}
		};
		
		unassignedTable.setModel(unassignedTableModel);
		unassignedTable.setRowSorter(new TableRowSorter<DefaultTableModel>(unassignedTableModel));
		
		Object[][] data = DBConnector.getDeviceUnassignedData(personID);
		for (int i = 0; i < data.length; i++) {
			unassignedTableModel.addRow(new Object[] {
													data[i][0],
													data[i][1],
													data[i][2],
													data[i][3]
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
		int dID = (int)MainFrame.getValueByColName(unassignedTable, row, "Ger\u00e4teID");
		DBConnector.assignDevice(dID, personID);
		getAssignedData(personID);
		getUnassignedData(personID);
		tablePanel.updateUI();
	}
	
	private void unassignButtonPressed() {
		int row = assignedTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Kein Eintrag ausgew\u00e4hlt!");
			return;
		}
		int dID = (int)MainFrame.getValueByColName(assignedTable, row, "Ger\u00e4teID");
		DBConnector.unassignDevice(dID, personID);
		getAssignedData(personID);
		getUnassignedData(personID);
		tablePanel.updateUI();
	}
	
	private static void assignedTableEditingStopped() {
		int row = assignedTable.getSelectedRow();
		int dID = (int)MainFrame.getValueByColName(assignedTable, row, "Ger\u00e4teID");
		double useTime = Double.parseDouble((String)MainFrame.getValueByColName(assignedTable, row, "Nutzungszeit (in Stunden)"));
		DBConnector.setUseTime(dID, personID, useTime);
	}
}
