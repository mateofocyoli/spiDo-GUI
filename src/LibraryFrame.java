import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class LibraryFrame extends JFrame implements ActionListener {
	
	
	LibraryFrame() {
		
		//SETUP FRAME
		this.setTitle("Biblioteca ITALIANA");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 450);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		
		//Icon image of frame
		ImageIcon ambrogio = new ImageIcon("ambrogio.jpg");
		this.setIconImage(ambrogio.getImage());
		
		//Sfondo provvisorio del JFrame
		JLabel sfondo = new JLabel();
		ImageIcon bookImage = new ImageIcon("libreria.jpg");
		this.add(sfondo);
		sfondo.setIcon(bookImage);
		
		JPanel northPanel = new JPanel();
		JPanel westPanel = new JPanel();
		JPanel eastPanel = new JPanel();
		JPanel southPanel = new JPanel();
		northPanel.setBackground(Color.RED);
		westPanel.setBackground(Color.YELLOW);
		eastPanel.setBackground(Color.BLUE);
		southPanel.setBackground(Color.GREEN);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(westPanel, BorderLayout.WEST);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(southPanel, BorderLayout.SOUTH);
		northPanel.setPreferredSize(new Dimension(100, 100));
		westPanel.setPreferredSize(new Dimension(100, 100));
		eastPanel.setPreferredSize(new Dimension(100, 100));
		southPanel.setPreferredSize(new Dimension(100, 100));
		
		
		
		this.pack();
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
