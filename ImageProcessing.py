import cv2
import matplotlib.pyplot as plt
import numpy as np

cloth_type="1"

# Read Image and convert it to gray scale
original_img_color=cv2.imread("basma.jpeg")

original_img=cv2.cvtColor(original_img_color, cv2.COLOR_BGR2GRAY)
plt.imshow(original_img, cmap="gray")
plt.show()