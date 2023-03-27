package com.hyundai.higher.mapper.makeup;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

	public ReservVO reserv(@Param("rimg") String rimg, @Param("rdate") String rdate, @Param("mid") String mid);

	public int CountDate(String rdate);

}
