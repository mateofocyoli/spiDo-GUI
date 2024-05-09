package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.AppCloser;
import exceptions.InvalidAdminException;
import users.Admin;

public class AdminFrame extends JFrame implements ActionListener {

    private static final String FRAME_TITLE = "Admin - ";

    private JMenuBar menuBar;
    private JMenu sortBy, filterBy, search, edit, view;
    private JMenuItem sortByTitle, sortByAuthor, sortByYear, sortByPages, sortByGenre;
    private JMenuItem filterByTitle, filterByAuthor, filterByYear, filterByPages, filterByGenre;
    private JMenuItem searche;
    private JMenuItem add, remove;
    private JMenuItem loans;
    private JButton index;

    private Admin admin;

    public AdminFrame(Admin admin) throws InvalidAdminException {
        if (admin == null)
            throw new InvalidAdminException("The admin object can not be null.");
        this.admin = admin;

        this.setTitle(FRAME_TITLE + admin.getCredentials().getUsername());
        this.setSize(750, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new AppCloser());

        // MENU BAR SETUP
        // addition of the menu bar at the top of the frame
        menuBar = new JMenuBar();

        // the first voice on the menu will be sort by
        sortBy = new JMenu("Sort by");
        // possibilities to chose what to sort by
        sortByTitle = new JMenuItem("Title");
        sortByAuthor = new JMenuItem("Author");
        sortByYear = new JMenuItem("Year");
        sortByPages = new JMenuItem("Number of pages");
        sortByGenre = new JMenuItem("Genre");
        // addition of the voices to the sort menu
        sortBy.add(sortByTitle);
        sortBy.add(sortByAuthor);
        sortBy.add(sortByYear);
        sortBy.add(sortByPages);
        sortBy.add(sortByGenre);

        // the second voice on the menu will be filter by
        filterBy = new JMenu("Filter by");
        // possibilities to chose what to filter by
        filterByTitle = new JMenuItem("Title");
        filterByAuthor = new JMenuItem("Author");
        filterByYear = new JMenuItem("Year");
        filterByPages = new JMenuItem("Number of pages");
        filterByGenre = new JMenuItem("Genre");
        // addition of the voices to the sort menu
        filterBy.add(filterByTitle);
        filterBy.add(filterByAuthor);
        filterBy.add(filterByYear);
        filterBy.add(filterByPages);
        filterBy.add(filterByGenre);

        // the third voice on the menu will be search by
        search = new JMenu("Search");
        searche = new JMenuItem("Search");
        search.add(searche);

        // the fourth voice on the menu will be edit
        edit = new JMenu("Edit");
        // possibilities to chose what to filter by
        add = new JMenuItem("Add book");
        remove = new JMenuItem("Remove book");
        edit.add(add);
        edit.add(remove);

        // the fifth voice on the menu will be view loans
        view = new JMenu("View");
        // possibilities to chose what to view
        loans = new JMenuItem("View loans");
        view.add(loans);

        // addition of the five voices to the menu bar
        menuBar.add(sortBy);
        menuBar.add(filterBy);
        menuBar.add(search);
        menuBar.add(edit);
        menuBar.add(view);

        // set keyboard shortcuts
        sortBy.setMnemonic(KeyEvent.VK_S); // Alt+S for sortMenu
        filterBy.setMnemonic(KeyEvent.VK_F); // Alt+F for filterMenu
        search.setMnemonic(KeyEvent.VK_C); // Alt+C for searchMenu
        edit.setMnemonic(KeyEvent.VK_E); // Alt+E for edit
        view.setMnemonic(KeyEvent.VK_V); // Alt+V for view
        sortByTitle.setMnemonic(KeyEvent.VK_T); // T for title
        filterByTitle.setMnemonic(KeyEvent.VK_T);
        sortByAuthor.setMnemonic(KeyEvent.VK_A); // A for author
        filterByAuthor.setMnemonic(KeyEvent.VK_A);
        sortByYear.setMnemonic(KeyEvent.VK_Y); // Y for year
        filterByYear.setMnemonic(KeyEvent.VK_Y);
        sortByPages.setMnemonic(KeyEvent.VK_N); // N for number of pages
        filterByPages.setMnemonic(KeyEvent.VK_N);
        sortByGenre.setMnemonic(KeyEvent.VK_G); // G for genre
        filterByGenre.setMnemonic(KeyEvent.VK_G);
        searche.setMnemonic(KeyEvent.VK_C); // C for search
        add.setMnemonic(KeyEvent.VK_A); // A for add
        remove.setMnemonic(KeyEvent.VK_R); // R for add
        loans.setMnemonic(KeyEvent.VK_V); // V for view

        // addiction of the action listeners to perform methods when pressed
        sortByTitle.addActionListener(this);
        sortByAuthor.addActionListener(this);
        sortByYear.addActionListener(this);
        sortByPages.addActionListener(this);
        sortByGenre.addActionListener(this);
        filterByTitle.addActionListener(this);
        filterByAuthor.addActionListener(this);
        filterByYear.addActionListener(this);
        filterByPages.addActionListener(this);
        filterByGenre.addActionListener(this);
        searche.addActionListener(this);
        add.addActionListener(this);
        remove.addActionListener(this);
        loans.addActionListener(this);

        // set of the menu bar in the frame
        this.setJMenuBar(menuBar);

        // scroll bar setup
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new GridLayout(0, 1));
        this.add(backgroundPanel);
        JScrollPane s = new JScrollPane(backgroundPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(s);

        // temporary//bottoni a cazzo
        for (int i = 1; i < 100; i++) {
            index = new JButton("" + i);
            backgroundPanel.add(index);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ACTION PERMORMED WHEN A VOICE OF THE MENUBAR IS SELECTED
        // ------------------da sostituire con i metodi di Marco penso------------------
        // if sort by title
        if (e.getSource() == sortByTitle) {
            System.out.println("sortByTitle");
        }
        // if sort by author
        if (e.getSource() == sortByAuthor) {
            System.out.println("sortByAuthor");
        }
        // if sort by year
        if (e.getSource() == sortByYear) {
            System.out.println("sortByYear");
        }
        // if sort by number fo pages
        if (e.getSource() == sortByPages) {
            System.out.println("sortByPages");
        }
        // if sort by genre
        if (e.getSource() == sortByGenre) {
            System.out.println("sortByGenre");
        }
        // if filter by title
        if (e.getSource() == filterByTitle) {
            System.out.println("filterByTitle");
        }
        // if filter by author
        if (e.getSource() == filterByAuthor) {
            System.out.println("filterByAuthor");
        }
        // if filter by year
        if (e.getSource() == filterByYear) {
            System.out.println("filterByYear");
        }
        // if filter by number of pages
        if (e.getSource() == filterByPages) {
            System.out.println("filterByPages");
        }
        // if filter by genre
        if (e.getSource() == filterByGenre) {
            System.out.println("filterByGenre");
        }
        // if search
        if (e.getSource() == searche) {
            System.out.println("search");
        }
        // if add
        if (e.getSource() == add) {
            try {
                this.dispose();
                new NewBookFrame(admin);
            } catch (InvalidAdminException e1) {
                e1.printStackTrace();
                System.exit(ABORT);
            }
        }
        // if remove
        if (e.getSource() == remove) {
            System.out.println("remove");
        }
        // if view loans
        if (e.getSource() == loans) {
            System.out.println("view loans");
        }
    }

}
