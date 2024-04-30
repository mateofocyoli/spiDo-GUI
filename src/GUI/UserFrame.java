package GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import users.Person;

public class UserFrame extends JFrame implements ActionListener {
	
	UserFrame(Person person) {
		this.setTitle("User frame");
		this.setSize(750, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println(person);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
