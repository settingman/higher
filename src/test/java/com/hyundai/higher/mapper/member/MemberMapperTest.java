package com.hyundai.higher.mapper.member;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.higher.domain.member.Member;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(MockitoJUnitRunner.class)
@Transactional
class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void testFindById() {
        String memberId = "testMemberId";
        Member member = memberMapper.findById(memberId);
        assertThat(member).isNotNull();
    }

    @Test
    void testSave() {
        Member member = new Member();
        memberMapper.save(member);
        Member savedMember = memberMapper.findById(member.getMId());
        assertThat(savedMember).isNotNull();
    }

    @Test
    void testFindByNameBirth() {
        String mName = "testMemberName";
        String mBirth = "testMemberBirth";
        Member member = memberMapper.findByNameBirth(mName, mBirth);
        assertThat(member).isNotNull();
    }

    @Test
    void testFindByNameId() {
        String mName = "testMemberName";
        String mId = "testMemberId";
        Member member = memberMapper.findByNameId(mName, mId);
        assertThat(member).isNotNull();
    }

    @Test
    void testUpdatePassword() {
        String mId = "testMemberId";
        String encryptPassword = "testEncryptedPassword";
        memberMapper.updatePassword(mId, encryptPassword);
        Member updatedMember = memberMapper.findById(mId);
        assertThat(updatedMember.getMPassword()).isEqualTo(encryptPassword);
    }

    @Test
    void testFindMileage() {
        String mId = "testMemberId";
        Integer mileage = memberMapper.findMileage(mId);
        assertThat(mileage).isNotNull();
    }

    @Test
    void testUpdateMileage() {
    	String mId = "testMemberId";
    	Integer mileage = memberMapper.findMileage(mId);
        
        Integer newMileage = 100;
        memberMapper.updateMileage(mId, newMileage);
        Member updatedMember = memberMapper.findById(mId);
        
        assertThat(mileage).isEqualTo(newMileage);
    }

    @Test
    void testFindMember() {
        List<Member> memberList = memberMapper.findMember();
        assertThat(memberList).isNotNull();
    }
}
