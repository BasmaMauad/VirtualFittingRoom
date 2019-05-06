import cv2
import matplotlib.pyplot as plt
import numpy as np

cloth_type="1"

# Read Image and convert it to gray scale
original_img_color=cv2.imread("basma.jpeg")

original_img=cv2.cvtColor(original_img_color, cv2.COLOR_BGR2GRAY)
plt.imshow(original_img, cmap="gray")
plt.show()
###############################################
#threshold

img=original_img.copy()
ret,thresh_obj = cv2.threshold(img,140,230,cv2.THRESH_BINARY)

img2=original_img.copy()
blur=cv2.blur(thresh_obj,(9,9))
contours, hierarchy= cv2.findContours(blur,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
plt.imshow((cv2.drawContours(img2, contours, -1, (255,0,0), 1)))
plt.show()




plt.imshow(thresh_obj)
plt.show()




#detect the body contour 
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

##############################################
# resize the clothes
s_img_color = cv2.imread("max-mara-Black-Drawstring-Shirt-Dress.jpeg")  
s_img=cv2.cvtColor(s_img_color, cv2.COLOR_BGR2GRAY)

# s_img=cv2.resize(s_img,(int(w-80),int(w-80))) #chemise
# s_img=cv2.resize(s_img,(int(w/1.16),int(w/1.16))) #final chemise

# s_img=cv2.resize(s_img,(int(w/1.16),int(h/1.16)) )  #dress


#Find contour of chemise
ret,thresh_obj = cv2.threshold(s_img,200,100,cv2.THRESH_BINARY)
blur=cv2.blur(thresh_obj,(9,9))
contours_chemise, hierarchy= cv2.findContours(blur,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
len(contours_chemise)

#centroids of chemise
M = cv2.moments(contours_chemise[0])

centroid_x = int(M['m10']/M['m00'])
centroid_y = int(M['m01']/M['m00'])

plt.imshow(thresh_obj, cmap="gray")
plt.show()

#####################################################

#face detecton using haar cascade
img = original_img_color.copy()
face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_alt.xml')
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
faces = face_cascade.detectMultiScale(gray, 1.3, 5)
x1,y1,w1,h1 = faces[0]


#######################################################
#calculate offset to position clothes on body
if cloth_type=="0": #top
    y_offset= y1+h1
    x_offset=abs((x+(w/2))-(x1+ (w1/2))) + (w/w1)*10

# x_offset=abs((x+(w/2))-(x1+ (w1/2)))
if cloth_type=="1":#dress
    y_offset= y1+h1 +20
    x_offset=abs((x+(w/2))-(x1+ (w1/2)))  - (w/w1)*10

#x_offset=abs((x+w/2)-(x_ch+ w_ch/2))

#######################################################
