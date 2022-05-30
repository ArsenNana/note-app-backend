package de.note.app.io.services;

import java.nio.charset.StandardCharsets;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

import de.note.app.io.dao.UserRepository;
import de.note.app.io.dto.LoginDto;
import de.note.app.io.dto.UserDto;
import de.note.app.io.entity.User;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private UserRepository userRepos;
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public User registerUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		String passwordHashed = Hashing.sha256().hashString(userDto.getPassword(), StandardCharsets.UTF_8).toString();
		user.setPassword(passwordHashed);
		return this.userRepos.save(user);

	}

	@Override
	public User login(LoginDto loginDto) {
		User user = this.userRepos.findByUsernameAndPassword(loginDto.getUsername(),
				Hashing.sha256().hashString(loginDto.getPassword(), StandardCharsets.UTF_8).toString());
		if (user == null) {
			return null;
		}
		return null;
	}

}
