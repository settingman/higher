package com.hyundai.higher.controller.makeup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.higher.domain.beauty.Profile;
import com.hyundai.higher.domain.makeup.BlushVO;
import com.hyundai.higher.domain.makeup.FoundationVO;
import com.hyundai.higher.domain.makeup.LipVO;
import com.hyundai.higher.domain.makeup.ReservVO;
import com.hyundai.higher.domain.makeup.ResultVO;
import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.mapper.beauty.BeautyMapper;
import com.hyundai.higher.service.makeup.MakeupService;
import com.hyundai.higher.service.makeup.MypageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @since : 2023. 4. 1.
 * @FileName: MypageController.java
 * @author : 이세아
 * @설명 : @
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 1.     이세아       create
 * 2023. 4. 1.     이세아       마이페이지 controller - makeon 예약관리
 * 2023. 4. 2.     이세아       마이페이지 controller - makeon 예약결과
 *     </pre>
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
	
	@Autowired
	private BeautyMapper mapper;

	@Value("${com.demo.upload.path}")
	private String uploadPath;

	@GetMapping("/reserv")
	public void reserving(Principal principal, Model model) {
		log.info("===== 마이페이지 예약관리 =====");
		log.info(principal);
		List<ReservVO> nores = service.noResult(principal.getName());
		List<ReservVO> yesres = service.yesResult(principal.getName());
		List<ReservVO> ready = service.getReservReady(principal.getName());
		model.addAttribute("nores", nores);
		model.addAttribute("yesres", yesres);
		model.addAttribute("ready", ready);
	}

	@GetMapping("/result2")
	public void mypageresult2(@RequestParam("rid") String rid, Model model) throws IOException {
		log.info("==== 마이페이지 예약 결과 창 ====");

		ReservVO info = service.getReservInfo(rid);
		String rrid = info.getMid();
		Member mem = new Member();
		mem = makeup.MemInfo(rrid);
		
		Profile pro = new Profile();
		pro = mapper.findProfile(rrid, rid);

		ResultVO result = service.getResultInfo(rid);

		log.info(info);
		log.info(result);
		log.info(mem.getMName());

		LipVO lipresult = new LipVO();
		BlushVO blushresult = new BlushVO();
		FoundationVO faceresult = new FoundationVO();

		log.info("입술 옵션 : " + result.getLip_opt());
		String lipopt = result.getLip_opt();
		lipresult = service.getLipResult(lipopt);
		log.info("가져온 입술 정보 : " + lipresult);

		log.info("블러쉬 옵션 : " + result.getBlush_opt());
		String blushopt = result.getBlush_opt();
		blushresult = service.getBlushResult(blushopt);
		log.info("가져온 블러쉬 정보 : " + blushresult);

		log.info("파운데이션 옵션 : " + result.getFace_opt());
		log.info("파운데이션 상품코드 : " + result.getFace_pcode());
		String faceopt = result.getFace_opt();
		String facepcode = result.getFace_pcode();
		faceresult = service.getFaceResult(faceopt, facepcode);
		log.info("가져온 파운데이션 정보 : " + faceresult);

		String simlipcolor = lipresult.getOptcolor();
		String simlippcode = lipresult.getPcode();
		List<LipVO> simlip = service.SimLip(simlipcolor, simlippcode);
		log.info(simlip);

		String simblushcolor = blushresult.getOptcolor();
		String simblushpcode = blushresult.getPcode();
		List<BlushVO> simblush = service.SimBlush(simblushcolor, simblushpcode);
		log.info(simblush);

		String simfacecolor = faceresult.getOptcolor();
		String simfacepcode = faceresult.getPcode();
		List<FoundationVO> simface = service.SimFace(simfacecolor, simfacepcode);
		log.info(simface);

		model.addAttribute("mem", mem);
		model.addAttribute("pro", pro);
		model.addAttribute("info", info);
		model.addAttribute("result", result);
		model.addAttribute("lipresult", lipresult);
		model.addAttribute("blushresult", blushresult);
		model.addAttribute("faceresult", faceresult);

		model.addAttribute("simlip", simlip);
		model.addAttribute("simblush", simblush);
		model.addAttribute("simface", simface);

	}

	@GetMapping("/result")
	public void mypageresult(@RequestParam("rid") String rid, Model model) throws IOException {
		log.info("==== 마이페이지 예약 결과 창 ====");

		ReservVO info = service.getReservInfo(rid);
		String rrid = info.getMid();
		Member mem = new Member();
		mem = makeup.MemInfo(rrid);
		
		Profile pro = new Profile();
		pro = mapper.findProfile(rrid, rid);

		ResultVO result = service.getResultInfo(rid);

		log.info(info);
		log.info(result);
		log.info(mem.getMName());

		LipVO lipresult = new LipVO();
		BlushVO blushresult = new BlushVO();
		FoundationVO faceresult = new FoundationVO();

		log.info("입술 옵션 : " + result.getLip_opt());
		String lipopt = result.getLip_opt();
		lipresult = service.getLipResult(lipopt);
		log.info("가져온 입술 정보 : " + lipresult);

		log.info("블러쉬 옵션 : " + result.getBlush_opt());
		String blushopt = result.getBlush_opt();
		blushresult = service.getBlushResult(blushopt);
		log.info("가져온 블러쉬 정보 : " + blushresult);

		log.info("파운데이션 옵션 : " + result.getFace_opt());
		log.info("파운데이션 상품코드 : " + result.getFace_pcode());
		String faceopt = result.getFace_opt();
		String facepcode = result.getFace_pcode();
		faceresult = service.getFaceResult(faceopt, facepcode);
		log.info("가져온 파운데이션 정보 : " + faceresult);

		String simlipcolor = lipresult.getOptcolor();
		String simlippcode = lipresult.getPcode();
		List<LipVO> simlip = service.SimLip(simlipcolor, simlippcode);
		log.info(simlip);

		String simblushcolor = blushresult.getOptcolor();
		String simblushpcode = blushresult.getPcode();
		List<BlushVO> simblush = service.SimBlush(simblushcolor, simblushpcode);
		log.info(simblush);

		String simfacecolor = faceresult.getOptcolor();
		String simfacepcode = faceresult.getPcode();
		List<FoundationVO> simface = service.SimFace(simfacecolor, simfacepcode);
		log.info(simface);

		model.addAttribute("mem", mem);
		model.addAttribute("pro", pro);
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
