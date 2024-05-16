package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.util.List;

import exceptions.InvalidAdminException;
import users.Admin;
import users.User;
import users.sanctions.Sanction;

public class SanctionViewerFrame extends JFrame {

    private Admin admin;

    public SanctionViewerFrame(Admin admin, User user) throws InvalidAdminException {
        if (admin == null)
            throw new InvalidAdminException("The admin object can not be null.");
        this.admin = admin;

        this.setTitle("Person Viewer - " + admin.getCredentials().getUsername());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ((JFrame) e.getSource()).dispose();
                try {
                    new PersonViewerFrame(admin);
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

        List<Sanction> sanctions = user.getSanctions();
        if(sanctions.isEmpty()) {
            JLabel noSanctionsLabel = new JLabel("  There are no sanctions  ");
            noSanctionsLabel.setForeground(Color.GRAY);
            noSanctionsLabel.setFont(new Font("Lexend", Font.ITALIC, 20));
            panel.add(noSanctionsLabel);
        } else {
            for(Sanction s : sanctions) {
                panel.add(new SanctionPanel(admin, user, s, this));
            }
        }

        pack();
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()), (int) Math.min(getSize().getHeight(), screenDim.getHeight()));
    }

    public void redraw() {
        this.dispose();
        try {
            new PersonViewerFrame(admin);
        } catch (InvalidAdminException e) {
            e.printStackTrace();
            System.exit(ABORT);
        }
    }
}
