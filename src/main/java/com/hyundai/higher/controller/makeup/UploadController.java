package com.hyundai.higher.controller.makeup;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hyundai.higher.domain.makeup.UploadResultDTO;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@Log4j2
@RestController
@RequestMapping("/reserv")
public class UploadController {

	@Value("${com.demo.upload.path}")
	private String uploadPath;

	/*
	 * @Value("${cloud.aws.credentials.accessKey}") private String accessKey;
	 * 
	 * @Value("${cloud.aws.credentials.secretKey}") private String secretKey;
	 * 
	 * @Value("${cloud.aws.s3.bucket}") private String bucketName;
	 */
	
	private String accessKey = "AKIARGO2DAHDK5ATCHPN";
	private String secretKey = "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";
	private String bucketName = "hbeauty.bucket";

	private AmazonS3 s3client = AmazonS3ClientBuilder.standard()
			.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
			 .withRegion(Regions.AP_NORTHEAST_2).build();


	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String folderPath = str.replace("/", File.separator);
		File uploadPathFolder = new File(uploadPath, folderPath);
		if (uploadPathFolder.exists() == false) {
			uploadPathFolder.mkdirs();
		}
		log.info(folderPath);
		return folderPath;
	}

	@PostMapping("/upload.do")
	public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles) {
	    List<UploadResultDTO> resultDTOList = new ArrayList<>();

	    for (MultipartFile i : uploadFiles) {
	        if (i.getContentType().startsWith("image") == false) {
	            log.warn("this file is not image type");
	            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	        }

	        String originalName = i.getOriginalFilename();
	        log.info("fileName :" + originalName);
	        String folderPath = makeFolder();
	        log.info(folderPath);
	        String uuid = UUID.randomUUID().toString();
	        String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + originalName;
	        log.info(saveName);
			Path savePath = Paths.get(saveName);
	        try {
				i.transferTo(savePath);

	            PutObjectRequest putRequest = new PutObjectRequest(bucketName, saveName, i.getInputStream(), new ObjectMetadata());
	            s3client.putObject(putRequest);
	            
	            String thumnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_"
						+ originalName;
	            File thumbailFile = new File(thumnailSaveName);
				Thumbnailator.createThumbnail(savePath.toFile(), thumbailFile, 300, 400);
	            PutObjectRequest putThumbnailRequest = new PutObjectRequest(bucketName, thumnailSaveName, thumbailFile);
	            s3client.putObject(putThumbnailRequest);

	            resultDTOList.add(new UploadResultDTO(originalName, uuid, folderPath));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    log.info(resultDTOList);
	    return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
	}
	
	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(String fileName) {
		ResponseEntity<byte[]> result = null;
		try {
			String srcFileName = URLDecoder.decode(fileName, "UTF-8");
			File file = new File(uploadPath + File.separator + srcFileName);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		return result;
	}
	
	@PostMapping("/removeFile")
	   public ResponseEntity<Boolean> removeFile(String fileName){
	       String srcFileName= null;
	       log.info("removeFile-----");
	      
	       try{
	           srcFileName = URLDecoder.decode(fileName, "UTF-8");
	           log.info("srcFileName: "+srcFileName);
	           File file = new File(uploadPath + File.separator+ srcFileName);
	           boolean result = file.delete();
	           File thumnailfile =
	                   new File(file.getParent(), "s_" + file.getName());
	           result = thumnailfile.delete();
	           return new ResponseEntity<>(result, HttpStatus.OK);
	       }catch (Exception e){
	           e.printStackTrace();
	           return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	       }      
	   }

}