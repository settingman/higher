function setProdInfo(id){
	var pcode = $('#'+id).find('.myPcode').val();
	var oid = $('#'+id).find('.myOid').val();
	var imgUrl = $('#'+id).find('.myProd-img img').attr("src");
	var odate =  $('#'+id).find('.odate i').text();
	var bname = $('#'+id).find('.bname').text();
	var pname = $('#'+id).find('.pname').text();
	var price = $('#'+id).find('.price i').text();

	$('.pimg img').attr('src', imgUrl);
	$('#select-brand').html(bname);
	$('.ptit').html(pname);
	$('#select-price em').text(price);
	$('#select-odate i').text(odate);
	
	$('#pcode').val(pcode);
	$('#oid').val(oid);

	$('#my_product').css('display', 'none');
	$('#reviewText').css('display', 'none');
	$('#select-prod').css('display', 'block');
	
	console.log(oid);
}

$(function(){
	$('.star_rating input[type=radio]').on("click", function(){
		var rate = $(this).val();
		
		$('#rrate').val(rate);
	});
	
	$('.writeBtn').on('click', function(){
		$('#reviewForm').submit();
	});
});