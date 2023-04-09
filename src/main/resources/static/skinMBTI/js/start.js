const main = document.querySelector("#main");
const qna = document.querySelector("#qna");
const endPoint = 12;
const resultScore = [0, 0, 0, 0];
let agreeStat = 'disagree';
let mbti = '';


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
    resultWords += 'S';
  }else{
    resultWords += 'R';
  }

  if(resultScore[2] <= 7){
    resultWords += 'P';
  }else{
    resultWords += 'N';
  }

  if(resultScore[3] <= 7){
    resultWords += 'W';
  }else{
    resultWords += 'T';
  }
  
  return resultWords;
}

function goResult(){
   let mbti = calResult();

   $("#mbti").val(mbti);
   $("#score1").val(resultScore[0]);
   $("#score2").val(resultScore[1]);
   $("#score3").val(resultScore[2]);
   $("#score4").val(resultScore[3]);
   
   $("#resultFrm").submit();
}

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
