import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dawsoncanby on 4/24/17.
 *
 * Extends JPanel, serves as the main container for all GUI elements such as:
 *      load/save/create buttons
 *      help button
 *      canvas to draw lot on
 */
public class DesignerWindow extends JPanel {

    private JPanel buttonPanel;
    private Button createNewButton;
    private Button loadButton;
    private Button saveButton;

    private ParkingLotCanvas curLotCanvas;

    public DesignerWindow() {
        super();

        setLayout(new BorderLayout());

        // setup both JPanels
        buttonPanel = new JPanel(new FlowLayout());

        // add create/load buttons
        createNewButton = new Button("Create New Parking Lot");
        createNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // get width and height from user
                int width, height;
                try {
                    width = getValidIntFromUser("Enter the width of the lot in spaces:");
                    height = getValidIntFromUser("Enter the height of the lot in spaces:");
                }
                catch (Exception ex) {
                   return;
                }
               attemptToReplaceCanvas(createNewLot(width, height));
            }
        });
        buttonPanel.add(createNewButton);

        loadButton = new Button("Load Parking Lot");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // open load dialog for user
                attemptToReplaceCanvas(loadLot());
            }
        });
        buttonPanel.add(loadButton);

        // setup save button
        saveButton = new Button("Save Parking Lot");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // open save dialog
                saveLot();
            }
        });
        buttonPanel.add(saveButton);

        // TODO: add help button

        // init empty canvas
        curLotCanvas = new ParkingLotCanvas(48, 32);

        add(curLotCanvas, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);
    }

    /**
     * helper method
     * Attempts to add the given canvas to the canvasScrollPane.
     * @param canvas the new ParkingLotCanvas to load into the screen
     */
    private void attemptToReplaceCanvas(ParkingLotCanvas canvas) {
        // if canvas created successfully
        if (canvas != null) {
            remove(curLotCanvas);
            curLotCanvas = canvas;
            add(curLotCanvas, BorderLayout.CENTER);
            repaint();
            curLotCanvas.repaint();
        }
    }

    /**
     * helper method
     * Repeatedly asks the user for an integer until a valid value is given.
     * @param message the message to show the user
     * @return a valid integer entered by the user
     */
    public static int getValidIntFromUser(String message) throws Exception {
        Integer val = null;

        // loop until input is valid
        while(val == null) {
            String s = JOptionPane.showInputDialog(message);

            if (s == null) {
                throw new Exception("operation cancelled");
            }

            // try to get valid input
            try {
                val = Integer.parseInt(s);
            }
            catch (NumberFormatException e) {
                continue;
            }

        }
        return val;
    }

    /**
     * Creates a new parking lot the user can draw into.
     * @param width the width of the lot in spaces
     * @param height the height of the lot in spaces
     * @return the ParkingLotCanvas object created
     */
    private ParkingLotCanvas createNewLot(int width, int height) {
        return new ParkingLotCanvas(width, height);
    }

    /**
     * Loads a parking lot with a java file chooser.
     */
    private ParkingLotCanvas loadLot() {
                // open filechooser for user to select file
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(loadButton);

                // if user selects a file
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    return new ParkingLotCanvas(new ParkingLot(fileChooser.getSelectedFile()));
                }
                return null;
    }

    /**
     * Saves the current parking lot with a java file chooser
     * @return true on success, false on fail
     */
    private boolean saveLot() {
        // open a filechooser to allow user to save
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showSaveDialog(saveButton);

        // if user saves file
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return curLotCanvas.getParkingLot().writeParkingLot(fileChooser.getSelectedFile());
        }
        return false;
    }
}
