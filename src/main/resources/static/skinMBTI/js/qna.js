var totalSlides = $(".part_chk").length;
var sliderWidth = $(".survey__form").width();
var slideIndex = 0;
var part1Len = $("#part1").children().length;
var part2Len = $("#part2").children().length;
var part3Len = $("#part3").children().length;
var part4Len = $("#part4").children().length;
const select = [[0,0,0,0], [0,0,0,0], [0,0,0,0], [0,0,0,0]];


$(document).ready(function() {
	// 문항 선택 시 선택 값 저장 + 다음 페이지로 이동
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

// 전체 항목을 선택한 지 확인하는 함수
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

// 점수 계산
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

// 다음 페이지로 이동하는 함수
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
	
	if(slideIndex>=0 && slideIndex<=2){
		$('.step_num li').removeClass('on');
		$('#step1').addClass('on');
	}else if(slideIndex>=3 && slideIndex<=5){
		$('.step_num li').removeClass('on');
		$('#step2').addClass('on');
	}else if(slideIndex>=6 && slideIndex<=8){
		$('.step_num li').removeClass('on');
		$('#step3').addClass('on');
	}else if(slideIndex>=9 && slideIndex<=11){
		$('.step_num li').removeClass('on');
		$('#step4').addClass('on');
	}
}

// 슬라이드
function showSlides(n) {
	slideIndex += n;
	$(".question__all").css("left", (-(slideIndex * $(".survey__form").width()) + 'px'));
}