def loadlot(path):
   
    infile = open(path, 'r')

    # the parking lot will be stored as lot[distance from POI] = spots in zone
    lot = dict()
    
    # read and discard dimensions of lot 
    infile.readline()

    # read in zone info
    line = infile.readline()
    while (line != ''):
        
        # convert line into list of ints
        numbers = map(int, line.split())

        # calculate number of spots in zone based on width and height
        numSpots = numbers[2] * numbers[3]

        # get distance from POI
        distFromPOI = numbers[4]

        # add zone to lot
        # add to existing numSpots if key exists
        # otherwise create new key
        if distFromPOI in lot:
            lot[distFromPOI] += numSpots
        else:
            lot[distFromPOI] = numSpots
        
        # read next line and continue
        line = infile.readline()
    
    print lot

