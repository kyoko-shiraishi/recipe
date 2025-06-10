package com.example.demo.Logic;
import java.util.ArrayList;
import java.util.List;

public class CharacterManager {
	private List<Character> characters; //Character 型の要素を入れるための リスト型のフィールド（変数）
			public CharacterManager() {
			characters = new ArrayList<>();
			characters.add(new Ninja("そんなもん"));
			}
	
	//characters リストの内容を**外部から取得できるようにするメソッド（getter）
	public List<Character> getCharacters() {
        return characters;
    }
}
