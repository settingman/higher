const main = document.querySelector("#main");
const qna = document.querySelector("#qna");
const result = document.querySelector("#result");
const endPoint = 12;
const resultScore = [0, 0, 0, 0];
let mbti = '';

function calResult(){
  for(var i=0; i<=3; i++){
	for(var j=1; j<=3; j++){
		resultScore[i] += parseInt(select[i][j]);
	}
  }

  let resultWords = ['', ''];

  if(resultScore[0] <= 7){
    resultWords[0] += 'D';
    resultWords[1] += '#건성';
  }else{
    resultWords[0] += 'O';
    resultWords[1] += '#지성';
  }

  if(resultScore[1] <= 7){
    resultWords[0] += 'S';
    resultWords[1] += ' #민감성';
  }else{
    resultWords[0] += 'R';
    resultWords[1] += ' #저항성';
  }

  if(resultScore[2] <= 7){
    resultWords[0] += 'P';
    resultWords[1] += ' #색소성';
  }else{
    resultWords[0] += 'N';
    resultWords[1] += ' #비색소성';
  }

  if(resultScore[3] <= 7){
    resultWords[0] += 'W';
    resultWords[1] += ' #주름';
  }else{
    resultWords[0] += 'T';
    resultWords[1] += ' #탄력';
  }
  
  return resultWords;
}

function setResult(){
  let setWord = calResult();
  const resultName = document.querySelector(".resultName");
  const resultKeyword = document.querySelector(".resultKeyword");
  resultName.innerHTML = setWord[0];
  resultKeyword.innerHTML = setWord[1];
  mbti = setWord[0];
  
  $.ajax({
	url : '/skinMBTIRest/getData?stype=' + mbti,
	type : 'get',
	success : function(r){
		$("#title").text('"' + r.stitle + '"');
		$(".resultExplain").text(r.sexplain);
		$("#pros").text(r.spros);
		$("#cons").text(r.scons);
		$("#solution").text(r.ssolution);
	},
	error : function(e){
		console.log(e);
	}
  });
  
}

function goResult(){
  qna.style.WebkitAnimation = "fadeOut 1s";
  qna.style.animation = "fadeOut 1s";
  setTimeout(() => {
    result.style.WebkitAnimation = "fadeIn 1s";
    result.style.animation = "fadeIn 1s";
    setTimeout(() => {
      qna.style.display = "none";
      result.style.display = "block";
    }, 400)});
    setResult();
}

function begin(){
  main.style.WebkitAnimation = "fadeOut 1s";
  main.style.animation = "fadeOut 1s";
  setTimeout(() => {
    qna.style.WebkitAnimation = "fadeIn 1s";
    qna.style.animation = "fadeIn 1s";
    setTimeout(() => {
      main.style.display = "none";
      qna.style.display = "block";
    }, 400)
  }, 400);
}