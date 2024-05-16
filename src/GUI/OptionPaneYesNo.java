package GUI;

import javax.swing.JOptionPane;

public class OptionPaneYesNo extends JOptionPane {

    public enum Options {
        YES_NO("Yes", "No"), 
        CONFIRM_CANCEL("Confirm", "Cancel"), 
        REMOVE_CANCEL("Remove", "Cancel"), 
        ADD_CANCEL("Add", "Cancel"), 
        EDIT_CANCEL("Edit", "Cancel");
        
        private final String yesString;
        private final String noString;

        private Options(String yes, String no) {
            this.yesString = yes;
            this.noString = no;
        }

        public String[] getOptions() {
            return new String[]{yesString, noString};
        }
    }

    public static boolean show(String title, String message, Options options) {
        int response = JOptionPane.showOptionDialog(null, message,
        title, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
        options.getOptions(), 0);

        return response == 0;
    }

}
