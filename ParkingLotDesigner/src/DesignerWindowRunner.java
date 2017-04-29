import javax.swing.*;
import java.awt.*;

/**
 * Created by dawsoncanby on 4/24/17.
 * Top level container for the GUI, holds the JFrame.
 */
public class DesignerWindowRunner implements Runnable {


    @Override
    public void run() {
        // init frame
        JFrame frame = new JFrame("Parking Lot Designer");

        frame.setMinimumSize(new Dimension(320, 240));
        frame.setSize(480, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // add main JPanel
        DesignerWindow gui = new DesignerWindow();
        frame.setContentPane(gui);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new DesignerWindowRunner());
    }
}
