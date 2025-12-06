package com.example.demo.Services;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.example.demo.Entities.User;

import java.util.Collection;
import java.util.Collections;
public class MyUserDetails implements UserDetails {
	
	private final User user;
	public MyUserDetails(User user) {
		this.user = user;
	}
	@Override
	//このユーザーが持っている「権限（ロール）」をSpringに教える
	//GrantedAuthority もしくはそのサブクラスのオブジェクトを集めたコレクションが戻り値となる
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO 自動生成されたメソッド・スタブ
		//「このユーザーは user.getRole() という権限を1つだけ持っているリストを返すよ」
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
	}

	@Override
	//DBに保存されているハッシュ化されたパスワードを返す
	public String getPassword() {
		// TODO 自動生成されたメソッド・スタブ
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO 自動生成されたメソッド・スタブ
		return user.getName();
	}
	@Override
	//アカウントが期限切れになっていないか（true なら使用可能）
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	//アカウントがロックされていないか（true なら使用可能）
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	//パスワードの有効期限が切れていないか（true なら使用可能）
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	//アカウントが有効かどうか（true ならログイン可）
	public boolean isEnabled() {
		return true;
	}
}
