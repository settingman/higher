function setProdInfo(id){
	var pcode = $('#'+id).find('.myPcode').val();
	var imgUrl = $('#'+id).find('.myProd-img img').attr("src");
	var odate =  $('#'+id).find('.odate i').text();
	var bname = $('#'+id).find('.bname').text();
	var pname = $('#'+id).find('.pname').text();
	var price = $('#'+id).find('.price').text();
	

	$('.pimg img').attr('src', imgUrl);
	$('#select-brand').html(bname);
	$('.ptit').html(pname);
	$('#select-price em').text(price);
	$('#select-odate i').text(odate);
	$('#pcode').val(pcode);

	$('#reviewText').css('display', 'none');
	$('#select-prod').css('display', 'block');
}