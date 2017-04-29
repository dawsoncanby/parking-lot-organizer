class Zone:

    def __init__(self, x, y, width, height, dist_from_poi):
        self.x = x
        self.y = y
        self.width = width
        self.height = height
        self.dist_from_poi = dist_from_poi
        self.vertical = self.width > self.height # true when this zone is a column

