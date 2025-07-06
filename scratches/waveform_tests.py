import numpy as np
import matplotlib.pyplot as plt

def sine_wave():
    x = np.array([i for i in range(1,20)])
    y = np.array([np.sin(i) for i in x])
    return y
