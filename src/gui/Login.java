package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.DBConnection;
import net.miginfocom.swing.MigLayout;

public class Login extends JDialog {

	private static final long serialVersionUID = 1L;

	private static Login login = new Login();
	
	private JPanel contentPanel = new JPanel();
	private JPanel buttonPanel;
	private JLabel lblPasswort;
	private JLabel lblBenutzername;
	private static JTextField tfBenutzername;
	private static JPasswordField pfPasswort;
	private JButton btnLogin;
	//private static boolean valid;
	
	private Color frameColor = new Color(32, 32, 32);
	private Color backgroundColor = new Color(25, 25, 25);
	private Color foregroundColor = new Color(255, 255, 255);
	
	public static Login getInstance() {
		tfBenutzername.setText("");
		pfPasswort.setText("");
		return login;
	}

	// Creating the dialog.
	private Login() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				
				System.exit(0);
			}
		});
		setTitle("Admin Login");
		setBounds(100, 100, 450, 140);
		setBackground(frameColor);
		setForeground(foregroundColor);
		setLocationRelativeTo(null); //Centers the login dialog
		
		// Configuration of the content panel
		contentPanel = new JPanel();
		contentPanel.setBackground(backgroundColor);
		contentPanel.setForeground(foregroundColor);
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "10[]10[]"));
		
		lblBenutzername = new JLabel("Benutzername:");
		lblBenutzername.setForeground(foregroundColor);
		contentPanel.add(lblBenutzername, "cell 0 0,alignx trailing");
		
		tfBenutzername = new JTextField();
		contentPanel.add(tfBenutzername, "cell 1 0,growx");
		tfBenutzername.setColumns(10);
		
		lblPasswort = new JLabel("Passwort:");
		lblPasswort.setForeground(foregroundColor);
		contentPanel.add(lblPasswort, "cell 0 1,alignx trailing");
		
		pfPasswort = new JPasswordField();
		contentPanel.add(pfPasswort, "cell 1 1,growx");
		
		// Configuration of the button panel
		buttonPanel = new JPanel();
		buttonPanel.setBackground(backgroundColor);
		buttonPanel.setForeground(foregroundColor);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnLogin = new JButton("Anmelden");
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent evt) {
				if (isLoginValid()) {
					login.setVisible(false);
					MainFrame.start();
				} else {
					JOptionPane.showMessageDialog(null, "Benutzername oder Passwort falsch!");
				}
			}
		});
		buttonPanel.add(btnLogin);
		getRootPane().setDefaultButton(btnLogin);
		
		setVisible(true);
	}
	
	public boolean isLoginValid() {
		try {
			Connection con = DBConnection.connectLogin();
			if (DBConnection.checkLogin(tfBenutzername.getText(), pfPasswort.getText())) {
				con.close();
				return true;
			} else {
				con.close();
				return false;
			}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
	}
	
	public boolean checkAdmin() {
		try {
			Connection con = DBConnection.connectLogin();
			if (DBConnection.checkAdmin(tfBenutzername.getText())) {
				con.close();
				return true;
			} else {
				con.close();
				return false;
			}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), e, "Dialog", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
	}
	
	
}
