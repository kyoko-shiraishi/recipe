package com.example.demo.DTO;
import com.example.demo.*;

public class StepRequest {
    private Long id;           // DBにある既存手順のID（新規追加ならnull）
    private int stepNumber;    // 表示順
    private String content;    // 手順内容
    private String img;    // 画像パス（文字列）

    // --- getter ---
    public Long getId() {
        return id;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public String getContent() {
        return content;
    }

    public String getImg() {
        return img;
    }

    // --- setter ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
