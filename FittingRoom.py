from flask import Flask, request #import main Flask class and request object
import base64
import cv2
import numpy as np
import base64
import scipy.misc

def stringToImage(imgdata):
    imgdata = imgdata.replace('\n', '')
    imgdata=imgdata.replace(' ', '')
    imgdata = bytes(imgdata, 'utf-8')
    imgdata = base64.b64decode(imgdata)
    nparr = np.fromstring(imgdata, np.uint8)
    return cv2.imdecode(nparr, cv2.IMREAD_COLOR)
def processing(original_img_color, cloth_type, s_img_color):
	#image_human=cv2.imread("lolo3.jpeg")
	#original_img_color=cv2.imread("lolo3.jpeg")

	original_img=cv2.cvtColor(original_img_color, cv2.COLOR_BGR2GRAY)
	img=original_img.copy()
	ret,thresh_obj = cv2.threshold(img,80,230,cv2.THRESH_BINARY)

	img2=original_img.copy()
	blur=cv2.blur(thresh_obj,(9,9))
	contours, hierarchy= cv2.findContours(blur,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
	max_cnt=contours[0]
	for c in contours:
	    if cv2.contourArea(c) > cv2.contourArea(max_cnt):
	        max_cnt=c
	cv2.contourArea(max_cnt)
	M1 = cv2.moments(max_cnt)

	centroid_x1 = int(M1['m10']/M1['m00'])
	centroid_y1 = int(M1['m01']/M1['m00'])

	x,y,w,h = cv2.boundingRect(max_cnt)
	#s_img_color = cv2.imread("blause4.jpeg")  
	s_img=cv2.cvtColor(s_img_color, cv2.COLOR_BGR2GRAY)

	ret,thresh_obj = cv2.threshold(s_img,200,100,cv2.THRESH_BINARY)
	blur=cv2.blur(thresh_obj,(9,9))
	contours_chemise, hierarchy= cv2.findContours(blur,cv2.RETR_TREE,cv2.CHAIN_APPROX_SIMPLE)
	len(contours_chemise)

	#centroids of chemise
	M = cv2.moments(contours_chemise[0])

	centroid_x = int(M['m10']/M['m00'])
	centroid_y = int(M['m01']/M['m00'])
	img = original_img_color.copy()

	face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_alt.xml')

	gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

	faces = face_cascade.detectMultiScale(gray, 1.3, 5)

	x1,y1,w1,h1 = faces[0]
	if cloth_type=="0": #top
	    y_offset= y1+h1
	    x_offset=abs((x+(w/2))-(x1+ (w1/2))) + (w/w1)*15

# x_offset=abs((x+(w/2))-(x1+ (w1/2)))
	if cloth_type=="1":
	    y_offset= y1+h1 +20
	    x_offset=abs((x+(w/2))-(x1+ (w1/2)))  - (w/w1)*27

	#x_offset=abs((x+w/2)-(x_ch+ w_ch/2))


	x_offset


	w/w1
	s_img=s_img_color.copy()
	s_img = cv2.cvtColor(s_img, cv2.COLOR_BGR2RGBA)
	bg_color = s_img[0][0]
	mask = np.all(s_img == bg_color, axis=2)
	s_img[mask] = [0, 0, 0, 0]

	if cloth_type=="0":
		s_img=cv2.resize(s_img,(int(w/1.16),int(w/1.16))) # Top
	if cloth_type=="1":
	    s_img=cv2.resize(s_img,(int(w*1.2),int(h-(y1+h1)))) #chemise

	x_ch,y_ch,w_ch,h_ch = cv2.boundingRect(contours_chemise[0])
	image=original_img_color.copy()
	image = cv2.cvtColor(image, cv2.COLOR_BGR2RGBA)
	bg_color = image[0][0]
	mask = np.all(image == bg_color, axis=2)
	image[mask] = [0, 0, 0, 0]
	#plt.imshow(image)
	a,b,c=s_img.shape

	for i in range(0, a):
	    for j in range(0, b):

	        if s_img[i, j][3] != 0:
	            image[int(y_offset) + i , int(x_offset)+ j] = s_img[i, j]

	image = cv2.cvtColor(image, cv2.COLOR_RGBA2BGR)
	retval, buffer = cv2.imencode('.jpg', image)
	jpg_as_text = base64.b64encode(buffer)
	return jpg_as_text
app = Flask(__name__)
@app.route("/", methods=['GET', 'POST'])
def query_example():
    if request.method == 'POST':
	    human = request.form.get('human')
	    clothes = request.form.get('clothes')
	    category = request.form.get('category')
	    #imgdata=human["image"]
	    image_human=stringToImage(human)
	    image_cloth=stringToImage(clothes)
	    return(processing(image_human, category, image_cloth))

    return("BARMS")

if __name__ == '__main__':
    app.run(debug=True)
