package com.hyundai.higher.mapper.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hyundai.higher.domain.member.Member;

/**
 * @since : 2023. 2. 24.
 * @FileName: MemberMapper.java
 * @author : 박성환
 * @설명 : 데이터베이스 mybatis Interface
 * 
 *     <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 2. 24.     박성환      DateBase Member Mapper Interface
 *     </pre>
 */
@Mapper
public interface MemberMapper {

	// 회원 아이디를 이용하여 회원 조회
	Member findById(String mId);

	// 회원 저장
	void save(Member member);

	// 이름과 생일로 아이디 찾기
	Member findByNameBirth(@Param("mName") String mName, @Param("mBirth") String mBirth);

	// 이름과 아이디로 비밀번호 찾기
	Member findByNameId(@Param("mName") String mName, @Param("mId") String mId);

	// 비밀번호 변경
	void updatePassword(@Param("mId") String mId, @Param("encryptPassword") String encryptPassword);
	
	// 회원 마일리지 조회
	Integer findMileage(@Param("mId") String mId);
	
	// 회원 마일리지 업데이트
	void updateMileage(@Param("mId") String mId, @Param("MMILEAGE") Integer MMILEAGE);
	
	// 회원 목록 조회
	List<Member> findMember();

}
