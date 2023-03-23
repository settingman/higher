package com.hyundai.higher.service.skinMBTI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.higher.domain.skinMBTI.SkinMBTIDTO;
import com.hyundai.higher.mapper.skinMBTI.SkinMBTIMapper;

@Service
public class SkinMBTIServiceImpl implements SkinMBTIService {

	@Autowired
	private SkinMBTIMapper mapper;
	
	@Override
	public SkinMBTIDTO selectSkinMBTI(String stype) {

		return mapper.selectMBTI(stype);
	}

}
