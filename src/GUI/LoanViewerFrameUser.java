package GUI;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.util.List;

import items.Book;
import items.LoanRequest;
import items.Loanable;
import items.managers.ArchiveManager;
import items.managers.LoanRequestsManager;
import users.User;

public class LoanViewerFrameUser extends JFrame {

	private User user;
	
	public LoanViewerFrameUser(User user) {
		
		this.user = user;
		
		//Frame setup
		this.setTitle("Loan Viewer Frame - " + user.getCredentials().getUsername());
		this.setResizable(true);
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
        List<LoanRequest> requests = lrm.filterBy(LoanRequestsManager.Criteria.APPLICANT, user);
        if(requests.isEmpty()) {
            JLabel noRequestsLabel = new JLabel("  There are no requests  ");
            noRequestsLabel.setForeground(Color.GRAY);
            noRequestsLabel.setFont(new Font("Lexend", Font.ITALIC, 20));
            rightPanel.add(noRequestsLabel);
        } else {
            for(LoanRequest lr : lrm.filterBy(LoanRequestsManager.Criteria.APPLICANT, user)) {
                rightPanel.add(new RequestedBookPanelUser(user, lr, this));
            }
        }

        ArchiveManager am = ArchiveManager.getInstance();
        List<Book> onLoan = am.filterBy(ArchiveManager.Criteria.LOAN_STATE, Loanable.LoanState.ON_LOAN);
        if(requests.isEmpty()) {
            JLabel noBooksLabel = new JLabel("  There are no borrowed books  ");
            noBooksLabel.setForeground(Color.GRAY);
            noBooksLabel.setFont(new Font("Lexend", Font.ITALIC, 20));
            leftPanel.add(noBooksLabel);
        } else {
            for(Book b : onLoan) {
                if(b.getBorrower().equals(user))
                    leftPanel.add(new BorrowedBookPanelUser(user, b));
            }
        }

        pack();
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()), (int) Math.min(getSize().getHeight(), screenDim.getHeight()));
	}
	
	public void redraw() {
        this.dispose();
        new LoanViewerFrameUser(user);
        
    }
}
