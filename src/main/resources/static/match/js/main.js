$(document).ready(function() {


	$("#detail-btn").on("click", function() {
		$("#mbtiFrm").submit();
	});

	$("#search-input").on("focus", function() {
		$("#search-list").css('display', 'block');
		$('#search-list').html('');
	});

	$(".product_wrap").on('click', function() {
		$("#search-list").css('display', 'none');
		$('#search-list').html('');
	});

	$('#search-input').on('keyup', function() {
		let keyword = $(this).val();

		if (keyword == '') {
			$('#search-result').html('');
		} else {
			$.ajax({
				url: '/similarCosRest/checkKeyword?keyword=' + keyword,
				type: 'GET',
				success: function(r) {

					$result = r;
					let tag = '';

					if ($result.length > 0) {
						tag = '';
						for (let i = 0; i < $result.length; i++) {
							let pname = $result[i].pname;
							tag += '<li onclick="goDetail(&quot;' + $result[i].pcode + '&quot;);">';
							tag += pname.replace(keyword, '<span style="color:red;">' + keyword + '</span>');
							tag += '</li>';
							if (i == 7) break;
						}
					} else {
						$('#search-list').html('');
					}
					$('#search-list').html(tag);
				},
				error: function(e) {
					console.log(e);
				}
			});
		}

	});

	$("#search-frm").submit(function() {
		let searchKeyword = $("#search-input").val();

		$.ajax({
			url: '/similarCosRest/searchProd?searchKeyword=' + searchKeyword,
			type: 'GET',
			success: function(r) {
				
		
				$('#search-keyword').text(searchKeyword);
				$('#search_tit').css('display', 'block');
				$('.prd_term').css('display', 'none');


				let $result = r;
				let tag = '';
				if ($result.length <= 0) {
					$(".sort_list").css('display', 'none');
					$("#totalCount").text(0);

					tag += `<div style="text-align: center;">
							<div style="color: gray; font-size: 20px; margin-bottom: 20px;">찾으시는 검색 결과가 없습니다.</div>
							<p class="subtitle" style="color: gray; line-height: 24px; font-size: 16px;">
								검색어를 다시 한 번 확인해 주세요.<br>
								검색어가 두 단어 이상인 경우, 띄어쓰기를 확인해 주세요.<br>
								상품명이 기억나지 않을 땐 관련 단어를 입력해 보세요!
							</p>
						</div>`;
				} else {
					$(".sort_list").css('display', 'block');
					$("#totalCount").text($result.length);

					for (let i = 0; i < $result.length; i++) {
						tag += `<li class="`
						if (i % 3 == 0) {
							tag += `new`;
						}
						if (i != 0 && i % 5 == 0) {
							tag += `best`;
						}
						tag += `">
								<a href="` + `/match/detail?pcode=` + $result[i].pcode + `"> 
									<img src="`;
						if ($result[i].thumbImgList[0].iloc == null) {
							tag += `https://image.thehyundai.com/hdmall/images/pd/no_image_480x480.jpg`;
						} else {
							tag += $result[i].thumbImgList[0].iloc + `_600.jpg`;
						}
						tag += `" alt="` + $result[i].pname + `"> 
									<span class="hash_tag">` + $result[i].bname + `</span>
									<span class="tit">` + $result[i].pname + `</span> 
									<span class="kind">` + CommaFormat($result[i].pprice) + `<em>원</em></span> 
									<span class="rating">
										<div class="star-rating small">
											<span class="c00">
												<em class="hide">벌점 0개</em>
											</span>
										</div> 
										<em>(0)</em>
									</span> <span class="price"><button class="match-btn">매칭하기</button></span>
								</a>
								<div class="over">
									<button class="detailview">자세히보기</button>
									<button data-value="` + $result[i].pcode + `" data-cnt="1" data-price="` + $result[i].pprice + `" data-linename="` + $result[i].pname + `" class="addcart basket">장바구니 담기</button>
								</div> 
								<span class="like"><i class="hide">관심제품</i></span>
							</li>`;
					}
					$("#search-list").css('display', 'none');
				}
				$(".product_list").html(tag);
				
				$('.product_list li').hover(function() {
					$(this).find('.match-btn').css('color', '#fff').css('background-color', '#bfae90').css('transition', 'all .5s ease');
				}, function() {
					$(this).find('.match-btn').css('color', '#bfae90').css('background-color', '#fff').css('transition', 'all .5s ease');
				});
			},
			error: function(e) {
				console.log(e);
			}

		});

	});
	
	$('.product_wrap .product_list li').hover(function() {
		$(this).find('.match-btn').css('color', '#fff').css('background-color', '#bfae90').css('transition', 'all .5s ease');
	}, function() {
		$(this).find('.match-btn').css('color', '#bfae90').css('background-color', '#fff').css('transition', 'all .5s ease');
	});

});

function CommaFormat(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


function goDetail(pcode) {
	window.location.href = "/match/detail?pcode=" + pcode;
}