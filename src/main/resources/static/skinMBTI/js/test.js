const qnaList = [
    {
        q: '1. 얼굴을 가까이 봤을 때 모공 수가 많고 크기가 커보이나요?',
        a: [
            { answer: '전혀 그렇지 않아요', type: [0, 1] },
            { answer: '얼굴의 일부에만 작게 보여요', type: [0, 2] },
            { answer: '곳곳에 꽤 도드라져 보여요', type: [0, 3] },
            { answer: '얼굴 전체에 큰 모공이 많아요', type: [0, 4] },
        ]
    },
    {
        q: '2. 외출 후 반나절이 지나면 피부가 번들거리나요?',
        a: [
            { answer: '외출 전보다 윤기가 없어요', type: [0, 1] },
            { answer: '외출 전화 변함이 없어요', type: [0, 2] },
            { answer: '약간 번들거리고 윤기가 있어요', type: [0, 3] },
            { answer: '많이 번들거리고 기름져요', type: [0, 4] },
        ]
    },
    {
        q: '3. 평소 얼굴에 건조함이 느껴지는 부위는 어디인가요?',
        a: [
            { answer: '없어요', type: [0, 1] },
            { answer: 'U존 일부(볼 혹은 턱)', type: [0, 2] },
            { answer: 'U존 전체(볼과 턱)', type: [0, 3] },
            { answer: '얼굴 전체', type: [0, 4] },
        ]
    },
    {
        q: '4. 최근 2주 사이에 생긴 여드름이 얼마나 있었나요?',
        a: [
            { answer: '없었어요', type: [1, 1] },
            { answer: '약간 있었어요(1~3개)', type: [1, 2] },
            { answer: '꽤 있었어요(4~6개)', type: [1, 3] },
            { answer: '많았어요(7개 이상)', type: [1, 4] },
        ]
    },
    {
        q: '5. 여드름이 생기면 주로 어떤 상태인가요?',
        a: [
            { answer: '생기지 않아요', type: [1, 1] },
            { answer: '하얀 피지가 깔끔하게 나와요', type: [1, 2] },
            { answer: '피고름이 약간 나오고 끈적해요', type: [1, 3] },
            { answer: '크게 곪고 통증이 심해요', type: [1, 4] },
        ]
    },
    {
        q: '6. 세안 직후 얼굴에 홍조나 붉은 기가 보이나요?',
        a: [
            { answer: '전혀 안 보여요', type: [1, 1] },
            { answer: '지금은 없지만 가끔 보여요', type: [1, 2] },
            { answer: '부분적으로 붉게 보여요', type: [1, 3] },
            { answer: '전체적으로 붉게 보여요', type: [1, 4] },
        ]
    },
    {
        q: '7. 얼굴에 주근깨, 잡티, 기미가 두드러지게 보이나요?',
        a: [
            { answer: '전혀 안 보여요', type: [2, 1] },
            { answer: '거의 안 보여요', type: [2, 2] },
            { answer: '약간 눈에 띄어요', type: [2, 3] },
            { answer: '곳곳에 많이 보여요', type: [2, 4] },
        ]
    },
    {
        q: '8. 하루 중 몇 시간 정도 강한 햇빛에 노출되나요?',
        a: [
            { answer: '노출되지 않아요', type: [2, 1] },
            { answer: '1시간 이내로 노출돼요', type: [2, 2] },
            { answer: '1~2시간 이상 노출돼요', type: [2, 3] },
            { answer: '반나절 이상 노출돼요', type: [2, 4] },
        ]
    },
    {
        q: '9. 여드름이나 트러블이 생겼던 곳에 흔적이 남아있나요?',
        a: [
            { answer: '흔적이 남지 않아요', type: [2, 1] },
            { answer: '연하게 흔적이 남지만 금방 사라져요', type: [2, 2] },
            { answer: '진한 흔적이 며칠간 지속돼요', type: [2, 3] },
            { answer: '매우 진한 흔적이 일주일 넘게 지속돼요', type: [2, 4] },
        ]
    },
    {
        q: '10. 입을 다물고 살짝 미소 지어보세요. 입 주변에 팔자주름이 생기나요?',
        a: [
            { answer: '전혀 생기지 않아요', type: [3, 1] },
            { answer: '미소 지을 때만 약간 생겨요', type: [3, 2] },
            { answer: '미소 지을 때 진하게 생겨요', type: [3, 3] },
            { answer: '미소 짓지 않아도 생겨요', type: [3, 4] },
        ]
    },
    {
        q: '11. 주름의 깊이가 어떤가요? 선이 굵고 뚜렷한지, 얇고 여러 겹인지 봐주세요.',
        a: [
            { answer: '주름이 없어요', type: [3, 1] },
            { answer: '잔주름이에요', type: [3, 2] },
            { answer: '깊은 주름이에요', type: [3, 3] },
            { answer: '잔주름과 깊은 주름 다 있어요', type: [3, 4] },
        ]
    },
    {
        q: '12. 평소 과일이나 채소를 얼마나 자주 먹나요?',
        a: [
            { answer: '하루에 세 번은 먹어요', type: [3, 1] },
            { answer: '하루에 한 번은 먹어요', type: [3, 2] },
            { answer: '어쩌다 가끔 먹어요', type: [3, 3] },
            { answer: '전혀 안 먹어요', type: [3, 4] },
        ]
    }
]

qnaList.map(item=>{
	let question = item.q;
	let answer = item.a;
	let part =`<div class="part_chk show" id="part1_1">
													<div class="survey__container">
														<div class="qna-num-box">
															<div>Question 1-1</div>
														</div>
														<div>
															<h3 class="q-box" th:text>세안 후 제품을 바르지 않은 상태에서 내 피부는?</h3>
														</div>
														<div class="survey__radio">
															<div class="survey__radio__answer4">
																<input style="display: none;" type="radio" id="1_1_1"
																	name="1" value="1, 매우 거칠고 벗겨지거나 텁수룩하다. , A1"> <label
																	for="1_1_1">
																	<div style="background: center/contain no-repeat url(/skinMBTI/img/1-1/1-1-1.png);">
																	</div> <span>매우 거칠고 벗겨지거나<br>텁수룩하다.
																</span>
																</label>
															</div>
															
														</div>
													</div>
												</div>
												`;
	 $("#part1").append();
	
	
	
})
