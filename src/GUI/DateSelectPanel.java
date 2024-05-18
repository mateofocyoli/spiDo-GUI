package GUI;

import javax.swing.JComboBox;
import javax.swing.JPanel;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class DateSelectPanel extends JPanel implements ActionListener{

    private int year;
    private int month;
    private int day;

    private static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	private JComboBox<Integer> dayComboBox, yearComboBox;
	private JComboBox<String> monthComboBox;

    public DateSelectPanel(int minYear, int maxYear){
        this.day=1;
        this.month=1;
        this.year=minYear;
        //date combo box setup
		dayComboBox = new JComboBox<>();
		monthComboBox = new JComboBox<>();
		yearComboBox = new JComboBox<>();

		dayComboBox.addActionListener(this);
		monthComboBox.addActionListener(this);
		yearComboBox.addActionListener(this);
		
		//filling yearComboBox with years from minYear to maxYear
		for(int i=minYear; i<maxYear; i++) {
			yearComboBox.addItem(i);
		}
		//filling monthComboBox with months
		for(String m : MONTHS) {
			monthComboBox.addItem(m);
		}
		
		//adding the three combo boxes all together in the panel
		this.setLayout(new GridLayout(1, 3));
		this.add(dayComboBox);
		this.add(monthComboBox);
		this.add(yearComboBox);
    }

    public DateSelectPanel(LocalDate defaultDate, int minYear, int maxYear){
        this.day=defaultDate.getDayOfMonth();
        this.month=defaultDate.getMonthValue();
        this.year=defaultDate.getYear();
        //date combo box setup
        dayComboBox = new JComboBox<>();
        monthComboBox = new JComboBox<>();
        yearComboBox = new JComboBox<>();

        dayComboBox.addActionListener(this);
        monthComboBox.addActionListener(this);
        yearComboBox.addActionListener(this);
        
        //filling yearComboBox with years from minYear to maxYear
        for(int i=minYear; i<maxYear; i++) {
            yearComboBox.addItem(i);
        }
        //filling monthComboBox with months
        for(String m : MONTHS) {
            monthComboBox.addItem(m);
        }
        
        yearComboBox.setSelectedIndex(defaultDate.getYear() - yearComboBox.getItemAt(0));
        monthComboBox.setSelectedIndex(defaultDate.getMonthValue() - 1);
        dayComboBox.setSelectedIndex(defaultDate.getDayOfMonth() - 1);

        //adding the three combo boxes all together in the panel
        this.setLayout(new GridLayout(1, 3));
        this.add(dayComboBox);
        this.add(monthComboBox);
        this.add(yearComboBox);
    }

    public boolean isLeap(int year) {
		if(year%400==0) return true;
		else if(year%100==0) return false;
		else if(year%4==0) return true;
		return false;		
	}
	
	
	public int hasDays(int month, int year) {
		
		if(month==2) {
			if(isLeap(year)) {
				return 29;
			} else return 28;
		}
		if(month==4 || month==6 || month==9 || month==11) return 30;
		return 31;
	}


    

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==monthComboBox || e.getSource()==yearComboBox) {
			if(monthComboBox.getSelectedItem() == null || yearComboBox.getSelectedItem() == null)
                return;
            
            dayComboBox.removeAllItems();
			for(int i=1; i<=hasDays((int)monthComboBox.getSelectedIndex() + 1, (int)yearComboBox.getSelectedItem()); i++) {
				dayComboBox.addItem(i);
			}
            this.year = (int) yearComboBox.getSelectedItem();
            this.month = (int) monthComboBox.getSelectedIndex() + 1;
			dayComboBox.setSelectedIndex(0);
		}
        else if (e.getSource() == dayComboBox && dayComboBox.getSelectedItem() != null) {
            this.day = (int) dayComboBox.getSelectedItem();
        }
    }
}
