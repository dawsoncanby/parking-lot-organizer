from Zone import *
from time import *

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

        # the references to the parking spots used by the canvas
        # empty until add_to_canvas called
        self.spot_refs = []

        # how long between flashes when a we need to tell car where to go
        self.flash_interval = 0.25

        # how many times to flash
        self.total_flashes = 5

        # how many flashes have occured
        self.cur_flashes = 0

        # last flash time
        self.last_flash_time = 0

        # references to the rectangles created by the flash
        self.flash_refs = []

    def add_zone(self, x, y, width, height, dist):
        print '{0}, {1}, {2}, {3}, {4}'.format(x, y, width, height, dist)
        self.zones.append(Zone(x, y, width, height, dist))

    def add_to_canvas(self, canvas):
        # add each zone to the canvas and update references
        for z in self.zones:
            for i in range(z.x, z.width + z.x):
                for j in range(z.y, z.height + z.y):
                    self.spot_refs.append(canvas.create_rectangle(
                        i * self.spot_pixel_size, j * self.spot_pixel_size,
                        i * self.spot_pixel_size + self.spot_pixel_size,
                        j * self.spot_pixel_size + self.spot_pixel_size,
                        outline='white'
                    ))

    def flash_zones(self, window, canvas, min_poi_dist):
        # recursively call until done flashing

        # we are done
        if self.cur_flashes == self.total_flashes + 1:
            return
        else:
            # if its time to flash
            if time() - self.last_flash_time > self.flash_interval:
                # update time and numflashes
                self.last_flash_time = time()
                self.cur_flashes += 1

                print 'flashed: ' + str(min_poi_dist)

                # flash zones
                for z in self.zones:
                    # if zone is valid
                    if z.dist_from_poi >= min_poi_dist:
                        self.flash_refs.append(canvas.create_rectangle(
                            z.x * self.spot_pixel_size, z.y * self.spot_pixel_size,
                            z.x * self.spot_pixel_size + z.width * self.spot_pixel_size,
                            z.y * self.spot_pixel_size + z.height * self.spot_pixel_size,
                            fill='#00ff00'
                        ))

                window.update()

                # sleep until its time to remove flash
                sleep(self.flash_interval / 2)

                # remove flashes from canvas
                for r in self.flash_refs:
                    canvas.delete(r)

                window.update()

                # sleep until its time to recursively call
                sleep(self.flash_interval / 2)
                self.flash_zones(window, canvas, min_poi_dist)

    def remove_from_canvas(self, canvas):
        for zr in self.spot_refs:
            canvas.delete(zr)
