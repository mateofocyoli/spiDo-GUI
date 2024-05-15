package GUI;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import exceptions.InvalidAdminException;
import items.Book;
import users.Admin;
import users.managers.PersonManager;
import users.sanctions.BookDelayedSanction;
import users.sanctions.BookLostSanction;
import users.sanctions.BookRuinedSanction;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class BorrowedBookPanelAdmin extends JPanel implements ActionListener {

    private Admin admin;
    private Book book;
    private LoanViewerFrameAdmin parent;
    private JButton returnButton;

    public BorrowedBookPanelAdmin(Admin admin, Book book, LoanViewerFrameAdmin parent) {
        this.admin = admin;
        this.book = book;
        this.parent = parent;

        // the dataPanel will contain the book data
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(2, 0));

        JLabel titleLabel = new JLabel(book.getTitle());
        JLabel dateLabel = new JLabel(book.getDueDate().toString());
        JLabel borrowerLabel = new JLabel("-null-");
        if(book.getBorrower() != null)
            borrowerLabel.setText(book.getBorrower().getCredentials().getUsername());

        titleLabel.setForeground(Color.BLACK);
        dateLabel.setForeground(Color.BLACK);
        borrowerLabel.setForeground(Color.BLACK);

        titleLabel.setFont(new Font("Lexend", Font.PLAIN, 12));
        dateLabel.setFont(new Font("Lexend", Font.ITALIC, 12));
        borrowerLabel.setFont(new Font("Lexend", Font.ITALIC, 12));

        dataPanel.add(borrowerLabel);
        dataPanel.add(titleLabel);
        dataPanel.add(dateLabel);

        // the buttonPanel will contain the button
        JPanel buttonPanel = new JPanel();

        returnButton = new JButton("Return");
        returnButton.setFocusable(false);
        returnButton.setForeground(Color.BLACK);
        returnButton.addActionListener(this);

        buttonPanel.add(returnButton);

        // added the two panels to the main panel
        this.add(dataPanel);
        this.add(buttonPanel);

        // set the border for the bookPanel
        Border border = BorderFactory.createLineBorder(new Color(0xA9A9A9), 5);
        this.setBorder(border);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            String[] returnStates = { "Normal", "Ruined", "Lost" };

            int response = JOptionPane.showOptionDialog(null, "In which state has the book been returned?",
                    "Select book state", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    returnStates, 0);

            PersonManager pm = PersonManager.getInstance();
            try {
                switch (response) {
                    case 0: // Normal
                        // Set the book as IN_ARCHIVE
                        // Check if the due date has been passed
                        if (LocalDate.now().isAfter(book.getDueDate()) && book.getBorrower() != null) {
                            // If so, sanction the user
                            pm.sanction(admin, book.getBorrower(), new BookDelayedSanction(LocalDate.now(), book));
                        }
                        book.setOnArchive(admin);
                        break;
                    case 1: // Ruined
                        // Set the book as RUINED
                        // Check if the due date has been passed
                        if (LocalDate.now().isAfter(book.getDueDate()) && book.getBorrower() != null) {
                            // If so, sanction the user
                            pm.sanction(admin, book.getBorrower(), new BookDelayedSanction(LocalDate.now(), book));
                        }

                        if(book.getBorrower() != null)
                            pm.sanction(admin, book.getBorrower(), new BookRuinedSanction(LocalDate.now(), book));
                        
                            book.setRuined(admin);
                        break;
                    case 2: // Lost
                        // Sanction the user and set the book as lost
                        if(book.getBorrower() != null)
                            pm.sanction(admin, book.getBorrower(), new BookLostSanction(LocalDate.now(), book));
                        
                        book.setLost(admin);
                    default:
                        break;
                }
            } catch (InvalidAdminException e1) {
                JOptionPane.showMessageDialog(this, "You don't have the privileges",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        parent.redraw();
    }
}
