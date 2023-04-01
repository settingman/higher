const ctx = document.getElementById('myChart');

new Chart(ctx, {
	type: 'radar',
	data: {
		labels: ['유수분밸런스', '민감도', '색소침착도', '탄력'],
		datasets: [{
			label: '내 피부',
			data: resultScore,
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

function saveResult(){
	
	$.ajax({
		url: '',
		type: 'POST',
		data: {
			mbti : mbti
		},
		success: function(r){
			
		},
		error: function(e){
			console.log(e);
		}
		
	});
}
