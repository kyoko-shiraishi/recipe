package com.example.demo.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import com.example.demo.Entities.Img;
import com.example.demo.repository.ImgRepository;
@Service
public class SaveImgService {
	private final ImgRepository img_repository;
	public SaveImgService(ImgRepository imgrepo) {
		this.img_repository = imgrepo;
	}
	
	public Img saveAndRegister(MultipartFile imgFile) throws IOException {
		//新規画像投稿がないならreturn null
		//メソッドの戻り値がImg→必ずImgかnullを返さないといけない。returnだけだと何も返さないので×
	    if (imgFile == null || imgFile.isEmpty()) return null;
		//uploads ディレクトリを表すPathオブジェクトuploadDirを作る
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);
        
        //元のファイル名を取得
        
        String original = imgFile.getOriginalFilename();
        
        //元のファイル名を正規表現に直す
        String safeName = original.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9._-]","");
        
        //正規化されたファイル名をユニークにして唯一無二のファイル名にする     
        String fileName = UUID.randomUUID() + "_" + safeName;
        
        //ディレクトリ＋ファイル名＝フルパス
        Path filePath = uploadDir.resolve(fileName);
        
        //保存場所が用意できたのでデータ書き込む
        Files.write(filePath, imgFile.getBytes());
        
        
        //Imgオブジェクトをnewして保存→保存済みのオブジェクトを戻す
        Img img = new Img();
        img.setPath("/uploads/"+fileName);
        img.setName(original);
        return  img_repository.save(img);
	    
	}
}

