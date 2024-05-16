package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import exceptions.InvalidAdminException;
import items.Book;
import items.Loanable;
import items.managers.ArchiveManager;
import users.Admin;
import users.User;

public class LoanViewerFrameAdmin extends JFrame {

    private Admin admin;

    /**
     * JFrame that shows the books with ON_LOAN state.
     * Use this constructor to show all the loaned books in the archive.
     * @param admin The admin that is requesting to see the loaned books
     * @throws InvalidAdminException If the admin object is not valid
     */
    public LoanViewerFrameAdmin(Admin admin) throws InvalidAdminException {
        if (admin == null)
            throw new InvalidAdminException("The admin object can not be null.");
        this.admin = admin;

        // Frame setup
        this.setTitle("Loan Viewer Frame Admin - " + admin.getCredentials().getUsername());
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ((JFrame) e.getSource()).dispose();
                try {
                    new AdminFrame(admin);
                } catch (InvalidAdminException e1) {
                    e1.printStackTrace();
                    System.exit(ABORT);
                }
            }
        });
        JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane);

        ArchiveManager am = ArchiveManager.getInstance();
        List<Book> onLoan = am.filterBy(ArchiveManager.Criteria.LOAN_STATE, Loanable.LoanState.ON_LOAN);
        for(Book b : onLoan) {
            panel.add(new BorrowedBookPanelAdmin(admin, b, this));
        }

        pack();
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()), (int) Math.min(getSize().getHeight(), screenDim.getHeight()));
    }

    /**
     * JFrame that shows the books with ON_LOAN state.
     * Use this constructor to show only the loaned books of one user.
     * @param admin The admin that is requesting to see the loaned books
     * @param user The user whom books are shown
     * @throws InvalidAdminException If the admin object is not valid
     */
    public LoanViewerFrameAdmin(Admin admin, User user) throws InvalidAdminException {
        if (admin == null)
            throw new InvalidAdminException("The admin object can not be null.");
        this.admin = admin;

        // Frame setup
        this.setTitle("Loan Viewer Frame Admin - " + admin.getCredentials().getUsername());
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ((JFrame) e.getSource()).dispose();
                try {
                    new AdminFrame(admin);
                } catch (InvalidAdminException e1) {
                    e1.printStackTrace();
                    System.exit(ABORT);
                }
            }
        });

        JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		
        JLabel titleLabel = new JLabel("Loans of " + user.getCredentials().getUsername());
        titleLabel.setFont(new Font("Lexend", Font.BOLD, 20));
        panel.add(titleLabel);

		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane);

        ArchiveManager am = ArchiveManager.getInstance();
        List<Book> onLoan = am.filterBy(ArchiveManager.Criteria.LOAN_STATE, Loanable.LoanState.ON_LOAN);
        for(Book b : onLoan) {
            if(b.getBorrower() == user)
                panel.add(new BorrowedBookPanelAdmin(admin, b, this));
        }

        pack();
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()), (int) Math.min(getSize().getHeight(), screenDim.getHeight()));
    }

    public void redraw() {
        this.dispose();
        try {
            new LoanViewerFrameAdmin(admin);
        } catch (InvalidAdminException e) {
            e.printStackTrace();
            System.exit(ABORT);
        }
    }
}
