/** 
 * global variable : 비동기 로딩 이미지 사용여부
 * 비동기 로딩이미지를 사용하지 않을 경우, false로 설정할 것
 */
var ajaxLoadImgUse = true;

var app = (function($) {
	var init = function(option) {
		console.log('app.init()');

		/* ajax start , end Method
		 */
		$(document).ajaxStart(function(e){
			/*console.log("ajax start - " + ajaxLoadImgUse)
			if(ajaxLoadImgUse && $(".loading-dimed").length == 0) {
				loading_show();
			}*/
		});

		$(document).ajaxStop(function(){
			/*if($(".loading-dimed").length > 0) {
				loading_remove();
			}
			ajaxLoadImgUse = true;*/
		});

		// 비동기 로그인 요청
		$(document).ajaxError(function (event, xhr, thr){
			if(xhr.status == 401){
				// alert($.i18n.prop('msg_cms_200000153'));
				alert('로그인이 필요합니다.');
				location.href="/login?autoUrl=" + encodeURI(location.href);
				return;
			} else {
				if(thr.url != "/inc/log") {
					alert('데이터 처리 도중 에러가 발생하였습니다.');
				}
			}
			ajaxLoadImgUse = true;
		});

		// vaildata alter 변경
		$.extend( $.validator.defaults, {
			 invalidHandler: function(form, validator) {
				var errors = validator.numberOfInvalids();
				if (errors) {
					alert(validator.errorList[0].message);
					var errEle = validator.errorList[0].element;
					// CK EDITOR 경우
					if($(errEle).prop("tagName")=="TEXTAREA" && $(errEle).next().hasClass("cke")) {
						CKEDITOR.instances[$(errEle).next().attr("id").replace("cke_","")].focus();
					// FILE 경우
					//} else if ($(errEle).attr('type') === "file" && $(errEle).data('style') === "fileinput"){
					//	$(validator.errorList[0].element).click();
					} else {
						validator.errorList[0].element.focus();
					}
				}
			},
			ignore: [], // CKEDITOR가 텍스트 영역을 숨기므로 jQuery 유효성 검사는 요소의 유효성을 검사하지 않습니다.
			onkeyup:false,
			onfocusout:false,
		    showErrors:function(errorMap, errorList){
	            /*if(this.numberOfInvalids()) { // 에러가 있을 때만..
	                alert(errorList[0].message);
	                $(errorList[0].element).focus()
	            }*/
	        },
			errorPlacement: function(error, element) {
				/*if (element.attr('type') === "file" && element.data('style') === "fileinput"){
					error.appendTo(element.closest("div.fileinput-holder").parent('div'));
				} else if (element.data('parent_class')) {
					error.appendTo(element.parents('.'+element.data('parent_class')));
				} else {
					error.insertAfter(element)
				}*/
			}
		});

	};

	return {
		init: init
	};
}(jQuery));

/**
 * vaildator 공백 처리
 * */
(function ($) {
    $.each($.validator.methods, function (key, value) {
        $.validator.methods[key] = function () {
            if(arguments.length > 0) {
                arguments[0] = $.trim(arguments[0]);
            }

            return value.apply(this, arguments);
        };
    });
} (jQuery));

/**
 * 전역함수
 **/
// 일시포맷
function formatDate(date, format) {
	if(!format) {
		format = 'YYYY-MM-DD';
	} 
	return (date ? moment(date).format(format) : '');
}
// 레이어팝업 오픈
function openPopup(options) {
	if (options.event) {
		event.preventDefault()
	}
	$('body').append('<div id="popup_result" />');
	$('html, body').addClass('body_hidden');
	//$('#popup_result').load(options.url + ' #popup_layer');
	$('#popup_result').load(options.url);
	if (options.callback) {
		$('#popup_result').data('callback', options.callback);
	}
}
//레이어팝업 클로즈
function closePopup(event) {
	if (event) {
		event.preventDefault();
	}
	$('#popup_result').remove();
	$('html, body').removeClass('body_hidden');
}
//메인팝업 오픈
function openMainPopup(options) {
	if (options.event) {
		event.preventDefault()
	}
	$.ajax({
		type: 'post', dataType: 'html',
		url: options.url,
	}).done(function(html) {	
		if (!html) {
			return false;
		}
		if ($('body .popup_main:last').length > 0) {
			var lastRight = $('.popup_main:last').css('right');
			$('body').append($(html).css('left', lastRight).get(0));
		} else {
			$('body').append(html);
		}
	});
}
//메인팝업 클로즈
function closeMainPopup(classNm) {
	$('.' + classNm).remove();
}
// 함수이름으로 함수정의 얻기
function getFunctionByNm(nm) {
	if (!nm || !nm.split('.')) {
		return null;
	}
	var ret = window;
	nm.split('.').forEach(function(item, index) {
		ret = ret[item];
	});
	return ret;
};
// 검색결과 레이어창 호출
function openSearchResult(keyword, lang) {
	$.ajax({
		dataType: 'html', type : 'post',
		url: '/' + lang + '/search/popResultAjaxHtml',
		data: {keyword : keyword},
	}).done(function(html) {
		$('#search').empty().html(html);
	});    
}
function openMobileSearchResult(keyword, lang) {
	$.ajax({
		dataType: 'html', type : 'post',
		url: '/' + lang + '/search/popResultAjaxHtml',
		data: {keyword : keyword},
	}).done(function(html) {
		$('#search').css('right', 0);
		$('#search').empty().html(html);
	});    
}
function changeUrl(state, title, url) {
    if (typeof (history.pushState)) { //브라우저가 지원하는 경우
        history.pushState(state, title, url);
    }
    else {
        location.href = url; //브라우저가 지원하지 않는 경우 페이지 이동처리
    }
}
