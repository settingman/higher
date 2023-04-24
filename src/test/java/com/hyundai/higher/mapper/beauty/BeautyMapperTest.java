package com.hyundai.higher.mapper.beauty;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyundai.higher.domain.beauty.Profile;
import com.hyundai.higher.webRTC.dto.ReservationDTO;

@RunWith(MockitoJUnitRunner.class)
class BeautyMapperTest {

    @Autowired
    private BeautyMapper beautyMapper;

    @Test
    void testFindReservation() {
        List<ReservationDTO> reservationList = beautyMapper.findReservation();
        assertThat(reservationList).isNotNull();
    }

    @Test
    void testFindTodayReservation() {
        List<ReservationDTO> todayReservationList = beautyMapper.findTodayReservation();
        assertThat(todayReservationList).isNotNull();
    }

    @Test
    void testUpdateRoomID() {
        String mid = "testMemberId";
        String rid = "testReservationId";
        String roomID = "testRoomId";
        beautyMapper.updateRoomID(mid, rid, roomID);
        // Check if the update was successful by retrieving the updated record and checking its values
        Profile updatedProfile = beautyMapper.findProfile(mid, rid);
        assertThat(updatedProfile.getRid()).isEqualTo(roomID);
    }

    @Test
    void testFindProfile() {
        String mid = "testMemberId";
        String rid = "testReservationId";
        Profile profile = beautyMapper.findProfile(mid, rid);
        assertThat(profile).isNotNull();
    }
}
