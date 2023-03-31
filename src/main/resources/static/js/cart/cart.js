$(function(){
	/* 장바구니에서 가격 변경 박스 닫기 버튼 동작*/
	$(".change-infobox a.close").on("click",  function(e) {
		/* 동작요청에 따라 remove or hide */
		$(this).parents("tr").removeClass("has-change-infobox");
		$(this).parents(".change-infobox-wrap").remove();
		return false
	});

	// 장바구니 좌측 체크박스 스타일링 & 클릭 영역 확장
/*	 $(".cart-list-wrap input").iCheck({increaseArea: '40%'});
*/
	$(".cart-list-wrap input[name='checkbox_all']").on("ifChanged", function() {
		if($(this).prop("checked") ){
			$(".cart-list-wrap input[name='"+$(this).val()+"']").iCheck("check");
		}
		else $(".cart-list-wrap input[name='"+$(this).val()+"']").iCheck("uncheck");
	});


	// 2015-08-10 묶음배송 띠 위치 조절.
	$("#js-cart-table tr.bind-top").each(function() {
		$bind_tape = $(this).find(".prod-info").parent("td").find(".bind-infomation-wrap p");
		//$infobox_tape = $(this).find(".prod-info").parent("td").find(".change-infobox-wrap .change-infobox");

		if($bind_tape.length > 0)
		{
			//$(this).find(".price-info").outerWidth(true)
			// 끝 두 td 길이+ 우측 padding 값 만큼 margin-right 를 - 한다.
			$width = $(this).find(".shipping").outerWidth(true) + $(this).find(".ctrl").outerWidth(true) ;
			
			//$width = $(this).find(".shipping").width() + $(this).find(".ctrl").width() ;
			$mr = $width + $(this).find(".price-info").outerWidth(true) + (parseInt($(this).find(".prod-info").parent("td").css("padding-right")) || 0);
			

			$bind_tape.css({'width':$width, 'margin-right':$mr *-1});
			
			
		}
	});

	$('select[name="select_date"]').on('selectric-change', function(element){
		change_date($(this));
	});
});



(function( $ ) {
    /* 장바구니 사이드 플로팅 */
    fnAside_scroll();
})( jQuery );

function fnAside_scroll(tabId){
    var lastScrollTop = 0;
    $(function(){ $(".cart-body").css("min-height",$(".cart-aside-wrap").outerHeight(true)+20); }); //20190108 수정
    $(".cart-aside-wrap").css({"position":"absolute","width":"280px","z-index":"1000","left":"50%","margin-left":"280px!important", "top":"0px"});

    $(window).on('scroll', function() {
        $cart_scroll_area = $(".cart-body .cart-cont-wrap");
        $cart_scroll_target = $(".cart-aside-wrap");

        if($cart_scroll_target.outerHeight(true) >= $cart_scroll_area.height()) {
            //$cart_scroll_area.css("min-height",$cart_scroll_target.outerHeight(true));
            return false;
        }

        st = $(this).scrollTop();
        if(st < lastScrollTop) {
            aside_scrollup();
        }
        else {
            aside_scrolldown();
        }
        lastScrollTop = st;
    });


    function aside_scrolldown()
    {
        $cart_scroll_area = $(".cart-body .cart-cont-wrap");
        $cart_scroll_target = $(".cart-aside-wrap");
        if($cart_scroll_area.offset().top + $cart_scroll_target.height() - $(window).height() + 20 < $(window).scrollTop())
        {
            if( $(window).scrollTop() > $cart_scroll_area.offset().top + $cart_scroll_area.height() + 20 - $(window).height() )
            {
                $cart_scroll_target.css({"position":"absolute"});
            }
            else
            {
                $cart_scroll_target.css({"position":"fixed", "bottom":"20px","top":"auto"});
            }
        }
        else
        {
            if($cart_scroll_target.css("position") == 'fixed') $cart_scroll_target.css({"position":"absolute", "bottom":"auto","top":$cart_scroll_target.offset().top-$cart_scroll_area.offset().top});
        }
    }
    function aside_scrollup()
    {
        $cart_scroll_area = $(".cart-body .cart-cont-wrap");
        $cart_scroll_target = $(".cart-aside-wrap");
        if($cart_scroll_target.offset().top > $(window).scrollTop())
        {
            if( $(window).scrollTop() < $cart_scroll_area.offset().top - 20 )
            {
                $cart_scroll_target.css({"position":"absolute", "top":"0", "bottom":"auto"});
            }
            else
            {
                $cart_scroll_target.css({"position":"fixed", "top":"20px","bottom":"auto"});
            }
        }
        else
        {

            if($cart_scroll_target.css("position") == 'fixed') $cart_scroll_target.css({"position":"absolute", "bottom":"auto","top":$cart_scroll_target.offset().top-$cart_scroll_area.offset().top});
        }
    }
}
/* end 장바구니 사이드 플로팅 */

/* 쿠폰 스크롤 처리 */
$(document).ready(function() {
	$(".coupon-scroller ").mCustomScrollbar({
		mouseWheel:{ preventDefault: true }
	});
});