package GUI.userView;

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
    private JPanel rightPanel;
    private JPanel leftPanel;
	
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
		
		
		
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(0,1));
		rightPanel.setLayout(new GridLayout(0,1));
		
		JScrollPane lS = new JScrollPane(leftPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane rS = new JScrollPane(rightPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(lS);
		this.add(rS);
		
		LoanRequestsManager lrm = LoanRequestsManager.getInstance();
        List<LoanRequest> requests = lrm.filterBy(LoanRequestsManager.Criteria.APPLICANT, user);

        ArchiveManager am = ArchiveManager.getInstance();
        List<Book> loans = am.filterBy(ArchiveManager.Criteria.LOAN_STATE, Loanable.LoanState.ON_LOAN);
        
        setRequestsAndLoansInFrame(requests, loans);

        pack();
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()), (int) Math.min(getSize().getHeight(), screenDim.getHeight()));
	}
	
	public void redraw() {
        this.dispose();
        new LoanViewerFrameUser(user);
    }

    public void setRequestsAndLoansInFrame(List<LoanRequest> requests, List<Book> loans) {
        rightPanel.removeAll();
        leftPanel.removeAll();

        JLabel leftTitle = new JLabel("Borrowed books", SwingConstants.CENTER);
		JLabel rightTitle = new JLabel("Requested books", SwingConstants.CENTER);
		leftTitle.setFont(new Font("Lexend", Font.BOLD, 25));
		leftPanel.add(leftTitle);
		rightTitle.setFont(new Font("Lexend", Font.BOLD, 25));
		rightPanel.add(rightTitle);

        if(requests.isEmpty()) {
            JLabel noRequestsLabel = new JLabel("  There are no requests  ");
            noRequestsLabel.setForeground(Color.GRAY);
            noRequestsLabel.setFont(new Font("Lexend", Font.ITALIC, 20));
            rightPanel.add(noRequestsLabel);
        } else {
            for(LoanRequest lr : requests) {
                rightPanel.add(new RequestedBookPanelUser(user, lr, this));
            }
        }

        if(loans.isEmpty()) {
            JLabel noBooksLabel = new JLabel("  There are no borrowed books  ");
            noBooksLabel.setForeground(Color.GRAY);
            noBooksLabel.setFont(new Font("Lexend", Font.ITALIC, 20));
            leftPanel.add(noBooksLabel);
        } else {
            for(Book b : loans) {
                if(b.getBorrower().equals(user))
                    leftPanel.add(new BorrowedBookPanelUser(user, b));
            }
        }
    }
}
