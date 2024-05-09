package GUI;

import java.awt.*;

import javax.swing.*;

import items.Book;

public class BookPanel extends JPanel {
	
	public BookPanel() {
		
		this.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		JPanel southPanel = new JPanel();
		
		JPanel dataPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		JLabel titleLabel = new JLabel("Title");
		JLabel authorLabel = new JLabel("Author");
		JLabel genreLabel = new JLabel("Genre");
		JLabel yearLabel = new JLabel("Year");
		JLabel pagesLabel = new JLabel("nPages");
		
		JButton button = new JButton("Action");
		button.setFocusable(false);
		
//		dataPanel.setBackground(Color.white);
//		buttonPanel.setBackground(Color.red);
		
		dataPanel.setLayout(new GridLayout(5,0));
		
		dataPanel.add(titleLabel);
		dataPanel.add(authorLabel);
		dataPanel.add(genreLabel);
		dataPanel.add(yearLabel);
		dataPanel.add(pagesLabel);
		buttonPanel.add(button);
		
		
		centerPanel.add(dataPanel);
		centerPanel.add(buttonPanel);
		
		
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		
//		b.setLayout(new GridLayout());
		
		
	}

}
