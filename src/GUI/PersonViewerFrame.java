package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import exceptions.InvalidAdminException;
import users.Admin;
import users.Person;
import users.managers.PersonManager;

public class PersonViewerFrame extends JFrame {

    private Admin admin;

    public PersonViewerFrame(Admin admin) throws InvalidAdminException {
        if (admin == null)
            throw new InvalidAdminException("The admin object can not be null.");
        this.admin = admin;

        this.setTitle("Person Viewer - " + admin.getCredentials().getUsername());
        this.setSize(750, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ((JFrame) e.getSource()).dispose();
                try {
                    new AdminFrame(admin);
                } catch (InvalidAdminException e1) {
                    e1.printStackTrace();
                    System.exit(ABORT);
                }
            }
        });

        JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane);

        PersonManager pm = PersonManager.getInstance();
        List<Person> people = pm.sortBy(PersonManager.Criteria.USERNAME);
        for(Person p : people) {
            panel.add(new PersonPanel(admin, p, this));
        }
    }

    public void redraw() {
        this.dispose();
        try {
            new PersonViewerFrame(admin);
        } catch (InvalidAdminException e) {
            e.printStackTrace();
            System.exit(ABORT);
        }
    }

}
