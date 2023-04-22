package it.epicode.progettoSettimanale.auth.runner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.epicode.progettoSettimanale.auth.configuration.DeviceConfig;
import it.epicode.progettoSettimanale.auth.entity.Device;
import it.epicode.progettoSettimanale.auth.entity.EDeviceType;
import it.epicode.progettoSettimanale.auth.entity.ERole;
import it.epicode.progettoSettimanale.auth.entity.Role;
import it.epicode.progettoSettimanale.auth.entity.User;
import it.epicode.progettoSettimanale.auth.repository.DeviceRepository;
import it.epicode.progettoSettimanale.auth.repository.RoleRepository;
import it.epicode.progettoSettimanale.auth.repository.UserRepository;
import it.epicode.progettoSettimanale.auth.service.AuthService;
import it.epicode.progettoSettimanale.auth.service.DeviceService;


@Component
public class AuthRunner implements ApplicationRunner {
	
	@Autowired RoleRepository roleRepository;
	@Autowired DeviceRepository deviceRepository;
	@Autowired UserRepository userRepository;
	@Autowired PasswordEncoder passwordEncoder;
	@Autowired AuthService authService;
	@Autowired DeviceService deviceService;
	
	
	private Set<Role> adminRole;
	private Set<Role> moderatorRole;
	private Set<Role> userRole;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Run...");
		//SENZA RUOLI SETTATI NEL DB L'APPLICAZIONE NON FUNZIONA
		
		// SETTA RUOLI NEL DB
		//setRoleDefault();
		
		
//		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(DeviceConfig.class);
//	//	Device d = (Device) appContext.getBean("createDevice", EDeviceType.PHONE);
//		Device d = (Device) appContext.getBean("createDevice", EDeviceType.LAPTOP);
//		// deviceService.addDevice(d);
//		User u = userRepository.findById(1l).get();
//		d = deviceRepository.findById(2l).get();
//		u.getDeviceList().add(d);
//	//	userRepository.save(u);
	}
	
	private void setRoleDefault() {
		Role admin = new Role();
		admin.setRoleName(ERole.ROLE_ADMIN);
		roleRepository.save(admin);
		
		Role user = new Role();
		user.setRoleName(ERole.ROLE_USER);
		roleRepository.save(user);
		
		Role moderator = new Role();
		moderator.setRoleName(ERole.ROLE_MODERATOR);
		roleRepository.save(moderator);
		
		adminRole = new HashSet<Role>();
		adminRole.add(admin);
		adminRole.add(moderator);
		adminRole.add(user);
		
		moderatorRole = new HashSet<Role>();
		moderatorRole.add(moderator);
		moderatorRole.add(user);
		
		userRole = new HashSet<Role>();
		userRole.add(user);
	}

}
