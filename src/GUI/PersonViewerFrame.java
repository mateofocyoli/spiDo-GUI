package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import GUI.adminView.AdminFrame;
import GUI.adminView.PersonPanel;
import exceptions.InvalidAdminException;
import users.Admin;
import users.Person;
import users.User;
import users.managers.PersonManager;

public class PersonViewerFrame extends JFrame {

    private class PersonSorterMenuItem extends JMenuItem implements ActionListener {
        private PersonManager.Criteria criteria;
        private PersonViewerFrame parentFrame;

        PersonSorterMenuItem(PersonManager.Criteria criteria, PersonViewerFrame parentFrame) {
            super(criteria.toString());

            this.criteria = criteria;
            this.parentFrame = parentFrame;
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            parentFrame.showPersonList(PersonManager.getInstance().sortBy(criteria));
        }
    }

    private class PersonFilterMenuItem extends JMenuItem implements ActionListener {
        private PersonManager.Criteria criteria;
        private PersonViewerFrame parentFrame;

        PersonFilterMenuItem(PersonManager.Criteria criteria, PersonViewerFrame parentFrame) {
            super(criteria.toString());

            this.criteria = criteria;
            this.parentFrame = parentFrame;
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (criteria) {
                case DATE_OF_BIRTH:
                case DATE_OF_BIRTH_BEFORE:
                case DATE_OF_BIRTH_AFTER:
                    String dateString = JOptionPane
                            .showInputDialog("Insert " + criteria + "\nThe date must be in the format 'yyyy-mm-dd'");
                    if (dateString != null) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        try {
                            LocalDate date = LocalDate.parse(dateString, dtf);
                            parentFrame.showPersonList(PersonManager.getInstance().filterBy(criteria, date));
                        } catch (DateTimeParseException dtpe) {
                            JOptionPane.showMessageDialog(null, "The date could not be parsed", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                    break;
                case SEX:
                    Person.Sex[] sexes = Person.Sex.values();
                    String[] options = new String[sexes.length];
                    for (int i = 0; i < options.length; i++)
                        options[i] = sexes[i].toString();

                    int response = JOptionPane.showOptionDialog(null, "Select what filtering argument will be used",
                            "Select filtering option", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options, 0);

                    parentFrame.showPersonList(PersonManager.getInstance().filterBy(criteria, sexes[response]));
                    break;
                case TYPE:
                    String[] types = { "ADMIN", "USER" };

                    int response1 = JOptionPane.showOptionDialog(null, "Select what filtering argument will be used",
                            "Select filtering option", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null,
                            types, 0);

                    parentFrame.showPersonList(PersonManager.getInstance()
                            .filterBy(criteria, (response1 == 0 ? Admin.class : User.class)));
                    break;
                default:
                    String argument = JOptionPane.showInputDialog("Insert " + criteria);
                    if (argument != null)
                        parentFrame.showPersonList(PersonManager.getInstance().filterBy(criteria, argument));

                    break;
            }
        }
    }

    private Admin admin;
    private JPanel personListPanel;

    public PersonViewerFrame(Admin admin) throws InvalidAdminException {
        if (admin == null)
            throw new InvalidAdminException("The admin object can not be null.");
        this.admin = admin;

        this.setTitle("Person Viewer - " + admin.getCredentials().getUsername());
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

        // MENU BAR SETUP
        // addition of the menu bar at the top of the frame
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu sortBy = new JMenu("Sort by");
        JMenu filterBy = new JMenu("Filter by");

        menuBar.add(sortBy);
        menuBar.add(filterBy);
        sortBy.setMnemonic(KeyEvent.VK_S);	//Alt+S for sortBy
        filterBy.setMnemonic(KeyEvent.VK_F);	//Alt+F for filterBy

        PersonManager.Criteria[] criterias = PersonManager.Criteria.values();
        for (PersonManager.Criteria c : criterias) {
            if (c != PersonManager.Criteria.DATE_OF_BIRTH_AFTER && c != PersonManager.Criteria.DATE_OF_BIRTH_BEFORE)
                sortBy.add(new PersonSorterMenuItem(c, this));
            
            filterBy.add(new PersonFilterMenuItem(c, this));
        }

        personListPanel = new JPanel();
        personListPanel.setLayout(new GridLayout(0, 1));

        JScrollPane scrollPane = new JScrollPane(personListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane);

        showPersonList(PersonManager.getInstance().getList());

        pack();
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()),
                (int) Math.min(getSize().getHeight(), screenDim.getHeight()));
    }

    public void showPersonList(List<Person> list) {
        personListPanel.removeAll();

        if(list.isEmpty()) {
            JLabel noPersonLabel = new JLabel("  There are no people  ");
            noPersonLabel.setForeground(Color.GRAY);
            noPersonLabel.setFont(new Font("Lexend", Font.ITALIC, 20));
            personListPanel.add(noPersonLabel);
        } else {
            for (Person p : list) {
                personListPanel.add(new PersonPanel(admin, p, this));
            }
        }

        revalidate();
        repaint();
        pack();
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()),
                (int) Math.min(getSize().getHeight(), screenDim.getHeight()));
    }
}
