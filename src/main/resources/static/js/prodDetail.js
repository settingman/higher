
var serverHostUrl = "https://www.thehyundai.com";
/* url에 front 추가 */
if (serverHostUrl.indexOf("front") < 0)
  serverHostUrl = serverHostUrl + "/front";
var stylistFlag = false;
var tempCurrentUrl = location.href;
/* https 프로토콜을 사용하는 페이지에 적용 - local 개발 환경은 제외 */
if (tempCurrentUrl.indexOf("local.thehyundai.com") < 0 && serverHostUrl.indexOf("https://") < 0)
  serverHostUrl = serverHostUrl.replace("http://", "https://");
$(document).ready(function ($) {
  //이탈방지솔루션, NHN모어(More)
  //mcroPageLoaded();
  //8.10 퍼블 요청 반영
  itemDetailView_urlCheck();
  /* 상품속성 셀렉트 박스 추가 */
  var select_root = $('div.sstpl_opt_selWrap > .selbox');
  var select_li = $('div.sstpl_opt_selWrap > .selbox > ul > li > ul > li');
  var select_li_f = $('div.sstpl_opt_selWrap > .selbox > ul > li > ul > li:first');
  var select_style_f = "<p><span class='tit'>상품 : </span> ";
  var select_style_b = "</p>";
  select_root.removeClass('current');
  // lst 2016-05-12
  $(document).bind('mousedown.selbox', function (e) {
    if (!$('div.sstpl_opt_selWrap .opt-select-layer').is(e.target) && $('div.sstpl_opt_selWrap .opt-select-layer').has(e.target).length === 0
      && $('div.sstpl_opt_selWrap .opt-select-layer').is(':visible')
      && !$('div.sstpl_opt_selWrap .opt-select-box a').is(e.target) && $('div.sstpl_opt_selWrap .opt-select-box a').has(e.target).length === 0
      && !$('div.sstpl_opt_selWrap .opt-select-box li').is(e.target) && $('div.sstpl_opt_selWrap .opt-select-box li').has(e.target).length === 0) {
      $('div.sstpl_opt_selWrap .selbox').removeClass('current');
    }
  });
  initOption();
  initHash();
  $('#itemDetailView img').each(function () {
    if ($(this).width() > 780)
      $(this).css("width", "100%");
  });
  /* 딜/옵션형상품 option select box list 추가 - S*/
  var tot_deal_1dp_op = ""; // 품절 포함된 select box length
  var cur_deal_1dp_op = $(".depth-opt-list:eq(0) > li").length; // 품절 빠진 select box length (현재)
  if ((tot_deal_1dp_op != null && typeof tot_deal_1dp_op != 'undefined' && tot_deal_1dp_op != '')
    && (cur_deal_1dp_op != null && typeof cur_deal_1dp_op != 'undefined')) { // null 체크
    //상품list 개수 안 맞을때만 실행(품절 빠진 list 사이즈랑 / 품절 포함된 list 사이즈랑 비교해서)
    if (tot_deal_1dp_op != cur_deal_1dp_op) { // 품절 상품 있는 경우
      addSelBoxSoldoutProdInfo(); // 딜/옵션형상품 개발 (2017.11.20)
    }
  }
  /* 딜/옵션형상품 option select box list 추가 - E*/
  // 2021.01.08 THD-4175 기획전폐쇄 링크 제거
  if ('00' == '01') {
    $('.top-con-logo > h1 > a').removeAttr('href');  // 2020.01.08 THD-4175 기획전폐쇄 링크 제거
    $('.prd-info-box > .prd-shop > .brand > a').removeAttr('href');  // 2020.01.08 THD-4175 기획전폐쇄 링크 제거
  }
});
// DEP01WORK-22764 2022.11.09 MA 솔루션 상품추천 로직 개발 -S
// 모든 resource load 후 그루비 스크립트 호출
window.onload = function () {
  if ('00' != '01') { // 기획전폐쇄 추천영역 제외처리
    rcmdItemSettingNew();
  }
};
function rcmdItemSettingNew() {
  var rcmdCampaignKeyL = 'RE92a44494fbce46fc87188a296033ca83';
  var rcmdCampaignKeyR = 'REae71b60fa7234b32992dc183335ff524';
  setGroobeeRcmdItem(rcmdCampaignKeyL, 'left', '11');
  setGroobeeRcmdItem(rcmdCampaignKeyR, 'right', '12');
}
function setGroobeeRcmdItem(campaignKey, locaiton, rcmdNum) {
  var jsonResult = groobee.getGroobeeRecommend(campaignKey, {});
  if (JSON.stringify(jsonResult) == '{}') {
    return;
  }
  var slitmCdArr = new Array();
  for (var i = 0; i < jsonResult.goodsList.length; i++) {
    slitmCdArr.push(jsonResult.goodsList[i].goodsCd);
  }
  if (slitmCdArr.length != 0) {
    fnRecommendProductsDataV3(slitmCdArr.join(','), function (resultV2, dataV2) {
      if (resultV2 == 'success') {
        rcmdAppendApi(dataV2, locaiton, rcmdNum, jsonResult.algorithmCd, jsonResult.campaignKey);
        generateDummyLog(null, 'PageName', 'ProductDetail', 'PageBanner', 'Review' + rcmdNum, null, null); // 추천 로직 정보 와이즈로그 전송
        sendGroobeeDI(dataV2, jsonResult.algorithmCd, jsonResult.campaignKey); // 그루비 상품 노출 정보 전송
      }
    });
  }
}
// API 추천 상품 리스트 생성
function rcmdAppendApi(data, location, rcmdNum, algorithmCd, campaignKey) {
  var searchCnt = 9;
  var lineCnt = 3;
  var lineEndCnt = 2;
  var jsonResultV2 = JSON.parse(JSON.stringify(data));
  var productCntV2 = JSON.stringify(jsonResultV2["data"]["count"]);
  if (productCntV2 == 0) {
    return;
  } else {
    if (productCntV2 > searchCnt) {
      productCntV2 = searchCnt;
    }
    var $html = '';
    for (var i = 0; i < productCntV2; i++) {
      var slitmCdI = JSON.stringify(jsonResultV2["data"]["products"][i]["p_no"]).replace(/\"/g, "");
      var slitmNmI = JSON.stringify(jsonResultV2["data"]["products"][i]["p_name"]).replace(/\"/g, "");
      var imgUrlI = JSON.stringify(jsonResultV2["data"]["products"][i]["thumb"]).replace(/\"/g, "");
      var slitmPrcI = JSON.stringify(jsonResultV2["data"]["products"][i]["price"]).replace(/\"/g, "");
      imgUrlI = imgUrlI.replace("http://", "https://");
      if (i == 0 || i % lineCnt == 0) {
        $html += '<li><ul class="product-box-set">';
      }
      $html += '<li>'
      $html += '  <div class="product-box">';
      $html += '      <img src="' + imgUrlI + '" alt="' + slitmNmI + '" onerror="noImage(this, \'https://image.thehyundai.com/hdmall/images/pd/no_image_300x300.jpg\')">';
      $html += '      <p class="price">' + addComma(slitmPrcI) + '원</p>';
      $html += '      <p><a href="/front/pda/itemPtc.thd?slitmCd=' + slitmCdI + '&PageName=ProductDetail&PageBanner=Reclick' + rcmdNum + '" target="_blank">' + slitmNmI + '</a></p>';
      $html += '      <a onclick="sendGroobeeCL(\'' + slitmCdI + '\',\'' + algorithmCd + '\',\'' + campaignKey + '\')" href="/front/pda/itemPtc.thd?slitmCd=' + slitmCdI + '&PageName=ProductDetail&PageBanner=Reclick' + rcmdNum + '" target="_blank">상품보기</a>';
      $html += '  </div>';
      $html += '</li>';
      if (i > 0 && i % lineCnt == lineEndCnt) {
        $html += '</ul></li>';
      }
    }
    if (location == 'left') {
      $productsObj = $('<div class="prd-slider-wrap fl-left">');
      $productsObj.append('<h4>다른 고객님들은 이 상품을 보고 있어요</h4>').append(
        $('<ul class="slider js-prd-slider cate-popular">').append($html));
    } else if (location == 'right') {
      $productsObj = $('<div class="prd-slider-wrap fl-right">');
      $productsObj.append('<h4>다른 고객님들이 많이 구매했어요</h4>').append(
        $('<ul class="slider js-prd-slider cate-popular">').append($html));
    }
    $('div.prd-related').append($productsObj);
    $('div.prd-related').find('.fl-' + location + ' .js-prd-slider.cate-popular').each(function () {
      $(this).bxSlider({
        controls: true,
        auto: false,
        autoHover: true,
        autoControls: true
      });
    });
  }
}
// 그루비 상품 노출 전송
function sendGroobeeDI(data, algorithmCd, campaignKey) {
  var groobeeObj = new Object();
  var groobeeItem = new Object();
  var goods = new Array();
  var jsonData = JSON.parse(JSON.stringify(data));
  var goodsCount = jsonData.data.count > 9 ? 9 : jsonData.data.count;
  for (var i = 0; i < goodsCount; i++) {
    groobeeItem.goodsCd = jsonData.data.products[i].p_no;
    goods.push(groobeeItem);
  }
  groobeeObj.algorithmCd = algorithmCd;
  groobeeObj.campaignKey = campaignKey;
  groobeeObj.campaignTypeCd = 'RE';
  groobeeObj.goods = goods;
  groobee.send("DI", groobeeObj);
}
// 그루비 상품 클릭 전송
function sendGroobeeCL(slitmCd, algorithmCd, campaignKey) {
  var groobeeObj = new Object();
  var groobeeItem = new Object();
  var goods = new Array();
  groobeeItem.goodsCd = slitmCd
  goods.push(groobeeItem);
  groobeeObj.algorithmCd = algorithmCd;
  groobeeObj.campaignKey = campaignKey;
  groobeeObj.campaignTypeCd = 'RE';
  groobeeObj.goods = goods;
  groobee.send("CL", groobeeObj);
}
function fnRecommendProductsDataV3(slitmCdArrA, callback) {
  var RecommendProductsUrl = serverHost + "/front/pda/getRcmdSlitmList.thd?mda012=0&slitmCdArr=" + slitmCdArrA;
  RecommendProductsUrl = RecommendProductsUrl.replace("http://", "https://");
  $.ajax({
    type: "get"
    , url: RecommendProductsUrl
    , dataType: "json"
    , async: false
    , success: function (data) {
      if (callback != undefined) {
        callback("success", data);
      }
    }, error: function (data) {
      if (callback != undefined) {
        callback("error", data);
      }
    }
  });
}
// 이미지 태그 생성
function getItemImgSrc(slitmCd, sFName, slitmNm) {
  if (slitmCd == null) {
    return '<img src="https://image.thehyundai.com/hdmall/images/pd/no_image_300x300.jpg" alt="' + slitmNm + '" onerror="noImage(this, \'https://image.thehyundai.com/hdmall/images/pd/no_image_300x300.jpg\')">';
  }
  var imgUrlI = "https://image.thehyundai.com/static/"
    + slitmCd.substring(8, 9) + "/"
    + slitmCd.substring(7, 8) + "/"
    + slitmCd.substring(6, 7) + "/"
    + slitmCd.substring(4, 6) + "/"
    + slitmCd.substring(2, 4) + "/"
    + sFName;
  return '<img src="' + imgUrlI + '" alt="' + slitmNm + '" onerror="noImage(this, \'https://image.thehyundai.com/hdmall/images/pd/no_image_300x300.jpg\')">';
}
/* 딜/옵션형상품 option select box list 추가 - S*/
function addSelBoxSoldoutProdInfo() {
  //상품list 개수 안 맞을때만 실행(품절 빠진 list 사이즈랑 / 품절 포함된 list 사이즈랑 비교해서)
  var total_1dp_opValue = "";
  var dealTmplGbcdValue = "";
  var slitmCdAry = [];    // 상품코드
  var slitmNmAry = [];    // 상품명
  var gnrlBnftPrcAry = []; // 혜택가
  var sellPrcAry = [];    // 판매가
  var sortOrdgAry = [];       // 상품별 정렬순서
  // 2018.10.18 이미지 파일명 추가
  var slitmImgNmAry = []; // 이미지 파일명
  var optSlitmCdAry = [];     // 기존 옵션형 select box list - 상품코드 (품절 미포함)
  var isSoldoutAry = [];      // 상품별 정렬순서
  // 상품코드 / 상품명 / 혜택가 / 판매가
  // 1depth 옵션 상품 확인 (품절 상품 존재할 경우, 품절 li 추가)
  for (i = 0; i < (total_1dp_opValue); i++) {
    var val = sortOrdgAry[i]; // val는 품절상품 기준 sortOrdg
    // 품절 상품 정보 변수
    var soldoutSlitmCd = ''; // 품절 상품 코드
    var soldoutEnlg1ImgNm = ''; // 품절 상품 이미지명
    var soldoutGnrlBnftPrc = ''; // 품절 혜택가
    var soldoutSellPrc = ''; // 품절 판매가
    var appendFlag = 0; // 품절상품 li 붙이는지 판별 ( 0: 안붙임 / 1, 2 : 붙임)
    var imgSrc = ''; // 최종 품절 상품 이미지 경로
    var slitmCdImgUrl = ''; // 상품코드별 이미지경로
    var innerTag;
    if ($(".depth-opt-list:eq(0) > li:eq(" + i + ")").attr("data") == undefined) {
      appendFlag = 1;
    } else if (val != $(".depth-opt-list:eq(0) > li:eq(" + i + ")").attr("data")) {
      appendFlag = 2;
    } else if (val == $(".depth-opt-list:eq(0) > li:eq(" + i + ")").attr("data") && isSoldoutAry[i] == 'true') { // 같은 sortOrd인데, 품절일 경우
      appendFlag = 2;
    }
    var enlg1ImgNmExt = '_0_170.jpg';   // 품절 상품 이미지 + 확장자
    var lImgNmExt = '_0_600.jpg';       // 품절 상품 이미지1 (zoom) + 확장자
    var enlg3ImgNmExt = '_0_1200.jpg';  // 품절 상품 이미지2 (zoom) + 확장자
    if (appendFlag > 0) { // 붙여야 하는 경우
      var j = i - 1; // append 시킬 li index
      soldoutSlitmCd = slitmCdAry[i]; // 품절 상품 코드
      soldoutGnrlBnftPrc = gnrlBnftPrcAry[i]; // 품절 상품 혜택가
      soldoutSellPrc = sellPrcAry[i]; // 품절 상품 판매가
      // 품절상품 이미지 경로 설정
      // 1. 상품코드별 이미지경로 설정
      slitmCdImgUrl = ''; // imgSrcPrefix (ex. image.thehyundai.com/~~~~~~/20XXXXXXXX)
      // 2018.10.18 - S
      soldoutEnlg1ImgNm = slitmImgNmAry[i];   // 2018.10.18 품절 상품 이미지명
      // 2018.10.18 이미지명 못 가져오는 CASE : 상품코드 기반으로 이미지명 생성
      if (soldoutEnlg1ImgNm == null || typeof soldoutEnlg1ImgNm == 'undefined' || soldoutEnlg1ImgNm == '') { // soldoutEnlg1ImgNm 정보 없으면 아래처럼 설정
        soldoutEnlg1ImgNm = soldoutSlitmCd + enlg1ImgNmExt;
      }
      var midImgNm = '';      // 2018.10.18 품절 상품 이미지 (확장자 제외)
      var strExt = '';        // 2018.10.18 품절 상품 이미지 확장자
      var zoom1ImgNm = '';    // 2018.10.18 품절 상품 이미지1 (zoom) + 확장자
      var zoom2ImgNm = '';    // 2018.10.18 품절 상품 이미지2 (zoom) + 확장자
      var zoom1Size = '600';  // 2018.10.18 품절 상품 이미지1 (zoom) size
      var zoom2Size = '1200'; // 2018.10.18 품절 상품 이미지1 (zoom) size
      if (soldoutEnlg1ImgNm != null && typeof soldoutEnlg1ImgNm != 'undefined' && soldoutEnlg1ImgNm != '') {
        // 이미지명 확장자와 분리
        midImgNm = (soldoutEnlg1ImgNm.split("."))[0];
        strExt = (soldoutEnlg1ImgNm.split("."))[1];
        // 이미지명(확장자 제외) -> 언더바 기준으로 split 후, zoom1 및 zoom2 img 경로 만들기
        var splitTmp = (midImgNm.split("_"))[0] + '_' + (midImgNm.split("_"))[1];
        zoom1ImgNm = splitTmp + '_' + zoom1Size + '.' + strExt;
        zoom2ImgNm = splitTmp + '_' + zoom2Size + '.' + strExt;
      }
      // 2018.10.18 - E
      // 2018.10.18 이미지 경로만 설정하도록 처리
      if (soldoutSlitmCd != null && typeof soldoutSlitmCd != 'undefined' && soldoutSlitmCd != '') {
        slitmCdImgUrl = "https://image.thehyundai.com/static/"
          + soldoutSlitmCd.substring(8, 9) + "/"
          + soldoutSlitmCd.substring(7, 8) + "/"
          + soldoutSlitmCd.substring(6, 7) + "/"
          + soldoutSlitmCd.substring(4, 6) + "/"
          + soldoutSlitmCd.substring(2, 4) + "/"
        // 2018.10.18 주석처리 //+ soldoutSlitmCd
      }
      // 2018.10.18 _170 size img 로 경로 설정
      imgSrc = slitmCdImgUrl + soldoutEnlg1ImgNm; // 2018.10.18 주석처리 slitmCdImgUrl + enlg1ImgNmExt; // _0_170.jpg // slitmCdImgUrl + soldoutEnlg1ImgNm;
      innerTag = '';
      innerTag += '<a href="#" class="cloudzoom-gallery type-soldout" data-cloudzoom="useZoom: \'#zoom1\', image: \'' + slitmCdImgUrl + zoom1ImgNm + '\', zoomImage: \'' + slitmCdImgUrl + zoom2ImgNm + '\', galleryEvent: \'mouseover\'">';
      // 2018.10.18 주석처리
      // innerTag += '<a href="#" class="cloudzoom-gallery type-soldout" data-cloudzoom="useZoom: \'#zoom1\', image: \'' + slitmCdImgUrl + lImgNmExt +'\', zoomImage: \'' + slitmCdImgUrl + enlg3ImgNmExt +'\', galleryEvent: \'mouseover\'">';
      innerTag += '<span class="opt-dill-img">';
      innerTag += '<img src="' + imgSrc + '" width="68" height="68" alt = "' + slitmNmAry[i] + '" onerror="this.src=\'https://image.thehyundai.com/hdmall/images/pd/no_image_170x170.jpg\'"/>';
      innerTag += '</span>';
      innerTag += '<span class="opt-dill-txt">';
      innerTag += '<span class="opt-seq">(선택' + val + ')</span>';
      innerTag += '<span class="opt-name">' + slitmNmAry[i] + '</span>';
      if (soldoutGnrlBnftPrc != null && typeof soldoutGnrlBnftPrc != 'undefined' && soldoutGnrlBnftPrc != '') { // 혜택가 있을 경우
        innerTag += '<span class="price">[품절] ' + addComma(soldoutGnrlBnftPrc) + '원</span>';
      } else {
        if (soldoutSellPrc != null && typeof soldoutSellPrc != 'undefined' && soldoutSellPrc != '') { // 혜택가없을경우 판매가노출
          innerTag += '<span class="price">[품절] ' + addComma(soldoutSellPrc) + '원</span>';
        } else { //판매가도 없으면 [품절]만 노출
          innerTag += '<span class="price">[품절]</span>';
        }
      }
      innerTag += '</span>';
      innerTag += '</a>';
      if (appendFlag == 1) {
        var curLen = $(".depth-opt-list:eq(0) > li").length;        // 옵션 다 품절일 경우 -> li없음 / length=0
        if (curLen == 0) {
          $(".depth-opt-list:eq(0)").append("<li data='" + val + "'>" + innerTag + "</li>");
        } else {
          $(".depth-opt-list:eq(0) > li:eq(" + j + ")").after("<li data='" + val + "'>" + innerTag + "</li>");
        }
      } else if (appendFlag == 2) {
        $(".depth-opt-list:eq(0) > li:eq(" + i + ")").before("<li data='" + val + "'>" + innerTag + "</li>");
      }
    }
  }
}
/* 딜/옵션형상품 option select box list 추가 - E*/
function option_reset(obj) {
  $(obj).children("li").each(function () {
    $(this).find(".depth-menu").removeClass("selected")
      .end().siblings(".depth-opt-list li").removeClass("current")
      .end().find(".selected-opt").text("");
    $(this).find(".depth-menu .selected-uitmseq").text("");
  });
  $(obj).parents(".opt-select-box").toggleClass("current");
  $('div.sstpl_opt_selWrap > .selbox:eq(0)').find('div.opt-select-value > a').text('').append("상품을 선택하세요 <i class='icon-arrow opt-toggle'></i>");
  if ($('div.sstpl_opt_selWrap > .selbox:eq(1)'))
    $('div.sstpl_opt_selWrap > .selbox:eq(1)').remove();
  uitmAttrTypeIndex = 0;
}
// @REQ-SD-007 @2018.11.07 @황희선 @2개 이상의 셀렉트 박스 삭제를 삭제 할 수 있도록 수정
function multiOption_reset(obj, objIndex) {
  // @REQ-SD-007 @2018.11.13 @황희선 @옵션명 셀렉트 박스에 입력
  var optNm = $('div.sstpl_opt_selWrap > .selbox:eq(' + objIndex + ')').find('div.opt-select-value').attr('uitmAttrTypeNm');
  $(obj).parents(".opt-select-box").toggleClass("current");
  if (optNm == undefined || !optNm) {
    $('div.sstpl_opt_selWrap > .selbox:eq(' + objIndex + ')').find('div.opt-select-value > a').text('').append("선택하세요 <i class='icon-arrow opt-toggle'></i>");
  } else {
    $('div.sstpl_opt_selWrap > .selbox:eq(' + objIndex + ')').find('div.opt-select-value > a').text('').append(optNm + "<i class='icon-arrow opt-toggle'></i>");
  }
  for (var i = $('div.sstpl_opt_selWrap > .selbox').length; i > objIndex; i--) {
    $('div.sstpl_opt_selWrap > .selbox:eq(' + i + ')').remove();
    if ($('div.sstpl_opt_selWrap > h4.opt-name:eq(' + i + ')'))
      $('div.sstpl_opt_selWrap > h4.opt-name:eq(' + i + ')').remove();
  }
  uitmAttrTypeIndex = objIndex;
}
var select_optQty = 0;
var uitmAttrTypeIndex = 0;
var zoomInstance = null;
jQuery(function ($) {
  $(".close-opinion").on("click", function (e) {
    e.preventDefault();
    $(".cont-wrap").toggleClass("expand");
    $(this).toggleClass("expand");
    return false;
  });
  var $tablock = $(".tab-block.detail");
  $tablock.wrap("<div class='tab-block-wrap'></div>").parents(".tab-block-wrap").css({ height: $tablock.outerHeight() });
  $('.prd-details-info .details-wrap').scrollspy({ target: '#goods_detail_spymenu', moffset: 100 });
  $('.product-detail .tab-pane').scrollspy({ target: '#goods_detail_spymenu2', moffset: 60 });
  $(window).on("scroll", function () {
    $range = $(".prd-details-info .scrollspywrap");
    $scrolltop = $(window).scrollTop();
    if ($range.offset().top - 80 < $scrolltop && ($range.offset().top + $range.outerHeight() - $(".prd-details-info .scrollspywrap .tab-block").outerHeight() - 80) > $scrolltop) {
      $(".prd-details-info .scrollspywrap .tab-block").css({ position: "fixed", top: "80px", bottom: "auto" });
    } else if ($range.offset().top + $range.outerHeight() - 80 - $(".prd-details-info .scrollspywrap .tab-block").outerHeight() < $scrolltop) {
      $(".prd-details-info .scrollspywrap .tab-block").css({ position: "absolute", top: "auto", bottom: "0" });
    } else {
      $(".prd-details-info .scrollspywrap .tab-block").css({ position: "absolute", top: "0", bottom: "auto" });
    }
    // tab-block detail
    var $tablock_wrap = $(".tab-block-wrap");
    if ($tablock_wrap.offset().top < $scrolltop) {
      $tablock.css({ position: "fixed", top: "0" });
    } else {
      $tablock.css({ position: "relative", top: "0" });
    }
  });
  /* S :: 줌 기능 활성 */
  var options_main_img = { mouseTriggerEvent: '' };
  var options_thum_img = { mouseTriggerEvent: 'mouseover' };
  jQuery('#zoom1').CloudZoom(options_main_img);
  jQuery('.cloudzoom-gallery').CloudZoom(options_thum_img);
  zoomInstance = $('#zoom1').data('CloudZoom');
  jQuery('#zoom_btn').click(function (e) {
    CloudZoom.quickStart();
    dummyLogCatch(null, 'ProductDetail', 'ImageExpend', null, 'N', null);
    jQuery('#zoom_btn').hide();
  });
  jQuery(".imgview").mouseleave(function () {
    zoomInstance.destroy();
    jQuery('#zoom_btn').show();
  });
  /* E :: 줌 기능 활성 */
  /* S :: 마지막에 마우스 오버된 썸네일의 원본 이미지를 zoom 객체에 넣어준다. */
  jQuery('.cloudzoom-gallery').mouseenter(function () {
    var sourceAttr = $(this).attr("data-cloudzoom");
    var targetAttr = $("#zoom1").attr("data-cloudzoom");
    if (sourceAttr != undefined && sourceAttr != '' && targetAttr != undefined && targetAttr != '') {
      var jsonSourceAttr = eval("({ " + sourceAttr + " })");
      var jsonTargetAttr = eval("({ " + targetAttr + " })");
      jsonTargetAttr.zoomImage = jsonSourceAttr.zoomImage;
      var zoomAttr = JSON.stringify(jsonTargetAttr);
      if (zoomAttr != undefined && zoomAttr != '') {
        zoomAttr = zoomAttr.replace("{", "");
        zoomAttr = zoomAttr.replace("}", "");
        $("#zoom1").attr("data-cloudzoom", zoomAttr);
      }
    }
  });
  /* E :: 줌 기능 활성 */
  $(document).on("click", "a.depth-menu", function (e) {
    e.preventDefault();
    $index = $(this).parent("li").index();
    // 선행 옵션을 선택하지 않으면 선택할수 없다.
    if ($(this).parent().prev().length > 0 && !$(this).parent().prev().find(".depth-menu").hasClass("selected") || $(this).find(".selected-uitmseq").text() == "") {
      alert("위의 정보를 먼저 선택해주세요!");
      return false;
    }
    $(this).parents(".opt-select-layer").children("li").each(function (idx, elm) {
      if (idx >= $index) {
        $(this).find("a").removeClass("selected");
        $(this).find(".depth-opt-list li").removeClass("current")
          .end().find(".selected-opt").text("");
        $(this).find(".selected-uitmseq").text("");
      }
    });
    // 아니면 다른 단계의 선택을 모두 제거하고 해당 단계부터 옵션을 다시 선택한다.
    $(this).parent().siblings("li").removeClass("current").end().addClass("current");
    return false;
  });
  /* 옵션선택박스 데이터 처리 */
  $(document).on("click", ".opt-select-value a", function (e) {
    e.preventDefault();
    if ($("input[name=trndHSsaleTagYn]").val() == "Y" || $("input[name=trndHExclTagExpsYn]").val() == "Y") {  /* 옵션상품이거나 딜상품인경우 */
      $(this).parents(".opt-select-box").toggleClass("current");  // 옵션 선택창 활성화
      // 옵션 선택중에 상품을 선택하면 옵션 영역 삭제
      // @REQ-SD-007 @2018.11.08 @황희선 @멀티 셀렉트 삭제 function으로 수정
      /* if($(this).parent().parent().index() == 0 && $(".sstpl_opt_selWrap > .opt-select-box.selbox").length > 1)
          option_reset($(this).parent().parent().find(".opt-select-layer")); */
      var $targetIndex = $(this).parents(".prd-opt-row.sstpl_opt_selWrap").find(".opt-select-box.selbox").index($(this).parent().parent());
      if ($(".sstpl_opt_selWrap > .opt-select-box.selbox").length > 1 && ($(".sstpl_opt_selWrap > .opt-select-box.selbox").length - 1) > $targetIndex)
        multiOption_reset($(this).parent().parent().find(".opt-select-layer"), $targetIndex);
      // 1 depth 활성화
      if (uitmAttrTypeIndex == 0)
        $(this).parents(".opt-select-box").find(".opt-select-layer li:first-child").addClass("current");
      // 현재 선택된 depth가 없으면 1 depth를 활성화한다.
      var bCheck = false;
      $(this).parents(".opt-select-box").find(".opt-select-layer > li").each(function () {
        if ($(this).attr("class") == "current")
          bCheck = true;
      });
      if (!bCheck)
        $(this).parents(".opt-select-box").find(".opt-select-layer li:first-child").addClass("current");
      uitmAttrTypeIndex = $(this).parent().parent().find("ul > li > ul").index("." + $(this).parent().parent().find("ul > li > ul").attr("class")); // 선택한 li의 셀렉트박스 index
      var sel_index = $(this).parent().parent().find("ul > li > ul > li").attr("data");   // 선택한 li가 해당 셀렉트박스에서 몇번째 li 인지 (선택한 li의 data 값)
      var selector = "ul > li > ul > li > a > ";
      var select_value = $(this).parent().parent().find(selector + '.opt-name').text();
      if ($(this).parent().find(selector + '.num').text() != '') {
        select_value = $(this).parent().find(selector + '.num').text() + select_value;
      }
      if (uitmAttrTypeIndex == 0 && sel_index == "") { return } // 첫번째 셀렉트, 첫번째 li 선택했을때 리턴
      if (uitmAttrTypeIndex == 0) {
        $("input[name=sellPrc]").val($(this).parent().parent().find("input[name=good_prc]").val());
        $("input[name=selectedOptSlitmCd]").val($(this).parent().parent().find("input[name=optSlitmCd]").val());
        $("input[name=selectedOptQty]").val($(this).parent().parent().find("input[name=optQty]").val());
        $("input[name=selectedUitmCombYn]").val($(this).parent().parent().find("input[name=uitmCombYn]").val());
        $("input[name=selectedUitmChocPossYn]").val($(this).parent().parent().find("input[name=uitmChocPossYn]").val());
        $("input[name=selectedDluMaxBuyQty]").val($(this).parent().parent().find("input[name=dluMaxBuyQty]").val());
        $("input[name=selectedordMakeYn]").val($(this).parent().parent().find("input[name=ordMakeYn]").val());
        $("input[name=selectedpackOpenRtpNdmtYn]").val($(this).parent().parent().find("input[name=packOpenRtpNdmtYn]").val());
        $("input[name=selectedfrgnDrctBuyYn]").val($(this).parent().parent().find("input[name=frgnDrctBuyYn]").val());
        $("input[name=selectedStckGdYn]").val($(this).parent().parent().find("input[name=stckGdYn]").val());
        if ($(this).parent().parent().find("input[name=mblOnlyItemYn]").val() == 'Y') {
          alert("모바일 전용 상품입니다.");
          return;
        }
      }
      var selectedOptSlitmCd = $("input[name=selectedOptSlitmCd]").val();
      select_optQty = $("input[name=selectedOptQty]").val(); // 선택한 li의 하위 옵션 갯수
    } else if ($("input[name=uitmCombYn]").val() == "Y") { // @REQ-SD-007 @2018.11.05 @황희선 @조합형인 경우의 옵션선택 박스 처리
      $(this).parents(".opt-select-box").toggleClass("current");  // 옵션 선택창 활성화
      // 옵션 선택중에 상품을 선택하면 옵션 영역 삭제
      var $targetIndex = $(this).parents(".prd-opt-row.sstpl_opt_selWrap").find(".opt-select-box.selbox").index($(this).parent().parent());
      if ($(".sstpl_opt_selWrap > .opt-select-box.selbox").length > 1 && ($(".sstpl_opt_selWrap > .opt-select-box.selbox").length - 1) > $targetIndex)
        multiOption_reset($(this).parent().parent().find(".opt-select-layer"), $targetIndex);
      // 1 depth 활성화
      if (uitmAttrTypeIndex == 0)
        $(this).parents(".opt-select-box").find(".opt-select-layer li:first-child").addClass("current");
      // 현재 선택된 depth가 없으면 1 depth를 활성화한다.
      var bCheck = false;
      $(this).parents(".opt-select-box").find(".opt-select-layer > li").each(function () {
        if ($(this).attr("class") == "current")
          bCheck = true;
      });
      if (!bCheck)
        $(this).parents(".opt-select-box").find(".opt-select-layer li:first-child").addClass("current");
      uitmAttrTypeIndex = $(this).parent().parent().find("ul > li > ul").index("." + $(this).parent().parent().find("ul > li > ul").attr("class")); // 선택한 li의 셀렉트박스 index
      var sel_index = $(this).parent().parent().find("ul > li > ul > li").attr("data");   // 선택한 li가 해당 셀렉트박스에서 몇번째 li 인지 (선택한 li의 data 값)
      var selector = "ul > li > ul > li > a > ";
      var select_value = $(this).parent().parent().find(selector + '.opt-name').text();
      if ($(this).parent().find(selector + '.num').text() != '') {
        select_value = $(this).parent().find(selector + '.num').text() + select_value;
      }
      if (uitmAttrTypeIndex == 0 && sel_index == "") { return } // 첫번째 셀렉트, 첫번째 li 선택했을때 리턴
    } else { /* 일반상품, 무형상품인 경우 */
      // 옵션 선택창 활성화
      $(this).parents(".opt-select-box").toggleClass("current");
      // 옵션 선택중에 상품을 선택하면 옵션 영역 삭제
      //if($(this).parent().parent().index() == 0)
      //  option_reset($(this).parent().parent().find(".opt-select-layer"));
      // 현재 선택된 depth가 없으면 1 depth를 활성화한다.
      var bCheck = false;
      $(this).parents(".opt-select-box").find(".opt-select-layer > li").each(function () {
        if ($(this).attr("class") == "current")
          bCheck = true;
      });
      if (!bCheck)
        $(this).parents(".opt-select-box").find(".opt-select-layer li:first-child").addClass("current");
    }
    // 높이 계산해서 모자랄 경우 상단으로 변경
    if ($(this).parents(".option-floating").length > 0 && $(this).parents(".option-floating").hasClass("fixed")) {
      // lst 2016-05-12 if($(this).parents(".option-floating-scroll").height() < $(this).parents(".opt-select-box").find(".opt-select-layer").height() + $(this).parents(".opt-select-box").find(".opt-select-layer").offset().top)
      if ($(this).closest(".mCSB_container").height() < $(this).parents(".opt-select-box").find(".opt-select-layer").height() + 81) // lst 2016-06-21
      {
        $(this).parents(".opt-select-box").find(".opt-select-layer").addClass("top");
      }
      else {
        $(this).parents(".opt-select-box").find(".opt-select-layer").removeClass("top");
      }
    }
    // lst 2016-05-12
    else {
      $(this).parents(".opt-select-box").find(".opt-select-layer").removeClass("top");
    }
    return false;
  });
  $(document).on("click", ".depth-opt-list > li > a", function (e) {
    e.preventDefault();
    var $topOptSelectBox = $(this).parents(".opt-select-box"); // lst 2016-05-12
    if ($("input[name=trndHSsaleTagYn]").val() == "Y" || $("input[name=trndHExclTagExpsYn]").val() == "Y") {  // 옵션상품이거나 딜상품인경우
      var obj = $(this);
      var slitmSeq = $(this).find(".opt-seq").text(); // 선택된 옵션의 순번
      var slitmNm = $(this).find(".opt-name").text(); // 선택된 옵션의 이름
      var slitmPrc = $(this).find(".price").text().replace("원", ""); // 선택된 옵션의 가격
      var uitmCd = $(this).parent().attr("uitmCd");
      var uitmSeq = $(this).parent().attr("data");
      var sellPossQty = $(this).parent().attr("sellPossQty");
      var stckYn = $(this).parent().attr("stckYn");
      var slitmCd = "";
      if (uitmAttrTypeIndex == 0) {
        slitmCd = $(this).parent().find("input[name=optSlitmCd]").val();
        $("input[name=sellPrc]").val($(this).parent().find("input[name=good_prc]").val());
        $("input[name=selectedOptSlitmCd]").val($(this).parent().find("input[name=optSlitmCd]").val());
        $("input[name=selectedOptQty]").val($(this).parent().find("input[name=optQty]").val());
        $("input[name=selectedUitmCombYn]").val($(this).parent().find("input[name=uitmCombYn]").val());
        $("input[name=selectedUitmChocPossYn]").val($(this).parent().find("input[name=uitmChocPossYn]").val());
        $("input[name=selectedDluMaxBuyQty]").val($(this).parent().find("input[name=dluMaxBuyQty]").val());
        $("input[name=selectedordMakeYn]").val($(this).parent().find("input[name=ordMakeYn]").val());
        $("input[name=selectedpackOpenRtpNdmtYn]").val($(this).parent().find("input[name=packOpenRtpNdmtYn]").val());
        $("input[name=selectedfrgnDrctBuyYn]").val($(this).parent().find("input[name=frgnDrctBuyYn]").val());
        $("input[name=selectedStckGdYn]").val($(this).parent().find("input[name=stckGdYn]").val());
        $("input[name=selectedVenCd]").val($(this).parent().find("input[name=venCd]").val());
        $("input[name=selectedDptsNm]").val($(this).parent().find("input[name=dptsNm]").val());
        $("input[name=selectedBrndCd]").val($(this).parent().find("input[name=brndCd]").val());
        $("input[name=selectedBrndNm]").val($(this).parent().find("input[name=brndNm]").val());
        $("input[name=selectedRsptMdCd]").val($(this).parent().find("input[name=rsptMdCd]").val());
        $("input[name=selectedRsptMdNm]").val($(this).parent().find("input[name=rsptMdNm]").val());
      } else {
        slitmCd = $(this).attr("optSlitmCd");
      }
      select_optQty = $("input[name=selectedOptQty]").val(); // 선택한 li의 하위 옵션 갯수
      var uitmCombYn = $("input[name=selectedUitmCombYn]").val(); // 선택된 상품의 조합속성 여부
      var uitmChocPossYn = $("input[name=selectedUitmChocPossYn]").val(); // 속성없음여부(N이면 없음)
      var uitmAttrTypeSeq = Number(uitmAttrTypeIndex) + 1; // 다음 옵션 셀렉트박스에 넣어줄 val 값 & 순서가된다.
      var stckGdYn = $("input[name=selectedStckGdYn]").val(); // 재고노출여부
      if ($("#bsicUitmListItem").length > 0) {
        slitmCd = $(this).parent().find("input[name=selectptSlitmCd]").val();
        $("#bsicUitmListItem > input[name=slitmCd]").val(slitmCd);
      }
      if (uitmAttrTypeIndex == 0) {
        // 선택된 값을 셀렉트 박스에 노출
        $('div.sstpl_opt_selWrap > .selbox:eq(' + uitmAttrTypeIndex + ')').find('div > a').text('').append(slitmSeq + " " + slitmNm + " " + slitmPrc + " <i class='icon-arrow opt-toggle'></i>");
      }
      // 일반 속성
      var uitmNmArr = new Array();
      if (uitmCombYn != 'Y') {
        //1. 상품선택시 데이터 조회 후 셀렉트 박스 생성
        var tempHTML = "";
        if (uitmAttrTypeIndex == 0) {
          $.ajax({
            type: "post"
            , url: "/front/pda/selectBsicUitmMst.thd?"
            , dataType: "json"
            , data: { slitmCd: slitmCd }
            , success: function (data) {
              if (data.uitmList) {
                if (uitmChocPossYn == 'N' || (data.uitmList.length == 1 && (data.uitmList[0].uitmTotNm == null || data.uitmList[0].uitmTotNm == '없음' || data.uitmList[0].uitmTotNm == ''))) {
                  slitmCd = obj.parent().find("input[name=selectptSlitmCd]").val();
                  //20200227 속성표기 개선
                  //uitmNmArr.push(slitmSeq + " " + slitmNm + " / " + data.uitmList[0].uitmTotNm);
                  uitmNmArr.push(slitmSeq + " " + slitmNm);
                  // 동일 속성 선택 존재여부 체크 후 존재하는 경우 리턴
                  var flag = true;
                  $(".opt-sel-box li").each(function () {
                    if (data.uitmList[0].uitmCd == $(this).find('p.name > input[name="uitmCd"]').val() && slitmCd == $(this).find('p.name > input[name="slitmCd"]').val()) {
                      alert("동일상품이 선택되었습니다.\n아래에서 선택사항을 확인해주세요.");
                      flag = false;
                    }
                  });
                  if (!flag) {
                    return;
                  }
                  var goodPrc = obj.parent().find("input[name=good_prc]").val();
                  var dluMaxBuyQty = obj.parent().find("input[name=dluMaxBuyQty]").val(); // 최대구매수량
                  var ordMakeYn = obj.parent().find("input[name=ordMakeYn]").val(); // 주문제작여부
                  var packOpenRtpNdmtYn = obj.parent().find("input[name=packOpenRtpNdmtYn]").val(); // 박스포장 안내팝업
                  var frgnDrctBuyYn = obj.parent().find("input[name=frgnDrctBuyYn]").val(); // 해외직구 안내팝업
                  var venCd = obj.parent().find("input[name=venCd]").val(); // 지점코드
                  var dptsNm = obj.parent().find("input[name=dptsNm]").val(); // 지점명
                  var brndCd = obj.parent().find("input[name=brndCd]").val(); // 브랜드코드
                  var brndNm = obj.parent().find("input[name=brndNm]").val(); // 브랜드명
                  var rsptMdCd = obj.parent().find("input[name=rsptMdCd]").val(); // 담당MD코드
                  var rsptMdNm = obj.parent().find("input[name=rsptMdNm]").val(); // 담당MD명
                  addUitmBsicHtmlDeal(data.uitmList[0].uitmCd, uitmNmArr, dluMaxBuyQty, goodPrc, slitmCd, ordMakeYn, packOpenRtpNdmtYn, frgnDrctBuyYn, "99", venCd, dptsNm, brndCd, brndNm, rsptMdCd, rsptMdNm);
                  calcSellPrc();
                } else if (data.uitmList.length > 0 && uitmChocPossYn == 'Y') {
                  // 해당 li의 하위 옵션이 있을경우 셀렉트 박스 그려줌(선택값이 있을경우만..)
                  var objHtml = '';
                  objHtml += '<div class="opt-select-box selbox">';
                  objHtml += '<div class="opt-select-value"><a href="#" class="ellips">옵션을 선택하세요.</a></div>';
                  objHtml += '<ul class="opt-select-layer">';
                  objHtml += '</ul>';
                  objHtml += '</div>';
                  $('.sstpl_opt_selWrap').append(objHtml);
                  var stockCnt = '';
                  for (var i in data.uitmList) {
                    if (stckGdYn == 'Y')
                      stockCnt = " / 잔여수량 " + data.uitmList[i].stockCount + "개";
                    var soldOutLink = "";
                    var soldOutHTML = "";
                    var soldOutStyle = "";
                    if (data.uitmList[i].stockCount < 1 && select_optQty == 1) {
                      soldOutLink = "onclick=\"javascript:showItemRishpAlrimPup(this, '" + slitmCd + "', '" + data.uitmList[i].uitmCd + "', '" + slitmNm + "', '" + data.uitmList[i].uitmTotNm + "', '" + data.uitmList[i].rishpAlrimYn + "'); return false;\"";
                      soldOutHTML = "<span class='soldout'>(품절)</span>";
                      soldOutStyle = "class='soldout'";
                    }
                    if (i == 0)
                      tempHTML = "<li><span class='selected-opt' style='display:none;'></span><span class='selected-uitmseq'></span><ul class='depth-opt-list'>";
                    tempHTML += "<li data ='" + data.uitmList[i].uitmSeq + "' uitmCd='" + data.uitmList[i].uitmCd + "' " + soldOutStyle + " sellPossQty='" + data.uitmList[i].stockCount + "' rishpAlrimYn='" + data.uitmList[i].rishpAlrimYn + "'><a href='#' " + soldOutLink + " optSlitmCd='" + slitmCd + "'><span class='opt-name'>" + data.uitmList[i].uitmTotNm + stockCnt + "</span>" + soldOutHTML + "</a></li>";
                    if (i == (data.uitmList.length - 1)) {
                      tempHTML += "</ul></li>";
                      $(".opt-select-layer").eq(uitmAttrTypeIndex + 1).append(tempHTML);
                    }
                    stockCnt = '';
                  }
                  //20200227 속성표기 개선
                  $('.sstpl_opt_selWrap .opt-select-box.selbox').eq(0).removeClass("current");
                  $('.sstpl_opt_selWrap .opt-select-box.selbox').eq(0).find('.opt-select-layer li:first-child').removeClass("current");
                  $('.sstpl_opt_selWrap .opt-select-box.selbox').eq(1).addClass("current");
                  $('.sstpl_opt_selWrap .opt-select-box.selbox').eq(1).find('.opt-select-layer li:first-child').addClass("current");
                  uitmAttrTypeIndex++;
                  $topOptSelectBox.toggleClass("current");
                  //20200227 속성표기 개선
                }
                $topOptSelectBox.toggleClass("current"); // lst 2016-05-12 옵션 선택창 활성화
              }
            }
            , error: function (data) {
            }
          });
        } else {
          $lastidx = $(this).parents(".opt-select-layer").children("li").length;
          // 해당 옵션 부모에 선택된 값 넣기.
          $(this).parents(".depth-opt-list").siblings(".selected-opt").text(slitmNm)
            .end().addClass("selected").parent("li").removeClass("current")
            .next().addClass("current");
          if (uitmCd != "" && $(this).parents(".depth-opt-list").parent("li").index() == $lastidx - 1) {
            // 동일 속성 선택 존재여부 체크 후 존재하는 경우 리턴
            var flag = true;
            $(".opt-sel-box li").each(function () {
              if (uitmCd == $(this).find('p.name > input[name="uitmCd"]').val() && slitmCd == $(this).find('p.name > input[name="slitmCd"]').val()) {
                alert("동일상품이 선택되었습니다.\n아래에서 선택사항을 확인해주세요.");
                flag = false;
              }
            });
            // 선택되었던 옵션 depth 활성화 class, sequence 삭제
            if (!flag) {
              $(this).parent().parent().parent().parent(".opt-select-layer").children("li").each(function () {
                $(this).find("a").removeClass("selected");
                $(this).find("li").removeClass("current");
                $(this).find(".selected-uitmseq").text("");
              });
              $topOptSelectBox.toggleClass("current"); // lst 2016-05-12 옵션 선택창 활성화
              return;
            }
            // 선택된 상품 가져오기
            uitmNmArr.push($('div.sstpl_opt_selWrap > .selbox:eq(0)').find('div.opt-select-value > a').text());
            // 선택된 옵션 가져오기
            $(".selected-opt").each(function () {
              uitmNmArr.push($(this).text());
            });
            addUitmBsicHtmlDeal(uitmCd, uitmNmArr, $("input[name=selectedDluMaxBuyQty]").val(), $("input[name=sellPrc]").val(), slitmCd, $("input[name=selectedordMakeYn]").val(), $("input[name=selectedpackOpenRtpNdmtYn]").val(), $("input[name=selectedfrgnDrctBuyYn]").val(), sellPossQty,
              $("input[name=selectedVenCd]").val(), $("input[name=selectedDptsNm]").val(), $("input[name=selectedBrndCd]").val(), $("input[name=selectedBrndNm]").val(), $("input[name=selectedRsptMdCd]").val(), $("input[name=selectedRsptMdNm]").val()); //밑에 그레이 박스에 추가 함수
            calcSellPrc();
            // 옵션 모두 선택됨. 초기화
            option_reset($(this).parents(".opt-select-layer"));
          }
          return false;
        }
      }
      else {
        // @REQ-SD-007 @2018.11.06 @황희선 @옵션선택을 depth별 ajax처리로 변경
        // 선택된 옵션의 이름
        var $target = $(this);
        var uitmSeqParam = "";
        var uitmSeqArrParam = new Array();
        // 조합형 속성
        if (uitmAttrTypeIndex == 0) {
        } else {
          // 해당 옵션 부모에 선택된 값 넣기.
          uitmSeqParam = uitmSeq;
          slitmNm += "<input type='hidden' name='uitmSeq' value='" + uitmSeq + "'>";
          $target.parents(".opt-select-box").find(".opt-select-value a").html(slitmNm);
          // 현재 선택 되어 있는 모든 uitmSeq
          $target.parents(".sstpl_opt_selWrap").find("input[name=uitmSeq]").each(function () {
            uitmSeqArrParam.push($(this).val());
          });
        }
        selectUitmAttrTypeListForChange($target, slitmCd, uitmSeqParam, uitmAttrTypeSeq, uitmSeqArrParam, function (data) {
          var flag = true;
          var uitmCd;
          //품절된 상품이면 알러트 띠우게 하기
          if (data == undefined || data == null || data.uitm == null || data.uitm.sellPossQty == 0 || data.uitm.stckYn == "N") {
            alert("품절된 상품입니다.");
            flag = false;
          } else {
            uitmCd = data.uitm.uitmCd
            // 동일 속성 선택 존재여부 체크 후 존재하는 경우 리턴
            $(".opt-sel-box li").each(function () {
              if ($(this).find('p.name > input[name="slitmCd"]').val() != '') {
                if ($(this).find('p.name > input[name="slitmCd"]').val() == slitmCd && uitmCd == $(this).find('p.name > input[name="uitmCd"]').val()) {
                  alert("동일상품이 선택되었습니다.\n아래에서 선택사항을 확인해주세요.");
                  flag = false;
                }
              } else {
                if (uitmCd == $(this).find('p.name > input[name="uitmCd"]').val()) {
                  alert("동일상품이 선택되었습니다.\n아래에서 선택사항을 확인해주세요.");
                  flag = false;
                }
              }
            });
          }
          // 선택되었던 옵션 depth 활성화 class, sequence 삭제
          if (!flag) {
            // @REQ-SD-007 @2018.11.15 @황희선 @옵션명 셀렉트 박스에 입력
            var optNm = $target.parents(".opt-select-box").find(".opt-select-value").attr('uitmAttrTypeNm');
            if (optNm == undefined || !optNm) {
              $target.parents(".opt-select-box").find(".opt-select-value a").html("선택하세요.");
            } else {
              $target.parents(".opt-select-box").find(".opt-select-value a").html(optNm + "<i class='icon-arrow opt-toggle'></i>");
            }
            $target.parents(".opt-select-box").toggleClass("current"); // lst 2016-05-12 옵션 선택창 활성화
            return;
          }
          // 선택된 상품 가져오기
          uitmNmArr.push($('div.sstpl_opt_selWrap > .selbox:eq(0)').find('div.opt-select-value > a').text());
          uitmNmArr.push(data.uitm.uitmTotNm);
          //addUitmCombHtml(uitmCd, uitmSeqArrParam, data.uitm.uitmTotNm, data.uitm.sellPossQty); // 선택한 상품 및 옵션 정보를 추가하는 html 생성.
          addUitmHtmlDeal(uitmCd, uitmNmArr, $("input[name=selectedDluMaxBuyQty]").val(), $("input[name=sellPrc]").val(), slitmCd, $("input[name=selectedordMakeYn]").val(), $("input[name=selectedpackOpenRtpNdmtYn]").val(), $("input[name=selectedfrgnDrctBuyYn]").val(), data.uitm.sellPossQty,
            $("input[name=selectedVenCd]").val(), $("input[name=selectedDptsNm]").val(), $("input[name=selectedBrndCd]").val(), $("input[name=selectedBrndNm]").val(), $("input[name=selectedRsptMdCd]").val(), $("input[name=selectedRsptMdNm]").val()); //밑에 그레이 박스에 추가 함수);
          calcSellPrc(); // 선택 상품 총 합계 함수
          // 옵션이 모두 선택 되면 초기화
          multiOption_reset($target.parents(".opt-select-layer"), 0);
        });
        return false;
      }
    } else { /* 일반상품, 무형상품인 경우 */
      var slitmNm = $(this).find(".opt-name").text() // 선택된 옵션의 이름
      var uitmCd = $(this).parent().attr("uitmCd");
      var uitmSeq = $(this).parent().attr("data");
      var sellPossQty = $(this).parent().attr("sellPossQty");
      var slitmCd = $("input[name=selectedOptSlitmCd]").val();
      var uitmCombYn = $("input[name=uitmCombYn]").val(); // 선택된 상품의 조합속성 여부
      var stckYn = $(this).parent().attr("stckYn");
      // 일반 속성
      var uitmNmArr = new Array();
      var uitmSeqArr = new Array();
      if (uitmCombYn != 'Y') {
        //1. 상품선택시 데이터 조회 후 셀렉트 박스 생성
        var tempHTML = "";
        $lastidx = $(this).parents(".opt-select-layer").children("li").length;
        // 해당 옵션 부모에 선택된 값 넣기.
        $(this).parents(".depth-opt-list").siblings(".selected-opt").text(slitmNm)
          .end().addClass("selected").parent("li").removeClass("current")
          .next().addClass("current");
        //$(this).parents(".depth-opt-list").siblings(".selected-uitmseq").text(uitmSeq);
        if (uitmCd != "" && $(this).parents(".depth-opt-list").parent("li").index() == $lastidx - 1) {
          // 동일 속성 선택 존재여부 체크 후 존재하는 경우 리턴
          var flag = true;
          $(".opt-sel-box:eq(0) > li").each(function () {
            if (uitmCd == $(this).find('p.name > input[name="uitmCd"]').val()) {
              alert("동일상품이 선택되었습니다.\n아래에서 선택사항을 확인해주세요.");
              flag = false;
            }
          });
          // 선택되었던 옵션 depth 활성화 class, sequence 삭제
          if (!flag) {
            $(this).parent().parent().parent().parent(".opt-select-layer").children("li").each(function () {
              $(this).find("a").removeClass("selected");
              $(this).find("li").removeClass("current");
              $(this).find(".selected-uitmseq").text("");
            });
            $topOptSelectBox.toggleClass("current"); // lst 2016-05-12  옵션 선택창 활성화
            return;
          }
          // 선택된 상품 가져오기
          uitmNmArr.push($('div.sstpl_opt_selWrap > .selbox:eq(0)').find('div.opt-select-value > a').text());
          // 선택된 옵션 가져오기
          $(".selected-opt").each(function () {
            uitmNmArr.push($(this).text());
          });
          addUitmBsicHtml(uitmCd, uitmNmArr, sellPossQty);
          calcSellPrc();
          // 옵션 모두 선택됨. 초기화
          option_reset($(this).parents(".opt-select-layer"));
        }
        return false;
      }
      else    // 조합형 속성
      {
        // @REQ-SD-007 @2018.11.06 @황희선 @옵션선택을 depth별 ajax처리로 변경
        // 선택된 옵션의 이름
        var $target = $(this);
        var slitmCd = $("#itemInfForm input[name='slitmCd']").val();
        var bsitmCd = $("#itemInfForm input[name='bsitmCd']").val();
        var slitmNm = $target.find(".opt-name").text();
        var uitmAttrTypeSeq = Number($target.parent().attr("uitmAttrTypeSeq")) + 1; // 다음 옵션 셀렉트박스에 넣어줄 val 값 & 순서가된다.
        // 해당 옵션 부모에 선택된 값 넣기.
        slitmNm += "<input type='hidden' name='uitmSeq' value='" + uitmSeq + "'>";
        $target.parents(".opt-select-box").find(".opt-select-value a").html(slitmNm);
        // 현재 선택 되어 있는 모든 uitmSeq
        var uitmSeqArrParam = new Array();
        $target.parents(".sstpl_opt_selWrap").find("input[name=uitmSeq]").each(function () {
          uitmSeqArrParam.push($(this).val());
        });
        /*
        * 선택한 속성에 따른 다음 속성 값 조회 후 select 셋팅
        */
        selectUitmAttrTypeListForChange($target, slitmCd, uitmSeq, uitmAttrTypeSeq, uitmSeqArrParam, function (data) {
          var flag = true;
          var uitmCd;
          //품절된 상품이면 알러트 띠우게 하기
          if (data == undefined || data == null || data.uitm == null || data.uitm.sellPossQty == 0 || data.uitm.stckYn == "N") {
            alert("품절된 상품입니다.");
            flag = false;
          } else {
            uitmCd = data.uitm.uitmCd
            // 동일 속성 선택 존재여부 체크 후 존재하는 경우 리턴
            $(".opt-sel-box li").each(function () {
              if (uitmCd == $(this).find('p.name > input[name="uitmCd"]').val()) {
                alert("동일상품이 선택되었습니다.\n아래에서 선택사항을 확인해주세요.");
                flag = false;
              }
            });
          }
          // 선택되었던 옵션 depth 활성화 class, sequence 삭제
          if (!flag) {
            // @REQ-SD-007 @2018.11.15 @황희선 @옵션명 셀렉트 박스에 입력
            var optNm = $target.parents(".opt-select-box").find(".opt-select-value").attr('uitmAttrTypeNm');
            if (optNm == undefined || !optNm) {
              $target.parents(".opt-select-box").find(".opt-select-value a").html("선택하세요.");
            } else {
              $target.parents(".opt-select-box").find(".opt-select-value a").html(optNm + "<i class='icon-arrow opt-toggle'></i>");
            }
            $target.parents(".opt-select-box").toggleClass("current"); // lst 2016-05-12 옵션 선택창 활성화
            return;
          }
          addUitmHtml(uitmSeqArrParam, data.uitm.uitmTotNm, data.uitm.sellPossQty, uitmCd); // 선택한 상품 및 옵션 정보를 추가하는 html 생성.
          calcSellPrc(); // 선택 상품 총 합계 함수
          // 옵션이 모두 선택 되면 초기화
          multiOption_reset($target.parents(".opt-select-layer"), 0);
        });
        return false;
      }
    }
  });
  $("select.selectUitmAttrType").change(function () {
    alert("err");
    var uitmAttrTypeIndex = $(this).index("select." + $(this).attr("class"));
    var uitmAttrTypeCnt = $("select.selectUitmAttrType").length;
    $("select.selectBaseCmpsType").find("option:first").attr("selected", true);
    if (isEmpty($(this).val())) {
      return;
    }
    /*
     * 제일 마지막 속성의 경우 검색할 필요가 없으므로 선택 목록에 추가 후 리턴 시킴
     */
    if ((uitmAttrTypeIndex + 1) == uitmAttrTypeCnt) {
      var uitmSeqArr = new Array();
      var uitmNmArr = new Array();
      // 선택된 속성 명 가져오기
      $(".selected-opt").each(function () {
        uitmNmArr.push($(this).text());
      });
      // 선택된 속성 코드  가져오기
      $(".selected-uitmseq").each(function () {
        uitmSeqArr.push($(this).text());
      });
      /*
       * 동일 속성 선택 존재여부 체크 후 존재하는 경우 리턴
       */
      var flag = true;
      $(".opt-sel-box:eq(0) > li").each(function () {
        var existsUitmSeqArr = new Array();
        $(this).find("input[name='uitmSeq']").each(function () {
          existsUitmSeqArr.push($(this).val());
        });
        if (existsUitmSeqArr.join('') == uitmSeqArr.join('')) {
          alert("동일상품이 선택되었습니다.\n아래에서 선택사항을 확인해주세요.");
          flag = false;
        }
      });
      if (!flag) {
        return;
      }
      // 기본구성품이 존재하는 경우 리턴 시킴 - 기본구성품은 필수 선택
      if ($(".dtBaseSlitmCmpsNmArea").length > 0) {
        return;
      }
      addUitmHtml(uitmSeqArr, uitmNmArr, sellPossQty);
      calcSellPrc();
      return;
    }
  });
  $("label.selectUitmAttrType input").click(function (e) {
    var sellPossQty = $(this).attr("sellPossQty");
    var uitmAttrTypeCnt = $(this).parent().parent().parent().find("div.prd-opt-row.selectable").length;
    var uitmAttrCurrentIndex = $(this).parent().parent().index("#productInfoDetailDl div.prd-opt-row.selectable");
    var currentUitmSeq = ""; //$(this).val();
    $("select.selectBaseCmpsType").find("option:first").attr("selected", true);
    if (isEmpty($(this).parent().text())) {
      return;
    }
    $("input[name='" + $(this).attr("name") + "']").each(function () {
      inputbox(this);
    });
    if (uitmAttrCurrentIndex > 0) {
      var bCheck = false;
      $(this).parent().parent().parent().find("div.prd-opt-row.selectable:eq(" + (uitmAttrCurrentIndex - 1) + ") > label").each(function () {
        var classNm = $(this).attr("class");
        if (classNm.indexOf("checked") > 0)
          bCheck = true;
      });
      if (!bCheck) {
        alert("위의 정보를 먼저 선택해주세요.");
        $(this).parent("label").removeClass("checked");
        return false;
      }
    }
    // 제일 마지막 속성의 경우 검색할 필요가 없으므로 선택 목록에 추가 후 리턴 시킴
    if ((uitmAttrCurrentIndex + 1) == uitmAttrTypeCnt) {
      var uitmSeqArr = new Array();
      var uitmNmArr = new Array();
      // 선택된 속성 명 가져오기
      $("label.selectUitmAttrType.checked").each(function () {
        uitmNmArr.push($(this).text());
      });
      // 선택된 속성 코드  가져오기
      $("label.selectUitmAttrType.checked").each(function () {
        uitmSeqArr.push($(this).find("input").val());
      });
      // 동일 속성 선택 존재여부 체크 후 존재하는 경우 리턴
      var flag = true;
      $(".opt-sel-box:eq(0) li.selected-uitm-wrap").each(function () {
        var existsUitmSeqArr = new Array();
        $(this).find("div.selected-uitm > p.name > input[name='uitmSeq']").each(function () {
          existsUitmSeqArr.push($(this).val());
        });
        if (existsUitmSeqArr.join('') == uitmSeqArr.join('')) {
          alert("동일상품이 선택되었습니다.\n아래에서 선택사항을 확인해주세요.");
          flag = false;
        }
      });
      if (!flag) {
        return;
      }
      // 기본구성품이 존재하는 경우 리턴 시킴 - 기본구성품은 필수 선택
      if ($(".dtBaseSlitmCmpsNmArea").length > 0) {
        return;
      }
      addUitmHtml(uitmSeqArr, uitmNmArr, sellPossQty);
      calcSellPrc();
    }
    else {
      /* 선택한 depth의 하위 옵션들은 체크해제 */
      uitmAttrCurrentIndex = uitmAttrCurrentIndex + 1;
      for (var i = uitmAttrCurrentIndex; uitmAttrTypeCnt > i; i++) {
        $(this).parent().parent().parent().find("div.prd-opt-row.selectable:eq(" + i + ") label").each(function () {
          $(this).removeClass("checked");
          /* 상위 depth에서 선택한 상품속성순번과 일치하는 하위 depth를 제외하고는 모두 숨김 처리한다. */
          if (uitmAttrCurrentIndex == (uitmAttrTypeCnt - 1)) {
            /* 재고가 없는 옵션은 비활성화 처리 */
            if ($(this).find("input").attr("stckYn") == "N") {
              $(this).find("input").attr("disabled", true);
              $(this).addClass("disabled");
              if ($(this).find("span.bg").length == 0) {
                $(this).append(
                  $("<span/>").addClass("bg")
                );
              }
              var slitmNm = $("#itemInfForm input[name=slitmNm]").val();
              var slitmCd = $("#itemInfForm input[name=slitmCd]").val();
              var uitmCd = $(this).find("input").attr("uitmCd");
              var uitmTotNm = $(this).find("input").attr("uitmTotNm");
              var rishpAlrimYn = $(this).find("input").attr("rishpAlrimYn");
              //$(this).attr("style", "cursor:pointer");
              //$(this).css({"cursor":"pointer"});
              $(this).attr("onclick", "showItemRishpAlrimPup(this, '" + slitmCd + "', '" + uitmCd + "', '" + slitmNm + "', '" + uitmTotNm + "', '" + rishpAlrimYn + "')");
            }
            $("label.selectUitmAttrType.checked").each(function () {
              currentUitmSeq += $(this).find("input").val() + "_";
            });
            var totSeqTemp = $(this).find("input").attr("totseq");
            if (totSeqTemp != '' && !(typeof totSeqTemp == 'undefined')) {
              totSeqTemp = totSeqTemp.substring(0, (totSeqTemp.length - 1));
            }
            if (totSeqTemp.indexOf(currentUitmSeq) > -1)
              $(this).removeAttr("style");
            else
              $(this).attr("style", "display:none");
            currentUitmSeq = "";
          }
        });
      }
    }
    return;
  });
  $("select.selectUitmCd").change(function () {
    $("select.selectBaseCmpsType").find("option:first").attr("selected", true);
    if (isEmpty($(this).val())) {
      return;
    }
    var uitmCd = $(this).val();
    var uitmTotNm = $(this).find("option:selected").text();
    var sellPossQty = $(this).find("option:selected").attr("sellPossQty");
    // 동일 속성 선택 존재여부 체크 후 존재하는 경우 리턴
    var flag = true;
    $(".opt-sel-box:eq(0) > li").each(function () {
      if (flag) {
        var exstUitmCd = $(this).find("p.name input[name='uitmCd']").val();
        if (exstUitmCd == uitmCd) {
          alert("동일상품이 선택되었습니다.\n아래에서 선택사항을 확인해주세요.");
          flag = false;
        }
      }
    });
    if (!flag) {
      //20200227 속성표기 개선
      //$(this).val('');
      $(this).find("option:first").attr("selected", true);
      $(this).parents('.selectric-hide-select').parents('.selectric-wrapper').find('div.selectric > p.label').text('옵션을 선택하세요');
      return;
    }
    // 기본구성품이 존재하는 경우 리턴 시킴 - 기본구성품은 필수 선택
    if ($(".dtBaseSlitmCmpsNmArea").length > 0) {
      return;
    }
    addUitmBsicHtml(uitmCd, uitmTotNm, sellPossQty);
    calcSellPrc();
    // lst 2016-05-12
    if ($(this).closest('.mCSB_container').length > 0) {
      $(this).closest('.mCSB_container').height('auto');
    }
    //20200227 속성표기 개선
    //$(this).val('');
    //$(this).find("option:first").attr("selected", true);
    $(this).parents('.selectric-hide-select').parents('.selectric-wrapper').find('div.selectric > p.label').text('옵션을 선택하세요');
    return;
  });
  $("select.selectBaseCmpsType").change(function () {
    var selectedCount = 0;
    $(".opt-sel-box:eq(0) li.liCmpsArea").each(function () {
      selectedCount++;
    });
    var uitmCombYn = $("input[name='uitmCombYn']").val();
    var uitmChocPossYn = $("input[name='uitmChocPossYn']").val();
    if (uitmCombYn == "Y" && uitmChocPossYn == "Y") {
      if (selectedCount <= 0) {
        alert("상품옵션을 먼저 선택하시기 바랍니다.");
        $(this).find("option:first").attr("selected", true);
        $(this).parent().parent().find("div.selectric > p.label").text("선택하세요");
        $(this).parent().parent().find("div.selectric-items > div.selectric-scroll > ul > li").removeClass("selected");
        $(this).parent().parent().find("div.selectric-items > div.selectric-scroll > ul > li:first").addClass("selected");
        // 옵션 선택창 활성화
        $(".opt-select-box").toggleClass("current");
        $(".opt-select-box").find(".opt-select-layer li:first-child").addClass("current")
        return;
      }
    } else {
      // selectUitmCd : 상품속성 속성유형이 존재하지 않는 경우의 class명
      if ($("select.selectUitmCd").length > 0 && isEmpty($("select.selectUitmCd").val())) {
        alert("상품옵션을 먼저 선택하시기 바랍니다.");
        $(this).find("option:first").attr("selected", true);
        $(this).parent().parent().find("div.selectric > p.label").text("선택하세요");
        $(this).parent().parent().find("div.selectric-items > div.selectric-scroll > ul > li").removeClass("selected");
        $(this).parent().parent().find("div.selectric-items > div.selectric-scroll > ul > li:first").addClass("selected");
        return;
      }
    }
    var baseCmpsTypeIndex = $(this).index("select." + $(this).attr("class"));
    // 선택값이 없을 경우 리턴시킴
    if (isEmpty($(this).val())) {
      return;
    }
    var baseCmpsTypeCnt = $("select.selectBaseCmpsType").length;
    var baseCmpsSelectedCount = 0;
    $("select.selectBaseCmpsType option:selected").each(function () {
      if (!isEmpty($(this).val())) {
        baseCmpsSelectedCount++;
      }
    });
    if (baseCmpsTypeCnt > 0 && (baseCmpsSelectedCount < baseCmpsTypeCnt)) {
      return;
    }
    var uitmSeqArr = new Array();
    var uitmNmArr = new Array();
    $(".selected-opt").each(function () {
      uitmNmArr.push($(this).text());
    });
    $(".selected-uitmseq").each(function () {
      uitmSeqArr.push($(this).val());
    });
    /*
     * 동일 속성 선택 존재여부 체크 후 존재하는 경우 리턴
     */
    var flag = true;
    $(".opt-sel-box:eq(0) > li").each(function () {
      var existsUitmSeqArr = new Array();
      $(this).find("input[name='uitmSeq']").each(function () {
        existsUitmSeqArr.push($(this).val());
      });
      if (existsUitmSeqArr.join('') == uitmSeqArr.join('')) {
        alert("동일상품이 선택되었습니다.\n아래에서 선택사항을 확인해주세요.");
        flag = false;
      }
    });
    if (!flag) {
      return;
    }
    sellPossQty = "0;";
    addUitmHtml(uitmSeqArr, uitmNmArr, sellPossQty);
    calcSellPrc();
  });
  $("select.selectAddCmpsType").change(function () {
    // 속성 선택여부 체크 (속성에 종속되기때문에 필수 선택)
    var selectedCount = 0;
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap").each(function () {
      selectedCount++;
    });
    var uitmCombYn = $("input[name='uitmCombYn']").val();
    var uitmChocPossYn = $("input[name='uitmChocPossYn']").val();
    if (uitmCombYn == "Y" && uitmChocPossYn == "Y") {
      // 조합형이고, 상품속성선택가능여부가 Y인 경우(accType 옵션선택)
      if (selectedCount <= 0) {
        alert("상품옵션을 먼저 선택하시기 바랍니다.");
        $(this).find("option:first").attr("selected", true);
        $(this).parent().parent().find("div.selectric > p.label").text("선택하세요");
        $(this).parent().parent().find("div.selectric-items > div.selectric-scroll > ul > li").removeClass("selected");
        $(this).parent().parent().find("div.selectric-items > div.selectric-scroll > ul > li:first").addClass("selected");
        // 옵션 선택창 활성화
        $(".opt-select-box").toggleClass("current");
        $(".opt-select-box").find(".opt-select-layer li:first-child").addClass("current")
        return;
      }
    } else {
      // selectUitmCd : 상품속성 속성유형이 존재하지 않는 경우의 class명
      if ($("select.selectUitmCd").length > 0 && isEmpty($("select.selectUitmCd").val())) {
        alert("상품옵션을 먼저 선택하시기 바랍니다.");
        $(this).find("option:first").attr("selected", true);
        $(this).parent().parent().find("div.selectric > p.label").text("선택하세요");
        $(this).parent().parent().find("div.selectric-items > div.selectric-scroll > ul > li").removeClass("selected");
        $(this).parent().parent().find("div.selectric-items > div.selectric-scroll > ul > li:first").addClass("selected");
        return;
      }
    }
    // 기본구성 선택여부 체크 (기본구성은 필수 선택 - 존재하지 않는 경우 무시한다.)
    var baseCmpsSelectedCount = 0;
    $("select.selectBaseCmpsType option:selected").each(function () {
      if (!isEmpty($(this).val())) {
        baseCmpsSelectedCount++;
      }
    });
    if ($("select.selectBaseCmpsType").length > 0 && (baseCmpsSelectedCount < $("select.selectBaseCmpsType").length)) {
      alert("기본구성을 먼저 선택하시기 바랍니다.");
      $(this).find("option:first").attr("selected", true);
      return;
    }
    var addCmpsTypeIndex = $(this).index("select." + $(this).attr("class"));
    // 선택값이 없을 경우 리턴시킴
    if (isEmpty($(this).val())) {
      return;
    }
    var cmpsSeq = $(this).parents(".prd-opt-wrap").find(".addCmpsRow:eq(" + addCmpsTypeIndex + ") > h4.dtAddSlitmCmpsNmArea > input[name='cmpsSeq']").val();
    var eqIdx = -1;
    $(".opt-sel-box:eq(0) > li.selected-uitm-wrap:last > div.opt-sel-block-wrap > input[name='cmpsSeq']").each(function () {
      if ($(this).val() == cmpsSeq) {
        eqIdx = $(this).index(".opt-sel-box:eq(0) > li.selected-uitm-wrap:last > div.opt-sel-block-wrap > input[name='cmpsSeq']");
      }
    });
    /*
     * 동일 속성 선택 존재여부/복수추가구매 불가여부 체크 후 존재하는 경우 리턴
     */
    if (eqIdx > -1) {
      var existYn = false;
      var cmpsItemSeq = $(this).val().split('|')[0];
      var plAddBuyNdmtYn = $("h4.dtAddSlitmCmpsNmArea:eq(" + addCmpsTypeIndex + ")").find("input[name='plAddBuyNdmtYn']").val();
      if (plAddBuyNdmtYn == 'Y') {
        alert("본 구성품은 1개만 선택하실 수 있습니다.");
        return;
      }
      $(".opt-sel-box:eq(0) > li.selected-uitm-wrap:last > div.opt-sel-block-wrap:eq(" + eqIdx + ") > div.opt-sel-block > div.cnt-ctrl > input[name='cmpsItemSeq']").each(function () {
        if ($(this).val() == cmpsItemSeq) {
          existYn = true;
        }
      });
      if (existYn) {
        alert("동일상품이 선택되었습니다.\n아래에서 선택사항을 확인해주세요.");
        return;
      }
    }
    if (eqIdx == -1) {    // 동일한 구성순번 구성품이 존재하지 않으므로 추가해준다.
      var div = $("<div></div>");
      $(div).addClass("opt-sel-block-wrap");
      $(div).append('<div class="add-cmps-nm">' + $("h4.dtAddSlitmCmpsNmArea:eq(" + addCmpsTypeIndex + ")").text() + '</div>');
      $(div).append($('<input type="hidden" name="cmpsSeq" value="' + cmpsSeq + '"/>'));
      $(div).append($('<input type="hidden" name="plAddBuyNdmtYn" value="' + $("h4.dtAddSlitmCmpsNmArea:eq(" + addCmpsTypeIndex + ")").find("input[name='plAddBuyNdmtYn']").val() + '"/>'));
      if ($(".opt-sel-box:eq(0) li.selected-uitm-wrap").length == 0) {
        $(".opt-sel-box:eq(0)").append('<li class="selected-uitm-wrap"></li>');
      }
      $(".opt-sel-box:eq(0) li.selected-uitm-wrap:last").append($(div));
      eqIdx = $(".opt-sel-box:eq(0) li.selected-uitm-wrap:last div.opt-sel-block-wrap").length - 1;
    }
    // clone() 함수 사용 시 오류 발생.
    var addDiv = $("#addCmpsTemplate").html();
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap:last div.opt-sel-block-wrap:eq(" + eqIdx + ")").append($(addDiv));
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap:last div.opt-sel-block-wrap:eq(" + eqIdx + ")").find("div.opt-sel-block:last p.name").text($(this).find("option:selected").text());
    if (!isEmpty($(this).val()) && $(this).val().split('|').length == 2) {
      var sellPrc = $(this).val().split('|')[1];
      $(".opt-sel-box:eq(0) li.selected-uitm-wrap:last div.opt-sel-block-wrap:eq(" + eqIdx + ")").find("div.opt-sel-block:last p.price").html(gfn_appendComma(sellPrc) + "<span>원</span>"); // 가격
      $(".opt-sel-box:eq(0) li.selected-uitm-wrap:last div.opt-sel-block-wrap:eq(" + eqIdx + ")").find("div.opt-sel-block:last div.cnt-ctrl").append($('<input type="hidden" name="cmpsItemSeq" value="' + $(this).val().split('|')[0] + '"/>'));
      $(".opt-sel-box:eq(0) li.selected-uitm-wrap:last div.opt-sel-block-wrap:eq(" + eqIdx + ")").find("div.opt-sel-block:last div.cnt-ctrl").append($('<input type="hidden" name="addCmpsSellPrc" value="' + sellPrc + '"/>'));
    }
    calcSellPrc();
  });
  /* 매장 소개글 등록 */
  $("#itemSubmitBtn").click(function (e) {
    e.preventDefault();
    if (wordchkcafe1($("textarea[name=mentCntn]").val())) {
      alert("금칙어가 입력되었습니다.");
      $("textarea[name=mentCntn]").focus();
      return false;
    }
    //최소 10자이상입력
    if ($("textarea[name=mentCntn]").val().length < 10) {
      alert("10글자 이상 작성하셔야 등록됩니다.");
      return false;
    }
    var url = "";
    var msg = "";
    if ($("input[name=storeInfoYn]").val() == 'Y') {
      url = "/front/pdb/updateItemStoreInfo.thd";
      msg = "수정";
    } else {
      url = "/front/pdb/insertItemStoreInfo.thd";
      msg = "입력";
    }
    $("#itemStoreForm").ajaxSubmit({
      url: url
      , dataType: "json"
      , success: function (data) {
        if (data.success) {
          alert("매장 정보가 " + msg + "되었습니다.");
          document.location.reload();
        } else {
          isWorking = false;
          alert(data.errorMessage);
        }
      }
      , error: function () {
        alert("매장 정보 " + msg + "에 실패하였습니다. 다시 시도해 주세요.");
        isWorking = false;
      }
    });
  });
});
// @REQ-SD-007 @2018.11.09 @황희선 @옵션 선택에 따른 다음 depth 옵션 설정
function selectUitmAttrTypeListForChange($target, slitmCd, uitmSeq, uitmAttrTypeSeq, uitmSeqArrParam, callback) {
  /*
  * 선택한 속성에 따른 다음 속성 값 조회 후 select 셋팅
  */
  $.ajax({
    type: "post"
    , url: "/front/pda/selectPDAUitmAttrTypeListForChangeSelect.thd?uitmSeqArr=" + uitmSeqArrParam.join("&uitmSeqArr=")
    , dataType: "json"
    , data: { slitmCd: slitmCd, uitmSeq: uitmSeq, uitmAttrTypeSeq: uitmAttrTypeSeq }
    , success: function (data) {
      if (data.uitmList && data.uitmList.length > 0) {
        var $topOptSelectBox = $('<div class="opt-select-box selbox">');
        var objHtml = '';
        // @REQ-SD-007 @2018.11.13 @황희선 @옵션명 삭제 후 셀렉트 박스에 입력
        //objHtml += '<h4 class="opt-name">'+data.uitmList[0].uitmAttrTypeNm+'</h4>';
        objHtml += '<div class="opt-select-value" uitmAttrTypeNm="' + data.uitmList[0].uitmAttrTypeNm + '"><a href="#" class="ellips">' + data.uitmList[0].uitmAttrTypeNm + '</a></div>';
        objHtml += '<ul class="opt-select-layer">';
        objHtml += '</ul>';
        $('.sstpl_opt_selWrap').append($topOptSelectBox.append(objHtml));
        var tempHTML = "";
        var prevUitmSeq = "";
        for (var i in data.uitmList) {
          if (data.uitmList[i].uitmAttrTypeSeq == uitmAttrTypeSeq) {
            if (prevUitmSeq != data.uitmList[i].uitmSeq) {
              prevUitmSeq = data.uitmList[i].uitmSeq;
              // 해당 li의 하위 옵션이 있을경우 셀렉트 박스 그려줌(선택값이 있을경우만..)
              if (i == 0)
                tempHTML = "<li><span class='selected-opt' style='display:none;'></span><span class='selected-uitmseq'></span><ul class='depth-opt-list'>";
              tempHTML += "<li data ='" + data.uitmList[i].uitmSeq + "' uitmCd='" + data.uitmList[i].uitmCd + "' uitmAttrTypeSeq='" + uitmAttrTypeSeq + "'><a href='#' optSlitmCd='" + slitmCd + "'><span class='opt-name'>" + data.uitmList[i].uitmAttrNm + "</span></a></li>";
              if (i == (data.uitmList.length - 1)) {
                tempHTML += "</ul></li>";
                $topOptSelectBox.find(".opt-select-layer").append(tempHTML);
              }
            }
          }
        }
        $target.parents(".opt-select-box").removeClass("current");
        $target.parents(".opt-select-box").find(".opt-select-layer li:first-child").removeClass("current");
        $topOptSelectBox.addClass("current");
        $topOptSelectBox.find(".opt-select-layer li:first-child").addClass("current");
        uitmAttrTypeIndex++;
      } else {
        // 상품속성코드 조회
        $.ajax({
          type: "post"
          , url: "/front/pda/selectPDAUitmAttrInfo.thd?uitmSeqArr=" + uitmSeqArrParam.join("&uitmSeqArr=")
          , dataType: "json"
          , data: { slitmCd: slitmCd, uitmSeq: uitmSeq, uitmAttrTypeSeq: (Number(uitmAttrTypeSeq) - 1) }
          , success: function (data) {
            if (callback != undefined && callback != null) {
              callback(data);
            }
          }
          , error: function (data) {
            alert("시스템 오류입니다. 다시 시도해주세요.");
          }
        });
        return;
      }
    }
    , error: function (data) {
      alert("시스템 오류입니다. 다시 시도해주세요.");
    }
  });
}
// @REQ-SD-090 @2018.12.12 @황희선 @상품선택에 따른 주문추가요청 정보 조회
function selectOrdAddReqnList(slitmCd, callback) {
  $.ajax({
    type: "post"
    , url: "/front/pda/selectPDAOrdAddReqnList.thd"
    , dataType: "json"
    , data: { slitmCd: slitmCd }
    , async: false
    , success: function (data) {
      $('#uitmTemplate').find('.selected-uitm-write').remove();
      if (data.ordAddReqnList && data.ordAddReqnList.length > 0) {
        var tempHTML = $('<ul class="selected-uitm-info-list">');
        // 주문추가요청정보 Html 생성
        for (var i in data.ordAddReqnList) {
          var ddHtml = $('<dd>');
          if (data.ordAddReqnList[i].addReqnEx != null && data.ordAddReqnList[i].addReqnEx != '') {
            ddHtml.append('<input type="text" name="addReqnInsrCntn" class="selected-uitm-info-input" placeholder="' + data.ordAddReqnList[i].addReqnEx + '" />');
          } else {
            ddHtml.append('<input type="text" name="addReqnInsrCntn" class="selected-uitm-info-input" placeholder="요청사항 입력" />');
          }
          ddHtml.append('<input type="hidden" name="addReqnSeq" value="' + data.ordAddReqnList[i].addReqnSeq + '" />');
          tempHTML.append(
            $('<li>').append(
              $('<dl class="selected-uitm-info-unit">').append(
                '<dt>' + data.ordAddReqnList[i].addReqnTitl + '</dt>'
              ).append(ddHtml)
            )
          );
        }
        $('#uitmTemplate>.selected-uitm-wrap').append(
          $('<div class="selected-uitm-write">').append(
            tempHTML
          )
        );
      }
      if (callback != undefined) {
        callback();
      }
    }
    , error: function (data) {
      alert("시스템 오류입니다. 다시 시도해주세요.");
    }
  });
}
// @REQ-SD-007 @2018.11.12 @황희선 @uitmCd를 추가
function addUitmHtml(uitmSeqArr, uitmNmArr, sellPossQty, uitmCd) {
  var div = $("#uitmTemplate").html();
  $(".opt-sel-box:eq(0)").append(div);
  // 선택된 옵션, 가격 정보 삽입
  // @REQ-SD-007 @2018.11.07 @황희선 @uitmNmArr의 type에 따른 분기 처리
  // @REQ-SD-009 @2018.12.12 @황희선 @selector 수정
  if (typeof uitmNmArr == "string") {
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").text(uitmNmArr);                                   // 옵션
  } else {
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").text(uitmNmArr.join(" / "));                                   // 옵션
  }
  $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.price").html(gfn_appendComma($("input[name='sellPrc']").val()) + "<span>원</span>");   // 가격
  $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > div.cnt-ctrl > input[name='sellPossQty']").val(sellPossQty);            // 판매가능수량
  var baseCmpsTypeLength = $("select.selectBaseCmpsType").length;
  if (baseCmpsTypeLength > 0) {
    $("select.selectBaseCmpsType").each(function () {
      var baseUitmSpan = $("#baseUitmSpanTemplate").html();
      var addBaseUitmText = $(this).find("option:selected").text();
      var cmpsItemSeq = $(this).val();
      var baseCmpsTypeIndex = $(this).index("select.selectBaseCmpsType");
      var cmpsSeq = $(this).parent().parent().find("h4.dtBaseSlitmCmpsNmArea input[name='cmpsSeq']:eq(" + baseCmpsTypeIndex + ")").val();
      $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($(baseUitmSpan).text("└" + addBaseUitmText));               // 기본구성
      $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($("<input type='hidden'/>").attr("name", "baseCmpsInfo").val(cmpsSeq + "|" + cmpsItemSeq));                // 기본구성
    });
  }
  var combUitmSeq = "";
  for (var i = 0; i < uitmSeqArr.length; i++) {
    combUitmSeq += uitmSeqArr[i] + "_";
    var input = $("<input type='hidden'/>");
    $(input).attr("name", "uitmSeq").val(uitmSeqArr[i]);
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap:last").find("div.selected-uitm > p.name").append($(input));
  }
  // @REQ-SD-007 @2018.11.12 @황희선 @uitmCd 유무에 따른 분기
  var selectedUitmCd = (uitmCd != undefined && uitmCd != null) ? uitmCd : $("#" + combUitmSeq).val();
  var input2 = $("<input type='hidden'/>");
  $(input2).attr("name", "uitmCd").val(selectedUitmCd);
  $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($(input2));
  input2 = $("<input type='hidden' name='optNm' value='" + uitmNmArr + "' />");
  $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($(input2));
  // @REQ-SD-009 @2019.01.14 @황희선 @주문추가요청사항 입력 글자수 체크(100)
  $(".opt-sel-box:eq(0) > li:last").find('dd > input[name="addReqnInsrCntn"]').each(function () {
    $(this).keyup(function () {
      inputLimitCharacters($(this), 100);
    });
  });
}
function addUitmHtmlDeal(uitmCd, uitmNmArr, dluMaxBuyQty, sellPrc, slitmCd, ordMakeYn, packOpenRtpNdmtYn, frgnDrctBuyYn, sellPossQty, venCd, dptsNm, brndCd, brndNm, rsptMdCd, rsptMdNm) {
  // @REQ-SD-009 @2018.12.12 @황희선 @상품의 주문추가요청정보 목록 조회
  selectOrdAddReqnList(slitmCd, function () {
    var div = $("#uitmTemplate").html();
    $(".opt-sel-box:eq(0)").append($(div));
    // @REQ-SD-009 @2018.12.12 @황희선 @selector 수정
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").text(uitmNmArr.join(" / "));                               // 옵션
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.price").html(gfn_appendComma(sellPrc) + "<span>원</span>");        // 가격
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > div.cnt-ctrl > input[name='sellPossQty']").val(sellPossQty);        // 판매가능수량
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > div.cnt-ctrl > input[name='orgSellPrc']").val(sellPrc);         // 판매가능수량
    var input = $("<input type='hidden'/>");
    $(input).attr("name", "uitmCd").val(uitmCd);
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($(input));
    input = $("<input type='hidden' name='slitmCd' value='" + slitmCd + "' /><input type='hidden' name='uitmSellPrc' value='" + sellPrc + "' /><input type='hidden' name='dluMaxBuyQty' value='" + dluMaxBuyQty + "' /><input type='hidden' name='ordMakeYn' value='" + ordMakeYn + "' /><input type='hidden' name='packOpenRtpNdmtYn' value='" + packOpenRtpNdmtYn + "' /><input type='hidden' name='frgnDrctBuyYn' value='" + frgnDrctBuyYn + "' />"
      + "<input type='hidden' name='optNm' value='" + uitmNmArr + "' /><input type='hidden' name='venCd' value='" + venCd + "' /><input type='hidden' name='dptsNm' value='" + dptsNm + "' /><input type='hidden' name='brndCd' value='" + brndCd + "' /><input type='hidden' name='brndNm' value='" + brndNm + "' /><input type='hidden' name='rsptMdCd' value='" + rsptMdCd + "' /><input type='hidden' name='rsptMdNm' value='" + rsptMdNm + "' />");
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($(input));
    $('div.sumprice').css("display", "");
    // @REQ-SD-009 @2019.01.14 @황희선 @주문추가요청사항 입력 글자수 체크(100)
    $(".opt-sel-box:eq(0) > li:last").find('dd > input[name="addReqnInsrCntn"]').each(function () {
      $(this).keyup(function () {
        inputLimitCharacters($(this), 100);
      });
    });
  });
  // 2018.12.28 딜/옵션형 예약판매문구 표시 - S
  // 2018.12.28 딜/옵션형 예약판매문구 표시 - E
}
function addUitmBsicHtml(uitmCd, uitmNmArr, sellPossQty) {
  var div = $("#uitmTemplate").html();
  $(".opt-sel-box:eq(0)").append(div);
  // @REQ-SD-009 @2018.12.12 @황희선 @selector 수정
  $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").text(uitmNmArr);                                               // 옵션
  $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.price").html(gfn_appendComma($("input[name='sellPrc']").val()) + "<span>원</span>");   // 가격
  $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > div.cnt-ctrl > input[name='sellPossQty']").val(sellPossQty);            // 판매가능수량
  var baseCmpsTypeLength = $("select.selectBaseCmpsType").length;
  if (baseCmpsTypeLength > 0) {
    $("select.selectBaseCmpsType").each(function () {
      var baseUitmSpan = $("#baseUitmSpanTemplate").html();
      var addBaseUitmText = $(this).find("option:selected").text();
      var cmpsItemSeq = $(this).val();
      var baseCmpsTypeIndex = $(this).index("select.selectBaseCmpsType");
      var cmpsSeq = $(this).parent().parent().find("h4.dtBaseSlitmCmpsNmArea input[name='cmpsSeq']:eq(" + baseCmpsTypeIndex + ")").val();
      $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($(baseUitmSpan).text("└" + addBaseUitmText));               // 기본구성
      $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($("<input type='hidden'/>").attr("name", "baseCmpsInfo").val(cmpsSeq + "|" + cmpsItemSeq));                // 기본구성
    });
  }
  var input2 = $("<input type='hidden'/>");
  $(input2).attr("name", "uitmCd").val(uitmCd);
  $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($(input2));
  input2 = $("<input type='hidden' name='optNm' value='" + uitmNmArr + "' />");
  $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($(input2));
  // @REQ-SD-009 @2019.01.14 @황희선 @주문추가요청사항 입력 글자수 체크(100)
  $(".opt-sel-box:eq(0) > li:last").find('dd > input[name="addReqnInsrCntn"]').each(function () {
    $(this).keyup(function () {
      inputLimitCharacters($(this), 100);
    });
  });
}
function addUitmBsicHtmlDeal(uitmCd, uitmNmArr, dluMaxBuyQty, sellPrc, slitmCd, ordMakeYn, packOpenRtpNdmtYn, frgnDrctBuyYn, sellPossQty, venCd, dptsNm, brndCd, brndNm, rsptMdCd, rsptMdNm) {
  // @REQ-SD-009 @2018.12.12 @황희선 @상품의 주문추가요청정보 목록 조회
  selectOrdAddReqnList(slitmCd, function () {
    //alert(uitmCd + " // " + uitmNmArr + " // " + dluMaxBuyQty + " // " + sellPrc + " // " + slitmCd + " // " + ordMakeYn + " // " + packOpenRtpNdmtYn);
    var div = $("#uitmTemplate").html();
    $(".opt-sel-box:eq(0)").append($(div));
    // @REQ-SD-009 @2018.12.12 @황희선 @selector 수정
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").text(uitmNmArr.join(" / "));                                           // 옵션
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.price").html(gfn_appendComma(sellPrc) + "<span>원</span>");        // 가격
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > div.cnt-ctrl > input[name='sellPossQty']").val(sellPossQty);        // 판매가능수량
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > div.cnt-ctrl > input[name='orgSellPrc']").val(sellPrc);         // 판매가능수량
    var input = $("<input type='hidden'/>");
    $(input).attr("name", "uitmCd").val(uitmCd);
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($(input));
    input = $("<input type='hidden' name='slitmCd' value='" + slitmCd + "' /><input type='hidden' name='uitmSellPrc' value='" + sellPrc + "' /><input type='hidden' name='dluMaxBuyQty' value='" + dluMaxBuyQty + "' /><input type='hidden' name='ordMakeYn' value='" + ordMakeYn + "' /><input type='hidden' name='packOpenRtpNdmtYn' value='" + packOpenRtpNdmtYn + "' /><input type='hidden' name='frgnDrctBuyYn' value='" + frgnDrctBuyYn + "' />"
      + "<input type='hidden' name='optNm' value='" + uitmNmArr + "' /><input type='hidden' name='venCd' value='" + venCd + "' /><input type='hidden' name='dptsNm' value='" + dptsNm + "' /><input type='hidden' name='brndCd' value='" + brndCd + "' /><input type='hidden' name='brndNm' value='" + brndNm + "' /><input type='hidden' name='rsptMdCd' value='" + rsptMdCd + "' /><input type='hidden' name='rsptMdNm' value='" + rsptMdNm + "' />");
    $(".opt-sel-box:eq(0) > li:last").find("div.selected-uitm > p.name").append($(input));
    $('div.sumprice').css("display", "");
    // @REQ-SD-009 @2019.01.14 @황희선 @주문추가요청사항 입력 글자수 체크(100)
    $(".opt-sel-box:eq(0) > li:last").find('dd > input[name="addReqnInsrCntn"]').each(function () {
      $(this).keyup(function () {
        inputLimitCharacters($(this), 100);
      });
    });
    // 2018.12.28 딜/옵션형 예약판매문구 표시 - S
    // 2018.12.28 딜/옵션형 예약판매문구 표시 - E
  });
}
function rsvMsgDisplay(slitmCd) {
  // 1. 예약판매문구 여부 확인 - 옵션상품 list 정보 확인하기
}
function rsvMsgDisplayArr() {
  var rsvInfArr = [];
  // 1. 예약판매문구 여부 확인 - 옵션상품 list 정보 확인하기
  return rsvInfArr;
}
function copySlitmCd(slitmCd) {
  dummyLogCatch(null, 'ProductDetail', 'CodeCopy', null, 'N', null);
  if (document.all) {
    window.clipboardData.setData("Text", slitmCd);
    alert('해당 상품 코드가 클립보드에\n복사되었습니다.\n\Ctrl + V 또는 붙여넣기를 통해\n사용하실 수 있습니다.');
  } else {
    temp = prompt("Ctrl + C를 눌러 클립보드로 복사하세요", slitmCd);
  }
}
function deleteUitm(obj) {
  var selectedIdx = $(obj).parent().parent().index();
  $(obj).parent().parent().parent().find("li.selected-uitm-wrap:eq(" + selectedIdx + ")").remove();
  calcSellPrc();
}
function deleteAddCmpsItem(obj) {
  var selected_1depth = $(obj).parent().parent().parent().index();
  var selected_2depth = $(obj).parent().parent().index("li.selected-uitm-wrap:eq(" + selected_1depth + ") > div.opt-sel-block-wrap");
  var addCmpsIdx = $(obj).parent().index("li.selected-uitm-wrap:eq(" + selected_1depth + ") > div.opt-sel-block-wrap:eq(" + selected_2depth + ") > div.opt-sel-block");
  if ($(obj).parent().parent().parent().parent().find("li.selected-uitm-wrap:eq(" + selected_1depth + ") > div.opt-sel-block-wrap:eq(" + selected_2depth + ") > div.opt-sel-block").length == 1)
    $(obj).parent().parent().parent().parent().find("li.selected-uitm-wrap:eq(" + selected_1depth + ") > div.opt-sel-block-wrap:eq(" + selected_2depth + ")").remove();
  else
    $(obj).parent().parent().parent().parent().find("li.selected-uitm-wrap:eq(" + selected_1depth + ") > div.opt-sel-block-wrap:eq(" + selected_2depth + ") > div.opt-sel-block:eq(" + addCmpsIdx + ")").remove();
  calcSellPrc();
}
function changeOrdQty(obj) {
  this.checkNumber(obj);
  var ordQty = Number($(obj).val());
  var limitCnt = Number($(obj).attr("maxval"));
  var sellPossQty = $(obj).parent().find("input[name='sellPossQty']").val();
  var reqOrdQty = ordQty;
  if (isEmpty(limitCnt)) {
    limitCnt = 99;
  }
  if (sellPossQty < limitCnt) {
    if (ordQty > sellPossQty) {
      alert("본상품의 재고 수량은 " + sellPossQty + "개 입니다.");
      $(obj).parent().find("input[name='ordQty']").val(sellPossQty);
      reqOrdQty = sellPossQty;
    }
  } else {
    if (ordQty > limitCnt) {
      $(obj).val(limitCnt);
      alert("본 상품의 최대 구매 수량은 " + limitCnt + "개입니다.");
      reqOrdQty = limitCnt;
    }
  }
  var sellPrc = $("input[name='sellPrc']").val();
  var prc = Number(sellPrc) * (reqOrdQty);
  $(obj).parent().parent().find("p.price").html(gfn_appendComma(prc) + "<span>원</span>");
  calcSellPrc();
}
function changeOrdQtyBasic(obj) {
  this.checkNumber(obj);
  var ordQty = Number($(obj).val());
  var limitCnt = Number($(obj).attr("maxval"));
  var sellPossQty = $(obj).parent().find("input[name='sellPossQty']").val();
  var reqOrdQty = ordQty;
  //기프티콘 연동 2017.03.21
  if ($("input[name='giftConYn']").val() == "Y") {
    /*if (ordQty > 10) {
        alert("본 상품의 최대구매 수량은 10개 입니다.");
        $(obj).val(10);
        reqOrdQty = 10;
    }*/
    if (ordQty > 1) {
      alert("본 상품의 최대구매 수량은 1개 입니다.");
      $(obj).val(1);
      reqOrdQty = 1;
    }
  } else {
    if (isEmpty(limitCnt)) {
      limitCnt = 99;
    }
    if (sellPossQty < limitCnt) {
      if (ordQty > sellPossQty) {
        alert("본상품의 재고 수량은 " + sellPossQty + "개 입니다.");
        $(obj).val(sellPossQty);
        reqOrdQty = sellPossQty;
      }
    } else {
      if (ordQty > limitCnt) {
        $(obj).val(limitCnt);
        alert("본 상품의 최대 구매 수량은 " + limitCnt + "개입니다.");
        reqOrdQty = limitCnt;
      }
    }
  }
  var sellPrc = $("input[name='sellPrc']").val();
  var prc = Number(sellPrc) * reqOrdQty;
  $(obj).parent().parent().find("p.price").html(gfn_appendComma(prc) + "<span>원</span>");
  calcSellPrc();
}
function plusOrdQty(obj, limitCnt) {
  var input = $(obj).parent().find("input[name='ordQty']");
  var sellPossQty = $(obj).parent().find("input[name='sellPossQty']").val();
  var ordQty = Number($(input).val());
  var reqOrdQty = ordQty + 1;
  if (isEmpty(limitCnt)) {
    limitCnt = 99;
  }
  if (sellPossQty < limitCnt) {
    if (ordQty >= sellPossQty) {
      alert("본상품의 재고 수량은 " + sellPossQty + "개 입니다.");
      $(obj).parent().find("input[name='ordQty']").val(sellPossQty);
      reqOrdQty = sellPossQty;
    }
  } else {
    if (ordQty >= limitCnt) {
      $(input).val(limitCnt);
      alert("본 상품의 최대 구매 수량은 " + limitCnt + "개입니다.");
      reqOrdQty = limitCnt;
    }
  }
  var sellPrc = $("input[name='sellPrc']").val();
  if ($(obj).parent().find("input[name='orgSellPrc']").val() != "")
    sellPrc = $(obj).parent().find("input[name='orgSellPrc']").val();
  var prc = Number(sellPrc) * (reqOrdQty);
  $(obj).parent().parent().find("p.price").html(gfn_appendComma(prc) + "<span>원</span>");
  $(obj).parent().parent().find("div.cnt-ctrl input[name='ordQty']").val(reqOrdQty);
  calcSellPrc();
}
function minusOrdQty(obj) {
  var input = $(obj).parent().find("input[name='ordQty']");
  var ordQty = Number($(input).val());
  if (ordQty <= 1) {
    return;
  }
  var sellPrc = $("input[name='sellPrc']").val();
  var prc = Number(sellPrc) * (ordQty - 1);
  $(obj).parent().parent().find("p.price").html(gfn_appendComma(prc) + "<span>원</span>");
  $(obj).parent().parent().find("div.cnt-ctrl input[name='ordQty']").val(ordQty - 1);
  calcSellPrc();
}
function plusOrdQtyForAddCmpsItem(obj, limitCnt) {
  var plAddBuyNdmtYn = $(obj).parent().parent().parent().find("input[name='plAddBuyNdmtYn']").val();
  var input = $(obj).parent().find("input[name='ordQty']");
  var ordQty = Number($(input).val());
  //복수추가구매불가여부(plAddBuyNdmtYn)가 Y이면 수량제한을 본품과 같게 처리
  if (plAddBuyNdmtYn != 'Y') {
    limitCnt = '';
  }
  if (isEmpty(limitCnt)) {
    limitCnt = 99;
  }
  if (ordQty >= limitCnt) {
    $(input).val(limitCnt);
    alert("본 상품의 최대 구매 수량은 " + limitCnt + "개입니다.");
    return;
  }
  var sellPrc = $(obj).parent().find("input[name='addCmpsSellPrc']").val();
  var prc = Number(sellPrc) * (ordQty + 1);
  $(obj).parent().parent().find("p.price").html(gfn_appendComma(prc) + "<span>원</span>");
  $(obj).parent().parent().find("div.cnt-ctrl input[name='ordQty']").val(ordQty + 1);
  calcSellPrc();
}
function minusOrdQtyForAddCmpsItem(obj) {
  var input = $(obj).parent().find("input[name='ordQty']");
  var ordQty = Number($(input).val());
  if (ordQty <= 1) {
    return;
  }
  var sellPrc = $(obj).parent().find("input[name='addCmpsSellPrc']").val();
  var prc = Number(sellPrc) * (ordQty - 1);
  $(obj).parent().parent().find("p.price").html(gfn_appendComma(prc) + "<span>원</span>");
  $(obj).parent().parent().find("div.cnt-ctrl input[name='ordQty']").val(ordQty - 1);
  calcSellPrc();
}
function plusOrdQtyForNoUitm(obj, limitCnt) {
  var input = $(obj).parent().find("input[name='ordQty']");
  var sellPossQty = $(obj).parent().find("input[name='sellPossQty']").val();
  var ordQty = Number($(input).val());
  var reqOrdQty = ordQty + 1;
  //기프티콘 연동
  if ($("input[name='giftConYn']").val() == "Y") {
    /*if (ordQty >= 10) {
        alert("본상품의 최대구매 수량은 10개 입니다.");
        $(obj).parent().find("input[name='ordQty']").val(10);
        reqOrdQty = 10;
    }*/
    if (ordQty >= 1) {
      alert("본상품의 최대구매 수량은 1개 입니다.");
      $(obj).parent().find("input[name='ordQty']").val(1);
      reqOrdQty = 1;
    }
  } else {
    if (isEmpty(limitCnt)) {
      limitCnt = 99;
    }
    if (sellPossQty < limitCnt) {
      if (ordQty >= sellPossQty) {
        alert("본상품의 재고 수량은 " + sellPossQty + "개 입니다.");
        $(obj).parent().find("input[name='ordQty']").val(sellPossQty);
        reqOrdQty = sellPossQty;
      }
    } else {
      if (ordQty >= limitCnt) {
        $(input).val(limitCnt);
        alert("본 상품의 최대 구매 수량은 " + limitCnt + "개입니다.");
        reqOrdQty = limitCnt;
      }
    }
  }
  var sellPrc = $("input[name='sellPrc']").val();
  var prc = Number(sellPrc) * (reqOrdQty);
  $(obj).parent().parent().find("p.price").html(gfn_appendComma(prc) + "<span>원</span>");
  $(input).val(reqOrdQty);
  calcSellPrc();
}
function minusOrdQtyForNoUitm(obj) {
  var input = $(obj).parent().find("input[name='ordQty']");
  var ordQty = Number($(input).val());
  if (ordQty <= 1) {
    return;
  }
  var sellPrc = $("input[name='sellPrc']").val();
  var prc = Number(sellPrc) * (ordQty - 1);
  $(obj).parent().parent().find("p.price").html(gfn_appendComma(prc) + "<span>원</span>");
  $(input).val(ordQty - 1);
  calcSellPrc();
}
function calcSellPrc() {
  var totSellPrc = 0;
  var isMultiUitm = false;
  var isMultiCmps = false;
  // 상품속성 유형이 조합형인 경우
  $("span.selected-opt").each(function () {
    isMultiUitm = true;
  });
  // 상품속성 속성유형이 존재하는 경우 - selectbox
  $("select.selectUitmAttrType").each(function () {
    if ($(this).find("option").length > 1)
      isMultiUitm = true;
  });
  // 상품속성 속성유형이 존재하는 경우 - 아코디언
  $("div.opt-select-box.selbox").each(function () {
    if ($(this).find(".opt-select-value").length > 0)
      isMultiUitm = true;
  });
  // 상품속성 속성유형이 존재하는 경우 - 버튼식
  $("label.selectUitmAttrType").each(function () {
    if ($(this).find("input").length > 0)
      isMultiUitm = true;
  });
  // 상품속성 속성유형이 존재하지 않는 경우
  $("select.selectUitmCd").each(function () {
    if ($(this).find("option").length > 1)
      isMultiUitm = true;
  });
  // 기본 구성품
  $("select.selectBaseCmpsType").each(function () {
    if ($(this).find("option").length > 1)
      isMultiCmps = true;
  });
  // 추가 구성품
  $("select.selectAddCmpsType").each(function () {
    if ($(this).find("option").length > 1)
      isMultiCmps = true;
  });
  if (isMultiUitm) {
    $(".opt-sel-box:eq(0) > li.selected-uitm-wrap div.selected-uitm p.price").each(function () {
      var sellPrc = removeComma($(this).text());
      sellPrc = sellPrc.replace("원", "");
      if ($.isNumeric(sellPrc)) {
        totSellPrc += Number(sellPrc);
      }
    });
  } else {  // 속성이 존재하지 않는 경우
    var sellPrc = $("input[name='sellPrc']").val();
    var ordQty = $("#ddOrdQtyArea input[name='ordQty']").val();
    if ($.isNumeric(sellPrc) && $.isNumeric(ordQty)) {
      totSellPrc = Number(sellPrc * ordQty);
    }
  }
  if (isMultiCmps) {
    $(".opt-sel-box:eq(0) > li.selected-uitm-wrap div.opt-sel-block-wrap div.opt-sel-block p.price").each(function () {
      var sellPrc = removeComma($(this).text());
      sellPrc = sellPrc.replace("원", "");
      if ($.isNumeric(sellPrc)) {
        totSellPrc += Number(sellPrc);
      }
    });
  }
  $("#totItemPrcSpan").text(gfn_appendComma(totSellPrc));
  if ($(".opt-sel-box:eq(0) li.selected-uitm-wrap").length == 0)
    $('div.sumprice').css("display", "none");
  else
    $('div.sumprice').css("display", "");
}
function addCart(obj) {
  if ($("input[name=trndHSsaleTagYn]").val() == "Y" || $("input[name=trndHExclTagExpsYn]").val() == "Y") {  /* 옵션상품이거나 딜상품인경우 */
    //상품 유효성 체크
    var result = checkItemDeal(false);
    if (!result) {
      return;
    }
    var sectId = $("#itemInfForm input[name='sectId']").val();
    $('#cartForm').find("input[name='slitmInf']").each(function (index, obj) {
      $(obj).remove();
    });
    // @REQ-SD-009 @2018.12.13 @황희선 @주문추가요청사항 초기화
    $('#cartForm').find("input[name=addReqnMap]").remove();
    var addReqnMap = new Object();
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap").each(function () {
      var obj = $(this).find('div.selected-uitm');
      var slitmCd = obj.find('p.name > input[name="slitmCd"]').val();
      var uitmCd = obj.find('p.name >input[name="uitmCd"]').val();
      var uitmQty = obj.find('div.cnt-ctrl input[name="ordQty"]').val();
      var val = slitmCd + ':' + uitmCd + ':' + sectId + ':' + uitmQty;
      var val_input = '<input type="hidden" name="slitmInf" value="' + val + '" />';
      $('#cartForm').append(val_input);
      // @REQ-SD-009 @2018.12.13 @황희선 @주문추가요청사항 set
      var addObj = $(this).find('div.selected-uitm-write');   // 주문추가요청사항 영역
      if (addObj.length != undefined && addObj.length > 0) {
        var addObjMap = new Object();
        addObj.find(".selected-uitm-info-list > li").each(function () {
          var addReqnSeq = $(this).find('dd > input[name="addReqnSeq"]');
          var addReqnInsrCntn = $(this).find('dd > input[name="addReqnInsrCntn"]');
          addObjMap[addReqnSeq.val()] = addReqnInsrCntn.val();
        });
        addReqnMap[val] = addObjMap;
      }
    });
    // @REQ-SD-009 @2018.12.13 @황희선 @주문추가요청사항 form append
    $('#cartForm').append($("<input type='hidden' name='addReqnMap'/>").val(JSON.stringify(addReqnMap)));
    var storePicDt = $("input[name=storePicDt]").val(); // 스토어픽 픽업 날짜
    var rcvnPlceGbcd = $("input[name=rcvnPlceGbcd]").val(); // 스토어픽 픽업 날짜
    if (storePicDt && storePicDt != "") {
      storePicDt = storePicDt.replace(/-/g, "");
      // 여러번 장바구니를 클릭하는 경우 보완 처리
      $("#cartForm input[name='dlvHopeDt'], #cartForm input[name='rcvnPlceGbcd']").remove();
      $("#cartForm").append('<input type="hidden" name="dlvHopeDt" value="' + storePicDt + '"/>');
      $("#cartForm").append('<input type="hidden" name="rcvnPlceGbcd" value="' + rcvnPlceGbcd + '"/>');
    }
    $("form[name='cartForm']").ajaxSubmit({
      url: "/front/odb/addBaskt.thd?PageName=ProductDetail&PageBanner=Basket"
      , dataType: "json"
      , async: false
      , success: function (data) {
        if (data.needLogin) {
          openLoginPopup("addToBasket");
        } else {
          if (data.isAddOk) {
            //장바구니개수
            $("#quick_baskt_num").html(fnSelectBasktCount());
            if (confirm("해당 상품을 장바구니에 추가하였습니다. 장바구니 페이지로 이동하시겠습니까?")) {
              //rat_helper.send_event("CART",koost_data);    //stg 주석처리
              location.href = serverHostUrl + "/odb/basktList.thd";
            } else {
              //rat_helper.send_event("CART",koost_data);    //stg 주석처리
              return;
            }
          } else {
            if (!isEmpty(data.errorMessages)) {
              alert(data.errorMessages);
            } else {
              alert("장바구니 담기에 실패하였습니다.");
            }
          }
        }
      }
      , error: function (xhr, status, error) {
      }
    });
  }
  else {
    $("#itemInfForm input[name='uitmInf'], #itemInfForm input[name='uitmCdInf'], #itemInfForm input[name='addCmpsInf'], #itemInfForm input[name='ordQty'], #itemInfForm input[name='uitmCd'], #itemInfForm input[name='baseCmpsInf'], #itemInfForm input[name='asctSlitmUitmInf']").remove();
    var result = checkItem(false);
    if (!result) {
      return;
    }
    setSelectItem();
    $("form[name='itemInfForm']").ajaxSubmit({
      url: "/front/odb/addBaskt.thd?PageName=ProductDetail&PageBanner=Basket"
      , dataType: "json"
      , async: false
      , success: function (data) {
        if (data.needLogin) {
          openLoginPopup("addToBasket");
        } else {
          if (data.isAddOk) {
            //장바구니개수
            $("#quick_baskt_num").html(fnSelectBasktCount());
            if (confirm("해당 상품을 장바구니에 추가하였습니다. 장바구니 페이지로 이동하시겠습니까?")) {
              //rat_helper.send_event("CART",koost_data);    //stg 주석처리
              location.href = serverHostUrl + "/odb/basktList.thd";
            } else {
              //rat_helper.send_event("CART",koost_data);    //stg 주석처리
              return;
            }
          } else {
            if (!isEmpty(data.errorMessages)) {
              alert(data.errorMessages);
            } else {
              alert("장바구니 담기에 실패하였습니다.");
            }
          }
        }
      }
      , error: function (xhr, status, error) {
      }
    });
  }
}
function setSelectItem() {
  var uitmCombYn = $("input[name='uitmCombYn']").val();
  var uitmChocPossYn = $("input[name='uitmChocPossYn']").val();
  var uitmMacYn = $("input[name='uitmMacYn']").val();
  var index = 0;
  var flag = true;
  // @REQ-SD-009 @2018.12.13 @황희선 @주문추가요청사항 초기화
  var addReqnMap = new Object();
  $('#itemInfForm').find("input[name=addReqnMap]").remove();
  if (uitmCombYn == "Y" && uitmChocPossYn == "Y") {
    // 속성 셋팅
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap").each(function () {
      var val;
      $(this).find("div.selected-uitm").each(function () {
        var ordQty = $(this).find("div.cnt-ctrl input[name='ordQty']").val();    // 속성 수량
        var uitmSeqArr = new Array();
        $(this).find("p.name input[name='uitmSeq']").each(function () {
          uitmSeqArr.push($(this).val());
        });
        var input = $("<input type='hidden' name='uitmInf'/>");
        val = index + "^" + uitmSeqArr.join("|") + "|" + ordQty;
        $(input).val(val);
        $("#itemInfForm").append(input);
        // 기본구성품 셋팅
        $(this).find("p.name input[name='baseCmpsInfo']").each(function () {
          var input = $("<input type='hidden' name='baseCmpsInf'/>");
          $(input).val(index + "^" + $(this).val() + "|" + ordQty);
          $("#itemInfForm").append(input);
        });
        flag = false;
      });
      // @REQ-SD-009 @2018.12.13 @황희선 @주문추가요청사항 set
      var addObj = $(this).find('div.selected-uitm-write');   // 주문추가요청사항 영역
      if (addObj.length != undefined && addObj.length > 0) {
        var addObjMap = new Object();
        addObj.find(".selected-uitm-info-list > li").each(function () {
          var addReqnSeq = $(this).find('dd > input[name="addReqnSeq"]');
          var addReqnInsrCntn = $(this).find('dd > input[name="addReqnInsrCntn"]');
          addObjMap[addReqnSeq.val()] = addReqnInsrCntn.val();
        });
        addReqnMap[val] = addObjMap;
      }
      if (!flag) {
        // 추가구성품 셋팅
        $(this).find("div.opt-sel-block-wrap").each(function () {
          var cmpsSeq = $(this).find("input[name='cmpsSeq']").val();
          $(this).find("div.opt-sel-block div.cnt-ctrl input[name='cmpsItemSeq']").each(function () {
            var input = $("<input type='hidden' name='addCmpsInf'/>");
            var ordQty = $(this).parent().find("input[name='ordQty']").val();    // 수량
            $(input).val(index + "^" + cmpsSeq + "|" + $(this).val() + "|" + ordQty);
            $("#itemInfForm").append(input);
          });
        });
        index++;
      }
    });
    if (flag) {//$("#divOptionArea div.selectedUitm").length == 0) {
      var uitmSeqArr = new Array();
      $(".selected-opt").each(function () {
        uitmNmArr.push($(this).text());
      });
      var ordQty = $("#ddOrdQtyArea input[name='ordQty']").val();
      var input = $("<input type='hidden' name='uitmInf'/>");
      $(input).val(index + "^" + uitmSeqArr.join("|") + "|" + ordQty);
      $("#itemInfForm").append(input);
      // 기본구성품 셋팅
      $("li.liBaseCmpsArea p.name input[name='baseCmpsInfo']").each(function () {
        var input = $("<input type='hidden' name='baseCmpsInf'/>");
        $(input).val(index + "^" + $(this).val() + "|" + ordQty);
        $("#itemInfForm").append(input);
      });
      // 추가구성품 셋팅
      $("div.opt-sel-block-wrap").each(function () {
        var cmpsSeq = $(this).find("input[name='cmpsSeq']").val();
        $(this).find("div.opt-sel-block div.cnt-ctrl input[name='cmpsItemSeq']").each(function () {
          var input = $("<input type='hidden' name='addCmpsInf'/>");
          var ordQty = $(this).parent().find("input[name='ordQty']").val();    // 수량
          $(input).val(index + "^" + cmpsSeq + "|" + $(this).val() + "|" + ordQty);
          $("#itemInfForm").append(input);
        });
      });
      index++;
    }
  } else if ($("select.selectUitmCd").length > 0) {
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap").each(function () {
      var val;
      $(this).find("div.selected-uitm").each(function () {
        var ordQty = $(this).find("div.cnt-ctrl input[name='ordQty']").val();    // 속성 수량
        var uitmCd = $(this).find("p.name input[name='uitmCd']").val();
        var input = $("<input type='hidden' name='uitmCdInf'/>");
        val = index + "^" + uitmCd + "|" + ordQty;
        $(input).val(val);
        $("#itemInfForm").append(input);
      });
      // @REQ-SD-009 @2018.12.13 @황희선 @주문추가요청사항 set
      var addObj = $(this).find('div.selected-uitm-write');   // 주문추가요청사항 영역
      if (addObj.length != undefined && addObj.length > 0) {
        var addObjMap = new Object();
        addObj.find(".selected-uitm-info-list > li").each(function () {
          var addReqnSeq = $(this).find('dd > input[name="addReqnSeq"]');
          var addReqnInsrCntn = $(this).find('dd > input[name="addReqnInsrCntn"]');
          addObjMap[addReqnSeq.val()] = addReqnInsrCntn.val();
        });
        addReqnMap[val] = addObjMap;
      }
      // 기본구성품 셋팅
      $(this).find("li.liBaseCmpsArea p.name input[name='baseCmpsInfo']").each(function () {
        var input = $("<input type='hidden' name='baseCmpsInf'/>");
        $(input).val(index + "^" + $(this).val() + "|" + ordQty);
        $("#itemInfForm").append(input);
      });
      // 추가구성품 셋팅
      $(this).find("div.opt-sel-block-wrap").each(function () {
        var cmpsSeq = $(this).find("input[name='cmpsSeq']").val();
        $(this).find("div.opt-sel-block > div.cnt-ctrl > input[name='cmpsItemSeq']").each(function () {
          var input = $("<input type='hidden' name='addCmpsInf'/>");
          var ordQty = $(this).parent().find("input[name='ordQty']").val();    // 수량
          $(input).val(index + "^" + cmpsSeq + "|" + $(this).val() + "|" + ordQty);
          $("#itemInfForm").append(input);
        });
      });
      index++;
    });
  } else {
    var ordQtyInput = $("<input type='hidden' name='ordQty'/>").val($("#ddOrdQtyArea input[name='ordQty']").val());
    var uitmCdInput = $("<input type='hidden' name='uitmCd'/>").val($(".opt-sel-box:eq(0) li.selected-uitm-wrap div.selected-uitm:eq(0) input[name='uitmCd']:eq(0)").val());
    // @REQ-SD-009 @2018.12.13 @황희선 @주문추가요청사항 set
    var addObj = $('.opt-sel-box:eq(0) li.selected-uitm-wrap div.selected-uitm-write'); // 주문추가요청사항 영역
    if (addObj.length != undefined && addObj.length > 0) {
      var addObjMap = new Object();
      addObj.find(".selected-uitm-info-list > li").each(function () {
        var addReqnSeq = $(this).find('dd > input[name="addReqnSeq"]');
        var addReqnInsrCntn = $(this).find('dd > input[name="addReqnInsrCntn"]');
        addObjMap[addReqnSeq.val()] = addReqnInsrCntn.val();
      });
      addReqnMap[$(".opt-sel-box:eq(0) li.selected-uitm-wrap div.selected-uitm:eq(0) input[name='uitmCd']:eq(0)").val()] = addObjMap;
    }
    // 기본구성품 셋팅
    $(".opt-sel-box:eq(0) li.liBaseCmpsArea").each(function () {
      var input = $("<input type='hidden' name='baseCmpsInf'/>");
      $(input).val(index + "^" + $("p.name input[name='baseCmpsInfo']").val() + "|" + ordQty);
      $("#itemInfForm").append(input);
    });
    // 추가구성품 셋팅
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap div.opt-sel-block-wrap").each(function () {
      var cmpsSeq = $(this).find("input[name='cmpsSeq']").val();
      $(this).find("div.opt-sel-block div.cnt-ctrl input[name='cmpsItemSeq']").each(function () {
        var input = $("<input type='hidden' name='addCmpsInf'/>");
        var ordQty = $(this).parent().find("input[name='ordQty']").val();    // 수량
        $(input).val(index + "^" + cmpsSeq + "|" + $(this).val() + "|" + ordQty);
        $("#itemInfForm").append(input);
      });
    });
    $("#itemInfForm").append(ordQtyInput);
    $("#itemInfForm").append(uitmCdInput);
  }
  // @REQ-SD-009 @2018.12.13 @황희선 @주문추가요청사항 form append
  $('#itemInfForm').append($("<input type='hidden' name='addReqnMap'/>").val(JSON.stringify(addReqnMap)));
  var storePicDt = $("input[name=storePicDt]").val(); // 스토어픽 픽업 날짜
  if (storePicDt && storePicDt != "") {
    storePicDt = storePicDt.replace(/-/g, "");
    $("#itemInfForm input[name='dlvHopeDt']").val(storePicDt);
  }
}
function checkItem(isGAFunc) {
  var uitmCombYn = $("input[name='uitmCombYn']").val();
  var uitmChocPossYn = $("input[name='uitmChocPossYn']").val();
  if (uitmCombYn == "Y" && uitmChocPossYn == "Y") {
    if ($(".selected-opt").length > 0) {
      if ($(".opt-sel-box:eq(0) > li").length == 0) {
        if (!isGAFunc) {
          alert("상품속성을 선택해주세요.");
        }
        return false;
      }
    }
    else if ($(".selectUitmAttrType").length > 0) {
      if ($(".opt-sel-box:eq(0) > li").length == 0) {
        if (!isGAFunc) {
          alert("상품속성을 선택해주세요.");
        }
        return false;
      }
    }
    //@REQ-SD-007 @2018.12.31 @황희선 @셀렉트박스 변경에 따른 수량 체크 추가
    if ($("ul.depth-opt-list > li").length > 0) {
      if ($(".opt-sel-box:eq(0) > li").length == 0) {
        if (!isGAFunc) {
          alert("상품속성을 선택해주세요.");
        }
        return false;
      }
    }
  } else {
    if ($("ul.depth-opt-list > li").length > 0) {
      if ($(".opt-sel-box:eq(0) > li").length == 0) {
        if (!isGAFunc) {
          alert("상품속성을 선택해주세요.");
        }
        return false;
      }
    }
    if ($("select.selectUitmCd").not(".hidden").length > 0) {
      if ($(".opt-sel-box:eq(0) > li").length == 0) {
        if (!isGAFunc) {
          alert("상품속성을 선택해주세요.");
        }
        return false;
      }
    }
  }
  // 기본구성 선택여부 체크 (기본구성은 필수 선택 - 존재하지 않는 경우 무시한다.)
  var baseCmpsSelectedCount = 0;
  $("select.selectBaseCmpsType option:selected").each(function () {
    if (!isEmpty($(this).val())) {
      baseCmpsSelectedCount++;
    }
  });
  if ($("select.selectBaseCmpsType").length > 0 && (baseCmpsSelectedCount < $("select.selectBaseCmpsType").length)) {
    if (!isGAFunc) {
      alert("기본구성을 먼저 선택하시기 바랍니다.");
    }
    $(this).find("option:first").attr("selected", true);
    return false;
  }
  var result = true;
  var flag = true;
  if (uitmCombYn == "Y" && uitmChocPossYn == "Y") {
    // 속성 셋팅
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap").each(function () {
      $(this).find("div.selected-uitm").each(function () {
        var ordQty = $(this).find("div.cnt-ctrl input[name='ordQty']").val();    // 속성 수량
        if (isEmpty(ordQty) || Number(ordQty) == 0) {
          var uitmNm = $(this).find("p.name").text();
          if (!isGAFunc) {
            alert("구매수량을 입력해주세요.");
          }
          result = false;
          return;
        }
        flag = false;
      });
      // 추가구성품 셋팅
      $(this).find("div.opt-sel-block-wrap").each(function () {
        var ordQty = $(this).find("div.opt-sel-block div.cnt-ctrl input[name='ordQty']").val();    // 수량
        if (isEmpty(ordQty) || Number(ordQty) == 0) {
          if (!isGAFunc) {
            alert("구매수량을 입력해주세요.");
          }
          result = false;
          return;
        }
      });
    });
    if (flag) {//$(".opt-sel-box li").length == 0) {
      var ordQty = $("#ddOrdQtyArea input[name='ordQty']").val();
      if (isEmpty(ordQty) || Number(ordQty) == 0) {
        var uitmNm = $(this).find("p.name").text();
        if (!isGAFunc) {
          alert(uitmNm + "의 구매수량을 입력해주세요.");
        }
        return false;
      }
      // 추가구성품 셋팅
      $(this).find("div.opt-sel-block-wrap").each(function () {
        var ordQty = $(this).find("div.opt-sel-block div.cnt-ctrl input[name='ordQty']").val();    // 수량
        if (isEmpty(ordQty) || Number(ordQty) == 0) {
          if (!isGAFunc) {
            alert("구매수량을 입력해주세요.");
          }
          result = false;
          return;
        }
      });
    }
  } else if ($("select.selectUitmCd").length > 0) {
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap").each(function () {
      var ordQty = $(this).find("div.selected-uitm div.cnt-ctrl input[name='ordQty']").val();    // 속성 수량
      if (isEmpty(ordQty) || Number(ordQty) == 0) {
        var uitmNm = $(this).find("p.name").text();
        if (!isGAFunc) {
          alert(uitmNm + "의 구매수량을 입력해주세요.");
        }
        result = false;
        return;
      }
      // 추가구성품 셋팅
      $(this).find("div.opt-sel-block-wrap").each(function () {
        var ordQty = $(this).find("div.opt-sel-block div.cnt-ctrl input[name='ordQty']").val();    // 수량
        if (isEmpty(ordQty) || Number(ordQty) == 0) {
          if (!isGAFunc) {
            alert("구매수량을 입력해주세요.");
          }
          result = false;
          return;
        }
      });
    });
  } else {
    var ordQty = $("input[name='ordQty']:eq(0)").val();
    if (isEmpty(ordQty) || Number(ordQty) == 0) {
      if (!isGAFunc) {
        alert("구매수량을 입력해주세요.");
      }
      return false;
    }
    // 추가구성품 셋팅
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap div.opt-sel-block-wrap").each(function () {
      var ordQty = $(this).find("div.cnt-ctrl input[name='ordQty']").val();    // 수량
      if (isEmpty(ordQty) || Number(ordQty) == 0) {
        if (!isGAFunc) {
          alert("구매수량을 입력해주세요.");
        }
        result = false;
        return;
      }
    });
  }
  return result;
}
function checkItemDeal(isGAFunc) {
  var msg = "상품을 선택해주세요.";
  var checkItem = 0;
  var result = true;
  $('.opt-sel-box:eq(0) li.selected-uitm-wrap').each(function () {
    checkItem++;
    var obj = $(this).find('div.selected-uitm');
    var uitmQty = obj.find('div.cnt-ctrl > input[name="ordQty"]').val();
    if (uitmQty == 0) {
      msg = "구매수량을 확인해주세요.";
      result = false;
      return;
    }
  });
  if ($('div.sstpl_opt_selWrap div.opt-select-box.selbox').length > 1) {
    $('div.sstpl_opt_selWrap div.opt-select-box.selbox').each(function () {
      if ($(this).find('div.opt-select-value > a').text() == "옵션을 선택하세요.") {
        msg = "상품속성을 선택해주세요.";
        result = false;
        return;
      }
    });
  }
  if ($('div.sstpl_opt_selWrap div.opt-select-box.selbox').length == 1) {
    $('div.sstpl_opt_selWrap div.opt-select-box.selbox').each(function () {
      if ($(this).find('div.opt-select-value > a').text() == "상품을 선택하세요.") {
        msg = "상품을 선택해주세요.";
        result = false;
        return;
      }
    });
  }
  if (checkItem == 0 || !result) {
    if (!isGAFunc) {
      alert(msg);
    }
    return false;
  }
  return result;
}
function copyShortenUrl(slitmCd) {
  var shortenUrl = "http://thehyundai.kr/?i" + slitmCd;
  dummyLogCatch(null, 'ProductDetail', 'share_URLshare', null, 'N', null);
  // @REQ-SD-010 @2018.12.28 @황희선 @쉐어버튼 닫힘
  if ($(".prd-share-box").hasClass("on")) {
    $(".prd-share-box").removeClass("on");
  }
  if (document.all) {
    window.clipboardData.setData("Text", shortenUrl);
    alert('해당 상품 단축URL이 클립보드에\n복사되었습니다.\n\Ctrl + V 또는 붙여넣기를 통해\n사용하실 수 있습니다.');
  } else {
    temp = prompt("Ctrl + C를 눌러 클립보드로 복사하세요", shortenUrl);
  }
}
function goTwitter() {
  var slitmNm = $("input[name='slitmNm']").val();
  var slitmCd = $("input[name='slitmCd']").val();
  var shareLink = "https://www.thehyundai.com/front/pda/itemPtc.thd?slitmCd=" + slitmCd;
  dummyLogCatch(null, 'ProductDetail', 'share_twitter', null, 'N', null);
  // @REQ-SD-010 @2018.12.28 @황희선 @쉐어버튼 닫힘
  if ($(".prd-share-box").hasClass("on")) {
    $(".prd-share-box").removeClass("on");
  }
  shareSnsTwitter(slitmNm, shareLink);
}
function goFacebook() {
  var slitmNm = $("input[name='slitmNm']").val();
  var slitmCd = $("input[name='slitmCd']").val();
  var shareLink = "https://www.thehyundai.com/front/pda/itemPtc.thd?slitmCd=" + slitmCd;
  dummyLogCatch(null, 'ProductDetail', 'share_facebook', null, 'N', null);
  // @REQ-SD-010 @2018.12.28 @황희선 @쉐어버튼 닫힘
  if ($(".prd-share-box").hasClass("on")) {
    $(".prd-share-box").removeClass("on");
  }
  shareSnsFacebook(slitmNm, 'https://www.thehyundai.com/images/co/logo.png', shareLink);
}
function goKakaoStroy() {
  var slitmNm = $("input[name='slitmNm']").val();
  var slitmCd = $("input[name='slitmCd']").val();
  var shareLink = "https://www.thehyundai.com/front/pda/itemPtc.thd?slitmCd=" + slitmCd;
  dummyLogCatch(null, 'ProductDetail', 'share_kakaostory', null, 'N', null);
  // @REQ-SD-010 @2018.12.28 @황희선 @쉐어버튼 닫힘
  if ($(".prd-share-box").hasClass("on")) {
    $(".prd-share-box").removeClass("on");
  }
  shareSnsKakaoStory(shareLink, slitmNm);
}
function loginChk() {
  if (isLogin() == 'false') {
    alert("로그인후 이용하실 수 있습니다.");
    openLoginPopup();
  }
}
function dealAgreeChk() {
  if (isLogin() == 'false') {
    openLoginPopup("orderDeal", "order");
  } else {
    orderDeal();
  }
}
function agreeChk() {
  if (isLogin() == 'false') {
    openLoginPopup("buyDirectFormSubmit", "order");
  } else {
    buyDirectFormSubmit();
  }
}
var basktInfArr = new Array();
var rsvInfArr = [];
function buyDirect() {
  basktInfArr = new Array();  // 2019.01.08 옵션 선택 상품 배열 초기화
  var rsvOptItemYn = 'N'; // 2019.01.07 딜/옵션형 선택 상품
  //기프티콘 연동 2017.03.15
  if ($("input[name='giftConYn']").val() == "Y") {
    /*if (Number($("input[name='ordQty']").val()) > 10) {
        alert("최대구매 수량은 10개 입니다.");
        return false;
    }*/
    if (Number($("input[name='ordQty']").val()) > 1) {
      alert("최대구매 수량은 1개 입니다.");
      return false;
    }
  }
  if ($("input[name=trndHSsaleTagYn]").val() == "Y" || $("input[name=trndHExclTagExpsYn]").val() == "Y") {  /* 옵션상품이거나 딜상품인경우 */
    var result = checkItemDeal(false);
    if (!result) {
      return;
    }
    $('#itemInfForm').find("input[name='basktInf']").each(function (index, obj) {
      $(obj).remove();
    });
    // @REQ-SD-009 @2018.12.13 @황희선 @주문추가요청사항 초기화
    $('#itemInfForm').find("input[name=addReqnMap]").remove();
    var addReqnMap = new Object();
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap").each(function () {
      var obj = $(this).find('div.selected-uitm');
      var slitmCd = obj.find('p.name > input[name="slitmCd"]').val();
      var uitmCd = obj.find('p.name >input[name="uitmCd"]').val();
      var uitmQty = obj.find('div.cnt-ctrl input[name="ordQty"]').val();
      var uitmPrc = removeComma(obj.find('p.price').text().replace("원", ""));
      var myshNo = "";
      if ($("input[name='myshNo']").val() != "")
        myshNo = $("input[name='myshNo']").val();
      var storePicDt = "";
      if ($("input[name='storePicDt']").val() != "") {
        storePicDt = $("input[name=storePicDt]").val(); // 스토어픽 픽업 날짜
        storePicDt = storePicDt.replace(/-/g, "");
      }
      var rcvnPlceGbcd = "";
      if ($("input[name='rcvnPlceGbcd']").val() != "")
        rcvnPlceGbcd = $("input[name=rcvnPlceGbcd]").val(); // 스토어픽 수령장소
      var val = slitmCd + '|' + uitmCd + '|0|' + uitmQty + '|' + uitmPrc + '|' + myshNo + '|' + rcvnPlceGbcd + '|' + storePicDt;
      var val_input = '<input type="hidden" name="basktInf" value="' + val + '" />';
      $('#itemInfForm').append(val_input);
      // @REQ-SD-009 @2018.12.13 @황희선 @주문추가요청사항 setting
      var addObj = $(this).find('div.selected-uitm-write');   // 주문추가요청사항 영역
      if (addObj.length != undefined && addObj.length > 0) {
        var addObjMap = new Object();
        addObj.find(".selected-uitm-info-list > li").each(function () {
          var addReqnSeq = $(this).find('dd > input[name="addReqnSeq"]');
          var addReqnInsrCntn = $(this).find('dd > input[name="addReqnInsrCntn"]');
          addObjMap[addReqnSeq.val()] = addReqnInsrCntn.val();
        });
        addReqnMap[val] = addObjMap;
      }
      // 2019.01.07 딜/옵션형 - 예약상품 구매하는지 체크 - S
      var idx = 0;
      var arrLen = rsvInfArr.length;
      // 2019.01.07 딜/옵션형 - 예약상품 구매하는지 체크 - E
    })
    // @REQ-SD-009 @2018.12.13 @황희선 @주문추가요청사항 form append
    $('#itemInfForm').append($("<input type='hidden' name='addReqnMap'/>").val(JSON.stringify(addReqnMap)));
    $('#itemInfForm').find("input[name='basktInf']").each(function (index, obj) {
      basktInfArr.push($(obj).val());
    });
    $('#itemInfForm').find("input[name='ordAgreeInf']").each(function (index, obj) {
      $(obj).remove();
    });
    $(".opt-sel-box:eq(0) li.selected-uitm-wrap").each(function () {
      var obj = $(this).find('div.selected-uitm');
      var slitmNm = $(obj).find("p.name").text();
      var ordMakeYn = obj.find('p.name > input[name="ordMakeYn"]').val();
      var packOpenRtpNdmtYn = obj.find('p.name >input[name="packOpenRtpNdmtYn"]').val();
      var frgnDrctBuyYn = obj.find('p.name >input[name="frgnDrctBuyYn"]').val();
      var val = ordMakeYn + '|' + packOpenRtpNdmtYn + '|' + slitmNm + '|' + '';
      var val_input = '<input type="hidden" name="ordAgreeInf" value="' + val + '" />';
      $('#itemInfForm').append(val_input);
    })
    if ($("input[name='ordMakeYn']").val() == "Y" && $("input[name='ordMakeExcldMCsfYn']").val() == "N") {
      // 주문제작상품 안내팝업
      // 2019.02.21 주문제작상품 AND 예약상품 -> 추가 처리 로직
      // (1) 예약상품 안내팝업 (2) 주문제작상품 안내팝업
      if (rsvOptItemYn == 'Y') {   // 주문제작상품 AND 예약상품
        rsvItemNoticeInfPup("1");
        return;
      } else if ($("input[name='packOpenRtpNdmtYn']").val() == "Y") {
        // 주문제작상품 & 배달 상품일 경우 & 예약상품이 아닐 때
        // 주문 & 박스 팝업 띄우기(2020.02.24)
        ordMakeAndboxPackItemInfPup();
        return;
      } else {    // 주문제작상품 (예약X)
        ordMakeItemOrdInfPup();
        return;
      }
    } else if ($("input[name='packOpenRtpNdmtYn']").val() == "Y") {
      // 박스포장 안내팝업
      // 2019.02.21 박스포장 AND 예약상품 -> 추가 처리 로직
      // (1) 예약상품 안내팝업 (2) 박스포장 안내팝업
      if (rsvOptItemYn == 'Y') {   // 박스포장 AND 예약상품
        rsvItemNoticeInfPup("2");
        return;
      } else {    // 박스포장 (예약X)
        boxPackItemOrdInfPup();
        return;
      }
    } else if ($("input[name='frgnDrctBuyYn']").val() == "Y") {
      // 해외직구 안내팝업
      //frgnDrctBuyItemOrdInfPup();
      //return;
      if (isLogin() == 'false') {
        openLoginPopup("orderDeal", "order");
        return;
      }
    } else if (rsvOptItemYn == 'Y') {
      // 2018.12.28 딜/옵션형 상품 and 옵션상품 내 예약상품이 포함된 상품
      // 2019.02.21 호출 위치 변경
      rsvItemNoticeInfPup("0");
      return;
    } else {
      if (isLogin() == 'false') {
        openLoginPopup("orderDeal", "order");
        return;
      }
    }
    orderDeal();
  }
  else {
    $("#itemInfForm input[name='uitmInf'], #itemInfForm input[name='uitmCdInf'], #itemInfForm input[name='addCmpsInf'], #itemInfForm input[name='ordQty'], #itemInfForm input[name='uitmCd'], #itemInfForm input[name='baseCmpsInf'], #itemInfForm input[name='asctSlitmUitmInf']").remove();
    var result = checkItem(false);
    if (!result) {
      return;
    }
    setSelectItem();
    if (!isEmpty($("input[name='sdlvcVenMinStlmAmt']").val()) && calcSellPrc() < Number($("input[name='sdlvcVenMinStlmAmt']").val())) {
      alert($("textarea[name='sdlvcVenItemPtcCntn']").text());
      if ($("input[name='sdlvcVenBsicAmtBuyPossYn']").val() == "N") {
        return;
      }
    }
    if ($("input[name='ordMakeYn']").val() == "Y" && $("input[name='ordMakeExcldMCsfYn']").val() == "N") {
      // 주문제작상품 안내팝업
      if ($("input[name='packOpenRtpNdmtYn']").val() == "Y") {
        ordMakeAndboxPackItemInfPup();
        return;
      } // 주문제작 & 박스포장(2020.02.25)
      else {
        ordMakeItemOrdInfPup();
      }
      return;
    } else if ($("input[name='packOpenRtpNdmtYn']").val() == "Y") {
      // 박스포장 안내팝업
      boxPackItemOrdInfPup();
      return;
    } else if ($("input[name='frgnDrctBuyYn']").val() == "Y") {
      // 해외직구 안내팝업
      //frgnDrctBuyItemOrdInfPup();
      //return;
      if (isLogin() == 'false') {
        openLoginPopup("buyDirectFormSubmit", "order");
        return;
      }
    } else {
      if (isLogin() == 'false') {
        openLoginPopup("buyDirectFormSubmit", "order");
        return;
      }
    }
    buyDirectFormSubmit();
  }
}
function orderDeal() {
  if (basktInfArr != null && basktInfArr != '') {
    var formParams = $('#itemInfForm').serialize();
    $.ajax({
      type: "post"
      , url: "/front/oda/buyDirect.thd?optYn=Y&&PageName=ProductDetail&PageBanner=Order&basktInf=" + basktInfArr.join("&basktInf=")
      , dataType: "json"
      , data: formParams
      , success: function (data) {
        if (!isEmpty(data.errorMessages)) {
          alert(data.errorMessages.join("\n"));
        } else {
          $("#orderForm").html("");
          $("#orderForm").append("<input type='hidden' name='sectId' value='" + 137920 + "'/>");
          //선물하기
          $("#orderForm").append("<input type='hidden' name='isGiftYn' value='" + $("#itemInfForm input[name=isGiftYn]").val() + "'/>");
          for (var i = 0; i < basktInfArr.length; i++) {
            $("#orderForm").append("<input type='hidden' name='basktInf' value='" + basktInfArr[i] + "'/>");
          }
          if (data.ODAOrderParamVO.addReqnMap != null) {
            $("#orderForm").append("<input type='hidden' name='addReqnMap' value='" + data.ODAOrderParamVO.addReqnMap + "'/>");
          }
          $("#orderForm").attr("action", serverHostUrl + "/oda/order.thd?optYn=Y");
          $("#orderForm").submit();
        }
      }
      , error: function (data) {
      }
    });
  }
}
function buyDirectFormSubmit() {
  $("#itemInfForm").ajaxSubmit({
    url: "/front/oda/buyDirect.thd?PageName=ProductDetail&PageBanner=Order"
    , dataType: "json"
    , success: function (data) {
      if (!isEmpty(data.errorMessages)) {
        alert(data.errorMessages.join("\n"));
      } else {
        $("#itemInfForm").attr("action", serverHostUrl + "/oda/order.thd");
        $("#itemInfForm").attr("target", "");
        $("#itemInfForm").submit();
      }
    }
    , error: function (xhr, status, error) {
    }
  });
}
function ordMakeItemOrdInfPup() {
  var msg = '<div class="perform-msg2">';
  msg += '<p>해당상품은 <strong>주문제작 상품</strong>으로 <strong>취소/교환/반품이 불가</strong>합니다.</p>';
  msg += '<small>(상품 하자 시 제외)</small>';
  msg += '</div>';
  $("#popover-layer-directbuy div.pop-content").html(msg);
  popover_open(this, '#popover-layer-directbuy');
}
function boxPackItemOrdInfPup() {
  var msg = '<div class="perform-msg2">';
  msg += '<p>해당상품은 <strong>박스포장 상품</strong>으로 <strong>개봉으로 인한 상품가치 하락 시</strong><br>';
  msg += '<strong>교환/반품이 불가</strong>합니다.<br>';
  msg += '<small>(단, 상품가치 하락 없이 재화 등의 내용을 확인하기 위해 포장 등을 훼손한 경우는 제외)</small>';
  msg += '</div>';
  $("#popover-layer-directbuy div.pop-content").html(msg);
  popover_open(this, '#popover-layer-directbuy');
}
function ordMakeAndboxPackItemInfPup() {
  var msg = '<div class="perform-msg2">';
  msg += '<p> 해당 상품은 <strong> 주문제작 및 박스포장 상품</strong>으로 <br><strong>취소/교환/반품이 불가</strong>합니다.<br>';
  msg += '<small>(단, 상품가치 하락 없이 재화 등의 내용을 확인하기 위해 포장 등을 훼손한 경우 및 제품 하자 시 제외)</small>';
  msg += '</div>';
  $("#popover-layer-directbuy div.pop-content").html(msg);
  popover_open(this, '#popover-layer-directbuy');
}
function frgnDrctBuyItemOrdInfPup() {
  var msg = '<div class="perform-msg2">';
  msg += '<p>해당상품은 <strong>해외배송 상품</strong>으로 <strong>취소/교환/반품이 불가</strong>합니다.</p>';
  msg += '<small>(단, 상품 하자 시 제외)</small>';
  msg += '</div>';
  $("#popover-layer-directbuy div.pop-content").html(msg);
  popover_open(this, '#popover-layer-directbuy');
}
function rsvItemNoticeInfPup(itemType) {
  // 2018.12.28 딜/옵션형 예약판매문구 표시
  // 2019.02.21 예약상품이고 / 주문제작 혹은 박스포장 상품일 경우, 추가로 안내 팝업이 뜨도록 설정함
  if (itemType == '1') {
    // 2019.02.21 예약상품이고 / 주문제작 상품일 경우, 주문안내 팝업이 뜨도록 설정함
    $("#rsvConfirmBtn").attr("onclick", "ordMakeItemOrdInfPup();");
  } else if (itemType == '2') {
    // 2019.02.21 예약상품이고 / 박스포장 상품일 경우, 주문안내 팝업이 뜨도록 설정함
    $("#rsvConfirmBtn").attr("onclick", "boxPackItemOrdInfPup();");
  }
  popover_open(this, '#popover-layer-reservations');
}
function zzim(slitmCd, e, obj) {
  var uitmCombYn = $("input[name='uitmCombYn']").val();
  var uitmChocPossYn = $("input[name='uitmChocPossYn']").val();
  var chkedSlitmCdUitmCds = slitmCd;
  var sectId = $("input[name='sectId']").val();
  var uitmSeqArr = new Array();
  var selectedCount = 0;
  $("select.selectUitmAttrType option:selected").each(function () {
    if (!isEmpty($(this).val())) {
      selectedCount++;
    }
  });
  if (uitmCombYn == "Y" && uitmChocPossYn == "Y") {

    $.ajax({
      type: "post"
      , url: "/front/pda/getUitmCdByUitmSeqArr.thd?PageName=ProductDetail&PageBanner=Wish&slitmCd=" + slitmCd + "&uitmSeqArr=" + uitmSeqArr.join("&uitmSeqArr=")
      , dataType: "json"
      , success: function (data) {
        if (data.uitmCd) {
          chkedSlitmCdUitmCds += ":" + data.uitmCd;
        }
        sltdItemDone(chkedSlitmCdUitmCds, sectId, e, obj);
      }
      , error: function (data) {
      }
    });
  } else {
    /*
    if($("select.selectUitmCd").length > 0 && isEmpty($("select.selectUitmCd").val())) {
        alert("주문선택사항을 먼저 선택하시기 바랍니다.");
        $(this).find("option:first").attr("selected", true);
        return;
    }
    */
    var uitmCd = $("select.selectUitmCd").find("option:selected").val();
    if (uitmCd != '' && !(typeof uitmCd == 'undefined')) {
      chkedSlitmCdUitmCds += ":" + uitmCd;
    }
    sltdItemDone(chkedSlitmCdUitmCds, sectId, e, obj);
  }
}
function sendSMS() {
  var today = new Date();
  if (isLogin() == 'false') {
    alert("로그인후 이용하실 수 있습니다.");
    openLoginPopup();
  } else {
    if (!confirm("SMS를 전송하시겠습니까?")) {
      return;
    }
    $("#itemSMSForm").ajaxSubmit({
      url: "/front/pda/sendSMS.thd"
      , dataType: "json"
      , success: function (data) {
        if (!isEmpty(data.errorMessages)) {
          alert(data.errorMessages.join("\n"));
        } else {
          if (data.save) {
            alert("문자메시지를 발송했습니다. 전송 시간은 최대 2분까지 소요됩니다.");
          }
          //else {
          //  alert("입력하신 핸드폰 번호로 이미 상품 URL을 전송하였습니다.");
          //}
        }
      }
      , error: function (xhr, status, error) {
      }
    });
  }
}
function initItemRishpAlrimPup() {
  var uitemPopContent = $("#popover-layer-stock div.pop-content");
  $("#itemRishpAlrimForm").find("input[name='slitmCd']").val("");
  $("#itemRishpAlrimForm").find("input[name='uitmCd']").val("");
  $("#itemRishpAlrimForm").find("input[name='rcvHopeTermGbcd']").val("");
  $(uitemPopContent).find("h3.pop-title.itemRishpAlrimOption").html("");
  $(uitemPopContent).find("div.btn-wrap.itemRishpAlrimOption").hide();
  $(uitemPopContent).find("input[name='checkEmail']").prop("checked", true);
  $(uitemPopContent).find("input[name='checkSMS']").prop("checked", true);
  // 이메일
  $(uitemPopContent).find("input[name='emailId']").val("");
  $(uitemPopContent).find("input[name='emailAddress']").val("");
  $(uitemPopContent).find("select[name='selEmailAddr']").val("");
  $(uitemPopContent).find("select[name='selEmailAddr']").selectric("refresh");
  // 핸드폰
  $(uitemPopContent).find("select[name='smsHpIdntNo']").val("010");
  $(uitemPopContent).find("select[name='smsHpIdntNo']").selectric("refresh");
  $(uitemPopContent).find("input[name='smsHpIntmNo']").val("");
  $(uitemPopContent).find("input[name='smsHpBckNo']").val("");
  // 수신희망기간
  var rcvHopeTermInfo = $(uitemPopContent).find("input[name='rcvHopeTermGbcd']").parent().parent();
  $(rcvHopeTermInfo).find("label").each(function () {
    $(this).removeClass("checked");
  });
  // 수신희망시간
  $(uitemPopContent).find("select[name='rcvHopeStrtTime']").val("1");
  $(uitemPopContent).find("select[name='rcvHopeStrtTime']").selectric("refresh");
  // 시작시간 이후로 자동 설정
  $(uitemPopContent).find("select[name='rcvHopeEndTime'] option").each(function () {
    $(this).remove();
  });
  $(uitemPopContent).find("select[name='rcvHopeStrtTime'] option").each(function () {
    if (parseInt($(this).val()) > 1) {
      var strOption = "";
      if (2 == parseInt($(this).val())) {
        strOption = '<option value="' + $(this).val() + '" selected>' + $(this).text() + '</option>';
      }
      else {
        strOption = '<option value="' + $(this).val() + '">' + $(this).text() + '</option>';
      }
      $(uitemPopContent).find("select[name='rcvHopeEndTime']").append(strOption);
    }
  });
  $(uitemPopContent).find("select[name='rcvHopeEndTime']").selectric("refresh");
}
var removeObjct;
function showItemRishpAlrimPup(obj, slitmCd, uitmCd, slitmNm, uitmTotNm, rishpAlrimYn) {
  if (rishpAlrimYn == "Y") {
    alert("이미 재입고알림이 신청된 상품입니다.");
    return false;
  }
  if (isLogin() == 'false') {
    alert("로그인후 이용하실 수 있습니다.");
    openLoginPopup();
    return false;
  }
  initItemRishpAlrimPup();
  var uitemPopContent = $("#popover-layer-stock div.pop-content");
  $("#itemRishpAlrimForm").find("input[name='slitmCd']").val(slitmCd);
  $("#itemRishpAlrimForm").find("input[name='uitmCd']").val(uitmCd);
  $(uitemPopContent).find("h3.pop-title.itemRishpAlrimOption").html(slitmNm);
  if (uitmTotNm != "없음") {
    $(uitemPopContent).find("div.btn-wrap.itemRishpAlrimOption").html("");
    var optionInfo = '<label class="checked"><input type="radio" name="radio01" checked>' + uitmTotNm + '</label>';
    $(uitemPopContent).find("div.btn-wrap.itemRishpAlrimOption").html(optionInfo);
    $(uitemPopContent).find("div.btn-wrap.itemRishpAlrimOption").show();
  }
  // 삭제대상 재입고 알림 버튼
  removeObjct = obj;
  popover_open(obj, '#popover-layer-stock');
}
function registItemRishpAlrimDtl() {
  var uitemPopContent = $("#popover-layer-stock div.pop-content");
  var emailAdr = "";
  var emailRcvHopeYn = "";
  var smsRcvHopeYn = "";
  var smsHpIdntNo = "";
  var smsHpIntmNo = "";
  var smsHpBckNo = "";
  var tel = "";
  var rcvHopeTermGbcd = "";
  var rcvHopeStrtTime = "";
  var rcvHopeEndTime = "";
  if ($(uitemPopContent).find("input[name='checkEmail']").prop("checked")) {
    emailRcvHopeYn = "Y";
    emailAdr = $(uitemPopContent).find("input[name='emailId']").val() + "@" + $(uitemPopContent).find("input[name='emailAddress']").val();
  }
  else {
    emailRcvHopeYn = "N";
    emailAdr = "";
  }
  if ($(uitemPopContent).find("input[name='checkSMS']").prop("checked")) {
    smsRcvHopeYn = "Y";
    smsHpIdntNo = $(uitemPopContent).find("select[name='smsHpIdntNo']").val();
    smsHpIntmNo = $(uitemPopContent).find("input[name='smsHpIntmNo']").val();
    smsHpBckNo = $(uitemPopContent).find("input[name='smsHpBckNo']").val();
    tel = smsHpIdntNo + smsHpIntmNo + smsHpBckNo;
  }
  else {
    smsRcvHopeYn = "N";
    smsHpIdntNo = "";
    smsHpIntmNo = "";
    smsHpBckNo = "";
  }
  var rcvHopeTermInfo = $(uitemPopContent).find("input[name='rcvHopeTermGbcd']").parent().parent();
  rcvHopeTermGbcd = $(rcvHopeTermInfo).find("label.checked").find("input[name='rcvHopeTermGbcd']").attr("value");
  rcvHopeStrtTime = $(uitemPopContent).find("select[name='rcvHopeStrtTime']").val();
  rcvHopeEndTime = $(uitemPopContent).find("select[name='rcvHopeEndTime']").val();
  if (emailRcvHopeYn == "Y") {
    $("input[name='emailRcvHopeYn']").val("Y");
  } else {
    $("input[name='emailRcvHopeYn']").val("N");
    $("input[name='emailAdr']").val('');
  }
  if ($("#sms").is(":checked")) {
    $("input[name='smsRcvHopeYn']").val("Y");
  } else {
    $("input[name='smsRcvHopeYn']").val("N");
  }
  if (emailRcvHopeYn != "Y" && smsRcvHopeYn != "Y") {
    alert("이메일 또는 핸드폰 중 하나 이상은 선택해야 합니다.");
    return;
  }
  //이메일 입력여부 확인
  if ((emailRcvHopeYn == "Y" && emailAdr == "") || (emailRcvHopeYn == "Y" && !isValidEmail(emailAdr))) {
    alert("이메일주소를 정확히 입력해주세요");
    $(uitemPopContent).find("input[name='emailId']").focus();
    return;
  }
  //전화번호 입력여부 확인
  if ((smsRcvHopeYn == "Y" && (smsHpIntmNo == "" || smsHpBckNo == "")) || (smsRcvHopeYn == "Y" && !isValidPhone(tel))) {
    alert("번호를 정확히 입력해주세요");
    $(uitemPopContent).find("input[name='smsHpIntmNo']").focus();
    return;
  }
  if (rcvHopeTermGbcd == undefined || rcvHopeTermGbcd == "undefined" || rcvHopeTermGbcd == "") {
    alert("수신희망기간을 확인해주세요");
    $(uitemPopContent).find("input[name='rcvHopeTermGbcd']").focus();
    return;
  }
  //시작일, 종료일 확인
  if (parseInt(rcvHopeStrtTime) > parseInt(rcvHopeEndTime)) {
    alert("수신희망시간을 확인해주세요");
    $(uitemPopContent).find("input[name='rcvHopeStrtTime']").focus();
    return;
  }
  // form 정보 셋팅
  $("#itemRishpAlrimForm").find("input[name='emailAdr']").val(emailAdr);
  $("#itemRishpAlrimForm").find("input[name='emailRcvHopeYn']").val(emailRcvHopeYn);
  $("#itemRishpAlrimForm").find("input[name='smsRcvHopeYn']").val(smsRcvHopeYn);
  $("#itemRishpAlrimForm").find("input[name='smsHpIdntNo']").val(smsHpIdntNo);
  $("#itemRishpAlrimForm").find("input[name='smsHpIntmNo']").val(smsHpIntmNo);
  $("#itemRishpAlrimForm").find("input[name='smsHpBckNo']").val(smsHpBckNo);
  $("#itemRishpAlrimForm").find("input[name='rcvHopeTermGbcd']").val(rcvHopeTermGbcd);
  $("#itemRishpAlrimForm").find("input[name='rcvHopeStrtTime']").val(rcvHopeStrtTime);
  $("#itemRishpAlrimForm").find("input[name='rcvHopeEndTime']").val(rcvHopeEndTime);
  if ($('body').data("loadingIndicator")) {
    $indicator_loader = $('body').data("loadingIndicator");
    $indicator_loader.show();
  }
  else {
    $('body').loadingIndicator();
  }
  $.ajax({
    type: "post"
    , url: "/front/mpc/registItemRishpAlrimDtl.thd"
    , dataType: "json"
    , data: $("#itemRishpAlrimForm").serialize()
    , async: false
    , success: function (data) {
      $indicator_loader = $('body').data("loadingIndicator");
      $indicator_loader.hide();
      if (!isEmpty(data.errorMessages)) {
        alert(data.errorMessages.join("\n"));
      } else {
        // 레이어 팝업 닫기
        $backtarget = $("#popover-layer-stock").data('backtarget');
        if ($backtarget) {
          $($backtarget).focus();
        }
        // $("#popover-layer-stock").bPopup().close();
        $.fancybox.close();
        // 재입고 알림 버튼 삭제
        $(removeObjct).remove();
        alert("재입고 서비스 신청이 완료되었습니다.");
      }
    }
    , error: function (xhr, status, error) {
      $indicator_loader = $('body').data("loadingIndicator");
      $indicator_loader.hide();
      alert("시스템 오류입니다. 다시 시도해주세요.");
    }
  });
}
function changeEmail(obj) {
  var uitemPopContent = $("#popover-layer-stock div.pop-content");
  if ($(obj).val() == "") {
    // $("#itemRishpAlrimForm").find("input[name='emailAddress']").attr("readonly",false);
    $(uitemPopContent).find("input[name='emailAddress']").val("");
  } else {
    // $("#itemRishpAlrimForm").find("input[name='emailAddress']").attr("readonly",true);
    $(uitemPopContent).find("input[name='emailAddress']").val($(obj).val());
  }
}
function changeRcvTime(obj) {
  var uitemPopContent = $("#popover-layer-stock div.pop-content");
  var selectVal = parseInt($(uitemPopContent).find("select[name='rcvHopeEndTime']").val());
  // 시작시간 이후로 자동 설정
  $(uitemPopContent).find("select[name='rcvHopeEndTime'] option").each(function () {
    $(this).remove();
  });
  $(uitemPopContent).find("select[name='rcvHopeStrtTime'] option").each(function () {
    if (parseInt($(this).val()) >= parseInt($(obj).val())) {
      var strOption = "";
      if (selectVal == parseInt($(this).val())) {
        strOption = '<option value="' + $(this).val() + '" selected>' + $(this).text() + '</option>';
      }
      else {
        strOption = '<option value="' + $(this).val() + '">' + $(this).text() + '</option>';
      }
      $(uitemPopContent).find("select[name='rcvHopeEndTime']").append(strOption);
    }
  });
  $(uitemPopContent).find("select[name='rcvHopeEndTime']").selectric("refresh");
}
function isValidPhone(data) {
  var format = /^01[0-9]{1}[0-9]{3,4}[0-9]{4}$/;
  if (data.search(format) == -1) {
    return false;
  }
  return true;
}
function isValidEmail(data) {
  var format = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
  if (data.search(format) == -1) {
    return false;
  }
  return true;
}
function storePicPop(slitmCd, venCd, ven2Cd, exstVenStockYn) {
  var result = checkItem(false);
  if (!result) {
    return;
  }
  popover_iframe_fullsize("/front/pda/storePicPup.thd?&PageName=ProductDetail&PageBanner=StoreOrder&slitmCd=" + slitmCd + "&venCd=" + venCd + "&ven2Cd=" + ven2Cd + "&exstVenStockYn=" + exstVenStockYn);
}
function storePicTicketPop(slitmCd, venCd, ven2Cd, exstVenStockYn, storpiupVlidEndDtm) {
  var result = checkItem(false);
  if (!result) {
    return;
  }
  popover_iframe_fullsize("/front/pda/storePicTicketPup.thd?PageName=ProductDetail&PageBanner=StoreOrder&slitmCd=" + slitmCd + "&storpiupVlidEndDtm=" + storpiupVlidEndDtm + "&venCd=" + venCd + "&ven2Cd=" + ven2Cd + "&exstVenStockYn=" + exstVenStockYn);
}
function mobileBuy(obj) {
  /*
  var result = checkItem();
  if(!result) {
      return;
  }
  */
  // 20200210 qr코드호출
  setQrCode("qrCode80", 80);
  popover_open(obj, '#popover-layer-mobile');
}
function initOption() {
  if ($("select.selectUitmCd").not(".hidden").length > 0) {
    $("select.selectUitmCd").find("option:first").attr("selected", true);
  }
  $("label.selectUitmAttrType.checked").each(function () {
    $(this).removeClass("checked");
  });
  // 기본구성품 초기화
  if ($("select.selectBaseCmpsType > option").length > 0) {
    $("select.selectBaseCmpsType").find("option:first").attr("selected", true);
  }
  // 추가구성품 초기화
  if ($("select.selectAddCmpsType > option").length > 0) {
    $("select.selectAddCmpsType").find("option:first").attr("selected", true);
  }
  if ($.trim($("#productInfoDetailDl").html()) == "") {
    $("#productInfoDetailDl").hide();
  }
}
function initHash() {
  if (location.hash == "#tab1-02") {
    $("#tab1-01").removeClass("active");
    $("#tab1-02").addClass("active");
    $("ul.nav-tabs2 > li:eq(0)").removeClass("active");
    $("ul.nav-tabs2 > li:eq(1)").addClass("active");
    location.hash = '#tab1-02';
  }
}
function adiUpdateIFrame(height) {
  var iframe = document.getElementById('iframe_main_adidas');
  iframe.setAttribute('height', height);
  this.scrollTo(0, 0);
}
function checkNumber(obj) {
  //방향키 인경우는 제외 처리(글쓰다가 앞으로 다시 안가지는 경우때문)
  if (event.keyCode >= 37 && event.keyCode <= 40)
    return;
  val = obj.value;
  re = /[^0-9]/gi;   //0~9 까지만 허용
  val = val.replace(re, "");
  v_cnt = val.indexOf(".", val.indexOf(".") + 1);
  if (v_cnt > -1) {
    val = val.substring(0, v_cnt) + val.substring(v_cnt + 1, val.length);
  }
  obj.value = val;
}
/* 더드림딜 - 상품 기본정보 셀렉트 박스(2016.04.14) */
function setItemsInfo(obj) {
  //alert('setItemsInfo> selected itemCode값 : '+ obj);
  //$("#itemsInfo").val(obj);
  $.ajax({
    type: "post"
    , url: "/front/pda/itemOptBasicInfo.thd"
    , data: {
      'slitmCd': obj
    }
    , dataType: "html"
    , async: false
    , success: function (html) {
      $(".itemOptBasicInfo").html("");
      $(".itemOptBasicInfo").html(html);
    }
  });
}
/* 더드림딜 - 상품 기본정보 셀렉트 박스(2016.04.14) */
function linkSelectOn() {
  // 셀렉트박스
  var $select = $('.link-selectBox');
  if ($select.length < 1) return;
  // current click
  $('.link-select-current a , .link-select-current .link-select-arrow').bind('click', function () {
    if ($(this).closest('.link-selectBox').find('.link-select-list').is(':visible')) {
      $(this).closest('.link-selectBox').find('.link-select-list').hide();
    } else {
      $(this).closest('.link-selectBox').find('.link-select-list').show();
    }
    return false;
  });
  // list click
  $('.link-select-list a').bind('click', function () {
    $(this).parent('li').addClass('current').siblings().removeClass('current');
    $(this).closest('.link-selectBox').find('.link-select-current a').html($(this).html());
    $(this).closest('.link-selectBox').find('.link-select-list').hide();
    var itemCode = $(this).siblings('p').html();
    $(this).closest('.link-selectBox').find('.link-select-current p').html(itemCode);
    var idx = $(this).parent().index();
    var src = "https://image.thehyundai.com/static/" // "https://image.thehyundai.com/static/"
      + itemCode.substring(8, 9) + "/"
      + itemCode.substring(7, 8) + "/"
      + itemCode.substring(6, 7) + "/"
      + itemCode.substring(4, 6) + "/"
      + itemCode.substring(2, 4) + "/"
      + itemCode + "_0_300.jpg";
    $('.link-img-box').children("img").attr('src', src);
    var price = $('.point-price').html();
    $('.link-discount').html(price);
    // TODO
    setItemsInfo(itemCode);
    return false;
  });
  // 바닥 클릭시 셀렉트 닫기
  $(document).bind('mousedown', function (e) {
    if (!$('.link-selectBox .link-select-list').is(e.target) && $('.link-selectBox .link-select-list').has(e.target).length === 0
      && $('.link-selectBox .link-select-list').is(':visible')
      && !$('.link-selectBox .link-select-current a').is(e.target) && $('.link-selectBox .link-select-current a').has(e.target).length === 0) {
      $('.link-selectBox .link-select-list').hide();
    }
  });
}
function getCurrentDate() { // 2019.05.13 현재날짜
  var currentDate = new Date();
  var year = currentDate.getFullYear();
  var month = currentDate.getMonth();
  var day = currentDate.getDate();
  var hour = currentDate.getHours();
  var min = currentDate.getMinutes();
  var sec = currentDate.getSeconds();
  month += 1;
  if (month < 10) {
    month = "0" + month;
  }
  if (day < 10) {
    day = "0" + day;
  }
  if (hour < 10) {
    hour = "0" + hour;
  }
  if (min < 10) {
    min = "0" + min;
  }
  if (sec < 10) {
    sec = "0" + sec;
  }
  return year + "" + month + "" + day + "" + hour + "" + min + "" + sec;
}
// 20171110 딥파인더 - S
//@20171110 스크롤시 dom 추가
// 변수 추가
var dfPage = 2; // 초기에 48개(2벌. 1벌에 24개) 그려서, dfPage=2 로 초기화
var detectIdScroll;
var jsonResultScroll; // 결과값
var actionFlag = 'Y'; // 스크롤 액션 수행하는지 확인하는 flag
var limitScroll = 200; // json 리스트 최대값 (200일수도 있고 / 적을수도 있고)
var sameCntScroll = 0;
function deepfinderScroll() {
  window.deepinder._$squareList.bind('scrollBottom', function () { // 20171116 - S
    // 스크롤시 dom 넣어야 하는부분
    var dfIdx = 0;
    var domSizeScroll = 0;
    // 1) 200개 list 가져왔으면
    if (jsonResultScroll != null && typeof jsonResultScroll != 'undefined' && actionFlag == 'Y') {
      // 처음 스크롤 내렸을 경우만 limitScroll, sameCntScroll 계산
      if (dfPage == 2) {
      }
      dfIdx = (dfPage * 24) + sameCntScroll - 1;
      domSizeScroll = dfIdx + 24; // domSize 셋팅
      // 불러온 이미지 다 그린 경우, 스크롤액션 시에도 dom 그리지 않도록 설정필요. flag 값 셋팅
      if (limitScroll < domSizeScroll) {
        domSizeScroll = limitScroll;
        actionFlag = 'N';
      }
      // for문으로 dom 그리기
      for (; dfIdx < domSizeScroll; dfIdx++) {
        //id  = 상품코드
        //image_url     = 이미지 URL
        //slitmNm       = 상품명
        //produc_url    = 링크
        //slitmPrc      = 가격
        var slitmCd = JSON.stringify(jsonResultScroll.data["recommend"][dfIdx]["id"]);
        var slitmNm = JSON.stringify(jsonResultScroll.data["recommend"][dfIdx]["context"]["name"]);
        var imgUrl = JSON.stringify(jsonResultScroll.data["recommend"][dfIdx]["imageInfo"]["content"]);
        var slitmPrc = JSON.stringify(jsonResultScroll.data["recommend"][dfIdx]["context"]["price"]["value"]);
        var dispSlitmNm;
        var item = '';
        slitmCd = slitmCd.replace(/\"/gi, '');
        dispSlitmNm = slitmNm.replace(/\"/gi, '');
        item += '<a class="item" href="/front/pda/itemPtc.thd?slitmCd=' + slitmCd + '&bfp=DEEP_FINDER_DF" title=' + slitmNm + ' onclick="javascript:generateDummyLog(null, \'Module\',\'PRODUCT_DETAIL\',\'CateId\',\'R5\', null, null);">';
        item += '   <span class="in">';
        item += '       <img src=' + imgUrl + ' onerror="noImage(this, \'https://image.thehyundai.com/hdmall/images/pd/no_image_300x300.jpg\')" alt="상품이미지" \/>';
        item += '       <span class="price">' + addComma(slitmPrc) + '<span>원</span></span>';
        item += '       <span class="over">';
        item += '           <span class="name"><span class="el">' + dispSlitmNm + '</span></span>';
        item += '       </span>';
        item += '   </span>';
        item += '</a>';
        // @ dom을 넣을곳(스크롤 안쪽 영역에 넣기)
        $('.square-list-wrap .square-list .inner').append(item);
      }
      window.deepinder.domSet(); //디자인 세팅
      dfPage++; // 이미지 1벌(24개) 그린 후, dfPage 증가
    } // null체크 if문
  }); // 2071116 - E
}
// 20171110 딥파인더 - E
// 20170816 딥파인더 - S
function deepfinderSet() {
  // 20170816 딥파인더 셋 : document ready 에 호출
  window.deepinder = new DeepfinderControl(); // 딥파인더 인스턴스 생성
  var dfUrl = location.href; // 딥파인더에서 왔는지 확인
  var dfPrefix = ''; // 딥파인더 prefix
  // url 딥파인더 여부 확인
  if (dfUrl.indexOf("bfp=DEEP_FINDER_") >= 0) { // 딥파인더추천 > 상품상세로 인입한 경우 prefix 설정
    dfPrefix = 'DF_';
  }
  // 딥파인더버튼 클릭 (상단에 한개, 하단에 한개)
  $('.btn-deepfinder, .deepfinder-banner-wrap .btn').bind('click', function () {
    // 2019.05.13 시간 체크
    var ct = getCurrentDate();
    if (ct >= '20190518130000' && ct <= '20190518150059') { // 시작,종료
      alert("금일 13:00~15:00까지 딥파인더 이용이 불가능합니다. 나중에 다시 이용해주세요.");
      return false;
    }
    var clickBtn = $(this).attr("class");
    var clickBtnType = clickBtn.match('btn-deepfinder'); // 상단일 경우,
    if (clickBtnType == null || typeof clickBtnType == 'undefined' || clickBtnType != 'btn-deepfinder') {
      clickBtnType = 'PRODUCT_DETAIL_BOTTOM';
    } else if (clickBtnType == 'btn-deepfinder') {
      clickBtnType = 'PRODUCT_DETAIL_TOP';
    }
    // 딥파인더 더미로그 (인입)
    generateDummyLog(null, 'Module', 'DEEP_FINDER', 'CateId', 'R5', null, (dfPrefix + clickBtnType));
    // 딥파인더 더미로그 셋팅 (닫기)
    $(".layer-deepfinder-closebtn").attr("onclick", "generateDummyLog(null, 'Module', 'DEEP_FINDER', 'Event=Close&CateId', 'R5', 'null', 'DF_" + clickBtnType + "');");
    if (!window.deepinder.load) { // 원 리스트 로드됬는지 조건값 (첫 클릭만 모션주기 위한 조건)
      /*
      @ 수정내용 (20171110)
          - 순서
          1. 로딩시작 : loadingBar.start();
          2. 데이터 가져오는 중
          3. 로딩스탑 : loadingBar.stop();
          4. 24개 dom 추가 ( 24개가 한벌, dom을 넣을곳(스크롤 안쪽 영역에 넣기위해) : deepinder._$squareList.find('.mCSB_container') )
          5. dom 디자인 세팅 : deepinder.domSet();
          6. ajax 로드 완료후 구분값 적용 : deepinder.load = true;
  */
      loadingBar.start(); //로딩시작
      // 20.06.19.김민규, omnious로 상품 추천 솔루션 교체, 데이터 파라미터 셋팅
      var data = {};
      data["id"] = "40A0635803";
      data["showContext"] = true;
      data["limit"] = 200;
      data["showImageInfo"] = true;
      // 2) 데이터 가져오기- S
      $.ajax({
        url: 'https://api.omnious.com/lens/v1.1/recommend',
        dataType: "json",
        data: JSON.stringify(data),
        beforeSend: function (xhr) { xhr.setRequestHeader('x-api-key', 'o6iURHJQXVAhSqIktxu7zdmBF1CGYLl2sjD4rK5P'); xhr.setRequestHeader('Content-Type', 'application/json'); },
        type: "POST",
        success: function (data) {
          var jsonObj = JSON.parse(JSON.stringify(data));
          if (jsonObj.status == "ok") {
            searchProduct(window.deepinder, clickBtnType); // 딥파인더 호출
          } else {
            //결과 없는 경우
            loadingBar.stop();
            alert("딥파인더 검색결과가 없습니다.");
          }
        }
        , error: function (data) {
          loadingBar.stop();
          alert("현재 딥파인더 이용이 불가능합니다. 나중에 다시 이용해주세요.");
          return;
        }
      });
      // 2) 데이터 가져오기- E
    }
    return false;
  });
}
function searchProduct(deepinder, clickBtnType) {
  var ctgrId = "536870928";
  var orgImgUrl = "https://image.thehyundai.com/static/0/8/5/63/A0/40A0635803_0_600.jpg";
  if (ctgrId == null || typeof ctgrId == 'undefined' || ctgrId == '') { // ctgrId 정보 없으면 아래 수행 안함
    return false;
  }
  var limit = 80;
  var domSize = 47; //원본 이미지 제외 (24개가 1벌 -> 총 2벌)
  var jsonResult;
  // 20.06.19.김민규, omnious로 상품 추천 솔루션 교체, 데이터 파라미터 셋팅
  var data = {};
  data["id"] = "40A0635803";
  data["showContext"] = true;
  data["limit"] = 80;
  data["showImageInfo"] = true;
  $.ajax({
    url: 'https://api.omnious.com/lens/v1.1/recommend',
    dataType: "json",
    data: JSON.stringify(data),
    beforeSend: function (xhr) { xhr.setRequestHeader('x-api-key', 'o6iURHJQXVAhSqIktxu7zdmBF1CGYLl2sjD4rK5P'); xhr.setRequestHeader('Content-Type', 'application/json'); },
    type: "POST",
    success: function (data) {
      loadingBar.stop();
      jsonResult = JSON.parse(JSON.stringify(data));
      if (jsonResult.data.recommend.length < limit) {
        // limit = jsonResult.data.recommend.length;
        domSize = jsonResult.data.recommend.length;
      }
      //초기화 이후에 리스트 뿌림
      var $html = '';
      var i = 0;
      // 원본 이미지 셋팅 - S (20171110)
      $html = '';
      $html += '<a class="item" href="/front/pda/itemPtc.thd?slitmCd=40A0635803&bfp=DEEP_FINDER_OR" title="크리니크 치크팝 (3.5g)">';
      $html += '  <span class="in">';
      $html += '      <img src="https://image.thehyundai.com/static/0/8/5/63/A0/40A0635803_0_600.jpg" onerror="noImage(this, \'https://image.thehyundai.com/hdmall/images/pd/no_image_300x300.jpg\')" alt="상품이미지" \/>';
      $html += '      <span class="price">33,000<span>원</span></span>';
      $html += '      <span class="over">';
      $html += '          <span class="name"><span class="el">크리니크 치크팝 (3.5g)</span></span>';
      $html += '      </span>';
      $html += '  </span>';
      $html += '</a>';
      // @ dom을 넣을곳(스크롤 안쪽 영역에 넣기) - 20171116 - S
      $('.square-list-wrap .square-list .inner').append($html);
      domSize += i; // domSize 셋팅
      for (; i < domSize; i++) {
        //id  = 상품코드
        //image_url     = 이미지 URL
        //slitmNm       = 상품명
        //slitmLnk      = 링크
        //slitmPrc      = 가격
        var slitmCd = JSON.stringify(jsonResult.data["recommend"][i]["id"]);
        var slitmLnk = JSON.stringify(jsonResult.data["recommend"][i]["url"]);
        var slitmNm = JSON.stringify(jsonResult.data["recommend"][i]["context"]["name"]);
        var imgUrl = JSON.stringify(jsonResult.data["recommend"][i]["imageInfo"]["content"]);
        var slitmPrc = JSON.stringify(jsonResult.data["recommend"][i]["context"]["price"]["value"]);
        var dispSlitmNm;
        $html = '';
        slitmCd = slitmCd.replace(/\"/gi, '');
        dispSlitmNm = slitmNm.replace(/\"/gi, '');
        // 2018. 06. 10 https 변경
        imgUrl = imgUrl.replace("http://", "https://");
        $html += '<a class="item" href="/front/pda/itemPtc.thd?slitmCd=' + slitmCd + '&bfp=DEEP_FINDER_DF" title=' + slitmNm + ' onclick="javascript:generateDummyLog(null, \'Module\',\'PRODUCT_DETAIL\',\'CateId\',\'R5\', null, null);">';
        $html += '  <span class="in">';
        $html += '      <img src=' + imgUrl + ' onerror="noImage(this, \'https://image.thehyundai.com/hdmall/images/pd/no_image_300x300.jpg\')" alt="상품이미지" \/>';
        $html += '      <span class="price">' + addComma(slitmPrc) + '<span>원</span></span>';
        $html += '      <span class="over">';
        $html += '          <span class="name"><span class="el">' + dispSlitmNm + '</span></span>';
        $html += '      </span>';
        $html += '  </span>';
        $html += '</a>';
        // @ dom을 넣을곳(스크롤 안쪽 영역에 넣기)
        $('.square-list-wrap .square-list .inner').append($html);
      }
      loadingBar.stop(); // 로딩 stop
      deepinder.domSet(); // dom 세팅
      window.deepinder.load = true; // 6.ajax 로드 완료후 구분값 으로 true 주기
    }
  });
  // 3) 두번째 데이터 호출 - S
  data["limit"] = 200;
  $.ajax({
    url: 'https://api.omnious.com/lens/v1.1/recommend',
    dataType: "json",
    data: JSON.stringify(data),
    beforeSend: function (xhr) { xhr.setRequestHeader('x-api-key', 'o6iURHJQXVAhSqIktxu7zdmBF1CGYLl2sjD4rK5P'); xhr.setRequestHeader('Content-Type', 'application/json'); },
    type: "POST",
    success: function (data) {
      jsonResultScroll = JSON.parse(JSON.stringify(data));
    } // success -end
  });
  // 3) 두번째 데이터 호출 - E
}
function addComma(data) {
  return Number(data).toLocaleString('en');
}
function buyDirectGift() {
  $("#itemInfForm").find("input[name='isGiftYn']").val("Y");
  var _n_logging_image = new Image();
  _n_logging_image.src = "/front/dpl/dummyLogParam.thd?PageName=ProductDetail&PageBanner=Gift2";
  if ($(".selected-uitm").length > 2) { /*21.01.05.김민규, 선물하기 속성 하나만 선택 가능(THD-4338)*/
    alert("1개의 속성만 선물하기가 가능합니다");
    return false;
  }
  buyDirect();
}
// 20170816 딥파인더 - E
$(document).ready(function () {
  // 2018.05.23 소스취약점 관련 : c:out 처리 추가
  // 와이즈로그 수집위해 빈 URL 호출
  $.ajax({
    type: "post"
    , url: "/front/dpl/dummyLogParam.thd?slitmCd=" + "40A0635803"
      + "&bfp=" + ""
      + "&CateId=" + "R5"
      + "&Module=PRODUCT_DETAIL"
    , dataType: "json"
    , success: function (data) {
    }
  });
  linkSelectOn();
  deepfinderSet(); // 20170816 딥파인더 셋
  // 200개 카운트 ajax가 완료시점에 스크롤시 dom 추가
  deepfinderScroll(); // 20171110 스크롤시 dom 추가
});
// (JIRA : DEP01WORK-14658 GA 구축 프로젝트)
var _gaPageTitle = '상품상세>크리니크 치크팝 (3.5g)';
// 상품상세 페이지 조회 GA 전자상거래 전송
// (JIRA : DEP01WORK-21241) : MA 솔루션 도입 - 로그 수집 함수 호출
$(document).ready(function () {
  sendEcommerceSet('Detail', 'detail', '');
  sendGroobee();
});
// GA 전자상거래 전송
function sendEcommerceSet(eventAction, productAction, buttonName) {
  var dimension = new Object();
  var actionList = new Object();
  var Products = new Array();
  var E_step = productAction;
  // 장바구니, 주문 버튼 클릭
  if (productAction == 'add' || productAction == 'checkout') {
    // 주문 버튼 클릭시 비로그인 상태 체크
    if (isLogin() == 'false' && productAction == 'checkout') {
      return;
    }
    // 상품 유효성 체크
    var result = true;
    if ($("input[name=trndHSsaleTagYn]").val() == "Y" || $("input[name=trndHExclTagExpsYn]").val() == "Y") {
      result = checkItemDeal(true);
      if (!result) {
        return;
      }
    } else {
      result = checkItem(true);
      if (!result) {
        return;
      }
    }
  }
  dimension.category = 'Ecommerce';
  dimension.action = eventAction;
  // 구매하기 버튼 클릭시
  if (eventAction == 'Checkout') {
    dimension.label = '상품상세';
  }
  // 상품상세 페이지 조회시
  if (eventAction == 'Detail') {
    dimension.nonInteraction = true;
  }
  // 상품정보 설정
  Products = setProducts(eventAction);
  actionList.action = productAction;
  actionList.currency = 'KRW';
  // 구매 버튼 클릭
  if (eventAction == 'Checkout') {
    actionList.step = 1;
  }
  EcommerceSet(E_step, Products, actionList, dimension);
  // 장바구니, 주문 버튼 클릭시 GA 이벤트 전송
  if (productAction == 'add' || productAction == 'checkout') {
    GA_Event('PC_상품상세', buttonName, '크리니크 치크팝 (3.5g)')
  }
}
// 상품 정보 설정
function setProducts(eventAction) {
  var Products = new Array();
  var trndHSsaleTagYn = 'N';
  var trndHExclTagExpsYn = 'N';
  if (eventAction == 'Detail') {
    var Product = new Object();
    Product.id = '40A0635803';
    Product.name = '크리니크 치크팝 (3.5g)';
    Product.brand = '크리니크';
    Product.category = '화장품/BRAND SHOP (노출)/크리니크/메이크업/블러셔';
    Product.dimension37 = '002265';
    Product.dimension38 = '137920';
    Product.dimension39 = '004504_무역센터점';
    Product.dimension40 = '수입화장품(1001)';
    Products.push(Product);
  } else {
    if (trndHSsaleTagYn == 'Y' || trndHExclTagExpsYn == 'Y') {
      $('.opt-sel-box .selected-uitm-wrap').each(function () {
        var Product = new Object();
        Product.id = $(this).find('input[name=slitmCd]').val();
        Product.name = $(this).find('input[name=optNm]').val();
        Product.brand = $(this).find('input[name=brndNm]').val();
        Product.category = '화장품/BRAND SHOP (노출)/크리니크/메이크업/블러셔';
        Product.dimension37 = $(this).find('input[name=brndCd]').val();
        Product.dimension38 = '137920';
        Product.dimension39 = $(this).find('input[name=venCd]').val() + "_" + $(this).find('input[name=dptsNm]').val();
        Product.dimension40 = $(this).find('input[name=rsptMdNm]').val() + "(" + $(this).find('input[name=rsptMdCd]').val() + ")";
        // 주문서 페이지로 이동시
        if (eventAction == 'Checkout') {
          Product.variant = $(this).find('input[name=optNm]').val();
          Product.quantity = Number($(this).find('input[name=ordQty]').val());
        }
        Products.push(Product);
      });
    } else {
      $('.opt-sel-box .selected-uitm-wrap').each(function () {
        var Product = new Object();
        Product.id = '40A0635803';
        Product.name = '크리니크 치크팝 (3.5g)';
        Product.brand = '크리니크';
        Product.category = '화장품/BRAND SHOP (노출)/크리니크/메이크업/블러셔';
        Product.dimension37 = '002265';
        Product.dimension38 = '137920';
        Product.dimension39 = '004504_무역센터점';
        Product.dimension40 = '수입화장품(1001)';
        // 주문서 페이지로 이동시
        if (eventAction == 'Checkout') {
          Product.variant = $(this).find('input[name=optNm]').val();
          Product.quantity = Number($(this).find('input[name=ordQty]').val());
        }
        Products.push(Product);
      });
    }
  }
  return Products;
}
// 상품 수량 계산
function calcOrdQty() {
  var ordQty = 0;
  $(".opt-sel-box input[name=ordQty]").each(function (index) {
    ordQty += Number($(".opt-sel-box input[name=ordQty]").eq(index).val());
  });
  return ordQty;
}
// (JIRA : DEP01WORK-21241) : MA 솔루션 도입 - 로그 수집 함수
function sendGroobee() {
  var groobeeGoods = new Array();
  var groobeeItem = new Object();
  groobeeItem.name = "크리니크 치크팝 (3.5g)";
  groobeeItem.code = "40A0635803";
  groobeeItem.amt = "33000";
  groobeeItem.prc = "33000";
  groobeeItem.salePrc = "33000";
  groobeeItem.img = "https://image.thehyundai.com/static/0/8/5/63/A0/40A0635803_0_480.jpg"; // 상품 썸네일 url";
  groobeeItem.status = "";
  if ("N" == "Y" && "N" == "Y") {
    groobeeItem.status = "SS";
  } else if ("false" == "Y") {
    groobeeItem.status = "SS";
  }
  groobeeItem.cat = "R5010305";
  groobeeItem.cateNm = "블러셔/하이라이터/컨투어";
  groobeeItem.catL = "R5";
  groobeeItem.cateLNm = "이미용";
  groobeeItem.catM = "R501";
  groobeeItem.cateMNm = "화장품/프레그넌스";
  groobeeItem.catS = "R50103";
  groobeeItem.cateSNm = "메이크업(여성용)";
  groobeeItem.catD = "R5010305";
  groobeeItem.cateDNm = "블러셔/하이라이터/컨투어";
  groobeeItem.brand = "002265";
  groobeeItem.brandNm = "크리니크";
  groobeeGoods.push(groobeeItem);
  groobee("VG", { goods: groobeeGoods });
}
