package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import users.User;
import items.LoanRequest;
import items.Loanable;

public class BorrowedBookPanelUser extends JPanel {
	
	public BorrowedBookPanelUser(User user, LoanRequest lr) {
        
		this.setLayout(new GridLayout(0, 1));
		Loanable item = lr.getRequested();
		
		JLabel nameLabel = new JLabel(item.getName());
		JLabel dateLabel = new JLabel("" + item.getDueDate());
		
		
		nameLabel.setForeground(Color.BLACK);
		dateLabel.setForeground(Color.BLACK);
		nameLabel.setFont(new Font("Lexend", Font.PLAIN, 12));
		dateLabel.setFont(new Font("Lexend", Font.ITALIC, 12));
		
		this.add(nameLabel);
		this.add(dateLabel);
		
		
		
		//set the border for the bookPanel
		Border border = BorderFactory.createLineBorder(new Color(0xA9A9A9), 5);
		this.setBorder(border);
	}

}
