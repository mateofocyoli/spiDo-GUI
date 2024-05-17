package GUI.adminView;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import exceptions.InvalidAdminException;
import exceptions.RequestNotPresentException;
import items.LoanRequest;
import items.Loanable;
import items.managers.LoanRequestsManager;
import users.Admin;

public class RequestedBookPanelAdmin extends JPanel implements ActionListener {

    private JButton denyButton, acceptButton;
    private Admin admin;
    private LoanRequest lr;
    private RequestsViewerFrameAdmin parent;

    public RequestedBookPanelAdmin(Admin admin, LoanRequest lr, RequestsViewerFrameAdmin parent) {

        this.lr = lr;
        this.admin = admin;
        this.parent = parent;

        // the dataPanel will contain the book data
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(2, 0));
        Loanable item = lr.getRequested();

        JLabel nameLabel = new JLabel(item.getName());
        JLabel dateLabel = new JLabel("" + item.getDueDate());
        JLabel requesterLabel = new JLabel(lr.getApplicant().getCredentials().getUsername());

        nameLabel.setForeground(Color.BLACK);
        dateLabel.setForeground(Color.BLACK);
        requesterLabel.setForeground(Color.BLACK);
        
        nameLabel.setFont(new Font("Lexend", Font.BOLD, 13));
        dateLabel.setFont(new Font("Lexend", Font.ITALIC, 10));
        requesterLabel.setFont(new Font("Lexend", Font.PLAIN, 15));

        dataPanel.add(requesterLabel);
        dataPanel.add(nameLabel);
        dataPanel.add(dateLabel);

        // the buttonPanel will contain the button
        JPanel buttonPanel = new JPanel();

        denyButton = new JButton("🗷");
        denyButton.setFocusable(false);
        denyButton.setForeground(Color.RED);
        denyButton.addActionListener(this);

        acceptButton = new JButton("🗹");
        acceptButton.setFocusable(false);
        acceptButton.setForeground(Color.GREEN);
        acceptButton.addActionListener(this);

        buttonPanel.add(acceptButton);
        buttonPanel.add(denyButton);

        // added the two panels to the main panel
        this.add(dataPanel);
        this.add(buttonPanel);

        // set the border for the bookPanel
        Border border = BorderFactory.createLineBorder(new Color(0xA9A9A9), 5);
        this.setBorder(border);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == denyButton) {
            try {
                LoanRequestsManager.getInstance().denyRequest(admin, lr);
            } catch (InvalidAdminException e1) {
                JOptionPane.showMessageDialog(this, "You don't have the privileges", "Error", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            } catch (RequestNotPresentException e1) {
                JOptionPane.showMessageDialog(this, "An error as occured. It seems that the request is no longer present", "Error", JOptionPane.ERROR_MESSAGE);
            }

            this.remove(acceptButton);
            this.remove(denyButton);
        }

        if(e.getSource() == acceptButton) {
            try {
                LoanRequestsManager.getInstance().acceptRequest(admin, lr);
            } catch (InvalidAdminException e1) {
                JOptionPane.showMessageDialog(this, "You don't have the privileges", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (RequestNotPresentException e1) {
                JOptionPane.showMessageDialog(this, "An error as occured. It seems that the request is no longer present", "Error", JOptionPane.ERROR_MESSAGE);
            }

            this.remove(acceptButton);
            this.remove(denyButton);
        }

        parent.redraw();
    }
}
