package com.hyundai.higher.service.makeup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.makeup.ReservVO;
import com.hyundai.higher.mapper.makeup.ReservMapper;

@Service
public class ReservServiceImpl implements ReservService {
	
	@Autowired
	private ReservMapper mapper;

	@Override
	public void reserv(ReservVO vo) {	
		mapper.reserv(vo);
	}

	@Override
	public int CountDate(String rdate) {
		return mapper.CountDate(rdate);
	}

	@Override
	public int CountTime(String rdate) {
		return mapper.CountTime(rdate);
	}

}
