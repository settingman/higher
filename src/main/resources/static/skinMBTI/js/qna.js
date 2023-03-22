var totalSlides = $(".part_chk").length;
var sliderWidth = $(".survey__form").width();
var slideIndex = 0;
var part1Len = $("#part1").children().length;
var part2Len = $("#part2").children().length;
var part3Len = $("#part3").children().length;
var part4Len = $("#part4").children().length;
const select = [[0,0,0,0], [0,0,0,0], [0,0,0,0], [0,0,0,0]];


$(document).ready(function() {
	$("input:radio").on('click', function() {
		var value = $(this).val();
		calc(value);
		if(value.split(',')[2] == ' D3'){
			checkSelect();
		}
		move('next');
	});
	$("#prev").hide();
	$("#finish").hide();
	$("#surveyForm").hide();
	$("#consentForm").hide();
	$("#resultBox").hide();
	$(".question__all").width(sliderWidth * totalSlides + 'px');
});

function checkSelect(){
  for(var i=0; i<=3; i++){
	for(var j=1; j<=3; j++){
		if(select[i][j] == 0){
			alert('선택되지 않은 항목이 있습니다.');
			return;
		}
	}
  }
  goResult();
}

function calc(obj){
	var score = obj.split(',')[0];
	var type = obj.split(',')[2];
	if(type.charAt(1) == 'A'){
		select[0][type.charAt(2)] = score;
	}else if(type.charAt(1) == 'B'){
		select[1][type.charAt(2)] = score;
	}else if(type.charAt(1) == 'C'){
		select[2][type.charAt(2)] = score;
	}else if(type.charAt(1) == 'D'){
		select[3][type.charAt(2)] = score;
	}	

}
console.log(totalSlides);
function move(obj) {
	if (obj == 'next') {
		var mo = 'next';
		var moIdx = 1;
	} else {
		var mo = 'prev';
		var moIdx = -1;
	}
	if (!(slideIndex == totalSlides - 1 && mo == 'next')) {
		showSlides(moIdx);
	}
	console.log(slideIndex);
	if (slideIndex == 0) {
		$("#prev").hide();
		$("#next").show();
	} else if (slideIndex == (totalSlides - 1)) {
		$("#prev").show();
		$("#next").hide();
		$("#finish").show();
	} else {
		$("#prev").show();
		$("#next").show();
		$("#finish").hide();
	}
	if (slideIndex == (part1Len) && mo == 'next') {
		$("#chapter1").removeClass('survey__chapter__on');
		$("#chapter2").addClass('survey__chapter__on');
	} else if (slideIndex == (part1Len - 1) && mo == 'prev') {
		$("#chapter1").addClass('survey__chapter__on');
		$("#chapter2").removeClass('survey__chapter__on');
	} else if (slideIndex == (part1Len + part2Len) && mo == 'next') {
		$("#chapter2").removeClass('survey__chapter__on');
		$("#chapter3").addClass('survey__chapter__on');
	} else if (slideIndex == (part1Len + part2Len - 1) && mo == 'prev') {
		$("#chapter2").addClass('survey__chapter__on');
		$("#chapter3").removeClass('survey__chapter__on');
	} else if (slideIndex == (part1Len + part2Len + part3Len) && mo == 'next') {
		$("#chapter3").removeClass('survey__chapter__on');
		$("#chapter4").addClass('survey__chapter__on');
	} else if (slideIndex == (part1Len + part2Len + part3Len - 1) && mo == 'prev') {
		$("#chapter3").addClass('survey__chapter__on');
		$("#chapter4").removeClass('survey__chapter__on');
	}
}
function showSlides(n) {
	slideIndex += n;
	$(".question__all").css("left", (-(slideIndex * $(".survey__form").width()) + 'px'));
}