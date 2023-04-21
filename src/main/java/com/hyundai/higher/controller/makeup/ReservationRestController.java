package com.hyundai.higher.controller.makeup;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 4. 21.
 * @FileName: ReservationRestController.java
 * @author : 박성환
 * @설명 : 사진 캡쳐 서버 저장
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 21.     박성환      	최초 생성
 *     </pre>
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/beauty")
@RestController
public class ReservationRestController {

	@RequestMapping(value = { "ImgSaveTest" }, method = RequestMethod.POST)
	public ModelMap ImgSaveTest(@RequestParam HashMap<Object, Object> param, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		ModelMap map = new ModelMap();

		String binaryData = request.getParameter("imgSrc");
		FileOutputStream stream = null;
		try {
			System.out.println("binary file   " + binaryData);
			if (binaryData == null || binaryData.trim().equals("")) {
				throw new Exception();
			}
			binaryData = binaryData.replaceAll("data:image/png;base64,", "");
			byte[] file = Base64.decodeBase64(binaryData);
			String fileName = UUID.randomUUID().toString();

			stream = new FileOutputStream("c:/test2/" + fileName + ".png");
			stream.write(file);
			stream.close();
			System.out.println("캡처 저장");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("에러 발생");
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
		
		
		
		

		map.addAttribute("resultMap", "");
		return map;
	}

}
