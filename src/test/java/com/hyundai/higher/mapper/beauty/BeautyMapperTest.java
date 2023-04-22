package com.hyundai.higher.mapper.beauty;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.hyundai.higher.domain.beauty.Profile;
import com.hyundai.higher.webRTC.dto.ReservationDTO;

public class BeautyMapperTest implements BeautyMapper {

	@Override
	public List<ReservationDTO> findReservation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservationDTO> findTodayReservation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRoomID(String mid, String rid, String roomID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Profile findProfile(String mid, String rid) {
		// TODO Auto-generated method stub
		return null;
	}

}
