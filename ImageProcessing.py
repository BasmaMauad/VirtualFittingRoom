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

if cloth_type=="1":#dress
    y_offset= y1+h1 +20
    x_offset=abs((x+(w/2))-(x1+ (w1/2)))  - (w/w1)*10

##########################################################
#add alpha channel to clothes
s_img=s_img_color.copy()
s_img = cv2.cvtColor(s_img, cv2.COLOR_BGR2RGBA)
bg_color = s_img[0][0]
mask = np.all(s_img == bg_color, axis=2)
s_img[mask] = [0, 0, 0, 0]
plt.imshow(s_img)
plt.show()

##########################################################
# Resize clothes image
if cloth_type=="0":
    s_img=cv2.resize(s_img,(int(w/1.16),int(w/1.16))) # Top
if cloth_type=="1":
    s_img=cv2.resize(s_img,(int(w*1.2),int(h-(y1+h1)))) #chemise

x_ch,y_ch,w_ch,h_ch = cv2.boundingRect(contours_chemise[0])

plt.imshow(s_img)
plt.show()

###########################################################
# add alpha channel to BODY
image=original_img_color.copy()
image = cv2.cvtColor(image, cv2.COLOR_BGR2RGBA)
bg_color = image[0][0]
mask = np.all(image == bg_color, axis=2)
image[mask] = [0, 0, 0, 0]

a,b,c=s_img.shape

############################################################
# remove background from clothes image then put it on body image
for i in range(0, a):
    for j in range(0, b):

        if s_img[i, j][3] != 0:
            image[int(y_offset) + i , int(x_offset)+ j] = s_img[i, j]

plt.imshow(image)
plt.show()

# Save output image
image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)
cv2.imwrite('output1.png',image)
