package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import database.DBConnection;
import net.miginfocom.swing.MigLayout;

public class UserEditor extends JFrame {

	private static UserEditor userEditor = new UserEditor();
	
	private Color frameColor = new Color(32, 32, 32);
	private Color backgroundColor = new Color(25, 25, 25);
	private Color foregroundColor = new Color(255, 255, 255);
	
	private JPanel contentPane;
	private JPanel elementPanel;
	private JPanel userTablePanel;
	
	private JScrollPane spUserTable;
	
	private JTable userTable = new JTable();
	
	private DefaultTableModel userTableModel;
	
	private DefaultTableCellRenderer userCellRenderer;
	
	private JLabel lblBearbeitungselemente;
	
	private JButton btnRefresh;
	
	
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
		contentPane.setLayout(new MigLayout("", "[grow]", "[65.0][300.0,grow][170.0,grow]"));
		
		//Panel for the editing elements
		elementPanel = new JPanel();
		elementPanel.setBackground(backgroundColor);
		elementPanel.setForeground(foregroundColor);
		contentPane.add(elementPanel, "cell 0 0, grow");
		elementPanel.setLayout(new MigLayout("", "[][]","[]10[]"));
		
		lblBearbeitungselemente = new JLabel("Bearbeitungselemente");
		lblBearbeitungselemente.setFont(new Font("Dialog", Font.BOLD, 14));
		lblBearbeitungselemente.setForeground(foregroundColor);
		elementPanel.add(lblBearbeitungselemente, "cell 0 0");
	
		//Refresh button
		btnRefresh = new JButton("Aktualisieren");
		ImageIcon refreshIcon = new ImageIcon(UserEditor.class.getResource("/images/refresh.png"));
		btnRefresh.setIcon(refreshIcon);
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getBenutzerData();
			}
		});
		elementPanel.add(btnRefresh, "cell 0 1");
		
		//Panel for the user table
		userTablePanel = new JPanel();
		userTablePanel.setBackground(backgroundColor);
		userTablePanel.setForeground(foregroundColor);
		contentPane.add(userTablePanel, "cell 0 1, grow");
		userTablePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		spUserTable = new JScrollPane();
		userTablePanel.add(spUserTable, "cell 0 0, grow");
		spUserTable.setViewportView(userTable);
		
		getBenutzerData();
	}
	
	public void getBenutzerData() {
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
		
		for (int i = 0; i < DBConnection.getUserData().length; i++) {
			userTableModel.addRow(new Object[] {DBConnection.getUserData()[i][0],
												DBConnection.getUserData()[i][1],
												DBConnection.getUserData()[i][2]
					
			});
		}
		userTableModel.fireTableDataChanged();
		
		userCellRenderer = new DefaultTableCellRenderer();
		userCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		
		userTable.getColumnModel().getColumn(0).setCellRenderer(userCellRenderer);
		userTable.setRowHeight(20);
	}

}
