package userLogin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

import gui.MainFrame;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class UserLogin extends JFrame {

	private static UserLogin userLogin = new UserLogin();
	
	private JPanel contentPanel = new JPanel();
	private JPanel buttonPanel;
	private JLabel lblPasswort;
	private JLabel lblBenutzername;
	private JTextField tfBenutzername;
	private JPasswordField pfPasswort;
	private JButton btnLogin;
	
	private Color frameColor = new Color(32, 32, 32);
	private Color backgroundColor = new Color(25, 25, 25);
	private Color foregroundColor = new Color(255, 255, 255);
	
	 
	
	public static UserLogin getInstance() {
		return userLogin;
	}

	// Creating the dialog.
	public UserLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("User Login");
		setBounds(520, 350, 450, 140);
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
		
		UIManager UI=new UIManager();
		 UI.put("OptionPane.background",frameColor);
		 UI.put("Panel.background", backgroundColor);
		 UI.put("OptionPane.messageForeground", foregroundColor);
		
		
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (AccessUserData.checkUsername(tfBenutzername.getText()) && AccessUserData.checkUserPassword(pfPasswort.getText())) {
					userLogin.dispose();
				
					JOptionPane.showMessageDialog(null, "Login erfolgreich!");
					
					MainFrame mf = new MainFrame();
					mf.setVisible(true);
				} else {
					JOptionPane jop = new JOptionPane();
					jop.setMessage("Benutzername oder Passwort falsch!");
					JDialog dialog = jop.createDialog(jop, "Message");
					dialog.setAlwaysOnTop(true);
					
					dialog.setVisible(true);
					
					
				}
			}
		});
		buttonPanel.add(btnLogin);
		getRootPane().setDefaultButton(btnLogin);
		
		setVisible(true);
	}
}
