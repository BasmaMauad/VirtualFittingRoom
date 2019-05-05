import cv2
import matplotlib.pyplot as plt
import numpy as np

cloth_type="1"

# Read Image and convert it to gray scale
original_img_color=cv2.imread("basma.jpeg")

original_img=cv2.cvtColor(original_img_color, cv2.COLOR_BGR2GRAY)
plt.imshow(original_img, cmap="gray")
plt.show()

#
img=original_img.copy()
ret,thresh_obj = cv2.threshold(img,140,230,cv2.THRESH_BINARY)

img2=original_img.copy()
blur=cv2.blur(thresh_obj,(9,9))
contours, hierarchy= cv2.findContours(blur,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
plt.imshow((cv2.drawContours(img2, contours, -1, (255,0,0), 1)))
plt.show()

plt.imshow(thresh_obj)
plt.show()

# detect the body contour 

max_cnt=contours[0]
for c in contours:
    if cv2.contourArea(c) > cv2.contourArea(max_cnt):
        max_cnt=c
cv2.contourArea(max_cnt)


# centroids of body
M1 = cv2.moments(max_cnt)

centroid_x1 = int(M1['m10']/M1['m00'])
centroid_y1 = int(M1['m01']/M1['m00'])

x,y,w,h = cv2.boundingRect(max_cnt)


