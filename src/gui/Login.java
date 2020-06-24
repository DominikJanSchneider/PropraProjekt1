package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.DBConnector;
import net.miginfocom.swing.MigLayout;

public class Login extends JDialog {

	private static final long serialVersionUID = 1L;

	private static Login login = new Login();
	
	private JPanel contentPanel = new JPanel();
	private JPanel buttonPanel;
	private JLabel lblPassword;
	private JLabel lblusername;
	private static JTextField tfusername;
	private static JPasswordField pfPassword;
	private JButton btnLogin;
	//private static boolean valid;
	
	private Color frameColor = new Color(32, 32, 32);
	private Color backgroundColor = new Color(25, 25, 25);
	private Color foregroundColor = new Color(255, 255, 255);
	
	public static Login getInstance() {
		tfusername.setText("");
		pfPassword.setText("");
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
		
		lblusername = new JLabel("Benutzername:");
		lblusername.setForeground(foregroundColor);
		contentPanel.add(lblusername, "cell 0 0,alignx trailing");
		
		tfusername = new JTextField();
		contentPanel.add(tfusername, "cell 1 0,growx");
		tfusername.setColumns(10);
		
		lblPassword = new JLabel("Passwort:");
		lblPassword.setForeground(foregroundColor);
		contentPanel.add(lblPassword, "cell 0 1,alignx trailing");
		
		pfPassword = new JPasswordField();
		contentPanel.add(pfPassword, "cell 1 1,growx");
		
		// Configuration of the button panel
		buttonPanel = new JPanel();
		buttonPanel.setBackground(backgroundColor);
		buttonPanel.setForeground(foregroundColor);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		btnLogin = new JButton("Anmelden");
		btnLogin.addActionListener(new ActionListener() {
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
		if (DBConnector.checkLogin(tfusername.getText(), pfPassword.getText())) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkAdmin() {
		if (DBConnector.checkAdmin(tfusername.getText())) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
