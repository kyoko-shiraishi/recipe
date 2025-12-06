package com.example.demo.Config;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Path;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		Path uploadDir = Paths.get("uploads").toAbsolutePath();
        String uploadPath = uploadDir.toUri().toString(); // 末尾に / が自動で付く
        registry.addResourceHandler("/uploads/**")//ブラウザから /uploads/xxx.png というリクエストが来たときにマッチするパターン
                .addResourceLocations(uploadPath)//実際にどのフォルダのファイルを返すかを指定
                .setCacheControl(CacheControl.noCache());
	}
}