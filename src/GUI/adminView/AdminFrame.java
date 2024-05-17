package GUI.adminView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.Year;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import GUI.EditProfileFrame;
import GUI.PersonViewerFrame;
import app.AppCloser;
import exceptions.InvalidAdminException;
import items.Book;
import items.managers.ArchiveManager;
import users.Admin;

public class AdminFrame extends JFrame implements ActionListener {

	private static final String FRAME_TITLE = "Admin - ";

	private JMenuBar menuBar;
	private JMenu sortBy, filterBy, edit, view;
	private JMenuItem sortByTitle, sortByAuthor, sortByYear, sortByPages, sortByGenre;
	private JMenuItem filterByTitle, filterByAuthor, filterByYear;
	private JMenu filterByGenre, filterByPages;
	private JMenuItem actionFilter, fantasyFilter, adventureFilter, romanceFilter, comedyFilter, scifiFilter,
			mysteryFilter, thrillerFilter, historicalFilter, comicFilter, mangaFilter, childrenFilter;
	private JMenuItem less, greater;
	private JMenuItem addBook, addAdmin, editProfile;
	private JMenuItem loans, requests, people;
	private JPanel backgroundPanel;

	private Admin admin;
	private ArchiveManager am;

	public AdminFrame(Admin admin) throws InvalidAdminException {
		if (admin == null)
			throw new InvalidAdminException("The admin object can not be null.");
		this.admin = admin;

		this.setTitle(FRAME_TITLE + admin.getCredentials().getUsername());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new AppCloser());
		this.am = ArchiveManager.getInstance();

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
		filterByPages = new JMenu("Number of pages");
		filterByGenre = new JMenu("Genre");
		// methods of pages filters
		less = new JMenuItem("Less or equal");
		greater = new JMenuItem("Greater or equal");
		// criteria for filter
		actionFilter = new JMenuItem("Action");
		fantasyFilter = new JMenuItem("Fantasy");
		adventureFilter = new JMenuItem("Adventure");
		romanceFilter = new JMenuItem("Romance");
		comedyFilter = new JMenuItem("Comedy");
		scifiFilter = new JMenuItem("Scifi");
		mysteryFilter = new JMenuItem("Mystery");
		thrillerFilter = new JMenuItem("Thriller");
		historicalFilter = new JMenuItem("Historical");
		comicFilter = new JMenuItem("Comic");
		mangaFilter = new JMenuItem("Manga");
		childrenFilter = new JMenuItem("Children");
		// add
		filterByPages.add(less);
		filterByPages.add(greater);
		filterByGenre.add(actionFilter);
		filterByGenre.add(fantasyFilter);
		filterByGenre.add(adventureFilter);
		filterByGenre.add(romanceFilter);
		filterByGenre.add(comedyFilter);
		filterByGenre.add(scifiFilter);
		filterByGenre.add(mysteryFilter);
		filterByGenre.add(thrillerFilter);
		filterByGenre.add(historicalFilter);
		filterByGenre.add(comicFilter);
		filterByGenre.add(mangaFilter);
		filterByGenre.add(childrenFilter);
		// addition of the voices to the sort menu
		filterBy.add(filterByTitle);
		filterBy.add(filterByAuthor);
		filterBy.add(filterByYear);
		filterBy.add(filterByPages);
		filterBy.add(filterByGenre);

		// the fourth voice on the menu will be edit
		edit = new JMenu("Edit");
		// possibilities to chose what to filter by
		addBook = new JMenuItem("Add book");
		addAdmin = new JMenuItem("Add admin");
        editProfile = new JMenuItem("Edit profile");
		edit.add(addBook);
		edit.add(addAdmin);
        edit.add(editProfile);

		// the fifth voice on the menu will be view loans
		view = new JMenu("View");
		// possibilities to chose what to view
		loans = new JMenuItem("View loans");
		requests = new JMenuItem("View requests");
		people = new JMenuItem("View accounts");
		view.add(loans);
		view.add(requests);
		view.add(people);

