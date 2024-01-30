package com.example.loc.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import jakarta.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.loc.domain.Location.LocationImg;
import com.example.loc.repository.LocationImgRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ImgService {
    
    private String imgLocation = "C:/location/img";

    private final LocationImgRepository locationImgRepository;

    private final FileService fileService;

    public void saveImg(@Valid LocationImg locationImg, MultipartFile imgFile) throws Exception {
        String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) { 
            // 이미지가 존재한다면
            imgName = fileService.uploadFile(imgLocation, oriImgName, imgFile.getBytes());
            imgUrl = "C:/location/img/" + imgName;
        }

        // 이미지 정보 저장
        locationImg.regImg(oriImgName, imgName, imgUrl);
        locationImgRepository.save(locationImg);
    }

    public String getBase64Image(String imgUrl) throws IOException {
        if (imgUrl != null) {
            // 이미지 파일 읽어오기
            File imgFile = new File(imgUrl);
            byte[] fileContent = Files.readAllBytes(imgFile.toPath());

            // Base64로 변환
            String base64Image = Base64.getEncoder().encodeToString(fileContent);
            return base64Image;
        }
        return null;
    }
}
