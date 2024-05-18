package GUI.adminView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import exceptions.InvalidAdminException;
import exceptions.NotInArchiveException;
import items.Book;
import items.managers.ArchiveManager;
import users.Admin;
import GUI.OptionPaneYesNo;

public class BookPanelAdmin extends JPanel implements ActionListener {

    private static final String FONT_NAME = "Lexend";
    private Admin admin;
    private Book book;
    private JButton removeButton, editButton;
    private AdminFrame adminFrame;

    BookPanelAdmin(Admin admin, Book book, AdminFrame adminFrame) {
        this.admin = admin;
        this.book = book;
        this.adminFrame = adminFrame;

        // the dataPanel will contain the book data
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(6, 0));

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
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        authorLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 20));
        genreLabel.setFont(new Font(FONT_NAME, Font.ITALIC, 12));
        yearLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 12));
        pagesLabel.setFont(new Font(FONT_NAME, Font.ITALIC, 12));

        dataPanel.add(titleLabel);
        dataPanel.add(authorLabel);
        dataPanel.add(genreLabel);
        dataPanel.add(yearLabel);
        dataPanel.add(pagesLabel);
        dataPanel.add(state);

        // the buttonPanel will contain the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 0, 5, 5));

        editButton = new JButton("Edit");
        editButton.setFocusable(false);
        editButton.setForeground(Color.BLACK);
        editButton.addActionListener(this);

        removeButton = new JButton("Remove");
        removeButton.setFocusable(false);
        removeButton.setForeground(Color.BLACK);
        removeButton.addActionListener(this);

        buttonPanel.add(removeButton);
        buttonPanel.add(editButton);

        // added the two panels tho the main panel
        this.add(dataPanel);
        this.add(buttonPanel);

        // set the border for the bookPanel
        Border border = BorderFactory.createLineBorder(new Color(0xA9A9A9), 5);
        this.setBorder(border);

        // changes the color of state label based on state
        switch (book.getState()) {
            case IN_ARCHIVE:
                state.setForeground(Color.GREEN);
                buttonPanel.add(removeButton);
                break;
            case ON_LOAN:
                state.setForeground(Color.RED);
                break;
            case RUINED:
                state.setForeground(Color.ORANGE);
                buttonPanel.add(removeButton);
                break;
            case LOST:
                state.setForeground(Color.GRAY);
                buttonPanel.add(removeButton);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == removeButton) {
            if (!OptionPaneYesNo.show("Confirm deletion",
                    "Are you sure you want to delete this book?",
                    OptionPaneYesNo.Options.CONFIRM_CANCEL))
                return;

            ArchiveManager am = ArchiveManager.getInstance();
            try {
                am.removeBook(admin, book);
            } catch (InvalidAdminException e1) {
                JOptionPane.showMessageDialog(this, "You don't have the privileges", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (NotInArchiveException e1) {
                JOptionPane.showMessageDialog(this,
                        "An error as occured. It seems that the book is no longer present", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            adminFrame.redraw();
        }
        else if (e.getSource() == editButton) {
            adminFrame.dispose();
            new EditBookFrame(admin, book);
        }
    }
}
