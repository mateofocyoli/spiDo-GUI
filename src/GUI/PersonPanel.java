package GUI;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import exceptions.InvalidAdminException;
import users.Admin;
import users.Person;
import users.User;
import users.managers.PersonManager;

public class PersonPanel extends JPanel implements ActionListener {

    private Admin applicant;
    private Person person;
    private JButton removeButton, showInfoButton, showLoansButton, showSanctionsButton;
    private PersonViewerFrame pvf;

    public PersonPanel(Admin applicant, Person person, PersonViewerFrame pvf) {
        this.applicant = applicant;
        this.person = person;
        this.pvf = pvf;

        JLabel typeLabel = new JLabel(person instanceof User ? "[U]   " : "[A]   ");
        typeLabel.setFont(new Font("Lexend", Font.ITALIC, 16));
        JLabel usernameLabel = new JLabel(person.getCredentials().getUsername());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        removeButton = new JButton("Remove");
        showInfoButton = new JButton("Show info");
        showLoansButton = new JButton("Show loans");
        showSanctionsButton = new JButton("Show sanctions");

        removeButton.addActionListener(this);
        showInfoButton.addActionListener(this);
        showLoansButton.addActionListener(this);
        showSanctionsButton.addActionListener(this);

        this.setLayout(new FlowLayout());
        this.add(typeLabel);
        this.add(usernameLabel);

        buttonPanel.add(removeButton);
        buttonPanel.add(showInfoButton);
        if(person instanceof User) {
            buttonPanel.add(showLoansButton);
            buttonPanel.add(showSanctionsButton);
        }
        
        this.add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == removeButton) {
            PersonManager pm = PersonManager.getInstance();
            try {
                pm.remove(applicant, person);
            } catch (IllegalAccessException e1) {
                JOptionPane.showMessageDialog(this, "You don't have the privileges", "Error", JOptionPane.ERROR_MESSAGE);
            }
            pvf.showPersonList(pm.getList());
        }
        if(e.getSource() == showInfoButton) {
            JOptionPane.showMessageDialog(this, 
            "Name: " + person.getName() +
            "\nSurname: " + person.getSurname() +
            "\nDate of birth: " + person.getBirth() +
            "\nCity of birth: " + person.getCityOfBirth() +
            "\nSex: " + person.getSex() +
            "\nUsername: " + person.getCredentials().getUsername(),
            "Info on " + person.getCredentials().getUsername(), JOptionPane.INFORMATION_MESSAGE);
        }
        if(e.getSource() == showLoansButton) {
            try {
                pvf.dispose();
                new LoanViewerFrameAdmin(applicant, (User) person);
            } catch (InvalidAdminException e1) {
                JOptionPane.showMessageDialog(this, "You don't have the privileges", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource() == showSanctionsButton) {
            try {
                pvf.dispose();
                new SanctionViewerFrame(applicant, (User) person);
            } catch (InvalidAdminException e1) {
                JOptionPane.showMessageDialog(this, "You don't have the privileges", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
