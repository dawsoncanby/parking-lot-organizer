import collections
import time as tme
import os.path

from loadlot import loadlot
from ParkingLotGUI import *

class lotPlacer:
    def __init__(self):
        self.lotFile = "test2.txt"
        self.rawlot = loadlot(self.lotFile)
        self.lotPercentiles = collections.OrderedDict()
        self.cars = []
        self.vistorCar = Car("ft")
        self.currentTime = 0
        self.totalSpaces = 0
        distances = []
        print self.rawlot
        for key in self.rawlot:
            self.totalSpaces = self.totalSpaces + self.rawlot[key]
            distances.append(key)
        distances.sort()
        print "Total spots: " + str(self.totalSpaces)
        lastVal = 0
        for d in distances:
            lastVal = self.lotPercentiles[d] = float(self.rawlot[d]) / float(self.totalSpaces) + lastVal
        print self.lotPercentiles

    def car(self,lpn):
        for c in self.cars:
            if c.lpn == lpn:
                if c.inLot:
                    self.outCar(c)
                    self.printCars()
                    return
                else:
                    self.inCar(c)
                    self.printCars()
                    return
        self.newCar(lpn)
        self.printCars()

    def printCars(self):
        for c in self.cars:
            print str(c)

    def readTestFile(self, fileName):
        data = open(fileName, 'r')
        line = data.readline()
        while(line != ''):
            tokens = line.split()
            lpn = tokens[0]
            inlot = (tokens[1] == "true")
            avgTime = int(tokens[2])
            lastTime = int(tokens[3])
            #print("LPN: " + lpn + " inLot: " + str(inlot) + " avgTime: " + str(avgTime) + " lastTime: "+str(lastTime))
            c = Car(lpn)
            c.inLot = inlot
            c.avgTimeIn = avgTime
            c.enterTime = lastTime
            self.cars.append(c)
            line = data.readline()
        self.currentTime = self.cars[len(self.cars)-1].enterTime
        self.cars.sort()
        self.printCars()

    def computePercentile(self, c):
        index = self.cars.index(c)
        percentile = len(self.cars)
        percentile = percentile - index
        return 1 - float((float(percentile) / float(len(self.cars))))

    def carT(self,lpn, time):
        self.currentTime = self.currentTime+time
        self.car(lpn)

    def inCar(self, c):
        print "In Car"
        c.enterLot(self.currentTime)
        self.cars.sort()
        percentile = self.computePercentile(c)
        print("Percentile of incoming car: " + str(percentile))
        for key in self.lotPercentiles:
            if self.lotPercentiles[key] > percentile:
                print "Place car in: " + str(key) + " or back"
                break
        gui = ParkingLotGUI(self.lotFile, key)

    def outCar(self, c):
        print "Out Car"
        c.leaveLot(self.currentTime)

    def newCar(self, lpn):
        # print "New Car"
        # self.cars.append(Car(lpn))
        print "New Car"
        nc = Car(lpn)
        nc.avgTimeIn = self.cars[len(self.cars) / 2].avgTimeIn
        self.cars.append(nc)
        self.cars.sort()
        self.inCar(nc)

class Car:
    def __init__(self, lpn):
        self.lpn = lpn
        self.avgTimeIn = 0
        self.enterTime = 0
        self.inLot = True

    def enterLot(self, cTime):
        self.inLot = True
        self.enterTime = cTime

    def leaveLot(self, cTime):
        self.inLot = False
        self.avgTimeIn = self.avgTimeIn + (cTime-self.enterTime)
        self.avgTimeIn = self.avgTimeIn/2

    def __eq__(self, other):
        return other.lpn == self.lpn

    def __lt__(self, other):
        return self.avgTimeIn < other.avgTimeIn

    def __gt__(self, other):
        return self.avgTimeIn > other.avgTimeIn

    def __str__(self):
        return "Car " + self.lpn + "{inLot: " + str(self.inLot) + " avgTime: " + str(self.avgTimeIn)  + "}"


def readNotSoSharedFile(lp, file):
    dtime = 0
    while not os.path.exists(file):
        tme.sleep(3)
        dtime = dtime + 3
        print "File does not exist"
    if os.path.isfile(file):
        fuckIt = open(file)
        doItLive = fuckIt.readline()
        fuckinThingSucks = doItLive.split(' ')
        lpn = fuckinThingSucks[4]
        fuckIt.close()
        os.remove(file)
        lp.carT(lpn,dtime)

    else:
        raise ValueError("%s isnt't a file!" % file)

if __name__ == '__main__':
    lp = lotPlacer()
    lp.readTestFile("testCars.txt")
    while True:
        readNotSoSharedFile(lp, "license_plate.txt")