		// addition of the five voices to the menu bar
		menuBar.add(sortBy);
		menuBar.add(filterBy);
		menuBar.add(edit);
		menuBar.add(view);

		// set keyboard shortcuts
		sortBy.setMnemonic(KeyEvent.VK_S); // Alt+S for sortMenu
		filterBy.setMnemonic(KeyEvent.VK_F); // Alt+F for filterMenu
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
		addBook.setMnemonic(KeyEvent.VK_B); // B for add Book
		loans.setMnemonic(KeyEvent.VK_V); // V for view
		requests.setMnemonic(KeyEvent.VK_R); // R for requests
		people.setMnemonic(KeyEvent.VK_A); // A for admin
		addAdmin.setMnemonic(KeyEvent.VK_A); // A for add Admin
		less.setMnemonic(KeyEvent.VK_L); // L for less
		greater.setMnemonic(KeyEvent.VK_G); // G for greater
        editProfile.setMnemonic(KeyEvent.VK_P); // P for profile

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
		addBook.addActionListener(this);
		addAdmin.addActionListener(this);
		loans.addActionListener(this);
		requests.addActionListener(this);
		people.addActionListener(this);
		actionFilter.addActionListener(this);
		fantasyFilter.addActionListener(this);
		adventureFilter.addActionListener(this);
		romanceFilter.addActionListener(this);
		comedyFilter.addActionListener(this);
		scifiFilter.addActionListener(this);
		mysteryFilter.addActionListener(this);
		thrillerFilter.addActionListener(this);
		historicalFilter.addActionListener(this);
		comicFilter.addActionListener(this);
		mangaFilter.addActionListener(this);
		childrenFilter.addActionListener(this);
		less.addActionListener(this);
		greater.addActionListener(this);
        editProfile.addActionListener(this);

		// set of the menu bar in the frame
		this.setJMenuBar(menuBar);

