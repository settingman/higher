const main = document.querySelector("#main");
const qna = document.querySelector("#qna");
const endPoint = 12;
const resultScore = [0, 0, 0, 0];
let agreeStat = 'disagree';
let mbti = '';

// 약관 동의 상태값 변경
$(function(){
	$('#radio2_1').click(function(){
		agreeStat = 'agree';
		console.log(agreeStat);
	});
	
	$('#radio2_2').click(function(){
		agreeStat = 'disagree';
		console.log(agreeStat);
	});
});

// 전체 점수 계산 결과값 산출하는 함수
function calResult(){
  for(var i=0; i<=3; i++){
	for(var j=1; j<=3; j++){
		resultScore[i] += parseInt(select[i][j]);
	}
  }

  let resultWords = '';

  if(resultScore[0] <= 7){
    resultWords += 'D';
  }else{
    resultWords += 'O';
  }

  if(resultScore[1] <= 7){
    resultWords += 'R'; 
  }else{
    resultWords += 'S';
  }

  if(resultScore[2] <= 7){
    resultWords += 'N';
  }else{
    resultWords += 'P';
  }

  if(resultScore[3] <= 7){
    resultWords += 'T';
  }else{
    resultWords += 'W';
  }
  
  return resultWords;
}

// 결과 페이지로 이동하는 함수
function goResult(){
   let mbti = calResult();

   $("#mbti").val(mbti);
   $("#score1").val(resultScore[0]);
   $("#score2").val(resultScore[1]);
   $("#score3").val(resultScore[2]);
   $("#score4").val(resultScore[3]);
   
   $("#resultFrm").submit();
}

// 시작 버튼 클릭시 진단 페이지를 띄우는 함수
function begin(){
if(agreeStat == 'disagree'){
	alert('민감 정보 수집에 동의하셔야 참여 가능합니다.');
	return;
}else{
  main.style.WebkitAnimation = "fadeOut 1s";
  main.style.animation = "fadeOut 1s";
  setTimeout(() => {
    qna.style.WebkitAnimation = "fadeIn 1s";
    qna.style.animation = "fadeIn 1s";
    setTimeout(() => {
		$('.tip_wrap').css('display', 'none');
      main.style.display = "none";
      qna.style.display = "block";
    }, 400)
  }, 400);
	
}
}
