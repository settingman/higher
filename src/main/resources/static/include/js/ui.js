$(function(){
    includeLayout();
    gnbNew();    
    
    /*dim 터치시 gnb 닫아짐*/
    function closeGnb(){
    	$('.box').css('opacity','0');
		setTimeout(function(){
			$('.dim').fadeOut();
			$('header').removeClass('sub_on');
			
		}, 500);
		setTimeout(function(){
			$('.box').hide();
			$('header').height('');			
		}, 800);
    }
    
    /*gnb 열림*/
    function gnbNew(){
    	$(document).on('click','.sub_link',function(){    		    		
    		var currH = $(this).parent().find('.box').outerHeight();
    		$('header').addClass('sub_on');
    		$('.dim').fadeIn();
    		$('.box').css('opacity','0').hide();
   			$(this).parent().find('.box').css('opacity','1').show();   			
   			if($('header').hasClass('up') == true){
   				$('header').height(525);
   			} else{
   				//$('header').height(686);
   				$('header').height(222+currH);
   			}
   			return false;
    	});
    	$(document).on('mouseenter','.dim',function(){
    		closeGnb();
    	});
    	
    	/*언어선택관련*/
    	$(document).on('mouseenter','header .arrow',function(e){
    		$(this).stop();
            $(this).addClass('over');
            $(this).children('ul').stop().slideDown();           
            e.stopPropagation();
        });
        $(document).on('mouseleave','header .arrow',function(e){
        	$(this).stop();
            $(this).removeClass('over');
            $(this).children('ul').stop().slideUp();
            e.stopPropagation();
        });
        $(document).on('focus','header .arrow > a',function(e){  
            $(this).next('ul').slideDown();
            e.stopPropagation();
        });
        $(document).on('focusout','header .arrow ul li:last-child a',function(e){
             $('header .arrow ul').slideUp();
             e.stopPropagation();
        });
    }
    /*마우스 스크롤 감지*/
    $(window).on('scroll',function() { 
        var before_position = $(this).scrollTop();        
        setTimeout(function(){
             
             if($(this).scrollTop() <= 686){
                $('.main').addClass('over');
                if($('header').hasClass('sub_on') == true){
                	$('header').height(686);
                }
                if($(this).scrollTop() == 0){
                 	$('header').removeClass('fixed');
                 	$('header').removeClass('down up');
                }
             }else{            	 
                 if(before_position > $(this).scrollTop()){
                	$('header').addClass('fixed');
                    $('.main').removeClass('over'); 
                    $('header').removeClass('down');
                    $('header').addClass('up');
                    //closeGnb();
                    $('.text_all').css('right','-520');                    
                    if($(this).scrollTop() <= 705){                    	
                    	$('header').removeClass('up'); 
                        $('header').addClass('down');
                        setTimeout(function(){
                        	$('header').removeClass('fixed');
                        	$('header').removeClass('down');                        	
                        },500)                        
                    }
                    if($('header').hasClass('sub_on')){
                    	$('header').height('525');
                    }
                    $('header .arrow').removeClass('over');
                    $('header .arrow ul').slideUp();
                 }else if(before_position < $(this).scrollTop()){
                    $('header').removeClass('up'); 
                    $('header').addClass('down');
                    $('.text_all').css('right','-520');
                    /*if($('header').hasClass('sub_on')){
                    	$('#gnb').css('top',0);
                    } else{
                    	//$('#gnb').css('top',-81);
                    }*/                   
                    closeGnb();
                    /*setTimeout(function(){
                    	$('header').addClass('fixed');                    	
                    },500);*/                                      
                } 
             }
     
        },50);
     })
     $(window).on('load',function(){
 		/* 검색 페이지 관련 */
 	    function searchResult(){
 	    	var insta_length = $('.search_result .swiper-slide').length;
 	    	var paging = ((100 / insta_length) + '%');
 	    	var swiper = new Swiper('.search_result .swiper-container', {
 	    		slidesPerView: 'auto',
 	    	    centeredSlides: true,
 	    	    speed : 700,
 	    	    loop: false,
 	    		pagination: {                   
 	    	        el: '.swiper-pagination',
 	    	        type: 'progressbar',
 	    	        clickable: false,
 	    	    },    
 	    	});    	
 	    	$('.search_result .swiper-pagination-bullet').width(paging);
 	    }   
 		var searchBox = $('.search_box input[type="text"]');
     	$('.go_search').on('click',function(e){    	
     		e.preventDefault();
     		closeGnb();
     		/*$('#search').show();
     		$('.search_box input[type="text"]').focus();
     		var height = $(window).height();
     		$('body').css('overflow', 'hidden');
     		$('#search').height(height);
     		$('#search .btn_search_close').show().css('opacity','1');
     		setTimeout(function(){
     			$('.search_area').css('opacity','1');
     		},300)   */  		
         });
     	$('.btn_ok').on('click',function(e){
     		e.preventDefault();
     		$('#search').addClass('active');		
     		var text = searchBox.val();
     		$('#search .tit').html('RESULT FOR' +  '&nbsp;' + '<strong>' + text + '</strong>');
     		console.log(text);
     		$('.recommend_word').hide();
     		$('.result_txt').show();
     		$('.search_result').fadeIn();
     		searchResult();
     	});
     	$('.btn_search_close').on('click',function(e){
     		/*e.preventDefault();     		   		
     		searchBox.val('');		
     		$('#search').removeClass('active');		
     		$('.result_txt .item').text('');
     		$('.recommend_word').show();
     		$('.result_txt').hide();
     		$('.search_result').hide();     		  
     		$('body').css('overflow','auto');
     		//$('#search').fadeOut();
     		$('.search_area').css('opacity',0);
     		$('#search .btn_search_close').hide();
     		$('#search').fadeOut();
     		setTimeout(function(){
     			$('#search').height('');
     		},700);
     		$('#search .tit').html('SEARCH');*/
     	});
     	var url = $(location).attr('href');
     	if(url.includes('promotion')){
     		$('#store_id').addClass('current');
     	}
     });
    
    
    /*백업*/
    function gnbEvent(){    
        /*$(document).on('mouseenter','.main header',function(){
            $('body').removeClass('over')
        });*/
        $(document).on('mouseleave','.main header',function(){
            $('body').addClass('over')
        });
        /*$(document).on('click','.gnb_inner > li > a.sub_link',function(e){
        	e.preventDefault();
        	$('body').removeClass('over')
            $('.gnb_inner > li').removeClass('active');
            $('#header.prd_detail header, #header.util_menu header, #header.new_prd_list header').addClass('on');
            /* 검색창이 열려 있지 않고 하위 메뉴가 있을시 메뉴 열림*/
            /*$(this).parent().addClass('active');
            if($(this).parent().find('.box').is('.box')){
                var ori_h= $(this).parent().find('.box').outerHeight(true)
                h = ori_h+h_header;                
                $('#gnb').css('height',h);                
                $(this).parent().find('.area').height(h-246)
            }else{
                 resetHeader();                 
            }
            $('.dim').fadeIn(300);
            $('.text_all').css('right','-520');
            $('header').css('background-color','#f5f2ed');
        });*/
     
        $(document).on('mouseleave','.box',function(){
        	//$('body').addClass('over');
        	$('#header.prd_detail header, #header.util_menu header, #header.new_prd_list header').removeClass('on');        	
            $('header').find('.box_sub').height('');
            $('.dim').hide();
            $('.text_all').css('right','-520');
            resetHeader();
            $('.gnb_inner > li').removeClass('active');
            $('header').css('background-color','');
            $(document).on('mouseleave','header .gnb_inner > li > a',function(){
            	
            });
        });
        $(document).on('focusout','.gnb_inner > li > div ul li:last-child a',function(){      
         
            $(this).parents('div.box').hide();
        });   
        

    }    

    /*$('.small_1, .small_2, .big_img, .img').hover(
        function(){ 
            TweenMax.to($(this).find('.effect_img_big'),.5,{scale:1.05,ease: Power3.easeOut});
        },function(){
            TweenMax.to($(this).find('.effect_img_big'),.5,{scale:1,ease: Power3.easeOut});
        }
    )*/
    
    
    
});

