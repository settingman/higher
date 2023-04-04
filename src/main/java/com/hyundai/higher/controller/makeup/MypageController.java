package com.hyundai.higher.controller.makeup;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.makeup.BlushVO;
import com.hyundai.higher.domain.makeup.FoundationVO;
import com.hyundai.higher.domain.makeup.LipVO;
import com.hyundai.higher.domain.makeup.ReservVO;
import com.hyundai.higher.domain.makeup.ResultVO;
import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.service.makeup.MakeupService;
import com.hyundai.higher.service.makeup.MypageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @since   : 2023. 4. 1.
 * @FileName: MypageController.java
 * @author  : 이세아
 * @설명    : @

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 1.     이세아       create
 * 2023. 4. 1.     이세아       마이페이지 controller - makeon 예약관리
 * 2023. 4. 2.     이세아       마이페이지 controller - makeon 예약결과
 * </pre>
 */

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/mypage")
@Controller
public class MypageController {
	
	@Autowired
	private MypageService service;
	
	@Autowired
	private MakeupService makeup;
	
	
	@Value("${com.demo.upload.path}") private String uploadPath;
	 
	
	@GetMapping("/mypage_reserv")
	public void reserving(Principal principal, Model model) {
		log.info("===== 마이페이지 예약관리 =====");
		log.info(principal);
		List<ReservVO> done = service.getReservdone(principal.getName());
		List<ReservVO> ready = service.getReservReady(principal.getName());
		model.addAttribute("done", done);
		model.addAttribute("ready", ready);
	}
	
	@GetMapping("/mypage_result")
	public void mypageresult(@RequestParam("rid") String rid, Model model) {
		log.info("==== 마이페이지 예약 결과 창 ====");
		
		ReservVO info = service.getReservInfo(rid);
		String rrid = info.getMid();
		Member mem = new Member();
		mem = makeup.MemInfo(rrid);
		
		ResultVO result = service.getResultInfo(rid);
		
		log.info(info);
		log.info(result);
		log.info(mem.getMName());
		
		LipVO lipresult = new LipVO();
		BlushVO blushresult = new BlushVO();
		FoundationVO faceresult = new FoundationVO();
		
		log.info("입술 옵션 : " +  result.getLip_opt());
		String lipopt = result.getLip_opt();
		lipresult = service.getLipResult(lipopt);
		log.info("가져온 입술 정보 : " + lipresult);
	    
	    log.info("블러쉬 옵션 : " +  result.getBlush_opt());
		String blushopt = result.getBlush_opt();
		blushresult = service.getBlushResult(blushopt);
		log.info("가져온 블러쉬 정보 : " + blushresult);
		
		log.info("파운데이션 옵션 : " +  result.getFace_opt());
		log.info("파운데이션 상품코드 : " +  result.getFace_pcode());		
		String faceopt = result.getFace_opt();
		String facepcode = result.getFace_pcode();
		faceresult = service.getFaceResult(faceopt, facepcode);
		log.info("가져온 파운데이션 정보 : " + faceresult);
		
		LipVO simlip = new LipVO();
		BlushVO simblush = new BlushVO();
		FoundationVO simface = new FoundationVO();
		
		String simlipcolor = lipresult.getOptcolor();
		String simlippcode = lipresult.getPcode();
		simlip = service.SimLip(simlipcolor, simlippcode);
		log.info(simlip);

		String simblushcolor = blushresult.getOptcolor();
		String simblushpcode = blushresult.getPcode();
		simblush = service.SimBlush(simblushcolor, simblushpcode);
		log.info(simblush);

		String simfacecolor = faceresult.getOptcolor();
		String simfacepcode = faceresult.getPcode();
		simface = service.SimFace(simfacecolor, simfacepcode);
		log.info(simface);
		
		model.addAttribute("mem", mem);
		model.addAttribute("info", info);
		model.addAttribute("result", result);
		model.addAttribute("lipresult", lipresult);
		model.addAttribute("blushresult", blushresult);
		model.addAttribute("faceresult", faceresult);	
		model.addAttribute("simlip", simlip);
		model.addAttribute("simblush", simblush);
		model.addAttribute("simface", simface);
	}
	
}
