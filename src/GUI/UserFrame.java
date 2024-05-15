package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.time.Year;
import java.util.List;

import javax.swing.*;
import app.AppCloser;
import items.Book;
import items.managers.ArchiveManager;
import users.User;

public class UserFrame extends JFrame implements ActionListener {
	
	private JMenuBar menuBar;
	private JMenu sortBy, filterBy, view;
	private JMenu filterByGenre;
	private JMenuItem sortByTitle, sortByAuthor, sortByYear, sortByPages, sortByGenre;
	private JMenuItem filterByTitle, filterByAuthor, filterByYear, filterByPages;
	private JMenuItem loans;
	private JMenuItem actionFilter, fantasyFilter, adventureFilter, romanceFilter, comedyFilter, scifiFilter, mysteryFilter, thrillerFilter, historicalFilter, comicFilter, mangaFilter, childrenFilter;
	private JPanel backgroundPanel;
	private ArchiveManager am;
	private User user;
	
	UserFrame(User user) {
		
		this.user = user;
		
		//FRAME INITIALIZATION
		this.setTitle("User frame");
		this.setSize(750, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new AppCloser());
        this.am = ArchiveManager.getInstance();
        
        //sets the icon of the LoginFrame
        ImageIcon frameIcon = new ImageIcon(Path.of("assets", "spidogui.png").toString());
        this.setIconImage(frameIcon.getImage());
		
		
		//MENU BAR SETUP
		//addition of the menu bar at the top of the frame
		menuBar = new JMenuBar();
		
		//the first voice on the menu will be sort by
		sortBy = new JMenu("Sort by");
		//possibilities to chose what to sort by
		sortByTitle = new JMenuItem("Title");
		sortByAuthor = new JMenuItem("Author");
		sortByYear = new JMenuItem("Year");
		sortByPages = new JMenuItem("Number of pages");
		sortByGenre = new JMenuItem("Genre");
		
		//addition of the voices to the sort menu
		sortBy.add(sortByTitle);
		sortBy.add(sortByAuthor);
		sortBy.add(sortByYear);
		sortBy.add(sortByPages);
		sortBy.add(sortByGenre);
		
		//the second voice on the menu will be filter by
		filterBy = new JMenu("Filter by");
		//possibilities to chose what to filter by
		filterByTitle = new JMenuItem("Title");
		filterByAuthor = new JMenuItem("Author");
		filterByYear = new JMenuItem("Year");
		filterByPages = new JMenuItem("Number of pages");
		filterByGenre = new JMenu("Genre");
		//criteria for filter
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
		//add
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
		//addition of the voices to the sort menu
		filterBy.add(filterByTitle);
		filterBy.add(filterByAuthor);
		filterBy.add(filterByYear);
		filterBy.add(filterByPages);
		filterBy.add(filterByGenre);
		
		//the third voice on the menu bar will be view
		view = new JMenu("View");
		//possibilities to chose what to view
		loans = new JMenuItem("Loans");
		view.add(loans);
		
		//set keyboard shortcuts
		sortBy.setMnemonic(KeyEvent.VK_S);		//Alt+S for sortMenu
		filterBy.setMnemonic(KeyEvent.VK_F);	//Alt+F for filterMenu
		view.setMnemonic(KeyEvent.VK_V);		//Alt+V for view
		sortByTitle.setMnemonic(KeyEvent.VK_T);		//T for title
		filterByTitle.setMnemonic(KeyEvent.VK_T);
		sortByAuthor.setMnemonic(KeyEvent.VK_A);	//A for author
		filterByAuthor.setMnemonic(KeyEvent.VK_A);
		sortByYear.setMnemonic(KeyEvent.VK_Y);		//Y for year
		filterByYear.setMnemonic(KeyEvent.VK_Y);
		sortByPages.setMnemonic(KeyEvent.VK_N);		//N for number of pages
		filterByPages.setMnemonic(KeyEvent.VK_N);
		sortByGenre.setMnemonic(KeyEvent.VK_G);		//G for genre
		filterByGenre.setMnemonic(KeyEvent.VK_G);
		loans.setMnemonic(KeyEvent.VK_L);			//L for loans
		
		//addiction of the action listeners to perform methods when pressed
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
		loans.addActionListener(this);
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
		
		//addition of the three voices to the menu bar
		menuBar.add(sortBy);
		menuBar.add(filterBy);
		menuBar.add(view);
		//set of the menu bar in the frame
		this.setJMenuBar(menuBar);
		
		
		//scroll bar setup
		this.backgroundPanel = new JPanel();
		this.backgroundPanel.setLayout(new GridLayout(0, 2));
		this.add(backgroundPanel);
		JScrollPane s = new JScrollPane(backgroundPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(s);
		
		List<Book> bookList = am.getSortedBooksBy(ArchiveManager.Criteria.TITLE);
		//temporary//bottoni a cazzo
		for(Book b : bookList) {		
			BookPanelUser bookPanel = new BookPanelUser(user, b);
			backgroundPanel.add(bookPanel);
		}
		
		this.pack();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//ACTION PERMORMED WHEN A VOICE OF THE MENUBAR IS SELECTED
		//if sort by title
		if(e.getSource()==sortByTitle) {
			this.setBooksInFrame(am.getSortedBooksBy(ArchiveManager.Criteria.TITLE));
		}
		//if sort by author
		if(e.getSource()==sortByAuthor) {
			this.setBooksInFrame(am.getSortedBooksBy(ArchiveManager.Criteria.AUTHOR));
		}
		//if sort by year
		if(e.getSource()==sortByYear) {
			this.setBooksInFrame(am.getSortedBooksBy(ArchiveManager.Criteria.RELEASE_YEAR));
		}
		//if sort by number fo pages
		if(e.getSource()==sortByPages) {
			this.setBooksInFrame(am.getSortedBooksBy(ArchiveManager.Criteria.NUM_PAGES));
		}
		//if sort by genre
		if(e.getSource()==sortByGenre) {
			this.setBooksInFrame(am.getSortedBooksBy(ArchiveManager.Criteria.GENRE));
		}
		//if filter by title
		if(e.getSource()==filterByTitle) {
			String title = JOptionPane.showInputDialog("Title filter");
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.TITLE, title));
		}
		//if filter by author
		if(e.getSource()==filterByAuthor) {
			String author = JOptionPane.showInputDialog("Author filter");
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.AUTHOR, author));
		}
		//if filter by year
		if(e.getSource()==filterByYear) {
			Year year;
			try {
				year = Year.parse(JOptionPane.showInputDialog("Year filter").trim());
				this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.RELEASE_YEAR, year));
			} catch(Exception eParseYear) {
				JOptionPane.showMessageDialog(null, "Input is not a valid year", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		//if filter by number of pages
		if(e.getSource()==filterByPages) {
			try {
				int nPages = Integer.parseInt(JOptionPane.showInputDialog("Number of pages filter"));
				this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.NUM_PAGES, nPages));
			} catch(Exception eParsePages) {
				JOptionPane.showMessageDialog(null, "Input is not a valid number of pages", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		//filter by genre
		
		//filter genre action
		if(e.getSource()==actionFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.ACTION));
		}
		
		//filter genre fantasy
		if(e.getSource()==fantasyFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.FANTASY));
		}
		
		//filter genre adventure
		if(e.getSource()==adventureFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.ADVENTURE));
		}
		
		//filter genre romance
		if(e.getSource()==romanceFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.ROMANCE));
		}
		
		//filter genre comedy
		if(e.getSource()==comedyFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.COMEDY));
		}
		
		//filter genre scifi
		if(e.getSource()==scifiFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.SCIFI));
		}
		
		//filter genre mystery
		if(e.getSource()==mysteryFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.MYSTERY));
		}
		
		//filter genre thriller
		if(e.getSource()==thrillerFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.THRILLER));
		}
		
		//filter genre historical
		if(e.getSource()==historicalFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.HISTORICAL));
		}
		
		//filter genre comic
		if(e.getSource()==comicFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.COMIC));
		}
		
		//filter genre manga
		if(e.getSource()==mangaFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.MANGA));
		}
		
		//filter genre children
		if(e.getSource()==childrenFilter) {
			this.setBooksInFrame(am.filterBy(ArchiveManager.Criteria.GENRE, Book.Genre.CHILDREN));
		}
		
		
		//if view loans
		if(e.getSource()==loans) {
			new LoanViewerFrameUser(user);
			this.dispose();
			
			
			
			
			
		}
	}
	
	private void setBooksInFrame(List<Book> books) {
		this.backgroundPanel.removeAll();
		for(Book b : books) {		
			BookPanelUser bookPanel = new BookPanelUser(user, b);
			this.backgroundPanel.add(bookPanel);
		}
		this.revalidate();
		this.repaint();
	}

}
