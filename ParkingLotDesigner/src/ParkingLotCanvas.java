import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by dawsoncanby on 4/24/17.
 *
 * A subclass of Canvas that the user can draw spots into
 * to model a real life parking lot.
 *
 * Zones of spots are drawn by clicking and dragging,
 * the properties of a zone can be changed by clicking on the region.
 */
public class ParkingLotCanvas extends JComponent {

    // the parking lot loaded into this canvas
    private ParkingLot parkingLot;

    // the pixel size of one parking space
    public static final int sizeofSpace = 20;

    // the pixel location of a users click (where they want to start drawing a new zone)
    private int startClickX, startClickY;

    // the current location of the users click (once click has started)
    private int curClickX, curClickY;

    // set to false to disable user from drawing
    private boolean canDraw;

    /**
     * Create a new ParkingLotCanvas
     * @param width the width of the lot in spaces
     * @param height the height of the lot in spaces
     */
    public ParkingLotCanvas(int width, int height) {
        super();

        parkingLot = new ParkingLot(width, height);
        initCanvas();
    }

    /**
     * Create a new ParkingLotCanvas with a pre-existing lot
     * @param parkingLot the lot to be loaded
     */
    public ParkingLotCanvas(ParkingLot parkingLot) {
        super();

        this.parkingLot = parkingLot;
        initCanvas();
    }

    private void initCanvas() {
        // set size of canvas based on size of lot
        setSize(parkingLot.getWidth() * sizeofSpace, parkingLot.getHeight() * sizeofSpace);
        canDraw = true;

        //setup mouse listeners
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                canDraw = true;
                startClickX = e.getX();
                startClickY = e.getY();

                // if theres already a zone cancel adding new zone
                // show options menu
                for (Zone z : parkingLot.getZones()) {
                    if (z.contains(startClickX, startClickY)) {
                        cancelDraw();
                        showZoneOptionsMenu(z);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // if zone is valid, add new zone
                if (canDraw) {
                    try {
                        parkingLot.getZones().add(calculateNewZone());
                    }
                    // user canceled add
                    catch (Exception ex) {
                    }
                }
                cancelDraw();
                repaint();
                canDraw = true;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelDraw();
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if (canDraw) {
                    curClickX = e.getX();
                    curClickY = e.getY();
                }

                // if user attempts to draw into an existing zone, cancel
                for (Zone z : parkingLot.getZones()) {
                    if (z.intersects(userDrawnRect())) {
                        cancelDraw();
                    }
                }

                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.black);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // draw grid
        g2.setColor(Color.WHITE);
        for (int i = 0; i < getParkingLot().getWidth(); i++) {
                g2.drawLine(i * sizeofSpace, 0, i * sizeofSpace, getParkingLot().getHeight() * sizeofSpace);
        }
        for (int j = 0; j < getParkingLot().getHeight(); j++) {
            g2.drawLine(0, j * sizeofSpace, getParkingLot().getWidth() * sizeofSpace, j * sizeofSpace);
        }

        // draw zones
        for (Zone z : parkingLot.getZones()) {
            z.draw(g2);
        }

        // draw zone user is creating
        g2.setColor(Color.white);
        g2.fill(userDrawnRect());
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    /**
     * Called when a user clicks on an existing zone.
     * give options to:
     *      delete
     *      change dist from poi
     * @param z the zone that was clicked
     */
    private void showZoneOptionsMenu(Zone z) {
        System.out.println("options");
        // TODO: add delete/change dist options
    }

    /**
     * Creates a new zone based on what user dragged.
     * Asks user to input the distance from POI, throws exception if user cancels
     * @return a zone based on the shape the user created
     */
    private Zone calculateNewZone() throws Exception {
        int dist;

        try {

            dist = DesignerWindow.getValidIntFromUser("How far is this zone from the point of interest?\n"
                    + "0 being closest, 100 being farthest");
        }
        catch (Exception e) {
            throw e;
        }
        Rectangle rect = userDrawnRect();

        //TODO: add better grid snapping
        int x = (int)Math.round((double) rect.x / sizeofSpace);
        int y = (int)Math.round((double) rect.y / sizeofSpace);
        int width = (int)Math.round((double) rect.width / sizeofSpace);
        int height = (int)Math.round((double) rect.height / sizeofSpace);

        // make sure width and height are at least 1
        if (width == 0) width = 1;
        if (height == 0) height = 1;

        Zone newZone = new Zone(x, y, width, height, dist);
        System.out.println(newZone);

        return newZone;
    }

    /**
     * Creates a rectangle based representing the area the user has dragged
     * @return a Rectangle based representing the area the user has dragged
     */
    private Rectangle userDrawnRect() {
        int x = Math.min(curClickX, startClickX);
        int y = Math.min(curClickY, startClickY);
        int width = Math.abs(curClickX - startClickX);
        int height = Math.abs(curClickY - startClickY);
        return  new Rectangle(x, y, width, height);
    }

    /**
     * reset the click pos
     */
    private void cancelDraw() {
        curClickX = curClickY = startClickX = startClickY = -1;
        canDraw = false;
    }

}
