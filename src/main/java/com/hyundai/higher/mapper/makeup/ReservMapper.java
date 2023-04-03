package com.hyundai.higher.mapper.makeup;

import org.apache.ibatis.annotations.Mapper;

import com.hyundai.higher.domain.makeup.ReservVO;

/**
 * @since : 2023. 3. 27.
 * @FileName: ReservMapper.java
 * @author : 이세아
 * @설명 : @
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 27.     이세아       create + 예약 캘린더 구현 위한 조회
 *     </pre>
 */

@Mapper
public interface ReservMapper {

	public void reserv(ReservVO vo);

	public int CountDate(String rdate);
	
	public int CountTime(String rdate);

}
