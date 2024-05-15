package GUI;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
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
        this.setSize(500, 550);
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
        for(LoanRequest lr : requests) {
            if(!lr.isAccepted()) {
                panel.add(new RequestedBookPanelAdmin(admin, lr, this));
            }
        }
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
