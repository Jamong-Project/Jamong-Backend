package com.example.jamong.volunteer.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.example.jamong.exception.FileResizeFailException;
import com.example.jamong.volunteer.domain.Picture;
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
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service {
    private static final  Integer IMAGE_TARGET_SIZE = 300;
    private static final String FILE_EXTENSION_SEPARATOR = ".";

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public List<Picture> uploadFile(List<MultipartFile> multipartFiles) {
        List<Picture> pictureList = new ArrayList<>();
        
        for (MultipartFile multipartFile : multipartFiles){
            addPictureToList(pictureList, multipartFile);
        }
        return pictureList;
    }

    private void addPictureToList(List<Picture> pictureList, MultipartFile multipartFile) {
        String fileName = buildFileName(multipartFile.getOriginalFilename());
        String fileFormatName = getFileFormatName(multipartFile);

        if (isImage(multipartFile)){
            validateFileExists(multipartFile);

            MultipartFile resizedFile = resizeImage(fileName, fileFormatName, multipartFile, IMAGE_TARGET_SIZE);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(resizedFile.getContentType());

            uploadToBucket(fileName, resizedFile, objectMetadata);

            pictureList.add(
                    Picture.builder()
                            .url(amazonS3Client.getUrl(bucketName, fileName).toString())
                            .fileName(fileName)
                            .build()
            );
        }
    }

    private boolean isImage(MultipartFile multipartFile) {
        return Objects.requireNonNull(multipartFile.getContentType()).contains("image");
    }

    public static String buildFileName(String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);

        return UUID.randomUUID() + fileName + fileExtension;
    }

    private String getFileFormatName(MultipartFile multipartFile) {
        return multipartFile.getContentType().substring(multipartFile.getContentType().lastIndexOf("/") + 1);
    }

    private void uploadToBucket(String fileName, MultipartFile resizedFile, ObjectMetadata objectMetadata) {
        try (InputStream inputStream = resizedFile.getInputStream()) {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            objectMetadata.setContentLength(bytes.length);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayInputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
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
            throw new FileResizeFailException();
        }
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
