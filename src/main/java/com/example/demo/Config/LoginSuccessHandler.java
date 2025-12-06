package com.example.demo.Config;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	//request：ログイン処理を行った HTTP リクエストオブジェクト
    //response：ブラウザへのレスポンス（リダイレクトなど）を操作するためのオブジェクト
    //auth：認証に成功したユーザー情報を含む Authentication オブジェクト
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {
		// TODO 自動生成されたメソッド・スタブ
		
	    String userName = auth.getName(); // ログイン中のユーザー名を取得
		HttpSession session = request.getSession();//リクエストスコープへデータを登録
		session.setAttribute("username",userName);
		System.out.println("LoginSuccessHandler：ログイン成功、ユーザー名＝" + userName);
		// リクエストキャッシュから SavedRequest を取得
	    SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);

	    if (savedRequest != null) {
	        String targetUrl = savedRequest.getRedirectUrl();
	        response.sendRedirect(targetUrl);
	    } else {
	        response.sendRedirect("/");
	    }

}
}
