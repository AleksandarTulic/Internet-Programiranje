package com.example.LoginRegistration.appuser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.LoginRegistration.registration.token.ConfirmationToken;
import com.example.LoginRegistration.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

//for us the username will be the email
@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService{
	private static final String USER_NOT_FOUND = "user with email %s not found";
	
	@Autowired private final AppUserRepository appUserRepository = null;
	@Autowired private final BCryptPasswordEncoder bCryptPasswordEncoder = null;
	@Autowired private final ConfirmationTokenService confirmationTokenService = null;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
	}

	public String singUpUser(AppUser appUser) {
		boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
		
		if (userExists) {
			throw new IllegalStateException("Email already taken.");
		}
		
		String encoded = bCryptPasswordEncoder.encode(appUser.getPassword());
		appUser.setPassword(encoded);
		
		appUserRepository.save(appUser);
		
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		
		return token;
	}
	
	public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
