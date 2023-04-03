from utils import *

# Static Images
image = cv2.imread("C:\park.jpg", cv2.IMREAD_UNCHANGED)

output1 = apply_makeup(image, False, 'blush',(0,0,102),1, False)
output2 = apply_makeup(image, False, 'lips',(0,0,102),1, False)
output3 = apply_makeup(image, False, 'foundation',(0,0,102),1.75, False)

# Blend the three output images together
alpha1 = 0.5 
alpha2 = 0.3  
alpha3 = 0.2  
beta = 1 - (alpha1 + alpha2 + alpha3)
blend = cv2.addWeighted(output1, alpha1, output2, alpha2, 0)
blend = cv2.addWeighted(blend, 1, output3, alpha3, 0)

# Display the images
cv2.imshow("Original", image)
cv2.imshow("Output 1", output1)
cv2.imshow("Output 2", output2)
cv2.imshow("Output 3", output3)
cv2.imshow("Blended", blend)
cv2.waitKey(0)
cv2.destroyAllWindows()