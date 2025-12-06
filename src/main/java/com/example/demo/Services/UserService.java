package com.example.demo.Services;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.demo.Entities.User;
import com.example.demo.DTO.UserDTO;
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	// コンストラクタでDI
	public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	// ユーザー登録処理
	public void register(UserDTO request) {
		if(userRepository.findByName(request.getName()).isPresent()) {
			throw new IllegalArgumentException("そのユーザー名はすでに使われています");
		}
		//パスワードをハッシュ化
		//多態性（ポリモーフィズム）を活かすためにPasswordEncoderで受ける
		String encoderedPassword = passwordEncoder.encode(request.getPassword());
		//エンティティを作成/保存
		User user = new User();
		user.setName(request.getName());
		user.setPassword(encoderedPassword);
		user.setRole("ROLE_USER");
		userRepository.save(user);
	}
	
}
