package com.hyundai.higher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * @since : 2023. 2. 24.
 * @FileName: ThehandsomeApplication.java
 * @author : 박성환
 * @설명 : 스프링부트 어플리케이션
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 2. 24.     박성환      최초생성
 * 2023. 2. 27.     박성환      테스트
 *     </pre>
 */

//SecurityAutoConfiguration = 시큐리티 임시 종료
@SpringBootApplication
@EnableWebSocket
public class HigherApplication {

	public static void main(String[] args) {
		SpringApplication.run(HigherApplication.class, args);
		
	}

}
