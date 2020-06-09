package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class UserEditor extends JFrame {

	private static UserEditor userEditor = new UserEditor();
	
	private Color frameColor = new Color(32, 32, 32);
	private Color backgroundColor = new Color(25, 25, 25);
	private Color foregroundColor = new Color(255, 255, 255);
	
	private JPanel contentPane;
	
	
	//Method for getting the frame, because of singelton scheme
	public static UserEditor getInstance() {
		return userEditor;
	}
	
	//Creating the frame
	public UserEditor() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Benutzerverwaltung");
		setBackground(frameColor);
		setForeground(foregroundColor);
		setBounds(100, 100, 1200, 600);
		contentPane = new JPanel();
		contentPane.setBackground(backgroundColor);
		contentPane.setForeground(foregroundColor);
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "grow"));
		
		//Panel for the editing elements
	}

}
