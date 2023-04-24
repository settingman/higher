package com.hyundai.higher.domain.makeup;

import java.io.Serializable;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @since   : 2023. 4. 8.
 * @FileName: UploadResultDTO.java
 * @author  : 이세아
 * @설명    : 예약 시 첨부파일 등록을 위한 DTO

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 4. 8.     이세아       create
 * </pre>
 */


@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable {
	private String fileName;
	private String uuid;
	private String folderPath;
	public String getImageURL() {
		try {
			return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "URL fail";
	}
	
	public String getThumbnailURL() {
		try {
			return URLEncoder.encode(folderPath+"/"+"s_"+uuid+"_"+fileName,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Thumb fail";
	}
}
