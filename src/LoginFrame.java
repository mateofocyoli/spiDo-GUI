import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginFrame extends JFrame implements ActionListener{
	
	JButton loginButton;
	JTextField usernameTextField;
	JTextField passwordTextField;
	
	LoginFrame() {
		
		this.setSize(500, 400);
//		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Library - Login");
		
		this.setLayout(new BorderLayout());
		
		
		
		JLabel usernameLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		JLabel unibsLogoContainer = new JLabel();
		
		
		
		loginButton = new JButton("Login");
		
		usernameTextField = new JTextField();
		passwordTextField = new JTextField();
		
		JPanel centerPanel = new JPanel();
		JPanel northPanel = new JPanel();
		JPanel westPanel = new JPanel();
		JPanel eastPanel = new JPanel();
		JPanel southPanel = new JPanel();
		
		centerPanel.setPreferredSize(new Dimension(100, 100));
		northPanel.setPreferredSize(new Dimension(100, 150));
		westPanel.setPreferredSize(new Dimension(50, 100));
		eastPanel.setPreferredSize(new Dimension(50, 100));
		southPanel.setPreferredSize(new Dimension(100, 80));
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(westPanel, BorderLayout.WEST);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(southPanel, BorderLayout.SOUTH);
		
		centerPanel.setLayout(new GridLayout(2, 2));
		
		centerPanel.add(usernameLabel);
		centerPanel.add(usernameTextField);
		centerPanel.add(passwordLabel);
		centerPanel.add(passwordTextField);
		
		loginButton.setFocusable(false);
		southPanel.add(loginButton);
		loginButton.addActionListener(this);
		
//		this.pack();
		
	}
	
	
	public static void main(String[] args) {
		new LoginFrame();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==loginButton) {
			String username = usernameTextField.getText();
			if(username.equals("admin")) {
				new AdminFrame();
				this.dispose();
			}
			else if(username.equals("user")) {
				new UserFrame();
				this.dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Username not found", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
}
