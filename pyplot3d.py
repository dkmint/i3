from mpl_toolkits import mplot3d
import numpy as np
import matplotlib.pyplot as plt

x, y, z = [], [], []
line = open('reRandSeedP.dat', 'r')
for line in line:
    values = [float(s) for s in line.split()]
    x.append(values[0])
    y.append(values[1])
    z.append(values[2])

ax = plt.axes(projection='3d')
plt.plot(x, y, z)
plt.show()

