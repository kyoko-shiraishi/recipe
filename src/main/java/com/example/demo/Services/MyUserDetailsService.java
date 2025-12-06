package com.example.demo.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;
import com.example.demo.Entities.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		User user = userRepository.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new MyUserDetails(user);
	}

}
