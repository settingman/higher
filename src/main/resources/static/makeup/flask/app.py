from flask import Flask, request
from flask_cors import CORS
from utils import apply_makeup
import cv2
import enum
import uuid
import time
import json
import boto3
import os

app = Flask(__name__)
app.debug = True
CORS(app)

# Author : 이세아
# s3를 통한 이미지 저장 및 스프링부트 서버와 연동
# 스프링부트 값에 따른 색상값을 이미지에 적용

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

@app.route('/apply-makeup/', methods=['POST'])
def try_makeup():
    lips = request.form['lips']
    blush = request.form['blush']
    foundation = request.form['foundation']
    filePath = request.form['filePath']
    
    # 색상별 BGR값 부여 -> lips
    if lips=='red': lip_color=(3,0,196)
    elif lips=='orange': lip_color=(0,0,168)
    elif lips=='pink': lip_color=(93,000,170)
    elif lips=='orangecoral' : lip_color=(3,0,186)
    elif lips=='orangenude': lip_color=(0,11,48)
    elif lips=='coral' : lip_color=(1,0,48)
    elif lips=='lightpink' : lip_color=(226,0,255)
    elif lips=='purple' : lip_color=(255,0,141)
    elif lips=='nude' : lip_color=(48,4,32)
    else : lip_color=()

     # 색상별 BGR값 부여 -> blush
    if blush=='red': blush_color=(3,0,196)
    elif blush=='orange': blush_color=(0,0,168)
    elif blush=='pink': blush_color=(93,000,170)
    elif blush=='orangecoral' : blush_color=(3,0,186)
    elif blush=='orangenude': blush_color=(0,11,48)
    elif blush=='coral' : blush_color=(1,0,48)
    elif blush=='lightpink' : blush_color=(226,0,255)
    elif blush=='purple' : blush_color=(255,0,141)
    elif blush=='nude' : blush_color=(48,4,32)
    else : blush_color=()
    
     # 색상별 BGR값 부여 -> foundation
    if foundation=='none': gamma=1
    elif foundation=='autumnwarm': gamma=4
    elif foundation=='warmbeige': gamma=2
    elif foundation=='coolpink' : gamma=0.5
    elif foundation=='lightbeige': gamma=0.8
    
    base_path = '/home/ec2-user/app/higher/'
    full_path = os.path.join(base_path, filePath)
    
    # 파일경로 불러오기
    image = cv2.imread(filePath, cv2.IMREAD_UNCHANGED)
    
    #print('원본 파일 경로')
    #print(full_path)
    
    # 각 부위 별 색상 넣기
    output_lip = apply_makeup(image, False, 'lips', lip_color, 1, False)
    output_blush = apply_makeup(image, False, 'blush', blush_color, 1,False)
    output_foundation = apply_makeup(image, False, 'foundation',(0,0,0), gamma, False)
    
    # Blend the three output images together
    alpha1 = 0.4
    alpha2 = 0.4  
    alpha3 = 0.2  
    beta = 1 - (alpha1 + alpha2 + alpha3)
    blend = cv2.addWeighted(output_lip, alpha1, output_blush, alpha2, 0)
    blend = cv2.addWeighted(blend, 1, output_foundation, alpha3, 0)
    
    # output 저장 -> 뒤에 시간에 따른 uuid 붙음
    output_filename = f"output_{int(time.time())}_{uuid.uuid1()}.jpg"
    output_filepath_local= f"./img/{output_filename}"
    cv2.imwrite(output_filepath_local, blend)
    #print('output 로컬 경로')
    #print(output_filepath_local)
    #print(output_filename)
    
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