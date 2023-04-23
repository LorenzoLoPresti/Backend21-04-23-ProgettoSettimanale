package it.epicode.progettoSettimanale;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.epicode.progettoSettimanale.auth.controller.TestController;

class TestControllerTest {

	TestController testController = new TestController();
	
	@Test
	void testAllAccess() {
		//String result = (String) testController.allAccess().getBody();
		//System.out.println(result);
		ResponseEntity<?> result = testController.allAccess();
		assertEquals("Public Content.", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void testAuthenticatedAccess() {
		ResponseEntity<?> result = testController.autenticatedAccess();
		assertEquals("Autenticated Content.", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	void testUserAccess() {
		ResponseEntity<?> result = testController.userAccess();
		assertEquals("User Content.", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void testuModeratorAcces() {
		ResponseEntity<?> result = testController.moderatorAccess();
		assertEquals("Moderator Board.", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	void testuAdminAcces() {
		ResponseEntity<?> result = testController.adminAccess();
		assertEquals("Admin Board.", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}