/* 상단 이동 버튼*/
var lastScrollTop = 0;
$(window).scroll(function(event){
   var st = $(this).scrollTop();
   if (st > lastScrollTop){
       // downscroll code
	   $('.btn_go_top').fadeIn();
   } else {
      // upscroll code
	   $('.btn_go_top').fadeOut();	   
   }
   lastScrollTop = st;
});
$(function(){
	$('.btn_go_top').on('click',function(){		
		//$('html, body').animate({scrollTop:0}, '1500');
		if($(window).scrollTop() == 0){
			setTimeout(function(){				
				$('header').removeClass('fixed up down');
			},200);			
		}		
	});
});

/* html include 역할*/
function includeLayout(){ 
    //확장자가 .html일 경우만 
    if(location.href.indexOf('.html') >0 ){
        var includeArea = $('[data-include]'); 
        var self, url; 
        $.each(includeArea, function() { 
            self = $(this); 
            url = self.data("include"); 
            self.load(url, function() { 
                self.removeAttr("data-include"); 
                
            }); 
        }); 
    }
}
/*
$(this).on("mousewheel DOMMouseScroll", function (e) {
                       
    var delta = 0;
    if (!event) event = window.event;
    
    if (event.wheelDelta) {
        delta = event.wheelDelta / 120;
        if (window.opera) delta = -delta;
    } else if (event.detail) delta = -event.detail / 3;
    var moveTop = null;
    
    // 마우스휠을 위에서 아래로  
  
    if (delta < 0) { 
       
    // 마우스휠을 아래에서 위로
    } else {
        
    }
  
});
*/
 


