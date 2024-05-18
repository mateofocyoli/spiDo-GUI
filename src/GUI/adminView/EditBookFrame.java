package GUI.adminView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.time.LocalDate;
import java.time.Year;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GUI.DateSelectPanel;
import exceptions.InvalidAdminException;

import items.Book;
import items.Loanable;
import items.Book.Genre;

import users.Admin;

public class EditBookFrame extends JFrame implements ActionListener {

    private static final String FONT_NAME = "Lexend";
    private static final String TITLE = "Edit book";
    private static final String INVALID_ADMIN_MSG = "only an admin con edit book's information";


    private Admin applicant;
    private Book toEdit;

    private JLabel titleLabel, authorLabel, genreLabel, yearLabel, numPagesLabel;
    private JTextField titleTextField, authorTextField, numPagesTextField, yearTextField;
    private JComboBox<Genre> genreComboBox;
	private DateSelectPanel datePanel;
    private JButton editButton;

    public EditBookFrame(Admin applicant, Book toEdit)  {
        this.applicant = applicant;
        this.toEdit = toEdit;

        this.setTitle(TITLE);
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
        mainLabel.setFont(new Font(FONT_NAME, Font.BOLD, 40));
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
        JPanel p61 = new JPanel();

        // title label setup
        titleLabel = new JLabel();
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        titleLabel.setText("Title:");
        p11.add(titleLabel);
        centerPanel.add(p11);

        // title textfield setup
        titleTextField = new JTextField(14);
        titleTextField.setFont(new Font(FONT_NAME, Font.PLAIN, 20));
        titleTextField.setText(toEdit.getTitle());
        p12.add(titleTextField);
        centerPanel.add(p12);

        // author label setup
        authorLabel = new JLabel();
        authorLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        authorLabel.setText("Author:");
        p21.add(authorLabel);
        centerPanel.add(p21);

        // author textfield setup
        authorTextField = new JTextField(14);
        authorTextField.setFont(new Font(FONT_NAME, Font.PLAIN, 20));
        authorTextField.setText(toEdit.getAuthor());
        p22.add(authorTextField);
        centerPanel.add(p22);

        // year label setup
        yearLabel = new JLabel();
        yearLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        yearLabel.setText("Release year:");
        p31.add(yearLabel);
        centerPanel.add(p31);

        // year textfield setup
        yearTextField = new JTextField(14);
        yearTextField.setFont(new Font(FONT_NAME, Font.PLAIN, 20));
        yearTextField.setText(toEdit.getReleaseYear().toString());
        p32.add(yearTextField);
        centerPanel.add(p32);

        // numpages label setup
        numPagesLabel = new JLabel();
        numPagesLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        numPagesLabel.setText("Number of pages:");
        p41.add(numPagesLabel);
        centerPanel.add(p41);

        // numpages textfield setup
        numPagesTextField = new JTextField(14);
        numPagesTextField.setFont(new Font(FONT_NAME, Font.PLAIN, 20));
        p42.add(numPagesTextField);
        numPagesTextField.setText(Integer.toString(toEdit.getNumPages()));
        centerPanel.add(p42);

        // genre label setup
        genreLabel = new JLabel();
        genreLabel.setFont(new Font(FONT_NAME, Font.BOLD, 20));
        genreLabel.setText("Genre:");
        p51.add(genreLabel);
        centerPanel.add(p51);

        // genre combobox setup
        genreComboBox = new JComboBox<>(Genre.values());
        genreComboBox.setFont(new Font(FONT_NAME, Font.PLAIN, 15));
        genreComboBox.setSelectedItem(toEdit.getGenre());
        p52.add(genreComboBox);
        centerPanel.add(p52);
        if (toEdit.getState().equals(Loanable.LoanState.ON_LOAN)){
            //date label setup
            this.datePanel = new DateSelectPanel(toEdit.getDueDate(), 2024, 2100);

            JLabel dateLabel = new JLabel();
            dateLabel.setFont(new Font("Lexend", Font.BOLD, 20));
            dateLabel.setText("Due date:");
            p61.add(dateLabel);
            centerPanel.add(p61);
            
            centerPanel.add(datePanel);
        }
        

        // BOTTOM PANEL SETUP
        editButton = new JButton();
        editButton.setText("Edit");
        bottomPanel.add(editButton);
        editButton.addActionListener(this);

        // east panel added purely for graphic reasons
        // it adds a little bit of an edge to the left
        JPanel eastPanel = new JPanel();
        this.add(eastPanel, BorderLayout.EAST);
        
        pack();
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()), (int) Math.min(getSize().getHeight(), screenDim.getHeight()));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // if add button is pressed
        if (e.getSource() == editButton) {
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
            try {
                this.toEdit.setTitle(applicant, title);
                this.toEdit.setAuthor(applicant, author);
                this.toEdit.setGenre(applicant, genre);
                this.toEdit.setNumPages(applicant, numPages);
                this.toEdit.setReleaseYear(applicant, year);
                if (toEdit.getState().equals(Loanable.LoanState.ON_LOAN)) {
                    int dueYear = datePanel.getYear(),
                    dueMonth = datePanel.getMonth(),
                    dueDay = datePanel.getDay();
                    LocalDate newDueDate = LocalDate.of(dueYear, dueMonth, dueDay);
                    this.toEdit.setDueDate(applicant, newDueDate);
                }

            } catch (InvalidAdminException editException) {
                JOptionPane.showMessageDialog(null, INVALID_ADMIN_MSG);
                return;
            }

            JOptionPane.showMessageDialog(null, "Book edited succesfully");
            this.dispose();
            try {
                new AdminFrame(applicant);
            } catch (InvalidAdminException e1) {
                e1.printStackTrace();
            }
        }
        
    }
}