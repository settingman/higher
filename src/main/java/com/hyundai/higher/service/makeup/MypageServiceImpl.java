package com.hyundai.higher.service.makeup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.makeup.BlushVO;
import com.hyundai.higher.domain.makeup.FoundationVO;
import com.hyundai.higher.domain.makeup.LipVO;
import com.hyundai.higher.domain.makeup.ReservVO;
import com.hyundai.higher.domain.makeup.ResultVO;
import com.hyundai.higher.mapper.makeup.MypageMapper;

@Service
public class MypageServiceImpl implements MypageService{
	
	@Autowired
	private MypageMapper mapper;

	@Override
	public List<ReservVO> getReservdone(String mid) {
		return mapper.getReservdone(mid);
	}

	@Override
	public List<ReservVO> getReservReady(String mid) {
		return mapper.getReservReady(mid);
	}

	@Override
	public ReservVO getReservInfo(String rid) {
		return mapper.getReservInfo(rid);
	}

	@Override
	public LipVO getLipResult(String optname) {
		return mapper.getLipResult(optname);
	}

	@Override
	public BlushVO getBlushResult(String optname) {
		return mapper.getBlushResult(optname);
	}

	@Override
	public FoundationVO getFaceResult(String optname, String pcode) {
		return mapper.getFaceResult(optname, pcode);
	}

	@Override
	public ResultVO getResultInfo(String rid) {
		return mapper.getResultInfo(rid);
	}

}
