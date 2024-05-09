package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Year;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import exceptions.InvalidAdminException;
import exceptions.InvalidBookException;
import items.Book;
import items.Book.Genre;
import items.managers.ArchiveManager;
import users.Admin;

public class NewBookFrame extends JFrame implements ActionListener {

    private Admin applicant;

    private JLabel titleLabel, authorLabel, genreLabel, yearLabel, numPagesLabel;
    private JTextField titleTextField, authorTextField, numPagesTextField, yearTextField;
    private JComboBox<Genre> genreComboBox;
    private JButton addButton;

    public NewBookFrame(Admin applicant) throws InvalidAdminException {
        if (applicant == null) {
            throw new InvalidAdminException("Admin object can not be null");
        }
        this.applicant = applicant;

        this.setTitle("Add new book");
        this.setSize(500, 550);
        this.setResizable(false);
        this.setVisible(true);

        // Open the admin frame instead of closing
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ((JFrame) e.getSource()).dispose();
                try {
                    new AdminFrame(applicant);
                } catch (InvalidAdminException e1) {
                    e1.printStackTrace();
                    System.exit(ABORT);
                }
            }
        });

        this.setLayout(new BorderLayout(0, 40));

        // layout frame
        // top panel will have the title
        // center panel will have all the book fields
        // bottom panel will have the add comfirmation button
        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // TOP PANEL SETUP
        JLabel mainLabel = new JLabel();
        mainLabel.setForeground(Color.RED);
        mainLabel.setFont(new Font("Lexend", Font.BOLD, 40));
        mainLabel.setText("Insert book details");
        topPanel.add(mainLabel);

        // CENTER PANEL SETUP
        // dividing the center panel in 14 different panels (7 labels and 7 inputs)
        centerPanel.setLayout(new GridLayout(7, 2, 15, 15));
        JPanel p11 = new JPanel();
        JPanel p12 = new JPanel();
        JPanel p21 = new JPanel();
        JPanel p22 = new JPanel();
        JPanel p31 = new JPanel();
        JPanel p32 = new JPanel();
        JPanel p41 = new JPanel();
        JPanel p42 = new JPanel();
        JPanel p51 = new JPanel();
        JPanel p52 = new JPanel();

        // title label setup
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Lexend", Font.BOLD, 20));
        titleLabel.setText("Title:");
        p11.add(titleLabel);
        centerPanel.add(p11);

        // title textfield setup
        titleTextField = new JTextField(14);
        titleTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
        p12.add(titleTextField);
        centerPanel.add(p12);

        // author label setup
        authorLabel = new JLabel();
        authorLabel.setFont(new Font("Lexend", Font.BOLD, 20));
        authorLabel.setText("Author:");
        p21.add(authorLabel);
        centerPanel.add(p21);

        // author textfield setup
        authorTextField = new JTextField(14);
        authorTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
        p22.add(authorTextField);
        centerPanel.add(p22);

        // year label setup
        yearLabel = new JLabel();
        yearLabel.setFont(new Font("Lexend", Font.BOLD, 20));
        yearLabel.setText("Release year:");
        p31.add(yearLabel);
        centerPanel.add(p31);

        // year textfield setup
        yearTextField = new JTextField(14);
        yearTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
        p32.add(yearTextField);
        centerPanel.add(p32);

        // numpages label setup
        numPagesLabel = new JLabel();
        numPagesLabel.setFont(new Font("Lexend", Font.BOLD, 20));
        numPagesLabel.setText("Number of pages:");
        p41.add(numPagesLabel);
        centerPanel.add(p41);

        // numpages textfield setup
        numPagesTextField = new JTextField(14);
        numPagesTextField.setFont(new Font("Lexend", Font.PLAIN, 20));
        p42.add(numPagesTextField);
        centerPanel.add(p42);

        // genre label setup
        genreLabel = new JLabel();
        genreLabel.setFont(new Font("Lexend", Font.BOLD, 20));
        genreLabel.setText("Genre:");
        p51.add(genreLabel);
        centerPanel.add(p51);

        // genre combobox setup
        genreComboBox = new JComboBox<>(Genre.values());
        genreComboBox.setFont(new Font("Lexend", Font.PLAIN, 15));
        p52.add(genreComboBox);
        centerPanel.add(p52);

        // BOTTOM PANEL SETUP
        addButton = new JButton();
        addButton.setText("Add");
        bottomPanel.add(addButton);
        addButton.addActionListener(this);

        // east panel added purely for graphic reasons
        // it adds a little bit of an edge to the left
        JPanel eastPanel = new JPanel();
        this.add(eastPanel, BorderLayout.EAST);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // if add button is pressed
        if (e.getSource() == addButton) {
            String title = titleTextField.getText();
            String author = authorTextField.getText();
            Genre genre = (Genre) genreComboBox.getSelectedItem();
            String yearString = yearTextField.getText();
            String numPagesString = numPagesTextField.getText();

            if (title.isBlank()) {
                JOptionPane.showMessageDialog(null, "Title field can not be empty");
                return;
            }

            if (author.isBlank()) {
                JOptionPane.showMessageDialog(null, "Author field can not be empty");
                return;
            }

            Year year;
            try {
                year = Year.parse(yearString);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Year not valid");
                return;
            }

            int numPages;
            try {
                numPages = Integer.parseInt(numPagesString);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Number of pages not valid");
                return;
            }

            Book book = new Book(title, author, genre, year, numPages, "");
            try {
                ArchiveManager.getInstance().addBook(applicant, book);
            } catch (InvalidBookException e1) {
                JOptionPane.showMessageDialog(null, "Book not valid, it can not be added");
                return;
            } catch (InvalidAdminException e1) {
                JOptionPane.showMessageDialog(null, "You have not the permission to add new books");
                return;
            }

            JOptionPane.showMessageDialog(null, "Book added succesfully");
            this.dispose();
            try {
                new AdminFrame(applicant);
            } catch (InvalidAdminException e1) {
                e1.printStackTrace();
            }
        }
    }
}
