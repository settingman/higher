package com.hyundai.higher.kakao.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @since   : 2023. 4. 8.
 * @FileName: Trans.java
 * @author  : 박성환
 * @설명    : 카카오톡 API 요청 바디 파라미터

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 8.     박성환      	최초 생성
 * </pre>
 */
public class Trans {

	public static String default_msg_param = "" + "template_object={\n" + "        \"object_type\": \"feed\",\n"
			+ "        \"content\": {\n" + "            \"title\": \"피드 메시지\",\n"
			+ "            \"description\": \"피드 메시지 기본 템플릿\",\n"
			+ "            \"image_url\": \"http://api1-kage.kakao.com/dn/cerDB5/ZSb2iRugKx/M4nuZxX823tnK1Mk5yVcv0/kakaolink40_original.png\",\n"
			+ "            \"link\": {\n" + "                \"web_url\": \"http://daum.net\",\n"
			+ "                \"mobile_web_url\": \"http://dev.kakao.com\"\n" + "            }\n" + "        },\n"
			+ "        \"social\": {\n" + "            \"like_count\": 100,\n" + "            \"comment_count\": 200\n"
			+ "        },\n" + "        \"button_title\": \"바로 확인\"\n" + "    }" + "";

	public static String default_friend_data = "receiver_uuids=%5B%22mKmcq5-unK2bt4a1gLeBsYm8iaWdpZ2klaLJ%22%2C%22mKGUo5urk6qGt4Kyg7qCtYKzn6efp56vmPA%22%2C%22mKGUrJupn6-bt4WzhrWBtoG5gKyUrJStnKvD%22%5D&request_url=https%3A%2F%2Flocalhost%3A8443&template_id=92317";

	
	public static String test = "receiver_uuid=\"[\"mKGUo5urk6qGt4Kyg7qCtYKzn6efp56vmPA\",\"mKGUrJupn6-bt4WzhrWBtoG5gKyUrJStnKvD\",\"mKmcq5-unK2bt4a1gLeBsYm8iaWdpZ2klaLJ\"]\",request_url=https://localhost:8443/test4";
	
	public static String token(String rtn, JsonParser parser) {
		JsonElement element = parser.parse(rtn);
		return element.getAsJsonObject().get("access_token").getAsString();

	}

}
