package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.*;

import app.AppCloser;
import exceptions.InvalidAdminException;
import users.*;
import users.managers.PersonManager;

public class LoginFrame extends JFrame implements ActionListener {
	
	private JButton loginButton, signupButton;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	
	public LoginFrame() {
		
		//frame setup
		this.setSize(500, 400);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new AppCloser());

		this.setTitle("Library - Login");
		this.setLayout(new BorderLayout());
		
		//sets the icon of the LoginFrame
		ImageIcon frameIcon = new ImageIcon(Path.of("assets", "spidogui.png").toString());
		this.setIconImage(frameIcon.getImage());
		
		//divides the frame into different panels
		JPanel centerPanel = new JPanel();
		JPanel northPanel = new JPanel();
		JPanel westPanel = new JPanel();
		JPanel eastPanel = new JPanel();
		JPanel southPanel = new JPanel();
		
		centerPanel.setPreferredSize(new Dimension(100, 100));
		northPanel.setPreferredSize(new Dimension(100, 200));
		westPanel.setPreferredSize(new Dimension(50, 100));
		eastPanel.setPreferredSize(new Dimension(50, 100));
		southPanel.setPreferredSize(new Dimension(100, 80));
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(westPanel, BorderLayout.WEST);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(southPanel, BorderLayout.SOUTH);
		
		
		//north panel setup
		JLabel unibsLogoContainer = new JLabel();
		ImageIcon unibsLogo = new ImageIcon(new ImageIcon(Path.of("assets", "spiDoGUI_libreria.png").toString()).getImage().getScaledInstance(400, -1, Image.SCALE_SMOOTH));
		//if second measure is -1 it autoscales
		unibsLogoContainer.setIcon(unibsLogo);
		
		northPanel.add(unibsLogoContainer);
		
		//center panel setup
		JLabel usernameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		
		usernameLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		passwordLabel.setFont(new Font("Lexend", Font.BOLD, 20));

		usernameTextField = new JTextField();
		passwordTextField = new JPasswordField();
		
		usernameTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
		passwordTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
		
		
		//south panel setup
		loginButton = new JButton("Log in");
		signupButton = new JButton("Sign up");
		
		
		centerPanel.setLayout(new GridLayout(2, 2));
		
		centerPanel.add(usernameLabel);
		centerPanel.add(usernameTextField);
		centerPanel.add(passwordLabel);
		centerPanel.add(passwordTextField);
		
		loginButton.setFocusable(false);
		southPanel.add(loginButton);
		loginButton.addActionListener(this);
		
		signupButton.setFocusable(false);
		southPanel.add(signupButton);
		signupButton.addActionListener(this);
		
		this.validate();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==loginButton) {
			
			PersonManager pm = PersonManager.getInstance();
			
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			Person person = pm.login(username, password);
			
			//checks if person logging in is admin or user
			if(person == null) {
				JOptionPane.showMessageDialog(null, "Incorrect credentials", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(person instanceof Admin) {
				try {
                    new AdminFrame((Admin) person);
                } catch (InvalidAdminException e1) {
                    // This should never be thrown
                    e1.printStackTrace();
                }
				this.dispose();
			}
			else if(person instanceof User) {
				new UserFrame(person);
				this.dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Username not found", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if(e.getSource()==signupButton) {
			new SignupFrame();
			this.dispose();
		}
		
	}
	
}
