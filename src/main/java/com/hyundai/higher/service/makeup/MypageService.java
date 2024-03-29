package com.hyundai.higher.service.makeup;

import java.util.List;

import com.hyundai.higher.domain.makeup.BlushVO;
import com.hyundai.higher.domain.makeup.FoundationVO;
import com.hyundai.higher.domain.makeup.LipVO;
import com.hyundai.higher.domain.makeup.ReservVO;
import com.hyundai.higher.domain.makeup.ResultProdVO;
import com.hyundai.higher.domain.makeup.ResultVO;

/**
 * @since : 2023. 4. 1.
 * @FileName: MypageService.java
 * @author : 이세아
 * @설명 : @마이페이지 용 서비스들
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 1.     이세아      	create
 * 2023. 4. 1.     이세아      	마이페이지에 예약 목록 나오기 service
 * 2023. 4. 2.     이세아      	마이페이지 예약 결과
 * 2023. 4. 8.	   이세아 		마이페이지 예약 상태 별 구분
 *     </pre>
 */
public interface MypageService {

	// 상담 후
	public List<ReservVO> getReservdone(String mid);

	// 예약만 된 상태
	public List<ReservVO> getReservReady(String mid);

	// 입장하기 코드
	public List<ReservVO> noResult(String mid);

	// 결과확인 코드
	public List<ReservVO> yesResult(String mid);

	// 결과 -> 결과값 립
	public LipVO getLipResult(String optname, String pcode);

	// 결과 -> 결과값 블러쉬
	public BlushVO getBlushResult(String optname, String pcode);

	// 결과 -> 결과값 페이스
	public FoundationVO getFaceResult(String optname, String pcode);

	// 유사제품 -> 립
	public List<LipVO> SimLip(String optcolor, String pcode);

	// 유사제품 -> 블러쉬
	public List<BlushVO> SimBlush(String optcolor, String pcode);

	// 유사제품 -> 파운데이션
	public List<FoundationVO> SimFace(String optcolor, String pcode);

	// 결과 -> 상담값
	public ReservVO getReservInfo(String rid);

	// 결과 -> 결과값
	public ResultVO getResultInfo(String rid);
	
	// 비교함 담기 위한 상품 상세
	public ResultProdVO resultProdInfo(String pcode);

}
