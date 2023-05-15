package com.example.concalendar.util.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * The type S 3 poster service.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class S3UploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    // 메소드 오버로딩 순서 MultipartFile & Long -> Post 이미지 저장
    public String uploadFileToS3(MultipartFile multipartFile, Long post_id) throws IOException {
        String s3_file_path = "post/"+post_id+"/"+System.currentTimeMillis();

        return uploadFile(multipartFile,s3_file_path);
    }
    // 메소드 오버로딩 순서 Long & MultipartFile -> 공연 이미지 저장
    public String uploadFileToS3(MultipartFile multipartFile, String concert_title) throws IOException {
        String s3_file_path = "Concert/"+concert_title;

        return uploadFile(multipartFile,s3_file_path);
    }

    /**
     * Upload file string.
     *
     * @param multipartFile the multipart file
     * @return the string
     * @throws IOException the io exception
     */
    public String uploadFile(MultipartFile multipartFile, String s3_file_path) throws IOException {
        String fileName = multipartFile.getOriginalFilename();

        //파일 형식 구하기
        String ext = fileName.split("\\.")[1];
        String contentType = "";

        if (ext == "jpeg"){
            contentType = "image/jpeg";
        }
        else if(ext == "png"){
            contentType = "image/png";
        }

        fileName = s3_file_path+"."+ext;

        log.info("파일 이름은 {}",fileName);

        // S3 객체의 메타 데이터를 추가하는 작업
        try {
            // S3 객체에 추가할 메타데이터 객체 생성
            ObjectMetadata metadata = new ObjectMetadata();
            // 메타데이터 ContentType을 설정하기
            // 현재는 jpeg or png
            metadata.setContentType(contentType);

            // S3버킷에 메타데이터 추가하기
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        //object 정보 가져오기
        ListObjectsV2Result listObjectsV2Result = amazonS3.listObjectsV2(bucket);
        List<S3ObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();

        for (S3ObjectSummary object: objectSummaries) {
            System.out.println("object = " + object.toString());
        }
        return amazonS3.getUrl(bucket, fileName).toString();

    }

}
