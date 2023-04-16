from utils import *

# Static Images
image = cv2.imread("C:\park1.jpg", cv2.IMREAD_UNCHANGED)

# (0,0,45) natural
# (0,0,255) coral
# (154,0,255) pink
# (255,0,213) lightpurple

colors = (3,0,186)

output1 = apply_makeup(image, False, 'blush',colors,1, False)
output2 = apply_makeup(image, False, 'lips',colors,1, False)
output3 = apply_makeup(image, False, 'foundation',(0,0,102),1, False)

# Blend the three output images together
alpha1 = 0.4
alpha2 = 0.4  
alpha3 = 0.2  
beta = 1 - (alpha1 + alpha2 + alpha3)
blend = cv2.addWeighted(output1, alpha1, output2, alpha2, 0)
blend = cv2.addWeighted(blend, 1, output3, alpha3, 0)

# Display the images
cv2.imshow("Original", image)
#cv2.imshow("Output 1", output1)
#cv2.imshow("Output 2", output2)
#cv2.imshow("Output 3", output3)
cv2.imshow("Blended", blend)
cv2.waitKey(0)
cv2.destroyAllWindows()