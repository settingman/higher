from utils import *

#
 # @since : 2023. 3. 13.
 # @FileName:main.py
 # @author : 이세아
 # @설명 : 색조 화장기능 실행 확인용 test
 # 
 # 수정일           수정자               수정내용
 # ----------      --------    ---------------------------
 # 2023. 3. 09.     이세아      create
 # 2023. 3. 12.     이세아      메이크업 기능 작동 확인
 # 2023. 3. 15.     이세아      이미지 blend 확인
 # 2023. 3. 16.     이세아      기능 구체화 확인 완료
# 

# 정적 이미지 input
image = cv2.imread("C:\park.jpg", cv2.IMREAD_UNCHANGED)

# test용 color값, gamma값 설정
output1 = apply_makeup(image, False, 'blush',(0,0,102),1, False)
output2 = apply_makeup(image, False, 'lips',(0,0,102),1, False)
output3 = apply_makeup(image, False, 'foundation',(0,0,102),1.75, False)

# 이미지 블렌드
alpha1 = 0.5 
alpha2 = 0.3  
alpha3 = 0.2  
beta = 1 - (alpha1 + alpha2 + alpha3)
blend = cv2.addWeighted(output1, alpha1, output2, alpha2, 0)
blend = cv2.addWeighted(blend, 1, output3, alpha3, 0)

# 결과값 확인
cv2.imshow("Original", image)
cv2.imshow("Output 1", output1)
cv2.imshow("Output 2", output2)
cv2.imshow("Output 3", output3)
cv2.imshow("Blended", blend)
cv2.waitKey(0)
cv2.destroyAllWindows()