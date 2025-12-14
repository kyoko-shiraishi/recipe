package com.example.demo.Services;
import com.example.demo.Entities.*;
import com.example.demo.DTO.*;
import org.springframework.stereotype.Service;

@Service
public class BuildStepsService {
    private BuildStepsService() {} // new禁止（ユーティリティパターン）

    public static Step of(RecipeDTO.StepDTO dto, Img img, Recipe recipe, int stepNum) {
        Step step = new Step();
        step.setImg(img);
        step.setStepNumber(stepNum);
        step.setContent(dto.getContent());
        step.setRecipe(recipe);
        return step;
    }
}
