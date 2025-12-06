package com.example.demo.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
@Configuration
public class SecurityConfig {
	//「Springさん、このアプリに UserDetailsService を実装してるやつがあれば注入してね」
	//Spring「あ、@Service の付いた MyUserDetailsService がある！」
	//UserDetailsService を実装してるし、唯一の実装だからこれでOK！
	//→ userDetailsService に MyUserDetailsService を注入してくれる
	@Autowired 
	private UserDetailsService userDetailsService;
	
	@Autowired 
	private LoginSuccessHandler loginSuccessHandler;
	@Bean
	public PasswordEncoder passwordEncoder() {
		//new できる = 必要なオーバーライドが全部終わっている
		return new BCryptPasswordEncoder();
	}
	// 認証プロバイダーの設定
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests(authz -> authz
				.requestMatchers("/login","/register","/register_form", "/error").permitAll()
				.anyRequest().authenticated()
		)
		.formLogin(form->form
				//formLogin の中で Spring Securityが自動で渡してくれるform という設定用オブジェクトを受け取って、
				//それに loginPage(...) や defaultSuccessUrl(...) を設定してる
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.successHandler(loginSuccessHandler)
				.permitAll()//ログインページは誰でも見れるようにする
								
		)
		.logout(logout->logout
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login?logout")
			.permitAll()
		);
		//Spring Security のフィルタチェーン（内部処理のルート）を完成させて返す
		return http.build();
		
	}
}
