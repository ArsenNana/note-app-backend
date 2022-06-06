package de.note.app.io.services;

import org.springframework.http.ResponseEntity;

import de.note.app.io.dto.LoginDto;
import de.note.app.io.dto.SignedInUserDto;
import de.note.app.io.dto.UserDto;

public interface AuthService {

	public ResponseEntity<?> registerUser(UserDto userDto);

	public SignedInUserDto login(LoginDto loginDto);
}
