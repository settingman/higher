const ctx = document.getElementById('myChart');
let score1 = $("#score1").val();
let score2 = $("#score2").val();
let score3 = $("#score3").val();
let score4 = $("#score4").val();
let scores = [score1, score2, score3, score4];

new Chart(ctx, {
	type: 'radar',
	data: {
		labels: ['유수분밸런스', '민감도', '색소침착도', '탄력'],
		datasets: [{
			label: '내 피부',
			data: scores,
			borderWidth: 3,
			fill: false,
			borderColor: '#FFC90D',
			backgroundColor: '#FFC90D',
		}]
	},
	options: {
		scales: {
			r: {
				angleLines: {
					display: true
				},
				suggestedMin: 0,
				suggestedMax: 12
			}
		}
	}
});

function saveResult(mbti){
	
	$.ajax({
		url: '/skinMBTIRest/saveMBTI',
		type: 'POST',
		data: {
			mbti: mbti,
			mbti_scores: scores[0] + "," + scores[1] + "," + scores[2] + "," + scores[3]
		},
		success: function(r){
			$("#survey_pop").css("display", "block");
			
		},
		error: function(e){
			console.log(e);
		}
		
	});
}

function goMatch(mbti){
	let url = "/match/main?mbti=" + mbti;
	
	$(location).attr('href',url);
}

$(function(){
	$("#save_close").on("click", function(){
		$("#survey_pop").css("display", "none");	
	});
	
});

