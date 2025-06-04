package br.com.fatec.easyDrive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.repository.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

//	public UserDetailsServiceImpl(UserRepository userRepository) {
//	    this.userRepository = userRepository;
//	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		return userRepository.findByUsername(username).map(user -> new UserAuthenticated(user))
				.orElseThrow(() -> new NotFoundException("Usuário com login " + username + " não encontrado."));
	}

}