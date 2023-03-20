package com.hyundai.higher.webRTC.service.fileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.hyundai.higher.webRTC.dto.FileUploadDto;

/**
 * @since   : 2023. 3. 20.
 * @FileName: FileService.java
 * @author  : 박성환
 * @설명    : AWS S3 파일 인터페이스

 * <pre>
 *   수정일         수정자               수정내용
 * ----------      --------    ---------------------------
 * 2023. 3. 20.     박성환      	최초 생성
 * </pre>
 */
public interface FileService {
    // 파일 업로드를 위한 메서드 선언
    FileUploadDto uploadFile(MultipartFile file, String transaction, String roomId);

    // 현재 방에 업로드된 모든 파일 삭제 메서드
    void deleteFileDir(String path);

    // 컨트롤러에서 받아온 multipartFile 을 File 로 변환시켜서 저장하기 위한 메서드
    default File convertMultipartFileToFile(MultipartFile mfile, String tmpPath) throws IOException {
        File file = new File(tmpPath);

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(mfile.getBytes());
            }
            return file;
        }
        throw new IOException();
    }

    // 파일 삭제
    default void removeFile(File file){
        file.delete();
    }

    ResponseEntity<byte[]> getObject(String fileDir, String fileName) throws IOException;
}
