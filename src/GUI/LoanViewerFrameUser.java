package GUI;

import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import items.LoanRequest;
import items.managers.LoanRequestsManager;
import users.User;

public class LoanViewerFrameUser extends JFrame {

	public LoanViewerFrameUser(User user) {
		//Frame setup
		this.setTitle("Loan Viewer Frame - " + user.getCredentials().getUsername());
		this.setSize(500, 550);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	                ((JFrame) e.getSource()).dispose();
	                new UserFrame(user);
	            }
	        });
		
		this.setLayout(new GridLayout(0, 2));
		
		
		
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(0,1));
		rightPanel.setLayout(new GridLayout(0,1));
		
		JScrollPane lS = new JScrollPane(leftPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane rS = new JScrollPane(rightPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(lS);
		this.add(rS);
		
		
		
		JLabel leftTitle = new JLabel("Borrowed books", SwingConstants.CENTER);
		JLabel rightTitle = new JLabel("Requested books", SwingConstants.CENTER);
		leftTitle.setFont(new Font("Lexend", Font.BOLD, 25));
		leftPanel.add(leftTitle);
		rightTitle.setFont(new Font("Lexend", Font.BOLD, 25));
		rightPanel.add(rightTitle);
		
		LoanRequestsManager lrm = LoanRequestsManager.getInstance();
		for(LoanRequest lr : lrm.filterBy(LoanRequestsManager.Criteria.APPLICANT, user)) {
			if(lr.isAccepted()) {
				leftPanel.add(new BorrowedBookPanelUser(user, lr));
			} else {
				rightPanel.add(new RequestedBookPanelUser(user, lr));
			}
		}
	}
}
