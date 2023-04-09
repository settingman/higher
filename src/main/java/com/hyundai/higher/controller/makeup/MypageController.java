package com.hyundai.higher.controller.makeup;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
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
import com.hyundai.higher.domain.makeup.MbtiVO;
import com.hyundai.higher.domain.makeup.ReservVO;
import com.hyundai.higher.domain.makeup.ResultVO;
import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;
import com.hyundai.higher.mapper.beauty.BeautyMapper;
import com.hyundai.higher.service.makeup.MakeupService;
import com.hyundai.higher.service.makeup.MypageService;
import com.hyundai.higher.service.skinMBTI.SkinMBTIService;

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
 * 2023. 4. 4.	   이세아		  마이페이지 결과 - 세부 결과 페이지
 * 2023. 4. 8.	   이세아		  마이페이지 - makeon 결과 세부 분리
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
	
	@Autowired
	private SkinMBTIService mbtiser;

	@Value("${com.demo.upload.path}")
	private String uploadPath;
	
	// 메이크업 예약 수정
	@GetMapping("/reserv_detail")
	public void reserv_read(@RequestParam("rid") String rid, Model model) {
		log.info("===== 예약 내역 확인 =====");
		
		ReservVO info = service.getReservInfo(rid);
		String rrid = info.getMid();
		Member mem = new Member();
		mem = makeup.MemInfo(rrid);
		
		Profile pro = new Profile();
		pro = mapper.findProfile(rrid, rid);
		
		model.addAttribute("mem", mem);
		model.addAttribute("pro", pro);
		model.addAttribute("info", info);
	}
	
	// 주문내역
	@GetMapping("/order")
	public void orderlist(Principal principal, Model model) {
		log.info("===== 회원 주문내역 마이페이지 =====");
	}
	
	// 예약만 완료
	@GetMapping("/reserv_ing")
	public void reserv_ing(Principal principal, Model model) {
		log.info("==== 예약만 받은 페이지 ====");
		List<ReservVO> ready = service.getReservReady(principal.getName());
		model.addAttribute("ready", ready);
	}
	// 입장 가능
	@GetMapping("/reserv_ready")
	public void reserv_ready(Principal principal, Model model) {
		log.info("==== 입장 가능 페이지 ====");
		List<ReservVO> nores = service.noResult(principal.getName());
		model.addAttribute("nores", nores);
	}
	// 예약 완료
	@GetMapping("/reserv_done")
	public void reserv_done(Principal principal, Model model) {
		log.info("==== 예약 완료 페이지 ====");
		List<ReservVO> yesres = service.yesResult(principal.getName());
		model.addAttribute("yesres", yesres);
	}
	
	// 회원 skinmbti 결과 확인
	@GetMapping("/skinmbti")
	public void skinmbti(Principal principal, Model model) {
		log.info("===== 피부 진단 결과 열람창 =====");
		
		MbtiVO info = new MbtiVO();
		info = makeup.findmbti(principal.getName());
		
		String score = info.getMbti_scores();
		List<String> scoresList = Arrays.asList(score.split(","));
		
		SkinMBTIDTO mbti = new SkinMBTIDTO();
		mbti = mbtiser.selectSkinMBTI(info.getMbti());
		
		model.addAttribute("info", info);
		model.addAttribute("mbti", mbti);
		model.addAttribute("score1", scoresList.get(0));
		model.addAttribute("score2", scoresList.get(1));
		model.addAttribute("score3", scoresList.get(2));
		model.addAttribute("score4", scoresList.get(3));
	}

	// 마이페이지 메인 / 예약 페이지
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

	// 결과 상세
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
	
	// 결과
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
