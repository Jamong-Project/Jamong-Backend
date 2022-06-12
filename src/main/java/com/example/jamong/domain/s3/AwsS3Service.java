package com.example.jamong.domain.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.example.jamong.domain.picture.Picture;
import com.example.jamong.domain.volunteer.Volunteer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service {
    private final static Integer IMAGE_TARGET_SIZE = 300;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private List<Picture> pictureList = new ArrayList<>();

    public List<Picture> uploadFile(List<MultipartFile> multipartFiles) {
        for (MultipartFile multipartFile : multipartFiles){
            String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());
            String fileFormatName = multipartFile.getContentType().substring(multipartFile.getContentType().lastIndexOf("/") + 1);

            if (Objects.requireNonNull(multipartFile.getContentType()).contains("image")){
                validateFileExists(multipartFile);

                MultipartFile resizedFile = resizeImage(fileName, fileFormatName, multipartFile, IMAGE_TARGET_SIZE);

                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(resizedFile.getContentType());

                try (InputStream inputStream = resizedFile.getInputStream()) {
                    byte[] bytes = IOUtils.toByteArray(inputStream);
                    objectMetadata.setContentLength(bytes.length);
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

                    amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayInputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
                } catch (IOException e) {
                    throw new IllegalArgumentException();
                }
                pictureList.add(
                        Picture.builder()
                                .url(amazonS3Client.getUrl(bucketName, fileName).toString())
                                .fileName(fileName)
                                .build()
                );
                log.info(pictureList.toString());
            }
        }
        return pictureList;
    }

    MultipartFile resizeImage(String fileName, String fileFormatName, MultipartFile originalImage, int targetWidth) {
        try {
            BufferedImage image = ImageIO.read(originalImage.getInputStream());
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

            if(originWidth < targetWidth)
                return originalImage;

            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", targetWidth);
            scale.setAttribute("newHeight", targetWidth * originHeight / originWidth);
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, fileFormatName, baos);
            baos.flush();

            return new MockMultipartFile(fileName, baos.toByteArray());

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 리사이즈에 실패했습니다.");
        }
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
