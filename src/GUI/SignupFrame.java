package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.time.LocalDate;

import javax.swing.*;

import users.Person.Sex;
import users.managers.PersonManager;
import users.*;

public class SignupFrame extends JFrame implements ActionListener {
	

	//Grafic tools for window design
	private JLabel nameLabel, surnameLabel, dateLabel, cityLabel, sexLabel, usernameLabel, passwordLabel;
	private JTextField nameTextField, surnameTextField, cityTextField, usernameTextField, passwordTextField;
	private DateSelectPanel datePanel;
	private JComboBox<Sex> sexComboBox;
	private JButton signupButton;
	
	//User credentials
	private String name;
	private String surname;
	private LocalDate birthDate;
	private String cityOfBirth;
	private Sex sex;
	private Credentials credentials;
	private String username;
	private String password;
	
	
	SignupFrame() {
		this.datePanel=new DateSelectPanel(1900, 2024);
		
		//Frame setup
		this.setTitle("Signing Page");
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ((JFrame) e.getSource()).dispose();
                new LoginFrame();
            }
        });
        
		this.setLayout(new BorderLayout(0, 40));
		
		//sets the icon of the LoginFrame
        ImageIcon frameIcon = new ImageIcon(Path.of("assets", "spidogui.png").toString());
        this.setIconImage(frameIcon.getImage());
		
		//layout frame
		//top panel will have the title
		//center panel will have all the credential fields
		//bottom panel will have the sign up comfirmation button
		JPanel topPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		
		//TOP PANEL SETUP
		JLabel titleLabel = new JLabel();
		titleLabel.setForeground(Color.RED);
		titleLabel.setFont(new Font("Lexend", Font.BOLD, 40));
		titleLabel.setText("Insert Credentials");
		topPanel.add(titleLabel);
		
		
		//CENTER PANEL SETUP
		//dividing the center panel in 14 different panels (7 labels and 7 inputs)
		centerPanel.setLayout(new GridLayout(7, 2, 15, 15));
		JPanel p11 = new JPanel();
		JPanel p12 = new JPanel();
		JPanel p21 = new JPanel();
		JPanel p22 = new JPanel();
		JPanel p31 = new JPanel();
		JPanel p41 = new JPanel();
		JPanel p42 = new JPanel();
		JPanel p51 = new JPanel();
		JPanel p52 = new JPanel();
		JPanel p61 = new JPanel();
		JPanel p62 = new JPanel();
		JPanel p71 = new JPanel();
		JPanel p72 = new JPanel();
		
		//name label setup
		nameLabel = new JLabel();
		nameLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		nameLabel.setText("First name:");
		p11.add(nameLabel);
		centerPanel.add(p11);
		
		//name textfield setup
		nameTextField = new JTextField(14);
		nameTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
		p12.add(nameTextField);
		centerPanel.add(p12);
		
		
		//surname label setup
		surnameLabel = new JLabel();
		surnameLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		surnameLabel.setText("Surname:");
		p21.add(surnameLabel);
		centerPanel.add(p21);
		
		//surname textfield setup
		surnameTextField = new JTextField(14);
		surnameTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
		p22.add(surnameTextField);
		centerPanel.add(p22);
		
		
		//date label setup
		dateLabel = new JLabel();
		dateLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		dateLabel.setText("Date of birth:");
		p31.add(dateLabel);
		centerPanel.add(p31);
		
		centerPanel.add(datePanel);
		
		
		//city label setup
		cityLabel = new JLabel();
		cityLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		cityLabel.setText("City of birth:");
		p41.add(cityLabel);
		centerPanel.add(p41);
		
		//city textfield setup
		cityTextField = new JTextField(14);
		cityTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
		p42.add(cityTextField);
		centerPanel.add(p42);
		
		
		//sex label setup
		sexLabel = new JLabel();
		sexLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		sexLabel.setText("Sex:");
		p51.add(sexLabel);
		centerPanel.add(p51);
		
		//sex combobox setup
		Sex[] sexes = {Sex.MALE, Sex.FEMALE};
		sexComboBox = new JComboBox<>(sexes);
		sexComboBox.setFont(new Font("Lexend", Font.PLAIN, 15));
		p52.add(sexComboBox);
		centerPanel.add(p52);
		
		
		//username label setup
		usernameLabel = new JLabel();
		usernameLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		usernameLabel.setText("Username:");
		p61.add(usernameLabel);
		centerPanel.add(p61);
		
		//username textfield setup
		usernameTextField = new JTextField(14);
		usernameTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
		p62.add(usernameTextField);
		centerPanel.add(p62);
		
		
		//password label setup
		passwordLabel = new JLabel();
		passwordLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		passwordLabel.setText("Password:");
		p71.add(passwordLabel);
		centerPanel.add(p71);
		
		//password textfield setup
		passwordTextField = new JTextField(14);
		passwordTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
		p72.add(passwordTextField);
		centerPanel.add(p72);
		
		
		//BOTTOM PANEL SETUP
		signupButton = new JButton();
		signupButton.setText("Sign up");
		bottomPanel.add(signupButton);
		signupButton.addActionListener(this);
		
		
		//east panel added purely for graphic reasons
		//it adds a little bit of an edge to the left
		JPanel eastPanel = new JPanel();
		this.add(eastPanel, BorderLayout.EAST);
		
        pack();
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()), (int) Math.min(getSize().getHeight(), screenDim.getHeight()));
	}
	

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//if sign up button is pressed
		if(e.getSource()==signupButton) {
			
			//memorize all the info
			name = nameTextField.getText();
			surname = surnameTextField.getText();
            int year = datePanel.getYear(),
                month = datePanel.getMonth(),
                day = datePanel.getDay();
			birthDate = LocalDate.of(year, month, day);
			
			cityOfBirth = cityTextField.getText();
			
			sex = (Sex) sexComboBox.getSelectedItem();
			
			//credentials
			username = usernameTextField.getText();
			password = passwordTextField.getText();
			try {
				credentials = new Credentials(username, password);
			} catch(InvalidParameterException ipe) {
				JOptionPane.showMessageDialog(null, "Username and password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			
			Person person = new User(name, surname, birthDate, cityOfBirth, sex, credentials);
			PersonManager pm = PersonManager.getInstance();
			
			if(!pm.add(person)) JOptionPane.showMessageDialog(null, "Fields not valid", "Error", JOptionPane.ERROR_MESSAGE);
			else {
				this.dispose();
				new LoginFrame();
			}
			
		}
		
	}
}
