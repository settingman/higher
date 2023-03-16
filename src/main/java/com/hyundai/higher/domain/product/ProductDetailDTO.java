package com.hyundai.higher.domain.product;

import java.util.List;

import lombok.Data;

@Data
public class ProductDetailDTO {
	
	private ProductDTO productDTO;
	private List<ImgDTO> thumbImgList;
	private List<ImgDTO> detailImgList;
	
}
