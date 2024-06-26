package GUI.userView;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import exceptions.InvalidUserException;
import exceptions.RequestNotPresentException;
import items.LoanRequest;
import items.Loanable;
import items.managers.LoanRequestsManager;
import users.User;

public class RequestedBookPanelUser extends JPanel implements ActionListener {
	
	private JButton button;
	private LoanRequestsManager lrm;
	private LoanRequest lr;
	private User user;
	private LoanViewerFrameUser lvfu;
	
	public RequestedBookPanelUser(User user, LoanRequest lr, LoanViewerFrameUser lvfu) {
		
		this.lr = lr;
		this.user = user;
		this.lvfu = lvfu;
		
		//the dataPanel will contain the book data
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(2,0));
		Loanable item = lr.getRequested();
		
		JLabel nameLabel = new JLabel(item.getName());
		JLabel dateLabel = new JLabel("" + item.getDueDate());
		
		nameLabel.setForeground(Color.BLACK);
		dateLabel.setForeground(Color.BLACK);
		nameLabel.setFont(new Font("Lexend", Font.BOLD, 12));
		dateLabel.setFont(new Font("Lexend", Font.ITALIC, 12));
		
		dataPanel.add(nameLabel);
		dataPanel.add(dateLabel);
		
		
		//the buttonPanel will contain the button
		JPanel buttonPanel = new JPanel();
		
		button = new JButton("Cancel");
		button.setFocusable(false);
		button.setForeground(Color.BLACK);
		button.addActionListener(this);
		
		buttonPanel.add(button);
		
		
		
		//added the two panels tho the main panel
		this.add(dataPanel);
		this.add(buttonPanel);
		
		
		//set the border for the bookPanel
		Border border = BorderFactory.createLineBorder(new Color(0xA9A9A9), 5);
		this.setBorder(border);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button) {
			try {
				lrm = LoanRequestsManager.getInstance();
				lrm.cancelRequest(user, lr);
				lvfu.redraw();
			} catch (InvalidUserException | RequestNotPresentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	

} 
