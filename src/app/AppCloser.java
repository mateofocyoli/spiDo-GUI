package app;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class AppCloser extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        // Dispose the frame that called the close operation and call the main exit procedure
        JFrame caller = (JFrame) e.getSource();
        caller.dispose();
        Library.exitProcedure();
    }
    
}
