package com.hyundai.higher.domain.beauty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @since   : 2023. 3. 30.
 * @FileName: Profile.java
 * @author  : 박성환
 * @설명    : 온라인 뷰티 회원 정보

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 30.     박성환      	최초 생성
 * </pre>
 */
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Profile {

	private String mid;
	private String mname;
	private String mbti;
	private String rid;
	private String rimg;
	private String age;

}
