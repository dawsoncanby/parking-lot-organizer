import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by dawsoncanby on 4/24/17.
 *
 * Models a real life parking lot.
 */
public class ParkingLot {

    // dimensions of the lot, in parking spaces
    private int width, height;

    // a list containing all zones
    private ArrayList<Zone> zones;

    public ParkingLot(int width, int height) {
        this.width = width;
        this.height = height;
        zones = new ArrayList<>();
    }

    public ParkingLot(File inFile) {
        readParkingLot(inFile);
    }

    /**
     * Write this parking lot to the specified file.
     * @param outFile the file to write to
     * @return true on success, false on fail
     */
    public boolean writeParkingLot(File outFile) {
        try {
            FileWriter writer = new FileWriter(outFile);

            // write size
            writer.write(width + " " + height + "\n");

            // write zones
            for (Zone z : zones) {
                writer.write(z.toString() + "\n");
            }
            writer.flush();
            writer.close();

            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Read a parking lot from given file into this object.
     * NOTE: assumes infile is correctly formatted
     * @param inFile the file to read into
     * @return true on success, false on fail
     */
    public boolean readParkingLot(File inFile) {
        try {
            Scanner sc = new Scanner(inFile);

            // empty/init zones
            zones = new ArrayList<>();

            // read zones from file
            try {
                // read size
                width = sc.nextInt();
                height = sc.nextInt();
                while (sc.hasNextInt()) {
                    zones.add(new Zone(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt()));
                }
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "file could not be parsed correctly");
                e.printStackTrace();
            }

            sc.close();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Zone> getZones() {
        return zones;
    }
}
