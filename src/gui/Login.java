package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import admin.AccessData;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		setTitle("Admin Login");
		setBounds(100, 100, 450, 140);
		setBackground(frameColor);
		setForeground(foregroundColor);
		
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
				if (AccessData.chekUsrname(tfBenutzername.getText()) && AccessData.checkPassword(pfPasswort.getText())) {
					DataEditor dataEditor =DataEditor.getInstance();
					dataEditor.setVisible(true);
					login.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Benutzername oder Passwort falsch!");
				}
			}
		});
		buttonPanel.add(btnLogin);
		getRootPane().setDefaultButton(btnLogin);
		
		setVisible(true);
	}
}
