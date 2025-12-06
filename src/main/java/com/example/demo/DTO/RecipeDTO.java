package com.example.demo.DTO;

import java.util.List;
import java.util.ArrayList;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.DTO.*;

// レシピ名・コメント・手順リスト・画像リストなどフォームからの情報を格納するDTO
public class RecipeDTO {

    private Long id;
    private String name;
    private String comment;
    private MultipartFile mainImg; // 新規作成時に使う
    private List<RecipeDTO.StepDTO> steps = new ArrayList<>();
    private List<RecipeDTO.Recipe_IngredientDTO> ingredients = new ArrayList<>();
    private Long existingMainImgId;
    private String existingMainImgPath;

    // --- Getter / Setter ---
    public Long getId() { 
    	return id; 
    	}
    public void setId(Long id) { 
    	this.id = id; 
    	}

    public String getName() { 
    	return name; 
    	}
    public void setName(String name) { 
    	this.name = name; 
    	}

    public String getComment() {
    	return comment; 
    	}
    public void setComment(String comment) { 
    	this.comment = comment;
    	}

    public MultipartFile getMainImg() { 
    	return mainImg; 
    	}
    public void setMainImg(MultipartFile mainImgFile) { 
    	this.mainImg = mainImgFile;
    	}

    public List<StepDTO> getSteps() { 
    	return steps; 
    	}
    public void setSteps(List<StepDTO> steps) { 
    	this.steps = steps; 
    	}

    public List<Recipe_IngredientDTO> getIngredients() { 
    	return ingredients; 
    	}
    public void setIngredients(List<Recipe_IngredientDTO> amos) { 
    	this.ingredients = amos;
    	}
    public Long getExistingMainImgId() {
        return existingMainImgId;
    }

    
    public void setExistingMainImgId(Long existingMainImgId) {
        this.existingMainImgId = existingMainImgId;
    }
    public String getExistingMainImgPath() {
        return existingMainImgPath;
    }

    
    public void setExistingMainImgPath(String existingMainImgPath) {
        this.existingMainImgPath = existingMainImgPath;
    }


    // ===== 静的インナークラスStepRequest =====
    public static class StepDTO {
        private Long id;                // DBにある既存手順のID（新規追加ならnull）
        private int stepNumber;         // 表示順
        private String content;         // 手順内容
        private MultipartFile mediaFile; // 手順画像
        private Long existingImgId;// DBにある既存画像のIDを保持
        private String existingImgPath; // DBから拾った画像のファイルパス
        private boolean removeImg= false; // trueなら既存画像削除


        public Long getId() { 
        	return id;
        	}
        public void setId(Long id) { 
        	this.id = id;
        	}

        public int getStepNumber() { 
        	return stepNumber; 
        	}
        public void setStepNumber(int stepNumber) { 
        	this.stepNumber = stepNumber; 
        	}

        public String getContent() { 
        	return content; 
        	}
        public void setContent(String content) {
        	this.content = content; 
        	}

        public MultipartFile getMediaFile() { 
        	return mediaFile; 
        	}
        public void setMediaFile(MultipartFile mediaFile) { 
        	this.mediaFile = mediaFile; 
        	}
        public Long getExistingImgId() {
            return existingImgId;
        }

        
        public void setExistingImgId(Long existingImgId) {
            this.existingImgId = existingImgId;
        }
        public String getExistingImgPath() {
            return existingImgPath;
        }

        
        public void setExistingImgPath(String existingImgPath) {
            this.existingImgPath = existingImgPath;
        }
        public boolean isRemoveImg() {
            return removeImg;
        }

        public void setRemoveImg(boolean removeImg) {
            this.removeImg = removeImg;
        }

    }

    // ===== 静的インナークラスRecipe_IngredientDTO =====
    public static class Recipe_IngredientDTO{
        private Long id;
        private Long recipeId;
        private String name;
        private String quantity;

        public Long getId() { 
        	return id; 
        	}
        public void setId(Long id) { 
        	this.id = id; 
        	}

        public Long getRecipeId() { 
        	return recipeId;
        	}
        public void setRecipeId(Long recipeId) {
        	this.recipeId = recipeId; 
        	}

        public String getName() { 
        	return name; 
        	}
        public void setName(String mateName) { 
        	this.name = mateName;
        	}

        public String getQuantity() { 
        	return quantity; 
        	}
        public void setQuantity(String amount) { 
        	this.quantity = amount;
        	}
    }
}
