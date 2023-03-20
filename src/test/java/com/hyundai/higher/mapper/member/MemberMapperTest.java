package com.hyundai.higher.mapper.member;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.domain.member.MemberRole;

import lombok.extern.slf4j.Slf4j;

/**
 * @since : 2023. 3. 02.
 * @FileName: MemberMapperTest.java
 * @author : 박성환
 * @설명 : MemberMapper Test
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 03. 02.     박성환      최초생성
 *     </pre>
 */
@Slf4j
@SpringBootTest
@Transactional
public class MemberMapperTest {

	@Autowired
	MemberMapper memberMapper;
	
	@Test
	public void testSave() {
		Member member = new Member("test.com", "1234", "테스트", "01029270550", "test@naver.com", new Date(199912123),
				MemberRole.USER);

		memberMapper.save(member);
	}

}
