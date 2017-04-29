import java.awt.*;

/**
 * Created by dawsoncanby on 4/24/17.
 *
 * A class representing an area of parking spots with relatively similar distance from the point of interest
 */
public class Zone {

    // the location of the zone
    private int x, y;

    // the dimensions of the zone (how many parking spaces)
    private int width, height;

    // represents how close this zone is to the point of interest
    // should keep this on a scale of 1 - 100, 100 being the 'worst' spot
    private int distFromPOI;

    // vertical/horizontal offset for the string holding distance from POI
    private final int stringOffsetX = 10;
    private final int stringOffsetY = 15;

    public Zone(int x, int y, int width, int height, int distFromPOI) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.distFromPOI = distFromPOI;
    }

    /**
     * Returns whether or not a point clicked on the canvas is inside this zone.
     * @param x x pixel location of click
     * @param y y pixel location of click
     * @return true if this zone was clicked
     */
    public boolean contains(int x, int y) {
        return getScreenRect().contains(x, y);
    }

    /**
     * Returns whether the given rectangle intersects this zone
     * @param rect the rectangle to check
     * @return true when this zone intersects rect, false otherwise
     */
    public boolean intersects(Rectangle rect) {
        return getScreenRect().intersects(rect);
    }

    /**
     * Get a rect representing the size of this zone on the screen.
     * @return a Rectangle representing the size of this zone
     */
    private Rectangle getScreenRect() {
        return new Rectangle(x * ParkingLotCanvas.sizeofSpace, y * ParkingLotCanvas.sizeofSpace, width * ParkingLotCanvas.sizeofSpace, height * ParkingLotCanvas.sizeofSpace);
    }

    /**
     * Draw this zone to the screen.
     * @param g the graphics object to draw to
     */
    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.fill(getScreenRect());
        g.setColor(Color.gray);
        g.drawString("distance: ", getScreenRect().x + stringOffsetX, getScreenRect().y + stringOffsetY);
        g.drawString("" + distFromPOI, getScreenRect().x + stringOffsetX, getScreenRect().y + stringOffsetY * 2);
    }

    public String toString() {
        return x + " " + y + " " + width + " " + height + " " + distFromPOI;
    }

}
