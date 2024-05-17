package GUI.adminView;

import java.awt.Color;
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
import items.LoanRequest;
import items.managers.LoanRequestsManager;
import users.Admin;

public class RequestsViewerFrameAdmin extends JFrame {

    private Admin admin;

    public RequestsViewerFrameAdmin(Admin admin) throws InvalidAdminException {
        if (admin == null)
            throw new InvalidAdminException("The admin object can not be null.");
        this.admin = admin;

        // Frame setup
        this.setTitle("Requests Viewer Frame Admin - " + admin.getCredentials().getUsername());
        this.setResizable(false);
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

        LoanRequestsManager lrm = LoanRequestsManager.getInstance();
        List<LoanRequest> requests = lrm.getSortedRequestsBy(LoanRequestsManager.Criteria.APPLICANT);
        if(requests.isEmpty()) {
            JLabel noRequestsLabel = new JLabel("  There are no requests  ");
            noRequestsLabel.setForeground(Color.GRAY);
            noRequestsLabel.setFont(new Font("Lexend", Font.ITALIC, 20));
            panel.add(noRequestsLabel);
        } else {
            for(LoanRequest lr : requests) {
                if(!lr.isAccepted()) {
                    panel.add(new RequestedBookPanelAdmin(admin, lr, this));
                }
            }
        }

        pack();
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.min(getSize().getWidth(), screenDim.getWidth()), (int) Math.min(getSize().getHeight(), screenDim.getHeight()));
    }

    public void redraw() {
        this.dispose();
        try {
            new RequestsViewerFrameAdmin(admin);
        } catch (InvalidAdminException e) {
            e.printStackTrace();
            System.exit(ABORT);
        }
    }
}
