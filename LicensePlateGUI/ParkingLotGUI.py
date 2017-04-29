import Tkinter as tk
from ParkingLot import *


class ParkingLotGUI:

    def __init__(self):
        # setup window
        self.window = tk.Tk()
        #self.window.minsize(width=640, height=480)
        #self.window.maxsize(width=640, height=480)
        self.canvas = tk.Canvas(self.window)
        self.canvas.create_rectangle(0, 0, 640, 480, fill="black")

        # init vars
        self.curLot = None

    # load a parking lot from the given file path and put it on the canvas
    def load_lot(self, path):
        infile = open(path, 'r')

        # read and discard dimensions of lot
        first_line = infile.readline()
        width_height = map(int, first_line.split())

        # init lot
        self.curLot = ParkingLot(width_height[0], width_height[1])

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
