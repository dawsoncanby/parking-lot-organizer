from ParkingLotGUI import *

gui = ParkingLotGUI()
gui.load_lot("test2.txt")

gui.on_car_entered(1)
gui.window.mainloop()