$.fn.barChart = function(){
    return this.each(function(){
        var $bar = $(this).find('.grp'),
            $view = $(this).find('.view'),
            $per = $(this).find('.per');
            $time = 1000;
        $bar.animate({
            'width' : $per.text()+'%'
        },$time);

        $view.animate({
        'left' : $per.text()+'%'
        },
        {
            duration:$time,
            step: function(now){
               $per.text(parseInt(now)+'%')
            }
        });
    });
}
 
$.fn.donutChart = function(){
    return this.each(function(){
        var $this = $(this),
            $donut = $this.find('.donut'),
            $donut2 = $this.find('.donut_2'),
            $per = $this.find('.per'),
            $per2 = $this.find('.per_2'),
            $point = $this.find('.point'),
            $point2 = $this.find('.point_2'),
            $start_point = parseInt($this.find('circle').css('stroke-dasharray')),
            rate = 100/($this.find('.shadow').text()),
            div  = ($start_point /100)*rate,                             
            per = parseInt($donut.text()),
            per2 = parseInt($donut2.text()),
            dashoffset = $start_point-div*per +"%", 
            dashoffset2 = $start_point-div*per2 +"%", 
            aniSec = 1000,
            point_unit = 2.89*rate; 
           
            $donut.animate({
                'stroke-dashoffset' : dashoffset
            }, aniSec,function(){}); 
            
            $per.animate({
                'per' : per
            }, {
                duration: aniSec,
                step: function(now){
                    // 50 117 100 234  
                   
                    $point.css('transform', 'rotate('+now*point_unit+'deg)'); 
                    $per.html(parseInt(now)+'<span>%</span>'); 
                    $per2.html(parseInt(now)+'<span>%</span>');
        
                } 
            });
           
            
            $donut2.animate({
                'stroke-dashoffset' : dashoffset
            }, aniSec,function(){}); 
        
            $per2.animate({
                'per' : per
            }, {
                duration: aniSec,
                step: function(now){
                    // 50 117 100 234   
                    $point2.css('transform', 'rotate('+now*point_unit+'deg)'); 
                    $per.html(parseInt(now)+'<span>%</span>'); 
                },
                complete:function(){  
                }  
            });    
           
            $donut2.delay(100).animate({
                'stroke-dashoffset' : dashoffset2
            }, aniSec);  
            
            $per2.delay(100).animate({
                'per' : per2
            }, {
                duration: aniSec,
                step: function(now2){
                    // 50 117 100 234                      
                    $point2.css('transform', 'rotate('+now2*point_unit+'deg)'); 
                    $per2.html(parseInt(now2)+'<span>%</span>');
        
                },
                complete:function(){
                    $donut2.animate({
                        'stroke-dashoffset' : dashoffset2
                    }, aniSec);   
                } 
                
            });                   
         
    });         	
}
$(function(){
	
});