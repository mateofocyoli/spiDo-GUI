package GUI.adminView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import exceptions.InvalidAdminException;
import users.Admin;
import users.Credentials;
import users.Person;
import users.Person.Sex;
import users.managers.PersonManager;

public class NewAdminFrame extends JFrame implements ActionListener {
	
    private static String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	//Grafic tools for window design
	private JLabel nameLabel, surnameLabel, dateLabel, cityLabel, sexLabel, usernameLabel, passwordLabel;
	private JTextField nameTextField, surnameTextField, cityTextField, usernameTextField, passwordTextField;
	private JComboBox<Integer> dayComboBox, yearComboBox;
	private JComboBox<String> monthComboBox;
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

    private Admin applicant;
	
	
	public NewAdminFrame(Admin applicant) throws InvalidAdminException {
		if (applicant == null)
            throw new InvalidAdminException("The admin object can not be null.");
        this.applicant = applicant;

		//Frame setup
		this.setTitle("Add new admin - " + applicant.getCredentials().getUsername());
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ((JFrame) e.getSource()).dispose();
                try {
                    new AdminFrame(applicant);
                } catch (InvalidAdminException e1) {
                    e1.printStackTrace();
                    System.exit(ABORT);
                }
            }
        });
        
		this.setLayout(new BorderLayout(0, 40));
		
		//sets the icon of the NewAdminFrame
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
		JPanel p32 = new JPanel();
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
		
		//date combo box setup
		dayComboBox = new JComboBox<>();
		monthComboBox = new JComboBox<>();
		yearComboBox = new JComboBox<>();

		dayComboBox.addActionListener(this);
		monthComboBox.addActionListener(this);
		yearComboBox.addActionListener(this);
		
		//filling yearComboBox with years from 1900 to 2024
		for(int i=1900; i<2024; i++) {
			yearComboBox.addItem(i);
		}
		//filling monthComboBox with months
		for(String m : MONTHS) {
			monthComboBox.addItem(m);
		}
		
		//adding the three combo boxes all together in the panel
		p32.setLayout(new GridLayout(1, 3));
		p32.add(dayComboBox);
		p32.add(monthComboBox);
		p32.add(yearComboBox);
		centerPanel.add(p32);
		
		
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
		signupButton.setText("Add");
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
	
	
	public boolean isLeap(int year) {
		if(year%400==0) return true;
		else if(year%100==0) return false;
		else if(year%4==0) return true;
		return false;		
	}
	
	
	public int hasDays(int month, int year) {
		
		if(month==2) {
			if(isLeap(year)) {
				return 29;
			} else return 28;
		}
		if(month==4 || month==6 || month==9 || month==11) return 30;
		return 31;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//if sign up button is pressed
		if(e.getSource()==signupButton) {
			
			//memorize all the info
			name = nameTextField.getText();
			surname = surnameTextField.getText();
            int year = (int) yearComboBox.getSelectedItem(),
                month = (int) monthComboBox.getSelectedIndex() + 1,
                day = (int) dayComboBox.getSelectedItem();
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
			
			
			Person person = new Admin(name, surname, birthDate, cityOfBirth, sex, credentials);
			PersonManager pm = PersonManager.getInstance();
			
			if(!pm.add(person)) JOptionPane.showMessageDialog(null, "Fields not valid", "Error", JOptionPane.ERROR_MESSAGE);
			else {
				this.dispose();
                JOptionPane.showMessageDialog(null, "New admin added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
				try {
                    new AdminFrame(applicant);
                } catch (InvalidAdminException e1) {
                    e1.printStackTrace();
                    System.exit(ABORT);
                }
			}
			
		}
		//if month or year is changed, update the number of days in the month
		if(e.getSource()==monthComboBox || e.getSource()==yearComboBox) {
			if(monthComboBox.getSelectedItem() == null || yearComboBox.getSelectedItem() == null)
                return;
            
            dayComboBox.removeAllItems();
			for(int i=1; i<=hasDays((int)monthComboBox.getSelectedIndex() + 1, (int)yearComboBox.getSelectedItem()); i++) {
				dayComboBox.addItem(i);
			}
			dayComboBox.setSelectedIndex(0);
		}
		
	}
}