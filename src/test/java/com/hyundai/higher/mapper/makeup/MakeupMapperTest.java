package com.hyundai.higher.mapper.makeup;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyundai.higher.TestConfig;
import com.hyundai.higher.domain.makeup.BlushVO;
import com.hyundai.higher.domain.makeup.FoundationVO;
import com.hyundai.higher.domain.makeup.LipVO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes= {TestConfig.class})
public class MakeupMapperTest {
	
	@Autowired
	private MakeupMapper mapper;
	
	@Test
	public void pickLip() {
		List<LipVO> lipList = new ArrayList<>();
		lipList = mapper.PickLip("red");
		log.info(lipList);
	}
	
	@Test
	public void pickBlush() {
		List<BlushVO> blushlist = new ArrayList<>();
		blushlist = mapper.PickBlush("coral");
		log.info(blushlist);
	}
	
	@Test
	public void pickFoundation() {
		List<FoundationVO> founlist = new ArrayList<>();
		founlist = mapper.PickFoundation("warmbeige");
		log.info(founlist);
	}

}
