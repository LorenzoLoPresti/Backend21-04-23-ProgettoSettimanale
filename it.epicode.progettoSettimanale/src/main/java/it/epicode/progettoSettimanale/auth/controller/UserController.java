package it.epicode.progettoSettimanale.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.progettoSettimanale.auth.entity.Device;
import it.epicode.progettoSettimanale.auth.entity.ERole;
import it.epicode.progettoSettimanale.auth.entity.Role;
import it.epicode.progettoSettimanale.auth.entity.User;
import it.epicode.progettoSettimanale.auth.exception.ResourceNotFoundException;
import it.epicode.progettoSettimanale.auth.payload.LoginDto;
import it.epicode.progettoSettimanale.auth.repository.DeviceRepository;
import it.epicode.progettoSettimanale.auth.repository.RoleRepository;
import it.epicode.progettoSettimanale.auth.repository.UserRepository;
import it.epicode.progettoSettimanale.auth.service.AuthServiceImpl;
import it.epicode.progettoSettimanale.auth.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/users")
public class UserController {

	@Autowired UserService userService;
	@Autowired UserRepository userRepo;
	@Autowired DeviceRepository deviceRepo;
	@Autowired RoleRepository roleRepository;
	@Autowired AuthServiceImpl authService;
	
	
	
	@GetMapping("/{username}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getLoggedUser(@RequestBody LoginDto login, @PathVariable String username){
		if(!login.getUsername().equals(username)) {
			throw new EntityNotFoundException("Access denied");
		}
		return new ResponseEntity<>(userService.returnLoggedUser(login), HttpStatus.OK);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<?> getUserList(){
		return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/device/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getDeviceListByUser(@PathVariable Long id){
		List<Device> list = deviceRepo.findDeviceListByUserId(id);
		if(list.size() == 0) {
			return new ResponseEntity<>("User has no devices", HttpStatus.OK);
		}
		return new ResponseEntity<>(deviceRepo.findDeviceListByUserId(id), HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createUser(@RequestBody User user){
		Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER).get();
    	user.getRoles().add(userRole);
		return new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id){
		return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<?> editUser(@RequestBody User u){
		User u2 = userRepo.findById(u.getId()).get();
		u.setDeviceList(u2.getDeviceList());
		u.setRoles(u2.getRoles());
		if(u.getPassword() == null || u.getPassword().length() <= 4) {
			u.setPassword(u2.getPassword());
		} else {
			u.setPassword(authService.encodePsw(u.getPassword()));
		}
		return new ResponseEntity<>(userRepo.save(u), HttpStatus.OK);
	}
}
