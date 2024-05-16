package GUI;

import java.awt.Color;
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
import users.User;
import users.managers.PersonManager;
import users.sanctions.Sanction;

public class SanctionPanel extends JPanel implements ActionListener {

    private Admin applicant;
    private User user;
    private Sanction sanction;
    private JButton removeButton, descriptionButton;
    private SanctionViewerFrame svf;

    public SanctionPanel(Admin applicant, User user, Sanction sanction, SanctionViewerFrame svf) {
        this.applicant = applicant;
        this.user = user;
        this.sanction = sanction;
        this.svf = svf;

        JLabel nameLabel = new JLabel(sanction.name);
        nameLabel.setFont(new Font("Lexend", Font.BOLD, 16));
        JLabel dateLabel = new JLabel(sanction.date.toString());
        dateLabel.setFont(new Font("Lexend", Font.ITALIC, 10));
        JLabel severityLabel = new JLabel(sanction.severity.toString());
        severityLabel.setFont(new Font("Lexend", Font.PLAIN, 16));

        switch (sanction.severity) {
            case LOW:
                severityLabel.setForeground(Color.YELLOW);
                break;
            case MEDIUM:
                severityLabel.setForeground(Color.ORANGE);
                break;
            case HIGH:
                severityLabel.setForeground(Color.RED);
                break;
            case WORST:
                severityLabel.setForeground(Color.MAGENTA);
                break;
            default:
                break;
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        removeButton = new JButton("Remove");
        descriptionButton = new JButton("Description");

        removeButton.addActionListener(this);
        descriptionButton.addActionListener(this);

        this.setLayout(new FlowLayout());
        this.add(nameLabel);
        this.add(dateLabel);
        this.add(severityLabel);

        buttonPanel.add(removeButton);
        buttonPanel.add(descriptionButton);
        
        this.add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == removeButton) {
            PersonManager pm = PersonManager.getInstance();
            try {
                pm.pardon(applicant, user, sanction);
            } catch (InvalidAdminException e1) {
                JOptionPane.showMessageDialog(this, "You don't have the privileges", "Error", JOptionPane.ERROR_MESSAGE);
            }
            svf.redraw();
        }
        if(e.getSource() == descriptionButton) {
            JOptionPane.showMessageDialog(this, 
            sanction.description, "Description",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
