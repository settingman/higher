const main = document.querySelector("#main");
const qna = document.querySelector("#qna");
const result = document.querySelector("#result");
const endPoint = 12;
const select = [0, 0, 0, 0];

function calResult(){
  let resultWord = '';

  if(select[0] <= 7){
    resultWord += 'D';
  }else{
    resultWord += 'O';
  }

  if(select[1] <= 7){
    resultWord += 'S';
  }else{
    resultWord += 'R';
  }

  if(select[2] <= 7){
    resultWord += 'P';
  }else{
    resultWord += 'N';
  }

  if(select[3] <= 7){
    resultWord += 'W';
  }else{
    resultWord += 'T';
  }

  return resultWord;
}

function setResult(){
  let setWord = calResult();
  const resultName = document.querySelector(".resultName");
  resultName.innerHTML = setWord;

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

function addAnswer(answerText, qIdx, idx){
  var a = document.querySelector('.answerBox');
  var answer = document.createElement('button');
  answer.classList.add('answerList');
  answer.classList.add('my-1');
  answer.classList.add('py-2');
  answer.classList.add('mx-auto');
  answer.classList.add('fadeIn');

  a.appendChild(answer);
  answer.innerHTML = answerText;

  answer.addEventListener("click", function(){
    var children = document.querySelectorAll('.answerList');
    for(let i=0; i<children.length; i++){
      children[i].disabled = true;
      children[i].style.WebkitAnimation = "fadeOut 0.5s";
      children[i].style.animation = "fadeOut 0.5s";
    }
    setTimeout(() => {
      select[qnaList[qIdx].a[idx].type[0]] += qnaList[qIdx].a[idx].type[1];
      for(let i=0; i<children.length; i++){
        children[i].style.display = 'none';
      }
      goNext(++qIdx);
    }, 450)
  }, false);
}

function goNext(qIdx){
  if(qIdx === endPoint){
    goResult();
    return;
  }
  
  var q = document.querySelector(".qBox");
  
  
  
  q.innerHTML = qnaList[qIdx].q;

  for(let i in qnaList[qIdx].a){
    addAnswer(qnaList[qIdx].a[i].answer, qIdx, i);
  }

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
    let qIdx = 0;
    goNext(qIdx);
  }, 400);
}