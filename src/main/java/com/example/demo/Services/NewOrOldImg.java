package com.example.demo.Services;
import java.io.IOException;
import com.example.demo.Services.*;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import com.example.demo.Entities.Img;
import com.example.demo.repository.ImgRepository;

@Service
public class NewOrOldImg {
	private final ImgRepository img_repository;
	private final SaveImgService saved_img_service;
public NewOrOldImg(ImgRepository img_repository,SaveImgService saved_img_service) {
	this.img_repository  = img_repository;
	this.saved_img_service = saved_img_service;
}
public Img createImg(MultipartFile mediaFile, Long existingImgId) {
    Img img = null;
    if(mediaFile != null && !mediaFile.isEmpty()) {
        try {
            img = saved_img_service.saveAndRegister(mediaFile);
        } catch (IOException e) {
            e.printStackTrace(); // ここで例外処理
            // 必要に応じて RuntimeException に変換することも可能
        }
    } else if(existingImgId != null) {
        img = img_repository.findById(existingImgId).orElse(null);
    }
    return img;
}

}
