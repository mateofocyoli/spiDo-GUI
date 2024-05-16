package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import exceptions.InvalidUserException;
import exceptions.NotInArchiveException;
import items.managers.LoanRequestsManager;
import users.User;
import items.Book;

public class BookPanelUser extends JPanel implements ActionListener {
	
	private JButton button;
	private LoanRequestsManager lrm;
	private Book book;
	private User user;
	
	public BookPanelUser(User user, Book book) {
		
		this.book = book;
		this.user = user;
		
		//the dataPanel will contain the book data
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(6,0));
		
		JLabel titleLabel = new JLabel(book.getTitle());
		JLabel authorLabel = new JLabel("-" + book.getAuthor());
		JLabel genreLabel = new JLabel("" + book.getGenre());
		JLabel yearLabel = new JLabel("[" + book.getReleaseYear() + "]");
		JLabel pagesLabel = new JLabel(book.getNumPages() + " pages");
		JLabel state = new JLabel("" + book.getState());
		
		titleLabel.setForeground(Color.BLACK);
		authorLabel.setForeground(Color.BLACK);
		genreLabel.setForeground(Color.BLACK);
		yearLabel.setForeground(Color.BLACK);
		pagesLabel.setForeground(Color.BLACK);
		titleLabel.setFont(new Font("Lexend", Font.BOLD, 20));
		authorLabel.setFont(new Font("Lexend", Font.PLAIN, 20));
		genreLabel.setFont(new Font("Lexend", Font.ITALIC, 12));
		yearLabel.setFont(new Font("Lexend", Font.PLAIN, 12));
		pagesLabel.setFont(new Font("Lexend", Font.ITALIC, 12));
		
		dataPanel.add(titleLabel);
		dataPanel.add(authorLabel);
		dataPanel.add(genreLabel);
		dataPanel.add(yearLabel);
		dataPanel.add(pagesLabel);
		dataPanel.add(state);
		
		
		//the buttonPanel will contain the button
		JPanel buttonPanel = new JPanel();
		
		button = new JButton("Borrow");
		button.setFocusable(false);
		button.setForeground(Color.BLACK);
		button.addActionListener(this);
		
		
		
		
		
		//added the two panels tho the main panel
		this.add(dataPanel);
		this.add(buttonPanel);
		
		
		//set the border for the bookPanel
		Border border = BorderFactory.createLineBorder(new Color(0xA9A9A9), 5);
		this.setBorder(border);
		
		
		//changes the color of state label based on state
		switch(book.getState()) {
		case IN_ARCHIVE: state.setForeground(Color.GREEN);
						 buttonPanel.add(button);
		break;
		case ON_LOAN: state.setForeground(Color.RED);
		break;
		case RUINED: state.setForeground(Color.ORANGE);
		break;
		case LOST: state.setForeground(Color.GRAY);
		break;
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button) {
			lrm = LoanRequestsManager.getInstance();
			try {
				lrm.makeBookRequest(user, book);
			} catch (InvalidUserException e1) {
				JOptionPane.showMessageDialog(this, "You are not a valid user", "Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} catch (NotInArchiveException e1) {
				JOptionPane.showMessageDialog(this, "The book is not in the archive", "Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
		
	}

}
