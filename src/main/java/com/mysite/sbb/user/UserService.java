package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
//		스프링시큐리티에서 BCryptPaswerdEncoder를 사용. 
		// 해시함수를 사용하는데 비밀번호 같은 보안 정보를 안전하게 저장하고 검증할때 사용하는 암호화 기술.
		user.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(user);
		return user;
	}
	
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);
		
		if(siteUser.isPresent()) {
			return siteUser.get();					
		}else {
			throw new DataNotFoundException("siteuser not found");
		}
	}

}