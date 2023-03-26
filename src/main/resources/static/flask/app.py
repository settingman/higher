from flask import Flask, request
from flask_cors import CORS
from utils import apply_makeup
import cv2
import enum
import uuid
import time
import json
import boto3

#
 # @since : 2023. 3. 13.
 # @FileName: app.py
 # @author : 이세아
 # @설명 : 색조 화장기능 실행 파이썬
 # 
 # 수정일           수정자               수정내용
 # ----------      --------    ---------------------------
 # 2023. 3. 13.     이세아      create
 # 2023. 3. 14.     이세아      flask 및 기본 세팅
 # 2023. 3. 15.     이세아      메이크업 기능 작동 확인
 # 2023. 3. 19.     이세아      Spring boot와 연동 완료
 # 2023. 3. 23.     이세아      색상 변경 확인 및 구체화 완료
 # 2023. 3. 24.     이세아      AWS s3와 연동 완료
# 


app = Flask(__name__)
CORS(app)

class FeatureChoice(str, enum.Enum):
    lips = 'lips'
    blush = 'blush'
    foundation = 'foundation'

def s3_connection():
    try:
        # s3 클라이언트 생성
        s3 = boto3.client(
            service_name="s3",
            region_name="ap-northeast-2",
            aws_access_key_id="AKIARGO2DAHDK5ATCHPN",
            aws_secret_access_key="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",
        )
    except Exception as e:
        print(e)
    else:
        print("=========s3 bucket connected!==========") 
        return s3
        
s3 = s3_connection()

@app.route('/makeup/apply-makeup/', methods=['POST'])
def try_makeup():
    lips = request.form['lips']
    blush = request.form['blush']
    foundation = request.form['foundation']
    filePath = request.form['filePath']
    
    # 색상별 BGR값 부여 -> lips
    if lips=='red': color=(0,0,255)
    elif lips=='orange': color=(0,127,255)
    elif lips=='purple': color=(102,000,153)
    elif lips=='lightpurple' : color=(130,0,75)
    elif lips=='pink': color=(255,0,255)
    elif lips=='coral' : color=(0,102,255)
    elif lips=='beige' : color=(0,153,204)
    elif lips=='rose' : color=(0,0,102)
    elif lips=='darkrose' : color=(000,000,102)
    elif lips=='salmon' : color=(000,000,204)
    elif lips=='none' : color=()
    
     # 색상별 BGR값 부여 -> blush
    if blush=='red': color=(0,0,255)
    elif blush=='orange': color=(0,127,255)
    elif blush=='purple': color=(102,000,153)
    elif blush=='lightpurple' : color=(130,0,75)
    elif blush=='pink': color=(255,0,255)
    elif blush=='coral' : color=(0,102,255)
    elif blush=='beige' : color=(0,153,204)
    elif blush=='rose' : color=(0,0,102)
    elif blush=='darkrose' : color=(000,000,102)
    elif blush=='salmon' : color=(000,000,204)
    elif blush=='none' : color=()
    
     # 색상별 BGR값 부여 -> foundation
    if foundation=='none': gamma=1
    elif foundation=='dark': gamma=4
    elif foundation=='warmbeige': gamma=2
    elif foundation=='coolpink' : gamma=0.5
    elif foundation=='lightbeige': gamma=0.8
    
    # 파일경로 불러오기
    image = cv2.imread(filePath, cv2.IMREAD_UNCHANGED)
    print(filePath)
    
    # 각 부위 별 색상 넣기
    output_lip = apply_makeup(image, False, 'lips', color, 1, False)
    output_blush = apply_makeup(image, False, 'blush', color, 1,False)
    output_foundation = apply_makeup(image, False, 'foundation',(0,0,0), gamma, False)
    
    # 혼합
    alpha1 = 0.5 
    alpha2 = 0.3  
    alpha3 = 0.2  
    beta = 1 - (alpha1 + alpha2 + alpha3)
    blend = cv2.addWeighted(output_lip, alpha1, output_blush, alpha2, 0)
    blend = cv2.addWeighted(blend, 1, output_foundation, alpha3, 0)
    
    # output 저장 -> 뒤에 시간에 따른 uuid 붙음
    output_filename = f"output_{int(time.time())}_{uuid.uuid4()}.jpg"
    output_filepath_local= f"../img/{output_filename}"
    cv2.imwrite(output_filepath_local, blend)
    print(output_filepath_local)
    print(output_filename)
    
    # s3에 저장
    output_filepath= f"https://s3.ap-northeast-2.amazonaws.com/hbeauty.bucket/{output_filename}"
    
    try:
        s3.upload_file(output_filepath_local,"hbeauty.bucket",output_filename)
    except Exception as e:
     print(e)
    
    #java로 선택된 값 전송
    response_data = {
    'lips': lips,
    'blush': blush,
    'foundation': foundation,
    'output_filepath': output_filepath
    }
    
    # json 더미 return
    return json.dumps(response_data)

if __name__ == '__main__':
    app.run()