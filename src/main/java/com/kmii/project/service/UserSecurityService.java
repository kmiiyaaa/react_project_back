package com.kmii.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kmii.project.entity.SiteUser;
import com.kmii.project.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		SiteUser siteUser = userRepository.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("사용자 없음")); //orElseThrow -> 없으면 null값 아니고 에러, 있으면 반환
		
		return org.springframework.security.core.userdetails.User
				.withUsername(siteUser.getUsername())
				.password(siteUser.getPassword()) //비밀번호가 암호화되어 있어야 함
				.authorities("USER") //권한부여
				.build(); //UserDetails 객체 생성 반환

}
	
}
