package com.hyundai.higher.mapper.makeup;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.hyundai.higher.domain.makeup.BlushVO;
import com.hyundai.higher.domain.makeup.FoundationVO;
import com.hyundai.higher.domain.makeup.LipVO;
import com.hyundai.higher.domain.makeup.ReservVO;
import com.hyundai.higher.domain.makeup.ResultProdVO;
import com.hyundai.higher.domain.makeup.ResultVO;

public class MypageMapperTest implements MypageMapper {

	@Override
	public List<ReservVO> getReservdone(String mid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservVO> getReservReady(String mid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservVO> noResult(String mid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservVO> yesResult(String mid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LipVO getLipResult(String optname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlushVO getBlushResult(String optname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FoundationVO getFaceResult(String optname, String pcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LipVO> SimLip(String optcolor, String pcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BlushVO> SimBlush(String optcolor, String pcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FoundationVO> SimFace(String optcolor, String pcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservVO getReservInfo(String rid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultVO getResultInfo(String rid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultProdVO resultProdInfo(String pcode) {
		// TODO Auto-generated method stub
		return null;
	}

}
