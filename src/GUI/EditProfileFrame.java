package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import users.Credentials;
import users.Person;
import users.Person.Sex;

public class EditProfileFrame extends JFrame implements ActionListener {
	

	//Grafic tools for window design
	private JTextField nameTextField, surnameTextField, cityTextField, passwordTextField;
	private DateSelectPanel datePanel;
	private JComboBox<Sex> sexComboBox;
	private JButton editButton;

    private Person person;
	
	public EditProfileFrame(Person person) {
		
        this.person = person;
		this.datePanel = new DateSelectPanel(person.getBirth(), 1900, 2024);
		//Frame setup
		this.setTitle("Edit profile");
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
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
		JLabel nameLabel = new JLabel();
		nameLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		nameLabel.setText("First name:");
		p11.add(nameLabel);
		centerPanel.add(p11);
		
		//name textfield setup
		nameTextField = new JTextField(14);
		nameTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
        nameTextField.setText(person.getName());
		p12.add(nameTextField);
		centerPanel.add(p12);
		
		
		//surname label setup
		JLabel surnameLabel = new JLabel();
		surnameLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		surnameLabel.setText("Surname:");
		p21.add(surnameLabel);
		centerPanel.add(p21);
		
		//surname textfield setup
		surnameTextField = new JTextField(14);
		surnameTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
        surnameTextField.setText(person.getSurname());
		p22.add(surnameTextField);
		centerPanel.add(p22);
		
		
		//date label setup
		JLabel dateLabel = new JLabel();
		dateLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		dateLabel.setText("Date of birth:");
		p31.add(dateLabel);
		centerPanel.add(p31);
		
		//date combo box setup		
		centerPanel.add(this.datePanel);
		
		
		//city label setup
		JLabel cityLabel = new JLabel();
		cityLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		cityLabel.setText("City of birth:");
		p41.add(cityLabel);
		centerPanel.add(p41);
		
		//city textfield setup
		cityTextField = new JTextField(14);
		cityTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
        cityTextField.setText(person.getCityOfBirth());
		p42.add(cityTextField);
		centerPanel.add(p42);
		
		
		//sex label setup
		JLabel sexLabel = new JLabel("Sex:");
		sexLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		p51.add(sexLabel);
		centerPanel.add(p51);
		
		//sex combobox setup
		Sex[] sexes = {Sex.MALE, Sex.FEMALE};
		sexComboBox = new JComboBox<>(sexes);
		sexComboBox.setFont(new Font("Lexend", Font.PLAIN, 15));
        sexComboBox.setSelectedIndex( (person.getSex() == sexes[0]) ? 0 : 1 );
		p52.add(sexComboBox);
		centerPanel.add(p52);
		
		
		//username label setup
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Lexend", Font.BOLD, 20));

        JLabel cannotModifyLabel = new JLabel("(can not be modified)");
        cannotModifyLabel.setFont(new Font("Lexend", Font.ITALIC, 12));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new GridLayout(0, 1));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(cannotModifyLabel);

		p61.add(usernamePanel);
		centerPanel.add(p61);
		
		//username textfield setup
		JLabel usernameTextFieldLabel = new JLabel(person.getCredentials().getUsername());
		usernameTextFieldLabel.setFont(new Font("Lexend", Font.PLAIN, 20));
		p62.add(usernameTextFieldLabel);
		centerPanel.add(p62);
		
		
		//password label setup
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Lexend", Font.BOLD, 20));

        JLabel leaveBlankLabel = new JLabel("(blank = no update)");
        leaveBlankLabel.setFont(new Font("Lexend", Font.ITALIC, 12));

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new GridLayout(0, 1));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(leaveBlankLabel);

		p71.add(passwordPanel);
		centerPanel.add(p71);
		
		//password textfield setup
		passwordTextField = new JTextField(14);
		passwordTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
		p72.add(passwordTextField);
		centerPanel.add(p72);
		
		
		//BOTTOM PANEL SETUP
		editButton = new JButton();
		editButton.setText("Edit");
		bottomPanel.add(editButton);
		editButton.addActionListener(this);
		
		
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
		if(e.getSource()==editButton) {
			
			//memorize all the info
			String name = nameTextField.getText();
			String surname = surnameTextField.getText();
            int year = datePanel.getYear(),
                month = datePanel.getMonth(),
                day = datePanel.getDay();
			LocalDate birthDate = LocalDate.of(year, month, day);
			
			String cityOfBirth = cityTextField.getText();
			
			Sex sex = (Sex) sexComboBox.getSelectedItem();
			
			//credentials
			String password = passwordTextField.getText();
            Credentials credentials = null;
            if (!password.isBlank()) {
                try {
                    credentials = new Credentials(person.getCredentials().getUsername(), password);
                } catch(InvalidParameterException ipe) {
                    JOptionPane.showMessageDialog(null, "Username and password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
			
            if(name.isBlank()) {
                JOptionPane.showMessageDialog(null, "Name can not be blank", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(surname.isBlank()) {
                JOptionPane.showMessageDialog(null, "Surname can not be blank", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(cityOfBirth.isBlank()) {
                JOptionPane.showMessageDialog(null, "City of birth can not be blank", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

			person.setName(name);
            person.setSurname(surname);
            person.setBirth(birthDate);
            person.setCityOfBirth(cityOfBirth);
            person.setSex(sex);
            if(credentials != null)
                person.setCredentials(credentials);
            
            this.dispose();
		}
		
	}
}