		// scroll bar setup
		backgroundPanel = new JPanel();
		backgroundPanel.setLayout(new GridLayout(0, 2));
		this.add(backgroundPanel);
		JScrollPane s = new JScrollPane(backgroundPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(s);

		List<Book> bookList = ArchiveManager.getInstance().getSortedBooksBy(ArchiveManager.Criteria.TITLE);
		setBooksInFrame(bookList);

		// Set the frame size as the preferred one
		pack();
		// Limit the size to the screen size
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()),
				(int) Math.min(getSize().getHeight(), screenDim.getHeight()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ACTION PERMORMED WHEN A VOICE OF THE MENUBAR IS SELECTED
		// ------------------da sostituire con i metodi di Marco penso------------------
		// if sort by title
		if (e.getSource() == sortByTitle) {
			this.setBooksInFrame(am.getSortedBooksBy(ArchiveManager.Criteria.TITLE));
		}
		// if sort by author
		if (e.getSource() == sortByAuthor) {
			this.setBooksInFrame(am.getSortedBooksBy(ArchiveManager.Criteria.AUTHOR));
		}
		// if sort by year
		if (e.getSource() == sortByYear) {
			this.setBooksInFrame(am.getSortedBooksBy(ArchiveManager.Criteria.RELEASE_YEAR));
		}
		// if sort by number fo pages
		if (e.getSource() == sortByPages) {
			this.setBooksInFrame(am.getSortedBooksBy(ArchiveManager.Criteria.NUM_PAGES));
		}
		// if sort by genre
		if (e.getSource() == sortByGenre) {
			this.setBooksInFrame(am.getSortedBooksBy(ArchiveManager.Criteria.GENRE));
		}
		// if filter by title
		if (e.getSource() == filterByTitle) {
			String title = JOptionPane.showInputDialog("Title filter");
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.TITLE, title));
		}
		// if filter by author
		if (e.getSource() == filterByAuthor) {
			String author = JOptionPane.showInputDialog("Author filter");
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.AUTHOR, author));
		}
		// if filter by year
		if (e.getSource() == filterByYear) {
			Year year;
			try {
				year = Year.parse(JOptionPane.showInputDialog("Year filter").trim());
				this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.RELEASE_YEAR, year));
			} catch (Exception eParseYear) {
				JOptionPane.showMessageDialog(null, "Input is not a valid year", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		// if filter by number of pages

		// if less or equal
		if (e.getSource() == less) {
			int nPages = Integer.parseInt(JOptionPane.showInputDialog("Number of pages filter"));
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.NUM_PAGES_LESS, nPages));
		}
		// if less or greater
		if (e.getSource() == greater) {
			int nPages = Integer.parseInt(JOptionPane.showInputDialog("Number of pages filter"));
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.NUM_PAGES_MORE, nPages));
		}

		// filter by genre

		// filter genre action
		if (e.getSource() == actionFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.ACTION));
		}

		// filter genre fantasy
		if (e.getSource() == fantasyFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.FANTASY));
		}

		// filter genre adventure
		if (e.getSource() == adventureFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.ADVENTURE));
		}

		// filter genre romance
		if (e.getSource() == romanceFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.ROMANCE));
		}

		// filter genre comedy
		if (e.getSource() == comedyFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.COMEDY));
		}

		// filter genre scifi
		if (e.getSource() == scifiFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.SCIFI));
		}

		// filter genre mystery
		if (e.getSource() == mysteryFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.MYSTERY));
		}

		// filter genre thriller
		if (e.getSource() == thrillerFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.THRILLER));
		}

		// filter genre historical
		if (e.getSource() == historicalFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.HISTORICAL));
		}

		// filter genre comic
		if (e.getSource() == comicFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.COMIC));
		}

		// filter genre manga
		if (e.getSource() == mangaFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.MANGA));
		}

		// filter genre children
		if (e.getSource() == childrenFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.CHILDREN));
		}
		// if add book
		if (e.getSource() == addBook) {
			try {
				this.dispose();
				new NewBookFrame(admin);
			} catch (InvalidAdminException e1) {
				e1.printStackTrace();
				System.exit(ABORT);
			}
		}
		// if add admin
		if (e.getSource() == addAdmin) {
			try {
				this.dispose();
				new NewAdminFrame(admin);
			} catch (InvalidAdminException e1) {
				e1.printStackTrace();
				System.exit(ABORT);
			}
		}
		// if view loans
		if (e.getSource() == loans) {
			try {
				this.dispose();
				new LoanViewerFrameAdmin(admin);
			} catch (InvalidAdminException e1) {
				e1.printStackTrace();
				System.exit(ABORT);
			}
		}
		if (e.getSource() == requests) {
			try {
				this.dispose();
				new RequestsViewerFrameAdmin(admin);
			} catch (InvalidAdminException e1) {
				e1.printStackTrace();
				System.exit(ABORT);
			}
		}
		if (e.getSource() == people) {
			try {
				this.dispose();
				new PersonViewerFrame(admin);
			} catch (InvalidAdminException e1) {
				e1.printStackTrace();
				System.exit(ABORT);
			}
		}
        if (e.getSource() == editProfile) {
            new EditProfileFrame(admin);
        }
	}

	public void redraw() {
		setBooksInFrame(ArchiveManager.getInstance().getSortedBooksBy(ArchiveManager.Criteria.TITLE));
	}

	private void setBooksInFrame(List<Book> books) {
		this.backgroundPanel.removeAll();
		
        if(books.isEmpty()) {
            JLabel noBooksLabel = new JLabel("  There are no books  ");
            noBooksLabel.setForeground(Color.GRAY);
            noBooksLabel.setFont(new Font("Lexend", Font.ITALIC, 20));
            this.backgroundPanel.add(noBooksLabel);
        } else {
            for (Book b : books) {
                BookPanelAdmin bookPanel = new BookPanelAdmin(admin, b, this);
                this.backgroundPanel.add(bookPanel);
            }
        }
        
		this.revalidate();
		this.repaint();
		pack();
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()),
				(int) Math.min(getSize().getHeight(), screenDim.getHeight()));
	}

}
