
var num = 3000;
var speed = 1000; //300
var swiperMain = new Swiper('.main_top .swiper-container', { 
    spaceBetween: 0,
    speed : speed,
    navigation: {
        nextEl: '.main_top .swiper-button-next',
        prevEl: '.main_top .swiper-button-prev',
    },
    effect: 'fade',
    on: {
    	init: function(){
    		//timer();
    	},
    },
    //autoplayDisableOnInteraction: true,
    //autoplay:3000,
    loop : false,
    /*autoplay: {
        delay: num,
    },*/
});
function timer(){
	var width = $('.timer_wrap').width();
	var tWidth = $('.timer').width();
    $('.timer').animate({"width":width}, num + speed);
    $('.timer').animate({"width":0}, 0);
}
swiperMain.on('slideChange',function(){
	var index = swiperMain.realIndex;
	$('.current_num .current').text('0' + (index + 1));
	$('.current_num .next').text('0' + (index + 2));
	//if(index == 2){
	//	$('.current_num .next').text('0' + 1);
	//}
});
swiperMain.on('transitionEnd',function(){		
		$('.timer').stop().css('width',0);
		timer();
});
$('.current_num').on('mouseover',function(){
	$('.current').hide();
	$('.autoplay').show();
});
$('.main_control').on('mouseleave',function(){
	$('.autoplay').hide();
	$('.current').show();	
});
$('.stop').on('click',function(e){
	e.preventDefault();	
	$('.timer').stop().css('width',0);
	swiperMain.autoplay.stop();
	$(this).hide();
	$('.play').show();
});
$('.play').on('click',function(e){
	e.preventDefault();
	$(this).hide();
	$('.stop').show();
	swiperMain.autoplay.start();
	timer();	
});
//슬라이드자동재생
$('.play').trigger('click');

var insta_length = $('.instagram_list .swiper-slide').length;
var paging = ((100 / insta_length) + '%');
/*
//기존스크롤페이징인스타그램swiper
var swiper = new Swiper('.instagram_list .swiper-container', {
	slidesPerView: 'auto',
	speed : 700,
    centeredSlides: true,
    spaceBetween: 40,
    loop: false,
	pagination: {                   
        el: '.swiper-pagination',
        type: 'progressbar',
        clickable: false,
    },
    //scrollbar: {
        //el: '.swiper-scrollbar',
        //hide: false,
      //},
});
*/

var swiperInstar = new Swiper('.instagram_list .swiper-container', { 
    slidesPerView: 'auto',
	speed : 700,
    centeredSlides: true,
    spaceBetween: 40,
    loop: false,
    loopFillGroupWithBlank : false,
    navigation: {
        nextEl: '.instagram_list .swiper-button-next',
        prevEl: '.instagram_list .swiper-button-prev',
    },
    pagination: {                   
        el: '.swiper-pagination',
        type: 'progressbar',
        clickable: false,
    },
});
/*
swiperInstar.on('slideChange',function(){
	var Lastindex = swiperInstar.activeIndex;
	if(Lastindex == (insta_length - 3)){
		swiperInstar.allowSlideNext = false;
		$('.instagram_list .swiper-button-next').hide();
	}else{
		swiperInstar.allowSlideNext = true;
		$('.instagram_list .swiper-button-next').show();
	}
});
*/
if (insta_length < 4){
	//console.log('at least');
	swiperInstar.allowSlidePrev = false;
	swiperInstar.allowSlideNext = false;
	$('.instagram_list .swiper-button-prev').hide();
	$('.instagram_list .swiper-button-next').hide();
	
} else{
	swiperInstar.allowSlidePrev = true;
	swiperInstar.allowSlideNext = true;
}


//$('.insta_bullet .swiper-pagination-bullet').width(paging);
 var temp = [$('.main_area .txt_1'),$('.main_area .txt_2'),$('.main_area .more'),$('h2'), $('.msg')]
 
 TweenMax.set(temp, {y:50, opacity:0});
 
 //section1
 //TweenMax.set($('.section_1 .bg_image'), {opacity:0 }); 
 TweenMax.set($('.section_1 img.s1_img2'), {x:-50, opacity:0, });
 TweenMax.set($('.section_1 img.s1_img3'), {x:0, y:120,opacity:0, });
//section2
 TweenMax.set($('.section_2 img.s2_img1'), {x:0, y:50, opacity:0, });
 TweenMax.set($('.section_2 img.s2_img2'), {x:0, y:0,opacity:0, });
 
