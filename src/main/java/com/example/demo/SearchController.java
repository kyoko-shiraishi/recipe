package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; //追記
import org.springframework.web.bind.annotation.RequestParam; ///追記

 
 @Controller
 public class SearchController {
 	@RequestMapping(value="/search",method=RequestMethod.GET)
 	//method="get" → ブラウザのURLにクエリパラメータが付く（例：/search?dish-name=鉄粉おにぎり）
 	//→dish-name という名前の検索パラメータに『鉄粉おにぎり』という値を指定して、/search にアクセス
 	//クエリパラメータ dish-name を keyword 変数に格納
 	public String search(@RequestParam("dish-name") String keyword,Model mod) {
 		mod.addAttribute("hoge",keyword);
 		return "index";
 	}
 }

