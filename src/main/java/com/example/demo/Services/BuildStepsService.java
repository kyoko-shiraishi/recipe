package com.example.demo.Services;
import com.example.demo.Entities.*;
import com.example.demo.DTO.*;
import org.springframework.stereotype.Service;

@Service
public class BuildStepsService {
public Step buildSteps(RecipeDTO.StepDTO sdto,Img step_img,Recipe recipe,int stepNum) {
	Step step = new Step();
	step.setImg(step_img);
	step.setStepNumber(stepNum);
	step.setContent(sdto.getContent());
	step.setRecipe(recipe);
	return step;
}
}
