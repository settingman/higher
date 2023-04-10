package com.hyundai.higher.domain.category;

import java.util.List;

import lombok.Data;

@Data
public class CategoryAllDTO {

	private List<CategoryDTO> skinDTO;
	private List<MBTIDTO> mbtiDTO;
	private List<BrandDTO> skinBrandDTO;
	
	private List<CategoryDTO> makeDTO;
	private List<BrandDTO> makeBrandDTO;
	
	private List<CategoryDTO> bodyDTO;
	private List<BrandDTO> bodyBrandDTO;
}
