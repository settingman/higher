const ctx = document.getElementById('myChart');
let score1 = $("#score1").val();
let score2 = $("#score2").val();
let score3 = $("#score3").val();
let score4 = $("#score4").val();
let scores = [score1, score2, score3, score4];

// 결과 차트 
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

// 진단 결과 저장 함수
function saveResult(mbti){
	$(".layer-pop").trigger("click");
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

// 매칭 화장품 페이지로 이동
function goMatch(mbti){
	let url = "/match/main?mbti=" + mbti;
	
	$(location).attr('href',url);
}

$(function(){
	$("#save_close").on("click", function(){
		$("#survey_pop").css("display", "none");	
	});
	
});

var UI = UI || {};
UI = {
    init : function () {
        var scrollTop, winW, winH, headerH;
        $win = $(window);
        $doc = $(document);
        $html = $('html');
        $body = $('body');
        $wrap = $('#wrap');
        UI.ini.init(); // 시작 셋팅
        UI.POP.init(); // popup open,close
    },
    ini : {
        init : function () {
            $win.on('resize', function () {
                UI.ini.resizeWin();
            });
            UI.ini.resizeWin();
        },
        resizeWin : function () {
            winW = $win.innerWidth();
            winH = $win.innerHeight();
        },
        winLock : function () {
            $body.css({
                'overflow' : 'hidden',
                'height' : winH,
                'width' : '100%',
                'position' : 'relative'
            });
        },
        winUnlock : function () {
            $body.css({
                'overflow' : '',
                'height' : '',
                'position' : '',
                'width' : ''
            });
            if ($('.document-dimed').is(':visible')) {
                $('.document-dimed').remove();
            }
        }
    },
    POP : {
        init : function () {
            $btnClose = $('.layerpop_warp .close');
            this.event();
        },
        event : function () {
            var pop = this;
            $doc.on('click', '.layer-pop', function (e) {
                var $this = $(e.currentTarget);
                var id = $this.attr('href');
                var type = $this.attr('data-type');
                var url = $this.attr('data-url');
                var func = $this.attr('data-func');
                var addClass = $this.attr("data-addClass");
                var $con = $(id);
                var tf = false;
                $con.addClass('open');

                switch (type) {
                case "link":
                    linkLayerPop(id, url, func, addClass);
                    break;
                case "iframe":
                    var iframeUrl = $this.attr('data-iframe');
                    iframeLayerPop(id, iframeUrl);
                    break;
                default:
                    pop.open($con);
                }
                pop.resize($con);
                e.preventDefault();
           
            });

            $doc.on('click', '.close_pop', function (e) {
                var $con = $(this).closest('.layerpop_warp');
                pop.close($con);
                $con.removeClass('open');
                $con.find('.layerpop_cont').attr('style', '');
                e.preventDefault();
            });
            
           
        },
        open : function (obj) {
            // 180716 다중이 되게 변경
            // if($('.document-dimed').length == 0){
            if (obj.length > 0) { // 180822 팝업이 레이어가 있을 시에만 스크립트 진행
                $body.append('<div class="document-dimed"></div>');
                obj.show().addClass('open');
                UI.ini.winLock();
            }
            // }
        },
        close : function (obj) {
            if (obj.hasClass('link-type')) {
                obj.remove();
            } else {
                obj.hide();
            }
            obj.removeClass('open');
            obj.find('.layerpop_cont').attr('style', '');
            // 180716 다중이 되게 변경
            $('.document-dimed:last').remove();
            if ($('.document-dimed').length == 0) {
                UI.ini.winUnlock();
            }
        },
        resize : function (id) {
            var $con = $(id);
            $con.find('.layerpop_cont').css('height', 'auto');
            $(window).resize(function () { // 윈도우 팝업 높이값 조절
                var $pop = $con.find('.layerpop'), pop_h = $pop.height(), popHead_h = $pop.find('.layerpop_head').height(), win_h = $(window).height(), resize_h;

                if (win_h <= pop_h) {
                    $pop.css('height', '100%');
                    resize_h = $pop.height() - popHead_h;
                } else {
                    resize_h = $pop.height() - popHead_h;
                }
                $con.find('.layerpop_cont').css({
                    'height' : resize_h
                });

      
            }).resize();
        }
    }
};
