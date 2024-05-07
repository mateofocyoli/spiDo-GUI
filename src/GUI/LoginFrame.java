package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import users.*;

public class LoginFrame extends JFrame implements ActionListener {
	
	JButton loginButton, signupButton;
	JTextField usernameTextField, passwordTextField;
	
	public LoginFrame() {
		
		//frame setup
		this.setSize(500, 400);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Library - Login");
		
		this.setLayout(new BorderLayout());
		
		//*DA SOSTITUIRE CON LOGO UNIBS*
		ImageIcon frameIcon = new ImageIcon("assets\\spidogui.png");
		this.setIconImage(frameIcon.getImage());
		
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
		ImageIcon unibsLogo = new ImageIcon(new ImageIcon("assets\\unibsLogo.jpg").getImage().getScaledInstance(450, -1, Image.SCALE_SMOOTH));
		//if second measure is -1 it autoscales
		unibsLogoContainer.setIcon(unibsLogo);
		
		northPanel.add(unibsLogoContainer);
		
		//center panel setup
		JLabel usernameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		
		usernameLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		passwordLabel.setFont(new Font("Lexend", Font.BOLD, 20));

		usernameTextField = new JTextField();
		passwordTextField = new JTextField();
		
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
	
	
	public static void main(String[] args) {
		new LoginFrame();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==loginButton) {
			
			PersonManager pm = PersonManager.getInstance();
			pm.load();
			
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			Person person = pm.login(username, password);
			
			//checks if person logging in is admin or user
			if(person == null) {
				JOptionPane.showMessageDialog(null, "Incorrect credentials", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if(person instanceof Admin) {
				new AdminFrame();
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
