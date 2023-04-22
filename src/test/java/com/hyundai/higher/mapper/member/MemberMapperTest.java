package com.hyundai.higher.mapper.member;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.higher.domain.member.Member;
import com.hyundai.higher.domain.member.MemberRole;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
@Transactional
public class MemberMapperTest implements MemberMapper {
	
	
	@Autowired
	MemberMapper memberMapper;


	

	@Override
	@Test
	public Member findById(String mId) {
		Member member = memberMapper.findById("test.com");
		return null;
	}

	@Override
	@Test
	public void save(Member member) {
		
		
	}

	@Override
	@Test
	public Member findByNameBirth(String mName, String mBirth) {
		memberMapper.findByNameBirth("테스트", "950908");
		return null;
	}

	@Override
	@Test
	public Member findByNameId(String mName, String mId) {
		memberMapper.findByNameId("테스트", "test.com");
		return null;
	}

	@Override
	@Test
	public void updatePassword(String mId, String encryptPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Test
	public Integer findMileage(String mId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Test
	public void updateMileage(String mId, Integer MMILEAGE) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Test
	public List<Member> findMember() {
		// TODO Auto-generated method stub
		return null;
	}

}
