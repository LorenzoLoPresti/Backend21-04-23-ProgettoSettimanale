package it.epicode.progettoSettimanale.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.epicode.progettoSettimanale.auth.entity.User;
import it.epicode.progettoSettimanale.auth.payload.LoginDto;
import it.epicode.progettoSettimanale.auth.repository.UserRepository;

@Service
public class UserService {

	@Autowired UserRepository repo;
	
	public User returnLoggedUser(LoginDto login) {
		return repo.findLoggedUser(login.getUsername());
	}
	
	public String deleteUserById(Long id) {
		repo.deleteById(id);
		return "User deleted";
	}
	
	public User findUserById(Long id) {
		return repo.findById(id).get();
	}
}
