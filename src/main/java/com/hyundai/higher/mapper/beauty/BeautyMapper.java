package com.hyundai.higher.mapper.beauty;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hyundai.higher.domain.beauty.Profile;
import com.hyundai.higher.webRTC.dto.ReservationDTO;

/**
 * @since : 2023. 3. 21.
 * @FileName: BeautyMapper.java
 * @author : 박성환
 * @설명 : 온라인 뷰티 Mapper interface
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 21.     박성환      	최초 생성
 *     </pre>
 */
@Mapper
public interface BeautyMapper {

	List<ReservationDTO> findReservation();
	
	List<ReservationDTO> findTodayReservation();

	void updateRoomID(@Param("mid") String mid, @Param("rid") String rid, @Param("roomID") String roomID);
	
	Profile findProfile(@Param("mid") String mid, @Param("rid") String rid);

}
