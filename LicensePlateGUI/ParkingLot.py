from Zone import *


class ParkingLot:

    def __init__(self, gui, width, height):

        # the dimensions of the lot in spaces
        self.width = width
        self.height = height

        width_pix_size = gui.width / width
        height_pix_size = gui.height / height

        self.spot_pixel_size = width_pix_size if width_pix_size < height_pix_size else height_pix_size

        print self.spot_pixel_size

        # the physical zone objects of the lot
        self.zones = []

        # the references to the zones used by the canvas
        # empty until add_to_canvas called
        self.zone_refs = []

    def add_zone(self, x, y, width, height, dist):
        print '{0}, {1}, {2}, {3}, {4}'.format(x, y, width, height, dist)
        self.zones.append(Zone(x, y, width, height, dist))

    def add_to_canvas(self, canvas):
        # add each zone to the canvas and update references
        for z in self.zones:
            self.zone_refs.append(canvas.create_rectangle(
                    z.x * self.spot_pixel_size, z.y * self.spot_pixel_size,
                    z.width * self.spot_pixel_size + z.x * self.spot_pixel_size,
                    z.height * self.spot_pixel_size + z.y * self.spot_pixel_size,
                    fill="white"))

    def remove_from_canvas(self, canvas):
        for zr in self.zone_refs:
            canvas.delete(zr)
