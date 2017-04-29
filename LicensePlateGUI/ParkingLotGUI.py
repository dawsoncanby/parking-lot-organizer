import Tkinter as tk
from ParkingLot import *


class ParkingLotGUI:

    def __init__(self):
        # setup window
        self.window = tk.Tk()
        self.fullscreen = False
        self.window.bind('<Escape>', self.toggle_fullscreen)
        self.width = 480
        self.height = 320
        self.canvas = tk.Canvas(self.window)
        self.canvas.create_rectangle(0, 0, self.width, self.height, fill="black")

        # init vars
        self.curLot = None

    def toggle_fullscreen(self, event=None):
        self.fullscreen = not self.fullscreen
        self.window.attributes('-fullscreen', self.fullscreen)

# load a parking lot from the given file path and put it on the canvas
    def load_lot(self, path):
        infile = open(path, 'r')

        # read and discard dimensions of lot
        first_line = infile.readline()
        width_height = map(int, first_line.split())

        # init lot
        self.curLot = ParkingLot(self, width_height[0], width_height[1])

        # read in zone info
        line = infile.readline()
        while line != '':

            # split line into ints
            zone_data = map(int, line.split())

            # add zone to lot
            self.curLot.add_zone(zone_data[0], zone_data[1], zone_data[2], zone_data[3], zone_data[4])

            # read next line and continue
            line = infile.readline()

        # add zones to canvas
        self.curLot.add_to_canvas(self.canvas)
        self.canvas.pack()

    def on_car_entered(self, min_dist_poi):
        self.curLot.flash_zones(self.canvas, min_dist_poi)
