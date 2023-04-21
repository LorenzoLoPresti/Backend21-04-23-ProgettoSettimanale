package it.epicode.progettoSettimanale.auth.service;

import it.epicode.progettoSettimanale.auth.payload.LoginDto;
import it.epicode.progettoSettimanale.auth.payload.RegisterDto;

public interface AuthService {
    
	String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
    
}
