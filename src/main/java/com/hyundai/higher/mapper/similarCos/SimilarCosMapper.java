package com.hyundai.higher.mapper.similarCos;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.product.ProductDTO;
import com.hyundai.higher.domain.similarCos.SimilarCosDTO;

/**
 * @since : 2023. 3. 24.
 * @FileName: SimilarCosMapper.java
 * @author : 신수진
 * @설명 : 데이터베이스 mybatis Interface
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 24.     신수진      DateBase Member Mapper Interface
 *     </pre>
 */
@Mapper
public interface SimilarCosMapper {

	// 성분 유사템 추천
	public List<SimilarCosDTO> cntList(String pcode, int dept1no, int dept2no, List<String> list);

	// 상품명 검색 조회 AJAX
	public List<ProductDTO> searchProdList(String keyword);
	
	// 상품 검색 조회
	public List<ProductDTO> selectProdList(String keyword);
	
	// 인기 상품 목록
	public List<ProductDTO> selectBestProdList();
	
}
