package it.epicode.progettoSettimanale.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/all")
	public ResponseEntity<?> allAccess() {
		return new ResponseEntity<>("Public Content.", HttpStatus.OK);
	}

	@GetMapping("/auth")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> autenticatedAccess() {
		return new ResponseEntity<>("Autenticated Content.", HttpStatus.OK);
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> userAccess() {
		return new ResponseEntity<>("User Content.", HttpStatus.OK);
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<?> moderatorAccess() {
		return new ResponseEntity<>("Moderator Board.", HttpStatus.OK);
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> adminAccess() {
		return new ResponseEntity<>("Admin Board.", HttpStatus.OK);
	}
}
