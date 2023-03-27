package com.hyundai.higher.service.makeup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.makeup.BlushVO;
import com.hyundai.higher.domain.makeup.FoundationVO;
import com.hyundai.higher.domain.makeup.LipVO;
import com.hyundai.higher.mapper.makeup.MakeupMapper;

@Service
public class MakeupServiceImpl implements MakeupService{
	
	@Autowired
	private MakeupMapper mapper;

	@Override
	public List<LipVO> pickLip(String optcolor) {
		return mapper.PickLip(optcolor);
	}

	@Override
	public List<BlushVO> pickBlush(String optcolor) {
		return mapper.PickBlush(optcolor);
	}

	@Override
	public List<FoundationVO> pickFoundation(String optcolor) {
		return mapper.PickFoundation(optcolor);
	}

}
