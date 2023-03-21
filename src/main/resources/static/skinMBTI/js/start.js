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