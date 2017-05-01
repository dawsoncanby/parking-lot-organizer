import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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
        frame.setSize(640, 480);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // add main JPanel
        DesignerWindow gui = new DesignerWindow();

        // add window resize listener
        frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                // set lot size to minimum so all spots are always shown
                ParkingLotCanvas.sizeofSpace = Math.min
                        (frame.getWidth() / gui.getCurLotCanvas().getParkingLot().getWidth(),
                                frame.getHeight() - 40 / gui.getCurLotCanvas().getParkingLot().getHeight());
            }
            @Override
            public void componentMoved(ComponentEvent e) {
            }
            @Override
            public void componentShown(ComponentEvent e) {
            }
            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

        frame.setContentPane(gui);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new DesignerWindowRunner());
    }
}