$(window).on('load scroll',function() { 
    $('.main_area:not(.ok)').each(function(i){
    	//console.log($(window).scrollTop(), window.innerHeight);
    	//if($(window).scrollTop() > window.innerHeight/3){ 메인배너하단 영상 없을 경우
    	//if($(window).scrollTop() > window.innerHeight+300 )
    		if($(window).scrollTop() > window.innerHeight/3){
    		$('.section_1').addClass('ok');
    		var agent = navigator.userAgent.toLowerCase();
    		if (agent.indexOf("msie") != -1) {
    			$('.mask_layer').hide();
    			$('.bg_image').addClass('bg_mask');

    		} else if(agent.indexOf("rv:11.0") != -1){
    			$('.mask_layer').hide();
    			$('.bg_image').addClass('bg_mask');
    		} else if(agent.indexOf("edge/") != -1){
    			$('.mask_layer').hide();
    			$('.bg_image').addClass('bg_mask');
    		}else{
    			$('.mask_layer').addClass('mask');
    		}    		    		
    		var temp2 =  [$('.section_1').find('.txt_1'),$('.section_1').find('.txt_2'),$('.section_1').find('.more'),$('.section_1').find('i')];
    		TweenMax.to($(this).find('.s1_img2'),1.5,{opacity:1,x:0,ease: SlowMo.easeOut,delay:2.75});
            TweenMax.to($(this).find('.s1_img3'),1.5,{opacity:1,y:90,ease: SlowMo.easeOut,delay:3.65});
            TweenMax.to(temp2, 5.2, {opacity:1, y:0,ease: SlowMo.easeOut,delay:1});
    	}
    	if($(this).offset().top - $(this).outerHeight()*0.5 <= $(window).scrollTop()){                             
            $(this).addClass('ok');
            var temp2 =  [$(this).find('.txt_1'),$(this).find('.txt_2'),$(this).find('.more'),$(this).find('i')];
            if($('.section_2').hasClass('ok')){
            	TweenMax.to($(this).find('.s2_img1'),1.5,{opacity:1,y:0,ease: SlowMo.easeOut});
                TweenMax.to($(this).find('.s2_img2'),1.5,{opacity:1,x:-116,ease: SlowMo.easeOut,delay:.92});
                TweenMax.to(temp2, 5.2, {opacity:1, y:0,ease: SlowMo.easeOut,delay:1});
            }   
            TweenMax.staggerTo([$(this).find('h2'),$(this).find('.msg')], 1.2, {opacity:1, y:0,ease: SlowMo.easeOut},0.3,function(){});             
        }
    });
    var s3h = $('.section_bg').offset().top;
    //var s4h = $('.section_4').offset().top;
    var wTop = $(window).scrollTop();
    var sr = s3h - (824 / 2);    
    if(wTop > sr){
    	$('.section_bg .bg').css('opacity','0.6');
    	setTimeout(function(){
    		$('.section_bg .tit').css('opacity','1');
    	},300);    	
    	if(wTop > s3h + 200){
    		$('.section_bg .tit').addClass('fixed');
    	} else{
    		$('.section_bg .tit').removeClass('fixed');
    	}
    } else{
    	$('.section_bg .bg').css('opacity','0');
    	$('.section_bg .tit').css('opacity','0');
    	$('.section_bg .tit').removeClass('fixed');
    }
});
$(window).on('load resize',function(){
	var wrap = $('.section_3 .swiper-container');
	var img = $('.section_3 .swiper-slide').height();
	var textHeight = $('.section_3 .middle').height();
	var height = (textHeight - img) / 2;	
	wrap.css('padding-top', height);
});
$(window).on('load',function(){	
	var swiperProduct = new Swiper('.section_3 .swiper-container', { 
	    //spaceBetween: -3.125 + '%',
	    speed : 1200,
	    centeredSlides: true,    
	    slidesPerView: 'auto',
	    simulateTouch : false,
	    allowSwipeToNext: false,
	    allowSwipeToPrev: false,
	    loop : true,  
	    on: {
	    	init: function(){	    		
	    	},
	    },
	});	
	$('.item_text li > a').each(function(){	
		$(this).on('click',function(e){
			e.preventDefault();
			$('.item_text li a').removeClass('on');
			$(this).addClass('on');
			var index = $(this).parent().index();
			swiperProduct.slideTo(index+4);			
			$('.item_text li div').css('opacity',0).slideUp(700);			
			setTimeout(function(){
				$('.item_text li').eq(index).find('div').slideDown(500).css('opacity',1);
			},700);			
		});	
	});
});