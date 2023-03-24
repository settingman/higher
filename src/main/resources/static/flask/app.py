from flask import Flask, request
from flask_cors import CORS
from utils import apply_makeup
import cv2
import enum
import uuid
import time
import json

app = Flask(__name__)
CORS(app)

class FeatureChoice(str, enum.Enum):
    lips = 'lips'
    blush = 'blush'
    foundation = 'foundation'


@app.route('/apply-makeup/', methods=['POST'])
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
    
    # 각 부위 별 색상 넣기
    output_lip = apply_makeup(image, False, 'lips', color, 1, False)
    output_blush = apply_makeup(image, False, 'blush', color, 1,False)
    output_foundation = apply_makeup(image, False, 'foundation',(0,0,0), gamma, False)
    
    # Blend the three output images together
    alpha1 = 0.5 
    alpha2 = 0.3  
    alpha3 = 0.2  
    beta = 1 - (alpha1 + alpha2 + alpha3)
    blend = cv2.addWeighted(output_lip, alpha1, output_blush, alpha2, 0)
    blend = cv2.addWeighted(blend, 1, output_foundation, alpha3, 0)
    
    # output 저장 -> 뒤에 시간에 따른 uuid 붙음
    output_filename = f"output_{int(time.time())}_{uuid.uuid4()}.jpg"
    output_filepath1= f"../img/{output_filename}"
    cv2.imwrite(output_filepath1, blend)
    
    output_filepath = output_filepath1[1:]
    
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